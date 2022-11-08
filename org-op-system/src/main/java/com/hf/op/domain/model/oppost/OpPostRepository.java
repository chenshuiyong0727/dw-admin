package com.hf.op.domain.model.oppost;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.post.ListPostDropDownListVo;
import com.hf.op.infrastructure.dto.post.QueryOpPostListDto;
import com.hf.op.infrastructure.dto.post.SaveOrUpdateOpPostDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @function 岗位
 * @Date 2021/12/13
 */
@Repository
public interface OpPostRepository extends BaseMapper<OpPostEntity> {

  /**
   * 根据条件获取岗位列表
   */
  IPage<QueryOpPostListDto> pageListOpPost(Page<QueryOpPostListDto> page,
      @Param("query") QueryOpPostListDto queryOpPostListDto);

  /**
   * 获取岗位（下拉）列表
   */
  List<ListPostDropDownListVo> listDropDownPosts(
      @Param("dto") SaveOrUpdateOpPostDto saveOrUpdateOpPostDto);

}
