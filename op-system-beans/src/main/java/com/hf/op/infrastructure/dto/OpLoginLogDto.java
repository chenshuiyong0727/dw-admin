package com.hf.op.infrastructure.dto;

import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 运营系统日志(op_login_log)实体类
 * @Date 2021/12/09
 */
@Data
public class OpLoginLogDto extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -7281363635676022897L;

  /**
   * 用户编号
   */
  private Long sysUserId;

  /**
   * 登录IP
   */
  private String ip;

}
