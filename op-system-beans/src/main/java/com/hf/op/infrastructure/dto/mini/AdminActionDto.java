package com.hf.op.infrastructure.dto.mini;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_action的实体类
 * @date 2022-06-15
 */
@Data
public class AdminActionDto implements Serializable {


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
   * 资源ID
   */
  @JsonProperty(value = "actionId")
  private String actionid;
  /**
   * 资源编码
   */
  @JsonProperty(value = "actionCode")
  private String actioncode;
  /**
   * 资源名称
   */
  @JsonProperty(value = "actionName")
  private String actionname;
  /**
   * 资源描述
   */
  @JsonProperty(value = "actionDesc")
  private String actiondesc;
  /**
   * 资源父节点
   */
  @JsonProperty(value = "menuId")
  private String menuid;
  /**
   * 优先级 越小越靠前
   */
  private Integer priority;
  /**
   * 状态:0-无效 1-有效
   */
  private Integer status;

}
