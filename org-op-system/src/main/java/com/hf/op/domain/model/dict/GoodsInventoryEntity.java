package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 商品库存 Entity
 *
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
@TableName("goods_inventory")
public class GoodsInventoryEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2692180006602616234L;

  /**
   * 商品编号
   */
  private Long goodsId;

  /**
   * 尺码编号
   */
  private Long sizeId;

  /**
   * 库存
   */
  private Integer inventory;

  /**
   * 原始库存
   */
  private Integer oldInventory;

  /**
   * 库存
   */
  private BigDecimal price;

  /**
   * 库存
   */
  private BigDecimal dwPrice;
}
