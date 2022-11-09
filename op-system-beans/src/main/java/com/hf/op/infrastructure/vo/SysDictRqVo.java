package com.hf.op.infrastructure.vo;

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
public class SysDictRqVo implements Serializable {

  private static final long serialVersionUID = 3549335957376900578L;

  /**
   * 数据字典编号
   */
  private Long id;

  /**
   * 数据字典类型编号
   */
  private Long typeId;

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
  /**
   * 每页记录数,不填默认10
   */
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  private Integer pageNum;
}
