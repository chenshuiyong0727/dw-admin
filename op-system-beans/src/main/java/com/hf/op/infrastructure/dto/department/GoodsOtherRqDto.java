package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 其他收支 请求参数Dto
 *
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Data
@ToString
public class GoodsOtherRqDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 5149618644802141270L;

  /**
   * 类型
   */
  private Integer type;

  /**
   * 货号
   */
  private String actNo;

  /**
   * 商品名称
   */
  private String name;

  /**
   * 品牌
   */
  private String brand;

  /**
   * 备注
   */
  private String remark;

  /**
   * 金额 开始
   */
  private BigDecimal priceFrom;

  /**
   * 金额 结束
   */
  private BigDecimal priceTo;

  /**
   * 创建时间 开始
   */
  private String createTimeFrom;

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
