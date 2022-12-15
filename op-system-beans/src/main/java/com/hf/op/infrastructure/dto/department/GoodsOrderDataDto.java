package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品订单信息 Dto
 *
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Data
@ToString
public class GoodsOrderDataDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3572114745267816227L;

  private GoodsOrderCountDto countDto;
  private GoodsOrderCommonDto commonDto;

}
