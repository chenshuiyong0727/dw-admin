package com.hf.common.infrastructure.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author wpq
 * @function 加解密工具
 * @Date 2021/12/08
 */
public class EncryptUtils {

  public static final String CHARSET = "UTF-8";

  /**
   * MD5
   */
  public static String md5(String strSrc) {
    String outString;
    try {
      outString = DigestUtils.md5Hex(strSrc.getBytes(CHARSET));
    } catch (Exception e) {
      throw new RuntimeException("encrypt error：", e);
    }
    return outString;
  }

  /**
   * 数据加密，算法（AES）
   */
  public static String encryptAES(String strKey, String strIn) {
    String outString;
    try {
      outString = AES.aesEncrypt(strIn, strKey);
    } catch (Exception e) {
      throw new RuntimeException("encrypt error：", e);
    }
    return outString;
  }

  /**
   * 数据加密，算法（AES）url-safe
   */
  public static String encryptAESURLSafe(String strKey, String strIn) {
    String outString;
    try {
      outString = AES.aesEncryptURLSafe(strIn, strKey);
    } catch (Exception e) {
      throw new RuntimeException("encrypt error：", e);
    }
    return outString;
  }

  /**
   * 数据解密，算法（AES）
   */
  public static String decryptAES(String strKey, String strIn) {
    String outString;
    try {
      outString = AES.aesDecrypt(strIn, strKey);
    } catch (Exception e) {
      throw new RuntimeException("encrypt error：", e);
    }
    return outString;
  }

}
