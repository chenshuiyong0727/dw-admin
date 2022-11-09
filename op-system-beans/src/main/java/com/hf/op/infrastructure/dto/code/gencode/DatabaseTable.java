package com.hf.op.infrastructure.dto.code.gencode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成器 - 数据库表
 *
 * @author parker
 * @date 2020-09-16 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DatabaseTable {

  /**
   * 数据源
   */
  private String dbSource;

  /**
   * 数据库
   */
  private String dbName;

  /**
   * 表名称
   */
  private String tableName;

  /**
   * 描述
   */
  private String tableComments;

  // ========================================

}
