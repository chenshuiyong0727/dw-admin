package com.auth.common.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class UserAuthConfig {

  @Value("${tokenHeader}")
  private String tokenHeader;

  @Value("${filterUrl}")
  private String filterUrl;

  private byte[] pubKeyByte;

  public String getTokenHeader() {
    return tokenHeader;
  }

  public void setTokenHeader(String tokenHeader) {
    this.tokenHeader = tokenHeader;
  }

  public byte[] getPubKeyByte() {
    return pubKeyByte;
  }

  public void setPubKeyByte(byte[] pubKeyByte) {
    this.pubKeyByte = pubKeyByte;
  }

  public String getFilterUrl() {
    return filterUrl;
  }

  public void setFilterUrl(String filterUrl) {
    this.filterUrl = filterUrl;
  }
}
