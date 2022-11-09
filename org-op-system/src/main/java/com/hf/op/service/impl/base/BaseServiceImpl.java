package com.hf.op.service.impl.base;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.global.config.ConfigConst;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.DictRespEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.domain.model.dict.BaseSysDictEntity;
import com.hf.op.domain.model.dict.BaseSysDictRepository;
import com.hf.op.domain.model.dict.BaseSysDictTypeEntity;
import com.hf.op.domain.model.dict.BaseSysDictTypeRepository;
import com.hf.op.infrastructure.dto.ListSysDictTypePageVo;
import com.hf.op.infrastructure.dto.ListSysDictTypeVo;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;
import com.hf.op.infrastructure.vo.SysDictRqVo;
import com.hf.op.service.inf.base.BaseService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author wpq
 * @function 基础服务
 * @Date 2021/12/10
 */
@Service
@RefreshScope
@Slf4j
public class BaseServiceImpl extends CrudService implements BaseService {

  private final Integer baseRandom = 9000;

  private final Integer minCode = 1000;

  @CreateCache(expire = 300, name = CommCacheConst.BASE_KEY_ORG
      + "codeCache", cacheType = CacheType.REMOTE)
  private Cache<String, String> codeCache;
  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_base_dict_", cacheType = CacheType.BOTH)
  private Cache<String, List<ListSysDictVo>> opBaseDictKeyCache;
  private OpDepartmentRepository opDepartmentRepository;

  private BaseSysDictRepository baseSysDictRepository;

  private BaseSysDictTypeRepository baseSysDictTypeRepository;

  public BaseServiceImpl(OpDepartmentRepository opDepartmentRepository,
      BaseSysDictRepository baseSysDictRepository,
      BaseSysDictTypeRepository baseSysDictTypeRepository) {
    this.baseSysDictRepository = baseSysDictRepository;
    this.baseSysDictTypeRepository = baseSysDictTypeRepository;
    this.opDepartmentRepository = opDepartmentRepository;
  }

  @Override
  public ResponseMsg getLoginVerityCode(QueryVerityCodeLoginDto queryVerityCodeLoginDto) {
      /*  if (ResponseMsg.isNotSuccess(verityCodeResult)) {
            return verityCodeResult;
        }
        Map<String, Object> result = (Map<String, Object>) verityCodeResult.getData();*/
    //   QueryVerityCodeLoginDto queryVerityCodeLoginDto = new QueryVerityCodeLoginDto();
    queryVerityCodeLoginDto.setSecretKey(ConfigConst.ServerSecretAesKey);
    String code = ((new Random()).nextInt(baseRandom) + minCode) + "";
    codeCache.put(CommCacheConst.EXP_CODE + queryVerityCodeLoginDto.getLoginAccount(), code);
    queryVerityCodeLoginDto.setVerifyCode(code);
    String verifyCode = queryVerityCodeLoginDto.getVerifyCode();
    String secretKey = queryVerityCodeLoginDto.getSecretKey();
    Map<String, Object> resp = new HashMap<>();
    resp.put("verifyCode", verifyCode);
    resp.put("secretKey", secretKey);
    return new ResponseMsg(resp);
  }

  @Override
  public ResponseMsg listSysDict() {
        /*ResponseMsg responseMsg = baseComServiceImpl.remoteListSysDict();
        if (ResponseMsg.isNotSuccess(responseMsg)) {
            return responseMsg;
        }
        List<ListSysDictVo> result = (List<ListSysDictVo>) responseMsg.getData();
        responseMsg = this.setData(responseMsg, result);

        return responseMsg;*/

    List<ListSysDictVo> listSysDictVos = opBaseDictKeyCache
        .get(CommCacheConst.NOR_BASE_SYS_DICT_CACHE);
    if (CollectionUtils.isEmpty(listSysDictVos)) {
      listSysDictVos = opDepartmentRepository.listSysDict();
    }
    return new ResponseMsg(listSysDictVos);
  }


  @Override
  public ResponseMsg listSysDictFront() {
    List<ListSysDictVo> listSysDictVos = baseSysDictRepository.listSysDict();
    return new ResponseMsg(listSysDictVos);
  }


  @Override
  public ResponseMsg listSysDictByTypeId(Long typeId) {
    List<ListSysDictVo> listSysDictVos = baseSysDictRepository.listSysDictByTypeId(typeId);
    return new ResponseMsg(listSysDictVos);
  }

