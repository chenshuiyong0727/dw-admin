package com.hf.op.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GoodsBaseEntity;
import com.hf.op.domain.model.dict.GoodsBaseRepository;
import com.hf.op.infrastructure.dto.department.GoodsBaseDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseExportDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseRqDto;
import com.hf.op.infrastructure.vo.GoodsBasePageVo;
import com.hf.op.service.inf.GoodsBaseService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 商品基本信息 服务接口实现
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Slf4j
@Service
public class GoodsBaseServiceImpl extends
    BatchCrudService<GoodsBaseRepository, GoodsBaseEntity> implements
    GoodsBaseService {

  private GoodsBaseRepository repository;

  public GoodsBaseServiceImpl(GoodsBaseRepository repository) {
    this.repository = repository;
  }


  /**
   * @description 分页
   * @method page
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg page(GoodsBaseRqDto dto) {
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg add(GoodsBaseDto dto) {
    Long id = createId();
    GoodsBaseEntity entity = new GoodsBaseEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    try {
      repository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    return new ResponseMsg().setData(id);
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg update(GoodsBaseDto dto) {
    GoodsBaseEntity entity = new GoodsBaseEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      repository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    return new ResponseMsg().setData(dto.getId());
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg detail(Long id) {
    GoodsBaseEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsBaseDto dto = new GoodsBaseDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg remove(Long id) {
    LambdaQueryWrapper<GoodsBaseEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsBaseEntity::getId, id)
        .between(GoodsBaseEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseEntity entity = new GoodsBaseEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (repository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(id);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg batchRemove(List<Long> ids) {
    LambdaQueryWrapper<GoodsBaseEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GoodsBaseEntity::getId, ids)
        .between(GoodsBaseEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseEntity entity = new GoodsBaseEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (repository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(ids);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 变更状态
   * @date 2022-11-08 11:10:33
   */
  @Override
  public ResponseMsg status(StatusDto dto) {
    LambdaQueryWrapper<GoodsBaseEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsBaseEntity::getId, dto.getId())
        .between(GoodsBaseEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseEntity entity = new GoodsBaseEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (repository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(dto.getId());
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }

  /**
   * @description 导出excel列表
   * @date: 2022-11-08 11:10:33
   */
  @Override
  public List<GoodsBaseExportDto> queryExportPage(GoodsBaseRqDto dto) {
    QueryWrapper<GoodsBaseEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = repository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GoodsBaseExportDto>();
    }
    IPage ipage = repository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GoodsBaseExportDto>();
    }
    List<GoodsBasePageVo> list = ipage.getRecords();
    List<GoodsBaseExportDto> dtos = ListBeanUtil.listCopy(list, GoodsBaseExportDto.class);
    return dtos;
  }
}
