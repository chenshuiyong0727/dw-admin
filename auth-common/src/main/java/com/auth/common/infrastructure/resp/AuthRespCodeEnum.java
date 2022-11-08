package com.auth.common.infrastructure.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 鉴权错误枚举
 * @date 2021/3/31
 */
public enum AuthRespCodeEnum implements Serializable {

  ACCOUNT_NOT_EXIST(10001, "账户不存在"),
  PWD_IS_WRONG(10002, "密码错误"),
  LOGIN_TYPE_NOT_SUPPORT(10003, "不支持该登录方式!"),
  VERIFICATION_CODE_IS_ERROR(10004, "验证码错误!"),
  REFRESH_TOKEN_NOT_EXIST_OR_EXPIRE(10005, "REFRESH-TOKEN不存在或者已过期!"),
  NOT_RIGHT_REFRESH_TOKEN(10006, "没有权限刷新该TOKEN!"),
  TOKEN_TRANS_ERROR(10007, "TOKEN转换异常!"),
  TOKEN_CREATE_ERROR(10008, "TOKEN创建异常!"),
  MOBILE_EXIST_ERROR(10009, "该手机号已被注册!"),
  ACCOUNT_EXIST_ERROR(10010, "该账号已被注册!"),
  OLD_PASSWORD_ERROR(10011, "旧密码不正确!"),
  TWO_PASS_NO_THE_SAME(10012, "用户密码和确认密码不一致!"),
  ADD_AUTH_USER_FAIL(10013, "同步统一用户失败!");

  private Integer code;
  private String msg;

  AuthRespCodeEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
