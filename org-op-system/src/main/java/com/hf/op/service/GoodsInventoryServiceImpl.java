package com.hf.op.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GoodsInventoryEntity;
import com.hf.op.domain.model.dict.GoodsInventoryRepository;
import com.hf.op.infrastructure.dto.department.GoodsInventoryDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryExportDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryRqDto;
import com.hf.op.infrastructure.dto.department.GoodsInventorySizeDto;
import com.hf.op.infrastructure.vo.GoodsInventoryPageVo;
import com.hf.op.service.inf.GoodsInventoryService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 商品库存 服务接口实现
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Slf4j
@Service
public class GoodsInventoryServiceImpl extends
	BatchCrudService<GoodsInventoryRepository, GoodsInventoryEntity> implements
    GoodsInventoryService {

  private GoodsInventoryRepository repository;

	public GoodsInventoryServiceImpl(GoodsInventoryRepository repository){
	    this.repository = repository;
	}


  /**
   * @description 分页
   * @method page
   * @date: 2022-11-12 20:10:34
   */
  @Override
  public ResponseMsg page(GoodsInventoryRqDto dto){
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()),dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-12 20:10:34
   */
  @Override
  public ResponseMsg add(GoodsInventoryDto dto){
//    Long id = createId();
//    GoodsInventoryEntity entity = new GoodsInventoryEntity();
//    BeanUtils.copyProperties(dto, entity);
//    entity.setId(id);
//    setCreateUser(entity);
//    try {
//      repository.insert(entity);
//    } catch (Exception e) {
//      log.error("新增失败 ",e);
//      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
//          "新增失败");
//    }
    List<GoodsInventorySizeDto>  list =  dto.getSizeDtos();
    List<GoodsInventoryEntity> entities = new ArrayList<>();
    for (GoodsInventorySizeDto inventorySizeDto : list) {
      Assert.notNull(inventorySizeDto.getSizeId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.notNull(inventorySizeDto.getGoodsId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Long id = createId();
      GoodsInventoryEntity entity = new GoodsInventoryEntity();
      BeanUtils.copyProperties(inventorySizeDto, entity);
      entity.setId(id);
      setCreateUser(entity);
      entities.add(entity);
    }
    this.saveBatch(entities);
    return new ResponseMsg();
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-12 20:10:34
   */
  @Override
  public ResponseMsg update(GoodsInventoryDto dto){
    GoodsInventoryEntity entity = new GoodsInventoryEntity();
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
   * @date: 2022-11-12 20:10:34
   */
  @Override
  public ResponseMsg detail(Long id){
    GoodsInventoryEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsInventoryDto dto = new GoodsInventoryDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

   /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-12 20:10:34
   */
   @Override
   public ResponseMsg remove(Long id){
   LambdaQueryWrapper<GoodsInventoryEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsInventoryEntity::getId, id)
        .between(GoodsInventoryEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsInventoryEntity entity = new GoodsInventoryEntity();
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
   * @date: 2022-11-12 20:10:34
   */
   @Override
   public ResponseMsg batchRemove(List<Long> ids){
   LambdaQueryWrapper<GoodsInventoryEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GoodsInventoryEntity::getId, ids)
        .between(GoodsInventoryEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsInventoryEntity entity = new GoodsInventoryEntity();
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
   * @date 2022-11-12 20:10:34
   */
   @Override
   public ResponseMsg status(StatusDto dto){
   LambdaQueryWrapper<GoodsInventoryEntity> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(GoodsInventoryEntity::getId, dto.getId())
          .between(GoodsInventoryEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
              DataStatusEnum.ENABLE.getCode());
      GoodsInventoryEntity entity = new GoodsInventoryEntity();
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
   * @date: 2022-11-12 20:10:34
   */
  @Override
  public List<GoodsInventoryExportDto> queryExportPage(GoodsInventoryRqDto dto) {
    QueryWrapper<GoodsInventoryEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = repository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GoodsInventoryExportDto>();
    }
    IPage ipage = repository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GoodsInventoryExportDto>();
    }
    List<GoodsInventoryPageVo> list = ipage.getRecords();
    List<GoodsInventoryExportDto> dtos = ListBeanUtil.listCopy(list, GoodsInventoryExportDto.class);
    return dtos;
  }
}
