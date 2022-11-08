package com.hf.op.domain.model.role;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import com.hf.op.infrastructure.dto.function.QueryOpFunctionsTreeListDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 运营系统角色(op_sys_role)实体类
 * @date 2021/11/9
 */
@Data
@TableName("op_sys_role")
public class OpSysRoleEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 7502373962127637514L;

  @TableField(exist = false)
  private OpSysRoleFunctionEntity opSysRoleFunctionEntity;

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 备注
   */
  private String description;

  /**
   * 备注
   */
  private Integer systemId;

  /**
   * 角色id
   */
  @TableField(exist = false)
  private Long roleId;

  /**
   * 权限id集合
   */
  @TableField(exist = false)
  private List<Long> functions;

  /**
   * 权限id集合
   */
  @TableField(exist = false)
  private List<QueryOpFunctionsTreeListDto> opFunTreeListDtos;

}
