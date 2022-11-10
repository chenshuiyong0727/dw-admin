package com.hf.op.service.impl.auth;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.domain.LoginInfoValueObject;
import com.auth.common.infrastructure.constant.LoginAccountTypeEnum;
import com.auth.common.infrastructure.constant.RedisKeyConstant;
import com.auth.common.infrastructure.resp.AuthResp;
import com.auth.common.infrastructure.resp.AuthRespCodeEnum;
import com.auth.common.infrastructure.resp.SsoUserInfoResp;
import com.auth.common.infrastructure.util.jwt.IJWTInfo;
import com.auth.common.infrastructure.util.jwt.JWTInfo;
import com.auth.common.infrastructure.util.jwt.JwtTokenUtil;
import com.auth.common.infrastructure.util.jwt.UserAuthUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.resp.BaseResponse;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.AESUtil;
import com.hf.common.infrastructure.util.HttpClientUtil;
import com.hf.common.infrastructure.util.MD5Utils;
import com.hf.common.infrastructure.util.PasswordUtils;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.ThreadPoolUtils;
import com.hf.common.service.CrudService;
import com.hf.op.domain.model.auth.AuthLoginLogEntity;
import com.hf.op.domain.model.auth.AuthLoginLogRepository;
import com.hf.op.domain.model.auth.AuthLoginRepository;
import com.hf.op.domain.model.auth.AuthUnifiedUserEntity;
import com.hf.op.domain.model.auth.RefreshTokenService;
import com.hf.op.domain.model.login.LoginValueObject;
import com.hf.op.domain.model.login.OpLoginLogEntity;
import com.hf.op.domain.model.login.OpLoginLogRepository;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.domain.service.LoginService;
import com.hf.op.infrastructure.dto.auth.AuthLoginComDto;
import com.hf.op.infrastructure.dto.auth.AuthRefreshTokenDto;
import com.hf.op.infrastructure.dto.auth.AuthUserComDto;
import com.hf.op.infrastructure.dto.role.ListSystemVo;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.hf.op.service.inf.OpSysUserService;
import com.hf.op.service.inf.auth.AuthService;
import com.open.api.dto.FunctionDto;
import com.open.api.req.ApiDataRequestMsg;
import com.xxl.sso.core.login.SsoTokenLoginHelper;
import com.xxl.sso.core.store.SsoSessionIdHelper;
import com.xxl.sso.core.user.XxlSsoUser;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 曾仕斌
 * @function 用户登录
 * @date 2021/11/9
 */
@Service
@Slf4j
@RefreshScope
public class AuthServiceImpl extends CrudService implements AuthService {

  //其他类型权限
  public static final Integer ROLE_TYPE_FOR_OTHER = 9;
  @CreateCache(expire = 300, name = CommCacheConst.BASE_KEY_ORG
      + "codeCache", cacheType = CacheType.REMOTE)
  private Cache<String, String> codeCache;
  @CreateCache(expire = 86400, name = CommCacheConst.BASE_KEY_ORG
      + "userRoleCache:", cacheType = CacheType.REMOTE)
  private Cache<Long, HashMap<String, FunctionDto>> userRoleCache;
  @CreateCache(name = CommCacheConst.BASE_KEY_ORG
      + "allRoleCacheForFunctionKey:", cacheType = CacheType.REMOTE)
  @CacheRefresh(refresh = 1, timeUnit = TimeUnit.MINUTES)
  private Cache<String, FunctionDto> allRoleCacheForFunctionKey;

  private OpSysUserService opSysUserServiceImpl;
  @Autowired
  private ObjectMapper jacksonObjectMapper;
  private OpLoginLogRepository opLoginLogRepository;
  private AuthLoginRepository authLoginRepository;

  /**
   * 免检校验
   */
  @Value("${exemptUrl}")
  private String exemptUrl;

  @Value("${loginNoCheckFlag}")
  private Integer loginNoCheckFlag;//是否验证登录密码 是1否0
  @Value("${expire}")
  private int expire;


  @Value("${refreshExpire}")
  private int refreshExpire;

  @Value("${plateFormUrl}")
  private String plateFormUrl;

  @Value("${portalUrl}")
  private String portalUrl;

  @Value("${plateFormAccountId}")
  private String plateFormAccountId;

  @Value("${plateFormKey}")
  private String plateFormKey;

  @Value("${miniFormUrl}")
  private String miniFormUrl;

  @Value("${miniFormUser}")
  private String miniFormUser;

  @Value("${miniFormPass}")
  private String miniFormPass;

  @Value("${xxl.sso.redis.expire.minute}")
  private int redisExpireMinute;

  private JwtTokenUtil jwtTokenUtil;

  private UserAuthUtil userAuthUtil;

  private AuthLoginLogRepository authLoginLogRepository;

  @CreateCache(expire = 1200, name = CommCacheConst.BASE_KEY_ORG
      + "tokenCache", cacheType = CacheType.REMOTE)
  private Cache<String, String> tokenCache;

