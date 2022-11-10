package com.hf.op.service.inf;

import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.department.GoodsBaseDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseExportDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseRqDto;
import java.util.List;

/**
 * 商品基本信息 服务接口
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
public interface GoodsBaseService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg page(GoodsBaseRqDto dto);

  /**
   * @description 新增
   * @method add
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg add(GoodsBaseDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg update(GoodsBaseDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg detail(Long id);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-11-08 11:10:33
   */
  ResponseMsg status(StatusDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-11-08 11:10:33
   */
  List<GoodsBaseExportDto> queryExportPage(GoodsBaseRqDto dto);
}
