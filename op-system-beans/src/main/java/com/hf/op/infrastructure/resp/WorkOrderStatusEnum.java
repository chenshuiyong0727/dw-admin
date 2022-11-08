package com.hf.op.infrastructure.resp;

/**
 * @author 梁灿龙
 * @function
 * @date 2022/4/15
 **/
public enum WorkOrderStatusEnum {
  TO_BE_ASSIGNED(1, "待分配"),
  TO_BE_STARTED(2, "待开始"),
  TO_BE_FOLLOWED(3, "待跟进"),
  NOT_EXPORTED(4, "未导出"),
  EXPORTED(5, "已导出"),
  PAST_DUE(6, "已逾期"),
  COMPLETED(7, "已完成");
  /**
   * 状态 ,1 待分配， 2 待开始，3 待跟进，4 未导出 ，5 已导出，6 已逾期，7 已完成
   */
  private Integer status;

  /**
   * 描述
   */
  private String describe;

  WorkOrderStatusEnum(Integer status, String describe) {
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
    for (WorkOrderStatusEnum value : WorkOrderStatusEnum.values()) {
      if (value.getStatus().equals(status)) {
        return value.getDescribe();
      }
    }
    return "异常状态";
  }
}
