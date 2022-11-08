package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.infrastructure.dto.function.OpFunctionsDto;
import com.hf.op.infrastructure.dto.function.QueryOpFunctionsTreeListDto;
import com.hf.op.service.inf.OpSysRoleService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("v1/menu")
@Slf4j
public class OpSysMenuController {

  private OpSysRoleService opSysRoleServiceImpl;

  public OpSysMenuController(OpSysRoleService opSysRoleServiceImpl) {
    this.opSysRoleServiceImpl = opSysRoleServiceImpl;
  }


  /**
   * 系统列表
   */
  @RequestMapping(value = "findMenuTreePageByLazy", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg findMenuTreePageByLazy(@RequestBody QueryOpFunctionsTreeListDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(dto.getSystemId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Long pid = dto.getPid();
    if (pid == null) {
      pid = 0L;
    }
    ResponseMsg result = opSysRoleServiceImpl.findMenuTreePageByLazy(dto.getSystemId(), pid);
    return result;
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody QueryOpFunctionsTreeListDto dto) {
    return opSysRoleServiceImpl.add(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody QueryOpFunctionsTreeListDto dto) {
    return opSysRoleServiceImpl.update(dto);
  }


  /**
   * @return 应用信息
   */
  @RequestMapping(value = "getMenuDetail", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getMenuDetail(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysRoleServiceImpl.getMenuDetail(id);
    return result;
  }

  /**
   * 系统列表
   */
  @RequestMapping(value = "goCopy", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg goCopy(@RequestBody QueryOpFunctionsTreeListDto dto) {
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(dto.getSystemId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(dto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opSysRoleServiceImpl.goCopy(dto.getSystemId(), dto.getId());
    return result;
  }

  /**
   * 批量删除
   */
  @PutMapping("/batch/remove")
  public ResponseMsg batchRemove(@RequestBody List<Long> ids) {
    return opSysRoleServiceImpl.batchRemove(ids);
  }


  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return opSysRoleServiceImpl.remove(id);
  }

  /**
   * 批量删除
   */
  @PutMapping("/updateKeyList")
  public ResponseMsg updateKeyList(@RequestBody OpFunctionsDto dto) {
    return opSysRoleServiceImpl.updateKeyList(dto);
  }

}
