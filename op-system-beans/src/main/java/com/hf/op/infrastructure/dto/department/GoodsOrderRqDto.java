package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 商品订单信息 请求参数Dto
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Data
@ToString
public class GoodsOrderRqDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 5158114711655177619L;

 /**
  * 订单主键
  */
  private Long id;

 /**
  * 订单号
  */
  private String orderNo;

 /**
  * 库存编号
  */
  private Long inventoryId;

 /**
  * 状态
  */
  private Integer status;

  /**
  * 原售价 开始
  */
  private BigDecimal shelvesPriceFrom;

 /**
  * 原售价 结束
  */
  private BigDecimal shelvesPriceTo;

  /**
  * 运费 开始
  */
  private BigDecimal freightFrom;

 /**
  * 运费 结束
  */
  private BigDecimal freightTo;

  /**
  * 手续费 开始
  */
  private BigDecimal poundageFrom;

 /**
  * 手续费 结束
  */
  private BigDecimal poundageTo;

  /**
  * 到手价 开始
  */
  private BigDecimal theirPriceFrom;

 /**
  * 到手价 结束
  */
  private BigDecimal theirPriceTo;

 /**
  * 地址编号
  */
  private Long addressId;

 /**
  * 运单编号
  */
  private Long waybillNo;

  /**
  * 创建时间 开始
  */
  private String createTimeFrom;

  /**
  * 创建时间 开始
  */
  private String statusList;

  /**
   * 创建时间 开始
   */
  private List<String> statusArray;

 /**
  * 创建时间 结束
  */
  private String createTimeTo;

  /**
  * 更新时间 开始
  */
  private String updateTimeFrom;

 /**
  * 更新时间 结束
  */
  private String updateTimeTo;

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
