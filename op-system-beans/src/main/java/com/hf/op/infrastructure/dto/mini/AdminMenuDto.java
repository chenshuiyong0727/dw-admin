package com.hf.op.infrastructure.dto.mini;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_menu的实体类
 * @date 2022-06-15
 */
@Data
public class AdminMenuDto implements Serializable {

  @JsonProperty(value = "createTime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date createtime;
  @JsonProperty(value = "updateTime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date updatetime;
  /**
   * 状态
   */
  private Integer syncType;
  /**
   * 菜单Id
   */
  @JsonProperty(value = "menuId")
  private String menuid;
  /**
   * 父级菜单
   */
  @JsonProperty(value = "parentId")
  private String parentid;
  /**
   * 菜单编码
   */
  @JsonProperty(value = "menuCode")
  private String menucode;
  /**
   * 菜单名称
   */
  @JsonProperty(value = "menuName")
  private String menuname;
  /**
   * 描述
   */
  @JsonProperty(value = "menuDesc")
  private String menudesc;
  /**
   * 路径前缀
   */
  private String scheme;
  /**
   * 请求路径
   */
  private String path;
  /**
   * 菜单标题
   */
  private String icon;
  /**
   * 打开方式:_self窗口内,_blank新窗口
   */
  private String target;
  /**
   * 优先级 越小越靠前
   */
  private Integer priority;
  /**
   * 状态:0-无效 1-有效
   */
  private Integer status;


}
