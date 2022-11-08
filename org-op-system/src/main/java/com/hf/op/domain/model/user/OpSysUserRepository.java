package com.hf.op.domain.model.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.domain.model.function.OpSysFunctionEntity;
import com.hf.op.infrastructure.dto.role.ListSystemRoleVo;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.dto.user.QueryOpUserListDto;
import com.hf.op.infrastructure.dto.user.QueryUserAccountInfoDto;
import com.hf.op.infrastructure.dto.user.UserDropDownVo;
import com.hf.op.infrastructure.model.AuthorityMenu;
import com.hf.op.infrastructure.model.OpenAuthority;
import com.open.api.dto.FunctionDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 曾仕斌
 * @function 操作运营系统用户表(op_sys_user)表
 * @date 2021/11/9
 */
@Repository
public interface OpSysUserRepository extends BaseMapper<OpSysUserEntity> {

  /**
   * 通过系统内部用户编号获取用户权限列表
   */
  List<FunctionDto> getUserFunctionsBySysUserId(@Param("sysUserId") Long sysUserId);

  /**
   * 通过系统内部用户编号获取用户权限列表
   */
  List<AuthorityMenu> getUserFunctionsMiniBySysUserId(@Param("sysUserId") Long sysUserId);

  /**
   * 通过系统内部用户编号获取用户权限列表
   */
  List<OpenAuthority> selectAuthorityByRole(@Param("sysUserId") Long sysUserId);

  /**
   * 通过接口得到系统分类列表
   */
  List<ListSystemVo> getCurListSystemVo(@Param("sysUserId") Long sysUserId);

  /**
   * 通过系统内部用户编号获取用户权限实体列表
   */
  List<OpSysFunctionEntity> getUserFunctionEntitiesBySysUserId(@Param("sysUserId") Long sysUserId);

  /**
   * 根据条件获取用户列表
   */
  IPage<QueryOpUserListDto> pageListOpUser(Page<QueryOpUserListDto> page,
      @Param("query") QueryOpUserListDto queryOpUserListDto);

  /**
   * 通过系统用户编号获取角色列表
   */
  List<ListSystemRoleVo> getRoleIdsBySysUserId(@Param("sysUserId") Long sysUserId);

  /**
   * 通过系统内部用户编号获取用户账号信息
   */
  QueryUserAccountInfoDto getOpSysUserAccountInfoById(@Param("sysUserId") Long sysUserId);

  /**
   * 通过部门id获取用户列表
   */
  List<UserDropDownVo> listUsersByDeptId(@Param("deptId") Long deptId);

  /**
   * 获取所有权限
   **/
  List<FunctionDto> getAllUserFunctions();

}
