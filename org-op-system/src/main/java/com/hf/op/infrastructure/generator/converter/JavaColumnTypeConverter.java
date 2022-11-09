package com.hf.op.infrastructure.generator.converter;


import com.google.common.collect.Lists;
import com.hf.common.infrastructure.util.ListDistinctUtil;
import com.hf.op.infrastructure.generator.enums.JavaType;
import com.hf.op.infrastructure.generator.enums.TypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java 字段类型
 *
 * @author Parker
 * @date 2021年5月26日14:25:47
 */
public class JavaColumnTypeConverter implements ColumnTypeConverter {

  private static final Map<TypeEnum, JavaType> TYPE_MAP = new HashMap<>(64);

  static {
    TYPE_MAP.put(TypeEnum.BIT, JavaType.BOOLEAN);
    TYPE_MAP.put(TypeEnum.TINYINT, JavaType.BYTE);
    TYPE_MAP.put(TypeEnum.SMALLINT, JavaType.SHORT);
    TYPE_MAP.put(TypeEnum.INT, JavaType.INTEGER);
    TYPE_MAP.put(TypeEnum.INTEGER, JavaType.INTEGER);
    TYPE_MAP.put(TypeEnum.BIGINT, JavaType.LONG);
    TYPE_MAP.put(TypeEnum.FLOAT, JavaType.FLOAT);
    TYPE_MAP.put(TypeEnum.DOUBLE, JavaType.DOUBLE);
    TYPE_MAP.put(TypeEnum.DECIMAL, JavaType.BIG_DECIMAL);
    TYPE_MAP.put(TypeEnum.CHAR, JavaType.STRING);
    TYPE_MAP.put(TypeEnum.VARCHAR, JavaType.STRING);
    TYPE_MAP.put(TypeEnum.TEXT, JavaType.STRING);
    TYPE_MAP.put(TypeEnum.DATE, JavaType.DATE);
    TYPE_MAP.put(TypeEnum.DATETIME, JavaType.DATE);
    TYPE_MAP.put(TypeEnum.TIMESTAMP, JavaType.DATE);
    TYPE_MAP.put(TypeEnum.BLOB, JavaType.BYTE_ARRAY);
    TYPE_MAP.put(TypeEnum.JSONB, JavaType.MAP_OBJECT);
    TYPE_MAP.put(TypeEnum.BOOLEAN, JavaType.BOOLEAN);
  }

  @Override
  public String convertType(TypeEnum type) {
    return TYPE_MAP.getOrDefault(type, TYPE_MAP.get(TypeEnum.VARCHAR)).getType();
  }

  @Override
  public List<String> convertTypeBySafety(TypeEnum type) {
    String currType = TYPE_MAP.getOrDefault(type, TYPE_MAP.get(TypeEnum.VARCHAR)).getType();
    String defType = TYPE_MAP.get(TypeEnum.VARCHAR).getType();
    List<String> typeList = Lists.newArrayListWithCapacity(2);
    typeList.add(currType);
    typeList.add(defType);
    return ListDistinctUtil.distinct(typeList);
  }
}
