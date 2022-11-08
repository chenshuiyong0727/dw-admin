package com.hf.op.infrastructure.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * @author chensy
 * @function 基础日志登录DTO
 * @date 2021/411
 */
@Data
@ToString
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuthLoginLogDto {

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 用户本地编号，对应旧的统一账户平台用户编号
   */
  private String userLocalNo;

  /**
   * 登录时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime loginTime;
  /**
   * 登录IP
   */
  private String ip;

  /**
   * 登录系统,10-运营系统,11-和家公众号,12-和家小程序,13-和家平板,14-和家安卓,15-和家IOS,16-和家WEB
   */
  private Integer loginSystem;

}
