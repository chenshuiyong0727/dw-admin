package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
 * 商品订单信息 Entity
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Data
@ToString
@TableName("goods_order")
public class GoodsOrderEntity extends BaseEntity implements Serializable {

		private static final long serialVersionUID = 2506428064257655669L;

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
    * 原售价
    */
    private BigDecimal shelvesPrice;

    /**
    * 运费
    */
    private BigDecimal freight;

    /**
    * 手续费
    */
    private BigDecimal poundage;

    /**
    * 到手价
    */
    private BigDecimal theirPrice;

    /**
    * 地址编号
    */
    private Long addressId;

    /**
    * 运单编号
    */
    private Long waybillNo;

}
