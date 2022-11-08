package com.hf.op.infrastructure.run;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.infrastructure.config.KeyConfiguration;
import com.auth.common.infrastructure.config.UserAuthConfig;
import com.auth.common.infrastructure.util.jwt.RsaKeyHelper;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {

  private static final String REDIS_USER_PRI_KEY = "CLOUD_V1:AUTH:JWT:PRI";

  private static final String REDIS_USER_PUB_KEY = "CLOUD_V1:AUTH:JWT:PUB";

  private UserAuthConfig userAuthConfig;

  private KeyConfiguration keyConfiguration;

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "authKeyCache", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, String> keyCache;

  public AuthClientRunner(UserAuthConfig userAuthConfig, KeyConfiguration keyConfiguration) {
    this.userAuthConfig = userAuthConfig;
    this.keyConfiguration = keyConfiguration;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("初始化加载用户pubKey");
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
}
