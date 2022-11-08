package com.hf.op.infrastructure.resp;

import java.util.Arrays;
import java.util.List;

/**
 * @author 梁灿龙
 * @function
 * @date 2022/4/8
 **/
public enum WorkOrderMenuTypeEnum {
  /**
   * 我的待关怀
   */
  WAIT_CARE(1, 1, 1, Arrays.asList(new Integer[]{3, 6}), Arrays.asList(new Integer[]{1}), null),
  /**
   * 待合作方推进
   */
  WAIT_PARTNER_CARE(2, 1, 2, Arrays.asList(new Integer[]{2, 3, 4, 5, 6}),
      Arrays.asList(new Integer[]{1}), null),
  /**
   * 我的已完结
   */
  CARE_COMPLETE(3, 1, null, Arrays.asList(new Integer[]{7}), Arrays.asList(new Integer[]{-1}), 1),
  /**
   * 我的计划工单
   */
  MY_PLAN_CARE_WORK_ORDER(4, 1, null, Arrays.asList(new Integer[]{2}),
      Arrays.asList(new Integer[]{0}), 1),
  /**
   * 我的全部工单
   */
  MY_ALL_CARE_WORK_ORDER(5, 1, null, null, null, null),
  /**
   * 全部待分配
   */
  ALL_WAIT_DISTRIBUTION(6, null, null, Arrays.asList(new Integer[]{1}),
      Arrays.asList(new Integer[]{1}), null),
  /**
   * 全部工单
   */
  ALL_CARE_WORK_ORDER(7, null, null, null, null, null);


  /**
   * 菜单类型
   */
  private Integer menuType;

  /**
   * 是否带入本人id
   */
  private Integer self;

  /**
   * 工单类型 1 自营 2 合作方
   */
  private Integer type;

  /**
   * 状态区间  1 待分配， 2 待开始，3 待跟进，4 已导出，5 未导出，6 已逾期，7 已完成
   */
  private List<Integer> statusArea;
  /**
   * 执行状态区间  -1-取消；0-暂停；1-正常
   */
  private List<Integer> executionStatusArea;

  /**
   * 执行状态和 状态直接是 OR 还是 and 1 Or 0 AND
   */
  private Integer orAnd;

  WorkOrderMenuTypeEnum(Integer menuType, Integer self, Integer type,
      List<Integer> statusArea, List<Integer> executionStatusArea, Integer orAnd) {
    this.menuType = menuType;
    this.self = self;
    this.type = type;
    this.statusArea = statusArea;
    this.executionStatusArea = executionStatusArea;
    this.orAnd = orAnd;
  }

  public static WorkOrderMenuTypeEnum queryType(Integer menuType) {
    for (WorkOrderMenuTypeEnum value : WorkOrderMenuTypeEnum.values()) {
      if (value.menuType.equals(menuType)) {
        return value;
      }
    }
    return null;
  }

  public Integer getSelf() {
    return self;
  }

  public Integer getType() {
    return type;
  }

  public List<Integer> getStatusArea() {
    return statusArea;
  }

  public List<Integer> getExecutionStatusArea() {
    return executionStatusArea;
  }

  public Integer getOrAnd() {
    return orAnd;
  }
}
