package com.hf.op.infrastructure.config;

import com.hf.op.infrastructure.util.SsoTokenHelper;
import com.xxl.sso.core.store.SsoLoginStore;
import com.xxl.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuxueli 2018-04-03 20:41:07
 */
@Configuration
public class XxlSsoConfig implements InitializingBean, DisposableBean {

  @Value("${xxl.sso.redis.address}")
  private String redisAddress;

  @Value("${expire}")
  private int expire;

  @Override
  public void afterPropertiesSet() throws Exception {
    int redisExpireMinite = expire / 60;
    SsoLoginStore.setRedisExpireMinite(redisExpireMinite);
    SsoTokenHelper.setRedisExpireMinite(redisExpireMinite);
    JedisUtil.init(redisAddress);
  }

  @Override
  public void destroy() throws Exception {
    JedisUtil.close();
  }

}
