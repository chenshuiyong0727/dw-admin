package com.hf.op.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hf.op.domain.model.dict.BaseSysDictTypeEntity;
import com.hf.op.domain.model.dict.BaseSysDictTypeRepository;
import com.hf.op.service.inf.base.BaseSysDictTypeService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author wpq
 * @function 运营系统用户角色关系实现类
 * @Date 2022/02/09
 */
@Service
public class BaseSysDictTypeServiceImpl extends
    ServiceImpl<BaseSysDictTypeRepository, BaseSysDictTypeEntity> implements
    BaseSysDictTypeService {

  private BaseSysDictTypeRepository baseSysDictTypeRepository;

  public BaseSysDictTypeServiceImpl(BaseSysDictTypeRepository baseSysDictTypeRepository) {
    this.baseSysDictTypeRepository = baseSysDictTypeRepository;
  }


  @Override
  public Boolean addList(List<BaseSysDictTypeEntity> addList) throws Exception {
    return this.saveBatch(addList);
  }

}
