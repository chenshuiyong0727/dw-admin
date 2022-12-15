package com.hf.op.infrastructure.dto.department;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 商品库存 Dto
 *
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
public class GoodsShelvesGoodsRqDto implements Serializable {

  private static final long serialVersionUID = 3352235730262773894L;

  private Long inventoryId;

  /**
   * 上下架 1上架 0下架
   */
  private Integer type;

  /**
   * 库存
   */
  private Integer inventory;

  /**
   * 数量
   */
  private Integer num;

  /**
   * 上架价格
   */
  private BigDecimal shelvesPrice;

}
