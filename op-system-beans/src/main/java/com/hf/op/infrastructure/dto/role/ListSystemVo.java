package com.hf.op.infrastructure.dto.role;

import java.io.Serializable;
import lombok.Data;

/**
 * @author chensy
 * @function 系统分类
 * @Date 2022/02/14
 */
@Data
public class ListSystemVo implements Serializable {


  private static final long serialVersionUID = 4674227803203928918L;

  /**
   * 系统编号
   */
  private Integer systemId;

  /**
   * 系统名称
   */
  private String systemName;

  /**
   * 系统地址
   */
  private String systemUrl;

  /**
   * 是否有权限 是1否0
   */
  private Integer isHaveRole;

}
