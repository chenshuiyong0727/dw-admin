package com.hf.op.infrastructure.dto.department;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 部门下拉列表
 * @Date 2022/02/14
 */
@Data
public class ListDeptDropDownListVo implements Serializable {

  private static final long serialVersionUID = 5065857665432429582L;

  /**
   * 部门编号
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

}
