package com.hf.op.infrastructure.global;

/**
 * Created by wpq on 2022/02/09.
 */
public class AuthSvcGlobalConst {

  /**
   * 是否默认密码，0：不是，1:是，如果是默认密码则登录后提醒修改密码
   */
  public static class DefaultPwd {

    /**
     * 不是
     */
    public static final Integer DefaultPwdNo = 0;
    /**
     * 是
     */
    public static final Integer DefaultPwdYes = 1;
  }

}
