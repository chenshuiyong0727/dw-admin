package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.department.GoodsInventoryRqDto;
import com.hf.op.infrastructure.vo.GoodsBasePageVo;
import com.hf.op.infrastructure.vo.GoodsInventoryPageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品库存 Repository
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Repository
public interface GoodsInventoryRepository  extends BaseMapper<GoodsInventoryEntity> {


  /**
   * @description 查询商品库存列表
   * @method page
   * @date:  2022-11-12 20:10:34
   */
  IPage<GoodsBasePageVo> page(Page page, @Param("dto") GoodsInventoryRqDto dto);
  IPage<GoodsInventoryPageVo> pageGoods(Page page, @Param("dto") GoodsInventoryRqDto dto);

}
