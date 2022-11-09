package com.hf.op.service.inf.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.code.GenTemplateDto;


/**
 * 服务接口
 *
 * @author system
 * @date 2022-08-23
 */
public interface GenTemplateService {

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  ResponseMsg add(GenTemplateDto dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  ResponseMsg update(GenTemplateDto dto);

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  ResponseMsg page(GenTemplateDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  ResponseMsg detail(Long id);

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
  ResponseMsg status(GenTemplateDto dto);


}
