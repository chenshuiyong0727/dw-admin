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
 * 其他收支 Dto
 *
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Data
@ToString
public class GoodsOtherDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3202146933980024489L;

  /**
   * 类型
   */
  @ApiModelProperty(value = "类型")
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(3)
  private Integer type;

  /**
   * 货号
   */
  @ApiModelProperty(value = "货号")
  @ValidatorLenMax(64)
  private String actNo;

  /**
   * 商品名称
   */
  @ApiModelProperty(value = "商品名称")
  @ValidatorLenMax(128)
  private String name;

  /**
   * 图片地址
   */
  @ApiModelProperty(value = "图片地址")
  @ValidatorLenMax(512)
  private String imgUrl;

  /**
   * 品牌
   */
  @ApiModelProperty(value = "品牌")
  @ValidatorLenMax(64)
  private String brand;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  @ValidatorLenMax(512)
  private String remark;

  /**
   * 金额
   */
  @ApiModelProperty(value = "金额")
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(10)
  private BigDecimal price;

}
