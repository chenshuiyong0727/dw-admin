package com.hf.op.infrastructure.dto.department;

import com.hf.common.infrastructure.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;


/**
 * 商品尺码关系 请求参数Dto
 *
 * @author chensy
 * @date 2022-11-12 16:48:45
 */
@Data
@ToString
public class GoodsBaseSizeRqDto extends BaseDto implements Serializable {

  private static final long serialVersionUID = 5440674682965324590L;


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
