package com.hf.op.infrastructure.generator.database.mysql;


import com.hf.op.infrastructure.generator.TypeFormatter;
import java.util.Arrays;
import java.util.Collections;

/**
 * MySQL 字段格式化判断
 *
 * @author Parker
 * @date 2021年5月26日14:51:50
 */
public class MySqlTypeFormatter implements TypeFormatter {

  @Override
  public boolean isBit(String columnType) {
    return isContains(Collections.singletonList("bit"), columnType);
  }

  @Override
  public boolean isBoolean(String columnType) {
    return false;
  }

  @Override
  public boolean isTinyint(String columnType) {
    return isContains(Collections.singletonList("tinyint"), columnType);
  }

  @Override
  public boolean isSmallint(String columnType) {
    return isContains(Collections.singletonList("smallint"), columnType);
  }

  @Override
  public boolean isInt(String columnType) {
    return !isLong(columnType) && isContains(Arrays.asList("int", "integer"), columnType);
  }

  @Override
  public boolean isLong(String columnType) {
    return !isVarchar(columnType) && isContains(Collections.singletonList("bigint"), columnType);
  }

  @Override
  public boolean isFloat(String columnType) {
    return isContains(Collections.singletonList("float"), columnType);
  }

  @Override
  public boolean isDouble(String columnType) {
    return isContains(Collections.singletonList("double"), columnType);
  }

  @Override
  public boolean isDecimal(String columnType) {
    return isContains(Collections.singletonList("decimal"), columnType);
  }

  @Override
  public boolean isVarchar(String columnType) {
    return isContains(Arrays.asList("CHAR", "VARCHAR", "TEXT"), columnType);
  }

  @Override
  public boolean isDatetime(String columnType) {
    return isContains(Arrays.asList("DATE", "TIME", "DATETIME", "TIMESTAMP"), columnType);
  }

  @Override
  public boolean isBlob(String columnType) {
    return isContains(Collections.singletonList("blob"), columnType);
  }

  @Override
  public boolean isJsonb(String columnType) {
    return false;
  }
}
