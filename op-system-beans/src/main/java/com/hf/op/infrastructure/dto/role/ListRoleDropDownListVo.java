package com.hf.op.infrastructure.dto.role;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 权限角色下拉列表
 * @Date 2022/02/14
 */
@Data
public class ListRoleDropDownListVo implements Serializable {

  private static final long serialVersionUID = 8909621469014426625L;

  /**
   * 权限角色编号
   */
  private Long roleId;

  /**
   * 权限角色名称
   */
  private String roleName;

}
