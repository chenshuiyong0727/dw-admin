package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.department.GoodsOtherRqDto;
import com.hf.op.infrastructure.vo.GoodsOtherPageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 其他收支 Repository
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Repository
public interface GoodsOtherRepository  extends BaseMapper<GoodsOtherEntity> {


  /**
   * @description 查询其他收支列表
   * @method page
   * @date:  2022-11-29 17:04:31
   */
  IPage<GoodsOtherPageVo> page(Page page, @Param("dto") GoodsOtherRqDto dto);

}
