package com.hf.op.infrastructure.dto.department;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
  * 商品订单信息 exportDto
  * @author chensy
  * @date 2022-11-15 17:39:00
  */
@Data
public class GoodsOrderExportDto implements Serializable {

  private static final long serialVersionUID = 1152449205867437776L;

  /**
    * 订单主键
    */
  @Excel(name = "订单主键")
  private Long id;

  /**
    * 订单号
    */
  @Excel(name = "订单号")
  private String orderNo;

  /**
    * 库存编号
    */
  @Excel(name = "库存编号")
  private Long inventoryId;

  /**
    * 状态
    */
  @Excel(name = "状态", replace = {"下架_1","发货后取消_10","已上架_2","待发货_3","已发货_4","已揽件_5","已收货_6","成功_7","瑕疵_8","取消_9"})
  private Integer status;

  /**
    * 原售价
    */
  @Excel(name = "原售价")
  private BigDecimal shelvesPrice;

  /**
    * 运费
    */
  @Excel(name = "运费")
  private BigDecimal freight;
  /**
   * 补贴价
   */
  private BigDecimal subsidiesPrice;
  /**
    * 手续费
    */
  @Excel(name = "手续费")
  private BigDecimal poundage;

  /**
    * 到手价
    */
  @Excel(name = "到手价")
  private BigDecimal theirPrice;

  /**
    * 地址编号
    */
  @Excel(name = "地址编号")
  private Long addressId;

  /**
    * 运单编号
    */
  @Excel(name = "运单编号")
  private String waybillNo;
  public LocalDateTime sellTime ;
  public LocalDateTime successTime ;
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
