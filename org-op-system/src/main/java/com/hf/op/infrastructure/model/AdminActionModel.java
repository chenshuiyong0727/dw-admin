package com.hf.op.infrastructure.model;

import java.util.Date;
import lombok.Data;

/**
 * 系统资源-功能操作
 */
@Data
public class AdminActionModel {

  private static final long serialVersionUID = 1471599074044557390L;
  /**
   * 创建时间
   */
  public Date createTime;
  /**
   * 修改时间
   */
  public Date updateTime;
  /**
   * 资源ID
   */
  private String actionId;
  /**
   * 资源编码
   */
  private String actionCode;
  /**
   * 资源名称
   */
  private String actionName;
  /**
   * 资源父节点
   */
  private String menuId;
  /**
   * 优先级 越小越靠前
   */
  private Integer priority;
  /**
   * 资源描述
   */
  private String actionDesc;
  /**
   * 状态:0-无效 1-有效
   */
  private Integer status;
}
