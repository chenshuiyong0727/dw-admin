package com.hf.op.domain.model.auth;

import com.auth.common.infrastructure.constant.LoginAccountTypeEnum;
import com.auth.common.infrastructure.resp.AuthRespCodeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseBusinessEntity;
import com.hf.common.infrastructure.resp.BusinessRespCodeEnum;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.PasswordUtils;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.op.infrastructure.dto.auth.AuthUserComDto;
import com.hf.op.infrastructure.global.AuthSvcGlobalConst;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 统一用户表(auth_unified_user)的实体类
 * @date 2021/2/5
 */
@Data
@TableName("auth_unified_user")
public class AuthUnifiedUserEntity extends BaseBusinessEntity implements Serializable {

  private static final long serialVersionUID = -7881063083159309583L;

  /**
   * 本地编号，冗余字段，对应旧的统一账户平台用户编号
   */
  private String localNo;

  /**
   * SSO统一用户编号
   */
  private String ssoUserNo;

  /**
   * 用户类型: 10-用户 11-企业用户 12-运营平台用户 13-sso用户
   */
  private Integer userType;

  /**
   * 登录密码
   */
  private String loginPassword;

  /**
   * 盐,加密使用，更新登录时间时要同时刷新该值
   */
  private String salt;

  /**
   * 是否默认密码，0：不是，1:是，如果是默认密码则登录后提醒修改密码
   */
  private Integer defaultPwd;
  /**
   * 同步状态：0-未同步；1-已同步
   */
  private Integer syncStatus;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号码
   */
  private String userMobile;

  /**
   * 身份证号码
   */
  private String idCardNo;

  /**
   * 用户账户
   */
  private String userAccount;

  /**
   * 用户真实姓名
   */
  private String userRealName;

  /**
   * 用户随机密码
   */
  @TableField(exist = false)
  private String passWord;

  /**
   * 注册时间
   */
  private LocalDateTime regtime;

  /**
   * 登录校验
   */
  public static ResponseMsg loginCheck(String loginAccount, String pwd, String veryCode,
      Integer accountType) {
    assert StringUtilLocal.isNotEmpty(loginAccount) && accountType != null;
    if (accountType == LoginAccountTypeEnum.USER_NAME.getCode()) {
      if (StringUtilLocal.isEmpty(loginAccount) || null == accountType) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else {
      if (StringUtilLocal.isEmpty(loginAccount) || StringUtilLocal
          .isEmpty(veryCode) || null == accountType) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    }
    if (accountType.intValue() == LoginAccountTypeEnum.USER_NAME.getCode()
        || accountType.intValue() == LoginAccountTypeEnum.ID_CARD.getCode()) {
      if (StringUtilLocal.isEmpty(pwd)) {
        return new ResponseMsg(BusinessRespCodeEnum.PARAM_IS_EMPTY.getCode(),
            BusinessRespCodeEnum.PARAM_IS_EMPTY.getMsg());
      }
    } else if (accountType.intValue() != LoginAccountTypeEnum.MOBILE.getCode()) {
      return new ResponseMsg(AuthRespCodeEnum.LOGIN_TYPE_NOT_SUPPORT.getCode(),
          AuthRespCodeEnum.LOGIN_TYPE_NOT_SUPPORT.getMsg());
    }
    return new ResponseMsg(1000, "验证成功");
  }

  /**
   * 注册设值
   */
  public void registerSet(AuthUnifiedUserEntity authUnifiedUserEntity,
      AuthUserComDto authUserComDto) {
    String pwd = String.valueOf(PasswordUtils.randomPwd());
    authUnifiedUserEntity
        .setDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setUserType(authUserComDto.getUserType());
    authUnifiedUserEntity.setUserEmail(authUserComDto.getUserEmail());
    authUnifiedUserEntity.setUserMobile(authUserComDto.getUserMobile());
    authUnifiedUserEntity.setUserAccount(authUserComDto.getUserAccount());
    authUnifiedUserEntity.setUserRealName(authUserComDto.getUserRealName());
    authUnifiedUserEntity.setIdCardNo(authUserComDto.getIdCardNo());
    authUnifiedUserEntity.setSalt(PasswordUtils.getSalt());
    authUnifiedUserEntity.setSyncStatus(AuthUserComDto.SYNC_STATUS_SUCCESS);
    String encode = PasswordUtils.encode(pwd, authUnifiedUserEntity.getSalt());
    authUnifiedUserEntity.setLoginPassword(encode);
    authUnifiedUserEntity.setDefaultPwd(AuthSvcGlobalConst.DefaultPwd.DefaultPwdYes);
    authUnifiedUserEntity.setPassWord(pwd);
    LocalDateTime regtime =
        null == authUserComDto.getRegtime() ? LocalDateTime.now() : authUserComDto.getRegtime();
    authUnifiedUserEntity.setRegtime(regtime);

  }

  /**
   * @param authUnifiedUserEntity 统一用户
   * @param authUserComDto 统一用户
   * @description
   * @method registerAdd
   * @date: 2022/4/8 19:35
   * @author: chensy
   */
  public void registerAdd(AuthUnifiedUserEntity authUnifiedUserEntity,
      AuthUserComDto authUserComDto) {
    authUnifiedUserEntity
        .setDefaultInfo(authUserComDto.getSysUserId(), authUserComDto.getSysUserAccount());
    authUnifiedUserEntity.setLocalNo(authUserComDto.getLocalNo());
    authUnifiedUserEntity.setUserType(authUserComDto.getUserType());
    authUnifiedUserEntity.setUserEmail(authUserComDto.getUserEmail());
    authUnifiedUserEntity.setUserMobile(authUserComDto.getUserMobile());
    authUnifiedUserEntity.setUserAccount(authUserComDto.getUserAccount());
    authUnifiedUserEntity.setUserRealName(authUserComDto.getUserRealName());
    authUnifiedUserEntity.setIdCardNo(authUserComDto.getIdCardNo());
    authUnifiedUserEntity.setLoginPassword(authUserComDto.getNewPwd());
    authUnifiedUserEntity.setDefaultPwd(authUserComDto.getDefaultPwd());
    authUnifiedUserEntity.setPassWord(authUserComDto.getNewPwd());
    authUnifiedUserEntity.setSyncStatus(authUserComDto.getSyncStatus());
    authUnifiedUserEntity.setRegtime(authUserComDto.getRegtime());
  }

}
