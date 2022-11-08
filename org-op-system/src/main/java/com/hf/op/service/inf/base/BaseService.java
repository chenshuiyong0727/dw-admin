package com.hf.op.service.inf.base;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;

/**
 * @author wpq
 * @function 基础信息
 * @Date 2021/12/10
 */
public interface BaseService {

  ResponseMsg getLoginVerityCode(QueryVerityCodeLoginDto baseComLoginDto);

  ResponseMsg listSysDict();


}
