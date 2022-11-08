package com.open.api.util;

import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 曾仕斌
 * @function 全局异常处理
 * @date 2021/2/7
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseMsg exceptionHandler(Exception e) {
    log.error("GlobalExceptionHandler.exceptionHandler:" + e.getMessage(), e);
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getMsg());
  }
}
