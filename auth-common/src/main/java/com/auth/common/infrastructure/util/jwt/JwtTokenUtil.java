package com.auth.common.infrastructure.util.jwt;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.infrastructure.config.KeyConfiguration;
import com.auth.common.infrastructure.config.UserAuthConfig;
import com.hf.common.infrastructure.util.StringUtilLocal;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 曾仕斌
 * @function Jwt工具类
 * @date 2021/2/24
 */
@Component
@RefreshScope
@Slf4j
public class JwtTokenUtil {


  private static final String REDIS_USER_PRI_KEY = "CLOUD_V1:AUTH:JWT:PRI";

  private static final String REDIS_USER_PUB_KEY = "CLOUD_V1:AUTH:JWT:PUB";

  @Value("${expire}")
  private int expire;

  @Value("${refreshExpire}")
  private int refreshExpire;

  @CreateCache(name = "authKeyCache", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, String> keyCache;

  private UserAuthConfig userAuthConfig;

  /**
   * 公私钥配置
   */
  private KeyConfiguration keyConfiguration;

  public JwtTokenUtil(UserAuthConfig userAuthConfig, KeyConfiguration keyConfiguration) {
    this.userAuthConfig = userAuthConfig;
    this.keyConfiguration = keyConfiguration;
  }

  /**
   * 生成token
   */
  public String generateToken(IJWTInfo jwtInfo) throws Exception {
    byte[] userPriKey = keyConfiguration.getUserPriKey();
    if (userPriKey == null) {
      this.setKey();
    }
    userPriKey = keyConfiguration.getUserPriKey();
    return JWTHelper.generateToken(jwtInfo, userPriKey, expire);
  }

  private void setKey() {
    try {
      if (StringUtilLocal.isNotEmpty(keyCache.get(REDIS_USER_PRI_KEY)) && StringUtilLocal
          .isNotEmpty(keyCache.get(REDIS_USER_PUB_KEY))) {
        byte[] pri = RsaKeyHelper.toBytes(keyCache.get(REDIS_USER_PRI_KEY));
        byte[] pub = RsaKeyHelper.toBytes(keyCache.get(REDIS_USER_PUB_KEY));
        keyConfiguration.setUserPriKey(pri);
        keyConfiguration.setUserPubKey(pub);
      } else {
        Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
        keyConfiguration.setUserPriKey(keyMap.get("pri"));
        keyConfiguration.setUserPubKey(keyMap.get("pub"));
        keyCache.put(REDIS_USER_PRI_KEY, RsaKeyHelper.toHexString(keyMap.get("pri")));
        keyCache.put(REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(keyMap.get("pub")));
      }
      refreshUserPubKey();
    } catch (Exception e) {
      log.error("初始化加载用户pubKey失败,1分钟后自动重试!", e);
    }
  }

  private void refreshUserPubKey() {
    this.userAuthConfig.setPubKeyByte(keyConfiguration.getUserPubKey());
  }

  /**
   * 解析token
   */
  public IJWTInfo getInfoFromToken(String token) throws Exception {
    byte[] userPubKey = keyConfiguration.getUserPubKey();
    if (userPubKey == null) {
      this.setKey();
    }
    userPubKey = keyConfiguration.getUserPubKey();
    return JWTHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
  }

  /**
   * 生成用于刷新的TOKEN
   */
  public String generateRefreshToken(IJWTInfo jwtInfo) throws Exception {
    byte[] userPriKey = keyConfiguration.getUserPriKey();
    if (userPriKey == null) {
      this.setKey();
    }
    userPriKey = keyConfiguration.getUserPriKey();
    return JWTHelper.generateToken(jwtInfo, userPriKey, refreshExpire);
  }


}
