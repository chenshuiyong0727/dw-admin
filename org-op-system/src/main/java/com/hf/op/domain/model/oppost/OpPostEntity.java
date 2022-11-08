package com.hf.op.domain.model.oppost;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 岗位实体
 * @Date 2021/12/13
 */
@Data
@TableName("op_post")
public class OpPostEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -5014559502257540710L;

  /**
   * 岗位名称
   */
  @TableField("`name`")
  private String name;

  /**
   * 岗位分类，取自数据字典
   */
  @TableField("`type`")
  private Integer type;

  /**
   * 归属部门
   */
  private String departmentId;

  /**
   * 岗位说明
   */
  private String description;

}
