package com.hf.op.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GoodsBaseSizeEntity;
import com.hf.op.domain.model.dict.GoodsBaseSizeRepository;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeExportDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeRqDto;
import com.hf.op.infrastructure.vo.GoodsBaseSizePageVo;
import com.hf.op.service.inf.GoodsBaseSizeService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 商品尺码关系 服务接口实现
 *
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Slf4j
@Service
public class GoodsBaseSizeServiceImpl extends
    BatchCrudService<GoodsBaseSizeRepository, GoodsBaseSizeEntity> implements
    GoodsBaseSizeService {

  private GoodsBaseSizeRepository repository;

  public GoodsBaseSizeServiceImpl(GoodsBaseSizeRepository repository) {
    this.repository = repository;
  }


  /**
   * @description 分页
   * @method page
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg page(GoodsBaseSizeRqDto dto) {
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg add(GoodsBaseSizeDto dto) {
    Long id = createId();
    GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
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
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg update(GoodsBaseSizeDto dto) {
    GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
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
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg detail(Long id) {
    GoodsBaseSizeEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsBaseSizeDto dto = new GoodsBaseSizeDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg remove(Long id) {
    LambdaQueryWrapper<GoodsBaseSizeEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsBaseSizeEntity::getId, id)
        .between(GoodsBaseSizeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
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
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg batchRemove(List<Long> ids) {
    LambdaQueryWrapper<GoodsBaseSizeEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GoodsBaseSizeEntity::getId, ids)
        .between(GoodsBaseSizeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
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
   * @date 2022-11-12 16:48:45
   */
  @Override
  public ResponseMsg status(StatusDto dto) {
    LambdaQueryWrapper<GoodsBaseSizeEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsBaseSizeEntity::getId, dto.getId())
        .between(GoodsBaseSizeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
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
   * @date: 2022-11-12 16:48:45
   */
  @Override
  public List<GoodsBaseSizeExportDto> queryExportPage(GoodsBaseSizeRqDto dto) {
    QueryWrapper<GoodsBaseSizeEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = repository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GoodsBaseSizeExportDto>();
    }
    IPage ipage = repository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GoodsBaseSizeExportDto>();
    }
    List<GoodsBaseSizePageVo> list = ipage.getRecords();
    List<GoodsBaseSizeExportDto> dtos = ListBeanUtil.listCopy(list, GoodsBaseSizeExportDto.class);
    return dtos;
  }


  @Override
  public void addList(Long goodsId, List<Long> sizeList) {
    List<GoodsBaseSizeEntity> list = new ArrayList<>();
    sizeList = sizeList.stream().distinct().collect(Collectors.toList());
    for (Long sizeId : sizeList) {
      if (sizeId == null) {
        continue;
      }
      GoodsBaseSizeEntity entity = new GoodsBaseSizeEntity();
      entity.setGoodsId(goodsId);
      entity.setSizeId(sizeId);
      entity.setId(createId());
      setCreateUser(entity);
      list.add(entity);
    }
    this.remove(Wrappers.<GoodsBaseSizeEntity>lambdaQuery()
        .eq(GoodsBaseSizeEntity::getGoodsId, goodsId));
    this.saveBatch(list);
  }

}
