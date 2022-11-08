package com.hf.op.service.inf;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.domain.model.role.OpSysRoleEntity;
import com.hf.op.infrastructure.dto.function.OpFunctionsDto;
import com.hf.op.infrastructure.dto.function.QueryOpFunctionsTreeListDto;
import com.hf.op.infrastructure.dto.role.QueryOpRoleListDto;
import com.hf.op.infrastructure.dto.role.QueryRoleRelationUserListDto;
import java.util.List;

/**
 * @author 曾仕斌
 * @function 角色业务接口
 * @date 2021/11/9
 */
public interface OpSysRoleService {

  /**
   * 新增角色
   */
  ResponseMsg<String> saveOpSysRole(OpSysRoleEntity opSysRoleEntity);

  /**
   * 检查角色名称是否存在
   */
  Boolean isExist(OpSysRoleEntity opSysRoleEntity);

  /**
   * 编辑角色
   */
  ResponseMsg<String> updateOpSysRole(OpSysRoleEntity opSysRoleEntity);

  /**
   * 逻辑删除角色
   */
  ResponseMsg<String> updateRoleStatus(OpSysRoleEntity opSysRoleEntity);

  /**
   * 根据条件获取角色列表
   */
  ResponseMsg pageListOpRole(QueryOpRoleListDto queryOpRoleListDto);

  /**
   * 通过角色编号获取角色信息
   */
  ResponseMsg getOpSysRoleById(Long id);

  /**
   * 通过角色编号获取角色信息
   */
  ResponseMsg getMenuDetail(Long id);

  /**
   * 根据条件获取角色关联用户列表
   */
  ResponseMsg pageListOpRoleRelationUser(QueryRoleRelationUserListDto queryRoleRelationUserListDto);

  /**
   * 权限（树形）列表
   */
  ResponseMsg listTreeFunctions(Integer systemId, Long roleId);

  /**
   * 当前用户最新权限列表
   */
  ResponseMsg listCurrentUserLatestPermissions(Long id);

  /**
   * 权限角色（下拉）列表
   */
  ResponseMsg listDropDownRoles(QueryOpRoleListDto dto);

  /**
   * 获取系统列表
   */
  ResponseMsg getOpSystemList();

  /**
   * 获取系统列表
   */
  ResponseMsg findMenuTreePageByLazy(Integer systemId, Long parentId);

  /**
   * 获取系统列表
   */
  ResponseMsg goCopy(Integer systemId, Long id);

  /**
   * @description 新增
   * @method add
   * @date: 2022-09-28 14:59:14
   */
  ResponseMsg add(QueryOpFunctionsTreeListDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-09-28 14:59:14
   */
  ResponseMsg update(QueryOpFunctionsTreeListDto dto);


  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-09-28 14:59:14
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-09-28 14:59:14
   */
  ResponseMsg batchRemove(List<Long> ids);

  ResponseMsg updateKeyList(OpFunctionsDto dto);
}
