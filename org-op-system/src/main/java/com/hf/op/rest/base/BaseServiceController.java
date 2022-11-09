package com.hf.op.rest.base;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.ListSysDictTypeVo;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;
import com.hf.op.infrastructure.vo.SysDictRqVo;
import com.hf.op.service.inf.base.BaseService;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  /**
   * 根据类型id获取系统数据字典
   *
   * @return 应用信息
   */
  @RequestMapping(value = "sys/dict/listSysDictByTypeId", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listSysDictByTypeId(@RequestParam(name = "typeId") Long typeId) {
    Assert.isTrue(null != typeId, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = baseServiceImpl.listSysDictByTypeId(typeId);
    return result;
  }

  /**
   * 获取系统数据字典
   *
   * @return 应用信息
   */
  @GetMapping("/sys/dict/listSysDictType")
  public ResponseMsg listSysDictType(HttpServletRequest request) {
    ListSysDictTypeVo dto = HfBeanUtil.populate(new ListSysDictTypeVo(), request);
    return baseServiceImpl.listSysDictType(dto);
  }


  /**
   * 获取数据
   */
  @GetMapping("/sys/dict/dictType/{id}")
  public ResponseMsg dictTypeDetail(@PathVariable(value = "id") Long id) {
    ListSysDictTypeVo vo = new ListSysDictTypeVo();
    vo.setId(id);
    return baseServiceImpl.dictTypeDetail(vo);
  }

  /**
   * 新增数据
   */
  @PostMapping("/sys/dict/dictType")
  public ResponseMsg addDictType(@RequestBody ListSysDictTypeVo dto) {
    return baseServiceImpl.addDictType(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("/sys/dict/dictType")
  public ResponseMsg updateDictType(@RequestBody ListSysDictTypeVo dto) {
    return baseServiceImpl.updateDictType(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/sys/dict/dictType/{id}")
  public ResponseMsg removeDictType(@PathVariable(value = "id") Long id) {
    return baseServiceImpl.removeDictType(id);
  }

  /**
   * 获取系统数据字典
   *
   * @return 应用信息
   */
  @GetMapping("/sys/dict/dict/page")
  public ResponseMsg dictPage(HttpServletRequest request) {
    SysDictRqVo dto = HfBeanUtil.populate(new SysDictRqVo(), request);
    return baseServiceImpl.dictPage(dto);
  }


  /**
   * 获取数据
   */
  @GetMapping("/sys/dict/dict/{id}")
  public ResponseMsg dictDetail(@PathVariable(value = "id") Long id) {
    SysDictRqVo vo = new SysDictRqVo();
    vo.setId(id);
    return baseServiceImpl.dictDetail(vo);
  }

  /**
   * 新增数据
   */
  @PostMapping("/sys/dict/dict")
  public ResponseMsg addDictDetail(@RequestBody SysDictRqVo dto) {
    return baseServiceImpl.addDictDetail(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("/sys/dict/dict")
  public ResponseMsg updateDictDetail(@RequestBody SysDictRqVo dto) {
    return baseServiceImpl.updateDictDetail(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/sys/dict/dict/{id}")
  public ResponseMsg removeDictDetail(@PathVariable(value = "id") Long id) {
    return baseServiceImpl.removeDictDetail(id);
  }


}
