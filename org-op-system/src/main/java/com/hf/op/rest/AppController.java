package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.user.UserDropDownVo;
import com.hf.op.service.inf.OpSysUserService;
import com.xxl.sso.core.entity.ReturnT;
import com.xxl.sso.core.login.SsoTokenLoginHelper;
import com.xxl.sso.core.store.SsoLoginStore;
import com.xxl.sso.core.store.SsoSessionIdHelper;
import com.xxl.sso.core.user.XxlSsoUser;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * sso server (for app)
 *
 * @author xuxueli 2018-04-08 21:02:54
 */
@Controller
@RequestMapping("/v1/thirdAppLogin")
public class AppController {


  private OpSysUserService opSysUserServiceImpl;

  public AppController(OpSysUserService opSysUserServiceImpl) {
    this.opSysUserServiceImpl = opSysUserServiceImpl;
  }

  /**
   * Login
   */
  @RequestMapping("/login")
  @ResponseBody
  public ResponseMsg login(String username, String password) {

    // valid login
    ResponseMsg result = opSysUserServiceImpl.findUser(username, password);
    if (ResponseMsg.isNotSuccess(result)) {
      return result;
    }
    UserDropDownVo userInfo = (UserDropDownVo) result.getData();

    // 1、make xxl-sso user
    XxlSsoUser xxlUser = new XxlSsoUser();
    xxlUser.setUserid(String.valueOf(userInfo.getUserId()));
    xxlUser.setUsername(userInfo.getUserName());
    xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
    xxlUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
    xxlUser.setExpireFreshTime(System.currentTimeMillis());

    // 2、generate sessionId + storeKey
    String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

    // 3、login, store storeKey
    SsoTokenLoginHelper.login(sessionId, xxlUser);

    // 4、return sessionId
    return new ResponseMsg(sessionId);
  }


  /**
   * Logout
   */
  @RequestMapping("/logout")
  @ResponseBody
  public ReturnT<String> logout(String sessionId) {
    // logout, remove storeKey
    SsoTokenLoginHelper.logout(sessionId);
    return ReturnT.SUCCESS;
  }

  /**
   * logincheck
   */
  @RequestMapping("/logincheck")
  @ResponseBody
  public ReturnT<XxlSsoUser> logincheck(String sessionId) {

    // logout
    XxlSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
    if (xxlUser == null) {
      return new ReturnT<XxlSsoUser>(ReturnT.FAIL_CODE, "sso not login.");
    }
    return new ReturnT<XxlSsoUser>(xxlUser);
  }

}
