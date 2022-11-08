package com.auth.common.infrastructure.constant;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 登录类型枚举
 * @date 2021/2/7
 */
public enum LoginAccountTypeEnum implements Serializable {

  USER_NAME(1, "用户名"),
  MOBILE(2, "手机号"),
  ID_CARD(3, "身份证");

  private int code;

  private String name;

  LoginAccountTypeEnum(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
