package com.hf.common.infrastructure.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储业务上下文内容,往本地线程空间存放数据
 *
 * @author :曾仕斌
 * @date 2022-03-10
 */
public class ThreadLocalContext {

  @SuppressWarnings("unchecked")
  private static ThreadLocal threadLocal = new ThreadLocal() {

    @Override
    protected synchronized Map<String, Object> initialValue() {
      return new HashMap<String, Object>();
    }
  };

  @SuppressWarnings("unchecked")
  public static Map<String, Object> get() {
    return (Map<String, Object>) threadLocal.get();
  }

  public static String getStrValue(String key) {
    Object val = ThreadLocalContext.get().get(key);
    if (null == val) {
      return null;
    }
    return val.toString();
  }

}
