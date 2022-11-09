package com.hf.op.infrastructure.dto.code.gencode;


import com.alibaba.excel.annotation.ExcelProperty;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ExcelInfo;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码模板 Model
 *
 * @author Parker
 * @date 2021-05-27 14:33:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTemplateModel extends BaseApiWrapper {


  /**
   * 模板名称
   */
  @ApiModelProperty(value = "模板名称")
  @ExcelProperty(value = "模板名称", order = 1)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_GENERAL_WITH_CHINESE,
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(100)
  private String tempName;

  /**
   * 表类型
   */
  @ApiModelProperty(value = "表类型")
  @ExcelProperty(value = "表类型", order = 2)
  @ExcelInfo(dictType = "table_type")
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(30)
  private String tableType;


  /**
   * 备注信息
   */
  @ApiModelProperty(value = "备注信息")
  @ExcelProperty(value = "备注信息", order = 3)
  @ExcelInfo
  @ValidatorLenMax(255)
  private String remark;

}
