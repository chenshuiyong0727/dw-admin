package com.hf.op.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.Data;

/**
 * 菜单权限
 *
 * @author liuyadu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthorityMenu extends AdminMenuModel implements Serializable {

  private static final long serialVersionUID = 3474271304324863160L;
  /**
   * 权限ID
   */
  private String authorityId;

  /**
   * 权限标识
   */
  private String authority;


  private List<AuthorityAction> actionList;


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuthorityMenu)) {
      return false;
    }
    AuthorityMenu a = (AuthorityMenu) obj;
    return this.authorityId.equals(a.getAuthorityId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorityId);
  }
}
