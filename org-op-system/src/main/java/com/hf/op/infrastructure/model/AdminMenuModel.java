package com.hf.op.infrastructure.model;

import java.util.Date;
import lombok.Data;

/**
 * 系统资源-菜单信息
 */
@Data
public class AdminMenuModel {

  private static final long serialVersionUID = -4414780909980518788L;
  /**
   * 创建时间
   */
  public Date createTime;
  /**
   * 修改时间
   */
  public Date updateTime;
  private String menuId;
  /**
   * 菜单编码
   */
  private String menuCode;
  /**
   * 菜单名称
   */
  private String menuName;
  /**
   * 图标
   */
  private String icon;
  /**
   * 父级菜单
   */
  private String parentId;
  /**
   * 请求协议:/,http://,https://
   */
  private String scheme;
  /**
   * 请求路径
   */
  private String path;
  /**
   * 打开方式:_self窗口内,_blank新窗口
   */
  private String target;
  /**
   * 优先级 越小越靠前
   */
  private Integer priority;
  /**
   * 描述
   */
  private String menuDesc;
  /**
   * 状态:0-无效 1-有效
   */
  private Integer status;

}
