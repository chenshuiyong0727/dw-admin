package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 其他收支 Entity
 *
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Data
@ToString
@TableName("goods_other")
public class GoodsOtherEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2297852568631073087L;

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

  /**
   * 金额
   */
  private BigDecimal price;

}
