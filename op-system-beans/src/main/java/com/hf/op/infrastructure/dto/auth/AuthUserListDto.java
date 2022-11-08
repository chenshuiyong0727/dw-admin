package com.hf.op.infrastructure.dto.auth;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * @author chensy
 * @function 注册统一用户listdto
 * @Date 2022/04/28
 */
@Data
@ToString
public class AuthUserListDto implements Serializable {

  private static final long serialVersionUID = -7855505969830333944L;

  private List<AuthUserComDto> list;

}
