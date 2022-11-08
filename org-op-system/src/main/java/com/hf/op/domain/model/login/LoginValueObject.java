package com.hf.op.domain.model.login;

import com.hf.common.domain.ValueObject;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 登录值对象
 * @date 2021/3/11
 */
@Data
public class LoginValueObject implements ValueObject<LoginValueObject> {

  private static final long serialVersionUID = 587831222077440769L;

  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 登录密码
   */
  private String loginPassword;

  /**
   * 登录账户类型:1-用户名 2-手机
   */
  private Integer accountType = 1;

  /**
   * 校验码
   */
  private String verifyCode;

  @Override
  public boolean sameValueAs(LoginValueObject other) {
    if (null == other || StringUtilLocal.isEmpty(this.loginAccount) || StringUtilLocal
        .isEmpty(other.loginAccount)) {
      return false;
    }
    return other.loginAccount.equals(this.loginAccount);
  }
}
