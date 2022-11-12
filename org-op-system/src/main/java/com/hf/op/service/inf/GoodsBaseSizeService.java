package com.hf.op.service.inf;

import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.domain.model.user.OpSysUserEntity;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeExportDto;
import com.hf.op.infrastructure.dto.department.GoodsBaseSizeRqDto;
import java.util.List;

/**
 * 商品尺码关系 服务接口
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
public interface GoodsBaseSizeService {

  /**
   * @description 分页
   * @method page
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg page(GoodsBaseSizeRqDto dto);

    /**
   * @description 新增
   * @method add
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg add(GoodsBaseSizeDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg update(GoodsBaseSizeDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg detail(Long id);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg remove(Long id);

  /**
   * @description 批量移除数据
   * @method batchRemove
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg batchRemove(List<Long> ids);

  /**
   * @description 变更状态
   * @date: 2022-11-12 16:48:45
   */
  ResponseMsg status(StatusDto dto);

  /**
   * @description 导出excel列表
   * @date: 2022-11-12 16:48:45
   */
  List<GoodsBaseSizeExportDto> queryExportPage(GoodsBaseSizeRqDto dto);


  /**
   * 用户绑定角色
   */
  void addList(  Long id , List<Long> sizeList);
}
