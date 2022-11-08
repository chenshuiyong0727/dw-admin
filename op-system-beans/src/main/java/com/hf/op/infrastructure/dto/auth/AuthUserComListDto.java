package com.hf.op.infrastructure.dto.auth;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author chensy
 * @function 注册统一用户listdto
 * @Date 2022/04/18
 */
@Data
public class AuthUserComListDto implements Serializable {

  /**
   * 本地编号，冗余字段，对应旧的统一账户平台用户编号
   */
  private List<String> localNos;

  /**
   * 用户id集合
   */
  private List<Long> userIds;

}
