package com.hf.op.infrastructure.vo;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;


/**
 * 商品基本信息 Dto
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
@ToString
public class GoodsBasePageVo extends BaseDto implements Serializable {

  private static final long serialVersionUID = 4171658542273027807L;

  /**
   * 类型 ：1 鞋，2服 ，3配件
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
   * 图片地址
   */
  private String imgUrl;

  /**
   * 品牌
   */
  private String brand;

  /**
   * 备注
   */
  private String remark;

}
