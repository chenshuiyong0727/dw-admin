package com.hf.op.infrastructure.dto.department;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
  * 其他收支 exportDto
  * @author chensy
  * @date 2022-11-29 17:04:31
  */
@Data
public class GoodsOtherExportDto implements Serializable {

  private static final long serialVersionUID = 1700878341890655005L;

  /**
    * 其他收支编号
    */
  @Excel(name = "其他收支编号")
  private Long id;

  /**
    * 类型
    */
  @Excel(name = "类型", replace = {"收入_1","支出_2"})
  private Integer type;

  /**
    * 货号
    */
  @Excel(name = "货号")
  private String actNo;

  /**
    * 商品名称
    */
  @Excel(name = "商品名称")
  private String name;

  /**
    * 图片地址
    */
  @Excel(name = "图片地址")
  private String imgUrl;

  /**
    * 品牌
    */
  @Excel(name = "品牌")
  private String brand;

  /**
    * 备注
    */
  @Excel(name = "备注")
  private String remark;

  /**
    * 金额
    */
  @Excel(name = "金额")
  private BigDecimal price;

  /**
    * 创建时间
    */
  @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime createTime;

  /**
    * 更新时间
    */
  @Excel(name = "更新时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime updateTime;

}
