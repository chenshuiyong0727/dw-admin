package com.hf.op.service.impl.code;


import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GenTemplateDetailEntity;
import com.hf.op.domain.model.dict.GenTemplateDetailRepository;
import com.hf.op.infrastructure.dto.code.GenTemplateDetailDto;
import com.hf.op.infrastructure.dto.code.gencode.GenTemplateDetailModel;
import com.hf.op.infrastructure.generator.enums.DictType;
import com.hf.op.infrastructure.vo.code.GenTemplateDetailDetailVo;
import com.hf.op.service.inf.code.GenTemplateDetailService;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务接口实现
 *
 * @author system
 * @date 2022-08-23
 */
@Slf4j
@Service
public class GenTemplateDetailServiceImpl extends
    BatchCrudService<GenTemplateDetailRepository, GenTemplateDetailEntity> implements
    GenTemplateDetailService {

  private GenTemplateDetailRepository genTemplateDetailRepository;

  public GenTemplateDetailServiceImpl(GenTemplateDetailRepository genTemplateDetailRepository) {
    this.genTemplateDetailRepository = genTemplateDetailRepository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg add(GenTemplateDetailDto dto) {
    Long id = createId();
    GenTemplateDetailEntity entity = new GenTemplateDetailEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    try {
      genTemplateDetailRepository.insert(entity);
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
  public ResponseMsg update(GenTemplateDetailDto dto) {
    GenTemplateDetailEntity entity = new GenTemplateDetailEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      genTemplateDetailRepository.updateById(entity);
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
  public ResponseMsg page(GenTemplateDetailDto dto) {
    IPage ipage = genTemplateDetailRepository
        .page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenTemplateDetailEntity entity = genTemplateDetailRepository.selectById(id);
    if (entity != null) {
      GenTemplateDetailDetailVo vo = new GenTemplateDetailDetailVo();
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
    LambdaQueryWrapper<GenTemplateDetailEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTemplateDetailEntity::getId, id)
        .between(GenTemplateDetailEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTemplateDetailEntity entity = new GenTemplateDetailEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (genTemplateDetailRepository.update(entity, queryWrapper) > 0) {
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
  public ResponseMsg status(GenTemplateDetailDto dto) {
    LambdaQueryWrapper<GenTemplateDetailEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTemplateDetailEntity::getId, dto.getId())
        .between(GenTemplateDetailEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTemplateDetailEntity entity = new GenTemplateDetailEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (genTemplateDetailRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }

  /**
   * 根据 父类ID 全部删除
   *
   * @param parentId 父类ID
   * @return boolean
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public List<GenTemplateDetailModel> findListByParent(Long parentId, String dbName) {
    if (parentId == null) {
      return ListUtil.empty();
    }
    LambdaQueryWrapper<GenTemplateDetailEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTemplateDetailEntity::getParentId, parentId);
    if (DictType.DB_NAME.getValue().equals(dbName)) {
      queryWrapper.eq(GenTemplateDetailEntity::getDbName, dbName);
    }
    List<GenTemplateDetailEntity> list = genTemplateDetailRepository.selectList(queryWrapper);
    List<GenTemplateDetailModel> res = ListBeanUtil.listCopy(list, GenTemplateDetailModel.class);
    return res;
  }
}
