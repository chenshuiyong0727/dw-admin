package com.hf.op.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.constant.OrderStatusEnum;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.OrderNoUtils;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GoodsOrderEntity;
import com.hf.op.domain.model.dict.GoodsOrderRepository;
import com.hf.op.infrastructure.dto.department.GoodsOrderDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.infrastructure.dto.department.GoodsShelvesGoodsRqDto;
import com.hf.op.infrastructure.vo.GoodsOrderPageVo;
import com.hf.op.service.inf.GoodsOrderService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 商品订单信息 服务接口实现
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Slf4j
@Service
public class GoodsOrderServiceImpl extends
    BatchCrudService<GoodsOrderRepository, GoodsOrderEntity> implements
    GoodsOrderService {

  private GoodsOrderRepository repository;

  public GoodsOrderServiceImpl(GoodsOrderRepository repository){
    this.repository = repository;
  }

  @Override
  public ResponseMsg addList(GoodsShelvesGoodsRqDto dto){
    List<GoodsOrderEntity> entities = new ArrayList<>();
    for (int i = 0; i < dto.getNum(); i++) {
      Assert.notNull(dto.getInventoryId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.notNull(dto.getShelvesPrice(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Long id = createId();
      GoodsOrderEntity entity = new GoodsOrderEntity();
      entity.setId(id);
      entity.setStatus(OrderStatusEnum.GALLERY.getOderStatus());
      entity.setOrderNo(OrderNoUtils.getFreeOrderId());
      entity.setInventoryId(dto.getInventoryId());
      entity.setShelvesPrice(dto.getShelvesPrice());
      setCreateUser(entity);
      entities.add(entity);
    }
    this.saveBatch(entities);
    return new ResponseMsg();
  }


  /**
   * @description 分页
   * @method page
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg page(GoodsOrderRqDto dto){
    if(StringUtilLocal.isNotEmpty(dto.getStatusList())){
      List<String> list = Arrays.asList(dto.getStatusList().split(","));
      dto.setStatusArray(list);
    }
    if (StringUtilLocal.isNotEmpty(dto.getKeyword())){
      dto.setKeyword(dto.getKeyword().toUpperCase());
    }
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()),dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg sellGoods(GoodsOrderDto dto){
    // 修改订单状态
    this.statusOrder(dto.getId(),dto.getStatus());
    if (OrderStatusEnum.WAITDELIVER.getOderStatus().equals(dto.getStatus())) {
      dto.setSellTime(LocalDateTime.now());
    }
    if (OrderStatusEnum.SUCCESSFUL.getOderStatus().equals(dto.getStatus())) {
      dto.setSuccessTime(LocalDateTime.now());
    }
    return this.update(dto);
  }

  private void statusOrder(Long id , Integer status){
    if (id == null || status == null) {
      return;
    }
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOrderEntity::getId, id);
    GoodsOrderEntity entity = new GoodsOrderEntity();
    entity.setStatus(status);
    setUpdateUser(entity);
    repository.update(entity, queryWrapper);
  }

  @Override
  public ResponseMsg add(GoodsOrderDto dto){
    Long id = createId();
    GoodsOrderEntity entity = new GoodsOrderEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    try {
      repository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ",e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    return new ResponseMsg().setData(id);
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg update(GoodsOrderDto dto){
    GoodsOrderEntity entity = new GoodsOrderEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      repository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ",e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    return new ResponseMsg().setData(dto.getId());
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg detail(Long id){
    GoodsOrderEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsOrderDto dto = new GoodsOrderDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg remove(Long id){
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOrderEntity::getId, id)
        .between(GoodsOrderEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsOrderEntity entity = new GoodsOrderEntity();
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
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg batchRemove(List<Long> ids){
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GoodsOrderEntity::getId, ids)
        .between(GoodsOrderEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsOrderEntity entity = new GoodsOrderEntity();
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
   * @date 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg status(StatusDto dto){
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOrderEntity::getId, dto.getId())
        .between(GoodsOrderEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsOrderEntity entity = new GoodsOrderEntity();
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
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public List<GoodsOrderExportDto> queryExportPage(GoodsOrderRqDto dto) {
    QueryWrapper<GoodsOrderEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = repository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GoodsOrderExportDto>();
    }
    IPage ipage = repository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GoodsOrderExportDto>();
    }
    List<GoodsOrderPageVo> list = ipage.getRecords();
    List<GoodsOrderExportDto> dtos = ListBeanUtil.listCopy(list, GoodsOrderExportDto.class);
    return dtos;
  }
}