  public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, UserAuthUtil userAuthUtil,
      AuthLoginRepository authLoginRepository,
      AuthLoginLogRepository authLoginLogRepository,
      OpSysUserService opSysUserServiceImpl, OpLoginLogRepository opLoginLogRepository) {
    this.authLoginLogRepository = authLoginLogRepository;
    this.opSysUserServiceImpl = opSysUserServiceImpl;
    this.opLoginLogRepository = opLoginLogRepository;
    this.authLoginRepository = authLoginRepository;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userAuthUtil = userAuthUtil;
  }

  @Override
  public ResponseMsg login(AuthLoginComDto authLoginComDto) throws Exception {
    if (CommonConstant.LOGIN_NO_CHECK_FLAG_YES.equals(loginNoCheckFlag)) {
      String code = codeCache
          .get(CommCacheConst.EXP_CODE + authLoginComDto.getLoginAccount());
      if (StringUtilLocal.isEmpty(code)) {
        return new ResponseMsg(OpRespCodeEnum.EXP_VERITY_CODE.getCode(),
            OpRespCodeEnum.EXP_VERITY_CODE.getMsg());
      }
      if (!code.equals(authLoginComDto.getVerifyCode())) {
        return new ResponseMsg(OpRespCodeEnum.ERROR_VERITY_CODE.getCode(),
            OpRespCodeEnum.ERROR_VERITY_CODE.getMsg());
      }
    }
    ResponseMsg loginResult = remoteLogin(authLoginComDto);
    if (ResponseMsg.isNotSuccess(loginResult)) {
      return loginResult;
    }
    AuthResp result = (AuthResp) loginResult.getData();
    Long userId = result.getUserId();
    OpSysUserEntity opUserEntity = opSysUserServiceImpl.getUserByUserId(userId);
    if (null == opUserEntity) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    SsoUserInfoResp ssoUserInfoResp = new SsoUserInfoResp();
    BeanUtils.copyProperties(opUserEntity, ssoUserInfoResp);
    List<FunctionDto> opFunctions = opSysUserServiceImpl
        .getUserFunctionsBySysUserId(opUserEntity.getId());
    List<ListSystemVo> systemList = opSysUserServiceImpl
        .getCurListSystemVo(opUserEntity.getId());
    if (CollectionUtils.isEmpty(systemList)) {
      return new ResponseMsg(OpRespCodeEnum.SYSTEM_LIST_NOT_EXIST.getCode(),
          OpRespCodeEnum.SYSTEM_LIST_NOT_EXIST.getMsg());
    }
    String token = result.getAccessToken();
    String userAccount = result.getUserAccount();
    String userExp = result.getExp();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String exp = sdf.format(new Date(Long.parseLong(userExp.concat("000"))));      // 时间戳转换成时间
    // SSO 统一用户登录
//    String sessionId = this.getSessionId(result.getSsoUserNo(), userAccount, ssoUserInfoResp);
    Map<String, Object> resp = new HashMap<>();
    resp.put("id", opUserEntity.getId());
    resp.put("userId", userId);
    resp.put("userAccount", userAccount);
    resp.put("responsibleDepartment", opUserEntity.getDepartmentId());
    resp.put("token", token);
    resp.put("refreshToken", result.getRefreshToken());
    resp.put("tokenExpireTime", exp);
//    List<FunctionDto> functionList = null;
//    try {
//      functionList = this
//          .getOrgunitFunctions(opFunctions, sessionId, opUserEntity.getId(), systemList);
//    } catch (BusinessException e) {
//      return new ResponseMsg(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getCode(),
//          OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
//    }
    resp.put("functionList", opFunctions);
    resp.put("systemList", systemList);
    /*统一登录标识*/
//    resp.put("sessionId", sessionId);
    loginResult = this.setData(loginResult, resp);
    //记录登录日志
    this.insertLoginLog(userId, authLoginComDto.getLoginAccount());
    doRoleCache(userId, opFunctions);
    return loginResult;
  }

//  private List<FunctionDto> getOrgunitFunctions(List<FunctionDto> opFunctions, String sessionId,
//      Long sysUserId, List<ListSystemVo> systemList) {
//    List<Integer> ids = ListBeanUtil.toList(systemList, "systemId");
//    if (CollectionUtils.isEmpty(ids)) {
//      return opFunctions;
//    }
//    SsoTokenHelper.putSystemIdList(sessionId, ids);
//    Map<Integer, List<FunctionDto>> integerListMap = ListBeanUtil
//        .toMapList(opFunctions, ids, "systemId");
//    if (integerListMap == null) {
//      return opFunctions;
//    }
//    List<FunctionDto> res = new ArrayList<>();
//    List<OpenAuthority> openAuthorities = null;
//    for (ListSystemVo vo : systemList) {
//      // 小程序
//      if (SsoConstant.SYSTEM_ID_MINI.equals(vo.getSystemId())) {
//        openAuthorities = this.findAuthorityByUser(sysUserId);
//        if (CollectionUtils.isNotEmpty(openAuthorities)) {
//          List<AuthorityMenu> authorityMenuList = this.getAuthorityMenu(sysUserId);
//          vo.setIsHaveRole(CommonConstant.STATUS_NORMAL);
//          SsoTokenHelper.putAuthorityMenus(sessionId, authorityMenuList);
//          SsoTokenHelper.putOpenAuthoritys(sessionId, openAuthorities);
//        }
//      } else {
//        List<FunctionDto> list = integerListMap.get(vo.getSystemId());
//        SsoTokenHelper.putFunctions(vo.getSystemId(), sessionId, list);
//        if (SsoConstant.SYSTEM_ID_ORGUNIT.equals(vo.getSystemId())) {
//          res = list;
//          //JedisUtil.setStringValue(SsoConstant.SSO_CLENT_URL_KEY, vo.getSystemUrl(), SsoConstant.SSO_CLENT_URL_KEY_EX_TIME);
//        }
//      }
//    }
//    if (CollectionUtils.isEmpty(opFunctions) && CollectionUtils.isEmpty(openAuthorities)) {
//      throw new BusinessException(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
//    }
//    if (CollectionUtils.isEmpty(res)) {
//      res = new ArrayList<>();
//    }
//    // 组织中台权限列表
//    return res;

