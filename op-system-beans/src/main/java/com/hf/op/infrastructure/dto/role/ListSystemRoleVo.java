package com.hf.op.infrastructure.dto.role;

import java.io.Serializable;
import lombok.Data;

/**
 * @author chensy
 * @function 系统分类
 * @Date 2022/02/14
 */
@Data
public class ListSystemRoleVo implements Serializable {


  private static final long serialVersionUID = 4674227803203128918L;

  /**
   * 系统编号
   */
  private Integer systemId;

  /**
   * 权限编号
   */
  private Long roleId;


}
