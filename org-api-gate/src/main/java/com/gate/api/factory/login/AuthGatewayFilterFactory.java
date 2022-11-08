package com.gate.api.factory.login;

import com.auth.common.infrastructure.config.UserAuthConfig;
import com.auth.common.infrastructure.util.jwt.UserAuthUtil;
import com.gate.api.factory.BaseGateWayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author 曾仕斌
 * @function 鉴权网关
 * @date 2021/3/30
 */
@Slf4j
@Component
public class AuthGatewayFilterFactory extends BaseGateWayFilterFactory {

  public AuthGatewayFilterFactory(UserAuthConfig userAuthConfig,
      UserAuthUtil userAuthUtil, WebClient.Builder webClientBuilder) {
    this.userAuthConfig = userAuthConfig;
    this.userAuthUtil = userAuthUtil;
    this.webClientBuilder = webClientBuilder;
  }

  @Override
  public GatewayFilter apply(Object config) {
    this.needCheckPermission = false;
    return super.apply(config);
  }
}
