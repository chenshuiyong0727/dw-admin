package com.hf.op.infrastructure.vo;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;


/**
 * 其他收支 Dto
 * @author chensy
 * @date 2022-11-29 17:04:31
 */
@Data
@ToString
public class GoodsOtherPageVo extends BaseDto implements Serializable {

		private static final long serialVersionUID = 4895408951302988022L;

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
