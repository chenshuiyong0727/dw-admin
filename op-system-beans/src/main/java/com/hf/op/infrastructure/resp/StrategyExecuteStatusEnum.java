package com.hf.op.infrastructure.resp;

/**
 * @author 曾仕斌
 * @function 策略执行状态
 * @date 2022/4/27
 **/
public enum StrategyExecuteStatusEnum {
  FAIL(-1, "失败"),
  LOCK_IN_PROGRESS(0, "锁定执行中"),
  SUCCESS(1, "成功"),
  TO_BE_RETRIED(2, "待重试");

  /**
   * 状态 ,-1 失败， 0 锁定执行中，1 成功"，2 待重试
   */
  private Integer status;

  /**
   * 描述
   */
  private String describe;

  StrategyExecuteStatusEnum(Integer status, String describe) {
    this.status = status;
    this.describe = describe;
  }

  public Integer getStatus() {
    return status;
  }

  public String getDescribe() {
    return describe;
  }

  public static String queryDescribeByStatus(Integer status) {
    for (StrategyExecuteStatusEnum value : StrategyExecuteStatusEnum.values()) {
      if (value.getStatus().equals(status)) {
        return value.getDescribe();
      }
    }
    return "异常状态";
  }
}
