package com.hf.op.infrastructure.dto.user;

import com.hf.op.infrastructure.dto.OpLoginLogDto;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wpq
 * @function 个人用户账号信息
 * @Date 2022/02/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserAccountInfoDto implements Serializable {

  private static final long serialVersionUID = -8882884751003281251L;

  /**
   * 真实姓名
   */
  private String userRealName;

  /**
   * 性别：0-男 1-女 2-未知
   */
  private Integer gender;

  /**
   * 部门名称
   */
  private String departmentName;

  /**
   * 岗位名称
   */
  private String postName;

  /**
   * 员工类别：0-试用期员工 1-正式员工
   */
  private Integer staffType;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号
   */
  private String userMobile;

  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 权限角色名称集合，多个用逗号分隔
   */
  private String roleNames;

  /**
   * 登录日志信息
   */
  private List<OpLoginLogDto> opLoginLogList;

}
