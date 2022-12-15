package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
public class GoodsOrderLineVo extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3572114745267816227L;

  // 交易成功数
  private Integer successNum = 0;
  // 订单总额
  private BigDecimal orderAmount = BigDecimal.ZERO;
  // 利润总额
  private BigDecimal profitsAmount = BigDecimal.ZERO;

  // 交易成功数
  private Integer successNumLast = 0;
  // 订单总额
  private BigDecimal orderAmountLast = BigDecimal.ZERO;
  // 利润总额
  private BigDecimal profitsAmountLast = BigDecimal.ZERO;
  //  // 利润比例
  private List<GoodsOrderCommonDto> rows;

}
