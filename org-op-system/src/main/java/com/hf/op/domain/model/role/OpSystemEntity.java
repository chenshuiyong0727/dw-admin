package com.hf.op.domain.model.role;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author chensy
 * @function 系统分类
 * @date 2022/06/13
 */
@Data
@TableName("op_system")
public class OpSystemEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -440530723282491139L;

  /**
   * 系统名称
   */
  private String systemName;

  /**
   * 系统地址
   */
  private Integer systemUrl;


}
