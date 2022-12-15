package com.hf.op.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.function.OpSysFunctionEntity;
import com.hf.op.domain.model.function.OpSysFunctionRepository;
import com.hf.op.domain.model.role.OpSysRoleEntity;
import com.hf.op.domain.model.role.OpSysRoleFunctionEntity;
import com.hf.op.domain.model.role.OpSysRoleFunctionRepository;
import com.hf.op.domain.model.role.OpSysRoleRepository;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRepository;
import com.hf.op.infrastructure.dto.function.OpFunctionsDto;
import com.hf.op.infrastructure.dto.function.QueryOpFunctionsTreeListDto;
import com.hf.op.infrastructure.dto.role.ListRoleDropDownListVo;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.dto.role.QueryOpRoleListDto;
import com.hf.op.infrastructure.dto.role.QueryRoleRelationUserListDto;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.hf.op.service.inf.OpSysRoleFunctionService;
import com.hf.op.service.inf.OpSysRoleService;
import com.open.api.dto.FunctionDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author wpq
 * @function 角色业务类
 * @Date 2022/02/16
 */
@Service
@Slf4j
public class OpSysRoleServiceImpl extends CrudService implements OpSysRoleService {

  /**
   * 排序字段
   */
  private static final String SORT_FIELD = "order";

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_sys_role_", cacheType = CacheType.BOTH)
  private Cache<Long, OpSysRoleEntity> opSysRoleKeyCache;

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_sys_user_", cacheType = CacheType.BOTH)
  private Cache<Long, OpSysUserEntity> opSysUserKeyCache;


  private OpSysRoleRepository opSysRoleRepository;

  private OpSysRoleFunctionRepository opSysRoleFunctionRepository;

  private OpSysFunctionRepository opSysFunctionRepository;


  private OpSysRoleFunctionService opSysRoleFunctionServiceImpl;

  private OpSysUserRepository opSysUserRepository;

  private OpSysUserServiceImpl opSysUserServiceImpl;

