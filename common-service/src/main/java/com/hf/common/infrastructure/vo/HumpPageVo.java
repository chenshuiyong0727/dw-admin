package com.hf.common.infrastructure.vo;

import java.util.List;
import lombok.Data;

/**
 * @author zengshibin
 * @function 分页视图对象驼峰写法
 * @date 2022/2/7
 */
@Data
public class HumpPageVo {

  private List list;

  private HumpPageInfo pageInfo;
}
