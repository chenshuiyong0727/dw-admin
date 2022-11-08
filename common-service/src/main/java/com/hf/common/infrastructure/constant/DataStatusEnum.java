package com.hf.common.infrastructure.constant;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 发送短信验证码场景
 * @date 2021/2/7
 */
public enum DataStatusEnum implements Serializable {

  DELETE(-1, "删除状态"),
  FORBIDDEN(0, "禁用状态"),
  ENABLE(1, "启用状态");

  private Integer code;

  private String desc;

  DataStatusEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
