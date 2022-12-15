package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * 商品库存 Dto
 *
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
public class GoodsInventoryDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 3352235730262773894L;

  private List<GoodsInventorySizeDto> sizeDtos;

}
