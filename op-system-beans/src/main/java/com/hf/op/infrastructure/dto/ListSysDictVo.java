package com.hf.op.infrastructure.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * @author wpq
 * @function 系统数据字典
 * @Date 2021/12/13
 */
@Data
@ToString
public class ListSysDictVo implements Serializable {

  private static final long serialVersionUID = 3549335957376900578L;

  /**
   * 数据字典编号
   */
  private String id;

  /**
   * 类型值
   */
  private String typeValue;

  /**
   * 类型名称
   */
  private String typeName;

  /**
   * 字段值
   */
  private String fieldValue;

  /**
   * 字段名称
   */
  private String fieldName;

  /**
   * 秘钥
   */
  private Integer sort;

}
