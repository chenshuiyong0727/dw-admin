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
public class ListSysDictTypePageVo implements Serializable {

  private static final long serialVersionUID = 3549335957376900578L;

  /**
   * 编号
   */
  public Long id;

  /**
   * 字典类型名称
   */
  private String typeName;

  /**
   * 字典类型值
   */
  private String typeValue;
}
