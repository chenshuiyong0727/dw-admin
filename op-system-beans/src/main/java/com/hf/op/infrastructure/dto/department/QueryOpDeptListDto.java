package com.hf.op.infrastructure.dto.department;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hf.common.infrastructure.util.StringUtilLocal;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 * @author wpq
 * @function 部门列表数据传输对象
 * @Date 2021/11/30
 */
@Data
public class QueryOpDeptListDto implements Serializable {

  private static final long serialVersionUID = 5906455713104266249L;

  /**
   * 部门编号
   */
  private Long id;

  /**
   * 部门名称
   */
  private String name;

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
  private Long levelTwoDepartment;

  /**
   * 二级部门名称
   */
  private String levelTwoDepartmentName;

  /**
   * 三级部门，如果上级部门存在该字段直接拷贝
   */
  private Long levelThreeDepartment;

  /**
   * 三级部门名称
   */
  private String levelThreeDepartmentName;

  /**
   * 四级部门，如果上级部门存在该字段直接拷贝
   */
  private Long levelFourDepartment;

  /**
   * 四级部门名称
   */
  private String levelFourDepartmentName;

  /**
   * 其他上级，四级部门以下的上级部门通过部门编号拼接，多个部门用逗号分隔，如果上级部门存在该字段直接拷贝后追加上级部门编号
   */
  private String otherSuperiorDepartment;

  /**
   * 其他上级，四级部门以下的上级部门通过部门编号拼接，多个部门用逗号分隔，如果上级部门存在该字段直接拷贝后追加上级部门编号
   */
  private String otherSuperiorDepartmentName;

  /**
   * 更多层级
   */
  private String moreLevels;

  /**
   * 部门说明
   */
  private String description;

  /**
   * 状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 修改时间
   */
  private String updateTime;

  /**
   * 修改人名称
   */
  private String updateUserName;

  /**
   * 修改时间开始
   */
  @TableField(exist = false)
  private String updateTimeFrom;

  /**
   * 修改时间结束
   */
  @TableField(exist = false)
  private String updateTimeTo;

  /**
   * 每页记录数,不填默认10
   */
  @TableField(exist = false)
  private Integer pageSize;

  /**
   * 当前页码,不填默认首页
   */
  @TableField(exist = false)
  private Integer pageNum;

  public static String getDeptName(Map<Long, QueryOpDeptListDto> downListVoMap,
      String departmentId) {
    QueryOpDeptListDto deptListDto = downListVoMap.get(Long.parseLong(departmentId));
    if (deptListDto == null) {
      return null;
    }
    String name = deptListDto.getLevelOneDepartmentName();
    if (StringUtilLocal.isNotEmpty(deptListDto.getLevelTwoDepartmentName())) {
      name = name + "-" + deptListDto.getLevelTwoDepartmentName();
      if (StringUtilLocal.isNotEmpty(deptListDto.getLevelThreeDepartmentName())) {
        name = name + "-" + deptListDto.getLevelThreeDepartmentName();
        if (StringUtilLocal.isNotEmpty(deptListDto.getLevelFourDepartmentName())) {
          name = name + "-" + deptListDto.getLevelFourDepartmentName();
          if (StringUtilLocal.isNotEmpty(deptListDto.getOtherSuperiorDepartmentName())) {
            name = name + "-" + deptListDto.getOtherSuperiorDepartmentName();
          }
        }
      }
    }
    return name;
  }
}
