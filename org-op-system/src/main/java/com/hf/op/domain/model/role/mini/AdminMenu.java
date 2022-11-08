package com.hf.op.domain.model.role.mini;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author system
 * @function admin_menu的实体类
 * @date 2022-06-15
 */
@Data
@TableName("admin_menu")
public class AdminMenu implements Serializable {

  /**
   * 菜单Id
   */
  @TableId
  private String menuid;

  /**
   * 父级菜单
   */
  private String parentid;

  /**
   * 菜单编码
   */
  private String menucode;

  /**
   * 菜单名称
   */
  private String menuname;

  /**
   * 描述
   */
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
    return "AdminMenuEntity{menuid:" + menuid
        + ",parentid:" + parentid
        + ",menucode:" + menucode
        + ",menuname:" + menuname
        + ",menudesc:" + menudesc
        + ",scheme:" + scheme
        + ",path:" + path
        + ",icon:" + icon
        + ",target:" + target
        + ",priority:" + priority
        + ",status:" + status
        + ",createtime:" + createtime
        + ",updatetime:" + updatetime + '}';
  }
}
