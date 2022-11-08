package com.gate.api.factory.access;

import com.auth.common.infrastructure.config.UserAuthConfig;
import com.auth.common.infrastructure.util.jwt.UserAuthUtil;
import com.gate.api.factory.BaseGateWayFilterFactory;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author 曾仕斌
 * @function 运营系统网关
 * @date 2021/11/28
 */
@Slf4j
@Component
public class OpGatewayFilterFactory extends BaseGateWayFilterFactory {

  @Value("${permission.check.uri.prefix.mp}")
  private String checkUriMp;

  public OpGatewayFilterFactory(UserAuthConfig userAuthConfig,
      UserAuthUtil userAuthUtil, WebClient.Builder webClientBuilder) {
    this.userAuthConfig = userAuthConfig;
    this.userAuthUtil = userAuthUtil;
    this.webClientBuilder = webClientBuilder;
  }

  @Override
  public GatewayFilter apply(Object config) {
    if (StringUtilLocal.isEmpty(checkUriPrefix)) {
      this.checkUriPrefix = checkUriMp;
    }
    return super.apply(config);
  }
}
