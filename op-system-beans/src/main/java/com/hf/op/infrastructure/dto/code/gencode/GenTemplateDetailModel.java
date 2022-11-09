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
 * 代码模板详情 Model
 *
 * @author Parker
 * @date 2021-05-28 17:12:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTemplateDetailModel extends BaseApiWrapper {


  /**
   * 父级ID
   */
  @ApiModelProperty(value = "父级ID")
  @ExcelProperty(value = "父级ID", order = 1)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(19)
  private String parentId;

  /**
   * 类型 0 后端 1 前端
   */
  @ApiModelProperty(value = "类型")
  @ExcelProperty(value = "类型", order = 2)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(1)
  private String type;

  /**
   * 路径
   */
  @ApiModelProperty(value = "路径")
  @ExcelProperty(value = "路径", order = 3)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(255)
  private String path;

  /**
   * 文件名
   */
  @ApiModelProperty(value = "文件名")
  @ExcelProperty(value = "文件名", order = 4)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(100)
  private String fileName;

  /**
   * 文件内容
   */
  @ApiModelProperty(value = "文件内容")
  @ExcelProperty(value = "文件内容", order = 5)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(20000)
  private String fileContent;

  /**
   * 是否忽略文件名
   */
  @ApiModelProperty(value = "文件名")
  @ExcelProperty(value = "文件名", order = 6)
  @ExcelInfo
  @Validator({
      ValidatorType.IS_NOT_NULL
  })
  @ValidatorLenMax(1)
  private String ignoreFileName;

}
