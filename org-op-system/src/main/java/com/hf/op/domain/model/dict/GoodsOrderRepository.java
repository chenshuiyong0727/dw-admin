package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.infrastructure.vo.GoodsOrderPageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品订单信息 Repository
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Repository
public interface GoodsOrderRepository  extends BaseMapper<GoodsOrderEntity> {


  /**
   * @description 查询商品订单信息列表
   * @method page
   * @date:  2022-11-15 17:39:00
   */
  IPage<GoodsOrderPageVo> page(Page page, @Param("dto") GoodsOrderRqDto dto);

}