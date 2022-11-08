package com.hf.common.infrastructure.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author 曾仕斌
 * @function 签名工具
 * @date 2021/12/21
 */
public class SignUtil {


  private final static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  /**
   * 生成签名
   *
   * @param data 内容
   * @param key 键值
   */
  public static String generateSignature(final Map<String, Object> data, String key,
      String keyName) {
    Set<String> keySet = data.keySet();
    String[] keyArray = keySet.toArray(new String[keySet.size()]);
    Arrays.sort(keyArray);
    StringBuilder sb = new StringBuilder();
    for (String k : keyArray) {
      if ("sign".equals(k)) {
        continue;
      }
      if (data.get(k) != null && data.get(k).toString().trim().length() > 0) {
        sb.append(data.get(k).toString().trim());
      }
    }
    sb.append(key);
    return sign(sb.toString());
  }

  /**
   * api签名
   *
   * @param content 内容
   */
  private static String sign(String content) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] bytes = md5.digest(content.getBytes("UTF-8"));
      return toHexString(bytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   */
  private static String toHexString(byte[] b) {
    StringBuilder sb = new StringBuilder(b.length * 2);
    for (int i = 0; i < b.length; i++) {
      sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
      sb.append(hexChar[b[i] & 0x0f]);
    }
    return sb.toString();
  }

}
