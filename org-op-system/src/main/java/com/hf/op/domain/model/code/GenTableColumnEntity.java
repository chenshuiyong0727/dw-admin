package com.hf.op.domain.model.code;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author system
 * @function gen_table_column的实体类
 * @date 2022-08-23
 */
@Data
@TableName("gen_table_column")
public class GenTableColumnEntity extends BaseEntity implements Serializable {

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
   * 验证集合
   */
  @TableField(exist = false)//非数据库字段
  private List<String> validateTypeList;

  @Override
  public String toString() {
    return "GenTableColumnEntity{createTime:" + createTime
        + ",createUserId:" + createUserId
        + ",createUserName:" + createUserName
        + ",dataStatus:" + dataStatus
        + ",dictTypeCode:" + dictTypeCode
        + ",fieldComments:" + fieldComments
        + ",fieldLength:" + fieldLength
        + ",fieldName:" + fieldName
        + ",fieldPrecision:" + fieldPrecision
        + ",fieldType:" + fieldType
        + ",id:" + id
        + ",izNotNull:" + izNotNull
        + ",izPk:" + izPk
        + ",izShowForm:" + izShowForm
        + ",izShowList:" + izShowList
        + ",javaType:" + javaType
        + ",queryType:" + queryType
        + ",showType:" + showType
        + ",sort:" + sort
        + ",tableId:" + tableId
        + ",updateTime:" + updateTime
        + ",updateUserId:" + updateUserId
        + ",updateUserName:" + updateUserName
        + ",validateType:" + validateType + '}';
  }
}
