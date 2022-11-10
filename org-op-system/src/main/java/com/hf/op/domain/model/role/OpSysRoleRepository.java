package com.hf.op.domain.model.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.function.QueryOpFunctionsTreeListDto;
import com.hf.op.infrastructure.dto.role.ListRoleDropDownListVo;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.dto.role.QueryOpRoleListDto;
import com.hf.op.infrastructure.dto.role.QueryRoleRelationUserListDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 曾仕斌
 * @function 运营系统角色表(op_sys_role)表
 * @date 2021/11/9
 */
@Repository
public interface OpSysRoleRepository extends BaseMapper<OpSysRoleEntity> {

  /**
   * 根据条件获取角色列表
   */
  IPage<QueryOpRoleListDto> pageListOpRole(Page<QueryOpRoleListDto> page,
      @Param("query") QueryOpRoleListDto queryOpPostListDto);
//
//  /**
//   * 通过角色编号获取角色权限列表
//   */
//  List<Long> getRoleFunctionsBySysRoleId(@Param("sysRoleId") Long sysRoleId);
//
//  /**
//   * 通过角色编号获取角色权限列表-小程序
//   */
//  List<Long> getRoleFunctionsBySysRoleIdMini(@Param("sysRoleId") Long sysRoleId);

  /**
   * 根据条件获取角色关联用户列表
   */
  IPage<QueryRoleRelationUserListDto> pageListOpRoleRelationUser(
      Page<QueryRoleRelationUserListDto> page,
      @Param("query") QueryRoleRelationUserListDto queryRoleRelationUserListDto);

  /**
   * 获取权限（树形）列表
   */
  List<QueryOpFunctionsTreeListDto> listTreeFunctions(@Param("systemId") Integer systemId,
      @Param("sysRoleId") Long sysRoleId);

  /**
   * 获取权限（树形）列表 小程序
   */
//  List<QueryOpFunctionsTreeListDto> listTreeFunctionsMini(@Param("sysRoleId") Long sysRoleId);

  /**
   * 获取权限角色（下拉）列表
   */
  List<ListRoleDropDownListVo> listDropDownRoles(@Param("systemId") Integer systemId);

  /**
   * 系统分类（下拉）列表
   */
  List<ListSystemVo> getOpSystemList();

}
