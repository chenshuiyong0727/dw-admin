package com.hf.op.service.impl.code;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GenTemplateEntity;
import com.hf.op.domain.model.dict.GenTemplateRepository;
import com.hf.op.infrastructure.dto.code.GenTemplateDto;
import com.hf.op.infrastructure.vo.code.GenTemplateDetailVo;
import com.hf.op.service.inf.code.GenTemplateService;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 服务接口实现
 *
 * @author system
 * @date 2022-08-23
 */
@Slf4j
@Service
public class GenTemplateServiceImpl extends
    BatchCrudService<GenTemplateRepository, GenTemplateEntity> implements
    GenTemplateService {

  private GenTemplateRepository genTemplateRepository;

  public GenTemplateServiceImpl(GenTemplateRepository genTemplateRepository) {
    this.genTemplateRepository = genTemplateRepository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg add(GenTemplateDto dto) {
    Long id = createId();
    GenTemplateEntity entity = new GenTemplateEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    try {
      genTemplateRepository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    return new ResponseMsg().setData(new HashMap().put("id", id));
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg update(GenTemplateDto dto) {
    GenTemplateEntity entity = new GenTemplateEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      genTemplateRepository.updateById(entity);
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
  public ResponseMsg page(GenTemplateDto dto) {
    IPage ipage = genTemplateRepository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenTemplateEntity entity = genTemplateRepository.selectById(id);
    if (entity != null) {
      GenTemplateDetailVo vo = new GenTemplateDetailVo();
      BeanUtils.copyProperties(entity, vo);
      return new ResponseMsg().setData(vo);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg remove(Long id) {
    LambdaQueryWrapper<GenTemplateEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTemplateEntity::getId, id)
        .between(GenTemplateEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTemplateEntity entity = new GenTemplateEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (genTemplateRepository.update(entity, queryWrapper) > 0) {
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
  public ResponseMsg status(GenTemplateDto dto) {
    LambdaQueryWrapper<GenTemplateEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTemplateEntity::getId, dto.getId())
        .between(GenTemplateEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTemplateEntity entity = new GenTemplateEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (genTemplateRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }
}
