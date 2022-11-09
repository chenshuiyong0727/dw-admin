package com.hf.op.infrastructure.generator.utils;

import com.hf.common.infrastructure.exception.ServiceException;
import com.hf.common.infrastructure.resp.BaseMsg;

/**
 * 抛出异常工具类
 *
 * @author Parker
 * @date 2021年5月7日18:32:32
 */
public final class ThrowExceptionUtil {

  private ThrowExceptionUtil() {
  }

  /**
   * 直接抛出异常
   *
   * @param msg 异常信息
   */
  public static void throwException(BaseMsg msg) {
    throw new ServiceException(msg);
  }

  /**
   * 判断是否抛出异常
   *
   * @param msg 异常信息
   */
  public static void isThrowException(boolean isError, BaseMsg msg) {
    if (isError) {
      throw new ServiceException(msg);
    }
  }
}
