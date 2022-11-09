package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 商品基本信息 请求参数Dto
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
@ToString
public class GoodsBaseRqDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 5166013716067703519L;

 /**
  * 商品基本信息编号
  */
  private Long id;

 /**
  * 类型 ：1 鞋，2服 ，3配件
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
  * 品牌
  */
  private String brand;

 /**
  * 备注
  */
  private String remark;


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
