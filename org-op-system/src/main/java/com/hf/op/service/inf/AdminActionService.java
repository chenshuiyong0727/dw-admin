package com.hf.op.service.inf;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.mini.AdminActionDto;


/**
 * 服务接口
 *
 * @author system
 * @date 2022-06-15
 */
public interface AdminActionService {

  /**
   * @description 新增
   * @method add
   * @date: 2022-06-15
   */
  ResponseMsg syncAction(AdminActionDto dto) throws Exception;


}
