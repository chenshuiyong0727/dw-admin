package com.hf.op.service.inf.base;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.ListSysDictTypeVo;
import com.hf.op.infrastructure.dto.QueryVerityCodeLoginDto;
import com.hf.op.infrastructure.vo.SysDictRqVo;

/**
 * @author wpq
 * @function 基础信息
 * @Date 2021/12/10
 */
public interface BaseService {

  ResponseMsg getLoginVerityCode(QueryVerityCodeLoginDto baseComLoginDto);

  ResponseMsg listSysDict();

  ResponseMsg listSysDictFront();


  ResponseMsg listSysDictByTypeValue(String typeValue);

  ResponseMsg listSysDictByTypeId(Long typeId);

  ResponseMsg listSysDictType(ListSysDictTypeVo dto);

  ResponseMsg dictTypeDetail(ListSysDictTypeVo dto);

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  ResponseMsg addDictType(ListSysDictTypeVo dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  ResponseMsg updateDictType(ListSysDictTypeVo dto);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  ResponseMsg removeDictType(Long id);

  ResponseMsg dictPage(SysDictRqVo dto);

  ResponseMsg dictDetail(SysDictRqVo dto);

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  ResponseMsg addDictDetail(SysDictRqVo dto);

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  ResponseMsg updateDictDetail(SysDictRqVo dto);

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  ResponseMsg removeDictDetail(Long id);
}
