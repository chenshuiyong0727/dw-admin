package com.hf.op.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hf.op.domain.model.role.OpSysRoleEntity;
import com.hf.op.domain.model.role.OpSysRoleFunctionEntity;
import com.hf.op.domain.model.role.OpSysRoleFunctionRepository;
import com.hf.op.service.inf.OpSysRoleFunctionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author wpq
 * @function 运营系统角色权限关系实现类
 * @Date 2022/02/16
 */
@Service
public class OpSysRoleFunctionServiceImpl extends
    ServiceImpl<OpSysRoleFunctionRepository, OpSysRoleFunctionEntity> implements
    OpSysRoleFunctionService {

  private OpSysRoleFunctionRepository opSysRoleFunctionRepository;

  public OpSysRoleFunctionServiceImpl(
      OpSysRoleFunctionRepository opSysRoleFunctionRepository) {
    this.opSysRoleFunctionRepository = opSysRoleFunctionRepository;
  }

  @Override
  public void addRoleFunction(OpSysRoleEntity opSysRoleEntity) {
    List<OpSysRoleFunctionEntity> list = new ArrayList<>();
    for (Long functionId : opSysRoleEntity.getFunctions()) {
      OpSysRoleFunctionEntity sysRoleFunctionEntity = new OpSysRoleFunctionEntity();
      sysRoleFunctionEntity.setFunctionId(functionId);
      sysRoleFunctionEntity.setRoleId(opSysRoleEntity.getRoleId());
      sysRoleFunctionEntity
          .setDefaultInfo(opSysRoleEntity.getCreateUserId(), opSysRoleEntity.getCreateUserName());
      list.add(sysRoleFunctionEntity);
    }
    this.remove(Wrappers.<OpSysRoleFunctionEntity>lambdaQuery()
        .eq(OpSysRoleFunctionEntity::getRoleId, opSysRoleEntity.getRoleId()));
    this.saveBatch(list);
  }
}
