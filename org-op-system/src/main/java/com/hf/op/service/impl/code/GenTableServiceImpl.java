package com.hf.op.service.impl.code;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.constant.DataStatusEnum;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.GeneratorMsg;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.PageUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.TypeConversionUtil;
import com.hf.common.service.BatchCrudService;
import com.hf.op.domain.model.dict.GenTableColumnEntity;
import com.hf.op.domain.model.dict.GenTableColumnRepository;
import com.hf.op.domain.model.dict.GenTableEntity;
import com.hf.op.domain.model.dict.GenTableRepository;
import com.hf.op.domain.model.dict.BaseSysDictRepository;
import com.hf.op.infrastructure.dto.ListSysDictVo;
import com.hf.op.infrastructure.dto.code.GenTableColumn1Model;
import com.hf.op.infrastructure.dto.code.GenTableColumnDto;
import com.hf.op.infrastructure.dto.code.GenTableDto;
import com.hf.op.infrastructure.dto.code.gencode.DatabaseColumn;
import com.hf.op.infrastructure.dto.code.gencode.DatabaseTable;
import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTableColumnModel;
import com.hf.op.infrastructure.generator.enums.DictType;
import com.hf.op.service.inf.code.GenTableService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 服务接口实现
 *
 * @author system
 * @date 2022-08-23
 */
