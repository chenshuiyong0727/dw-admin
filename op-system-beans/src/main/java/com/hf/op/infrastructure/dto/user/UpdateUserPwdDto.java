package com.hf.op.infrastructure.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 修改当前用户密码
 * @Date 2022/02/11
 */
@Data
public class UpdateUserPwdDto implements Serializable {

  private static final long serialVersionUID = 6138697838964492628L;

  /**
   * 账号编号
   */
  private Long id;

  /**
   * 原密码
   */
  private String oldPwd;

  /**
   * 新密码
   */
  private String newPwd;

  /**
   * 确认密码
   */
  private String confirmPwd;

}
