package com.hf.op.service.impl.code;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GenDemoEntity;
import com.hf.op.domain.model.dict.GenDemoRepository;
import com.hf.op.infrastructure.dto.code.GenDemoDto;
import com.hf.op.infrastructure.dto.code.GenDemoExportDto;
import com.hf.op.infrastructure.dto.code.GenDemoRqDto;
import com.hf.op.infrastructure.vo.code.GenDemoPageVo;
import com.hf.op.service.inf.code.GenDemoService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 案例 服务接口实现
 *
 * @author chensy
 * @date 2022-09-08 15:57:34
 */
@Slf4j
@Service
public class GenDemoServiceImpl extends
    BatchCrudService<GenDemoRepository, GenDemoEntity> implements
    GenDemoService {

  private GenDemoRepository genDemoRepository;

  public GenDemoServiceImpl(GenDemoRepository genDemoRepository) {
    this.genDemoRepository = genDemoRepository;
  }


  /**
   * @description 分页
   * @method page
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg page(GenDemoRqDto dto) {
    IPage ipage = genDemoRepository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg add(GenDemoDto dto) {
    Long id = createId();
    GenDemoEntity entity = new GenDemoEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    try {
      genDemoRepository.insert(entity);
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
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg update(GenDemoDto dto) {
    GenDemoEntity entity = new GenDemoEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      genDemoRepository.updateById(entity);
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
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenDemoEntity entity = genDemoRepository.selectById(id);
    if (entity != null) {
      GenDemoDto dto = new GenDemoDto();
      BeanUtils.copyProperties(entity, dto);
      return new ResponseMsg().setData(dto);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg remove(Long id) {
    LambdaQueryWrapper<GenDemoEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenDemoEntity::getId, id)
        .between(GenDemoEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenDemoEntity entity = new GenDemoEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (genDemoRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(id);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg batchRemove(List<Long> ids) {
    LambdaQueryWrapper<GenDemoEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(GenDemoEntity::getId, ids)
        .between(GenDemoEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenDemoEntity entity = new GenDemoEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (genDemoRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(ids);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 变更状态
   * @date 2022-09-08 15:57:34
   */
  @Override
  public ResponseMsg status(GenDemoDto dto) {
    LambdaQueryWrapper<GenDemoEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenDemoEntity::getId, dto.getId())
        .between(GenDemoEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenDemoEntity entity = new GenDemoEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (genDemoRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(dto.getId());
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }

  /**
   * @description 导出excel列表
   * @date: 2022-09-08 15:57:34
   */
  @Override
  public List<GenDemoExportDto> queryExportPage(GenDemoRqDto dto) {
    QueryWrapper<GenDemoEntity> entityQueryWrapper = new QueryWrapper();
    int countByAccount = genDemoRepository.selectCount(entityQueryWrapper);
    if (countByAccount == 0) {
      return new ArrayList<GenDemoExportDto>();
    }
    IPage ipage = genDemoRepository.page(new Page(1L, countByAccount), dto);
    if (ipage == null || ipage.getTotal() == 0L) {
      return new ArrayList<GenDemoExportDto>();
    }
    List<GenDemoPageVo> list = ipage.getRecords();
    List<GenDemoExportDto> dtos = ListBeanUtil.listCopy(list, GenDemoExportDto.class);
    return dtos;
  }
}
