package com.hf.op.infrastructure.generator.enums;

import com.hf.common.infrastructure.util.ListDistinctUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Java 类型
 *
 * @author Parker
 * @date 2021年5月26日16:31:08
 */
public enum JavaType {

  /**
   * 包装类型
   */
  BYTE("Byte", null),
  SHORT("Short", null),
  CHARACTER("Character", null),
  INTEGER("Integer", null),
  LONG("Long", null),
  FLOAT("Float", null),
  DOUBLE("Double", null),
  BOOLEAN("Boolean", null),
  STRING("String", null),

  /**
   * Big类
   */
  BIG_INTEGER("BigInteger", "java.math.BigInteger"),
  BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),

  /**
   * 时间类
   */
  DATE("Date", "java.time.LocalDateTime"),
  //  DATE("Date", "java.util.Date"),
  LOCAL_DATE("LocalDate", "java.time.LocalDate"),
  LOCAL_TIME("LocalTime", "java.time.LocalTime"),
  YEAR("Year", "java.time.Year"),
  YEAR_MONTH("YearMonth", "java.time.YearMonth"),
  LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
  INSTANT("Instant", "java.time.Instant"),

  /**
   * 其他
   */
  BYTE_ARRAY("Byte[]", null),
  MAP_OBJECT("Map<String, Object>", "java.util.Map"),

  ;

  /**
   * 类型
   */
  private final String type;
  /**
   * 包路径
   */
  private final String pkg;

  JavaType(final String type, final String pkg) {
    this.type = type;
    this.pkg = pkg;
  }

  /**
   * 获得 包地址
   *
   * @param typeList 类型集合
   * @return 包集合
   */
  public static List<String> getPkgList(List<String> typeList) {
    if (typeList == null || typeList.size() == 0) {
      return Collections.emptyList();
    }

    List<String> pkgList = new ArrayList<>();
    JavaType[] types = values();
    for (JavaType javaType : types) {
      if (typeList.contains(javaType.type)) {
        pkgList.add(javaType.pkg);
      }
    }
    pkgList.removeIf(StringUtils::isBlank);
    // 去重复
    return ListDistinctUtil.distinct(pkgList);
  }

  public String getType() {
    return type;
  }

  public String getPkg() {
    return pkg;
  }

}
