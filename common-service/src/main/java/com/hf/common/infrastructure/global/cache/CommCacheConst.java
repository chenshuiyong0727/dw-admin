package com.hf.common.infrastructure.global.cache;

/**
 * @author wpq
 * @function
 * @Date 2021/12/09
 */
public class CommCacheConst extends BaseCacheConst {

  private static final String BASE_KEY = NAMESPACE + "comm:";

  public static final String BASE_KEY_ORG = "orgunit_1_0:";

  //=========================== 普通缓存 ==================================

  /**
   * 公共字典缓存
   */
  public static final String NOR_BASE_SYS_DICT_CACHE = BASE_KEY + "base_sys_dict";

  /**
   * 公共区域缓存
   */
  public static final String NOR_BASE_SYS_AREA_CACHE = BASE_KEY + "base_sys_area:level:";

  //=========================== 带过期时间缓存 ==================================

  /**
   * 验证码
   */
  public static final String EXP_CODE = BASE_KEY + "code:";
  /**
   * 短信验证码
   */
  public static final String EXP_SMS_PHONE_CODE = BASE_KEY + "smscode:";
  /**
   * 短信验证码操作间隔
   */
  public static final String EXP_SMS_PHONE_CODE_INTERVAL = BASE_KEY + "smscodeinterval:";

  //=========================== 队列 ==================================

  public static final String QUEUE_DELAY_TASK = BASE_KEY + "queue_delay_task";

  // 添加统一用户 过期时间
  public static final Integer REDIS_KEY_AUTH_USER_EXPIRE_TIME = 30;

  // 添加统一用户 rediskey
  public static final String REDIS_KEY_AUTH_USER_ADD = "AuthUser:add";


}
