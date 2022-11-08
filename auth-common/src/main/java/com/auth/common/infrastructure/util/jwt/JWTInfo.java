package com.auth.common.infrastructure.util.jwt;

import com.auth.common.infrastructure.util.UUIDUtils;
import java.io.Serializable;

/**
 * JWT信息类
 *
 * @author 曾仕斌
 * @date 2021-01-12
 */
public class JWTInfo implements Serializable, IJWTInfo {

  private static final long serialVersionUID = 3146723455760073643L;


  /**
   * 登录编号
   */
  private Long userId;

  /**
   * TOKEN编号
   */
  private String tokenId;

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
   * 用户账号
   */
  private String userAccount;

  /**
   * 用户真实姓名
   */
  private String userRealName;

  /**
   * 过期时间
   */
  private String exp;

  public JWTInfo(Long userId, String userEmail, String userMobile, String idCardNo,
      String userAccount, String userRealName, String exp) {
    this.userId = userId;
    this.userEmail = userEmail;
    this.userMobile = userMobile;
    this.idCardNo = idCardNo;
    this.userAccount = userAccount;
    this.userRealName = userRealName;
    this.exp = exp;
    this.tokenId = UUIDUtils.generateShortUuid();
  }

  public JWTInfo(Long userId, String userEmail, String userMobile, String idCardNo,
      String userAccount, String userRealName, String exp, String tokenId) {
    this.userId = userId;
    this.userEmail = userEmail;
    this.userMobile = userMobile;
    this.idCardNo = idCardNo;
    this.userAccount = userAccount;
    this.userRealName = userRealName;
    this.exp = exp;
    this.tokenId = tokenId;
  }


  @Override
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String getUserEmail() {
    return userEmail;
  }

  @Override
  public String getUserMobile() {
    return userMobile;
  }

  @Override
  public String getIdCardNo() {
    return idCardNo;
  }

  @Override
  public String getUserAccount() {
    return userAccount;
  }


  @Override
  public String getTokenId() {
    return tokenId;
  }


  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public void setUserMobile(String userMobile) {
    this.userMobile = userMobile;
  }

  public void setIdCardNo(String idCardNo) {
    this.idCardNo = idCardNo;
  }

  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }

  @Override
  public String getUserRealName() {
    return userRealName;
  }

  public void setUserRealName(String userRealName) {
    this.userRealName = userRealName;
  }

  @Override
  public String getExp() {
    return exp;
  }

  public void setExp(String exp) {
    this.exp = exp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JWTInfo jwtInfo = (JWTInfo) o;
    if (userId != null) {
      return userId.equals(jwtInfo.userId);
    } else {
      return jwtInfo.userId == null;
    }
  }

  @Override
  public int hashCode() {
    int result = userMobile != null ? userMobile.hashCode() : 0;
    if (result == 0) {
      result = userAccount != null ? userAccount.hashCode() : 0;
    }
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    return result;
  }
}