  @Override
  public ResponseMsg listSysDictByTypeValue(String typeValue) {
    List<ListSysDictVo> listSysDictVos = baseSysDictRepository.listSysDictByTypeValue(typeValue);
    return new ResponseMsg(listSysDictVos);
  }

  private void refreshDist() {
    List<ListSysDictVo> listSysDict = baseSysDictRepository.listSysDict();
    opBaseDictKeyCache.put(CommCacheConst.NOR_BASE_SYS_DICT_CACHE, listSysDict);
  }


  @Override
  public ResponseMsg listSysDictType(ListSysDictTypeVo dto) {
    IPage ipage = baseSysDictTypeRepository
        .page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg dictTypeDetail(ListSysDictTypeVo dto) {
    BaseSysDictTypeEntity entity = baseSysDictTypeRepository.selectById(dto.getId());
    if (entity != null && !DataStatusEnum.DELETE.getCode().equals(entity.getDataStatus())) {
      ListSysDictTypePageVo vo = new ListSysDictTypePageVo();
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
  public ResponseMsg removeDictType(Long id) {
    LambdaQueryWrapper<BaseSysDictTypeEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(BaseSysDictTypeEntity::getId, id)
        .between(BaseSysDictTypeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    BaseSysDictTypeEntity entity = new BaseSysDictTypeEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (baseSysDictTypeRepository.update(entity, queryWrapper) > 0) {
      refreshDist();
      return new ResponseMsg().setData(new HashMap().put("id", id));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }


  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg addDictType(ListSysDictTypeVo dto) {

    Long id = createId();
    BaseSysDictTypeEntity entity = new BaseSysDictTypeEntity();
    BeanUtils.copyProperties(dto, entity);
    // 唯一验证
    boolean verificationByTypeValue = this.uniqueVerificationByTypeValue(entity);
    if (!verificationByTypeValue) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_TYPE_VALUE_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_TYPE_VALUE_REPEAT.getMsg());
    }
    // 唯一验证
    boolean verificationByTypeName = this.uniqueVerificationByTypeName(entity);
    if (!verificationByTypeName) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getMsg());
    }
    entity.setId(id);
    setCreateUser(entity);

    try {
      baseSysDictTypeRepository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    refreshDist();
    return new ResponseMsg().setData(id);
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg updateDictType(ListSysDictTypeVo dto) {
    BaseSysDictTypeEntity entity = new BaseSysDictTypeEntity();
    BeanUtils.copyProperties(dto, entity);
    boolean verificationByTypeValue = this.uniqueVerificationByTypeValue(entity);
    if (!verificationByTypeValue) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_TYPE_VALUE_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_TYPE_VALUE_REPEAT.getMsg());
    }
    // 唯一验证
    boolean verificationByTypeName = this.uniqueVerificationByTypeName(entity);
    if (!verificationByTypeName) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getMsg());
    }
    try {
      setUpdateUser(entity);
      baseSysDictTypeRepository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    refreshDist();
    return new ResponseMsg().setData(dto.getId());
  }


  private boolean uniqueVerificationByTypeValue(BaseSysDictTypeEntity entity) {
    if (entity == null || StringUtilLocal.isEmpty(entity.getTypeValue())) {
      return false;
    }
    LambdaQueryWrapper<BaseSysDictTypeEntity> wrapper = new LambdaQueryWrapper();
    // code 唯一
    wrapper.eq(BaseSysDictTypeEntity::getTypeValue, entity.getTypeValue());
    wrapper.between(BaseSysDictTypeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
        DataStatusEnum.ENABLE.getCode());
    // 重复校验排除自身
    if (entity.getId() != null) {
      wrapper.ne(BaseSysDictTypeEntity::getId, entity.getId());
    }
    BaseSysDictTypeEntity entity1 = baseSysDictTypeRepository.selectOne(wrapper);
    return entity1 == null;
  }

  private boolean uniqueVerificationByTypeName(BaseSysDictTypeEntity entity) {
    if (entity == null || StringUtilLocal.isEmpty(entity.getTypeValue())) {
      return false;
    }
    LambdaQueryWrapper<BaseSysDictTypeEntity> wrapper = new LambdaQueryWrapper();
    // code 唯一
    wrapper.eq(BaseSysDictTypeEntity::getTypeName, entity.getTypeName());
    wrapper.between(BaseSysDictTypeEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
        DataStatusEnum.ENABLE.getCode());
    // 重复校验排除自身
    if (entity.getId() != null) {
      wrapper.ne(BaseSysDictTypeEntity::getId, entity.getId());
    }
    return baseSysDictTypeRepository.selectCount(wrapper) == 0;
  }


