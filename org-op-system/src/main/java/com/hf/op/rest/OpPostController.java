package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.op.infrastructure.dto.post.QueryOpPostListDto;
import com.hf.op.infrastructure.dto.post.SaveOrUpdateOpPostDto;
import com.hf.op.service.inf.OpPostService;
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
 * @function 运营系统岗位控制器
 * @Date 2021/11/29
 */
@RestController
@RequestMapping("v1/sys/post/")
@Slf4j
public class OpPostController {

  private OpPostService opPostServiceImpl;

  public OpPostController(OpPostService opPostServiceImpl) {
    this.opPostServiceImpl = opPostServiceImpl;
  }

  /**
   * 新增岗位
   */
  @RequestMapping(value = "savePost", method = RequestMethod.POST)
  public ResponseMsg<String> savePost(@RequestBody SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    Assert.notNull(saveOrUpdateOpPostDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpPostDto.getName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpPostDto.getDescription(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getType(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getDepartmentId(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getDataStatus(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opPostServiceImpl.saveOpPost(saveOrUpdateOpPostDto);
    return result;
  }

  /**
   * 获取岗位详情
   *
   * @return 应用信息
   */
  @RequestMapping(value = "getOpPostById", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getOpPostById(@RequestParam(name = "id") Long id) {
    Assert.isTrue(null != id, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opPostServiceImpl.getOpPostById(id);
    return result;
  }

  /**
   * 编辑岗位
   */
  @RequestMapping(value = "updatePost", method = RequestMethod.POST)
  public ResponseMsg<String> updatePost(@RequestBody SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    Assert.notNull(saveOrUpdateOpPostDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpPostDto.getName(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.hasText(saveOrUpdateOpPostDto.getDescription(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getType(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getDepartmentId(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getDataStatus(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> result = opPostServiceImpl.updateOpPost(saveOrUpdateOpPostDto);
    return result;
  }

  /**
   * 岗位列表
   */
  @RequestMapping(value = "pageListOpPost", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg pageListOpPost(@RequestBody QueryOpPostListDto queryOpPostListDto) {
    Assert.notNull(queryOpPostListDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpPostListDto.getPageNum(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != queryOpPostListDto.getPageSize(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg result = opPostServiceImpl.pageListOpPost(queryOpPostListDto);
    return result;
  }

  /**
   * 更新岗位状态
   */
  @RequestMapping(value = "updatePostStatus", method = RequestMethod.POST)
  public ResponseMsg<String> updatePostStatus(
      @RequestBody SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    Assert.notNull(saveOrUpdateOpPostDto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.isTrue(null != saveOrUpdateOpPostDto.getDataStatus(),
        ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    ResponseMsg<String> resultMap = opPostServiceImpl.updateOpPostStatus(saveOrUpdateOpPostDto);
    return resultMap;
  }

  /**
   * 岗位（下拉）列表
   */
  @RequestMapping(value = "listDropDownPosts", method = RequestMethod.POST)
  @ResponseBody
  public ResponseMsg listDropDownPosts(
      @RequestBody(required = false) SaveOrUpdateOpPostDto saveOrUpdateOpPostDto) {
    ResponseMsg result = opPostServiceImpl.listDropDownPosts(saveOrUpdateOpPostDto);
    return result;
  }

}
