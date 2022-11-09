package com.hf.op.service.impl.code;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.code.GenTableColumnEntity;
import com.hf.op.domain.model.code.GenTableColumnRepository;
import com.hf.op.infrastructure.dto.code.GenTableColumnDto;
import com.hf.op.infrastructure.vo.code.GenTableColumnDetailVo;
import com.hf.op.service.inf.code.GenTableColumnService;
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
public class GenTableColumnServiceImpl extends
    BatchCrudService<GenTableColumnRepository, GenTableColumnEntity> implements
    GenTableColumnService {

  private GenTableColumnRepository repository;

  public GenTableColumnServiceImpl(GenTableColumnRepository repository) {
    this.repository = repository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg add(GenTableColumnDto dto) {
    Long id = createId();
    GenTableColumnEntity entity = new GenTableColumnEntity();
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
    return new ResponseMsg().setData(new HashMap().put("id", id));
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg update(GenTableColumnDto dto) {
    GenTableColumnEntity entity = new GenTableColumnEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      repository.updateById(entity);
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
  public ResponseMsg page(GenTableColumnDto dto) {
    IPage ipage = repository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenTableColumnEntity entity = repository.selectById(id);
    if (entity != null) {
      GenTableColumnDetailVo vo = new GenTableColumnDetailVo();
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
    LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTableColumnEntity::getId, id)
        .between(GenTableColumnEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTableColumnEntity entity = new GenTableColumnEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (repository.update(entity, queryWrapper) > 0) {
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
  public ResponseMsg status(GenTableColumnDto dto) {
    LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTableColumnEntity::getId, dto.getId())
        .between(GenTableColumnEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTableColumnEntity entity = new GenTableColumnEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (repository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }
}
