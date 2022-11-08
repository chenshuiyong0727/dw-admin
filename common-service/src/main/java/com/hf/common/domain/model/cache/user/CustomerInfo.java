package com.hf.common.domain.model.cache.user;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 客户信息缓存
 * @date 2022/4/13
 */
@Data
public class CustomerInfo implements Serializable {

  private static final long serialVersionUID = 72075073250210078L;

  /**
   * 账户编号
   */
  private Long accountId;

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 客户编号
   */
  private String customerId;

  /**
   * 状态
   */
  private String dataStatus;

  /**
   * 企业名称
   */
  private String customerName;

  /**
   * 密钥
   */
  private String secretKey;


}
