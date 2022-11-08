package com.hf.op.infrastructure.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 公共校验错误枚举类——远程调用使用
 * @date 2021/2/8
 */
public enum AuthRespCodeComEnum implements Serializable {
  LOGIN_TYPE_NOT_SUPPORT(11000, "不支持该登录方式!");

  private int code;
  private String msg;

  AuthRespCodeComEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
