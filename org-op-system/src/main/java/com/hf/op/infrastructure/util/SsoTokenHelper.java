package com.hf.op.infrastructure.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hf.common.infrastructure.constant.SsoConstant;
import com.hf.op.infrastructure.model.AuthorityMenu;
import com.hf.op.infrastructure.model.OpenAuthority;
import com.open.api.dto.FunctionDto;
import com.xxl.sso.core.store.SsoLoginStore;
import com.xxl.sso.core.store.SsoSessionIdHelper;
import com.xxl.sso.core.user.XxlSsoUser;
import com.xxl.sso.core.util.JedisUtil;
import java.util.ArrayList;
import java.util.List;

public class SsoTokenHelper extends SsoLoginStore {

  private static int redisExpireMinite = 360;

  public static void setRedisExpireMinite(int redisExpireMinite) {
  /*  if (redisExpireMinite < 30) {
      redisExpireMinite = 30;
    }*/
    SsoTokenHelper.redisExpireMinite = redisExpireMinite;
  }

  private static String redisKey(String key, Integer systemId, String sessionId) {
    String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
    return key.concat("#").concat("systemId").concat("#").concat(systemId.toString()).concat("#")
        .concat(storeKey);
  }
  private static String redisKey(String key, Integer systemId) {
    return key.concat("#").concat("systemId").concat("#").concat(systemId.toString()).concat("#");
  }

  private static String redisKey(String key, String sessionId) {
    String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
    return key.concat("#").concat("systemId").concat("#")
        .concat(storeKey);
  }

