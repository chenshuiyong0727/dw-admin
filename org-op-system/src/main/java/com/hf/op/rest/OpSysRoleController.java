package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.domain.model.role.OpSysRoleEntity;
import com.hf.op.infrastructure.dto.role.QueryOpRoleListDto;
import com.hf.op.infrastructure.dto.role.QueryRoleRelationUserListDto;
import com.hf.op.service.inf.OpSysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wpq
 * @function 运营系统角色控制器
 * @Date 2022/02/16
 */
@RestController
@RequestMapping("v1/role/")
@Slf4j
public class OpSysRoleController {

  private OpSysRoleService opSysRoleServiceImpl;

  public OpSysRoleController(OpSysRoleService opSysRoleServiceImpl) {
    this.opSysRoleServiceImpl = opSysRoleServiceImpl;
  }

  /**
   * 新增角色
   */
  @RequestMapping(value = "saveOpSysRole", method = RequestMethod.POST)
  public ResponseMsg<String> saveOpSysRole(@RequestBody OpSysRoleEntity opSysRoleEntity) {
    Assert.notNull(opSysRoleEntity, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysRoleEntity.getRoleName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(opSysRoleEntity.getSystemId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opSysRoleServiceImpl.saveOpSysRole(opSysRoleEntity);
    return result;
  }

  /**
   * 编辑角色
   */
  @RequestMapping(value = "updateOpSysRole", method = RequestMethod.POST)
  public ResponseMsg<String> updateOpSysRole(@RequestBody OpSysRoleEntity opSysRoleEntity) {
    Assert.notNull(opSysRoleEntity, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(opSysRoleEntity.getRoleName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(opSysRoleEntity.getSystemId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opSysRoleServiceImpl.updateOpSysRole(opSysRoleEntity);
    return result;
  }

  /**
   * 更新角色状态
   */
  @RequestMapping(value = "updateRoleStatus", method = RequestMethod.POST)
  public ResponseMsg<String> updateRoleStatus(@RequestBody OpSysRoleEntity opSysRoleEntity) {
    Assert.isTrue(null != opSysRoleEntity.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != opSysRoleEntity.getDataStatus(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> resultMap = opSysRoleServiceImpl.updateRoleStatus(opSysRoleEntity);
    return resultMap;
  }

  /**
   * 角色列表
   */
  @RequestMapping(value = "pageListOpRole", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg pageListOpRole(@RequestBody QueryOpRoleListDto queryOpRoleListDto) {
    Assert.notNull(queryOpRoleListDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpRoleListDto.getPageNum(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpRoleListDto.getPageSize(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysRoleServiceImpl.pageListOpRole(queryOpRoleListDto);
    return result;
  }

  /**
   * 获取角色详情
   *
   * @return 应用信息
   */
  @RequestMapping(value = "getOpSysRoleById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getOpSysRoleById(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysRoleServiceImpl.getOpSysRoleById(id);
    return result;
  }

  /**
   * 角色关联用户列表
   */
  @RequestMapping(value = "pageListOpRoleRelationUser", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg pageListOpRoleRelationUser(
      @RequestBody QueryRoleRelationUserListDto queryRoleRelationUserListDto) {
    Assert.notNull(queryRoleRelationUserListDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryRoleRelationUserListDto.getRoleId(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryRoleRelationUserListDto.getPageNum(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryRoleRelationUserListDto.getPageSize(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysRoleServiceImpl
        .pageListOpRoleRelationUser(queryRoleRelationUserListDto);
    return result;
  }

  /**
   * 权限（树形）列表
   */
  @RequestMapping(value = "listTreeFunctions", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listTreeFunctions(@RequestBody QueryOpRoleListDto dto) {
    ResponseMsg result = opSysRoleServiceImpl.listTreeFunctions(dto.getSystemId(), null);
    return result;
  }

  /**
   * 当前用户最新权限列表
   */
  @RequestMapping(value = "listCurrentUserPermissionsByUserId", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg listCurrentUserLatestPermissions(@RequestParam(name = "id") Long id) {
    ResponseMsg result = opSysRoleServiceImpl.listCurrentUserLatestPermissions(id);
    return result;
  }

  /**
   * 权限角色（下拉）列表
   */
  @RequestMapping(value = "listDropDownRoles", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listDropDownRoles(@RequestBody QueryOpRoleListDto dto) {
    ResponseMsg result = opSysRoleServiceImpl.listDropDownRoles(dto);
    return result;
  }

  /**
   * 系统列表
   */
  @RequestMapping(value = "getOpSystemList", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getOpSystemList() {
    ResponseMsg result = opSysRoleServiceImpl.getOpSystemList();
    return result;
  }

}
