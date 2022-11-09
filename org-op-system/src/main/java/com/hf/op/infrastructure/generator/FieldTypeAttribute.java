package com.hf.op.infrastructure.generator;


import lombok.Data;


/**
 * MySQL 字段类型 属性
 *
 * @author parker
 * @date 2020-11-18 13:21
 */
@Data
public class FieldTypeAttribute {

  private boolean izLength;

  private boolean izPrecision;

  public FieldTypeAttribute() {
  }

  public FieldTypeAttribute(boolean izLength, boolean izPrecision) {
    this.izLength = izLength;
    this.izPrecision = izPrecision;
  }
}
