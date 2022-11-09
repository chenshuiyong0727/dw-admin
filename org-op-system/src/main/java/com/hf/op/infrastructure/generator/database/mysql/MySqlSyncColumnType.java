package com.hf.op.infrastructure.generator.database.mysql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hf.common.infrastructure.util.ListDistinctUtil;
import com.hf.op.infrastructure.generator.FieldTypeAttribute;
import com.hf.op.infrastructure.generator.converter.ColumnTypeConverter;
import com.hf.op.infrastructure.generator.converter.JavaColumnTypeConverter;
import com.hf.op.infrastructure.generator.enums.TypeEnum;
import java.util.List;
import java.util.Map;

/**
 * MySQL 字段类型 判断
 *
 * @author parker
 * @date 2020-11-18 13:21
 */
public enum MySqlSyncColumnType {

  /**
   * 实例对象
   */
  INSTANCE;

  /**
   * 字段类型
   */
  private final Map<String, FieldTypeAttribute> fieldTypeMap = Maps.newHashMap();
  /**
   * 类型转换器
   */
  private final ColumnTypeConverter columnTypeConverter = new JavaColumnTypeConverter();

  MySqlSyncColumnType() {
    fieldTypeMap.put(TypeEnum.BIT.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.TINYINT.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.SMALLINT.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.INT.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.INTEGER.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.BIGINT.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.FLOAT.getType(), new FieldTypeAttribute(true, true));
    fieldTypeMap.put(TypeEnum.DOUBLE.getType(), new FieldTypeAttribute(true, true));
    fieldTypeMap.put(TypeEnum.DECIMAL.getType(), new FieldTypeAttribute(true, true));
    fieldTypeMap.put(TypeEnum.CHAR.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.VARCHAR.getType(), new FieldTypeAttribute(true, false));
    fieldTypeMap.put(TypeEnum.TEXT.getType(), new FieldTypeAttribute(false, false));
    fieldTypeMap.put(TypeEnum.DATE.getType(), new FieldTypeAttribute(false, false));
    fieldTypeMap.put(TypeEnum.DATETIME.getType(), new FieldTypeAttribute(false, false));
    fieldTypeMap.put(TypeEnum.TIMESTAMP.getType(), new FieldTypeAttribute(false, false));
    fieldTypeMap.put(TypeEnum.BLOB.getType(), new FieldTypeAttribute(false, false));
  }


  /**
   * 获得全部类型
   *
   * @return List
   */
  public List<String> getFieldTypes() {
    List<String> fieldTypes = Lists.newArrayListWithCapacity(fieldTypeMap.size());
    fieldTypes.addAll(fieldTypeMap.keySet());
    return ListDistinctUtil.distinct(fieldTypes);
  }

  /**
   * 获得全部类型对应Java类型
   *
   * @return List
   */
  public Map<String, String> getJavaFieldTypes() {
    List<String> fieldTypes = this.getFieldTypes();
    Map<String, String> fieldJavaTypeMap = Maps.newHashMapWithExpectedSize(fieldTypes.size());
    for (String fieldType : fieldTypes) {
      fieldJavaTypeMap.put(fieldType, columnTypeConverter.convertType(
          TypeEnum.getType(fieldType)));
    }
    return fieldJavaTypeMap;
  }

  /**
   * 获得全部类型对应Java类型集合（兜底String 类型）
   *
   * @return List
   */
  public Map<String, List<String>> getJavaFieldTypesBySafety() {
    List<String> fieldTypes = this.getFieldTypes();
    Map<String, List<String>> fieldJavaTypeMap = Maps.newHashMapWithExpectedSize(fieldTypes.size());
    for (String fieldType : fieldTypes) {
      fieldJavaTypeMap.put(fieldType, columnTypeConverter.convertTypeBySafety(
          TypeEnum.getType(fieldType)));
    }
    return fieldJavaTypeMap;
  }

  public FieldTypeAttribute getAttr(String fieldType) {
    return fieldTypeMap.get(fieldType);
  }

}
