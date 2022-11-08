package com.open.api.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 平台权限DTO
 * @date 2021/11/9
 */
@Data
public class FunctionDto implements Serializable {

  private static final long serialVersionUID = 3512474338326716339L;

  /**
   * 权限编号
   */
  private Long id;

  /**
   * 权限键值
   */
  private String functionKey;

  /**
   * 系统id
   */
  private Integer systemId;

  /**
   * 权限名称
   */
  private String functionName;

  /**
   * 权限描述
   */
  private String functionDesc;

  /**
   * 路由
   */
  private String route;

  /**
   * 本地路径，用于页面分级展示
   */
  private String locationPath;
  /**
   * 其他路径，当operationType为JUMP 时，用于页面分级展示
   */
  private String otherLocationPath;

  /**
   * 操作类型: none-无附加操作 list-列表展示 post-添加 put-修改 delete-删除 get-单个查询 query-列表查询 export-导出 import-导入
   * batch_delete-批量删除 batch_modify-批量修改 reset-查询重置 jump:列表跳转 stretch-伸展 other:其他
   */
  private String operationType;

  /**
   * 权限类型: 1-菜单权限 2-列表查询按钮权限 3-列表记录按钮权限，存放于列表记录中 9-其他
   */
  private Integer functionType;

  /**
   * 权限标签，如按钮上显示的名称
   */
  private String functionLabel;

  /**
   * 排序
   */
  private Long sort;

}
