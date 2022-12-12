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
import com.hf.op.domain.model.dict.GoodsInventoryEntity;
import com.hf.op.domain.model.dict.GoodsInventoryRepository;
import com.hf.op.domain.model.dict.GoodsOrderEntity;
import com.hf.op.domain.model.dict.GoodsOrderRepository;
import com.hf.op.infrastructure.dto.department.GoodsOrderCommonDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderCountDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderDataDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderLineVo;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.infrastructure.dto.department.GoodsShelvesGoodsRqDto;
import com.hf.op.infrastructure.vo.GoodsOrderPageVo;
import com.hf.op.service.inf.GoodsOrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 商品订单信息 服务接口实现
 *
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Slf4j
@Service
public class GoodsOrderServiceImpl extends
    BatchCrudService<GoodsOrderRepository, GoodsOrderEntity> implements
    GoodsOrderService {

  private GoodsOrderRepository repository;

  private GoodsInventoryRepository goodsInventoryRepository;

  public GoodsOrderServiceImpl(GoodsOrderRepository repository,
      GoodsInventoryRepository goodsInventoryRepository) {
    this.repository = repository;
    this.goodsInventoryRepository = goodsInventoryRepository;
  }

  @Override
  public ResponseMsg addList(GoodsShelvesGoodsRqDto dto) {
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
  public ResponseMsg page(GoodsOrderRqDto dto) {
    if (StringUtilLocal.isNotEmpty(dto.getStatusList())) {
      List<String> list = Arrays.asList(dto.getStatusList().split(","));
      dto.setStatusArray(list);
    }
    if (StringUtilLocal.isNotEmpty(dto.getKeyword())) {
      dto.setKeyword(dto.getKeyword().toUpperCase());
    }
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg sellGoods(GoodsOrderDto dto) {
    // 修改订单状态
    if (OrderStatusEnum.WAITDELIVER.getOderStatus().equals(dto.getStatus())) {
      dto.setSellTime(LocalDateTime.now());
    }
    if (OrderStatusEnum.SUCCESSFUL.getOderStatus().equals(dto.getStatus())) {
      dto.setSuccessTime(LocalDateTime.now());
      this.successOrder(dto);
    }
//    this.statusOrder(dto.getId(), dto.getStatus());
    return this.update(dto);
  }

  private void successOrder(GoodsOrderDto dto) {
    GoodsOrderEntity entity = repository.selectById(dto.getId());
    if (!OrderStatusEnum.SUCCESSFUL.getOderStatus().equals(entity.getStatus())) {
      GoodsInventoryEntity goodsInventoryEntity = goodsInventoryRepository
          .selectById(entity.getInventoryId());
      if (goodsInventoryEntity != null && goodsInventoryEntity.getInventory() > 0) {
        goodsInventoryEntity.setInventory(goodsInventoryEntity.getInventory() - 1);
        goodsInventoryRepository.updateById(goodsInventoryEntity);
      }
    }
  }

  private void statusOrder(Long id, Integer status) {
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
  public ResponseMsg add(GoodsOrderDto dto) {
    Long id = createId();
    GoodsOrderEntity entity = new GoodsOrderEntity();
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
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg update(GoodsOrderDto dto) {
    GoodsOrderEntity entity = new GoodsOrderEntity();
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
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg detail(Long id) {
    GoodsOrderEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsOrderDto dto = new GoodsOrderDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  //  下架	1
//  已上架	2
//  待发货	3
//  已发货	4
//  已揽件	5
//  已收货	6
//  成功	7
//  瑕疵	8
//  取消	9
//  发货后取消	10
  @Override
  public ResponseMsg indexData() {
    GoodsOrderDataDto dto = new GoodsOrderDataDto();
    Integer count2 = this.getCount(OrderStatusEnum.GALLERY.getOderStatus());
    Integer count3 = this.getCount(OrderStatusEnum.WAITDELIVER.getOderStatus());
    Integer count4 = this.getCount(OrderStatusEnum.SHIPPED.getOderStatus());
    Integer count5 = this.getCount(OrderStatusEnum.ALREADY_TOOK_A.getOderStatus());
    Integer count6 = this.getCount(OrderStatusEnum.RECEIVED.getOderStatus());
    Integer count8 = this.getCount(OrderStatusEnum.DEFECTS.getOderStatus());
    GoodsOrderCountDto countDto = new GoodsOrderCountDto();
    countDto.setCount2(count2);
    countDto.setCount3(count3);
    countDto.setCount4(count4);
    countDto.setCount5(count5);
    countDto.setCount6(count6);
    countDto.setCount8(count8);
    dto.setCountDto(countDto);
    GoodsOrderCommonDto commonDto = new GoodsOrderCommonDto();
//    Integer count7 = this.getCount(OrderStatusEnum.SUCCESSFUL.getOderStatus());
//    commonDto.setSuccessNum(count7);
    List<GoodsOrderPageVo> list1 = repository.indexData();
    for (GoodsOrderPageVo vo : list1) {
      switch (vo.getSize()) {
        case "goodsNum": {
          commonDto.setGoodsNum(vo.getPrice().intValue());
        }
        case "inventoryNum": {
          commonDto.setInventoryNum(vo.getPrice().intValue());
        }
        case "goodsPutInNum": {
          commonDto.setGoodsPutInNum(vo.getPrice().intValue());
        }
        case "inventoryCost": {
          commonDto.setInventoryCost(vo.getPrice());
        }
        case "marketValue": {
          commonDto.setMarketValue(vo.getPrice());
        }
        case "otherRevenue": {
          commonDto.setOtherRevenue(vo.getPrice());
        }
        case "inventoryAmount": {
          commonDto.setInventoryAmount(vo.getPrice());
        }
        default:
          break;
      }
    }
    this.converSuccessOrder(commonDto);
    dto.setCommonDto(commonDto);
    return new ResponseMsg().setData(dto);

  }

  @Override
  public ResponseMsg indexOrderData(GoodsOrderRqDto dto) {
    GoodsOrderLineVo lineVo = repository.indexOrderData1();
    if (lineVo == null) {
      return new ResponseMsg().setData(dto);
    }
    List<GoodsOrderCommonDto> list = repository.indexOrderData(dto);
    lineVo.setRows(list);
    return new ResponseMsg().setData(lineVo);
  }


  private void converSuccessOrder(GoodsOrderCommonDto commonDto) {
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOrderEntity::getStatus, OrderStatusEnum.SUCCESSFUL.getOderStatus());
    List<GoodsOrderEntity> list = repository.selectList(queryWrapper);
    if (CollectionUtils.isEmpty(list)) {
      return;
    }
    commonDto.setSuccessNum(list.size());
    // 订单总额
    BigDecimal orderAmount = BigDecimal.ZERO;
    // 利润总额
    BigDecimal profitsAmount = BigDecimal.ZERO;
    // 手续费
    BigDecimal poundage = BigDecimal.ZERO;
    // 运费
    BigDecimal freight = BigDecimal.ZERO;
    for (GoodsOrderEntity goodsOrderEntity : list) {
      orderAmount = orderAmount.add(goodsOrderEntity.getTheirPrice());
      profitsAmount = profitsAmount.add(goodsOrderEntity.getProfits());
      poundage = poundage.add(goodsOrderEntity.getPoundage());
      freight = freight.add(goodsOrderEntity.getFreight());
    }
    commonDto.setOrderAmount(orderAmount);
    commonDto.setPoundage(poundage);
    commonDto.setFreight(freight);
    commonDto.setProfitsAmount(profitsAmount);
  }

  private Integer getCount(Integer oderStatus) {
    LambdaQueryWrapper<GoodsOrderEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOrderEntity::getStatus, oderStatus);
    return repository.selectCount(queryWrapper);
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-15 17:39:00
   */
  @Override
  public ResponseMsg remove(Long id) {
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
  public ResponseMsg batchRemove(List<Long> ids) {
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
  public ResponseMsg status(StatusDto dto) {
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


  @Override
  public ResponseMsg sellList(GoodsOrderRqDto dto) {
    dto.setOrderBy("months desc");
    List<GoodsOrderCommonDto> list = repository.indexOrderData(dto);
    if (!CollectionUtils.isEmpty(list)) {
      List<GoodsOrderCommonDto> res = new ArrayList<>();
      GoodsOrderCommonDto orderCommonDto = new GoodsOrderCommonDto();
      orderCommonDto.setMonths("合计");
      Integer successNum = 0;
      BigDecimal orderAmount = BigDecimal.ZERO;
      BigDecimal profitsAmount = BigDecimal.ZERO;
      for (GoodsOrderCommonDto commonDto : list) {
        successNum = commonDto.getSuccessNum() + successNum;
        orderAmount = orderAmount.add(commonDto.getOrderAmount());
        profitsAmount = profitsAmount.add(commonDto.getProfitsAmount());
      }
      orderCommonDto.setSuccessNum(successNum);
      orderCommonDto.setOrderAmount(orderAmount);
      orderCommonDto.setProfitsAmount(profitsAmount);
      res.add(orderCommonDto);
      for (GoodsOrderCommonDto commonDto : list) {
        res.add(commonDto);
      }
      return new ResponseMsg().setData(res);
    }
    return new ResponseMsg().setData(list);
  }


  @Override
  public ResponseMsg putInStorage(GoodsOrderRqDto dto) {
    dto.setOrderBy("months desc");
    List<GoodsOrderCommonDto> list = repository.putInStorage(dto);
    if (!CollectionUtils.isEmpty(list)) {
      List<GoodsOrderCommonDto> res = new ArrayList<>();
      GoodsOrderCommonDto orderCommonDto = new GoodsOrderCommonDto();
      orderCommonDto.setMonths("合计");
      Integer successNum = 0;
      BigDecimal orderAmount = BigDecimal.ZERO;
      BigDecimal profitsAmount = BigDecimal.ZERO;
      for (GoodsOrderCommonDto commonDto : list) {
        successNum = commonDto.getSuccessNum() + successNum;
        orderAmount = orderAmount.add(commonDto.getOrderAmount());
        profitsAmount = profitsAmount.add(commonDto.getProfitsAmount());
      }
      orderCommonDto.setSuccessNum(successNum);
      orderCommonDto.setOrderAmount(orderAmount);
      orderCommonDto.setProfitsAmount(profitsAmount);
      res.add(orderCommonDto);
      for (GoodsOrderCommonDto commonDto : list) {
        res.add(commonDto);
      }
      return new ResponseMsg().setData(res);
    }
    return new ResponseMsg().setData(list);
  }

}
