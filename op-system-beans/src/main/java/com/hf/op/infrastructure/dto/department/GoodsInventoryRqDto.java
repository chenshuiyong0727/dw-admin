package com.hf.op.infrastructure.dto.department;

import java.time.LocalDateTime;
import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 商品库存 请求参数Dto
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Data
@ToString
public class GoodsInventoryRqDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 5176396168136426798L;

 /**
  * 库存编号
  */
  private Long id;

 /**
  * 商品编号
  */
  private Long goodsId;

 /**
  * 尺码编号
  */
  private Long sizeId;

  /**
  * 库存 开始
  */
  private Integer inventoryFrom;

 /**
  * 库存 结束
  */
  private Integer inventoryTo;

  /**
  * 创建时间 开始
  */
  private String createTimeFrom;

 /**
  * 创建时间 结束
  */
  private String createTimeTo;

  /**
  * 更新时间 开始
  */
  private String updateTimeFrom;

 /**
  * 更新时间 结束
  */
  private String updateTimeTo;


	/**
	 * 编号集合
	 */
  private List<Long> ids;

  /**
   * 每页记录数,不填默认10
   */
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  private Integer pageNum;
}
