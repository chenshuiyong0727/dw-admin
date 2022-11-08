package com.hf.op.domain.model.department;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 运营系统部门(op_department)实体类
 * @Date 2021/11/29
 */
@Data
@TableName("op_department")
public class OpDepartmentEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2330643662856475877L;

  /**
   * 部门名称
   */
  @TableField("`name`")
  private String name;

  /**
   * 部门级别，从1开始叠加，三级部门就是3
   */
  @TableField("`level`")
  private Integer level;

  /**
   * 一级部门，如果上级部门存在该字段直接拷贝
   */
  private Long levelOneDepartment;

  /**
   * 一级部门名称
   */
  private String levelOneDepartmentName;

  /**
   * 二级部门，如果上级部门存在该字段直接拷贝
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long levelTwoDepartment;

  /**
   * 二级部门名称
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String levelTwoDepartmentName;

  /**
   * 三级部门，如果上级部门存在该字段直接拷贝
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long levelThreeDepartment;

  /**
   * 三级部门名称
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String levelThreeDepartmentName;

  /**
   * 四级部门，如果上级部门存在该字段直接拷贝
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long levelFourDepartment;

  /**
   * 四级部门名称
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String levelFourDepartmentName;

  /**
   * 上级部门
   */
  private Long superiorDepartment;
  /**
   * 上级部门名称
   */
  @TableField(exist = false)
  private String superiorDepartmentName;

  /**
   * 其他上级，四级部门以下的上级部门通过部门编号拼接，多个部门用逗号分隔，如果上级部门存在该字段直接拷贝后追加上级部门编号
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String otherSuperiorDepartment;

  /**
   * 其他上级名称
   */
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String otherSuperiorDepartmentName;

  /**
   * 部门说明
   */
  private String description;

  public void setLevelParams(OpDepartmentEntity opDepartmentEntity,
      OpDepartmentEntity parentOpDepartmentEntity) {
    if (opDepartmentEntity.getLevel() == 2) {
      opDepartmentEntity.setLevelOneDepartment(parentOpDepartmentEntity.getLevelOneDepartment());
      opDepartmentEntity
          .setLevelOneDepartmentName(parentOpDepartmentEntity.getLevelOneDepartmentName());
      opDepartmentEntity.setLevelTwoDepartment(opDepartmentEntity.getId());
      opDepartmentEntity.setLevelTwoDepartmentName(opDepartmentEntity.getName());

      opDepartmentEntity.setLevelThreeDepartment(null);
      opDepartmentEntity.setLevelThreeDepartmentName(null);
      opDepartmentEntity.setLevelFourDepartment(null);
      opDepartmentEntity.setLevelFourDepartmentName(null);
      opDepartmentEntity.setOtherSuperiorDepartment(null);
      opDepartmentEntity.setOtherSuperiorDepartmentName(null);

    } else if (opDepartmentEntity.getLevel() == 3) {
      opDepartmentEntity.setLevelOneDepartment(parentOpDepartmentEntity.getLevelOneDepartment());
      opDepartmentEntity
          .setLevelOneDepartmentName(parentOpDepartmentEntity.getLevelOneDepartmentName());
      opDepartmentEntity.setLevelTwoDepartment(parentOpDepartmentEntity.getLevelTwoDepartment());
      opDepartmentEntity
          .setLevelTwoDepartmentName(parentOpDepartmentEntity.getLevelTwoDepartmentName());
      opDepartmentEntity.setLevelThreeDepartment(opDepartmentEntity.getId());
      opDepartmentEntity.setLevelThreeDepartmentName(opDepartmentEntity.getName());

      opDepartmentEntity.setLevelFourDepartment(null);
      opDepartmentEntity.setLevelFourDepartmentName(null);
      opDepartmentEntity.setOtherSuperiorDepartment(null);
      opDepartmentEntity.setOtherSuperiorDepartmentName(null);
    } else if (opDepartmentEntity.getLevel() == 4) {
      opDepartmentEntity.setLevelOneDepartment(parentOpDepartmentEntity.getLevelOneDepartment());
      opDepartmentEntity
          .setLevelOneDepartmentName(parentOpDepartmentEntity.getLevelOneDepartmentName());
      opDepartmentEntity.setLevelTwoDepartment(parentOpDepartmentEntity.getLevelTwoDepartment());
      opDepartmentEntity
          .setLevelTwoDepartmentName(parentOpDepartmentEntity.getLevelTwoDepartmentName());
      opDepartmentEntity
          .setLevelThreeDepartment(parentOpDepartmentEntity.getLevelThreeDepartment());
      opDepartmentEntity
          .setLevelThreeDepartmentName(parentOpDepartmentEntity.getLevelThreeDepartmentName());
      opDepartmentEntity.setLevelFourDepartment(opDepartmentEntity.getId());
      opDepartmentEntity.setLevelFourDepartmentName(opDepartmentEntity.getName());

      opDepartmentEntity.setOtherSuperiorDepartment(null);
      opDepartmentEntity.setOtherSuperiorDepartmentName(null);
    } else if (opDepartmentEntity.getLevel() == 5) {
      opDepartmentEntity.setLevelOneDepartment(parentOpDepartmentEntity.getLevelOneDepartment());
      opDepartmentEntity
          .setLevelOneDepartmentName(parentOpDepartmentEntity.getLevelOneDepartmentName());
      opDepartmentEntity.setLevelTwoDepartment(parentOpDepartmentEntity.getLevelTwoDepartment());
      opDepartmentEntity
          .setLevelTwoDepartmentName(parentOpDepartmentEntity.getLevelTwoDepartmentName());
      opDepartmentEntity
          .setLevelThreeDepartment(parentOpDepartmentEntity.getLevelThreeDepartment());
      opDepartmentEntity
          .setLevelThreeDepartmentName(parentOpDepartmentEntity.getLevelThreeDepartmentName());
      opDepartmentEntity.setLevelFourDepartment(parentOpDepartmentEntity.getLevelFourDepartment());
      opDepartmentEntity
          .setLevelFourDepartmentName(parentOpDepartmentEntity.getLevelFourDepartmentName());
      opDepartmentEntity.setOtherSuperiorDepartment(opDepartmentEntity.getId().toString());
      opDepartmentEntity.setOtherSuperiorDepartmentName(opDepartmentEntity.getName());
    } else {
      opDepartmentEntity.setLevelOneDepartment(parentOpDepartmentEntity.getLevelOneDepartment());
      opDepartmentEntity
          .setLevelOneDepartmentName(parentOpDepartmentEntity.getLevelOneDepartmentName());
      opDepartmentEntity.setLevelTwoDepartment(parentOpDepartmentEntity.getLevelTwoDepartment());
      opDepartmentEntity
          .setLevelTwoDepartmentName(parentOpDepartmentEntity.getLevelTwoDepartmentName());
      opDepartmentEntity
          .setLevelThreeDepartment(parentOpDepartmentEntity.getLevelThreeDepartment());
      opDepartmentEntity
          .setLevelThreeDepartmentName(parentOpDepartmentEntity.getLevelThreeDepartmentName());
      opDepartmentEntity.setLevelFourDepartment(parentOpDepartmentEntity.getLevelFourDepartment());
      opDepartmentEntity
          .setLevelFourDepartmentName(parentOpDepartmentEntity.getLevelFourDepartmentName());
      String otherSuperiorDepartment = parentOpDepartmentEntity.getOtherSuperiorDepartment()
          .concat(",").concat(opDepartmentEntity.getId().toString());
      opDepartmentEntity.setOtherSuperiorDepartment(otherSuperiorDepartment);
      String otherSuperiorDepartmentName = parentOpDepartmentEntity.getOtherSuperiorDepartmentName()
          .concat("-").concat(opDepartmentEntity.getName());
      opDepartmentEntity.setOtherSuperiorDepartmentName(otherSuperiorDepartmentName);
    }
  }

}
