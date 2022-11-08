package com.hf.common.infrastructure.vo;

import lombok.Data;

/**
 * @author zengshibin
 * @function 分页信息驼峰写法
 * @date 2022/2/7
 */
@Data
public class HumpPageInfo {

  private long totalCount;

  private long pageSize;

  private long pageNum;
}
