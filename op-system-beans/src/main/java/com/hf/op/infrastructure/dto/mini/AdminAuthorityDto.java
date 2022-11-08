package com.hf.op.infrastructure.dto.mini;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_authority的实体类
 * @date 2022-06-15
 */
@Data
public class AdminAuthorityDto implements Serializable {


  private static final long serialVersionUID = -1184467892503401740L;
  @JsonProperty(value = "createTime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date createtime;
  @JsonProperty(value = "updateTime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date updatetime;
  @JsonProperty(value = "authorityId")
  private String authorityid;
  /**
   * 权限标识
   */
  @JsonProperty(value = "authority")
  private String authority;
  /**
   * 菜单资源ID
   */
  @JsonProperty(value = "menuId")
  private String menuid;
  /**
   * API资源ID
   */
  @JsonProperty(value = "apiId")
  private String apiid;
  /**
   * 操作资源ID
   */
  @JsonProperty(value = "actionId")
  private String actionid;
  /**
   * 状态
   */
  private Integer status;
  /**
   * 状态
   */
  private Integer syncType;


}
