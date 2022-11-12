package com.hf.op.domain.model.dict;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品尺码关系 Entity
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Data
@ToString
@TableName("goods_base_size")
public class GoodsBaseSizeEntity extends BaseEntity implements Serializable {

		private static final long serialVersionUID = 2520336688060790803L;

    /**
    * 尺码编号
    */
    private Long sizeId;

    /**
    * 商品编号
    */
    private Long goodsId;

}
