package com.hf.op.infrastructure.dto.code;

import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_template_detail的实体类
 * @date 2022-08-23
 */
@Data
public class GenTemplateDetailDto implements Serializable {

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
