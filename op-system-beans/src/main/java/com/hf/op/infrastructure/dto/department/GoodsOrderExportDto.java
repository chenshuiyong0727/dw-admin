package com.hf.op.infrastructure.dto.department;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
  * 订单商品 exportDto
  * @author chensy
  * @date 2022-12-15 17:44:17
  */
@Data
public class GoodsOrderExportDto implements Serializable {

  private static final long serialVersionUID = 1894854637239372563L;

  /**
    * 订单号
    */
  @Excel(name = "订单号")
  private String orderNo;

  /**
    * 状态
    */
  @Excel(name = "状态", replace = {"未上架_0","下架_1","发货后取消_10","已上架_2","待发货_3","已发货_4","已揽件_5","已收货_6","成功_7","瑕疵_8","取消_9"})
  private Integer status;

  /**
    * 原售价
    */
  @Excel(name = "原售价" , type = 10,isStatistics = true)
  private BigDecimal shelvesPrice;

  /**
    * 运费
    */
  @Excel(name = "运费", type = 10,isStatistics = true)
  private BigDecimal freight;

  /**
    * 补贴
    */
  @Excel(name = "补贴", type = 10,isStatistics = true)
  private BigDecimal subsidiesPrice;

  /**
    * 手续费
    */
  @Excel(name = "手续费", type = 10,isStatistics = true)
  private BigDecimal poundage;

  /**
    * 到手价
    */
  @Excel(name = "到手价", type = 10,isStatistics = true)
  private BigDecimal theirPrice;

  /**
    * 利润
    */
  @Excel(name = "利润", type = 10,isStatistics = true)
  private BigDecimal profits;

  /**
    * 地址编号
    */
  @Excel(name = "地址编号", replace = {"_null","上海-龙盘路888号_1","广州-山门大道700号_2","四川成都-南六路1号_3","湖北武汉-临空北路100号_4","河北廊坊-富文道888号_5","上海-彭封路333号_6"})
  private Integer addressId;

  /**
    * 运单编号
    */
  @Excel(name = "运单编号")
  private String waybillNo;

  /**
    * 卖出时间
    */
  @Excel(name = "卖出时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime sellTime;

  /**
    * 交易成功时间
    */
  @Excel(name = "交易成功时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime successTime;

  /**
    * 数据状态
    */
  @Excel(name = "数据状态", replace = {"删除_-1","禁用_0","正常_1"})
  private Integer dataStatus;

  /**
    * 创建时间
    */
  @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime createTime;

}
