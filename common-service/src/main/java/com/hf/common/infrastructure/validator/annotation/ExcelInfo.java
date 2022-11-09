package com.hf.common.infrastructure.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 字典标示
 *
 * @author Parker
 * @date 2020-09-16 11:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelInfo {

  /**
   * 字典类型 code
   */
  String dictType() default "";

  /**
   * 字段样式
   */
  CellStyleFormat cellStyleFormat() default @CellStyleFormat();

}
