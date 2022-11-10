package com.hf.op.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.infrastructure.constant.RedisKeyConstant;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.auth.AuthUnifiedUserEntity;
import com.hf.op.domain.model.department.OpDepartmentEntity;
import com.hf.op.domain.model.department.OpDepartmentRepository;
import com.hf.op.domain.model.function.OpSysFunctionEntity;
import com.hf.op.domain.model.login.OpLoginLogEntity;
import com.hf.op.domain.model.login.OpLoginLogRepository;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.model.user.OpSysUserRepository;
import com.hf.op.infrastructure.dto.auth.AuthUserComDto;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.department.QueryOpDeptTreeListDto;
import com.hf.op.infrastructure.dto.role.ListSystemRoleVo;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.dto.user.QueryOpUserListDto;
import com.hf.op.infrastructure.dto.user.QueryUserAccountInfoDto;
import com.hf.op.infrastructure.dto.user.UpdateUserPwdDto;
import com.hf.op.infrastructure.dto.user.UserDropDownVo;
import com.hf.op.infrastructure.global.OpGlobalConst;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.hf.op.service.impl.auth.AuthServiceImpl;
import com.hf.op.service.inf.OpSysUserRoleService;
import com.hf.op.service.inf.OpSysUserService;
import com.open.api.dto.FunctionDto;
import com.xxl.sso.core.entity.ReturnT;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author wpq
 * @function 用户业务类
 * @Date 2022/02/16
 */
@Service
@RefreshScope
@Slf4j
public class OpSysUserServiceImpl extends CrudService implements OpSysUserService {

  private static List<UserDropDownVo> mockUserList = new ArrayList<>();

  static {
    for (int i = 0; i < 5; i++) {
      UserDropDownVo userInfo = new UserDropDownVo();
      userInfo.setUserId(1000L + i);
      userInfo.setUserName("user" + (i > 0 ? String.valueOf(i) : ""));
      userInfo.setPassword("123456");
      mockUserList.add(userInfo);
    }
  }

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "op_sys_user_", cacheType = CacheType.BOTH)
  private Cache<Long, OpSysUserEntity> opSysUserKeyCache;
  @CreateCache(expire = 1200, name = CommCacheConst.BASE_KEY_ORG
      + "tokenCache", cacheType = CacheType.REMOTE)
  private Cache<String, String> tokenCache;

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG
      + "allRoleCacheForFunctionKey:", cacheType = CacheType.REMOTE)
  @CacheRefresh(refresh = 1, timeUnit = TimeUnit.MINUTES)
  private Cache<String, FunctionDto> allRoleCacheForFunctionKey;

  private OpSysUserRepository opSysUserRepository;
  private AuthServiceImpl authServiceImpl;
  private OpSysUserRoleService opSysUserRoleServiceImpl;
  private OpLoginLogRepository opLoginLogRepository;
  private OpDepartmentRepository opDepartmentRepository;

  public OpSysUserServiceImpl(OpSysUserRepository opSysUserRepository,
      AuthServiceImpl authServiceImpl,
      OpSysUserRoleService opSysUserRoleServiceImpl, OpLoginLogRepository opLoginLogRepository,
      OpDepartmentRepository opDepartmentRepository) {
    this.opSysUserRepository = opSysUserRepository;
    this.authServiceImpl = authServiceImpl;
    this.opSysUserRoleServiceImpl = opSysUserRoleServiceImpl;
    this.opLoginLogRepository = opLoginLogRepository;
    this.opDepartmentRepository = opDepartmentRepository;
  }

  @Override
  public List<FunctionDto> getUserFunctionsBySysUserId(Long sysUserId) {
    if (null == sysUserId) {
      return null;
    }
    List<FunctionDto> platformFunctions = opSysUserRepository
        .getUserFunctionsBySysUserId(sysUserId);
    return platformFunctions;
  }

