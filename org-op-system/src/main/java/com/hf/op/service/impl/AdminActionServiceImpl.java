package com.hf.op.service.impl;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HttpClientUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.role.mini.AdminAction;
import com.hf.op.domain.model.role.mini.AdminActionRepository;
import com.hf.op.infrastructure.dto.mini.AdminActionDto;
import com.hf.op.service.inf.AdminActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 服务接口实现
 *
 * @author system
 * @date 2022-06-15
 */
@Slf4j
@Service
public class AdminActionServiceImpl extends
    BatchCrudService<AdminActionRepository, AdminAction> implements
    AdminActionService {

  private AdminActionRepository repository;

  public AdminActionServiceImpl(AdminActionRepository repository) {
    this.repository = repository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-06-15
   */
  @Override
  public ResponseMsg syncAction(AdminActionDto dto) throws Exception {
    if (HttpClientUtil.SYNC_OP_TYPE_ADD.equals(dto.getSyncType())) {
      AdminAction entity = new AdminAction();
      BeanUtils.copyProperties(dto , entity);
      repository.insert(entity);
    } else if (HttpClientUtil.SYNC_OP_TYPE_UPDATE.equals(dto.getSyncType())) {
      AdminAction entity = new AdminAction();
      BeanUtils.copyProperties(dto , entity);
      repository.updateById(entity);
    } else {
      repository.deleteById(dto.getActionid());
    }
    return new ResponseMsg();
  }

}
