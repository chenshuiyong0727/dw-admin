package com.hf.op.infrastructure.dto.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * @author wpq
 * @function 注册统一用户dto
 * @Date 2022/02/08
 */
@Data
@ToString
public class AuthUserComDto implements Serializable {

  /**
   * 用户类型: 10-用户
   */
  public static final Integer USER_TYPE_USER = 10;

  /**
   * 用户类型: 10-用户
   */
  public static final Integer USER_TYPE_SSO = 13;
  /**
   * 是否默认密码，1:是
   */
  public static final Integer DEFAULTPWD_YES = 1;
  /**
   * 是否默认密码，0:否
   */
  public static final Integer DEFAULTPWD_NO = 0;
  /**
   * 同步状态：1-已同步
   */
  public static final Integer SYNC_STATUS_SUCCESS = 1;
  /**
   * 同步状态：0-未同步
   */
  public static final Integer SYNC_STATUS_FAIL = 0;
  private static final long serialVersionUID = -9084071511905662679L;
  /**
   * 本地编号，冗余字段，对应旧的统一账户平台用户编号
   */
  private String localNo;
  /**
   * 用户类型: 10-用户 11-企业用户 12-运营平台用户 13-sso用户
   */
  private Integer userType;

  /**
   * 是否默认密码，0：不是，1:是，如果是默认密码则登录后提醒修改密码
   */
  private Integer defaultPwd;

  /**
   * 同步状态：0-未同步；1-已同步
   */
  private Integer syncStatus;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号码
   */
  private String userMobile;

  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 用户真实姓名
   */
  private String userRealName;

  /**
   * 身份证号码
   */
  private String idCardNo;

  /**
   * 用户编号，统一用户表的用户编号
   */
  private Long userId;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 原密码
   */
  private String oldPwd;

  /**
   * 新密码
   */
  private String newPwd;

  /**
   * 令牌，一段时间后失效
   */
  private String token;

  /**
   * 注册时间
   */
  private LocalDateTime regtime;
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

}
