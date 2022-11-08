package com.hf.common.infrastructure.util;

import com.hf.common.domain.UserInfo;
import com.hf.common.infrastructure.constant.CommonConstant;

/**
 * @author 曾仕斌
 * @function 用户工具，用于获取上下文中的用户信息
 * @date 2022/3/10
 */
public class UserUtil {

  private final static Long DEFAULT_USER_ID = Long.valueOf("1");

  private final static String DEFAULT_USER_NAME = "";

  /**
   * 设置用户编号
   */
  public static void setUserId(String userId) {
    if (StringUtilLocal.isNotEmpty(userId)) {
      ThreadLocalContext.get().put(CommonConstant.USER_ID, Long.valueOf(userId));
    } else {
      ThreadLocalContext.get().put(CommonConstant.USER_ID, DEFAULT_USER_ID);
    }
  }

  /**
   * 设置用户姓名
   */
  public static void setUserRealName(String userRealName) {
    if (StringUtilLocal.isNotEmpty(userRealName)) {
      ThreadLocalContext.get().put(CommonConstant.USER_REAL_NAME, userRealName);
    } else {
      ThreadLocalContext.get().put(CommonConstant.USER_REAL_NAME, DEFAULT_USER_NAME);
    }
  }

  /**
   * 获取用户编号
   */
  public static Long getUserId() {
    UserInfo userInfo = getUserInfo();
    if (null == userInfo) {
      return null;
    }
    return userInfo.getUserId();
  }

  /**
   * 获取用户真实姓名
   */
  public static String getUserRealName() {
    UserInfo userInfo = getUserInfo();
    if (null == userInfo) {
      return null;
    }
    return userInfo.getUserRealName();
  }

  /**
   * 获取用户信息
   */
  public static UserInfo getUserInfo() {
    Object userInfo = ThreadLocalContext.get().get(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER);
    if (null == userInfo) {
      return null;
    }
    return (UserInfo) userInfo;
  }

}