  public static void putSystemIdList(String sessionId, List<Integer> list) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_SYSTEM, sessionId);
    JedisUtil.setObjectValue(redisKey, list, redisExpireMinite * 60);
  }

  public static List<Integer> getSystemIdList(String sessionId) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_SYSTEM, sessionId);
    List<Integer> list = (List<Integer>) JedisUtil.getObjectValue(redisKey);
    return list;
  }

  public static void putFunctions(Integer systemId, String sessionId,
      List<FunctionDto> opFunctions) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_OPFUN, systemId, sessionId);
    JedisUtil.setObjectValue(redisKey, opFunctions, redisExpireMinite * 60);
  }

  public static List<FunctionDto> getFunctions(Integer systemId, String sessionId) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_OPFUN, systemId, sessionId);
    List<FunctionDto> functionDtos = (List<FunctionDto>) JedisUtil.getObjectValue(redisKey);
    return functionDtos;
  }

  public static void putFunctions(Integer systemId,
      List<FunctionDto> opFunctions) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_ALL_OPFUN, systemId);
    JedisUtil.setObjectValue(redisKey, opFunctions, redisExpireMinite * 60 * 2 * 24 * 365);
  }

  public static List<FunctionDto> getFunctions(Integer systemId) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_ALL_OPFUN, systemId);
    List<FunctionDto> functionDtos = (List<FunctionDto>) JedisUtil.getObjectValue(redisKey);
    return functionDtos;
  }

  public static void putAuthorityMenus(String sessionId, List<AuthorityMenu> list) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_MINI_MENU, SsoConstant.SYSTEM_ID_MINI,
        sessionId);
    JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
    JedisUtil.setObjectValue(redisKey, array, redisExpireMinite * 60);
  }

  public static List<AuthorityMenu> getAuthorityMenus(String sessionId) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_MINI_MENU, SsoConstant.SYSTEM_ID_MINI,
        sessionId);
    List<AuthorityMenu> list = (List<AuthorityMenu>) JedisUtil.getObjectValue(redisKey);
    return list;
  }

  public static void putOpenAuthoritys(String sessionId, List<OpenAuthority> list) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_OPEN_AUTH, SsoConstant.SYSTEM_ID_MINI,
        sessionId);
    JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
    JedisUtil.setObjectValue(redisKey, array, redisExpireMinite * 60);
  }

  public static List<OpenAuthority> getOpenAuthoritys(String sessionId) {
    String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_OPEN_AUTH, SsoConstant.SYSTEM_ID_MINI,
        sessionId);
    List<OpenAuthority> list = (List<OpenAuthority>) JedisUtil.getObjectValue(redisKey);
    return list;
  }

  public static XxlSsoUser loginCheck(String sessionId) {
    String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
    if (storeKey == null) {
      return null;
    } else {
      XxlSsoUser xxlUser = SsoLoginStore.get(storeKey);
      if (xxlUser != null) {
        //String version = SsoSessionIdHelper.parseVersion(sessionId);
       // if (xxlUser.getVersion().equals(version)) {
          if (System.currentTimeMillis() - xxlUser.getExpireFreshTime() > (long) (
              xxlUser.getExpireMinite() / 2)) {
            xxlUser.setExpireFreshTime(System.currentTimeMillis());
            SsoTokenHelper.put(storeKey, xxlUser);
          }
        //}
        return xxlUser;
      }
      return null;
    }
  }

  public static void put(String storeKey, XxlSsoUser xxlUser) {
    String redisKey = redisKey(storeKey);
    JedisUtil.setObjectValue(redisKey, xxlUser, redisExpireMinite * 60);
  }

  private static String redisKey(String sessionId) {
    return "xxl_sso_sessionid".concat("#").concat(sessionId);
  }

  public static void logout(String sessionId) {
    String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
    if (storeKey != null) {
      SsoLoginStore.remove(storeKey);
    }
    List<Integer> systemIdList = SsoTokenHelper.getSystemIdList(sessionId);
    if (systemIdList != null && systemIdList.size() > 0) {
      for (Integer systemId : systemIdList) {
        if (SsoConstant.SYSTEM_ID_MINI.equals(systemId)) {
          String redisKey1 = redisKey(SsoConstant.REDIS_KEY_SSO_MINI_MENU,
              SsoConstant.SYSTEM_ID_MINI,
              sessionId);
          String redisKey2 = redisKey(SsoConstant.REDIS_KEY_SSO_OPEN_AUTH,
              SsoConstant.SYSTEM_ID_MINI,
              sessionId);
          JedisUtil.del(redisKey1);
          JedisUtil.del(redisKey2);
        } else {
          String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_OPFUN, systemId, sessionId);
          JedisUtil.del(redisKey);
        }
      }
      String redisKey = redisKey(SsoConstant.REDIS_KEY_SSO_SYSTEM, sessionId);
      JedisUtil.del(redisKey);
    }
  }

  public static void main(String[] args) throws JsonProcessingException {
    JedisUtil.init("redis://xxl-sso:hjjnHLW2022@redis-ce3cd66d-75ae-42da-be97-4e59cdfee093.cn-north-1.dcs.myhuaweicloud.com:6390/0");
  /*  String xxlSsoRedisAddress = "redis://xxl-sso:password@127.0.0.1:6379/0";
    xxlSsoRedisAddress = "redis://127.0.0.1:6379/0";
    init(xxlSsoRedisAddress);*/
    /*JedisUtil.setObjectValue("key", "666", 7200);
    System.out.println(JedisUtil.getObjectValue("key"));*/
   /* FunctionDto opFunction = new FunctionDto();
    opFunction.setFunctionDesc("中午");
    List<FunctionDto> opFunctions = new ArrayList<>();
    opFunctions.add(opFunction);
    SsoTokenHelper.putFunctions(SsoConstant.SYSTEM_ID_PLATE,
        "a891a8b3-b1e4-4085-9c89-06f94877d30f_ad9b6b47e04c44df8c4f5270af3489e2", opFunctions);
    List<FunctionDto> functionDtos = SsoTokenHelper.getFunctions(SsoConstant.SYSTEM_ID_PLATE,
        "a891a8b3-b1e4-4085-9c89-06f94877d30f_ad9b6b47e04c44df8c4f5270af3489e2");*/
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    String ID = "abcSystem_1234";
    SsoTokenHelper.putSystemIdList(ID, list);
    List<Integer> list1 = SsoTokenHelper.getSystemIdList(ID);
    System.out.println(list1);
  }
}
