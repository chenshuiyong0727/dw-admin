package com.hf.op.infrastructure.dto.role;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 角色列表数据传输对象
 * @Date 2022/02/07
 */
@Data
public class QueryOpRoleListDto implements Serializable {

  private static final long serialVersionUID = -3456598591211752286L;

  /**
   * 角色编号
   */
  private Long id;

  /**
   * 所属系统 1-组织中台,2-运营运营平台,3-小程序
   */
  private Integer systemId;

  /**
   * 角色编号
   */
  private String systemName;

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 备注
   */
  private String description;

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
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  @TableField(exist = false)
  private Integer pageNum;

}
