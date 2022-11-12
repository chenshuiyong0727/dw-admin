package com.hf.op.infrastructure.dto.department;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import com.hf.common.infrastructure.dto.BaseDto;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品尺码关系 Dto
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Data
@ToString
public class GoodsBaseSizeDto extends BaseDto implements Serializable {

		private static final long serialVersionUID = 3504897929236190154L;

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
	  * 商品编号
	  */
	  @ApiModelProperty(value = "商品编号")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(19)
	  private Long goodsId;

}
