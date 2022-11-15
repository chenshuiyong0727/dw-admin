package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
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
public class GoodsInventorySizeDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3352235730262773894L;

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
   * 库存
   */
  private BigDecimal price;

  /**
   * 库存
   */
  private BigDecimal dwPrice;

}
