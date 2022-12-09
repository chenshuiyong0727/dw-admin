package com.hf.op.infrastructure.vo;

import com.hf.common.infrastructure.vo.HumpPageInfo;
import java.util.List;
import lombok.Data;

/**
 * @author zengshibin
 * @function 分页视图对象驼峰写法
 * @date 2022/2/7
 */
@Data
public class HumpInventoryPageVo {

  private List list;

  private HumpPageInfo pageInfo;

  private GoodsInventoryPageVo goodsInventoryPageVo;
}
