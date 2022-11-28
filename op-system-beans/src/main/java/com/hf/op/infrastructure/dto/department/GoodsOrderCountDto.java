package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 商品订单信息 Dto
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Data
@ToString
public class GoodsOrderCountDto extends BaseDto implements Serializable {

		private static final long serialVersionUID = 3572114745267816227L;

	  private Integer count2;
	  private Integer count3;
	  private Integer count4;
	  private Integer count5;
	  private Integer count6;
	  private Integer count8;

}
