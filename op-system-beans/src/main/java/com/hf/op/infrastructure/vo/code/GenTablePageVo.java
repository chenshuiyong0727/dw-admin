package com.hf.op.infrastructure.vo.code;

import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_table的实体类
 * @date 2022-08-23
 */
@Data
public class GenTablePageVo implements Serializable {

  /**
   * 描述
   */
  private String comments;

  /**
   * 主键
   */
  private Long id;

  /**
   * 同步
   */
  private Integer izSync;

  /**
   * 数据库类型 { MySQL\Oracle\SQLServer ...}
   */
  private String jdbcType;

  /**
   * 备注信息
   */
  private String remark;

  /**
   * 表名称
   */
  private String tableName;

  /**
   * 表类型 1 表单
   */
  private String tableType;
}
