package com.hf.op.infrastructure.dto.code.gencode;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ExcelInfo;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成器 - 表
 *
 * @author parker
 * @date 2020-09-16 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTableModel extends BaseApiWrapper {


  /**
   * 表名称
   */
  @ApiModelProperty(value = "表名称")
  @ExcelProperty(value = "表名称", order = 1)
  @ExcelInfo
  @Validator({ValidatorType.IS_NOT_NULL, ValidatorType.IS_GENERAL})
  @ValidatorLenMax(100)
  private String tableName;

  /**
   * 旧表名称
   */
  @ApiModelProperty(value = "旧表名称")
  @ExcelIgnore
  private String oldTableName;

  /**
   * 表类型
   */
  @ApiModelProperty(value = "表类型")
  @ExcelProperty(value = "表类型", order = 2)
  @ExcelInfo(dictType = "table_type")
  @Validator({ValidatorType.IS_NOT_NULL})
  @ValidatorLenMax(1)
  private String tableType;

  /**
   * 数据库类型
   */
  @ApiModelProperty(value = "数据库类型")
  @ExcelProperty(value = "数据库类型", order = 3)
  @ExcelInfo(dictType = "jdbc_type")
  @Validator({ValidatorType.IS_NOT_NULL})
  @ValidatorLenMax(30)
  private String jdbcType;

  /**
   * 描述
   */
  @ApiModelProperty(value = "描述")
  @ExcelProperty(value = "描述", order = 4)
  @ExcelInfo
  @ValidatorLenMax(200)
  private String comments;

  /**
   * 同步
   */
  @ApiModelProperty(value = "同步")
  @ExcelProperty(value = "是否同步", order = 5)
  @ExcelInfo(dictType = "no_yes")
  @ValidatorLenMax(1)
  private String izSync;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  @ExcelProperty(value = "备注", order = 6)
  @ExcelInfo
  @ValidatorLenMax(255)
  private String remark;


}
