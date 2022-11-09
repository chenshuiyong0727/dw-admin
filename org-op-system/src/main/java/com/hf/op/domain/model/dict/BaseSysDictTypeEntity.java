package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 字典类型
 * @Date 2021/12/13
 */
@Data
@TableName("base_sys_dict_type")
public class BaseSysDictTypeEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3165218897477926180L;

  /**
   * 字典类型名称
   */
  private String typeName;

  /**
   * 字典类型值
   */
  private String typeValue;

}
