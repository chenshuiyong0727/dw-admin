package com.hf.common.infrastructure.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @author zengshibin
 * @function 分页视图对象下划线写法
 * @date 2022/2/7
 */
@Data
public class UnderlinePageVo {

  private List list;

  @JsonProperty(value = "page_info")
  private UnderlinePageInfo pageInfo;
}
