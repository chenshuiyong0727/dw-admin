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
 * 商品基本信息 Dto
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
@ToString
public class GoodsBaseDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3354493634895589016L;

  /**
   * 类型 ：1 鞋，2服 ，3配件
   */
  @ApiModelProperty(value = "类型 ：1 鞋，2服 ，3配件")
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(3)
  private Integer type;

  /**
   * 货号
   */
  @ApiModelProperty(value = "货号")
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
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
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
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

}
