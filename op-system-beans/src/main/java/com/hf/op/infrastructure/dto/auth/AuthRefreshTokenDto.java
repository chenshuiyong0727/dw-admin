package com.hf.op.infrastructure.dto.auth;

import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 鉴权刷新token传输对象
 * @Date 2021/12/09
 */
@Data
public class AuthRefreshTokenDto implements Serializable {

  private static final long serialVersionUID = 5502689304856512337L;

  /**
   * 统一用户编号
   */
  private String userId;

  /**
   * 令牌，一段时间后失效
   */
  private String token;

  /**
   * 用于刷新的令牌，令牌失效前根据刷新令牌延长令牌时效
   */
  private String refreshToken;

}
