package com.hf.op.service.impl.code;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.exception.BusinessException;
import com.hf.op.infrastructure.generator.strategy.create.CodeBuilder;
import com.hf.op.infrastructure.generator.utils.GenTemplateUtil;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.GeneratorMsg;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GenLogsEntity;
import com.hf.op.domain.model.dict.GenLogsRepository;
import com.hf.op.infrastructure.dto.code.GenLogsDto;
import com.hf.op.infrastructure.dto.code.gencode.GenBuilderModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTemplateDetailModel;
import com.hf.op.infrastructure.vo.code.GenLogsDetailVo;
import com.hf.op.service.inf.code.GenLogsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 服务接口实现
 *
 * @author system
 * @date 2022-08-23
 */
@Slf4j
@Service
public class GenLogsServiceImpl extends
    BatchCrudService<GenLogsRepository, GenLogsEntity> implements
    GenLogsService {

  private GenLogsRepository genLogsRepository;

  private GenTableServiceImpl genTableServiceImpl;

  private GenTemplateDetailServiceImpl genTemplateDetailServiceImpl;

  public GenLogsServiceImpl(GenLogsRepository genLogsRepository, GenTableServiceImpl genTableServiceImpl,
      GenTemplateDetailServiceImpl genTemplateDetailServiceImpl) {
    this.genLogsRepository = genLogsRepository;
    this.genTableServiceImpl = genTableServiceImpl;
    this.genTemplateDetailServiceImpl = genTemplateDetailServiceImpl;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public GenLogsEntity add(GenLogsDto dto) {
    Long id = createId();
    GenLogsEntity entity = new GenLogsEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    genLogsRepository.insert(entity);
    return entity;
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg update(GenLogsDto dto) {
    GenLogsEntity entity = new GenLogsEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      genLogsRepository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
  }

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg page(GenLogsDto dto) {
    IPage ipage = genLogsRepository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenLogsEntity entity = genLogsRepository.selectById(id);
    if (entity != null) {
      GenLogsDetailVo vo = new GenLogsDetailVo();
      BeanUtils.copyProperties(entity, vo);
      return new ResponseMsg().setData(vo);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg getByTableId(Long tableId) {
    GenLogsDetailVo vo = this.getVoByTableId(tableId);
    return new ResponseMsg().setData(vo);
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  private GenLogsDetailVo getVoByTableId(Long tableId) {
    LambdaQueryWrapper<GenLogsEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenLogsEntity::getTableId, tableId);
    queryWrapper.orderBy(true, false, GenLogsEntity::getCreateTime);
    List<GenLogsEntity> list = genLogsRepository.selectList(queryWrapper);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    GenLogsEntity entity = list.get(0);
    if (entity != null) {
      GenLogsDetailVo vo = new GenLogsDetailVo();
      BeanUtils.copyProperties(entity, vo);
      return vo;
    }
    return null;
  }


  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg remove(Long id) {
    LambdaQueryWrapper<GenLogsEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenLogsEntity::getId, id)
        .between(GenLogsEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenLogsEntity entity = new GenLogsEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (genLogsRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", id));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 变更状态
   * @method 2022-08-23
   */
  @Override
  public ResponseMsg status(GenLogsDto dto) {
    LambdaQueryWrapper<GenLogsEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenLogsEntity::getId, dto.getId())
        .between(GenLogsEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenLogsEntity entity = new GenLogsEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (genLogsRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(GenLogsDto dto, HttpServletResponse response) {
    if (dto == null) {
      // 生成失败，数据为空
      throw new BusinessException(GeneratorMsg.EXCEPTION_CREATE_NULL.getMessage());
    }
    dto.setSubModuleName(null);
    GenLogsEntity genLogsEntity = this.add(dto);
    if (genLogsEntity != null) {
      GenTableAndColumnModel currTableModel = genTableServiceImpl
          .getGenTableAndColumnModel(genLogsEntity.getTableId());
      if (dto == null) {
        // 生成失败，数据为空
        throw new BusinessException(GeneratorMsg.EXCEPTION_CREATE_TABLE_NULL.getMessage());
      }
      // 生成代码
      GenBuilderModel builderModel = new GenBuilderModel();
      BeanUtils.copyProperties(genLogsEntity, builderModel);
      builderModel.setModel(currTableModel);
      List<GenTemplateDetailModel> listByParent = genTemplateDetailServiceImpl
          .findListByParent(genLogsEntity.getTemplateId(), currTableModel.getDbName());
      Map<String, Object> templateMap = Maps.newHashMapWithExpectedSize(listByParent.size());
      for (GenTemplateDetailModel genTemplateDetailModel : listByParent) {
        templateMap.put(genTemplateDetailModel.getId().toString(), genTemplateDetailModel);
      }
      // 处理集合数据
      List<GenTemplateDetailModel> templateDetailList = GenTemplateUtil.handleDictList(templateMap);
      CodeBuilder.INSTANCE.build(builderModel, response, templateDetailList);

    }
  }
}
