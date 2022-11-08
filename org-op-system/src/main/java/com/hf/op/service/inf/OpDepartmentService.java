package com.hf.op.service.inf;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.department.SaveOrUpdateOpDeptDto;

/**
 * @author wpq
 * @function 部门关系业务
 * @Date 2021/11/29
 */
public interface OpDepartmentService {

  /**
   * 新增部门
   */
  ResponseMsg<String> saveDepartments(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto);

  /**
   * 通过部门编号获取部门信息
   */
  ResponseMsg getDepartmentsById(Long id);

  /**
   * 编辑部门
   */
  ResponseMsg<String> updateDepartments(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto);

  /**
   * 检查部门名称是否存在
   */
  Boolean isExist(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto);

  /**
   * 根据条件获取部门列表
   */
  ResponseMsg pageListDepartments(QueryOpDeptListDto queryOpDeptListDto);

  /**
   * 逻辑删除部门
   */
  ResponseMsg<String> updateDeptStatus(SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto);

  /**
   * 部门（树形）列表
   */
  ResponseMsg listTreeDepartments();

  /**
   * 部门（等级）列表
   */
  ResponseMsg listLevelDepartments();

  /**
   * 部门（下拉）列表
   */
  ResponseMsg listDropDownDepartments();

}
