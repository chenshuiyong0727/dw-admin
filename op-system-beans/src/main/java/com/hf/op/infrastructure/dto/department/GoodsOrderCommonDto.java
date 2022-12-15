package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class GoodsOrderCommonDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3572114745267816227L;

  // 商品总数
  private Integer goodsNum = 0;
  //  日期
  private String months;
  // 入库商品总数
  private Integer goodsPutInNum = 0;
  // 库存总数
  private Integer inventoryNum = 0;
  // 库存成本
  private BigDecimal inventoryCost = BigDecimal.ZERO;
  // 市值总价
  private BigDecimal marketValue = BigDecimal.ZERO;
  // 市值利润
  private BigDecimal marketProfits = BigDecimal.ZERO;
  // 交易成功数
  private Integer successNum = 0;
  // 订单总额
  private BigDecimal orderAmount = BigDecimal.ZERO;
  // 利润总额
  private BigDecimal profitsAmount = BigDecimal.ZERO;
  // 手续费
  private BigDecimal poundage = BigDecimal.ZERO;
  // 运费
  private BigDecimal freight = BigDecimal.ZERO;
  // 其他收支
  private BigDecimal otherRevenue = BigDecimal.ZERO;

  // 交易成功数
  private Integer successNumLast = 0;
  // 订单总额
  private BigDecimal orderAmountLast = BigDecimal.ZERO;
  // 利润总额
  private BigDecimal profitsAmountLast = BigDecimal.ZERO;
  // 入库总额
  private BigDecimal inventoryAmount = BigDecimal.ZERO;
  // 入库均价
//  private BigDecimal inboundAverage=BigDecimal.ZERO;
  // 运费均价
//  private BigDecimal freightAverage=BigDecimal.ZERO;
//  // 成本均价
//  private BigDecimal costAverage=BigDecimal.ZERO;
//  // 利润均价
//  private BigDecimal profitsAverage=BigDecimal.ZERO;
//  // 利润比例
//  private BigDecimal profitsProportion=BigDecimal.ZERO;

}
