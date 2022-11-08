package com.hf.op.domain.model.role.mini;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.AbstractEntity;
import java.util.Date;
import lombok.Data;

/**
 * 系统权限-角色关联
 */
@Data
@TableName("admin_authority_role")
public class AdminAuthorityRole extends AbstractEntity {

  private static final long serialVersionUID = 1L;
  /**
   * 权限ID
   */
  private String authorityid;
  /**
   * 角色ID
   */
  private String roleid;
  /**
   * 过期时间:null表示长期
   */
  private Date expiretime;
}
