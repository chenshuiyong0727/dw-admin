package com.hf.op.infrastructure.dto.role;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 角色关联用户列表数据传输对象
 * @Date 2022/02/07
 */
@Data
public class QueryRoleRelationUserListDto implements Serializable {

  private static final long serialVersionUID = 429597837283530220L;

  /**
   * 角色编号
   */
  private Long roleId;

  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 真实姓名
   */
  private String userRealName;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 配置人
   */
  private String createUserName;

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
