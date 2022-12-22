package com.hf.op.infrastructure.dto.department;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class GoodsOrderOutExportDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3572114745267816227L;

  //  月份
  @Excel(name = "月份")
  private String months;

  @Excel(name = "销售数", type = 10)
  private Integer successNum;

  @Excel(name = "销售金额", type = 10)
  private BigDecimal orderAmount;

  @Excel(name = "利润", type = 10)
  private BigDecimal profitsAmount;

  @Excel(name = "销售均价", type = 10)
  private BigDecimal orderAmountAvg;

  @Excel(name = "平均利润", type = 10)
  private BigDecimal profitsAmountAvg;
}
