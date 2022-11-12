package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品库存 Dto
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
public class GoodsInventoryDto extends BaseDto implements Serializable {

		private static final long serialVersionUID = 3352235730262773894L;

	  /**
	  * 商品编号
	  */
	  @ApiModelProperty(value = "商品编号")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(19)
	  private Long goodsId;

	  /**
	  * 尺码编号
	  */
	  @ApiModelProperty(value = "尺码编号")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(19)
	  private Long sizeId;

	  /**
	  * 库存
	  */
	  @ApiModelProperty(value = "库存")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(10)
	  private Integer inventory;

}
