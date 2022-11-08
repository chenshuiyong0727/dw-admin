package com.hf.common.infrastructure.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 基础业务表Entity
 * @date 2021/2/04
 */
@Data
public class BaseEntity {

  private final static Long SYS_USER_ID = 1L;

  private final static String SYS_USER_NAME = "SYSTEM";

  /**
   * 编号
   */
  public Long id;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  public Integer dataStatus = 1;

  /**
   * 创建时间
   */
  public LocalDateTime createTime = null;

  /**
   * 创建人编号,取自登录表的登录编号
   */
  public Long createUserId = null;

  /**
   * 创建人名称
   */
  public String createUserName = null;

  /**
   * 修改时间
   */
  //@Version
  public LocalDateTime updateTime = null;

  /**
   * 修改人编号,取自登录表的登录编号
   */
  public Long updateUserId = null;

  /**
   * 修改人名称
   */
  public String updateUserName = null;

  /**
   * 设置系统默认值
   */
  public void setDefaultCreateInfo() {
    createUserId = SYS_USER_ID;
    updateUserId = SYS_USER_ID;
    createUserName = SYS_USER_NAME;
    updateUserName = SYS_USER_NAME;
  }

  /**
   * 设置系统默认值
   */
  public void setDefaultInfo(Long userId, String userName) {
    createUserId = userId;
    updateUserId = userId;
    createUserName = userName;
    updateUserName = userName;
    createTime = LocalDateTime.now();
    updateTime = LocalDateTime.now();
  }

  /**
   * 设置更新默认值
   */
  public void setUpdateDefaultInfo(Long userId, String userName) {
    updateUserId = userId;
    updateUserName = userName;
    updateTime = LocalDateTime.now();
  }

}
