package com.hf.common.infrastructure.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author chensy
 * @function 基础业务dto
 * @date 2021/2/04
 */
@Data
public class BaseDto {

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


}
