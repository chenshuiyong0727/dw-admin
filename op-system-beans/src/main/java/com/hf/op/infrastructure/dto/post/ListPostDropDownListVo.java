package com.hf.op.infrastructure.dto.post;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 岗位下拉列表
 * @Date 2022/02/14
 */
@Data
public class ListPostDropDownListVo implements Serializable {

  private static final long serialVersionUID = 1935389828071569387L;

  /**
   * 岗位编号
   */
  private Long postId;

  /**
   * 岗位名称
   */
  private String postName;

}
