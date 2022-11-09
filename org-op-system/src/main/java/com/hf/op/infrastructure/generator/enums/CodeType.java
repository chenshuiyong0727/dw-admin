package com.hf.op.infrastructure.generator.enums;

/**
 * 代码类型
 *
 * @author Mybatis-plus
 * @date 2020-09-22 11:17
 */
public enum CodeType {

  /**
   * 代码类型
   */
  BACKEND("0", "后端"),
  FRONTEND("1", "前端"),
  ;

  private final String type;
  private final String desc;

  public static CodeType getCodeType(String type) {
    CodeType[] var = values();
    for (CodeType codeType : var) {
      if (codeType.type.equalsIgnoreCase(type)) {
        return codeType;
      }
    }
    return BACKEND;
  }

  public String getType() {
    return this.type;
  }

  public String getDesc() {
    return this.desc;
  }

  // ================

  CodeType(final String type, final String desc) {
    this.type = type;
    this.desc = desc;
  }
}
