package com.hf.op.infrastructure.resp;

/**
 * 用户认知进程对照服务能力
 *
 * @author 梁灿龙
 * @function
 * @date 2022/4/15
 **/
public enum UserProcessVSCapabilityEnum {
  REGISTRATION_COMPLETE(1, "registration_complete"),
  RAPID_SCREENING(2, "rapid_screening"),
  ASSESSMEN(4, "assessmen"),
  DIAGNOSIS(6, "diagnosis"),
  TRAINING(8, "training");
  /**
   * 进程
   */
  private Integer process;

  /**
   * 能力
   */
  private String capability;

  UserProcessVSCapabilityEnum(Integer process, String capability) {
    this.process = process;
    this.capability = capability;
  }

  public Integer getProcess() {
    return process;
  }

  public String getCapability() {
    return capability;
  }

  public static UserProcessVSCapabilityEnum queryByProcess(Integer process) {
    for (UserProcessVSCapabilityEnum value : UserProcessVSCapabilityEnum.values()) {
      if (value.getProcess().equals(process)) {
        return value;
      }
    }
    return null;
  }

}
