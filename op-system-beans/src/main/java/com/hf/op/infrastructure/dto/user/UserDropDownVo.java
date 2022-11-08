package com.hf.op.infrastructure.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 用户下拉vo
 * @Date 2022/03/02
 */
@Data
public class UserDropDownVo implements Serializable {

  private static final long serialVersionUID = 4433974245761561035L;
  /**
   * 系统用户编号
   */
  private Long userId;

  /**
   * 用户名称
   */
  private String userName;
  private String password;

}
