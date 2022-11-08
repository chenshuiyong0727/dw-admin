package com.hf.op.domain.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 运营系统用户表(op_sys_user)实体类
 * @date 2021/11/9
 */
@Data
@TableName("op_sys_user")
public class OpSysUserEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -7371605969337244025L;

  @TableField(exist = false)
  private OpSysUserRoleEntity opSysUserRoleEntity;

  /**
   * 用户编号，统一用户表的用户编号
   */
  private Long userId;

  /**
   * 部门编号
   */
  private Long departmentId;

  /**
   * 所属岗位
   */
  private Long postId;

  /**
   * 员工类别：0-试用期员工 1-正式员工
   */
  private Integer staffType;

  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 性别：0-男 1-女 2-未知
   */
  private Integer gender;

  /**
   * 真实姓名
   */
  private String userRealName;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号
   */
  private String userMobile;

  /**
   * 系统用户登录编号
   */
  @TableField(exist = false)
  private Long sysUserId;

  /**
   * 系统用户账号
   */
  @TableField(exist = false)
  private String sysUserAccount;

  /**
   * 客户关怀 0：不参加客户关怀 1：参加客户关怀
   */
  @TableField(exist = false)
  private Integer customerCare;

  /**
   * 角色id集合
   */
  @TableField(exist = false)
  private List<Long> roleIds;

  /**
   * 角色id集合
   */
  @TableField(exist = false)
  private List<List<Long>> systemAndRoleIds;

  /**
   * 登录密码
   */
  @TableField(exist = false)
  private String passWord;

}
