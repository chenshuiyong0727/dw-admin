package com.hf.op.infrastructure.dto.post;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 岗位列表数据传输对象
 * @Date 2021/12/13
 */
@Data
public class QueryOpPostListDto implements Serializable {

  private static final long serialVersionUID = -835863885539921114L;

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
   * 归属部门名称
   */
  private String departmentName;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 修改时间
   */
  private String updateTime;

  /**
   * 修改人名称
   */
  private String updateUserName;

  /**
   * 修改时间开始
   */
  @TableField(exist = false)
  private String updateTimeFrom;

  /**
   * 修改时间结束
   */
  @TableField(exist = false)
  private String updateTimeTo;

  /**
   * 每页记录数,不填默认10
   */
  @TableField(exist = false)
  private Integer pageSize = 10;

  /**
   * 当前页码,不填默认首页
   */
  @TableField(exist = false)
  private Integer pageNum = 1;

}
