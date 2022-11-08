package com.hf.common.infrastructure.entity;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author: flyme
 * @date: 2018/3/7 15:01
 * @desc: 实体类父类
 */
@Data
public abstract class AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  public Date createtime;
  public Date updatetime;
}
