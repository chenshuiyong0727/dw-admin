package com.hf.op.service.inf.code;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.code.GenDemoDto;
import com.hf.op.infrastructure.dto.code.GenDemoExportDto;
import com.hf.op.infrastructure.dto.code.GenDemoRqDto;
import java.util.List;

/**
 * 案例 服务接口
 *
 * @author chensy
 * @date 2022-09-08 15:08:04
 */
public interface GenDemoService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg page(GenDemoRqDto dto);

  /**
   * @description 新增
   * @method add
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg add(GenDemoDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg update(GenDemoDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg detail(Long id);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-09-08 15:08:04
   */
  ResponseMsg status(GenDemoDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-09-08 15:08:04
   */
  List<GenDemoExportDto> queryExportPage(GenDemoRqDto dto);
}
