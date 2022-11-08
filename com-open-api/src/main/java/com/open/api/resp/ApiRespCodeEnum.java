package com.open.api.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 网关异常
 * @date 2021/2/20
 */
public enum ApiRespCodeEnum implements Serializable {

  TOKEN_NOT_EXIST(101, "TOKEN不存在,请重新登录!"),
  TOKEN_IS_EXPIRE(102, "TOKEN已过期,请重新登录!"),
  TOKEN_NOT_EXIST_OR_EXPIRE(103, "TOKEN不存在或者已过期,请重新登录!"),
  SESSION_NOT_EXIST_OR_EXPIRE(104, "SSO未登录,请重新登录!"),
  RESULT_SYSTEM_ERROR(201, "系统访问异常,请联系管理员!"),
  NOT_HAVE_RIGHT(202, "权限不足,请求被拒绝!"),
  LOGIN_NOT_EXIST(203, "登陆编号不存在，不能访问系统!"),
  DATA_NOT_SUPPORT_GET(301, "数据交互不支持GET请求!"),
  DATA_PARAM_IS_EMPTY(302, "数据交互请求参数为空!"),
  DATA_ACCOUNT_IS_EMPTY(303, "数据交互请求中账户不能为空!"),
  DATA_REQUEST_IS_EMPTY(304, "数据交互请求中请求编号不能为空!"),
  DATA_CONTENT_IS_EMPTY(305, "数据交互请求中请求内容不能为空!"),
  DATA_TIME_IS_EMPTY(306, "数据交互请求中时间戳不能为空!"),
  DATA_TIME_EXPIRED(307, "数据交互请求中时间已过期!"),
  DATA_ACCOUNT_NOT_EXIST(308, "数据交互请求中账户不存在!"),
  DATA_SIGN_ERROR(309, "数据交互请求验签失败!"),
  ERR_AUTH_FAIL_CODE(310, "认证失败!"),
  REQUEST_CANNOT_DUPLICATE(998, "重复请求!"),
  UNKOWN_SERVICE_ERROR(999, "未知错误，服务出现异常!");

  private int code;
  private String msg;

  ApiRespCodeEnum(int code, String msg) {
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
