package com.hf.op.rest;

import com.hf.common.infrastructure.resp.BaseController;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.infrastructure.dto.mini.AdminActionDto;
import com.hf.op.infrastructure.dto.mini.AdminAuthorityDto;
import com.hf.op.infrastructure.dto.mini.AdminMenuDto;
import com.hf.op.service.inf.AdminActionService;
import com.hf.op.service.inf.AdminAuthorityService;
import com.hf.op.service.inf.AdminMenuService;
import com.hf.op.service.inf.OpSysUserService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.Assert;
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
@RequestMapping("v1/syncData")
@RefreshScope
@Slf4j
public class OpSyncDataController extends BaseController {

  private AdminAuthorityService adminAuthorityServiceImpl;
  private AdminActionService adminActionServiceImpl;
  private AdminMenuService adminMenuServiceImpl;
  private OpSysUserService opSysUserServiceImpl;

  public OpSyncDataController(AdminAuthorityService adminAuthorityServiceImpl,
      OpSysUserService opSysUserServiceImpl,
      AdminActionService adminActionServiceImpl, AdminMenuService adminMenuServiceImpl) {
    this.adminAuthorityServiceImpl = adminAuthorityServiceImpl;
    this.adminActionServiceImpl = adminActionServiceImpl;
    this.adminMenuServiceImpl = adminMenuServiceImpl;
    this.opSysUserServiceImpl = opSysUserServiceImpl;
  }

  /**
   * 同步小程序系统权限
   */
  @RequestMapping(value = "/mini/syncAuthority", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg syncAuthority(@RequestBody AdminAuthorityDto dto) {
    try {
      log.info("OpSyncDataController.addAuthority.begin:" + dto);
      Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(dto.getAuthorityid(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      return adminAuthorityServiceImpl.syncAuthority(dto);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("syncAuthority", e);
    }
    return null;
  }

  /**
   * 同步小程序菜单权限
   */
  @RequestMapping(value = "/mini/syncAction", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg syncAction(@RequestBody AdminActionDto dto) {
    try {
      log.info("OpSyncDataController.addAuthority.begin:" + dto);
      Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(dto.getActionid(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      return adminActionServiceImpl.syncAction(dto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 同步小程序方法权限
   */
  @RequestMapping(value = "/mini/syncMenu", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg syncMenu(@RequestBody AdminMenuDto dto) {
    try {
      log.info("OpSyncDataController.addAuthority.begin:" + dto);
      Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      Assert.hasText(dto.getMenuid(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
      return adminMenuServiceImpl.syncMenu(dto);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

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
