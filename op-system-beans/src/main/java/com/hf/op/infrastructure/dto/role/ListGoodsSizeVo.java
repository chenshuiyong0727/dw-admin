package com.hf.op.infrastructure.dto.role;

import java.io.Serializable;
import lombok.Data;

/**
 * @author chensy
 * @function 系统分类
 * @Date 2022/02/14
 */
@Data
public class ListGoodsSizeVo implements Serializable {


  private static final long serialVersionUID = 4674227803203128918L;

  /**
   * 尺码
   */
  private String size;

  /**
   * 系统编号
   */
  private String type;

  /**
   * 权限编号
   */
  private Long id;


}
