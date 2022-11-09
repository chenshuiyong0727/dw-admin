package com.hf.op.infrastructure.generator.enums;

/**
 * 数据库类型
 *
 * @author Mybatis-plus
 * @date 2020-09-22 11:17
 */
public enum DataBaseType {

  /**
   * 数据库类型
   */
  MYSQL("mysql", "MySql数据库"),
  OTHER("other", "其他数据库");

  private final String db;
  private final String desc;

  public static DataBaseType getDbType(String dbType) {
    DataBaseType[] var = values();
    for (DataBaseType type : var) {
      if (type.db.equalsIgnoreCase(dbType)) {
        return type;
      }
    }

    return OTHER;
  }

  public String getDb() {
    return this.db;
  }

  public String getDesc() {
    return this.desc;
  }

  // ================

  DataBaseType(final String db, final String desc) {
    this.db = db;
    this.desc = desc;
  }
}
