package com.hf.op.service.inf.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hf.op.domain.model.dict.BaseSysDictTypeEntity;
import java.util.List;

public interface BaseSysDictTypeService extends IService<BaseSysDictTypeEntity> {

  /**
   * 用户绑定角色
   */
  Boolean addList(List<BaseSysDictTypeEntity> addList) throws Exception;
}
