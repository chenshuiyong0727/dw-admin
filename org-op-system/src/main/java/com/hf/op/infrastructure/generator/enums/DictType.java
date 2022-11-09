package com.hf.op.infrastructure.generator.enums;


/**
 * 字典类型
 *
 * @author : Parker
 * @date : 2020-09-17 23:40
 */
public enum DictType {

  /**
   * 是否
   */
  NO_YES_NO("no_yes", "0", "否"),
  NO_YES_YES("no_yes", "1", "是"),
  /**
   * 字典
   */
  DICT_TYPE("data_status", "36", "状态字典"),
  /**
   * 字典
   */
  DB_NAME("db_name", "hf_portal", "op"),

  /**
   * 显示类型
   */
  SHOW_TYPE_INPUT("show_type", "0", "文本框"),
  SHOW_TYPE_TEXT("show_type", "1", "文本域"),
  SHOW_TYPE_DICT("show_type", "2", "字典选择"),
  SHOW_TYPE_DATE("show_type", "3", "日期选择"),
  SHOW_TYPE_TIME("show_type", "4", "时间选择"),

  /**
   * 菜单 标签
   */
  MENU_LABEL_SYSTEM("menu_role_type", "0", "系统模块"),
  MENU_LABEL_FUNCTION("menu_role_type", "1", "功能模块"),

  /**
   * 菜单
   */
  MENU_MENU("menu_type", "1", "菜单"),
  MENU_BUTTON("menu_type", "2", "按钮"),
  MENU_EXTERNAL("menu_type", "3", "外链"),

  /**
   * 数据范围
   */
  DATA_SCOPE_SELF("role_data_scope", "0", "仅本人数据"),
  DATA_SCOPE_DEPT("role_data_scope", "1", "本部门数据"),
  DATA_SCOPE_DEPT_AND_UNDER("role_data_scope", "2", "本部门及以下数据"),
  DATA_SCOPE_ALL("role_data_scope", "3", "全部数据"),

  /**
   * 登入登出日志类型
   */
  LOGIN_LOG_TYPE_LOGIN("login_log_type", "1", "登入"),
  LOGIN_LOG_TYPE_LOGOUT("login_log_type", "2", "登出");

  private final String type;
  private final String value;
  private final String desc;

  DictType(String type, String value, String desc) {
    this.type = type;
    this.value = value;
    this.desc = desc;
  }

  public String getValue() {
    return value;
  }

  public String getType() {
    return this.type;
  }

  public String getDesc() {
    return this.desc;
  }

  /**
   * 获得对应的字典
   *
   * @param type 类型
   * @param value 值
   * @return DictType
   */
  public static DictType getDict(String type, String value) {
    DictType[] types = values();
    for (DictType dict : types) {
      if (dict.type.equals(type) &&
          dict.value.equalsIgnoreCase(value)
      ) {
        return dict;
      }
    }
    return null;
  }

  /**
   * 是否是字典内
   *
   * @param type 类型
   * @param value 值
   * @return boolean
   */
  public static boolean hasDict(String type, String value) {
    DictType[] types = values();
    for (DictType dict : types) {
      if (dict.type.equals(type) &&
          dict.value.equalsIgnoreCase(value)) {
        return true;
      }
    }
    return false;
  }
}
