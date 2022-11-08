package com.hf.op.service.inf;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.domain.model.function.OpSysFunctionEntity;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.dto.user.QueryOpUserListDto;
import com.hf.op.infrastructure.dto.user.UpdateUserPwdDto;
import com.hf.op.infrastructure.model.AuthorityMenu;
import com.hf.op.infrastructure.model.OpenAuthority;
import com.open.api.dto.FunctionDto;
import java.util.List;

/**
 * @author 曾仕斌
 * @function 用户业务接口
 * @date 2021/11/9
 */
public interface OpSysUserService {

  /**
   * 通过接口得到系统分类列表
   */
  List<ListSystemVo> getCurListSystemVo(Long userId);

  /**
   * 通过系统用户编号获取用户权限列表
   */
  List<FunctionDto> getUserFunctionsBySysUserId(Long userId);

  /**
   * 通过系统用户编号获取用户权限列表
   */
  List<AuthorityMenu> getUserFunctionsMiniBySysUserId(Long userId);

  /**
   * 通过系统用户编号获取用户权限列表
   */
  List<OpenAuthority> selectAuthorityByRole(Long userId);

  /**
   * 获取所有用户权限列表
   */
  List<FunctionDto> getAllUserFunctions();

  /**
   * 获取所有用户权限列表
   */
  void setAllUserFunctions();

  /**
   * 通过系统用户编号获取用户权限列表
   */
  List<OpSysFunctionEntity> getUserFunctionEntitiesBySysUserId(Long userId);

  /**
   * 通过统一用户编号获取用户信息
   */
  OpSysUserEntity getUserByUserId(Long userId);

  /**
   * 通过系统用户编号获取用户信息
   */
  OpSysUserEntity getUserById(Long id);

  /**
   * 新增用户
   */
  ResponseMsg<OpSysUserEntity> saveOpSysUser(OpSysUserEntity opSysUserEntity);

  /**
   * 编辑用户
   */
  ResponseMsg<String> updateOpSysUser(OpSysUserEntity opSysUserEntity);

  /**
   * 更新用户状态
   */
  ResponseMsg<String> updateUserStatus(OpSysUserEntity opSysUserEntity);

  /**
   * 根据条件获取用户列表
   */
  ResponseMsg pageListOpUser(QueryOpUserListDto queryOpUserListDto);

  /**
   * 通过用户编号获取用户信息
   */
  ResponseMsg getOpSysUserById(Long id);

  /**
   * 用户重置密码
   */
  ResponseMsg resetUserPwd(Long id);

  /**
   * 通过用户编号获取个人用户账号信息
   */
  ResponseMsg getOpSysUserAccountInfoById(Long id);

  /**
   * 用户修改密码
   */
  ResponseMsg updateUserPwd(UpdateUserPwdDto updateUserPwdDto);

  /**
   * 用户退出
   */
  ResponseMsg logout(String tokenAuth);

  /**
   * 用户（下拉）列表
   */
  ResponseMsg listDropDownUsers();

  /**
   * @description 查询登录日志
   * @method queryOpSysUserLoginLogById
   * @date: 2022/4/22 14:50
   * @author:liangcanlong
   */
  ResponseMsg queryOpSysUserLoginLogById(Long id, Integer pageNum, Integer pageSize);

  ResponseMsg findUser(String username, String password);
}
