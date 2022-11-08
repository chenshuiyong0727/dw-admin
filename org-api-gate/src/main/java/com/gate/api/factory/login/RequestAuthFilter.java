package com.gate.api.factory.login;

import com.alibaba.fastjson.JSON;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.AppUtils;
import com.open.api.resp.ApiRespCodeEnum;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author CHENSY
 * @function 鉴权网关
 * @date 2022/5/30
 */
@Slf4j
@Component
public class RequestAuthFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String servletPath = request.getURI().getPath();
    if (!servletPath.contains("/v1/syncData") && !servletPath.contains("/v1/sso")) {
      return chain.filter(exchange);
    }
    log.info("ssoApi拦截到url请求----{}", servletPath);
    if (!AppUtils.isValid(request)) {
      // response
      exchange.getResponse().setStatusCode(HttpStatus.OK);
      ResponseMsg responseMsg = ResponseMsg
          .createSysErrorResp(ApiRespCodeEnum.ERR_AUTH_FAIL_CODE.getCode(),
              ApiRespCodeEnum.ERR_AUTH_FAIL_CODE.getMsg());
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(
          JSON.toJSONString(responseMsg).getBytes(
              StandardCharsets.UTF_8));
      ServerHttpResponse response = exchange.getResponse();
      //指定编码，否则在浏览器中会中文乱码
      response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
      return response.writeWith(Mono.just(buffer));
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 2;
  }
}
