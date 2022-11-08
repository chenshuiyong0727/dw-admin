package com.hf.op.infrastructure.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 用户列表数据传输对象
 * @Date 2022/02/09
 */
@Data
public class QueryOpUserListDto implements Serializable {

  private static final long serialVersionUID = -5368928009248842810L;

  /**
   * 账号编号
   */
  private Long id;

  /**
   * 用户id
   */
  private Long userId;
  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 真实姓名
   */
  private String userRealName;

  /**
   * 部门编号
   */
  private Long departmentId;

  /**
   * 部门名称
   */
  private String departmentName;

  /**
   * 所属岗位
   */
  private Long postId;

  /**
   * 岗位名称
   */
  private String postName;

  /**
   * 性别：0-男 1-女 2-未知
   */
  private Integer gender;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号
   */
  private String userMobile;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 修改时间
   */
  private String updateTime;

  /**
   * 角色名称集合，多个用逗号分隔
   */
  private String roleNames;

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
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  @TableField(exist = false)
  private Integer pageNum;

}
