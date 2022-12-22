package com.hf.op.infrastructure.dto.department;

import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品基本信息 exportDto
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
public class GoodsBaseExportDto implements Serializable {

  private static final long serialVersionUID = 1791779083083367547L;

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
   * 品牌
   */
  @Excel(name = "品牌")
  private String brand;

  /**
   * 备注
   */
  @Excel(name = "备注")
  private String remark;

}
