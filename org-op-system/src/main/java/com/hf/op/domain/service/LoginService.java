package com.hf.op.domain.service;

import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.op.domain.model.login.LoginValueObject;
import com.hf.op.infrastructure.resp.AuthRespCodeComEnum;
import com.hf.op.infrastructure.resp.LoginAccountTypeComEnum;

/**
 * @author 曾仕斌
 * @function 登录业务
 * @date 2021/11/9
 */
public class LoginService {

  /**
   * 远程登录校验
   */
  public static ResponseMsg remoteLoginCheck(LoginValueObject loginValueObject) {
    assert loginValueObject != null;
    String loginAccount = loginValueObject.getLoginAccount();
    String pwd = loginValueObject.getLoginPassword();
    String veryCode = loginValueObject.getVerifyCode();
    Integer accountType = loginValueObject.getAccountType();
    assert (null != accountType && StringUtilLocal.isNotEmpty(loginAccount));
    if (accountType == LoginAccountTypeComEnum.USER_NAME.getCode()) {
      if (StringUtilLocal.isEmpty(loginAccount)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else {
      if (StringUtilLocal.isEmpty(loginAccount) || StringUtilLocal
          .isEmpty(veryCode)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    }
    if (loginValueObject.getAccountType().intValue() == LoginAccountTypeComEnum.USER_NAME
        .getCode()
        || loginValueObject.getAccountType().intValue() == LoginAccountTypeComEnum.ID_CARD
        .getCode()) {
      if (StringUtilLocal.isEmpty(pwd)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else if (loginValueObject.getAccountType().intValue() != LoginAccountTypeComEnum.MOBILE
        .getCode()) {

      //其他登录方式当前暂时不支持
      return new ResponseMsg(AuthRespCodeComEnum.LOGIN_TYPE_NOT_SUPPORT.getCode(),
          AuthRespCodeComEnum.LOGIN_TYPE_NOT_SUPPORT.getMsg());
    }
    return new ResponseMsg(1000, "验证成功");
  }

  /**
   * 远程登录校验
   */
  public static ResponseMsg remoteLoginCheckH5(LoginValueObject loginValueObject) {
    assert loginValueObject != null;
    String loginAccount = loginValueObject.getLoginAccount();
    String pwd = loginValueObject.getLoginPassword();
//    String veryCode = loginValueObject.getVerifyCode();
    Integer accountType = loginValueObject.getAccountType();
    assert (null != accountType && StringUtilLocal.isNotEmpty(loginAccount));
    if (accountType == LoginAccountTypeComEnum.USER_NAME.getCode()) {
      if (StringUtilLocal.isEmpty(loginAccount)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else {
      if (StringUtilLocal.isEmpty(loginAccount)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    }
    if (loginValueObject.getAccountType().intValue() == LoginAccountTypeComEnum.USER_NAME
        .getCode()
        || loginValueObject.getAccountType().intValue() == LoginAccountTypeComEnum.ID_CARD
        .getCode()) {
      if (StringUtilLocal.isEmpty(pwd)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else if (loginValueObject.getAccountType().intValue() != LoginAccountTypeComEnum.MOBILE
        .getCode()) {

      //其他登录方式当前暂时不支持
      return new ResponseMsg(AuthRespCodeComEnum.LOGIN_TYPE_NOT_SUPPORT.getCode(),
          AuthRespCodeComEnum.LOGIN_TYPE_NOT_SUPPORT.getMsg());
    }
    return new ResponseMsg(1000, "验证成功");
  }
}
