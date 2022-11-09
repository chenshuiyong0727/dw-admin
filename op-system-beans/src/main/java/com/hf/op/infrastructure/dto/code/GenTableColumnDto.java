package com.hf.op.infrastructure.dto.code;

import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_table_column的实体类
 * @date 2022-08-23
 */
@Data
public class GenTableColumnDto implements Serializable {

  /**
   * 数据状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 字典类型
   */
  private String dictTypeCode;

  /**
   * 字段描述
   */
  private String fieldComments;

  /**
   * 字段长度
   */
  private Integer fieldLength;

  /**
   * 字段名称
   */
  private String fieldName;

  /**
   * 字段精度
   */
  private Integer fieldPrecision;

  /**
   * 字段类型
   */
  private String fieldType;

  /**
   * 主键
   */
  private Long id;

  /**
   * 是否可为空
   */
  private String izNotNull;

  /**
   * 是否主键
   */
  private String izPk;

  /**
   * 是否表单显示
   */
  private String izShowForm;

  /**
   * 是否列表字段
   */
  private String izShowList;

  /**
   * Java数据类型
   */
  private String javaType;

  /**
   * 检索类别
   */
  private String queryType;

  /**
   * 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
   */
  private String showType;

  /**
   * 排序（升序）
   */
  private Integer sort;

  /**
   * 归属表ID
   */
  private Long tableId;

  /**
   * 验证类别
   */
  private String validateType;
  /**
   * 每页记录数,不填默认10
   */
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  private Integer pageNum;

  /**
   * 创建用户
   */
  private String createUserName;
  /**
   * 更新用户
   */
  private String updateUserName;

  /**
   * 创建时间开始
   */
  private String createTimeFrom;

  /**
   * 创建时间结束
   */
  private String createTimeTo;

  /**
   * 修改时间开始
   */
  private String updateTimeFrom;

  /**
   * 修改时间结束
   */
  private String updateTimeTo;


}
