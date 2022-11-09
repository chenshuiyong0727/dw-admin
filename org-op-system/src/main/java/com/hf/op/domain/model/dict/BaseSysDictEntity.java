package com.hf.op.domain.model.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hf.common.infrastructure.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

/**
 * @author wpq
 * @function 字典
 * @Date 2021/12/13
 */
@Data
@TableName("base_sys_dict")
public class BaseSysDictEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -2222622328099330511L;

  /**
   * 字典类型编号
   */
  private Long typeId;

  /**
   * 字典名称
   */
  private String fieldName;

  /**
   * 字典值
   */
  private String fieldValue;

  /**
   * 排序
   */
  private Integer sort;

}
