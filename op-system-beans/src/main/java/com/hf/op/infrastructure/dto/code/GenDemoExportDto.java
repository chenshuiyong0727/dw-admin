package com.hf.op.infrastructure.dto.code;

import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 案例 exportDto
 *
 * @author chensy
 * @date 2022-09-08 15:34:00
 */
@Data
public class GenDemoExportDto implements Serializable {

  private static final long serialVersionUID = 1685717080163565146L;

  /**
   * 编号
   */
  @Excel(name = "编号")
  private Long id;

  /**
   * 其他表id
   */
  @Excel(name = "其他表id")
  private Long opId;

  /**
   * 名称
   */
  @Excel(name = "名称")
  private String demoName;

  /**
   * 数量
   */
  @Excel(name = "数量")
  private Integer demoNum;

  /**
   * 类型
   */
  @Excel(name = "类型", replace = {"新增_1", "删除_2", "修改_3", "查看_4"})
  private Integer demoType;

  /**
   * 金额
   */
  @Excel(name = "金额")
  private BigDecimal demoAmount;

  /**
   * 时间
   */
  @Excel(name = "时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime demoTime;

  /**
   * 日期
   */
  @Excel(name = "日期")
  private LocalDate demoDate;

  /**
   * 文章
   */
  @Excel(name = "文章")
  private String demoText;

  /**
   * 数据状态：
   */
  @Excel(name = "数据状态", replace = {"删除_-1", "禁用_0", "正常_1"})
  private Integer dataStatus;

  /**
   * 创建时间
   */
  @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime createTime;

  /**
   * 创建人编号
   */
  @Excel(name = "创建人编号")
  private Long createUserId;

  /**
   * 创建人名称
   */
  @Excel(name = "创建人名称")
  private String createUserName;

}
