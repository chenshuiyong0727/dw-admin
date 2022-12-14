package com.hf.op.infrastructure.generator;

import cn.hutool.core.collection.CollUtil;
import com.hf.op.infrastructure.generator.enums.TypeEnum;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 将各数据库类型格式化成统一的类型
 *
 * @author Parker
 * @date 2021年5月26日14:45:15
 */
public interface TypeFormatter {

  /**
   * 转化格式
   *
   * @param columnType 字段类型
   * @return String
   */
  default TypeEnum format(String columnType) {
    if (isBit(columnType)) {
      return TypeEnum.BIT;
    }
    if (isBoolean(columnType)) {
      return TypeEnum.BOOLEAN;
    }
    if (isTinyint(columnType)) {
      return TypeEnum.TINYINT;
    }
    if (isSmallint(columnType)) {
      return TypeEnum.SMALLINT;
    }
    if (isInt(columnType)) {
      return TypeEnum.INT;
    }
    if (isLong(columnType)) {
      return TypeEnum.BIGINT;
    }
    if (isFloat(columnType)) {
      return TypeEnum.FLOAT;
    }
    if (isDouble(columnType)) {
      return TypeEnum.DOUBLE;
    }
    if (isDecimal(columnType)) {
      return TypeEnum.DECIMAL;
    }
    if (isJsonb(columnType)) {
      return TypeEnum.JSONB;
    }
    if (isVarchar(columnType)) {
      return TypeEnum.VARCHAR;
    }
    if (isDatetime(columnType)) {
      return TypeEnum.DATETIME;
    }
    if (isBlob(columnType)) {
      return TypeEnum.BLOB;
    }

    return TypeEnum.VARCHAR;
  }

  /**
   * 比较 是否包含
   *
   * @param columnTypes 类型集合
   * @param type 当前类型
   * @return boolean
   */
  default boolean isContains(List<String> columnTypes, String type) {
    if (CollUtil.isEmpty(columnTypes)) {
      return false;
    }
    for (String columnType : columnTypes) {
      if (StringUtils.equalsIgnoreCase(columnType, type)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 是否二进制
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isBit(String columnType);

  /**
   * 是否布尔类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isBoolean(String columnType);

  /**
   * 是否微小整数类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isTinyint(String columnType);

  /**
   * 是否小整数类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isSmallint(String columnType);

  /**
   * 是否整数类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isInt(String columnType);

  /**
   * 是否长整数类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isLong(String columnType);

  /**
   * 是否单浮点类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isFloat(String columnType);

  /**
   * 是否双浮点类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isDouble(String columnType);

  /**
   * 是否小数精度类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isDecimal(String columnType);

  /**
   * 是否字符串类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isVarchar(String columnType);

  /**
   * 是否时间类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isDatetime(String columnType);

  /**
   * 是否对象类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isBlob(String columnType);

  /**
   * 是否JSON类型
   *
   * @param columnType 字段类型
   * @return boolean
   */
  boolean isJsonb(String columnType);
}
