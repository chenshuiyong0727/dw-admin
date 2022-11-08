package com.hf.op.infrastructure.dto.project;

import java.io.Serializable;
import lombok.Data;

/**
 * 项目信息 - 用户登录
 *
 * @author chensy
 * @function
 * @date 2022/4/11
 **/
@Data
public class ListProjectInfoLoginVo implements Serializable {


  private static final long serialVersionUID = -4625401213701731330L;
  /**
   * 项目id
   */
  private String projectId;

  /**
   * 项目名称
   */
  private String projectName;

  /**
   * 客户id
   */
  private Long customerId;

}
