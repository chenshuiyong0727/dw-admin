package com.gate.api.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author 曾仕斌
 * @function URI限流
 * @date 2021/2/24
 */
@Configuration
@Slf4j
public class ResolverConfig {

  /**
   * 接口限流
   */
  @Bean
  KeyResolver apiKeyResolver() {
    return exchange -> Mono.just(exchange.getRequest().getPath().value());
  }

}
