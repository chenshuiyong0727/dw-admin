package com.hf.op.infrastructure.dto.department;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 部门(op_department)的数据传输对象
 * @Date 2021/11/29
 */
@Data
public class SaveOrUpdateOpDeptDto implements Serializable {

  private static final long serialVersionUID = 3782681619016059061L;

  /**
   * 部门编号
   */
  private Long id;

  /**
   * 一级部门：0-不是，1-是
   */
  private Integer levelOne;

  /**
   * 上级编号
   */
  private String superiorDepartment;

  /**
   * 部门名称
   */
  private String name;

  /**
   * 部门说明
   */
  private String description;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

}
