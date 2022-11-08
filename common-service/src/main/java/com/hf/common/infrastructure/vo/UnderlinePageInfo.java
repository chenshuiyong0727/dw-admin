package com.hf.common.infrastructure.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zengshibin
 * @function 分页信息下划线写法
 * @date 2022/2/7
 */
@Data
public class UnderlinePageInfo {

  @JsonProperty(value = "total_count")
  private long totalCount;

  @JsonProperty(value = "page_size")
  private long pageSize;

  @JsonProperty(value = "page_num")
  private long pageNum;
}
