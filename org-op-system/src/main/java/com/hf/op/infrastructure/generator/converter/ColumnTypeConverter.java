package com.hf.op.infrastructure.generator.converter;

import com.hf.op.infrastructure.generator.enums.TypeEnum;
import java.util.List;

/**
 * 将数据库类型转换成各语言对应的类型
 *
 * @author Parker
 * @date 2021年5月26日14:22:31
 */
public interface ColumnTypeConverter {

  /**
   * 将数据库类型转成基本类型
   *
   * @param type 数据库类型
   * @return 基本类型
   */
  String convertType(TypeEnum type);

  /**
   * 将数据库类型转成基本类型 (会多返 一个String 兜底类型)
   *
   * @param type 数据库类型
   * @return List 基本类型
   */
  List<String> convertTypeBySafety(TypeEnum type);

}
