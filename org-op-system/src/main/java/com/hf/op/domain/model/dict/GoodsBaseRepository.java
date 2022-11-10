package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.department.GoodsBaseRqDto;
import com.hf.op.infrastructure.vo.GoodsBasePageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品基本信息 Repository
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Repository
public interface GoodsBaseRepository extends BaseMapper<GoodsBaseEntity> {


  /**
   * @description 查询商品基本信息列表
   * @method page
   * @date: 2022-11-08 11:10:33
   */
  IPage<GoodsBasePageVo> page(Page page, @Param("dto") GoodsBaseRqDto dto);

}
