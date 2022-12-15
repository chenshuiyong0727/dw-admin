package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeRqDto;
import com.hf.op.infrastructure.vo.GoodsBaseSizePageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品尺码关系 Repository
 *
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Repository
public interface GoodsBaseSizeRepository extends BaseMapper<GoodsBaseSizeEntity> {


  /**
   * @description 查询商品尺码关系列表
   * @method page
   * @date: 2022-11-12 16:48:45
   */
  IPage<GoodsBaseSizePageVo> page(Page page, @Param("dto") GoodsBaseSizeRqDto dto);

}
