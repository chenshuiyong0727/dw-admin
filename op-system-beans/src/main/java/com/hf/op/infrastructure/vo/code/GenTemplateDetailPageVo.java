package com.hf.op.infrastructure.vo.code;

import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_template_detail的实体类
 * @date 2022-08-23
 */
@Data
public class GenTemplateDetailPageVo implements Serializable {

  /**
   * 数据状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 文件内容
   */
  private String fileContent;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 主键
   */
  private Long id;

  /**
   * 忽略文件名
   */
  private String ignoreFileName;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 路径
   */
  private String path;

  /**
   * 类型 0 后端  / 1 前端
   */
  private String type;

}
