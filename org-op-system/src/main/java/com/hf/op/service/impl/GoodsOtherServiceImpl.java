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
import com.hf.op.domain.model.dict.GoodsOtherEntity;
import com.hf.op.domain.model.dict.GoodsOtherRepository;
import com.hf.op.infrastructure.dto.department.GoodsOtherDto;
import com.hf.op.infrastructure.dto.department.GoodsOtherExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOtherRqDto;
import com.hf.op.infrastructure.vo.GoodsOtherPageVo;
import com.hf.op.service.inf.GoodsOtherService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 其他收支 服务接口实现
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Slf4j
@Service
public class GoodsOtherServiceImpl extends
	BatchCrudService<GoodsOtherRepository, GoodsOtherEntity> implements
    GoodsOtherService {

  private GoodsOtherRepository repository;

	public GoodsOtherServiceImpl(GoodsOtherRepository repository){
	    this.repository = repository;
	}


  /**
   * @description 分页
   * @method page
   * @date: 2022-11-29 17:04:31
   */
  @Override
  public ResponseMsg page(GoodsOtherRqDto dto){
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()),dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-29 17:04:31
   */
  @Override
  public ResponseMsg add(GoodsOtherDto dto){
    Long id = createId();
    GoodsOtherEntity entity = new GoodsOtherEntity();
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
   * @date: 2022-11-29 17:04:31
   */
  @Override
  public ResponseMsg update(GoodsOtherDto dto){
    GoodsOtherEntity entity = new GoodsOtherEntity();
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
   * @date: 2022-11-29 17:04:31
   */
  @Override
  public ResponseMsg detail(Long id){
    GoodsOtherEntity entity = repository.selectById(id);
    if (entity != null) {
      GoodsOtherDto dto = new GoodsOtherDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

   /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-29 17:04:31
   */
   @Override
   public ResponseMsg remove(Long id){
   LambdaQueryWrapper<GoodsOtherEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GoodsOtherEntity::getId, id)
        .between(GoodsOtherEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsOtherEntity entity = new GoodsOtherEntity();
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
   * @date: 2022-11-29 17:04:31
   */
   @Override
   public ResponseMsg batchRemove(List<Long> ids){
   LambdaQueryWrapper<GoodsOtherEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GoodsOtherEntity::getId, ids)
        .between(GoodsOtherEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GoodsOtherEntity entity = new GoodsOtherEntity();
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
   * @date 2022-11-29 17:04:31
   */
   @Override
   public ResponseMsg status(StatusDto dto){
   LambdaQueryWrapper<GoodsOtherEntity> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(GoodsOtherEntity::getId, dto.getId())
          .between(GoodsOtherEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
              DataStatusEnum.ENABLE.getCode());
      GoodsOtherEntity entity = new GoodsOtherEntity();
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
   * @date: 2022-11-29 17:04:31
   */
  @Override
  public List<GoodsOtherExportDto> queryExportPage(GoodsOtherRqDto dto) {
    QueryWrapper<GoodsOtherEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = repository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GoodsOtherExportDto>();
    }
    IPage ipage = repository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GoodsOtherExportDto>();
    }
    List<GoodsOtherPageVo> list = ipage.getRecords();
    List<GoodsOtherExportDto> dtos = ListBeanUtil.listCopy(list, GoodsOtherExportDto.class);
    return dtos;
  }
}
