package com.hf.op.infrastructure.dto.auth;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 梁灿龙
 * @function
 * @date 2022/4/1
 **/
@Data
public class AuthCustomerAccountComDto implements Serializable {

  private static final long serialVersionUID = -4610680484596381434L;

  /**
   * 企业账户id
   */
  private Long customerId;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 状态
   */
  private Integer dataStatus;

  @Override
  public String toString() {
    return "AuthCustomerAccountComDto{" +
        "customerId=" + customerId +
        ", dataStatus=" + dataStatus +
        '}';
  }
}
