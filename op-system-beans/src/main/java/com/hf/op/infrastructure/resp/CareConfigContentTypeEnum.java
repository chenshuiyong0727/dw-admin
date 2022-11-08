package com.hf.op.infrastructure.resp;

/**
 * 关怀常量
 *
 * @author 梁灿龙
 * @function
 * @date 2022/3/17
 **/
public enum CareConfigContentTypeEnum {
  COOPERATION("合作方推进", 1),
  SELF_EMPLOYED("自营关怀", 2);
  /**
   * 名词
   */
  private String name;

  /**
   * 类型
   */
  private Integer type;

  CareConfigContentTypeEnum(String name, Integer type) {
    this.name = name;
    this.type = type;
  }

  public static String getNameByType(Integer type) {
    for (CareConfigContentTypeEnum value : CareConfigContentTypeEnum.values()) {
      if (type.equals(value.type)) {
        return value.name;
      }
    }
    return "未知关怀目的";
  }
}