//}

  /**
   * @description
   * @method getSessionId 得到统一登录标识,后续请求都需带 xxl_sso_sessionid
   * @date: 2022/6/2 14:35
   * @author: chensy
   */
  private String getSessionId(String ssoUserNo, String userAccount,
      SsoUserInfoResp ssoUserInfoResp) {
    // 1、make xxl-sso user
    XxlSsoUser xxlUser = new XxlSsoUser();
    xxlUser.setUserid(ssoUserNo);
    xxlUser.setUsername(userAccount);
    xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
    xxlUser.setExpireMinite(expire / 60);
    Map<String, String> userMap = JSON
        .parseObject(JSON.toJSONString(ssoUserInfoResp), new TypeReference<Map<String, String>>() {
        });
    xxlUser.setPlugininfo(userMap);
    // 2、generate sessionId + storeKey
    String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);
    log.info("登录统一标识 : " + SsoSessionIdHelper.parseStoreKey(sessionId));
    // 3、login, store storeKey
    SsoTokenLoginHelper.login(sessionId, xxlUser);
    return sessionId;
  }

//  private List<OpenAuthority> findAuthorityByUser(Long sysUserId) {
//    List<OpenAuthority> authorities = opSysUserServiceImpl.selectAuthorityByRole(sysUserId);
//    // 权限去重
//    HashSet h = new HashSet(authorities);
//    authorities.clear();
//    authorities.addAll(h);
//    return authorities;
//  }


  private void insertLoginLog(Long userId, String loginAccount) throws UnknownHostException {
    OpLoginLogEntity opLoginLogEntity = new OpLoginLogEntity();
    opLoginLogEntity.setId(createId());
    opLoginLogEntity.setSysUserId(userId);
    opLoginLogEntity.setDefaultInfo(userId, loginAccount);
    String machineIp = InetAddress.getLocalHost().getHostAddress();
    opLoginLogEntity.setIp(machineIp);
    opLoginLogRepository.insert(opLoginLogEntity);
  }

  /**
   * @description 缓存登录相关信息[权限列表]
   * @method doLoginCache
   * @date: 2022/5/16 9:19
   * @author:liangcanlong
   */
  private void doRoleCache(Long userId, List<FunctionDto> opFunctions) {
    HashMap<String, FunctionDto> map = Maps.newLinkedHashMapWithExpectedSize(opFunctions.size());
    for (FunctionDto opFunction : opFunctions) {
      if (opFunction == null) {
        continue;
      }
      map.put(opFunction.getFunctionKey(), opFunction);
    }
    userRoleCache.put(userId, map);
  }


  @Override
  public ResponseMsg refreshToken(AuthRefreshTokenDto authRefreshTokenDto) throws Exception {
    ResponseMsg refreshTokenResult = this.remoteRefreshToken(authRefreshTokenDto);
    if (ResponseMsg.isNotSuccess(refreshTokenResult)) {
      return refreshTokenResult;
    }
    AuthResp authResp = (AuthResp) refreshTokenResult.getData();
    String token = authResp.getAccessToken();
    String userExp = authResp.getExp();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String exp = sdf.format(new Date(Long.parseLong(userExp.concat("000"))));      // 时间戳转换成时间
    Map<String, Object> resp = new HashMap<>();
    resp.put("token", token);
    resp.put("tokenExpireTime", exp);
    refreshTokenResult = this.setData(refreshTokenResult, resp);
    return refreshTokenResult;
  }

  private ResponseMsg remoteRefreshToken(AuthRefreshTokenDto authRefreshTokenDto) throws Exception {
    ResponseMsg responseMsg = RefreshTokenService
        .refreshTokenBasicCheck(authRefreshTokenDto.getRefreshToken(),
            authRefreshTokenDto.getToken(), Long.valueOf(authRefreshTokenDto.getUserId()));
    if (!ResponseMsg.isSuccess(responseMsg)) {
      return responseMsg;
    }
    IJWTInfo refreshTokenJwtInfo = null;
    IJWTInfo oldTokenJwtInfo = null;
    try {
      refreshTokenJwtInfo = jwtTokenUtil.getInfoFromToken(authRefreshTokenDto.getRefreshToken());
      oldTokenJwtInfo = jwtTokenUtil.getInfoFromToken(authRefreshTokenDto.getToken());
    } catch (Exception e) {
      log.error("token转换异常:{}", e);
      return new ResponseMsg(AuthRespCodeEnum.REFRESH_TOKEN_NOT_EXIST_OR_EXPIRE.getCode(),
          AuthRespCodeEnum.REFRESH_TOKEN_NOT_EXIST_OR_EXPIRE.getMsg());
    }
    String refreshTokenCache = tokenCache.get(
        RedisKeyConstant.REDIS_KEY_REFRESH_TOKEN + ":" + authRefreshTokenDto.getRefreshToken());
    responseMsg = RefreshTokenService
        .isCanRefresh(Long.valueOf(authRefreshTokenDto.getUserId()), refreshTokenCache);
    if (!ResponseMsg.isSuccess(responseMsg) || null == responseMsg.getData()) {
      return responseMsg;
    }
    LoginInfoValueObject onlineLog = (LoginInfoValueObject) responseMsg.getData();
    JWTInfo jwtInfo = new JWTInfo(onlineLog.getUserId(), onlineLog.getUserEmail(),
        onlineLog.getUserMobile(), onlineLog.getIdCardNo(), onlineLog.getUserAccount(),
        onlineLog.getUserRealName(), null);
    String token = null;
    try {
      token = jwtTokenUtil.generateToken(jwtInfo);
    } catch (Exception e) {
      return new ResponseMsg(AuthRespCodeEnum.TOKEN_CREATE_ERROR.getCode(),
          AuthRespCodeEnum.TOKEN_CREATE_ERROR.getMsg());
    }
    IJWTInfo userInfo = userAuthUtil.getInfoFromToken(token);
    jwtInfo.setExp(userInfo.getExp());
    tokenCache.put(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + token,
        JSON.toJSONString(jwtInfo, false), expire, TimeUnit.SECONDS);

    //移除旧的tokenId
    tokenCache.remove(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + authRefreshTokenDto.getToken());

    AuthResp authResp = new AuthResp();
    authResp.setAccessToken(token);
    authResp.setExp(userInfo.getExp());
    return new ResponseMsg(authResp);
  }

  @Override
  public ResponseMsg checkFunction(Long userId, String requestUri) {
    assert null != userId && StringUtilLocal.isNotEmpty(requestUri);
    if (null == userId || StringUtilLocal.isEmpty(requestUri)) {
      return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
          BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
    }
    OpSysUserEntity opUserEntity = opSysUserServiceImpl.getUserByUserId(userId);
    if (null == opUserEntity) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_NOT_EXIST.getMsg());
    }
    //包含免检字符串
    if (exemptUrl.contains(requestUri)) {
      return new ResponseMsg();
    }
    //检查是否免检
    FunctionDto exemptFunction = allRoleCacheForFunctionKey.get(requestUri);
    if (exemptFunction == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getCode(),
          OpRespCodeEnum.USER_FUNCTIONS_NOT_EXIST.getMsg());
    } else {
      if (ROLE_TYPE_FOR_OTHER.equals(exemptFunction.getFunctionType())) {
        return new ResponseMsg();
      }
    }
    //缓存中获取用户权限
    HashMap<String, FunctionDto> functionDtoMap = userRoleCache.get(userId);
    if (MapUtil.isEmpty(functionDtoMap)) {
      return new ResponseMsg(ServerErrorConst.ERR_TOKEN_EMPTY,
          ServerErrorConst.ERR_TOKEN_EMPTY_MSG);
    }
    if (functionDtoMap.get(requestUri) == null) {
      return new ResponseMsg(OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getCode(),
          OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getMsg());
    }
    return new ResponseMsg();
  }
  /**
   * 到统一认证中心登录
   */