//  @Override
//  public List<OpenAuthority> selectAuthorityByRole(Long sysUserId) {
//    if (null == sysUserId) {
//      return null;
//    }
//    List<OpenAuthority> platformFunctions = opSysUserRepository
//        .selectAuthorityByRole(sysUserId);
//    return platformFunctions;
//  }

  @Override
  public List<ListSystemVo> getCurListSystemVo(Long sysUserId) {
    if (null == sysUserId) {
      return null;
    }
    List<ListSystemVo> platformFunctions = opSysUserRepository
        .getCurListSystemVo(sysUserId);
    return platformFunctions;
  }

  /**
   * 获取所有权限列表
   */
  @Override
  public List<FunctionDto> getAllUserFunctions() {
    List<FunctionDto> platformFunctions = opSysUserRepository
        .getAllUserFunctions();
    return platformFunctions;
  }

  @Override
  public void setAllUserFunctions() {
    List<FunctionDto> functionDtos = this.getAllUserFunctions();
//    if (!CollectionUtils.isEmpty(functionDtos)) {
//      List<Integer> systemIds = ListBeanUtil.toList(functionDtos, "systemId");
//      systemIds = systemIds.stream().distinct().collect(Collectors.toList());
//      Map<Integer, List<FunctionDto>> integerListMap = ListBeanUtil
//          .toMapList(functionDtos, systemIds, "systemId");
//      if (integerListMap == null) {
//        return;
//      }
    for (FunctionDto functionDto : functionDtos) {
      allRoleCacheForFunctionKey.put(functionDto.getFunctionKey(), functionDto);
    }
//      for (Integer systemId : systemIds) {
//        List<FunctionDto> list = integerListMap.get(systemId);
//        if (SsoConstant.SYSTEM_ID_ORGUNIT.equals(systemId)) {
//          for (FunctionDto functionDto : list) {
//            allRoleCacheForFunctionKey.put(functionDto.getFunctionKey(), functionDto);
//          }
//        } else {
//          SsoTokenHelper.putFunctions(systemId, list);
//        }
//      }
//    }
  }

  @Override
  public List<OpSysFunctionEntity> getUserFunctionEntitiesBySysUserId(Long sysUserId) {
    if (null == sysUserId) {
      return null;
    }
    List<OpSysFunctionEntity> platformFunctionEntities = opSysUserRepository
        .getUserFunctionEntitiesBySysUserId(sysUserId);
    return platformFunctionEntities;
  }

  @Override
  public OpSysUserEntity getUserByUserId(Long userId) {
    if (null == userId) {
      return null;
    }
    QueryWrapper<OpSysUserEntity> wrapper = new QueryWrapper();
    wrapper.eq("user_id", userId);
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    return opSysUserRepository.selectOne(wrapper);
  }

  @Override
  public OpSysUserEntity getUserById(Long id) {
    if (id == null) {
      return null;
    }
    QueryWrapper<OpSysUserEntity> wrapper = new QueryWrapper();
    wrapper.eq("id", id);
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    OpSysUserEntity opSysUserEntity = opSysUserRepository.selectOne(wrapper);
    return opSysUserEntity;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<OpSysUserEntity> saveOpSysUser(OpSysUserEntity saveOpSysUserEntity) {
    AuthUserComDto authUserComDto = new AuthUserComDto();
    BeanUtils.copyProperties(saveOpSysUserEntity, authUserComDto);
    authUserComDto.setUserType(OpGlobalConst.UserType.SSOUser);
    authUserComDto.setDefaultPwd(OpGlobalConst.DefaultPwd.DefaultPwdYes);
    ResponseMsg authUserResult = authServiceImpl.register(authUserComDto);
    if (ResponseMsg.isNotSuccess(authUserResult)) {
      return authUserResult;
    }
    if (StringUtilLocal.isEmpty(authUserResult)) {
      return new ResponseMsg(OpRespCodeEnum.FAIL_CREATE_AUTHUSER.getCode(),
          OpRespCodeEnum.FAIL_CREATE_AUTHUSER.getMsg());
    }
    AuthUnifiedUserEntity authUnifiedUserEntity = (AuthUnifiedUserEntity) authUserResult.getData();
    Long userId = authUnifiedUserEntity.getId();
    String passWord = authUnifiedUserEntity.getPassWord();
    saveOpSysUserEntity.setUserRealName(saveOpSysUserEntity.getUserRealName().trim());
    ResponseMsg result = new ResponseMsg();
    OpSysUserEntity opSysUserEntity = new OpSysUserEntity();
    opSysUserEntity.setId(createId());
    opSysUserEntity.setDefaultInfo(saveOpSysUserEntity.getSysUserId(),
        saveOpSysUserEntity.getSysUserAccount());
    opSysUserEntity.setUserId(userId);
    opSysUserEntity.setUserRealName(saveOpSysUserEntity.getUserRealName());
    opSysUserEntity.setUserAccount(saveOpSysUserEntity.getUserAccount());
    opSysUserEntity.setUserEmail(saveOpSysUserEntity.getUserEmail());
    opSysUserEntity.setUserMobile(saveOpSysUserEntity.getUserMobile());
    opSysUserEntity.setDepartmentId(saveOpSysUserEntity.getDepartmentId());
    opSysUserEntity.setPostId(saveOpSysUserEntity.getPostId());
    opSysUserEntity.setStaffType(saveOpSysUserEntity.getStaffType());
    opSysUserEntity.setGender(saveOpSysUserEntity.getGender());
    setCreateUser(opSysUserEntity);
    opSysUserRepository.insert(opSysUserEntity);
    if (!CollectionUtils.isEmpty(saveOpSysUserEntity.getRoleIds())) {
      saveOpSysUserEntity.setUserId(opSysUserEntity.getId());
      opSysUserRoleServiceImpl.addUserRole(saveOpSysUserEntity);
    }
    opSysUserEntity.setPassWord(passWord);
    opSysUserKeyCache.put(opSysUserEntity.getId(), opSysUserEntity);
    result = setData(result, opSysUserEntity);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateOpSysUser(OpSysUserEntity updateOpSysUserEntity) {
    OpSysUserEntity opSysUserEntity = opSysUserRepository.selectById(updateOpSysUserEntity.getId());
    if (opSysUserEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    AuthUserComDto authUserComDto = new AuthUserComDto();
    BeanUtils.copyProperties(updateOpSysUserEntity, authUserComDto);
    authUserComDto.setUserId(opSysUserEntity.getUserId());
    ResponseMsg authUserResult = authServiceImpl.updateAuthUser(authUserComDto);
    if (ResponseMsg.isNotSuccess(authUserResult)) {
      return authUserResult;
    }
    Long remoteResult = (Long) authUserResult.getData();
    if (remoteResult == null) {
      return new ResponseMsg(OpRespCodeEnum.FAIL_UPDATE_AUTHUSER.getCode(),
          OpRespCodeEnum.FAIL_UPDATE_AUTHUSER.getMsg());
    }

    ResponseMsg result = new ResponseMsg();
    opSysUserEntity.setUpdateDefaultInfo(updateOpSysUserEntity.getSysUserId(),
        updateOpSysUserEntity.getSysUserAccount());
    opSysUserEntity.setUserRealName(updateOpSysUserEntity.getUserRealName());
    opSysUserEntity.setUserAccount(updateOpSysUserEntity.getUserAccount());
    opSysUserEntity.setUserEmail(updateOpSysUserEntity.getUserEmail());
    opSysUserEntity.setUserMobile(updateOpSysUserEntity.getUserMobile());
    opSysUserEntity.setDepartmentId(updateOpSysUserEntity.getDepartmentId());
    opSysUserEntity.setPostId(updateOpSysUserEntity.getPostId());
    opSysUserEntity.setStaffType(updateOpSysUserEntity.getStaffType());
    opSysUserEntity.setGender(updateOpSysUserEntity.getGender());
    setUpdateUser(opSysUserEntity);
    opSysUserRepository.updateById(opSysUserEntity);
    if (!CollectionUtils.isEmpty(updateOpSysUserEntity.getRoleIds())) {
      updateOpSysUserEntity.setUserId(opSysUserEntity.getId());
      opSysUserRoleServiceImpl.addUserRole(updateOpSysUserEntity);
    }
    opSysUserKeyCache.put(opSysUserEntity.getId(), opSysUserEntity);
    result = setData(result, opSysUserEntity.getId().toString());
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg<String> updateUserStatus(OpSysUserEntity updateOpSysUserStatus) {
    OpSysUserEntity opSysUserEntity = opSysUserRepository.selectById(updateOpSysUserStatus.getId());
    if (opSysUserEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    AuthUserComDto authUserComDto = new AuthUserComDto();
    authUserComDto.setUserId(opSysUserEntity.getUserId());
    authUserComDto.setDataStatus(updateOpSysUserStatus.getDataStatus());
    ResponseMsg authUserResult = authServiceImpl.updateAuthUserStatus(authUserComDto);
    if (ResponseMsg.isNotSuccess(authUserResult)) {
      return authUserResult;
    }
    Long remoteResult = (Long) authUserResult.getData();
    if (remoteResult == null) {
      return new ResponseMsg(OpRespCodeEnum.FAIL_UPDATE_AUTHUSER_STATUS.getCode(),
          OpRespCodeEnum.FAIL_UPDATE_AUTHUSER_STATUS.getMsg());
    }
    opSysUserEntity.setUpdateDefaultInfo(updateOpSysUserStatus.getSysUserId(),
        updateOpSysUserStatus.getSysUserAccount());
    opSysUserEntity.setDataStatus(updateOpSysUserStatus.getDataStatus());
    opSysUserRepository.updateById(opSysUserEntity);
    if (null != opSysUserKeyCache.get(opSysUserEntity.getId())) {
      opSysUserKeyCache.put(opSysUserEntity.getId(), opSysUserEntity);
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opSysUserEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg pageListOpUser(QueryOpUserListDto queryOpUserListDto) {
    Page page = new Page(queryOpUserListDto.getPageNum(), queryOpUserListDto.getPageSize());
    IPage<QueryOpUserListDto> pageListOpRole = opSysUserRepository
        .pageListOpUser(page, queryOpUserListDto);
    if (pageListOpRole.getTotal() > 0L) {
      this.converPage(pageListOpRole);
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, pageListOpRole);
    return result;
  }

  private void converPage(IPage<QueryOpUserListDto> listOpPost) {
    List<QueryOpUserListDto> postListDtos = listOpPost.getRecords();
    QueryWrapper<OpDepartmentEntity> wrapper = new QueryWrapper();
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    Integer count = opDepartmentRepository.selectCount(wrapper);
    if (count == 0) {
      return;
    }
    Page page = new Page(0, count);
    IPage<QueryOpDeptListDto> pageListDepartments = opDepartmentRepository
        .pageListDepartments(page, new QueryOpDeptListDto());
    if (pageListDepartments.getTotal() == 0L) {
      return;
    }
    List<QueryOpDeptListDto> deptListDtos = pageListDepartments.getRecords();
    Map<Long, QueryOpDeptListDto> downListVoMap = ListBeanUtil.toMap(deptListDtos, "id");
    if (downListVoMap == null) {
      return;
    }
    for (QueryOpUserListDto dto : postListDtos) {
      if (dto.getDepartmentId() == null) {
        continue;
      }
      String name = QueryOpDeptListDto.getDeptName(downListVoMap, dto.getDepartmentId().toString());
      dto.setDepartmentName(name);
    }
  }

  @Override
  public ResponseMsg getOpSysUserById(Long id) {
    ResponseMsg result = new ResponseMsg();
    OpSysUserEntity opSysUserEntity = opSysUserKeyCache.get(id);
    if (null == opSysUserEntity) {
      opSysUserEntity = opSysUserRepository.selectById(id);
    }
    if (opSysUserEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    List<ListSystemRoleVo> vos = opSysUserRepository.getRoleIdsBySysUserId(id);
    if (CollectionUtils.isEmpty(vos)) {
      return new ResponseMsg(OpRespCodeEnum.USER_ROLES_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_ROLES_NOT_EXIST.getMsg());
    }
    List<List<Long>> systemAndRoleIds = new ArrayList<>();
    for (ListSystemRoleVo vo : vos) {
      if (vo == null || vo.getSystemId() == null) {
        continue;
      }
      List<Long> list = new ArrayList<>();
      list.add(vo.getSystemId().longValue());
      list.add(vo.getRoleId());
      systemAndRoleIds.add(list);
    }
    opSysUserEntity.setSystemAndRoleIds(systemAndRoleIds);
    result = setData(result, opSysUserEntity);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg resetUserPwd(Long id) {
    OpSysUserEntity opSysUserEntity = opSysUserKeyCache.get(id);
    if (null == opSysUserEntity) {
      opSysUserEntity = opSysUserRepository.selectById(id);
    }
    if (opSysUserEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    AuthUserComDto authUserComDto = new AuthUserComDto();
    authUserComDto.setUserId(opSysUserEntity.getUserId());
    ResponseMsg remoteResetUserPwd = authServiceImpl.resetUserPwd(authUserComDto);
    if (ResponseMsg.isNotSuccess(remoteResetUserPwd)) {
      return remoteResetUserPwd;
    }
    if (StringUtilLocal.isEmpty(remoteResetUserPwd)) {
      return new ResponseMsg(OpRespCodeEnum.FAIL_RESET_AUTHUSER_PWD.getCode(),
          OpRespCodeEnum.FAIL_RESET_AUTHUSER_PWD.getMsg());
    }
    AuthUnifiedUserEntity authUnifiedUserEntity = (AuthUnifiedUserEntity) remoteResetUserPwd
        .getData();
    opSysUserEntity.setPassWord(authUnifiedUserEntity.getPassWord());
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opSysUserEntity);
    return result;
  }

  @Override
  public ResponseMsg getOpSysUserAccountInfoById(Long id) {
    ResponseMsg result = new ResponseMsg();
    QueryUserAccountInfoDto userAccountInfoDto = opSysUserRepository
        .getOpSysUserAccountInfoById(id);
    result = setData(result, userAccountInfoDto);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg updateUserPwd(UpdateUserPwdDto updateUserPwdDto) {
    OpSysUserEntity opSysUserEntity = this.getUserByUserId(updateUserPwdDto.getId());
    if (opSysUserEntity == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    if (updateUserPwdDto.getNewPwd().length() < 6 || updateUserPwdDto.getNewPwd().length() > 16) {
      return new ResponseMsg(OpRespCodeEnum.ERR_FORMAT_PWD_MESSAGE.getCode(),
          OpRespCodeEnum.ERR_FORMAT_PWD_MESSAGE.getMsg());
    }
    if (updateUserPwdDto.getOldPwd().equals(updateUserPwdDto.getNewPwd())) {
      return new ResponseMsg(OpRespCodeEnum.ERR_SAME_PWD_MESSAGE.getCode(),
          OpRespCodeEnum.ERR_SAME_PWD_MESSAGE.getMsg());
    }
    if (!updateUserPwdDto.getNewPwd().equals(updateUserPwdDto.getConfirmPwd())) {
      return new ResponseMsg(OpRespCodeEnum.ERR_REPEAT_PWD_MESSAGE.getCode(),
          OpRespCodeEnum.ERR_REPEAT_PWD_MESSAGE.getMsg());
    }
    AuthUserComDto authUserComDto = new AuthUserComDto();
    authUserComDto.setUserId(opSysUserEntity.getUserId());
    authUserComDto.setOldPwd(updateUserPwdDto.getOldPwd());
    authUserComDto.setNewPwd(updateUserPwdDto.getNewPwd());
    ResponseMsg remoteUpdateUserPwd = authServiceImpl.updateUserPwd(authUserComDto);
    if (ResponseMsg.isNotSuccess(remoteUpdateUserPwd)) {
      return remoteUpdateUserPwd;
    }
    if (StringUtilLocal.isEmpty(remoteUpdateUserPwd)) {
      return new ResponseMsg(OpRespCodeEnum.FAIL_EDIT_AUTHUSER_PWD.getCode(),
          OpRespCodeEnum.FAIL_EDIT_AUTHUSER_PWD.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    result = setData(result, opSysUserEntity.getId().toString());
    return result;
  }

  @Override
  public ResponseMsg logout(String tokenAuth) {
    AuthUserComDto authUserComDto = new AuthUserComDto();
    authUserComDto.setToken(tokenAuth);
    tokenCache.remove(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + authUserComDto.getToken());
    ResponseMsg result = new ResponseMsg();
    result.suc();
    return result;
  }

  @Override
  public ResponseMsg listDropDownUsers() {
    List<QueryOpDeptTreeListDto> listTreeDepartments = opDepartmentRepository.listTreeDepartments();
    Map<Long, List<QueryOpDeptTreeListDto>> collect = listTreeDepartments.stream()
        .collect(Collectors.groupingBy(QueryOpDeptTreeListDto::getSuperiorDepartment));
    listTreeDepartments.forEach((item) -> {
      if (CollectionUtils.isEmpty(collect.get(item.getId()))) {
        item.setChild(new ArrayList<>());
      } else {
        item.setChild(collect.get(item.getId()));
      }
      List<UserDropDownVo> listParentDeptUsers = opSysUserRepository
          .listUsersByDeptId(item.getId());
      if (!CollectionUtils.isEmpty(listParentDeptUsers)) {
        for (UserDropDownVo userDropDownVo : listParentDeptUsers) {
          QueryOpDeptTreeListDto queryOpDeptTreeListDtos = new QueryOpDeptTreeListDto();
          queryOpDeptTreeListDtos.setId(userDropDownVo.getUserId());
          queryOpDeptTreeListDtos.setName(userDropDownVo.getUserName());
          queryOpDeptTreeListDtos.setIsDept(OpGlobalConst.IsDept.IsDeptNo);
          item.getChild().add(queryOpDeptTreeListDtos);
        }
      }
    });
    List<QueryOpDeptTreeListDto> list = listTreeDepartments
        .stream()
        .filter((item) -> item.getLevel() == 1)
        .collect(Collectors.toList());
    ResponseMsg result = new ResponseMsg();
    //System.out.println(JSON.toJSONString(list));
    result = setData(result, list);
    return result;
  }

  /**
   * @description 查询登录日志
   * @method queryOpSysUserLoginLogById
   * @date: 2022/4/22 14:50
   * @author:liangcanlong
   */
  @Override
  public ResponseMsg queryOpSysUserLoginLogById(Long id, Integer pageNum, Integer pageSize) {
    Page page = new Page(pageNum, pageSize);
    QueryWrapper<OpLoginLogEntity> wrapper = new QueryWrapper();
    wrapper.eq("sys_user_id", id);
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    wrapper.orderByDesc("create_time");
    IPage<OpLoginLogEntity> logEntityIPage = opLoginLogRepository.selectPage(page, wrapper);
    return new ResponseMsg().setData(PageUtil.getHumpPage(logEntityIPage));
  }

  @Override
  public ResponseMsg findUser(String username, String password) {

    if (username == null || username.trim().length() == 0) {
      return new ResponseMsg(ReturnT.FAIL_CODE, "Please input username.");
    }
    if (password == null || password.trim().length() == 0) {
      return new ResponseMsg(ReturnT.FAIL_CODE, "Please input username.");
    }

    // mock user
    for (UserDropDownVo mockUser : mockUserList) {
      if (mockUser.getUserName().equals(username) && mockUser.getPassword().equals(password)) {
        return new ResponseMsg(mockUser);
      }
    }
    return new ResponseMsg(ReturnT.FAIL_CODE, "username or password is invalid.");
  }

}
