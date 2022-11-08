package com.auth.common.infrastructure.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 平台权限DTO
 * @date 2021/2/23
 */
@Data
public class FunctionDto implements Serializable {

  private static final long serialVersionUID = 3512474338326716339L;

  /**
   * 权限编号
   */
  private Long id;

  /**
   * 权限键值
   */
  private String functionKey;

  /**
   * 权限名称
   */
  private String functionName;

  /**
   * 权限描述
   */
  private String functionDesc;

}
