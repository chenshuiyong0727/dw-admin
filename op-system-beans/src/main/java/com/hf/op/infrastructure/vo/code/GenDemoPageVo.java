package com.hf.op.infrastructure.vo.code;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;


/**
 * 案例 Dto
 *
 * @author chensy
 * @date 2022-09-08 15:28:15
 */
@Data
@ToString
public class GenDemoPageVo extends BaseDto implements Serializable {

  private static final long serialVersionUID = 4224776656068098842L;

  /**
   * 其他表id
   */
  private Long opId;

  /**
   * 名称
   */
  private String demoName;

  /**
   * 数量
   */
  private Integer demoNum;

  /**
   * 类型
   */
  private Integer demoType;

  /**
   * 金额
   */
  private BigDecimal demoAmount;

  /**
   * 时间
   */
  private LocalDateTime demoTime;

  /**
   * 日期
   */
  private LocalDate demoDate;

  /**
   * 文章
   */
  private String demoText;

}
