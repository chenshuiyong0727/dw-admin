package com.gate.api.factory.data;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hf.common.domain.model.cache.user.CustomerInfo;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.open.api.resp.ApiResponseMsg;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author 曾仕斌
 * @function 响应过滤器，对响应数据进行加密和签名
 * @date 2021/10/9
 */
@Component
@Slf4j
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "customerInfoCache", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, CustomerInfo> customerInfoCache;

  private ObjectMapper jacksonObjectMapper;

  public ResponseGlobalFilter(ObjectMapper jacksonObjectMapper) {
    this.jacksonObjectMapper = jacksonObjectMapper;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String uri = request.getPath().pathWithinApplication().value();
    if (!uri.startsWith("/gw/data")) {
      return chain.filter(exchange);
    }
    log.info("uri : " + uri);
    ResponseDecorator decorator = new ResponseDecorator(exchange.getResponse());
    return chain.filter(exchange.mutate().response(decorator).build());
  }

  @Override
  public int getOrder() {
    return -1000;
  }

  public class ResponseDecorator extends ServerHttpResponseDecorator {

    public ResponseDecorator(ServerHttpResponse delegate) {
      super(delegate);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

      if (body instanceof Flux) {
        Flux<DataBuffer> fluxBody = (Flux<DataBuffer>) body;

        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
          DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
          DataBuffer join = dataBufferFactory.join(dataBuffers);

          byte[] content = new byte[join.readableByteCount()];
          join.read(content);
          DataBufferUtils.release(join);// 释放掉内存

          String bodyStr = new String(content, Charset.forName("UTF-8"));
          log.info("body " + bodyStr);
          //修改响应体
          // bodyStr = modifyBody(bodyStr);

          getDelegate().getHeaders().setContentLength(bodyStr.getBytes().length);
          return bufferFactory().wrap(bodyStr.getBytes(StandardCharsets.UTF_8));
        }));
      }
      return super.writeWith(body);
    }

    //加密并签名
    private String modifyBody(String jsonStr) {
      ApiResponseMsg apiResponseMsg = null;
      try {
        apiResponseMsg = jacksonObjectMapper.readValue(jsonStr, ApiResponseMsg.class);
      } catch (JsonProcessingException e) {
        log.error("ResponseGlobalFilter.modifyBody.end.readValue", e);
      }
      if (null == apiResponseMsg) {
        return null;
      }
      CustomerInfo customerInfo = customerInfoCache.get(apiResponseMsg.getAccountId());
      if (null == customerInfo) {
        return jsonStr;
      }
      String key = customerInfo.getSecretKey();
      if (StringUtilLocal.isEmpty(key)) {
        return jsonStr;
      }
      log.info("响应结果 ：" + JSONObject.toJSONString(apiResponseMsg));
      if (!ServerErrorConst.SUCCESS.equals(apiResponseMsg.getSubCode())
          || !apiResponseMsg.getCode().equals(CommonConstant.GW_SUCCESS)) {
        log.info("调用失败  ：" + apiResponseMsg.getRequestId());
      }
      log.info("响应结果参数 ：" + apiResponseMsg.data);
      String data = apiResponseMsg.encode(key);
      apiResponseMsg.setData(data);
      String signStr = apiResponseMsg.sign(key);
      apiResponseMsg.setSign(signStr);
      String result = null;
      try {
        result = jacksonObjectMapper.writeValueAsString(apiResponseMsg);
      } catch (JsonProcessingException e) {
        log.error("ResponseGlobalFilter.modifyBody.end.writeValueAsString", e);
      }
      return result;
    }
  }

}
