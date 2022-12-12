package com.hf.op.service.inf;

import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.department.GoodsOrderDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.infrastructure.dto.department.GoodsShelvesGoodsRqDto;
import java.util.List;

/**
 * 商品订单信息 服务接口
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
public interface GoodsOrderService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg putInStorage(GoodsOrderRqDto dto);
  ResponseMsg page(GoodsOrderRqDto dto);

  ResponseMsg addList(GoodsShelvesGoodsRqDto dto);

    /**
   * @description 新增
   * @method add
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg add(GoodsOrderDto dto);
  ResponseMsg sellGoods(GoodsOrderDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg update(GoodsOrderDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg detail(Long id);
  ResponseMsg indexData();
  ResponseMsg indexOrderData(GoodsOrderRqDto dto);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-11-15 17:39:00
   */
  ResponseMsg status(StatusDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-11-15 17:39:00
   */
  List<GoodsOrderExportDto> queryExportPage(GoodsOrderRqDto dto);
}
