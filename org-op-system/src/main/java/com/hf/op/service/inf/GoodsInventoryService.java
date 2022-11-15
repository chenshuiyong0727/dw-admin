package com.hf.op.service.inf;

import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.department.GoodsInventoryDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryExportDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryRqDto;
import com.hf.op.infrastructure.dto.department.GoodsInventorySizeDto;
import com.hf.op.infrastructure.dto.department.GoodsShelvesGoodsRqDto;
import java.util.List;

/**
 * 商品库存 服务接口
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
public interface GoodsInventoryService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg page(GoodsInventoryRqDto dto);
  ResponseMsg pageGoods(GoodsInventoryRqDto dto);

    /**
   * @description 新增
   * @method add
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg add(GoodsInventoryDto dto);
  ResponseMsg shelvesGoods(GoodsShelvesGoodsRqDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg update(GoodsInventorySizeDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg detail(Long id);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-11-12 20:10:34
   */
  ResponseMsg status(StatusDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-11-12 20:10:34
   */
  List<GoodsInventoryExportDto> queryExportPage(GoodsInventoryRqDto dto);
}
