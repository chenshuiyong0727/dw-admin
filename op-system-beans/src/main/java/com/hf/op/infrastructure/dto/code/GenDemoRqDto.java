package com.hf.op.infrastructure.dto.code;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 案例 请求参数Dto
 *
 * @author chensy
 * @date 2022-09-08 15:34:00
 */
@Data
@ToString
public class GenDemoRqDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 5501170345398802824L;

  /**
   * 其他表id
   */
  private Long opId;

  /**
   * 名称
   */
  private String demoName;

  /**
   * 数量 开始
   */
  private Integer demoNumFrom;

  /**
   * 数量 结束
   */
  private Integer demoNumTo;

  /**
   * 类型
   */
  private Integer demoType;

  /**
   * 金额 开始
   */
  private BigDecimal demoAmountFrom;

  /**
   * 金额 结束
   */
  private BigDecimal demoAmountTo;

  /**
   * 时间 开始
   */
  private String demoTimeFrom;

  /**
   * 时间 结束
   */
  private String demoTimeTo;

  /**
   * 日期 开始
   */
  private String demoDateFrom;

  /**
   * 日期 结束
   */
  private String demoDateTo;

  /**
   * 文章
   */
  private String demoText;

  /**
   * 数据状态
   */
  private Integer dataStatus;

  /**
   * 创建时间 开始
   */
  private String createTimeFrom;

  /**
   * 创建时间 结束
   */
  private String createTimeTo;


  /**
   * 编号集合
   */
  private List<Long> ids;

  /**
   * 每页记录数,不填默认10
   */
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  private Integer pageNum;
}
