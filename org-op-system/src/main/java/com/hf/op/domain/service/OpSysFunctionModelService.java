package com.hf.op.domain.service;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.resp.OpRespCodeEnum;
import com.open.api.dto.FunctionDto;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author 梁灿龙
 * @function
 * @date 2022/5/16
 **/
public class OpSysFunctionModelService {

  public static ResponseMsg haveFunction(String requestUri, List<FunctionDto> functions) {
    if (CollectionUtils.isNotEmpty(functions)) {
      for (FunctionDto function : functions) {
        if (requestUri.equals(function.getFunctionKey())) {
          return new ResponseMsg();
        }
      }
    }
    return new ResponseMsg(OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getCode(),
        OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getMsg());
  }

  /**
   * @description 免检权限
   * @method haveFunction
   * @date: 2022/5/16 11:14
   * @author:liangcanlong
   */
  public static ResponseMsg exemptFunction(String requestUri, List<FunctionDto> functions) {
    if (CollectionUtils.isNotEmpty(functions)) {
      for (FunctionDto function : functions) {
        if (requestUri.equals(function.getFunctionKey())) {
          return new ResponseMsg();
        }
      }
    }
    return new ResponseMsg(OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getCode(),
        OpRespCodeEnum.USER_NOT_HAVE_RIGHT.getMsg());
  }

}
