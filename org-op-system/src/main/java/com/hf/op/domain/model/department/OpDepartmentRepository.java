package com.hf.op.domain.model.department;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.infrastructure.dto.department.ListDeptDropDownListVo;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.department.QueryOpDeptTreeListDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @function 运营系统部门表(op_department)
 * @Date 2021/11/29
 */
@Repository
public interface OpDepartmentRepository extends BaseMapper<OpDepartmentEntity> {

  List<ListSysDictVo> listSysDict();

  /**
   * 根据条件获取部门列表
   */
  IPage<QueryOpDeptListDto> pageListDepartments(Page<QueryOpDeptListDto> page,
      @Param("query") QueryOpDeptListDto queryOpDeptListDto);

  /**
   * 获取部门（树形）列表
   */
  List<QueryOpDeptTreeListDto> listTreeDepartments();

  /**
   * 获取部门（下拉）列表
   */
  List<ListDeptDropDownListVo> listDropDownDepartments();

}
