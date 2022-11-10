package com.hf.op.domain.model.function;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 运营系统权限(op_sys_function)实体类
 * @date 2021/11/08
 */
@Data
@TableName("op_sys_function")
public class OpSysFunctionEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 566166562198410705L;

  /**
   * 编号
   */
  @TableId(value = "id", type = IdType.AUTO)
  public Long id;

  /**
   * 权限键值
   */
  private String functionKey;

  /**
   * 路由，值由WEB端提供
   */
  private String route;

  /**
   * 本地路径，用于页面分级展示，如sys:role表示角色列表
   */
  private String locationPath;

  /**
   * 其他路径，当operationType 为 JUMP 时，用于页面分级展示，如sys:role表示角色列表
   */
  private String otherLocationPath;

  /**
   * 权限名称
   */
  private String functionName;

  /**
   * 操作类型: none-无附加操作 list-列表展示 post-添加 put-修改 delete-删除 get-单个查询 query-列表查询 export-导出 import-导入
   * batch_delete-批量删除 batch_modify-批量修改 reset-查询重置 jump:列表跳转 stretch-伸展 other:其他
   */
  private String operationType;

  /**
   * 权限描述
   */
  private String functionDesc;

  /**
   * 权限类型: 1-菜单权限 2-列表查询框按钮权限 3-列表上方按钮权限 4-列表记录按钮权限，存放于列表记录中 9-其他
   */
  private Integer functionType;

  /**
   * 权限标签，如按钮上显示的名称
   */
  private String functionLabel;

  /**
   * 排序，取值越小越靠前
   */
  private Long sort;

  /**
   * 判断是否有访问权限
   *
   * @param requestUri
   * @param functions
   * @return
   */

  private Long pid;
  private Integer systemId;


}
