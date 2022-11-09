package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @function 字典
 * @Date 2021/12/13
 */
@Repository
public interface BaseSysDictRepository extends BaseMapper<BaseSysDictEntity> {

  /**
   * 获取系统数据字典
   */
  List<ListSysDictVo> listSysDict();

  List<ListSysDictVo> listSysDictByTypeId(@Param("typeId") Long typeId);

  List<ListSysDictVo> listSysDictByTypeValue(@Param("typeValue") String typeValue);

}
