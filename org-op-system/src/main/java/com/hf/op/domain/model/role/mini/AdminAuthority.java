package com.hf.op.domain.model.role.mini;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_authority的实体类
 * @date 2022-06-15
 */
@Data
@TableName("admin_authority")
public class AdminAuthority implements Serializable {

  /**
   * 权限ID
   */
  @TableId
  private String authorityid;

  /**
   * 权限标识
   */
  private String authority;

  /**
   * 菜单资源ID
   */
  private String menuid;

  /**
   * API资源ID
   */
  private String apiid;

  /**
   * 功能ID
   */
  private String actionid;

  /**
   *
   */
  private Long functionid;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 创建时间
   */
  private Date createtime;

  /**
   * 修改时间
   */
  private Date updatetime;

  @Override
  public String toString() {
    return "AdminAuthorityEntity{authorityid:" + authorityid
        + ",authority:" + authority
        + ",menuid:" + menuid
        + ",apiid:" + apiid
        + ",actionid:" + actionid
        + ",functionid:" + functionid
        + ",status:" + status
        + ",createtime:" + createtime
        + ",updatetime:" + updatetime + '}';
  }
}
