package com.auth.common.infrastructure.resp;

import java.io.Serializable;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾仕斌
 * @function 鉴权结果
 * @date 2021/3/31
 */
@Data
@ResponseBody
public class SsoUserInfoResp implements Serializable {

  private static final long serialVersionUID = -8239345963871488314L;

  /**
   * 员工类别：0-试用期员工 1-正式员工
   */
  private Integer staffType;

  /**
   * 性别：0-男 1-女 2-未知
   */
  private Integer gender;

  /**
   * 真实姓名
   */
  private String userRealName;

  /**
   * 邮箱地址
   */
  private String userEmail;

  /**
   * 手机号
   */
  private String userMobile;
}
