package com.hf.common.domain;

import java.io.Serializable;

/**
 * 值对象
 */
public interface ValueObject<T> extends Serializable {

  /**
   * 用于比较值对象的值
   */
  boolean sameValueAs(T other);

}
