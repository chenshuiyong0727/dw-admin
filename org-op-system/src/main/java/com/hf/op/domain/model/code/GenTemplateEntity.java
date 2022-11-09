package com.hf.op.domain.model.code;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author system
 * @function gen_template的实体类
 * @date 2022-08-23
 */
@Data
@TableName("gen_template")
public class GenTemplateEntity extends BaseEntity implements Serializable {

  /**
   * 数据状态：-1-删除；0-禁用；1-正常
   */
  private Integer dataStatus;

  /**
   * 主键
   */
  private Long id;

  /**
   * 备注信息
   */
  private String remark;

  /**
   * 表类型
   */
  private String tableType;

  /**
   * 模板名称
   */
  private String tempName;
}
