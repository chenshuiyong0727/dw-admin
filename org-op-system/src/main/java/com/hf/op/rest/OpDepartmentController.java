package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.infrastructure.dto.department.QueryOpDeptListDto;
import com.hf.op.infrastructure.dto.department.SaveOrUpdateOpDeptDto;
import com.hf.op.service.inf.OpDepartmentService;
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
 * @function 运营系统部门控制器
 * @Date 2021/11/29
 */
@RestController
@RequestMapping("v1/sys/departments/")
@Slf4j
public class OpDepartmentController {

  private OpDepartmentService opDepartmentServiceImpl;

  public OpDepartmentController(OpDepartmentService opDepartmentServiceImpl) {
    this.opDepartmentServiceImpl = opDepartmentServiceImpl;
  }

  /**
   * 新增部门
   */
  @RequestMapping(value = "saveDepartments", method = RequestMethod.POST)
  public ResponseMsg<String> saveDepartments(
      @RequestBody SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    Assert.notNull(saveOrUpdateOpDeptDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpDeptDto.getName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Integer levelOne = saveOrUpdateOpDeptDto.getLevelOne();
    Assert.isTrue(null != levelOne, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    if (levelOne == 0) {
      Assert.hasText(saveOrUpdateOpDeptDto.getSuperiorDepartment(),
          ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    }
    ResponseMsg<String> result = opDepartmentServiceImpl.saveDepartments(saveOrUpdateOpDeptDto);
    return result;
  }

  /**
   * 获取部门详情
   *
   * @return 应用信息
   */
  @RequestMapping(value = "getDepartmentsById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getDepartmentsById(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opDepartmentServiceImpl.getDepartmentsById(id);
    return result;
  }

  /**
   * 编辑部门
   */
  @RequestMapping(value = "updateDepartments", method = RequestMethod.POST)
  public ResponseMsg<String> updateDepartments(
      @RequestBody SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    Assert.notNull(saveOrUpdateOpDeptDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpDeptDto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpDeptDto.getName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Integer levelOne = saveOrUpdateOpDeptDto.getLevelOne();
    Assert.isTrue(null != levelOne, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    if (levelOne == 0) {
      Assert.hasText(saveOrUpdateOpDeptDto.getSuperiorDepartment(),
          ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    }
    Assert.isTrue(null != saveOrUpdateOpDeptDto.getDataStatus(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opDepartmentServiceImpl.updateDepartments(saveOrUpdateOpDeptDto);
    return result;
  }

  /**
   * 部门列表
   */
  @RequestMapping(value = "pageDepartmentsList", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg pageDepartmentsList(@RequestBody QueryOpDeptListDto queryOpDeptListDto) {
    Assert.notNull(queryOpDeptListDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpDeptListDto.getPageNum(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpDeptListDto.getPageSize(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opDepartmentServiceImpl.pageListDepartments(queryOpDeptListDto);
    return result;
  }

  /**
   * 更新部门状态
   */
  @RequestMapping(value = "updateDeptStatus", method = RequestMethod.POST)
  public ResponseMsg<String> updateDeptStatus(
      @RequestBody SaveOrUpdateOpDeptDto saveOrUpdateOpDeptDto) {
    Assert.isTrue(null != saveOrUpdateOpDeptDto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpDeptDto.getDataStatus(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> resultMap = opDepartmentServiceImpl.updateDeptStatus(saveOrUpdateOpDeptDto);
    return resultMap;
  }

  /**
   * 部门（树形）列表
   */
  @RequestMapping(value = "listTreeDepartments", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listTreeDepartments() {
    ResponseMsg result = opDepartmentServiceImpl.listTreeDepartments();
    return result;
  }

  /**
   * 部门（等级）列表
   */
  @RequestMapping(value = "listLevelDepartments", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listLevelDepartments() {
    ResponseMsg result = opDepartmentServiceImpl.listLevelDepartments();
    return result;
  }

  /**
   * 部门（下拉）列表
   */
  @RequestMapping(value = "listDropDownDepartments", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listDropDownDepartments() {
    ResponseMsg result = opDepartmentServiceImpl.listDropDownDepartments();
    return result;
  }

}