  public OpSysRoleServiceImpl(OpSysRoleRepository opSysRoleRepository,
      OpSysRoleFunctionRepository opSysRoleFunctionRepository,
      OpSysRoleFunctionService opSysRoleFunctionServiceImpl,
      OpSysFunctionRepository opSysFunctionRepository,
      OpSysUserServiceImpl opSysUserServiceImpl,
      OpSysUserRepository opSysUserRepository) {
    this.opSysRoleFunctionRepository = opSysRoleFunctionRepository;
    this.opSysFunctionRepository = opSysFunctionRepository;
    this.opSysUserServiceImpl = opSysUserServiceImpl;
    this.opSysRoleRepository = opSysRoleRepository;
    this.opSysRoleFunctionServiceImpl = opSysRoleFunctionServiceImpl;
    this.opSysUserRepository = opSysUserRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> saveOpSysRole(OpSysRoleEntity saveOpSysRoleEntity) {
    saveOpSysRoleEntity.setRoleName(saveOpSysRoleEntity.getRoleName().trim());
    if (isExist(saveOpSysRoleEntity)) {
      return new ResponseMsg(OpRespCodeEnum.ROLE_NAME_EXIST.getCode(),
          OpRespCodeEnum.ROLE_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    OpSysRoleEntity opSysRoleEntity = new OpSysRoleEntity();
    opSysRoleEntity.setId(createId());
    setCreateUser(opSysRoleEntity);
    Integer systemId =
        saveOpSysRoleEntity.getSystemId() != null ? saveOpSysRoleEntity.getSystemId() : 1;
    opSysRoleEntity.setSystemId(systemId);
    opSysRoleEntity.setRoleName(saveOpSysRoleEntity.getRoleName());
    opSysRoleEntity.setDescription(saveOpSysRoleEntity.getDescription());
    opSysRoleRepository.insert(opSysRoleEntity);
    if (!CollectionUtils.isEmpty(saveOpSysRoleEntity.getFunctions())) {
      saveOpSysRoleEntity.setRoleId(opSysRoleEntity.getId());
      saveOpSysRoleEntity.setCreateUserId(opSysRoleEntity.getCreateUserId());
      saveOpSysRoleEntity.setCreateUserName(opSysRoleEntity.getCreateUserName());
      opSysRoleFunctionServiceImpl.addRoleFunction(saveOpSysRoleEntity);
//      if (SsoConstant.SYSTEM_ID_MINI.equals(systemId)) {
//        this.addAuthorityRole(opSysRoleEntity.getId().toString(),
//            saveOpSysRoleEntity.getFunctions());
//      } else {
//        saveOpSysRoleEntity.setRoleId(opSysRoleEntity.getId());
//        saveOpSysRoleEntity.setCreateUserId(opSysRoleEntity.getCreateUserId());
//        saveOpSysRoleEntity.setCreateUserName(opSysRoleEntity.getCreateUserName());
//        opSysRoleFunctionServiceImpl.addRoleFunction(saveOpSysRoleEntity);
//      }
    }
    opSysRoleKeyCache.put(opSysRoleEntity.getId(), opSysRoleEntity);
    opSysUserServiceImpl.setAllUserFunctions();
    result = setData(result, opSysRoleEntity.getId().toString());
    return result;
  }

  @Override
  public Boolean isExist(OpSysRoleEntity opSysRoleEntity) {
    QueryWrapper<OpSysRoleEntity> opSysRoleEntityQueryWrapper = new QueryWrapper<>();
    opSysRoleEntityQueryWrapper.eq("role_name", opSysRoleEntity.getRoleName());
    if (null != opSysRoleEntity.getId()) {
      opSysRoleEntityQueryWrapper.ne("id", opSysRoleEntity.getId());
    }
    int count = opSysRoleRepository.selectCount(opSysRoleEntityQueryWrapper);
    return count > 0 ? true : false;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateOpSysRole(OpSysRoleEntity updateOpSysRoleEntity) {
    OpSysRoleEntity opSysRoleEntity = opSysRoleRepository.selectById(updateOpSysRoleEntity.getId());
    if (opSysRoleEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.ROLE_NOT_EXIST.getCode(),
          OpRespCodeEnum.ROLE_NOT_EXIST.getMsg());
    }
    updateOpSysRoleEntity.setRoleName(updateOpSysRoleEntity.getRoleName().trim());
    if (isExist(updateOpSysRoleEntity)) {
      return new ResponseMsg(OpRespCodeEnum.ROLE_NAME_EXIST.getCode(),
          OpRespCodeEnum.ROLE_NAME_EXIST.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    setUpdateUser(opSysRoleEntity);
    Integer systemId =
        updateOpSysRoleEntity.getSystemId() != null ? updateOpSysRoleEntity.getSystemId() : 1;
    opSysRoleEntity.setRoleName(updateOpSysRoleEntity.getRoleName());
    opSysRoleEntity.setSystemId(systemId);
    opSysRoleEntity.setDescription(updateOpSysRoleEntity.getDescription());
    opSysRoleRepository.updateById(opSysRoleEntity);
    if (!CollectionUtils.isEmpty(updateOpSysRoleEntity.getFunctions())) {
//      if (SsoConstant.SYSTEM_ID_MINI.equals(systemId)) {
//        this.addAuthorityRole(opSysRoleEntity.getId().toString(),
//            updateOpSysRoleEntity.getFunctions());
//      } else {
//
//      }
      updateOpSysRoleEntity.setRoleId(opSysRoleEntity.getId());
      updateOpSysRoleEntity.setCreateUserId(opSysRoleEntity.getUpdateUserId());
      updateOpSysRoleEntity.setCreateUserName(opSysRoleEntity.getUpdateUserName());
      opSysRoleFunctionServiceImpl.addRoleFunction(updateOpSysRoleEntity);
    }
    opSysRoleKeyCache.put(opSysRoleEntity.getId(), opSysRoleEntity);
    opSysUserServiceImpl.setAllUserFunctions();
    result = setData(result, opSysRoleEntity.getId().toString());
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateRoleStatus(OpSysRoleEntity updateOpSysRoleStatus) {
    OpSysRoleEntity opSysRoleEntity = opSysRoleRepository.selectById(updateOpSysRoleStatus.getId());
    if (opSysRoleEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.ROLE_NOT_EXIST.getCode(),
          OpRespCodeEnum.ROLE_NOT_EXIST.getMsg());
    }
    setUpdateUser(opSysRoleEntity);
    opSysRoleEntity.setDataStatus(updateOpSysRoleStatus.getDataStatus());
    opSysRoleRepository.updateById(opSysRoleEntity);
    if (null != opSysRoleKeyCache.get(updateOpSysRoleStatus.getId())) {
      opSysRoleKeyCache.put(updateOpSysRoleStatus.getId(), opSysRoleEntity);
    }
    opSysUserServiceImpl.setAllUserFunctions();
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opSysRoleEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg pageListOpRole(QueryOpRoleListDto queryOpRoleListDto) {
    Page page = new Page(queryOpRoleListDto.getPageNum(), queryOpRoleListDto.getPageSize());
    IPage<QueryOpRoleListDto> pageListOpRole = opSysRoleRepository
        .pageListOpRole(page, queryOpRoleListDto);
    ResponseMsg result = new ResponseMsg();
    result = setData(result, pageListOpRole);
    return result;
  }

  @Override
  public ResponseMsg getOpSysRoleById(Long id) {
    ResponseMsg result = new ResponseMsg();
    OpSysRoleEntity opSysRoleEntity = opSysRoleKeyCache.get(id);
    if (null == opSysRoleEntity) {
      opSysRoleEntity = opSysRoleRepository.selectById(id);
    }
    if (opSysRoleEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.ROLE_NOT_EXIST.getCode(),
          OpRespCodeEnum.ROLE_NOT_EXIST.getMsg());
    }
    List<Long> functionIds = opSysRoleRepository.getRoleFunctionsBySysRoleId(id);
    opSysRoleEntity.setFunctions(functionIds);
    if (CollectionUtils.isEmpty(functionIds)) {
      return new ResponseMsg(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
    }
    List<QueryOpFunctionsTreeListDto> list = this
        .getListTreeFunctions(opSysRoleEntity.getSystemId(), id);
    if (CollectionUtils.isEmpty(list)) {
      return new ResponseMsg(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
    }
    opSysRoleEntity.setOpFunTreeListDtos(list);
    result = setData(result, opSysRoleEntity);
    return result;
  }

  @Override
  public ResponseMsg pageListOpRoleRelationUser(
      QueryRoleRelationUserListDto queryRoleRelationUserListDto) {
    Page page = new Page(queryRoleRelationUserListDto.getPageNum(),
        queryRoleRelationUserListDto.getPageSize());
    IPage<QueryRoleRelationUserListDto> pageListOpRoleRelationUser = opSysRoleRepository
        .pageListOpRoleRelationUser(page, queryRoleRelationUserListDto);
    ResponseMsg result = new ResponseMsg();
    result = setData(result, pageListOpRoleRelationUser);
    return result;
  }

  @Override
  public ResponseMsg listTreeFunctions(Integer systemId, Long roleId) {
    List<QueryOpFunctionsTreeListDto> list = this.getListTreeFunctions(systemId, roleId);
    ResponseMsg result = new ResponseMsg();
    result = setData(result, list);
    return result;
  }

  private List<QueryOpFunctionsTreeListDto> getListTreeFunctions(Integer systemId, Long roleId) {
    List<QueryOpFunctionsTreeListDto> listTreeFunctions = null;
    listTreeFunctions = opSysRoleRepository.listTreeFunctions(systemId, roleId);
    Map<Long, List<QueryOpFunctionsTreeListDto>> collect = listTreeFunctions.stream()
        .collect(Collectors.groupingBy(QueryOpFunctionsTreeListDto::getPid));
    listTreeFunctions.forEach((item) -> {
      if (CollectionUtils.isEmpty(collect.get(item.getId()))) {
        item.setChild(new ArrayList<>());
      } else {
        item.setChild(collect.get(item.getId()));
      }
    });
    List<QueryOpFunctionsTreeListDto> list = listTreeFunctions
        .stream()
        .filter((item) -> ("node").equals(item.getOperationType()) || ("icon")
            .equals(item.getOperationType()))
        .collect(Collectors.toList());
    return list;
  }

  @Override
  public ResponseMsg listCurrentUserLatestPermissions(Long id) {
    ResponseMsg result = new ResponseMsg();
    OpSysUserEntity opSysUserEntity = opSysUserKeyCache.get(id);
    if (null == opSysUserEntity) {
      opSysUserEntity = opSysUserRepository.selectById(id);
    }
    if (opSysUserEntity == null) {
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    List<FunctionDto> opFunctions = opSysUserRepository.getUserFunctionsBySysUserId(id);
    if (CollectionUtils.isEmpty(opFunctions)) {
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
    }
    result = setData(result, opFunctions);
    return result;

  }

  @Override
  public ResponseMsg listDropDownRoles(QueryOpRoleListDto dto) {
    List<ListRoleDropDownListVo> listRoleDropDownListVos = opSysRoleRepository
        .listDropDownRoles(dto.getSystemId());
    ResponseMsg result = new ResponseMsg();
    result = setData(result, listRoleDropDownListVos);
    return result;
  }

  @Override
  public ResponseMsg getOpSystemList() {
    List<ListSystemVo> listSystemVos = opSysRoleRepository.getOpSystemList();
    return new ResponseMsg(listSystemVos);
  }

  @Override
  public ResponseMsg findMenuTreePageByLazy(Integer systemId, Long parentId) {
    List<QueryOpFunctionsTreeListDto> dtos = this.getFuntionsTree(systemId, parentId);
    if (dtos == null) {
      return new ResponseMsg();
    }
    for (QueryOpFunctionsTreeListDto dto : dtos) {
      List<QueryOpFunctionsTreeListDto> list = this.getFuntionsTree(systemId, dto.getId());
      if (!CollectionUtils.isEmpty(list)) {
        dto.setHasChildren(true);
      }
    }
    return new ResponseMsg(dtos);
  }


  private List<QueryOpFunctionsTreeListDto> getFuntionsTree(Integer systemId, Long parentId) {
    List<OpSysFunctionEntity> list = this.opSysFunctionEntitylist(systemId, parentId);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    List<QueryOpFunctionsTreeListDto> dtos = ListBeanUtil
        .listCopy(list, QueryOpFunctionsTreeListDto.class);
    return dtos;
  }

  private List<OpSysFunctionEntity> opSysFunctionEntitylist(Integer systemId, Long parentId) {
    LambdaQueryWrapper<OpSysFunctionEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(OpSysFunctionEntity::getSystemId, systemId);
    queryWrapper.eq(OpSysFunctionEntity::getPid, parentId);
    queryWrapper.eq(OpSysFunctionEntity::getDataStatus, DataStatusEnum.ENABLE.getCode());
    queryWrapper.orderByAsc(OpSysFunctionEntity::getSort);
    List<OpSysFunctionEntity> list = opSysFunctionRepository.selectList(queryWrapper);
    return list;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-09-28 14:59:14
   */
  @Override
  public ResponseMsg add(QueryOpFunctionsTreeListDto dto) {
    OpSysFunctionEntity entity = new OpSysFunctionEntity();
    BeanUtils.copyProperties(dto, entity);
    setCreateUser(entity);
    try {
      opSysFunctionRepository.insert(entity);
    } catch (Exception e) {
      log.error("新增失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "新增失败");
    }
    return new ResponseMsg();
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-09-28 14:59:14
   */
  @Override
  public ResponseMsg update(QueryOpFunctionsTreeListDto dto) {
    OpSysFunctionEntity entity = new OpSysFunctionEntity();
    BeanUtils.copyProperties(dto, entity);
    setUpdateUser(entity);
    try {
      opSysFunctionRepository.updateById(entity);
    } catch (Exception e) {
      log.error("更新失败 ", e);
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    opSysUserServiceImpl.setAllUserFunctions();
    return new ResponseMsg().setData(dto.getId());
  }

  @Override
  public ResponseMsg getMenuDetail(Long id) {
    OpSysFunctionEntity entity = opSysFunctionRepository.selectById(id);
    if (entity == null) {
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_NOT_EXIST.getCode(),
          OpRespCodeEnum.MENU_NOT_EXIST.getMsg());
    }
    QueryOpFunctionsTreeListDto dto = new QueryOpFunctionsTreeListDto();
    BeanUtils.copyProperties(entity, dto);
    OpSysFunctionEntity parentSysFunctionEntity = opSysFunctionRepository
        .selectById(entity.getPid());
    if (parentSysFunctionEntity != null) {
      dto.setPname(parentSysFunctionEntity.getFunctionName());
    }
    return new ResponseMsg(dto);
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-09-28 14:59:14
   */
  @Override
  public ResponseMsg remove(Long id) {
    OpSysFunctionEntity entity = opSysFunctionRepository.selectById(id);
    if (entity == null) {
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_NOT_EXIST.getCode(),
          OpRespCodeEnum.MENU_NOT_EXIST.getMsg());
    }
    List<QueryOpFunctionsTreeListDto> list = this.getFuntionsTree(entity.getSystemId(), id);
    if (!CollectionUtils.isEmpty(list)) {
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_HAVE_CHILD.getCode(),
          OpRespCodeEnum.MENU_HAVE_CHILD.getMsg());
    }
    LambdaQueryWrapper<OpSysRoleFunctionEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(OpSysRoleFunctionEntity::getFunctionId, id);
    OpSysRoleFunctionEntity opSysRoleFunctionEntity = opSysRoleFunctionRepository
        .selectOne(queryWrapper);
    if (opSysRoleFunctionEntity != null) {
      LambdaQueryWrapper<OpSysRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
      lambdaQueryWrapper.eq(OpSysRoleEntity::getId, opSysRoleFunctionEntity.getRoleId());
      OpSysRoleEntity opSysRoleEntity = opSysRoleRepository.selectOne(lambdaQueryWrapper);
      String roleName = null;
      if (opSysRoleEntity != null) {
        roleName = opSysRoleEntity.getRoleName();
      }
      return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_HAVE_CHILD.getCode(),
          "菜单" + entity.getFunctionName() + " 已被权限角色 " + roleName + "使用，请勿删除"
      );
    }
    if (opSysFunctionRepository.deleteById(id) > 0) {
      return new ResponseMsg();
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-09-28 14:59:14
   */
  @Override
  public ResponseMsg batchRemove(List<Long> ids) {
    for (Long id : ids) {
      OpSysFunctionEntity entity = opSysFunctionRepository.selectById(id);
      if (entity == null) {
        return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_NOT_EXIST.getCode(),
            OpRespCodeEnum.MENU_NOT_EXIST.getMsg());
      }
      List<QueryOpFunctionsTreeListDto> list = this.getFuntionsTree(entity.getSystemId(), id);
      if (!CollectionUtils.isEmpty(list)) {
        return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_HAVE_CHILD.getCode(),
            OpRespCodeEnum.MENU_HAVE_CHILD.getMsg());
      }
      LambdaQueryWrapper<OpSysRoleFunctionEntity> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(OpSysRoleFunctionEntity::getFunctionId, id);
      OpSysRoleFunctionEntity opSysRoleFunctionEntity = opSysRoleFunctionRepository
          .selectOne(queryWrapper);
      if (opSysRoleFunctionEntity != null) {
        LambdaQueryWrapper<OpSysRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OpSysRoleEntity::getId, opSysRoleFunctionEntity.getRoleId());
        OpSysRoleEntity opSysRoleEntity = opSysRoleRepository.selectOne(lambdaQueryWrapper);
        String roleName = null;
        if (opSysRoleEntity != null) {
          roleName = opSysRoleEntity.getRoleName();
        }
        return ResponseMsg.createBusinessErrorResp(OpRespCodeEnum.MENU_HAVE_CHILD.getCode(),
            "菜单" + entity.getFunctionName() + " 已被权限角色 " + roleName + "使用，请勿删除"
        );
      }
    }
    if (opSysFunctionRepository.deleteBatchIds(ids) > 0) {
      return new ResponseMsg();
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  @Override
  public ResponseMsg goCopy(Integer systemId, Long id) {
    this.addFunctionListAndChild(systemId, id, null);
    return new ResponseMsg();
  }

  private void addFunctionListAndChild(Integer systemId, Long id, Long pid) {
    OpSysFunctionEntity entity = opSysFunctionRepository.selectById(id);
    if (entity == null) {
      return;
    }
    OpSysFunctionEntity opSysFunctionEntity = new OpSysFunctionEntity();
    BeanUtils.copyProperties(entity, opSysFunctionEntity, "id");
    setCreateUser(opSysFunctionEntity);
    if (pid != null) {
      opSysFunctionEntity.setPid(pid);
    }
    opSysFunctionRepository.insert(opSysFunctionEntity);
    System.out.println(opSysFunctionEntity.getId());
    List<OpSysFunctionEntity> list = this
        .opSysFunctionEntitylist(systemId, id);
    if (!CollectionUtils.isEmpty(list)) {
      for (OpSysFunctionEntity functionEntity : list) {
        this.addFunctionListAndChild(systemId, functionEntity.getId(), opSysFunctionEntity.getId());
      }
    }
  }

  @Override
  public ResponseMsg updateKeyList(OpFunctionsDto dto) {
//    LambdaQueryWrapper<OpSysFunctionEntity> queryWrapper = new LambdaQueryWrapper();
//    queryWrapper.in(OpSysFunctionEntity::getId, dto.getIds());
//    List<OpSysFunctionEntity> list = opSysFunctionRepository.selectList(queryWrapper);
    List<OpSysFunctionEntity> list = this.getOpSysFunctionEntityListByPids(dto.getIds());
    if (!CollectionUtils.isEmpty(list)) {
      for (OpSysFunctionEntity functionEntity : list) {
        int flag = 0;
        if (StringUtilLocal.isNotEmpty(dto.getOldKey()) &&
            StringUtilLocal.isNotEmpty(dto.getNewKey()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getFunctionKey()) && functionEntity
            .getFunctionKey().contains(dto.getOldKey())) {
          flag++;
          String key = functionEntity.getFunctionKey();
          key = key.replaceAll(dto.getOldKey(), dto.getNewKey());
          functionEntity.setFunctionKey(key);
        }
        if (StringUtilLocal.isNotEmpty(dto.getOldKey()) &&
            StringUtilLocal.isNotEmpty(dto.getNewKey()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getLocationPath()) && functionEntity
            .getLocationPath().contains(dto.getOldKey())) {
          flag++;
          String locationPath = functionEntity.getLocationPath();
          locationPath = locationPath.replaceAll(dto.getOldKey(), dto.getNewKey());
          functionEntity.setLocationPath(locationPath);
        }
        if (StringUtilLocal.isNotEmpty(dto.getOldKey()) &&
            StringUtilLocal.isNotEmpty(dto.getNewKey()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getRoute()) && functionEntity
            .getRoute().contains(dto.getOldKey())) {
          flag++;
          String route = functionEntity.getRoute();
          route = route.replaceAll(dto.getOldKey(), dto.getNewKey());
          functionEntity.setRoute(route);
        }
        if (StringUtilLocal.isNotEmpty(dto.getOldName()) &&
            StringUtilLocal.isNotEmpty(dto.getNewName()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getFunctionName()) && functionEntity
            .getFunctionName().contains(dto.getOldName())) {
          flag++;
          String functionName = functionEntity.getFunctionName();
          functionName = functionName.replaceAll(dto.getOldName(), dto.getNewName());
          functionEntity.setFunctionName(functionName);
        }
        if (StringUtilLocal.isNotEmpty(dto.getOldName()) &&
            StringUtilLocal.isNotEmpty(dto.getNewName()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getFunctionDesc()) && functionEntity
            .getFunctionDesc().contains(dto.getOldName())) {
          flag++;
          String functionDesc = functionEntity.getFunctionDesc();
          functionDesc = functionDesc.replaceAll(dto.getOldName(), dto.getNewName());
          functionEntity.setFunctionDesc(functionDesc);
        }
        if (StringUtilLocal.isNotEmpty(dto.getOldName()) &&
            StringUtilLocal.isNotEmpty(dto.getNewName()) &&
            StringUtilLocal.isNotEmpty(functionEntity.getFunctionLabel()) && functionEntity
            .getFunctionLabel().contains(dto.getOldName())) {
          flag++;
          String functionLabel = functionEntity.getFunctionLabel();
          functionLabel = functionLabel.replaceAll(dto.getOldName(), dto.getNewName());
          functionEntity.setFunctionLabel(functionLabel);
        }
        if (flag > 0) {
          opSysFunctionRepository.updateById(functionEntity);
        }
      }
    }
    return new ResponseMsg(dto);
  }

  private List<OpSysFunctionEntity> getOpSysFunctionEntityListByPids(List<Long> ids) {
    LambdaQueryWrapper<OpSysFunctionEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(OpSysFunctionEntity::getId, ids);
    List<OpSysFunctionEntity> list = opSysFunctionRepository.selectList(queryWrapper);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }

    List<OpSysFunctionEntity> menuList = opSysFunctionRepository.selectList(null);
    for (Long id : ids) {
      List<OpSysFunctionEntity> childMenu = this.treeMenuList(menuList, id);
      if (!CollectionUtils.isEmpty(childMenu)) {
        list.addAll(childMenu);
      }
    }
    for (OpSysFunctionEntity opSysFunctionEntity : list) {
      System.out.println(opSysFunctionEntity.getId());
    }
    return list;
  }


  public List<OpSysFunctionEntity> treeMenuList(List<OpSysFunctionEntity> menuList, Long pid) {
    List<OpSysFunctionEntity> childMenu = new ArrayList<OpSysFunctionEntity>();
    for (OpSysFunctionEntity mu : menuList) {
      //遍历出父id等于参数的id，add进子节点集合
      if (mu.getPid().equals(pid)) {
        //递归遍历下一级
        treeMenuList(menuList, mu.getId());
        childMenu.add(mu);
      }
    }
    return childMenu;
  }
}