@Slf4j
@Service
public class GenTableServiceImpl extends
    BatchCrudService<GenTableRepository, GenTableEntity> implements
    GenTableService {

  private GenTableRepository genTableRepository;

  private GenTableColumnRepository genTableColumnRepository;

  private GenTableColumnServiceImpl genTableColumnServiceImpl;

  private BaseSysDictRepository baseSysDictRepository;

  public GenTableServiceImpl(BaseSysDictRepository baseSysDictRepository,
      GenTableRepository genTableRepository,
      GenTableColumnServiceImpl genTableColumnServiceImpl,
      GenTableColumnRepository genTableColumnRepository) {
    this.baseSysDictRepository = baseSysDictRepository;
    this.genTableColumnServiceImpl = genTableColumnServiceImpl;
    this.genTableColumnRepository = genTableColumnRepository;
    this.genTableRepository = genTableRepository;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public Long add(GenTableDto dto) {
    Long id = createId();
    GenTableEntity entity = new GenTableEntity();
    BeanUtils.copyProperties(dto, entity);
    entity.setId(id);
    setCreateUser(entity);
    genTableRepository.insert(entity);
    return id;
  }


  @Override
  public List<DatabaseColumn> findColumns(String dbName, String tableName) {
    DatabaseColumn entity = new DatabaseColumn();
    entity.setDbName(dbName);
    entity.setTableName(tableName);

    List<DatabaseColumn> columns = genTableRepository.findColumns(entity);

    // 设置字段长度
    for (DatabaseColumn column : columns) {
      // MySQL 中 这两个 有一个会代表为当前字段长度
      Integer len1 = column.getColumnLength();
      Integer len2 = column.getColumnPrecision();
      if (len1 == null && len2 != null) {
        column.setColumnLength(len2);
      }
      // 如果小数位不为空 则需要 减掉小数位置
      if (column.getColumnScale() != null) {
        column.setColumnLength(
            column.getColumnLength() - column.getColumnScale()
        );
      }
    }

    return columns;
  }

  /**
   * @description 新增
   * @method add
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg importTables(String[] tableNames) {
    for (String tableName : tableNames) {
      // 获得当前表
      DatabaseTable table = null;
      List<DatabaseTable> tables = genTableRepository.listTable(tableName);
      if (tables != null && !tables.isEmpty()) {
        table = tables.get(0);
      }
      if (table == null) {
        String msg = StrUtil
            .format(GeneratorMsg.EXCEPTION_IMPORT_TABLE_NULL.getMessage(), tableName);
        return ResponseMsg
            .createBusinessErrorResp(GeneratorMsg.EXCEPTION_IMPORT_TABLE_NULL.getCode(),
                msg);
      }
      // 获得表字段
      List<DatabaseColumn> columns = this.findColumns(table.getDbName(), tableName);
      if (CollUtil.isEmpty(columns)) {
        // 暂未获得表字段
        return ResponseMsg
            .createBusinessErrorResp(GeneratorMsg.EXCEPTION_IMPORT_FIELD_NULL.getCode(),
                GeneratorMsg.EXCEPTION_IMPORT_FIELD_NULL.getMessage());
      }

      List<GenTableColumnDto> genTableColumnDtos = new ArrayList<>();
      for (int i = 0; i < columns.size(); i++) {
        DatabaseColumn column = columns.get(i);
        GenTableColumnDto genTableColumnDto = new GenTableColumnDto();
        genTableColumnDto.setFieldName(column.getColumnName());
        genTableColumnDto.setFieldType(column.getColumnType());
        genTableColumnDto.setFieldLength(column.getColumnLength());
        genTableColumnDto.setFieldPrecision(column.getColumnScale());
        genTableColumnDto.setFieldComments(column.getColumnComment());
        genTableColumnDto.setIzPk(column.getIzPk());
        genTableColumnDto.setIzNotNull(column.getIzNotNull());
        genTableColumnDto.setSort(i);
        // 赋默认值
        String javaType = TypeConversionUtil.getJavaType(column.getColumnType());
        genTableColumnDto.setJavaType(javaType);
        if (DictType.DICT_TYPE.getType().equals(column.getColumnName())) {
          genTableColumnDto.setDictTypeCode(DictType.DICT_TYPE.getValue());
        }
        genTableColumnDtos.add(genTableColumnDto);
      }
      // 生成本地数据
      GenTableDto genTableDto = new GenTableDto();
      genTableDto.setComments(table.getTableComments());
      genTableDto.setTableName(table.getTableName());
      genTableDto.setOldTableName(table.getTableName());
      genTableDto.setIzSync(0);
      genTableDto.setDbName(table.getDbName());
      genTableDto.setJdbcType(CommonConstant.DEFAULT_DB_MYSQL);
      genTableDto.setTableType("0");
      this.insertAny(genTableDto, genTableColumnDtos);
    }
    return new ResponseMsg();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertAny(GenTableDto genTableDto, List<GenTableColumnDto> genTableColumnDtos) {
    // 保存表头数据
    Long tableId = this.add(genTableDto);
    if (tableId != null) {
      // 删除旧字段 全部新增
      LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(GenTableColumnEntity::getTableId, tableId);
      genTableColumnRepository.delete(queryWrapper);
      if (CollectionUtils.isEmpty(genTableColumnDtos)) {
        return;
      }
      // 保存 表结构数据
      List<GenTableColumnEntity> columnList = ListBeanUtil
          .listCopy(genTableColumnDtos, GenTableColumnEntity.class);
      for (GenTableColumnEntity tableColumnModel : columnList) {
        tableColumnModel.setTableId(tableId);
        setCreateUser(tableColumnModel);
      }
      genTableColumnServiceImpl.saveBatch(columnList);
    }
  }

  /**
   * @description 更新
   * @method update
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg update(GenTableAndColumnModel model) {
    GenTableEntity entity = genTableRepository.selectById(model.getId());
    if (entity == null) {
      return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
          "更新失败");
    }
    // 唯一验证
    boolean verificationByTableName = this.uniqueVerificationByTableName(entity);
    if (!verificationByTableName) {
      // 重复
      return ResponseMsg.createBusinessErrorResp(GeneratorMsg.EXCEPTION_TABLE_NAME_REPEAT.getCode(),
          GeneratorMsg.EXCEPTION_TABLE_NAME_REPEAT.getMessage());
    }
    // 更新表
    entity.setOldTableName(entity.getTableName());
    BeanUtils.copyProperties(model, entity, "id", "oldTableName");
    entity.setIzSync(0);
    setUpdateUser(entity);
    genTableRepository.updateById(entity);
    // 删除字段
    LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTableColumnEntity::getTableId, model.getId());
    genTableColumnRepository.delete(queryWrapper);
    if (CollectionUtils.isEmpty(model.getColumnList())) {
      return new ResponseMsg().setData(new HashMap().put("id", model.getId()));
    }
    // 保存 表结构数据
    List<GenTableColumnEntity> columnList = ListBeanUtil
        .listCopy(model.getColumnList(), GenTableColumnEntity.class);
    for (GenTableColumnEntity tableColumnModel : columnList) {
      if (!CollectionUtils.isEmpty(tableColumnModel.getValidateTypeList())) {
        // list 转string
        tableColumnModel
            .setValidateType(StringUtils.join(tableColumnModel.getValidateTypeList(), ","));
      }
      tableColumnModel.setTableId(model.getId());
      setCreateUser(tableColumnModel);
    }
    genTableColumnServiceImpl.saveBatch(columnList);
    return new ResponseMsg().setData(new HashMap().put("id", model.getId()));
  }

  private boolean uniqueVerificationByTableName(GenTableEntity model) {
    if (model == null) {
      return false;
    }
    LambdaQueryWrapper<GenTableEntity> wrapper = new LambdaQueryWrapper();
    // code 唯一
    wrapper.eq(GenTableEntity::getTableName, model.getTableName());
    // 重复校验排除自身
    if (model.getId() != null) {
      wrapper.ne(GenTableEntity::getId, model.getId());
    }
    return super.count(wrapper) == 0;
  }

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg page(GenTableDto dto) {
    IPage ipage = genTableRepository.page(new Page(dto.getPageNum(), dto.getPageSize()), dto);
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 分页
   * @method page
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg getTables(GenTableDto dto) {
    // 获得当前库下表集合
    IPage ipage = genTableRepository.findTables(new Page(dto.getPageNum(), dto.getPageSize()));
    return new ResponseMsg().setData(PageUtil.getHumpPage(ipage));
  }

  /**
   * @description 获取详情
   * @method detail
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg detail(Long id) {
    GenTableAndColumnModel vo = this.getGenTableAndColumnModel(id);
    if (vo != null) {
      return new ResponseMsg().setData(vo);
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "查询失败");
  }

  @Override
  public GenTableAndColumnModel getGenTableAndColumnModel(Long id) {
    GenTableEntity entity = genTableRepository.selectById(id);
    if (entity != null) {
      GenTableAndColumnModel vo = new GenTableAndColumnModel();
      BeanUtils.copyProperties(entity, vo);
      GenTableDto genTableDto = new GenTableDto();
      BeanUtils.copyProperties(entity, genTableDto);
      vo.setGenTableDto(genTableDto);
      LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(GenTableColumnEntity::getTableId, id);
      queryWrapper.orderBy(true, true, GenTableColumnEntity::getSort);
      List<GenTableColumnEntity> list = genTableColumnRepository.selectList(queryWrapper);
      if (CollectionUtils.isEmpty(list)) {
        return null;
      }
      List<GenTableColumnModel> models = ListBeanUtil.listCopy(list, GenTableColumnModel.class);
      for (GenTableColumnModel columnModel : models) {
        if (DictType.SHOW_TYPE_DICT.getValue().equals(columnModel.getShowType()) || StringUtilLocal
            .isNotEmpty(columnModel.getDictTypeCode())) {
          columnModel.setExcelDicts(this.getDicts(columnModel.getDictTypeCode()));
        }
      }
      vo.setColumnList(models);
      List<GenTableColumn1Model> model1s = new ArrayList<>();
      for (GenTableColumnModel columnModel : models) {
        GenTableColumn1Model model = new GenTableColumn1Model();
        BeanUtils.copyProperties(columnModel, model);
        if (StringUtilLocal.isNotEmpty(columnModel.getIzShowForm())) {
          model.setIzShowForm(Integer.parseInt(columnModel.getIzShowForm()));
        }
        if (StringUtilLocal.isNotEmpty(columnModel.getIzShowList())) {
          model.setIzShowList(Integer.parseInt(columnModel.getIzShowList()));
        }
        if (StringUtilLocal.isNotEmpty(columnModel.getValidateType()) && CollectionUtils
            .isEmpty(columnModel.getValidateTypeList())) {
          List<String> validateTypeList = Convert
              .toList(String.class, columnModel.getValidateType());
          model.setValidateTypeList(validateTypeList);
        }
        model1s.add(model);
      }
      vo.setColumnList1(model1s);
      String serviceName = TypeConversionUtil.getServiceName(vo.getDbName());
      vo.setServiceName(serviceName);
      return vo;
    }
    return null;
  }

  private String getDicts(String dictTypeId) {
    // listmap 转 对象
    List<ListSysDictVo> list = baseSysDictRepository.listSysDictByTypeValue(dictTypeId);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    String dict = "";
    for (ListSysDictVo vo : list) {
      String val = "\"" + vo.getFieldName() + "_" + vo.getFieldValue() + "\"" + ",";
      dict = dict + val;
    }
    dict = dict.substring(0, dict.length() - 1);
    return dict;
  }

  /**
   * @description 移除数据
   * @method remove
   * @date: 2022-08-23
   */
  @Override
  public ResponseMsg remove(Long id) {
    if (genTableRepository.deleteById(id) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", id));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "删除失败");
  }

  /**
   * @description 变更状态
   * @method 2022-08-23
   */
  @Override
  public ResponseMsg status(GenTableDto dto) {
    LambdaQueryWrapper<GenTableEntity> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.eq(GenTableEntity::getId, dto.getId())
        .between(GenTableEntity::getDataStatus, DataStatusEnum.FORBIDDEN.getCode(),
            DataStatusEnum.ENABLE.getCode());
    GenTableEntity entity = new GenTableEntity();
    entity.setDataStatus(dto.getDataStatus());
    setUpdateUser(entity);
    if (genTableRepository.update(entity, queryWrapper) > 0) {
      return new ResponseMsg().setData(new HashMap().put("id", dto.getId()));
    }
    return ResponseMsg.createBusinessErrorResp(BusinessRespCodeEnum.RESULT_SYSTEM_ERROR.getCode(),
        "变更失败");
  }
}
