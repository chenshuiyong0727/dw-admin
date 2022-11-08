package com.hf.op.rest;

import com.hf.common.infrastructure.resp.BaseController;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.op.infrastructure.dto.auth.SsoRqDto;
import com.hf.op.infrastructure.model.AuthorityMenu;
import com.hf.op.infrastructure.model.OpenAuthority;
import com.hf.op.infrastructure.util.SsoTokenHelper;
import com.open.api.dto.FunctionDto;
import com.open.api.resp.ApiRespCodeEnum;
import com.xxl.sso.core.user.XxlSsoUser;
import com.xxl.sso.core.util.JedisUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chensy
 * @function 组织中台用户信息
 * @Date 2022/02/16
 */
@RestController
@RequestMapping("v1/sso")
@RefreshScope
@Slf4j
public class OpSsoController extends BaseController {

  public OpSsoController() {
  }

  /**
   * 通过sessionId得到用户信息
   */
  @RequestMapping(value = "getUserBySessionId", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getUserBySessionId(@RequestBody SsoRqDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(dto.getSessionId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    XxlSsoUser xxlUser = SsoTokenHelper.loginCheck(dto.getSessionId());
    if (xxlUser == null || StringUtilLocal.isEmpty(xxlUser.getUserid())) {
      return new ResponseMsg(ApiRespCodeEnum.SESSION_NOT_EXIST_OR_EXPIRE.getCode(),
          ApiRespCodeEnum.SESSION_NOT_EXIST_OR_EXPIRE.getMsg());
    }
    ResponseMsg result = new ResponseMsg();
    result.suc(xxlUser);
    return result;
  }

  /**
   * 通过sessionId退出登录
   */
  @RequestMapping(value = "logoutBySessionId", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg logoutBySessionId(@RequestBody SsoRqDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(dto.getSessionId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    SsoTokenHelper.logout(dto.getSessionId());
    ResponseMsg result = new ResponseMsg();
    result.suc();
    return result;
  }

  /**
   * 通过key得到权限列表
   */
  @RequestMapping(value = "getFunctions", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getFunctions(@RequestBody SsoRqDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(dto.getRedisKey(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    List<FunctionDto> functionDtos = (List<FunctionDto>) JedisUtil
        .getObjectValue(dto.getRedisKey());
    ResponseMsg result = new ResponseMsg();
    result.suc(functionDtos);
    return result;
  }

  /**
   * 通过key得到权限列表
   */
  @RequestMapping(value = "getOpenAuthoritys", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getOpenAuthoritys(@RequestBody SsoRqDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(dto.getRedisKey(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    List<OpenAuthority> list = (List<OpenAuthority>) JedisUtil
        .getObjectValue(dto.getRedisKey());
    ResponseMsg result = new ResponseMsg();
    result.suc(list);
    return result;
  }

  /**
   * 通过key得到权限列表
   */
  @RequestMapping(value = "getAuthorityMenus", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getAuthorityMenus(@RequestBody SsoRqDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(dto.getRedisKey(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    List<AuthorityMenu> list = (List<AuthorityMenu>) JedisUtil
        .getObjectValue(dto.getRedisKey());
    ResponseMsg result = new ResponseMsg();
    result.suc(list);
    return result;
  }

}
