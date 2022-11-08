package com.auth.common.infrastructure.util.jwt;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.auth.common.infrastructure.config.UserAuthConfig;
import com.auth.common.infrastructure.exception.UserTokenException;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthUtil {

  private static final String REDIS_USER_PUB_KEY = "CLOUD_V1:AUTH:JWT:PUB";

  private UserAuthConfig userAuthConfig;


  @CreateCache(name = CommCacheConst.BASE_KEY_ORG + "authKeyCache", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, String> keyCache;

  public UserAuthUtil(UserAuthConfig userAuthConfig) {
    this.userAuthConfig = userAuthConfig;
  }

  public IJWTInfo getInfoFromToken(String token) throws Exception {
    try {
      String pubKeyStr = keyCache.get(REDIS_USER_PUB_KEY);
      if (null == userAuthConfig.getPubKeyByte() && StringUtilLocal.isNotEmpty(pubKeyStr)) {
        byte[] pubKey = RsaKeyHelper.toBytes(pubKeyStr);
        userAuthConfig.setPubKeyByte(pubKey);
      }
      return JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
    } catch (ExpiredJwtException ex) {
      throw new UserTokenException("User token expired!");
    } catch (SignatureException ex) {
      throw new UserTokenException("User token signature error!");
    } catch (IllegalArgumentException ex) {
      throw new UserTokenException("User token is null or empty!");
    }
  }
}
