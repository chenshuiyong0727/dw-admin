package com.hf.op.rest.base;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;
import com.hf.op.service.inf.base.BaseService;
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
 * @function 基础信息控制器
 * @Date 2021/12/10
 */
@RestController
@RequestMapping("v1/base")
@RefreshScope
@Slf4j
public class BaseServiceController {

  private BaseService baseServiceImpl;

  public BaseServiceController(BaseService baseServiceImpl) {
    this.baseServiceImpl = baseServiceImpl;
  }

  /**
   * 获取用户验证码
   *
   * @return 应用信息
   */
  @RequestMapping(value = "vc/login/getLoginVerityCode", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg getLoginVerityCodeByBase(
      @RequestBody QueryVerityCodeLoginDto baseComLoginDto) {
    Assert.notNull(baseComLoginDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(baseComLoginDto.getLoginAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = baseServiceImpl.getLoginVerityCode(baseComLoginDto);
    return result;
  }
  /**
   * 获取用户验证码
   *
   * @return 应用信息
   */
 /*   @RequestMapping(value = "vc/login/getLoginVerityCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg getLoginVerityCode(
        @RequestBody QueryVerityCodeLoginDto queryVerityCodeLoginDto) throws Exception {
        Assert.notNull(queryVerityCodeLoginDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        Assert.hasText(queryVerityCodeLoginDto.getLoginAccount(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        return baseServiceImpl.getLoginVerityCode(queryVerityCodeLoginDto);
    }*/

  /**
   * 获取系统数据字典
   *
   * @return 应用信息
   */
  @RequestMapping(value = "sys/dict/listSysDict", method = RequestMethod.POST)
  public ResponseMsg listSysDict() {
    ResponseMsg result = baseServiceImpl.listSysDict();
    return result;
  }

  /*   *//**
   * 根据类型id获取系统数据字典
   *
   * @param
   * @return 应用信息
   *//*
    @RequestMapping(value = "sys/dict/listSysDictByTypeId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg listSysDictByTypeId(@RequestParam(name = "typeId") Long typeId) {
        Assert.isTrue(null != typeId, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        ResponseMsg result = baseServiceImpl.listSysDictByTypeId(typeId);
        return result;
    }

    *//**
   * 获取系统行政区域数据
   *
   * @param
   * @return 应用信息
   *//*
    @RequestMapping(value = "sys/area/listSysAreas", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg listSysAreas(@RequestBody QueryComSysAreasDto queryComSysAreasDto) {
        Assert.notNull(queryComSysAreasDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        Assert.isTrue(null != queryComSysAreasDto.getLevel(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        Assert.isTrue(null != queryComSysAreasDto.getParentId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
        ResponseMsg result = baseServiceImpl.listSysAreas(queryComSysAreasDto);
        return result;
    }*/


}
