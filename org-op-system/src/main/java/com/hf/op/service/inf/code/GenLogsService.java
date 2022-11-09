package com.hf.op.service.inf.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.domain.model.code.GenLogsEntity;
import com.hf.op.infrastructure.dto.code.GenLogsDto;
import javax.servlet.http.HttpServletResponse;


/**
 * 服务接口
 *
 * @author system
 * @date 2022-08-23
 */
public interface GenLogsService {

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  GenLogsEntity add(GenLogsDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  ResponseMsg update(GenLogsDto dto);

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  ResponseMsg page(GenLogsDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  ResponseMsg detail(Long id);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  ResponseMsg getByTableId(Long tableId);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  ResponseMsg remove(Long id);

  /**
   * @description 变更状态
   * @method 2022-08-23
   */
  ResponseMsg status(GenLogsDto dto);


  /**
   * 代码生成
   *
   * @param model 模型
   * @param response response
   */
  void create(GenLogsDto model, HttpServletResponse response);
}
