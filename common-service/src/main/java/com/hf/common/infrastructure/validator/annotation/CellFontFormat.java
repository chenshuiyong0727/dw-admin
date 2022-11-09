package com.hf.common.infrastructure.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 字体格式化
 *
 * @author Parker
 * @date 2020-09-16 11:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CellFontFormat {

  String fontName() default "Arial";

  short fontHeightInPoints() default 10;

  IndexedColors fontColor() default IndexedColors.BLACK;

  boolean bold() default false;

}
