package com.hf.op.infrastructure.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 基础登录
 * @Date 2021/12/09
 */
@Data
public class QueryVerityCodeLoginDto implements Serializable {

  private static final long serialVersionUID = -7063848381068153647L;

  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 登录账户类型:1-用户账号 2-手机 3-身份证
   */
  private Integer accountType;

  /**
   * 登录系统：10-运营系统 11-和家公众号 12-和家小程序 13-和家平板 14-和家安卓 15-和家IOS 16-和家WEB
   */
  private Integer loginSystem;

  /**
   * 校验码
   */
  private String verifyCode;

  /**
   * 秘钥
   */
  private String secretKey;

}
