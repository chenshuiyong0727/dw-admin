package com.hf.op.domain.model.role;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 运营系统角色权限关系(op_sys_role_function)实体类
 * @date 2021/11/9
 */
@Data
@TableName("op_sys_role_function")
public class OpSysRoleFunctionEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3005630022373528662L;

  /**
   * 权限编号
   */
  private Long functionId;

  /**
   * 角色编号
   */
  private Long roleId;

}
