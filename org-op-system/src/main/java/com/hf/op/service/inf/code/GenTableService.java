package com.hf.op.service.inf.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.code.GenTableColumnDto;
import com.hf.op.infrastructure.dto.code.GenTableDto;
import com.hf.op.infrastructure.dto.code.gencode.DatabaseColumn;
import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import java.util.List;


/**
 * 服务接口
 *
 * @author system
 * @date 2022-08-23
 */
public interface GenTableService {

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  Long add(GenTableDto dto);

  /**
   * 获得当前表中 所有字段
   *
   * @param dbName 数据库名
   * @param tableName 表名
   * @return List
   */
  List<DatabaseColumn> findColumns(String dbName, String tableName);

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  ResponseMsg importTables(String[] tableNameArray);

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  ResponseMsg update(GenTableAndColumnModel dto);

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  ResponseMsg page(GenTableDto dto);

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  ResponseMsg getTables(GenTableDto dto);

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  ResponseMsg detail(Long id);

  GenTableAndColumnModel getGenTableAndColumnModel(Long id);

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
  ResponseMsg status(GenTableDto dto);


  /**
   * 新增表数据
   */
  void insertAny(GenTableDto genTableDto, List<GenTableColumnDto> genTableColumnDtos);
}