  @Override
  public ResponseMsg dictPage(SysDictRqVo dto) {
    IPage ipage = baseSysDictTypeRepository
        .dictPage(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg dictDetail(SysDictRqVo dto) {
    BaseSysDictEntity entity = baseSysDictRepository.selectById(dto.getId());
    if (entity != null && !DataStatusEnum.DELETE.getCode().equals(entity.getDataStatus())) {
      ListSysDictVo vo = new ListSysDictVo();
      BeanUtils.copyProperties(entity, vo);
      vo.setId(entity.getId().toString());
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
  public ResponseMsg removeDictDetail(Long id) {
    LambdaQueryWrapper<BaseSysDictEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(BaseSysDictEntity::getId, id)
        .between(BaseSysDictEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    BaseSysDictEntity entity = new BaseSysDictEntity();
    entity.setDataStatus(DataStatusEnum.DELETE.getCode());
    setUpdateUser(entity);
    if (baseSysDictRepository.update(entity, queryWrapper) > 0) {
      refreshDist();
      return new ResponseMsg().setData(new HashMap().put("id", id));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }


  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg addDictDetail(SysDictRqVo dto) {

    Long id = createId();
    BaseSysDictEntity entity = new BaseSysDictEntity();
    BeanUtils.copyProperties(dto, entity);
    // 唯一验证
    boolean verificationByValue = this.uniqueVerificationByValue(entity);
    if (!verificationByValue) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_VALUE_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_VALUE_REPEAT.getMsg());
    }
    // 唯一验证
    boolean verificationByName = this.uniqueVerificationByName(entity);
    if (!verificationByName) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_TYPE_NAME_REPEAT.getMsg());
    }
    entity.setId(id);
    setCreateUser(entity);

    try {
      baseSysDictRepository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    refreshDist();
    return new ResponseMsg().setData(id);
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg updateDictDetail(SysDictRqVo dto) {
    BaseSysDictEntity entity = new BaseSysDictEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    // 唯一验证
    boolean verificationByValue = this.uniqueVerificationByValue(entity);
    if (!verificationByValue) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_VALUE_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_VALUE_REPEAT.getMsg());
    }
    // 唯一验证
    boolean verificationByName = this.uniqueVerificationByName(entity);
    if (!verificationByName) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(DictRespEnum.EXCEPTION_NAME_REPEAT.getCode(),
          DictRespEnum.EXCEPTION_NAME_REPEAT.getMsg());
    }
    try {
      baseSysDictRepository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    refreshDist();
    return new ResponseMsg().setData(dto.getId());
  }


  private boolean uniqueVerificationByValue(BaseSysDictEntity entity) {
    if (entity == null || StringUtilLocal.isEmpty(entity.getFieldValue())) {
      return false;
    }
    LambdaQueryWrapper<BaseSysDictEntity> wrapper = new LambdaQueryWrapper();
    // code 唯一
    wrapper.eq(BaseSysDictEntity::getFieldValue, entity.getFieldValue());
    wrapper.eq(BaseSysDictEntity::getTypeId, entity.getTypeId());
    wrapper.between(BaseSysDictEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
        DataStatusEnum.ENABLE.getCode());
    // 重复校验排除自身
    if (entity.getId() != null) {
      wrapper.ne(BaseSysDictEntity::getId, entity.getId());
    }
    return baseSysDictRepository.selectCount(wrapper) == 0;
  }

  private boolean uniqueVerificationByName(BaseSysDictEntity entity) {
    if (entity == null || StringUtilLocal.isEmpty(entity.getFieldName())) {
      return false;
    }
    LambdaQueryWrapper<BaseSysDictEntity> wrapper = new LambdaQueryWrapper();
    // code 唯一
    wrapper.eq(BaseSysDictEntity::getFieldName, entity.getFieldName());
    wrapper.eq(BaseSysDictEntity::getTypeId, entity.getTypeId());
    wrapper.between(BaseSysDictEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
        DataStatusEnum.ENABLE.getCode());
    // 重复校验排除自身
    if (entity.getId() != null) {
      wrapper.ne(BaseSysDictEntity::getId, entity.getId());
    }
    return baseSysDictRepository.selectCount(wrapper) == 0;
  }
}
