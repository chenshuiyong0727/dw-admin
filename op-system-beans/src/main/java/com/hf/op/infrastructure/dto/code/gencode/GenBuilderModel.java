package com.hf.op.infrastructure.dto.code.gencode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成器 - 生成日志 + 代码模型
 *
 * @author parker
 * @date 2020-09-16 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenBuilderModel extends BaseApiWrapper {

  /**
   * 归属表ID
   */
  private String tableId;

  /**
   * 包名
   */
  private String packageName;

  /**
   * 模块名
   */
  private String moduleName;

  /**
   * 子模块名
   */
  private String subModuleName;

  /**
   * 代码标题
   */
  private String codeTitle;

  /**
   * 代码标题简介
   */
  private String codeTitleBrief;

  /**
   * 作者名
   */
  private String authorName;

  /**
   * 模板ID
   */
  private String templateId;

  /**
   * serialVersionUID
   */
  private String serialId;

  /**
   * 代码模型
   */
  private GenTableAndColumnModel model;

}
