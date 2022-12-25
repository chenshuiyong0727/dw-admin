package com.hf.op.rest;

import com.hf.common.infrastructure.resp.BaseController;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.op.infrastructure.dto.auth.AuthLoginComDto;
import com.hf.op.infrastructure.dto.auth.AuthRefreshTokenDto;
import com.hf.op.service.inf.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wpq
 * @function 运营系统鉴权专用
 * @Date 2022/02/16
 */
@RestController
@RequestMapping("v1/auth")
@RefreshScope
@Slf4j
public class OpSysAuthController extends BaseController {

  public static final String numRegex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";

  private AuthService authServiceImpl;
  @Value("${token-header}")
  private String tokenHeader;

  public OpSysAuthController(AuthService authServiceImpl) {
    this.authServiceImpl = authServiceImpl;
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg createToken(@RequestBody AuthLoginComDto authLoginComDto) throws Exception {
    try {
      Assert.notNull(authLoginComDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(authLoginComDto.getLoginAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(authLoginComDto.getLoginPassword(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(authLoginComDto.getVerifyCode(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      authLoginComDto.setLoginPassword(decryptParam(authLoginComDto.getLoginPassword()));
      log.info("AuthController.createToken.begin:" + authLoginComDto.getLoginAccount());
      return authServiceImpl.login(authLoginComDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  @RequestMapping(value = "/loginH5", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg loginH5(@RequestBody AuthLoginComDto authLoginComDto) throws Exception {
    try {
      Assert.notNull(authLoginComDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(authLoginComDto.getLoginAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(authLoginComDto.getLoginPassword(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      authLoginComDto.setLoginPassword(decryptParam(authLoginComDto.getLoginPassword()));
      log.info("AuthController.createToken.begin:" + authLoginComDto.getLoginAccount());
      return authServiceImpl.loginH5(authLoginComDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @RequestMapping(value = "/token/refresh", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg refreshToken(@RequestBody AuthRefreshTokenDto authRefreshTokenDto)
      throws Exception {
    Assert.notNull(authRefreshTokenDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(authRefreshTokenDto.getRefreshToken(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(authRefreshTokenDto.getToken(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(authRefreshTokenDto.getUserId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    log.info("AuthController.createToken.begin:" + authRefreshTokenDto.getUserId());
    return authServiceImpl.refreshToken(authRefreshTokenDto);
  }

  /**
   * 权限校验
   */
  @RequestMapping(value = "/{userId}/checkFunction", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg checkFunction(@PathVariable("userId") Long userId, String requestMethod,
      String requestUri)
      throws Exception {
    assert null != userId && StringUtilLocal.isNotEmpty(requestUri) && StringUtilLocal
        .isNotEmpty(requestMethod);
    if (null == userId || StringUtilLocal.isEmpty(requestUri) || StringUtilLocal
        .isEmpty(requestMethod)) {
      return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
          BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
    }
    requestUri =
        StringUtilLocal.lowerCase(requestMethod) + ":" + requestUri.substring(1).replace("/", ":");
    //兼容 例如 uc/label/{id} 的请求方式
    String uris[] = requestUri.split(":");
    if (uris != null && uris.length > 0 && uris[uris.length - 1].matches(numRegex)) {
      String lastStr = uris[uris.length - 1];
      requestUri = requestUri.substring(0, requestUri.length() - lastStr.length());
    }
    return authServiceImpl.checkFunction(userId, requestUri);
  }
}
