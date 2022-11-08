package com.hf.common.infrastructure.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class AppInfo implements Serializable {

  private String appId;
  private String tocken;
  private String appName;


  public AppInfo(String appId, String tocken) {
    this.appId = appId;
    this.tocken = tocken;
  }

  public AppInfo(String appId, String tocken, String appName) {
    this.appId = appId;
    this.tocken = tocken;
    this.appName = appName;
  }


  @Override
  public String toString() {
    return this.appId + this.tocken;
  }
}
