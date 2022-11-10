package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.infrastructure.dto.user.QueryOpUserListDto;
import com.hf.op.infrastructure.dto.user.UpdateUserPwdDto;
import com.hf.op.service.inf.OpSysUserService;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wpq
 * @function 运营系统用户控制器
 * @Date 2022/02/16
 */
@RestController
@RequestMapping("v1/sys/users/")
@Slf4j
public class OpSysUserController {

  private OpSysUserService opSysUserServiceImpl;

  @Value("${tokenHeader}")
  private String tokenHeader;

  public OpSysUserController(OpSysUserService opSysUserServiceImpl) {
    this.opSysUserServiceImpl = opSysUserServiceImpl;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getUser(@PathVariable(value = "id") Long id) throws Exception {
    assert id != null;
    OpSysUserEntity OpSysUserEntity = opSysUserServiceImpl.getUserById(id);
    return new ResponseMsg(OpSysUserEntity);
  }

  /**
   * 新增系统用户
   */
  @RequestMapping(value = "saveOpSysUser", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg<OpSysUserEntity> saveOpSysUser(@RequestBody OpSysUserEntity opSysUserEntity) {
    Assert.notNull(opSysUserEntity, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getSysUserId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserRealName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getGender(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getDepartmentId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getPostId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getStaffType(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserMobile(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserEmail(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    //  Assert.isTrue(null != opSysUserEntity.getCustomerCare(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<OpSysUserEntity> result = opSysUserServiceImpl.saveOpSysUser(opSysUserEntity);
    return result;
  }

  /**
   * 编辑系统用户
   */
  @RequestMapping(value = "updateOpSysUser", method = RequestMethod.POST)
  public ResponseMsg<String> updateOpSysUser(@RequestBody OpSysUserEntity opSysUserEntity) {
    Assert.notNull(opSysUserEntity, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getSysUserId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserRealName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getGender(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getDepartmentId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getPostId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getStaffType(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserMobile(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysUserEntity.getUserEmail(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    //  Assert.isTrue(null != opSysUserEntity.getCustomerCare(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opSysUserServiceImpl.updateOpSysUser(opSysUserEntity);
    return result;
  }

  /**
   * 更新系统用户状态
   */
  @RequestMapping(value = "updateUserStatus", method = RequestMethod.POST)
  public ResponseMsg<String> updateUserStatus(@RequestBody OpSysUserEntity opSysUserEntity) {
    Assert.isTrue(null != opSysUserEntity.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getSysUserId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysUserEntity.getDataStatus(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> resultMap = opSysUserServiceImpl.updateUserStatus(opSysUserEntity);
    return resultMap;
  }

  /**
   * 系统用户列表
   */
  @RequestMapping(value = "pageListOpUser", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg pageListOpUser(@RequestBody QueryOpUserListDto queryOpUserListDto) {
    Assert.notNull(queryOpUserListDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpUserListDto.getPageNum(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpUserListDto.getPageSize(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysUserServiceImpl.pageListOpUser(queryOpUserListDto);
    return result;
  }

  /**
   * 获取用户详情
   *
   * @return 应用信息
   */
  @RequestMapping(value = "getOpSysUserById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getOpSysUserById(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysUserServiceImpl.getOpSysUserById(id);
    return result;
  }

  /**
   * 用户重置密码
   *
   * @return 应用信息
   */
  @RequestMapping(value = "resetUserPwd", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg resetUserPwd(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysUserServiceImpl.resetUserPwd(id);
    return result;
  }

  /**
   * 获取用户个人账号信息
   *
   * @return 应用信息
   */
  @RequestMapping(value = "getOpSysUserAccountInfoById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getOpSysUserAccountInfoById(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysUserServiceImpl.getOpSysUserAccountInfoById(id);
    return result;
  }

  /**
   * 获取用户个人账号登录日志信息
   *
   * @return 应用信息
   */
  @RequestMapping(value = "queryOpSysUserLoginLogById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg queryOpSysUserLoginLogById(@RequestParam(name = "id") Long id,
      @RequestParam(name = "pageNum") Integer pageNum,
      @RequestParam(name = "pageSize") Integer pageSize) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysUserServiceImpl.queryOpSysUserLoginLogById(id, pageNum, pageSize);
    return result;
  }

  /**
   * 修改系统用户密码
   */
  @RequestMapping(value = "updateUserPwd", method = RequestMethod.POST)
  public ResponseMsg<String> updateUserPwd(@RequestBody UpdateUserPwdDto updateUserPwdDto) {
    Assert.isTrue(null != updateUserPwdDto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != updateUserPwdDto.getOldPwd(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != updateUserPwdDto.getNewPwd(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != updateUserPwdDto.getConfirmPwd(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> resultMap = opSysUserServiceImpl.updateUserPwd(updateUserPwdDto);
    return resultMap;
  }

  /**
   * 用户退出
   *
   * @return 应用信息
   */
  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public ResponseMsg logout(HttpServletRequest request) {
    String tokenAuth = request.getHeader(tokenHeader);
    Assert.hasText(tokenAuth, ServerErrorConst.ERR_TOKEN_EMPTY_MSG);

    // logout, remove storeKey
    // 退出统一登录
//    String sessionId = request.getHeader(SsoConstant.SSO_SESSIONID);
//    SsoTokenHelper.logout(sessionId);

    ResponseMsg result = opSysUserServiceImpl.logout(tokenAuth);
    return result;
  }

  /**
   * 用户退出
   *
   * @return 应用信息
   */
//  @RequestMapping(value = "logincheck", method = RequestMethod.POST)
//  public ResponseMsg logincheck(HttpServletRequest request) {
//    // logout, remove storeKey
//    // 退出统一登录
//    String sessionId = request.getHeader(SsoConstant.SSO_SESSIONID);
//    XxlSsoUser xxlUser = SsoTokenHelper.loginCheck(sessionId);
//    ResponseMsg result = new ResponseMsg();
//    if (xxlUser == null) {
//      return new ResponseMsg(ApiRespCodeEnum.SESSION_NOT_EXIST_OR_EXPIRE.getCode(),
//          ApiRespCodeEnum.SESSION_NOT_EXIST_OR_EXPIRE.getMsg());
//    }
//    return result;
//  }


  /**
   * 部门用户树形列表
   */
  @RequestMapping(value = "listDropDownUsers", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listDropDownUsers() {
    ResponseMsg result = opSysUserServiceImpl.listDropDownUsers();
    return result;
  }

}
