package com.hf.op.service.inf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRoleEntity;

/**
 * @author wpq
 * @function 用户角色关系业务
 * @Date 2022/02/09
 */
public interface OpSysUserRoleService extends IService<OpSysUserRoleEntity> {

  /**
   * 用户绑定角色
   */
  void addUserRole(OpSysUserEntity opSysUserEntity);

}
