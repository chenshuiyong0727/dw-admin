package com.hf.op.infrastructure.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 获取行政区域数据
 * @Date 2022/02/14
 */
@Data
public class QueryComSysAreasDto implements Serializable {

  private static final long serialVersionUID = -2798853319671989262L;

  /**
   * 级别 0所有
   */
  public static final Integer AREA_LEVEL_ALL = 0;

  /**
   * 级别 0所有 1省份 2市 3区
   */
  private Integer level;

  /**
   * 父级id
   */
  private Long parentId;

}
