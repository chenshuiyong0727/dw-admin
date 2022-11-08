package com.hf.op.infrastructure.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

/**
 * @author chensy
 * @function 修改用户密码DTO
 * @date 2021/411
 */
@Data
@ToString
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthPwdDto {

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 用户本地编号，对应旧的统一账户平台用户编号
   */
  private String userLocalNo;

  /**
   * 旧密码
   */
  private String oldPwd;

  /**
   * 用户密码
   */
  private String userPassword;
  /**
   * 确认密码
   */
  private String confirmPassword;

}
