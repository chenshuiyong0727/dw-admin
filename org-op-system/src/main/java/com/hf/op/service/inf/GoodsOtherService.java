package com.hf.op.service.inf;

import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.department.GoodsOtherDto;
import com.hf.op.infrastructure.dto.department.GoodsOtherExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOtherRqDto;
import java.util.List;

/**
 * 其他收支 服务接口
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
public interface GoodsOtherService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg page(GoodsOtherRqDto dto);

    /**
   * @description 新增
   * @method add
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg add(GoodsOtherDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg update(GoodsOtherDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg detail(Long id);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-11-29 17:04:31
   */
  ResponseMsg status(StatusDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-11-29 17:04:31
   */
  List<GoodsOtherExportDto> queryExportPage(GoodsOtherRqDto dto);
}
