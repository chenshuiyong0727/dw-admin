package com.auth.common.infrastructure.exception;

import com.auth.common.infrastructure.constant.CommonConstants;
import com.hf.common.infrastructure.exception.BaseException;

/**
 * @author 曾仕斌
 * @function Token异常
 * @date 2021/3/31
 */
public class UserTokenException extends BaseException {

  public UserTokenException(String message) {
    super(message, CommonConstants.EX_USER_INVALID_CODE);
  }
}
