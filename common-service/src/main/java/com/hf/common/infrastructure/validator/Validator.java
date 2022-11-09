package com.hf.common.infrastructure.validator;

import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段验证器
 *
 * @author Parker
 * @date 2020-09-22 17:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Validator {

  ValidatorType[] value();

}
