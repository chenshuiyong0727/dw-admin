package com.hf.op.service.inf;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.post.QueryOpPostListDto;
import com.hf.op.infrastructure.dto.post.SaveOrUpdateOpPostDto;

/**
 * @author wpq
 * @function 岗位管理
 * @Date 2021/12/13
 */
public interface OpPostService {

  /**
   * 新增岗位
   */
  ResponseMsg<String> saveOpPost(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

  /**
   * 通过岗位编号获取岗位信息
   */
  ResponseMsg getOpPostById(Long id);

  /**
   * 编辑岗位
   */
  ResponseMsg<String> updateOpPost(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

  /**
   * 检查岗位名称是否存在
   */
  Boolean isExist(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

  /**
   * 根据条件获取岗位列表
   */
  ResponseMsg pageListOpPost(QueryOpPostListDto queryOpPostListDto);

  /**
   * 逻辑删除岗位
   */
  ResponseMsg<String> updateOpPostStatus(SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

  /**
   * 岗位（下拉）列表
   */
  ResponseMsg listDropDownPosts(
      SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

}
