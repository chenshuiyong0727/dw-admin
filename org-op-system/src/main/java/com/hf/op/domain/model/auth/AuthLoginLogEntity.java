package com.hf.op.domain.model.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 鉴权登录日志表(auth_login_log)的实体类
 * @Date 2021/12/09
 */
@Data
@TableName("auth_login_log")
public class AuthLoginLogEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -487868380897193823L;

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 登录IP
   */
  private String ip;

  /**
   * 登录系统,10-运营系统,11-和家公众号,12-和家小程序,13-和家平板,14-和家安卓,15-和家IOS,16-和家WEB
   */
  private Integer loginSystem;

}
