package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * 商品订单信息 Dto
 *
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Data
@ToString
public class GoodsOrderDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3572114745267816227L;

  /**
   * 订单号
   */
  private String orderNo;

  /**
   * 库存编号
   */
  private Long inventoryId;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 原售价
   */
  private BigDecimal shelvesPrice;

  /**
   * 运费
   */
  private BigDecimal freight;
  /**
   * 利润
   */
  private BigDecimal profits;
  /**
   * 补贴价
   */
  private BigDecimal subsidiesPrice;
  /**
   * 手续费
   */
  private BigDecimal poundage;

  /**
   * 到手价
   */
  private BigDecimal theirPrice;

  /**
   * 地址编号
   */
  private Long addressId;

  /**
   * 运单编号
   */
  private String waybillNo;
  public LocalDateTime sellTime;
  public LocalDateTime successTime;
}
