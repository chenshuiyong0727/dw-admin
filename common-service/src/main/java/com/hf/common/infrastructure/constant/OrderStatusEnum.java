package com.hf.common.infrastructure.constant;

/**
 * 性别
 *
 * @author chensy
 * @function
 * @date 2022/4/19
 **/
public enum OrderStatusEnum {

//  下架	1
//  已上架	2
//  待发货	3
//  已发货	4
//  已揽件	5
//  已收货	6
//  成功	7
//  瑕疵	8
//  取消	9
//  发货后取消	10

  NOT_ON(0, "未上架"),
  THE_SHELVES(1, "下架"),
  GALLERY(2, "已上架"),
  WAITDELIVER(3, "待发货"),
  SHIPPED(4, "已发货"),
  ALREADY_TOOK_A(5, "已揽件"),
  RECEIVED(6, "已收货"),
  SUCCESSFUL(7, "成功"),
  DEFECTS(8, "瑕疵"),
  CANCEL(9, "取消"),
  CANCEL_AFTER_DELIVERY(10, "发货后取消");

  /**
   * 性别
   */
  private Integer status;

  /**
   * 描述
   */
  private String describe;

  OrderStatusEnum(Integer status, String describe) {
    this.status = status;
    this.describe = describe;
  }

  public static String queryByOderStatus(Integer status) {
    for (OrderStatusEnum value : OrderStatusEnum.values()) {
      if (value.status.equals(status)) {
        return value.describe;
      }
    }
    return "未知";
  }

  public static Integer queryByOderStatus(String describe) {
    for (OrderStatusEnum value : OrderStatusEnum.values()) {
      if (value.describe.equals(describe)) {
        return value.status;
      }
    }
    return 2;
  }

  public Integer getOderStatus() {
    return status;
  }

  public void setOderStatus(Integer status) {
    this.status = status;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }
}
