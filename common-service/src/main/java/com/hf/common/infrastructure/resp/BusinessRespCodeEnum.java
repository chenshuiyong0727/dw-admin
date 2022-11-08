package com.hf.common.infrastructure.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 公共校验错误枚举类
 * @date 2021/1/14
 */
public enum BusinessRespCodeEnum implements Serializable {
  RESULT_SYSTEM_ERROR(1001, "系统异常,请联系管理员!"),
  SYSTEM_ACCESS_ERROR(1003, "系统访问异常,请联系管理员!"),
  DB_VERSION_ERROR(1004, "数据版本异常!"),
  PARAM_IS_EMPTY(1005, "基础参数为空");

  private int code;
  private String msg;

  BusinessRespCodeEnum(int code, String msg) {
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
