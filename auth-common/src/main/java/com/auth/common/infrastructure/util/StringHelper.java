package com.auth.common.infrastructure.util;

public class StringHelper {

  public static String getObjectValue(Object obj) {
    return obj == null ? "" : obj.toString();
  }
}
