package com.hf.op.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import lombok.Data;

/**
 * 功能权限
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthorityAction extends AdminActionModel {

  private static final long serialVersionUID = -691740581827186502L;

  /**
   * 权限ID
   */
  private String authorityId;

  /**
   * 权限标识
   */
  private String authority;

  /**
   * 是否需要安全认证
   */
  private Boolean isAuth = true;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuthorityAction)) {
      return false;
    }
    AuthorityAction a = (AuthorityAction) obj;
    return this.authorityId.equals(a.getAuthorityId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorityId);
  }
}
