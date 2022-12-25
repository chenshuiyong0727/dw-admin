package com.hf.op.service.inf.auth;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.op.infrastructure.dto.auth.AuthLoginComDto;
import com.hf.op.infrastructure.dto.auth.AuthRefreshTokenDto;
import com.hf.op.infrastructure.dto.auth.AuthUserComDto;

/**
 * @author 曾仕斌
 * @function 用户鉴权接口
 * @date 2021/11/9
 */
public interface AuthService {

  ResponseMsg login(AuthLoginComDto authLoginComDto) throws Exception;
  ResponseMsg loginH5(AuthLoginComDto authLoginComDto) throws Exception;

  ResponseMsg refreshToken(AuthRefreshTokenDto authRefreshTokenDto) throws Exception;

  /**
   * 权限校验
   */
  ResponseMsg checkFunction(Long userId, String requestUri);

  ResponseMsg register(AuthUserComDto authUserComDto);

  /**
   * 重置用户状态
   */
  ResponseMsg resetUserPwd(AuthUserComDto authUserComDto);

  /**
   * 更新用户状态
   */
  ResponseMsg updateAuthUserStatus(AuthUserComDto authUserComDto);

  /**
   * 更新用户
   */
  ResponseMsg updateAuthUser(AuthUserComDto authUserComDto);

  /**
   * 修改用户状态
   */
  ResponseMsg updateUserPwd(AuthUserComDto authUserComDto);

}
