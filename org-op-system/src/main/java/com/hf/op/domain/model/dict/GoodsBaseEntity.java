package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品基本信息 Entity
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
@ToString
@TableName("goods_base")
public class GoodsBaseEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2779270523099700292L;

  /**
   * 类型 ：1 鞋，2服 ，3配件
   */
  private String type;

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
