package com.auth.common.domain;

import com.hf.common.domain.ValueObject;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.Data;

/**
 * 登录信息
 */
@Data
public class LoginInfoValueObject implements ValueObject<LoginInfoValueObject> {

  private String refreshTokenId;

  /**
   * 用户账号
   */
  private String userAccount;

  /**
   * 登录IP地址
   */
  private String ipaddr;

  /**
   * 登录地址
   */
  private String loginLocation;

  /**
   * 浏览器类型
   */
  private String browser;

  /**
   * 操作系统
   */
  private String os;

  /**
   * 登录时间
   */
  private Long loginTime;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 用户邮箱
   */
  private String userEmail;

  /**
   * 用户手机
   */
  private String userMobile;

  /**
   * 用户身份证
   */
  private String idCardNo;

  /**
   * 用户真实姓名
   */
  private String userRealName;

  @Override
  public boolean sameValueAs(LoginInfoValueObject other) {
    if (null == other || StringUtilLocal.isEmpty(refreshTokenId) || StringUtilLocal
        .isEmpty(other.getRefreshTokenId())) {
      return false;
    }
    return refreshTokenId.equals(other.getRefreshTokenId());
  }
}
