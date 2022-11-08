package com.hf.op.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRoleEntity;
import com.hf.op.domain.model.user.OpSysUserRoleRepository;
import com.hf.op.service.inf.OpSysUserRoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author wpq
 * @function 运营系统用户角色关系实现类
 * @Date 2022/02/09
 */
@Service
public class OpSysUserRoleServiceImpl extends
    ServiceImpl<OpSysUserRoleRepository, OpSysUserRoleEntity> implements OpSysUserRoleService {

  private OpSysUserRoleRepository opSysUserRoleRepository;

  public OpSysUserRoleServiceImpl(OpSysUserRoleRepository opSysUserRoleRepository) {
    this.opSysUserRoleRepository = opSysUserRoleRepository;
  }

  @Override
  public void addUserRole(OpSysUserEntity opSysUserEntity) {
    List<OpSysUserRoleEntity> list = new ArrayList<>();
    List<Long> roleIds = opSysUserEntity.getRoleIds();
    roleIds = roleIds.stream().distinct().collect(Collectors.toList());
    for (Long roleId : roleIds) {
      if (roleId == null) {
        continue;
      }
      OpSysUserRoleEntity sysUserRoleEntity = new OpSysUserRoleEntity();
      sysUserRoleEntity.setRoleId(roleId);
      sysUserRoleEntity.setSysUserId(opSysUserEntity.getUserId());
      sysUserRoleEntity
          .setDefaultInfo(opSysUserEntity.getSysUserId(), opSysUserEntity.getSysUserAccount());
      list.add(sysUserRoleEntity);
    }
    this.remove(Wrappers.<OpSysUserRoleEntity>lambdaQuery()
        .eq(OpSysUserRoleEntity::getSysUserId, opSysUserEntity.getUserId()));
    this.saveBatch(list);
  }

}
