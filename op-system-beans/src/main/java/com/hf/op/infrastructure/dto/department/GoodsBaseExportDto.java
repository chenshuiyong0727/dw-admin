package com.hf.op.infrastructure.dto.department;

import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
  * 商品基本信息 exportDto
  * @author chensy
  * @date 2022-11-08 11:10:33
  */
@Data
public class GoodsBaseExportDto implements Serializable {

  private static final long serialVersionUID = 1791779083083367547L;

  /**
    * 商品基本信息编号
    */
  @Excel(name = "商品基本信息编号")
  private Long id;

  /**
    * 类型 ：1 鞋，2服 ，3配件
    */
  @Excel(name = "类型 ：1 鞋，2服 ，3配件", replace = {"鞋_1","服_2","配_3"})
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

}
