package com.hf.common.infrastructure.dto;

import lombok.Data;

/**
 * @author chensy
 * @function  更新状态dto
 * @date 2021/2/04
 */
@Data
public class StatusDto {

  /**
   * 编号
   */
  public Long id;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  public Integer dataStatus = 1;

  /**
  * 1 在售 0 下架
  * */
  private Integer status;
}
