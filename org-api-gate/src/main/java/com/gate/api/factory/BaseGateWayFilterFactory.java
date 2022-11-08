package com.gate.api.factory;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.infrastructure.config.UserAuthConfig;
import com.auth.common.infrastructure.constant.RedisKeyConstant;
import com.auth.common.infrastructure.exception.UserTokenException;
import com.auth.common.infrastructure.util.jwt.IJWTInfo;
import com.auth.common.infrastructure.util.jwt.UserAuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hf.common.domain.UserInfo;
import com.hf.common.domain.model.cache.user.CustomerInfo;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.BaseResponse;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.open.api.resp.ApiRespCodeEnum;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author 曾仕斌
 * @function 基础网关，实现了模板方法
 * @date 2021/3/26
 */
@Slf4j
public abstract class BaseGateWayFilterFactory extends AbstractGatewayFilterFactory {

  /**
   * 鉴权配置
   */
  protected UserAuthConfig userAuthConfig;

  /**
   * 鉴权工具
   */
  protected UserAuthUtil userAuthUtil;

  /**
   * 权限校验路径前缀
   */
  protected String checkUriPrefix;

  /**
   * 需要校验权限
   */
  protected boolean needCheckPermission = true;

  protected WebClient.Builder webClientBuilder;

  @CreateCache(expire = 1200, name = CommCacheConst.BASE_KEY_ORG
      + "tokenCache", cacheType = CacheType.REMOTE)
  protected Cache<String, String> tokenCache;

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG
      + "authCustomerAccount", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, CustomerInfo> authCustomerAccount;

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      // 获取当前网关访问的URI
      String requestUri = request.getPath().pathWithinApplication().value();
      log.info("requestUri " + requestUri);
      final String requestMethod = request.getMethod().toString();
      ServerHttpRequest.Builder mutate = request.mutate();
      request = exchange.getRequest().mutate()
          .header(CommonConstant.TRACE_ID, UUID.randomUUID().toString().replace("-", ""))
          .build();

      //网关不进行拦截的URI配置，常见如验证码、Login接口
      if (StringUtilLocal.isStartWithOneStr(requestUri, userAuthConfig.getFilterUrl())) {
        return chain.filter(exchange);
      }
      IJWTInfo user = null;
      try {
        user = getJWTUser(request, mutate);
      } catch (Exception e) {
        log.error("用户Token不存在或者过期！", e);
        return getVoidMono(exchange, ResponseMsg
                .createSysErrorResp(ApiRespCodeEnum.TOKEN_NOT_EXIST_OR_EXPIRE.getCode(),
                    ApiRespCodeEnum.TOKEN_NOT_EXIST_OR_EXPIRE.getMsg()),
            HttpStatus.UNAUTHORIZED);
      }
      if (null != user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        String json = null;
        try {
          json = jacksonObjectMapper.writeValueAsString(userInfo);
          request = exchange.getRequest().mutate()
              .header(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER, URLEncoder.encode(json, "UTF-8"))
              .build();
        } catch (Exception e) {
          log.error("GatewayFilter.apply", e);
        }
      }
      String customerId = request.getHeaders().getFirst(CommonConstant.CUSTOMER_ID);
      if (StringUtilLocal.isNotEmpty(customerId)) {
        CustomerInfo customerInfo = authCustomerAccount.get(customerId);
        if (null != customerInfo && null != customerInfo.getAccountId()) {
          request = exchange.getRequest().mutate()
              .header(CommonConstant.ACCOUNT_ID, customerInfo.getAccountId().toString())
              .build();
        }
      }

      //不需要权限校验则继续,一般管理平台都需要做权限校验
      if (!needCheckPermission) {
        return chain.filter(exchange.mutate().request(request).build());
      }
      Mono<ResponseMsg> checkPermissionInfoMono = webClientBuilder.build().
          get().uri(checkUriPrefix + requestUri, user.getUserId(), requestMethod).retrieve()
          .bodyToMono(ResponseMsg.class);
      final ServerHttpRequest requestTidy = request;
      return checkPermissionInfoMono.flatMap(responseMsg -> {
        if (ResponseMsg.isSuccess(responseMsg) && !"0".equals(responseMsg.getData())) {
          return chain.filter(exchange.mutate().request(requestTidy).build());
        } else {
          return getVoidMono(exchange,
              ResponseMsg.createSysErrorResp(ApiRespCodeEnum.NOT_HAVE_RIGHT.getCode(),
                  ApiRespCodeEnum.NOT_HAVE_RIGHT.getMsg()),
              HttpStatus.FORBIDDEN);
        }
      }).onErrorResume(
          (Function<Throwable, Mono<Void>>) throwable -> getVoidMono(exchange,
              ResponseMsg.createSysErrorResp(ApiRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
                  ApiRespCodeEnum.RESULT_SYSTEM_ERROR.getMsg()),
              HttpStatus.FORBIDDEN));
    };
  }

  /**
   * 返回session中的用户信息
   */
  private IJWTInfo getJWTUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx)
      throws Exception {
    //String tokenAuth = request.getQueryParams().getFirst("tokenAuth");
    List<String> strings = request.getHeaders().get(userAuthConfig.getTokenHeader());
    String authToken = null;
    if (CollectionUtils.isNotEmpty(strings)) {
      authToken = strings.get(0);
    }
    if (StringUtilLocal.isEmpty(authToken)) {
      throw new UserTokenException(ApiRespCodeEnum.TOKEN_NOT_EXIST.getMsg());
    }
    IJWTInfo infoFromToken = userAuthUtil.getInfoFromToken(authToken);
    String tokenCacheValue = tokenCache.get(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + authToken);
    if (StringUtilLocal.isEmpty(tokenCacheValue)) {
      throw new UserTokenException(ApiRespCodeEnum.TOKEN_IS_EXPIRE.getMsg());
    }
    ctx.header(userAuthConfig.getTokenHeader(), authToken);
    return infoFromToken;
  }

  /**
   * 网关抛异常
   */
  private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, BaseResponse body,
      HttpStatus status) {
    serverWebExchange.getResponse().setStatusCode(status);
    byte[] bytes = null;
    try {
      bytes = (jacksonObjectMapper.writeValueAsString(body)).getBytes(StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("BaseGateWayFilterFactory.getVoidMono:", e);
    }
    DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
    return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
  }
}
