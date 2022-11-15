package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 商品订单信息 Dto
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
	  @ApiModelProperty(value = "订单号")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(30)
	  private String orderNo;

	  /**
	  * 库存编号
	  */
	  @ApiModelProperty(value = "库存编号")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(19)
	  private Long inventoryId;

	  /**
	  * 状态
	  */
	  @ApiModelProperty(value = "状态")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(3)
	  private Integer status;

	  /**
	  * 原售价
	  */
	  @ApiModelProperty(value = "原售价")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(10)
	  private BigDecimal shelvesPrice;

	  /**
	  * 运费
	  */
	  @ApiModelProperty(value = "运费")
    @ValidatorLenMax(10)
	  private BigDecimal freight;

	  /**
	  * 手续费
	  */
	  @ApiModelProperty(value = "手续费")
    @ValidatorLenMax(10)
	  private BigDecimal poundage;

	  /**
	  * 到手价
	  */
	  @ApiModelProperty(value = "到手价")
    @ValidatorLenMax(10)
	  private BigDecimal theirPrice;

	  /**
	  * 地址编号
	  */
	  @ApiModelProperty(value = "地址编号")
    @ValidatorLenMax(19)
	  private Long addressId;

	  /**
	  * 运单编号
	  */
	  @ApiModelProperty(value = "运单编号")
    @ValidatorLenMax(19)
	  private Long waybillNo;

}
