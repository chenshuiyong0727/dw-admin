package com.hf.op.infrastructure.vo.code;

import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_logs的实体类
 * @date 2022-08-23
 */
@Data
public class GenLogsPageVo implements Serializable {

  /**
   * 作者名
   */
  private String authorName;

  /**
   * 代码标题
   */
  private String codeTitle;

  /**
   * 代码标题简介
   */
  private String codeTitleBrief;

  /**
   * 数据状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 主键
   */
  private Long id;

  /**
   * 生成模块名
   */
  private String moduleName;

  /**
   * 生成包名
   */
  private String packageName;

  /**
   * 生成子模块名
   */
  private String subModuleName;

  /**
   * 归属表ID
   */
  private Long tableId;

  /**
   * 表类型
   */
  private String tableType;

  /**
   * 模板ID
   */
  private Long templateId;
}
