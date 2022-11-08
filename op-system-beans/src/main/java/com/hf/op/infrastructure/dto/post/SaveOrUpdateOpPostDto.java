package com.hf.op.infrastructure.dto.post;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 岗位传输对象
 * @Date 2021/12/13
 */
@Data
public class SaveOrUpdateOpPostDto implements Serializable {

  private static final long serialVersionUID = -7538931209914275895L;

  /**
   * 岗位编号
   */
  private Long id;

  /**
   * 岗位名称
   */
  private String name;

  /**
   * 岗位说明
   */
  private String description;

  /**
   * 岗位分类 1：管理岗 2：产品岗
   */
  private Integer type;

  /**
   * 归属部门
   */
  private String departmentId;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

}
