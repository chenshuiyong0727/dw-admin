package com.hf.op.infrastructure.generator.enums;

/**
 * 统一类型枚举
 *
 * @author tanghc
 */
public enum TypeEnum {

  /**
   * 统一类型
   */
  BIT("bit"),

  BOOLEAN("boolean"),

  TINYINT("tinyint"),

  SMALLINT("smallint"),

  INT("int"),

  INTEGER("integer"),

  BIGINT("bigint"),

  FLOAT("float"),

  DOUBLE("double"),

  DECIMAL("decimal"),

  CHAR("char"),

  VARCHAR("varchar"),

  TEXT("text"),

  DATE("date"),

  DATETIME("datetime"),

  TIMESTAMP("timestamp"),

  BLOB("blob"),

  JSONB("jsonb");

  private final String type;

  TypeEnum(String type) {
    this.type = type;
  }

  public static TypeEnum getType(String type) {
    TypeEnum[] types = values();
    for (TypeEnum typeEnum : types) {
      if (typeEnum.type.equalsIgnoreCase(type)) {
        return typeEnum;
      }
    }
    return null;
  }

  public String getType() {
    return type;
  }
}
