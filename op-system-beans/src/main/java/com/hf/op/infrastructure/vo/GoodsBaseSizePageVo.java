package com.hf.op.infrastructure.vo;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;


/**
 * 商品尺码关系 Dto
 *
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Data
@ToString
public class GoodsBaseSizePageVo extends BaseDto implements Serializable {

  private static final long serialVersionUID = 4375376931390265668L;

  /**
   * 尺码编号
   */
  private Long sizeId;

  /**
   * 商品编号
   */
  private Long goodsId;

}
