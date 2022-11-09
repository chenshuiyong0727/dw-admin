package com.hf.op.infrastructure.dto.code.gencode;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hf.common.infrastructure.validator.Validator;
import com.hf.common.infrastructure.validator.ValidatorLenMax;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import com.hf.op.infrastructure.dto.code.GenTableColumn1Model;
import com.hf.op.infrastructure.dto.code.GenTableDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成器 - 表和表结构
 *
 * @author parker
 * @date 2020-09-16 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTableAndColumnModel extends BaseApiWrapper {


  /**
   * 表名称
   */
  @ApiModelProperty(value = "表名称")
  @ExcelIgnore
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
  @ExcelIgnore
  @Validator({ValidatorType.IS_NOT_NULL})
  @ValidatorLenMax(1)
  private String tableType;

  /**
   * 数据库类型
   */
  @ApiModelProperty(value = "数据库类型")
  @ExcelIgnore
  @Validator({ValidatorType.IS_NOT_NULL})
  @ValidatorLenMax(30)
  private String jdbcType;

  /**
   * 描述
   */
  @ApiModelProperty(value = "描述")
  @ExcelIgnore
  @ValidatorLenMax(200)
  private String comments;

  /**
   * 同步
   */
  @ApiModelProperty(value = "同步")
  @ExcelIgnore
  @ValidatorLenMax(1)
  private String izSync;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  @ExcelIgnore
  @ValidatorLenMax(255)
  private String remark;

  /**
   * 数据库名
   */
  @ApiModelProperty(value = "数据库名")
  @ExcelIgnore
  @ValidatorLenMax(255)
  private String dbName;

  /**
   * 服务名
   */
  @ApiModelProperty(value = "服务名")
  @ExcelIgnore
  @ValidatorLenMax(255)
  private String serviceName;

  /**
   * 表结构
   */
  @ApiModelProperty(value = "表结构")
  @ExcelIgnore
  @Validator({ValidatorType.IS_NOT_NULL})
  private List<GenTableColumnModel> columnList;

  /**
   * 表结构
   */
  @ApiModelProperty(value = "表结构")
  @ExcelIgnore
  @Validator({ValidatorType.IS_NOT_NULL})
  private List<GenTableColumn1Model> columnList1;

  /**
   * 表结构
   */
  @ApiModelProperty(value = "表结构")
  @ExcelIgnore
  @Validator({ValidatorType.IS_NOT_NULL})
  private List<GenTableColumnModel> columnFrontList;

  // =======================

  /**
   * 表名称 - 驼峰
   */
  @JsonIgnore
  @ExcelIgnore
  private String tableHumpName;

  /**
   * Entity 包地址
   */
  @JsonIgnore
  @ExcelIgnore
  private List<String> entityPkgList;

  /**
   * 前端Form集合
   */
  @JsonIgnore
  @ExcelIgnore
  private List<GenTableColumnModel> formList;

  /**
   * 简单检索集合
   */
  @JsonIgnore
  @ExcelIgnore
  private List<GenTableColumnModel> briefQueryList;

  /**
   * 更多检索集合
   */
  @JsonIgnore
  @ExcelIgnore
  private List<GenTableColumnModel> moreQueryList;

  /**
   * 更多检索集合
   */
  private GenTableDto genTableDto;

}
