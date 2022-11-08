package com.hf.op.infrastructure.model;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统权限-菜单权限、操作权限、API权限
 */
@Data
public class AdminAuthority {

  private static final long serialVersionUID = 1L;
  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date createTime;
  /**
   * 修改时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date updateTime;
  private String authorityId;
  /**
   * 权限标识
   */
  private String authority;
  /**
   * 菜单资源ID
   */
  private String menuId;
  /**
   * API资源ID
   */
  private String apiId;
  /**
   * 操作资源ID
   */
  private String actionId;
  /**
   * 状态
   */
  private Integer status;
}
