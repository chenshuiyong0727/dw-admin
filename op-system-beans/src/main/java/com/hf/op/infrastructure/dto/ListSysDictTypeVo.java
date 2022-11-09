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
public class ListSysDictTypeVo implements Serializable {

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
  /**
   * 每页记录数,不填默认10
   */
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  private Integer pageNum;
}
