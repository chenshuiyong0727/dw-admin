package com.hf.op.service.impl;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HttpClientUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.role.mini.AdminAuthority;
import com.hf.op.domain.model.role.mini.AdminAuthorityRepository;
import com.hf.op.infrastructure.dto.mini.AdminAuthorityDto;
import com.hf.op.service.inf.AdminAuthorityService;
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
public class AdminAuthorityServiceImpl extends
    BatchCrudService<AdminAuthorityRepository, AdminAuthority> implements
    AdminAuthorityService {

  private AdminAuthorityRepository repository;

  public AdminAuthorityServiceImpl(AdminAuthorityRepository repository) {
    this.repository = repository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-06-15
   */
  @Override
  public ResponseMsg syncAuthority(AdminAuthorityDto dto)
      throws Exception {
    if (HttpClientUtil.SYNC_OP_TYPE_ADD.equals(dto.getSyncType())) {
      AdminAuthority entity = new AdminAuthority();
      BeanUtils.copyProperties(dto , entity);
      repository.insert(entity);
    } else if (HttpClientUtil.SYNC_OP_TYPE_UPDATE.equals(dto.getSyncType())) {
      AdminAuthority entity = new AdminAuthority();
      BeanUtils.copyProperties(dto , entity);
      repository.updateById(entity);
    } else {
      repository.deleteById(dto.getAuthorityid());
    }
    return new ResponseMsg();
  }

}
