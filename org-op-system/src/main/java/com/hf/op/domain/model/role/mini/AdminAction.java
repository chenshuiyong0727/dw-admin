package com.hf.op.domain.model.role.mini;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_action的实体类
 * @date 2022-06-15
 */
@Data
@TableName("admin_action")
public class AdminAction implements Serializable {

  /**
   * 资源ID
   */
  @TableId
  private String actionid;

  /**
   * 资源编码
   */
  private String actioncode;

  /**
   * 资源名称
   */
  private String actionname;

  /**
   * 资源描述
   */
  private String actiondesc;

  /**
   * 资源父节点
   */
  private String menuid;

  /**
   * 优先级 越小越靠前
   */
  private Integer priority;

  /**
   * 状态:0-无效 1-有效
   */
  private Integer status;

  /**
   * 创建时间
   */
  private Date createtime;

  /**
   * 更新时间
   */
  private Date updatetime;

  @Override
  public String toString() {
    return "AdminActionEntity{actionid:" + actionid
        + ",actioncode:" + actioncode
        + ",actionname:" + actionname
        + ",actiondesc:" + actiondesc
        + ",menuid:" + menuid
        + ",priority:" + priority
        + ",status:" + status
        + ",createtime:" + createtime
        + ",updatetime:" + updatetime + '}';
  }
}
