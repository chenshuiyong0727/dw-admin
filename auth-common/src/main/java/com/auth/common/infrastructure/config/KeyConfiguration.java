package com.auth.common.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RefreshScope
public class KeyConfiguration {

  @Value("${rsaSecret}")
  private String userSecret;

  private byte[] userPubKey;

  private byte[] userPriKey;
}
