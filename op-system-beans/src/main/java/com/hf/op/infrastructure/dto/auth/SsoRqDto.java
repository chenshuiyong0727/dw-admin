package com.hf.op.infrastructure.dto.auth;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * @author chensy
 * @function
 * @date 2022/4/1
 **/
@Data
@ToString
public class SsoRqDto implements Serializable {

  private static final long serialVersionUID = -4610680484596381434L;

  /**
   * sessionId
   */
  private String sessionId;

  /**
   * redisKey
   */
  private String redisKey;
}
