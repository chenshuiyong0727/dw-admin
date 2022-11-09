package com.hf.op.domain.model.code;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * 案例 Entity
 *
 * @author chensy
 * @date 2022-09-08 15:05:44
 */
@Data
@ToString
@TableName("gen_demo")
public class GenDemoEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2394808136217874126L;

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
