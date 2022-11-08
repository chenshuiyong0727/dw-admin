package com.hf.op.infrastructure.global;

/**
 * @author wpq
 * @function 部门全局常量
 * @Date 2021/11/29
 */
public class OpGlobalConst {

  /**
   * 重试次数
   */
  public final static Integer STRATEGY_EXECUTE_RETRY_TIMES = 3;

  /**
   * 用户类型：10-用户 11-企业用户 12-运营平台用户
   */
  public static class UserType {

    /**
     * 用户
     */
    public static final Integer CommonUser = 10;
    /**
     * 企业用户
     */
    public static final Integer EnterpriseUser = 11;
    /**
     * 运营平台用户
     */
    public static final Integer PlatformUser = 12;
    /**
     * SSO用户
     */
    public static final Integer SSOUser = 13;
  }

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

  /**
   * 是否部门 0否 1是
   */
  public static class IsDept {

    /**
     * 否
     */
    public static final Integer IsDeptNo = 0;
    /**
     * 是
     */
    public static final Integer IsDeptYes = 1;
  }

}
