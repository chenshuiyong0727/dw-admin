package com.hf.op.domain.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseBusinessEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 运营系统用户角色(op_sys_user_role)实体类
 * @date 2021/11/9
 */
@Data
@TableName("op_sys_user_role")
public class OpSysUserRoleEntity extends BaseBusinessEntity implements Serializable {

  private static final long serialVersionUID = -8577429908471702154L;

  /**
   * 系统内部用户编号
   */
  private Long sysUserId;

  /**
   * 角色编号
   */
  private Long roleId;

}
