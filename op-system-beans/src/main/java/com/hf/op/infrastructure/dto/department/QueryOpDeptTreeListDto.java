package com.hf.op.infrastructure.dto.department;

import com.hf.op.infrastructure.dto.user.UserDropDownVo;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wpq
 * @function 部门树形列表
 * @Date 2021/12/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOpDeptTreeListDto implements Serializable {

  private static final long serialVersionUID = -634394323045599673L;
  private Long id;
  private Long superiorDepartment;
  private String name;
  private Integer level;
  private List<QueryOpDeptTreeListDto> child;
  private List<UserDropDownVo> listParentDeptUsers;
  private Integer isDept; //0否 1是

}
