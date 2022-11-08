package com.hf.op.domain.model.auth;

import com.alibaba.fastjson.JSON;
import com.auth.common.domain.LoginInfoValueObject;
import com.auth.common.infrastructure.resp.AuthRespCodeEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.StringUtilLocal;

/**
 * @author 曾仕斌
 * @function 刷新token服务
 * @date 2021/3/30
 */
public class RefreshTokenService {

  /**
   * 刷新Token基础校验
   */
  public static ResponseMsg refreshTokenBasicCheck(String refreshToken, String oldToken,
      Long loginId) {
    assert StringUtilLocal.isNotEmpty(refreshToken) && StringUtilLocal.isNotEmpty(oldToken)
        && null != loginId;
    if (StringUtilLocal.isEmpty(refreshToken) || StringUtilLocal.isEmpty(oldToken)
        || loginId == null) {
      return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
          BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
    }
    return new ResponseMsg();
  }

  /**
   * 判断是否可以刷新token
   */
  public static ResponseMsg isCanRefresh(Long loginId, String refreshTokenCache) {
    assert null != loginId && StringUtilLocal.isNotEmpty(refreshTokenCache);
    LoginInfoValueObject loginInfoValueObject = JSON
        .toJavaObject(JSON.parseObject(refreshTokenCache), LoginInfoValueObject.class);
    if (null == loginInfoValueObject) {
      return new ResponseMsg(AuthRespCodeEnum.REFRESH_TOKEN_NOT_EXIST_OR_EXPIRE.getCode(),
          AuthRespCodeEnum.REFRESH_TOKEN_NOT_EXIST_OR_EXPIRE.getMsg());
    }
    if (null == loginInfoValueObject.getUserId() || loginInfoValueObject
        .getUserId().longValue() != loginId.longValue()) {
      return new ResponseMsg(AuthRespCodeEnum.NOT_RIGHT_REFRESH_TOKEN.getCode(),
          AuthRespCodeEnum.NOT_RIGHT_REFRESH_TOKEN.getMsg());
    }
    return new ResponseMsg(loginInfoValueObject);
  }
}
