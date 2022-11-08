package com.hf.op.service.impl;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HttpClientUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.role.mini.AdminMenu;
import com.hf.op.domain.model.role.mini.AdminMenuRepository;
import com.hf.op.infrastructure.dto.mini.AdminMenuDto;
import com.hf.op.service.inf.AdminMenuService;
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
public class AdminMenuServiceImpl extends
    BatchCrudService<AdminMenuRepository, AdminMenu> implements
    AdminMenuService {

  private AdminMenuRepository repository;

  public AdminMenuServiceImpl(AdminMenuRepository repository) {
    this.repository = repository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-06-15
   */
  @Override
  public ResponseMsg syncMenu(AdminMenuDto dto) throws Exception {
    if (HttpClientUtil.SYNC_OP_TYPE_ADD.equals(dto.getSyncType())) {
      AdminMenu entity = new AdminMenu();
      BeanUtils.copyProperties(dto , entity);
      repository.insert(entity);
    } else if (HttpClientUtil.SYNC_OP_TYPE_UPDATE.equals(dto.getSyncType())) {
      AdminMenu entity = new AdminMenu();
      BeanUtils.copyProperties(dto , entity);
      repository.updateById(entity);
    } else {
      repository.deleteById(dto.getMenuid());
    }
    return new ResponseMsg();
  }
}
