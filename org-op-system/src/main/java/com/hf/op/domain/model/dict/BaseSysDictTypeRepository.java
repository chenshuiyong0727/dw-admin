package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.ListSysDictTypePageVo;
import com.hf.op.infrastructure.dto.ListSysDictTypeVo;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.infrastructure.vo.SysDictRqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wpq
 * @function 字典类型
 * @Date 2021/12/13
 */
@Repository
public interface BaseSysDictTypeRepository extends BaseMapper<BaseSysDictTypeEntity> {

  /**
   * @description 查询标签列表
   * @method page
   * @date: 2022-08-23
   */
  IPage<ListSysDictTypePageVo> page(Page page, @Param("dto") ListSysDictTypeVo dto);

  /**
   * @description 查询标签列表
   * @method page
   * @date: 2022-08-23
   */
  IPage<ListSysDictVo> dictPage(Page page, @Param("dto") SysDictRqVo dto);

}
