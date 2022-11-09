package com.hf.common.infrastructure.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 公共校验错误枚举类
 * @date 2021/1/14
 */
public enum DictRespEnum implements Serializable {

  EXCEPTION_TYPE_VALUE_REPEAT(40000, "字典类型值重复"),
  EXCEPTION_TYPE_NAME_REPEAT(40001, "字典类型名称重复"),
  EXCEPTION_VALUE_REPEAT(40002, "同一类型字典值重复"),
  EXCEPTION_NAME_REPEAT(40003, "同一类型字典名称重复");
  private int code;
  private String msg;

  DictRespEnum(int code, String msg) {
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
