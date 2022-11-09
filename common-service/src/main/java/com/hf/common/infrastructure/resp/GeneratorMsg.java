package com.hf.common.infrastructure.resp;

/**
 * 代码生成器 - 消息
 *
 * @author parker
 * @date 2020-09-13 19:36
 */
public enum GeneratorMsg implements BaseMsg {

  /**
   * 表
   */
  EXCEPTION_TABLE_NAME_REPEAT(50000, "表名重复"),
  /**
   * 字典
   */
  EXCEPTION_TYPE_VALUE_REPEAT(40000, "字典类型值重复"),
  EXCEPTION_TYPE_NAME_REPEAT(40001, "字典类型名称重复"),
  EXCEPTION_VALUE_REPEAT(40002, "同一类型字典值重复"),
  EXCEPTION_NAME_REPEAT(40003, "同一类型字典名称重复"),

  /**
   * 字段
   */
  EXCEPTION_TABLE_COLUMN_FIELD_NAME_REPEAT(50001, "字段名重复"),

  /**
   * 同步
   */
  EXCEPTION_SYNC_NULL(50100, "同步表失败，暂无该表"),
  EXCEPTION_SYNC_CORE(50101, "系统核心关键表不允许同步"),

  /**
   * 导入
   */
  EXCEPTION_IMPORT_NULL(50120, "未选中表，无法导入"),
  EXCEPTION_IMPORT_TABLE_NULL(50121, "暂无{}该表"),
  EXCEPTION_IMPORT_FIELD_NULL(50122, "暂未获得表字段"),

  /**
   * 生成
   */
  EXCEPTION_CREATE_NULL(50140, "生成失败，数据为空"),
  EXCEPTION_CREATE_TABLE_NULL(50141, "生成失败，暂无表数据"),
  EXCEPTION_CREATE_FIELD_NULL(50142, "生成失败，暂无表字段"),
  EXCEPTION_CREATE_MENU_CODE_NULL(50143, "生成菜单失败，请先生成代码"),
  EXCEPTION_CREATE_MENU_PARENT_NULL(50144, "上级菜单不可为空"),

  /**
   * 模板
   */
  EXCEPTION_TEMPLATE_NAME_REPEAT(50150, "模板名称重复"),
  EXCEPTION_TEMPLATE_AT_LEAST_ONE(50151, "代码模板同一表类型下，至少保障有一个模板"),
  EXCEPTION_TEMPLATE_COPY_NULL(50152, "暂无该模板"),

  /**
   * 其他
   */
  EXCEPTION_OTHER_NULL(50200, "暂无数据"),
  EXCEPTION_NOT_ENABLE(50201, "代码生成器未启用"),

  EXCEPTION_SEND_MSG(50300, "短信发送失败"),
  EXCEPTION_LOGIN_MSG(50301, "登录失败"),
  EXCEPTION_LOGIN_MSG_NO_PHONE(50304, "登录失败，获取手机号不成功"),
  EXCEPTION_NO_FIND_PROMOTE_USER(50305, "根据合作码，找不到推广员"),
  EXCEPTION_USER_MSG(50302, "用户不存在"),
  EXCEPTION_GOODS_MSG(50309, "商品不存在"),
  EXCEPTION_USER_EXIST_ORDER_MSG(50308, "该订单已存在"),
  EXCEPTION_USER_ORDER_MSG(50306, "订单不存在"),
  EXCEPTION_EXIST_REF_ORDER_MSG(50307, "该订单已退款"),
  EXCEPTION_MEMBER_MSG(50306, "服务包不存在"),
  EXCEPTION_IDCARD_MSG(50303, "身份识别失败"),


  MSG_SPORT_MORE(0, "您已获赠{}天服务包，可免费进行跟练"),
  MSG_TRAING_MORE(0, "您已获赠{}天服务包，可免费进行锻练"),
  MSG_SPORT_ONE(0, "您已购{}，获赠{}天服务包，可免费进行跟练"),
  MSG_TRAING_ONE(0, "您已购{}，获赠{}天服务包，可免费进行锻练"),

  ;

  private final int code;
  private final String message;

  GeneratorMsg(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public Integer getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
