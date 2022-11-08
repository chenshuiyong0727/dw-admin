package com.hf.op.service.inf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hf.op.domain.model.role.OpSysRoleEntity;
import com.hf.op.domain.model.role.OpSysRoleFunctionEntity;

/**
 * @author 曾仕斌
 * @function 角色权限关系业务
 * @date 2021/11/9
 */
public interface OpSysRoleFunctionService extends IService<OpSysRoleFunctionEntity> {

  /**
   * 角色绑定权限
   */
  void addRoleFunction(OpSysRoleEntity opSysRoleEntity);

}
