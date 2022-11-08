package com.auth.common.infrastructure.util.jwt;

/**
 * Token基础接口
 *
 * @author 曾仕斌
 * @date 2021-01-12
 */
public interface IJWTInfo {


  /**
   * 获取用户编号
   */
  Long getUserId();

  /**
   * 获取用户邮箱
   */
  String getUserEmail();

  /**
   * 获取用户手机号
   */
  String getUserMobile();

  /**
   * 获取用户身份证号
   */
  String getIdCardNo();

  /**
   * 获取账户名
   */
  String getUserAccount();

  /**
   * 获取用户名
   */
  String getUserRealName();

  /**
   * 获取过期时间
   */
  String getExp();

  /**
   * tokenId
   */
  String getTokenId();
}
