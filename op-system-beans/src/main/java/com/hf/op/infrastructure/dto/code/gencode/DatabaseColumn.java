package com.hf.op.infrastructure.dto.code.gencode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成器 - 数据库表字段
 *
 * @author parker
 * @date 2020-09-16 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DatabaseColumn {


  /**
   * 数据库
   */
  private String dbName;

  /**
   * 表名称
   */
  private String tableName;

  /**
   * 字段名称
   */
  private String columnName;

  /**
   * 字段类型
   */
  private String columnType;

  /**
   * 字段长度
   */
  private Integer columnLength;

  /**
   * 字段精度 = 位数
   */
  private Integer columnPrecision;

  /**
   * 字段位数 = 精度
   */
  private Integer columnScale;

  /**
   * 字段描述
   */
  private String columnComment;

  /**
   * 是否主键
   */
  private String izPk;

  /**
   * 是否可为空
   */
  private String izNotNull;

  // ========================================

}
