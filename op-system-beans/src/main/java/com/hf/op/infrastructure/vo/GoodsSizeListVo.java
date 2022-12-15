package com.hf.op.infrastructure.vo;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;


/**
 * 商品基本信息 Dto
 *
 * @author chensy
 * @date 2022-11-08 11:10:33
 */
@Data
@ToString
public class GoodsSizeListVo implements Serializable {

  private static final long serialVersionUID = 4171658542273027807L;
  /**
   * 编号
   */
  public Long id;
  /**
   * 尺码
   */
  private String size;

}
