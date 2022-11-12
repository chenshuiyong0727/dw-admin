package com.hf.op.infrastructure.vo;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;


/**
 * 商品库存 Dto
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
public class GoodsInventoryPageVo extends BaseDto implements Serializable {

		private static final long serialVersionUID = 4135096194346386474L;

    /**
    * 商品编号
    */
    private Long goodsId;

    /**
    * 尺码编号
    */
    private Long sizeId;

    /**
    * 库存
    */
    private Integer inventory;

}
