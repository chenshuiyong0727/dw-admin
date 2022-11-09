package com.hf.common.infrastructure.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾仕斌
 * @function 类型转换工具, 后续用数据字典替换
 * @date 2022/02/11
 */
public class TypeConversionUtil {

  /**
   * sql与java类型对应集合
   */
  private final static Map<String, String> sqlJavaTypeMap = getSqlJavaTypeMap();

  /**
   * sql与java类型对应集合
   */
  private final static Map<String, String> getDbMap = getDbMap();

  /**
   * sql与java对应类型完整路径集合
   */
  private final static Map<String, String> sqlJavaTypeFullPathMap = getSqlJavaTypeFullPathMap();

  /**
   * yapi对应类型完整路径集合
   */
  private final static Map<String, String> yapiTypeMap = getYapiTypeMap();


  /**
   * 获取sql与java类型对应集合
   */
  private static Map<String, String> getSqlJavaTypeMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("varchar", "String");
    map.put("char", "String");
    map.put("bigint", "Long");
    map.put("date", "LocalDate");
    map.put("datetime", "LocalDateTime");
    map.put("timestamp", "LocalDateTime");
    map.put("smallint", "Integer");
    map.put("int", "Integer");
    map.put("decimal", "BigDecimal");
    map.put("tinyint", "Integer");
    map.put("text", "String");
    return map;
  }

  /**
   * 数据库和服务的map
   */
  private static Map<String, String> getDbMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("hjjn_auth", "auth-svc");
    map.put("hjjn_base", "base-service");
    map.put("hjjn_data", "data-center");
    map.put("hjjn_img", "");
    map.put("hjjn_msg", "msg-pub");
    map.put("hjjn_op", "op-system");
    map.put("hjjn_oss", "oss-center");
    map.put("hjjn_project", "project-service");
    map.put("hjjn_uc", "user-center");
    return map;
  }

  /**
   * 根据数据类型取值
   */
  public static Object getValueByType(String type, String value) {
    if (StringUtilLocal.isEmpty(type) || StringUtilLocal.isEmpty(value)) {
      return value;
    }
    if ("string".equals(type) || "datetime".equals(type) || "date".equals(type)) {
      return value;
    } else if ("int".equals(type)) {
      return Integer.parseInt(value);
    } else if ("long".equals(type)) {
      return Long.parseLong(value);
    } else if ("decimal".equals(type)) {
      return (new BigDecimal(value));
    }
    return value;
  }

  /**
   * 获取sql与java对应类型完整路径集合,过滤掉基础类别
   */
  private static Map<String, String> getSqlJavaTypeFullPathMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("date", "java.time.LocalDate");
    map.put("datetime", "java.time.LocalDateTime");
    map.put("timestamp", "java.time.LocalDateTime");
    map.put("decimal", "java.math.BigDecimal");
    return map;
  }

  /**
   * yapi数据类型映射集合
   */
  private static Map<String, String> getYapiTypeMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("date", "string");
    map.put("datetime", "string");
    map.put("timestamp", "number");
    map.put("decimal", "number");
    map.put("int", "integer");
    return map;
  }

  /**
   * 通过sql字段类型获取对应的java类型
   */
  public static String getJavaType(String sqlType) {
    return sqlJavaTypeMap.get(sqlType);
  }

  /**
   * 通过数据库名得到服务名
   */
  public static String getServiceName(String db) {
    return getDbMap.get(db);
  }

  /**
   * 通过sql字段类型获取对应java类型的完整路径
   */
  public static String getJavaTypeFullPath(String sqlType) {
    return sqlJavaTypeFullPathMap.get(sqlType);
  }

  /**
   * 获取yapi对应类型
   */
  public static String getYapiType(String type) {
    String result = yapiTypeMap.get(type);
    if (StringUtilLocal.isEmpty(result)) {
      result = "string";
    }
    return result;
  }
}