/*
  private ResponseMsg remoteLogin1(AuthLoginComDto authLoginDto) {
    assert authLoginDto != null;
    if (null == authLoginDto) {
      return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
          BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
    }
    LoginValueObject loginValueObject = new LoginValueObject();
    BeanUtils.copyProperties(authLoginDto, loginValueObject);
    ResponseMsg loginResult = LoginService.remoteLoginCheck(loginValueObject);
    if (ResponseMsg.isNotSuccess(loginResult)) {
      return loginResult;
    }
    return authLoginComService.login(authLoginDto);
  }
*/

  /**
   * 到统一认证中心登录
   */
  private ResponseMsg remoteLogin(AuthLoginComDto authLoginDto) throws Exception {
    assert authLoginDto != null;
    if (null == authLoginDto) {
      return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
          BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
    }
    LoginValueObject loginValueObject = new LoginValueObject();
    BeanUtils.copyProperties(authLoginDto, loginValueObject);
    ResponseMsg loginResult = LoginService.remoteLoginCheck(loginValueObject);
    if (ResponseMsg.isNotSuccess(loginResult)) {
      return loginResult;
    }
    ResponseMsg responseMsg = AuthUnifiedUserEntity
        .loginCheck(authLoginDto.getLoginAccount(), authLoginDto.getLoginPassword(),
            authLoginDto.getVerifyCode(), authLoginDto.getAccountType());
    if (BaseResponse.isNotSuccess(responseMsg)) {
      return responseMsg;
    }
    return loginWithoutMobile(authLoginDto);

    //return authLoginComService.login(authLoginDto);
  }

  /**
   * 非手机号登录
   */
  public ResponseMsg loginWithoutMobile(AuthLoginComDto authLoginComDto) throws Exception {
    QueryWrapper<AuthUnifiedUserEntity> wrapper = new QueryWrapper();
    if (authLoginComDto.getAccountType().intValue() == LoginAccountTypeEnum.USER_NAME.getCode()) {
      wrapper.eq("user_account", authLoginComDto.getLoginAccount());
    } else {
      wrapper.eq("id_card_no", authLoginComDto.getLoginAccount());
    }
    wrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    AuthUnifiedUserEntity baseLoginEntity = authLoginRepository.selectOne(wrapper);
    if (null == baseLoginEntity) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getCode(),
          AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getMsg());
    }
    if (CommonConstant.LOGIN_NO_CHECK_FLAG_YES.equals(loginNoCheckFlag)) {
      if (!PasswordUtils.matches(baseLoginEntity.getSalt(), authLoginComDto.getLoginPassword(),
          baseLoginEntity.getLoginPassword())) {
        return new ResponseMsg(AuthRespCodeEnum.PWD_IS_WRONG.getCode(),
            AuthRespCodeEnum.PWD_IS_WRONG.getMsg());
      }
    }
    return buildSuccessResult(baseLoginEntity, authLoginComDto.getLoginSystem());
  }

  /**
   * 构建登录成功结果
   */
  private ResponseMsg buildSuccessResult(AuthUnifiedUserEntity baseLoginEntity, Integer loginSystem)
      throws Exception {
    String userEmail = baseLoginEntity.getUserEmail();
    String userMobile = baseLoginEntity.getUserMobile();
    String idCardNo = baseLoginEntity.getIdCardNo();
    String userAccount = baseLoginEntity.getUserAccount();
    String userRealName = baseLoginEntity.getUserRealName();
    Long userId = baseLoginEntity.getId();
    JWTInfo jwtInfo = new JWTInfo(userId, userEmail, userMobile, idCardNo, userAccount,
        userRealName, null);
    String token = null;
    String refreshToken = null;
    JWTInfo jwtRefreshInfo = null;
    try {
      token = jwtTokenUtil.generateToken(jwtInfo);
      jwtRefreshInfo = new JWTInfo(userId, userEmail, userMobile, idCardNo, userAccount,
          userRealName, null);
      refreshToken = jwtTokenUtil.generateRefreshToken(jwtRefreshInfo);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseMsg(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getMsg());
    }
    IJWTInfo userInfo = userAuthUtil.getInfoFromToken(token);
    AuthResp authResp = new AuthResp();
    authResp.setSsoUserNo(baseLoginEntity.getSsoUserNo());
    authResp.setAccessToken(token);
    authResp.setUserId(userId);
    authResp.setRefreshToken(refreshToken);
    authResp.setUserAccount(userAccount);
    authResp.setExp(userInfo.getExp());
    saveLoginInfo(jwtInfo, jwtRefreshInfo, loginSystem);
    jwtInfo.setExp(userInfo.getExp());
    tokenCache.put(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + token,
        JSON.toJSONString(jwtInfo, false), expire, TimeUnit.SECONDS);
    tokenCache.put(RedisKeyConstant.REDIS_KEY_REFRESH_TOKEN + ":" + refreshToken,
        JSON.toJSONString(jwtInfo, false), refreshExpire, TimeUnit.SECONDS);
    return new ResponseMsg(authResp);
  }

  private void saveLoginInfo(JWTInfo jwtInfo, JWTInfo refresgJwtInfo, Integer loginSystem)
      throws Exception {
    //final UserAgent userAgent = UserAgent
    //        .parseUserAgentString(WebUtils.getRequest().getHeader("User-Agent"));
    //final String ip = IpUtils.getRemoteIP(WebUtils.getRequest());
    //LoginInfoValueObject onlineLog = new LoginInfoValueObject();
    //
    //// 获取客户端操作系统
    //String os = userAgent.getOperatingSystem().getName();
    //
    //// 获取客户端浏览器
    //String browser = userAgent.getBrowser().getName();
    //onlineLog.setBrowser(browser);
    //onlineLog.setIpaddr(ip);
    //onlineLog.setRefreshTokenId(refresgJwtInfo.getTokenId());
    //onlineLog.setLoginTime(System.currentTimeMillis());
    //onlineLog.setUserId(jwtInfo.getUserId());
    //onlineLog.setUserAccount(jwtInfo.getUserAccount());
    //onlineLog.setOs(os);
    //onlineLog.setUserEmail(jwtInfo.getUserEmail());
    //onlineLog.setUserMobile(jwtInfo.getUserMobile());
    //onlineLog.setIdCardNo(jwtInfo.getIdCardNo());
    //onlineLog.setUserRealName(jwtInfo.getUserRealName());
    //记录登录日志
    AuthLoginLogEntity authLoginLogEntity = new AuthLoginLogEntity();
    authLoginLogEntity.setId(createId());
    authLoginLogEntity.setUserId(jwtInfo.getUserId());
    authLoginLogEntity.setDefaultInfo(jwtInfo.getUserId(), jwtInfo.getUserAccount());
    String machineIp = InetAddress.getLocalHost().getHostAddress();
    authLoginLogEntity.setIp(machineIp);
    authLoginLogEntity.setLoginSystem(loginSystem);
    authLoginLogRepository.insert(authLoginLogEntity);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg register(AuthUserComDto authUserComDto) {
    //查询数据库进行校验
    ResponseMsg validateMsg = this.validateAdd(authUserComDto);
    if (ResponseMsg.isNotSuccess(validateMsg)) {
      return validateMsg;
    }
    AuthUnifiedUserEntity authUnifiedUserEntity = new AuthUnifiedUserEntity();
    authUnifiedUserEntity.setId(createId());
    authUnifiedUserEntity.registerSet(authUnifiedUserEntity, authUserComDto);
    authUnifiedUserEntity.setSsoUserNo(UUID.randomUUID().toString().replace("-", ""));
    setCreateUser(authUnifiedUserEntity);
    authLoginRepository.insert(authUnifiedUserEntity);
    this.addThirdSSoUser(authUnifiedUserEntity);
    return new ResponseMsg(authUnifiedUserEntity);
  }

  /**
   * 异步将用户同步到业务系统
   */
  private void addThirdSSoUser(AuthUnifiedUserEntity authUnifiedUserEntity) {
    this.addPlatUserSynchronous(authUnifiedUserEntity);
    this.addPortalUserSynchronous(authUnifiedUserEntity);
    this.addMiniUserSynchronous(authUnifiedUserEntity, CommonConstant.MINI_SAVE_SSO_USER);
  }


  /**
   * 同步到小程序平台
   */
  private void addMiniUserSynchronous(AuthUnifiedUserEntity authUnifiedUserEntity, String path) {
    log.error(" AuthServiceImpl.addMiniUserSynchronous  ");
    ThreadPoolUtils.getPool().execute(() -> {
      try {
        this.saveMiniUser(authUnifiedUserEntity, path);
      } catch (Exception e) {
        e.printStackTrace();
        log.error("AuthServiceImpl.addMiniUserSynchronous 异常 " + e);
      }
    });
  }

  /**
   * 同步到运营平台
   */
  private void addPlatUserSynchronous(AuthUnifiedUserEntity authUnifiedUserEntity) {
    log.error(" AuthServiceImpl.addPlatUserSynchronous  ");
    ThreadPoolUtils.getPool().execute(() -> {
      try {
        this.addPlatUser(authUnifiedUserEntity);
      } catch (Exception e) {
        e.printStackTrace();
        log.error("AuthServiceImpl.addPlatUserSynchronous 异常 " + e);
      }
    });
  }

  /**
   * 同步到运营平台
   */
  private void addPortalUserSynchronous(AuthUnifiedUserEntity authUnifiedUserEntity) {
    log.error(" AuthServiceImpl.addPortalUserSynchronous  ");
    ThreadPoolUtils.getPool().execute(() -> {
      try {
        this.addPortalUser(authUnifiedUserEntity);
      } catch (Exception e) {
        e.printStackTrace();
        log.error("AuthServiceImpl.addPortalUserSynchronous 异常 " + e);
      }
    });
  }

  /**
   * 同步到小程序平台
   */
  private void saveMiniUser(AuthUnifiedUserEntity authUnifiedUserEntity, String path)
      throws IOException {
    Map<String, String> headers = HttpClientUtil.reqHeader(miniFormUser, miniFormPass);
    String url = miniFormUrl;
    Map<String, Object> param = this.addMiniUserData(authUnifiedUserEntity);
    HttpClientUtil.postByMap(url + path, param, headers);
  }

  /**
   * 同步到运营系统
   */
  private void addPlatUser(AuthUnifiedUserEntity authUnifiedUserEntity) throws IOException {
    String sendData = this.addPlatUserData(authUnifiedUserEntity);
    String path = CommonConstant.PLAT_SAVE_SSO_USER;
    // 运营平台
    HttpClientUtil.postByJson(plateFormUrl + path, sendData, plateFormKey);
  }

  /**
   * 同步到门户后台
   */
  private void addPortalUser(AuthUnifiedUserEntity authUnifiedUserEntity) throws IOException {
    String sendData = this.addPlatUserData(authUnifiedUserEntity);
    String path = CommonConstant.PORTAL_SAVE_SSO_USER;
    // 门户后台
    HttpClientUtil.postByJsonPortal(portalUrl + path, sendData, plateFormKey);
  }

  private Map<String, Object> addMiniUserData(AuthUnifiedUserEntity authUnifiedUserEntity) {
    Map<String, Object> userTherapist = new HashMap<>();
    userTherapist.put("recom", 0);
    userTherapist.put("type", "1");
    userTherapist.put("userId", authUnifiedUserEntity.getUserAccount());
    userTherapist.put("name", authUnifiedUserEntity.getUserRealName());
    userTherapist.put("phone", authUnifiedUserEntity.getUserMobile());
    userTherapist.put("ssoUserNo", authUnifiedUserEntity.getSsoUserNo());
    Map<String, Object> param = new HashMap<>();
    param.put("userTherapist", userTherapist);
    return param;
  }

  private String addPlatUserData(AuthUnifiedUserEntity authUnifiedUserEntity) {
    ApiDataRequestMsg apiDataRequestMsg = new ApiDataRequestMsg();
    apiDataRequestMsg.setRequestId(UUID.randomUUID().toString().replace("-", ""));
    apiDataRequestMsg.setTimestamp(System.currentTimeMillis());
    apiDataRequestMsg.setAccountId(plateFormAccountId);
    String key = plateFormKey;
    Map dataMap = new HashMap();
    dataMap.put("userAccount", authUnifiedUserEntity.getUserAccount());
    dataMap.put("userRealName", authUnifiedUserEntity.getUserRealName());
    dataMap.put("userEmail", authUnifiedUserEntity.getUserEmail());
    dataMap.put("userMobile", authUnifiedUserEntity.getUserMobile());
    dataMap.put("userType", AuthUserComDto.USER_TYPE_SSO);
    dataMap.put("ssoUserNo", authUnifiedUserEntity.getSsoUserNo());
    dataMap.put("id", authUnifiedUserEntity.getId());
    String dataContent = null;
    ObjectMapper jacksonObjectMapper = new ObjectMapper();
    try {
      dataContent = jacksonObjectMapper.writeValueAsString(dataMap);
    } catch (Exception e) {
      log.error("dataContent parse error:", e);
    }
    log.info("dataContent ===  " + dataContent);
    String data = AESUtil.encryptWithKey(dataContent, key);
    apiDataRequestMsg.setData(data);
    String str =
        "account_id=" + apiDataRequestMsg.getAccountId() + "&request_id=" + apiDataRequestMsg
            .getRequestId() + "&data=" + data + "&timestamp=" + apiDataRequestMsg.getTimestamp();
    String sign = MD5Utils.getMD5(str + "&key=" + key, "UTF-8");
    apiDataRequestMsg.setSign(sign);
    String sendData = null;
    try {
      sendData = jacksonObjectMapper.writeValueAsString(apiDataRequestMsg);
    } catch (Exception e) {
      log.error("sendData parse error:", e);
    }
    return sendData;
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg updateAuthUser(AuthUserComDto authUserComDto) {
    AuthUnifiedUserEntity authUnifiedUserEntity = authLoginRepository
        .selectById(authUserComDto.getUserId());
    ResponseMsg validateMsg = this.validateUpdate(authUserComDto, authUnifiedUserEntity);
    if (ResponseMsg.isNotSuccess(validateMsg)) {
      return validateMsg;
    }
    authUnifiedUserEntity
        .setUpdateDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setUserType(authUserComDto.getUserType());
    authUnifiedUserEntity.setUserEmail(authUserComDto.getUserEmail());
    authUnifiedUserEntity.setUserMobile(authUserComDto.getUserMobile());
    authUnifiedUserEntity.setUserAccount(authUserComDto.getUserAccount());
    authUnifiedUserEntity.setUserRealName(authUserComDto.getUserRealName());
    authUnifiedUserEntity.setIdCardNo(authUserComDto.getIdCardNo());
    setUpdateUser(authUnifiedUserEntity);
    authLoginRepository.updateById(authUnifiedUserEntity);
    this.updateThirdSSoUser(authUnifiedUserEntity);

    return new ResponseMsg(authUnifiedUserEntity.getId());
  }

  /**
   * 异步将用户同步到业务系统
   */
  private void updateThirdSSoUser(AuthUnifiedUserEntity authUnifiedUserEntity) {
    this.addPlatUserSynchronous(authUnifiedUserEntity);
    this.addPortalUserSynchronous(authUnifiedUserEntity);
    this.addMiniUserSynchronous(authUnifiedUserEntity, CommonConstant.MINI_UPDATE_SSO_USER);
  }

  /**
   * @description
   * @method validateAdd 验证新增会员参数
   * @date: 2022/4/10 21:31
   * @author: chensy
   */
  private ResponseMsg validateAdd(AuthUserComDto authUserComDto) {
    QueryWrapper<AuthUnifiedUserEntity> mobileWrapper = new QueryWrapper();
    if (authUserComDto.getUserType() != null) {
      mobileWrapper.eq("user_type", authUserComDto.getUserType());
    }
    mobileWrapper.eq("user_mobile", authUserComDto.getUserMobile());
    mobileWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    int countByMobile = authLoginRepository.selectCount(mobileWrapper);
    if (countByMobile > 0) {
      return new ResponseMsg(AuthRespCodeEnum.MOBILE_EXIST_ERROR.getCode(),
          AuthRespCodeEnum.MOBILE_EXIST_ERROR.getMsg());
    }
    QueryWrapper<AuthUnifiedUserEntity> accountWrapper = new QueryWrapper();
    accountWrapper.eq("user_account", authUserComDto.getUserAccount());
    accountWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    if (authUserComDto.getUserType() != null) {
      accountWrapper.eq("user_type", authUserComDto.getUserType());
    }
    int countByAccount = authLoginRepository.selectCount(accountWrapper);
    if (countByAccount > 0) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_EXIST_ERROR.getCode(),
          AuthRespCodeEnum.ACCOUNT_EXIST_ERROR.getMsg());
    }
    return new ResponseMsg();
  }


  /**
   * @description
   * @method validateAdd 验证修改会员参数
   * @date: 2022/4/10 21:31
   * @author: chensy
   */
  private ResponseMsg validateUpdate(AuthUserComDto authUserComDto,
      AuthUnifiedUserEntity authUnifiedUserEntity) {
    if (null == authUnifiedUserEntity) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getCode(),
          AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getMsg());
    }
    //查询数据库进行校验
    QueryWrapper<AuthUnifiedUserEntity> mobileWrapper = new QueryWrapper();
    mobileWrapper.eq("user_mobile", authUserComDto.getUserMobile());
    mobileWrapper.ne("local_no", authUserComDto.getLocalNo());
    mobileWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    if (authUserComDto.getUserType() != null) {
      mobileWrapper.eq("user_type", authUserComDto.getUserType());
    }
    int countByMobile = authLoginRepository.selectCount(mobileWrapper);
    if (countByMobile > 0) {
      return new ResponseMsg(AuthRespCodeEnum.MOBILE_EXIST_ERROR.getCode(),
          AuthRespCodeEnum.MOBILE_EXIST_ERROR.getMsg());
    }
    QueryWrapper<AuthUnifiedUserEntity> accountWrapper = new QueryWrapper();
    accountWrapper.eq("user_account", authUserComDto.getUserAccount());
    accountWrapper.ne("local_no", authUserComDto.getLocalNo());
    accountWrapper.eq("data_status", DataStatusEnum.ENABLE.getCode());
    if (authUserComDto.getUserType() != null) {
      accountWrapper.eq("user_type", authUserComDto.getUserType());
    }
    int countByAccount = authLoginRepository.selectCount(accountWrapper);
    if (countByAccount > 0) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_EXIST_ERROR.getCode(),
          AuthRespCodeEnum.ACCOUNT_EXIST_ERROR.getMsg());
    }
    return new ResponseMsg();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg updateAuthUserStatus(AuthUserComDto authUserComDto) {
    AuthUnifiedUserEntity authUnifiedUserEntity = authLoginRepository
        .selectById(authUserComDto.getUserId());
    if (authUnifiedUserEntity == null) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getCode(),
          AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getMsg());
    }
    authUnifiedUserEntity
        .setUpdateDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setDataStatus(authUserComDto.getDataStatus());
    authLoginRepository.updateById(authUnifiedUserEntity);
    return new ResponseMsg(authUnifiedUserEntity.getId());
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg resetUserPwd(AuthUserComDto authUserComDto) {
    AuthUnifiedUserEntity authUnifiedUserEntity = authLoginRepository
        .selectById(authUserComDto.getUserId());
    if (authUnifiedUserEntity == null) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getCode(),
          AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getMsg());
    }
    String pwd = String.valueOf(PasswordUtils.randomPwd());
    authUnifiedUserEntity
        .setUpdateDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setSalt(PasswordUtils.getSalt());
    String encode = PasswordUtils.encode(pwd, authUnifiedUserEntity.getSalt());
    authUnifiedUserEntity.setLoginPassword(encode);
    authLoginRepository.updateById(authUnifiedUserEntity);
    authUnifiedUserEntity.setPassWord(pwd);
    return new ResponseMsg(authUnifiedUserEntity);
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseMsg updateUserPwd(AuthUserComDto authUserComDto) {
    AuthUnifiedUserEntity authUnifiedUserEntity = authLoginRepository
        .selectById(authUserComDto.getUserId());
    if (authUnifiedUserEntity == null) {
      return new ResponseMsg(AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getCode(),
          AuthRespCodeEnum.ACCOUNT_NOT_EXIST.getMsg());
    }
    if (!PasswordUtils.matches(authUnifiedUserEntity.getSalt(), authUserComDto.getOldPwd(),
        authUnifiedUserEntity.getLoginPassword())) {
      return new ResponseMsg(AuthRespCodeEnum.OLD_PASSWORD_ERROR.getCode(),
          AuthRespCodeEnum.OLD_PASSWORD_ERROR.getMsg());
    }
    authUnifiedUserEntity
        .setUpdateDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setSalt(PasswordUtils.getSalt());
    String encode = PasswordUtils
        .encode(authUserComDto.getNewPwd(), authUnifiedUserEntity.getSalt());
    authUnifiedUserEntity.setLoginPassword(encode);
    authLoginRepository.updateById(authUnifiedUserEntity);
    return new ResponseMsg(authUnifiedUserEntity.getId());
  }
}
