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
public class AuthUserStatusListDto implements Serializable {

  /**
   * 同步状态：0-未同步；1-已同步
   */
  private Integer syncStatus;

  /**
   * 用户id集合
   */
  private List<Long> userIds;

}
