package com.auth.common.infrastructure.resp;

import java.io.Serializable;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾仕斌
 * @function 鉴权结果
 * @date 2021/3/31
 */
@Data
@ResponseBody
public class AuthResp implements Serializable {

  private static final long serialVersionUID = -8939345963871488314L;

  private String accessToken;

  /**
   * 统一用户编号
   */
  private Long userId;

  /**
   * SSO统一用户编号
   */
  private String ssoUserNo;

  private String refreshToken;

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
