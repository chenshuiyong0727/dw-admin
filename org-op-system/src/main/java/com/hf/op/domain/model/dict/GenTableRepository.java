package com.hf.op.domain.model.dict;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.code.GenTableDto;
import com.hf.op.infrastructure.dto.code.gencode.DatabaseColumn;
import com.hf.op.infrastructure.dto.code.gencode.DatabaseTable;
import com.hf.op.infrastructure.vo.code.GenTablePageVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 数据接口
 *
 * @author system
 * @date 2022-08-23
 */
@Repository
public interface GenTableRepository extends BaseMapper<GenTableEntity> {

  /**
   * @description 查询标签列表
   * @method page
   * @date: 2022-08-23
   */
  IPage<GenTablePageVo> page(Page page, @Param("dto") GenTableDto dto);

  /**
   * 获得当前库中 所有表
   */
  IPage<DatabaseTable> findTables(Page page);

  /**
   * 获得当前库中 所有表
   */
  List<DatabaseTable> listTable(@Param("tableName") String tableName);


  /**
   * 获得当前表中 所有字段
   */
  List<DatabaseColumn> findColumns(DatabaseColumn column);
}
