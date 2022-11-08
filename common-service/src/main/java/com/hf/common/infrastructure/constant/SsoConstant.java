package com.hf.common.infrastructure.constant;

/**
 * @author chensy
 * @function sso接口常量
 * @date 2022/6/9
 */
public interface SsoConstant {


  /**
   * SSO sessionid
   */
  final static String SSO_SESSIONID = "xxlSsoSessionId";

  /*
    SSO权限 redis常量 - 系统id 列表
 */
  final static String REDIS_KEY_SSO_SYSTEM = "xxl_sso_systemList";

  /*
    SSO权限 redis常量 - 权限列表
 */
  final static String REDIS_KEY_SSO_OPFUN = "xxl_sso_opFunctions";

  /*
    SSO权限 redis常量 - 全部权限列表
 */
  final static String REDIS_KEY_SSO_ALL_OPFUN = "xxl_sso_all_opFunctions";


  /*
    SSO权限redis常量 - 小程序菜单列表
 */
  final static String REDIS_KEY_SSO_MINI_MENU = "xxl_sso_mini_menu";

  /*
    SSO权限redis常量 - 小程序权限列表
 */
  final static String REDIS_KEY_SSO_OPEN_AUTH = "xxl_sso_mini_open_auth";

  /**
   * 系统编号 运营平台
   */
  public static final Integer SYSTEM_ID_PLATE = 1;
  /**
   * 系统编号 组织
   */
  public static final Integer SYSTEM_ID_ORGUNIT = 2;
  /**
   * 系统编号 小程序
   */
  public static final Integer SYSTEM_ID_MINI = 3;

  /**
   *
   */
  public static final Integer SYSTEM_ID_MINI1 = 1440 * 60 * 3;
}
