package com.hf.op.infrastructure.dto.auth;

import lombok.Data;

/**
 * @author 曾仕斌
 * @function 基础登录DTO——用于远程调调
 * @date 2021/2/9
 */
@Data
public class AuthLoginComDto {

  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 登录密码
   */
  private String loginPassword;

  /**
   * 登录账户类型:1-用户名 2-手机
   */
  private Integer accountType = 1;

  /**
   * 登录终端：10-管理端；11-用户端
   */
  private Integer loginSystem = 10;

  /**
   * 校验码
   */
  private String verifyCode;

}
