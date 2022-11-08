package com.hf.op.infrastructure.resp;

/**
 * 目标角色
 *
 * @author 梁灿龙
 * @function
 * @date 2022/3/17
 **/
public enum CareConfigTargeRoleEnum {
  USER("用户", 1),
  RECOMMENDER("推荐人", 2),
  USER_CHILDREN("用户子女", 3),
  RELATIVES_OF_USERS("用户亲属", 4);
  /**
   * 名词
   */
  private String name;

  /**
   * 类型
   */
  private Integer type;

  CareConfigTargeRoleEnum(String name, Integer type) {
    this.name = name;
    this.type = type;
  }

  public static String getNameByType(Integer type) {
    for (CareConfigTargeRoleEnum value : CareConfigTargeRoleEnum.values()) {
      if (type.equals(value.type)) {
        return value.name;
      }
    }
    return "未知关怀目的";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
