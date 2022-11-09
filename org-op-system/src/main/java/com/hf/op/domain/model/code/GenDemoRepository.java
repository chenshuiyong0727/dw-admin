package com.hf.op.domain.model.code;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.code.GenDemoRqDto;
import com.hf.op.infrastructure.vo.code.GenDemoPageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 案例 Repository
 *
 * @author chensy
 * @date 2022-09-08 15:05:44
 */
@Repository
public interface GenDemoRepository extends BaseMapper<GenDemoEntity> {


  /**
   * @description 查询案例列表
   * @method page
   * @date: 2022-09-08 15:05:44
   */
  IPage<GenDemoPageVo> page(Page page, @Param("dto") GenDemoRqDto dto);

}
