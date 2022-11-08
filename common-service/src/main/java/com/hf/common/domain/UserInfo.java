package com.hf.common.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by wpq on 2022/03/15.
 */
@Data
public class UserInfo implements Serializable {

  private static final long serialVersionUID = -4519792847071562410L;

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

}
