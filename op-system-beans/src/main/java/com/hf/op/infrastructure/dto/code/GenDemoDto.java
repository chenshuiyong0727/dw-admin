package com.hf.op.infrastructure.dto.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hf.common.infrastructure.dto.BaseDto;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * 案例 Dto
 * @author chensy
 * @date 2022-09-14 10:13:28
 */
@Data
@ToString
public class GenDemoDto extends BaseDto implements Serializable {

		private static final long serialVersionUID = 3208464309322838395L;

	  /**
	  * 其他表id
	  */
	  @ApiModelProperty(value = "其他表id")
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(19)
	  private Long opId;

	  /**
	  * 名称
	  */
	  @ApiModelProperty(value = "名称")
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_GENERAL_WITH_CHINESE
    })
    @ValidatorLenMax(64)
	  private String demoName;

	  /**
	  * 数量
	  */
	  @ApiModelProperty(value = "数量")
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(10)
	  private Integer demoNum;

	  /**
	  * 类型
	  */
	  @ApiModelProperty(value = "类型")
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(3)
	  private Integer demoType;

	  /**
	  * 金额
	  */
	  @ApiModelProperty(value = "金额")
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_DECIMAL
    })
    @ValidatorLenMax(10)
	  private BigDecimal demoAmount;

    /**
    * 时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	  @ApiModelProperty(value = "时间")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    private LocalDateTime demoTime;

	  /**
	  * 日期
	  */
    @JsonFormat(pattern = "yyyy-MM-dd")
	  @ApiModelProperty(value = "日期")
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
	  private LocalDate demoDate;

	  /**
	  * 文章
	  */
	  @ApiModelProperty(value = "文章")
    @ValidatorLenMax(20000)
	  private String demoText;

}
