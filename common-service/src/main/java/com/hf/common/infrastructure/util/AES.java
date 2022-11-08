package com.hf.common.infrastructure.util;

import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

public class AES {

  //算法
  private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

  /**
   * 将byte[]转为各种进制的字符串
   *
   * @param bytes byte[]
   * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
   * @return 转换后的字符串
   */
  public static String binary(byte[] bytes, int radix) {
    return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
  }

  /**
   * AES加密
   *
   * @param content 待加密的内容
   * @param encryptKey 加密密钥
   * @return 加密后的byte[]
   */
  public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128);
    Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

    return cipher.doFinal(content.getBytes("utf-8"));
  }


  /**
   * AES加密为base 64 code
   *
   * @param content 待加密的内容
   * @param encryptKey 加密密钥
   * @return 加密后的base 64 code
   */
  public static String aesEncrypt(String content, String encryptKey) throws Exception {
    return Base64.encodeBase64String(aesEncryptToBytes(content, encryptKey));
  }

  /**
   * AES加密为base 64 code url-safe
   *
   * @param content 待加密的内容
   * @param encryptKey 加密密钥
   * @return 加密后的base 64 code
   */
  public static String aesEncryptURLSafe(String content, String encryptKey) throws Exception {
    return Base64.encodeBase64URLSafeString(aesEncryptToBytes(content, encryptKey));
  }

  /**
   * AES解密
   *
   * @param encryptBytes 待解密的byte[]
   * @param decryptKey 解密密钥
   * @return 解密后的String
   */
  public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128);

    Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
    byte[] decryptBytes = cipher.doFinal(encryptBytes);
    return new String(decryptBytes, "utf-8");
  }


  /**
   * 将base 64 code AES解密
   *
   * @param encryptStr 待解密的base 64 code
   * @param decryptKey 解密密钥
   * @return 解密后的string
   */
  public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
    return StringUtils.isEmpty(encryptStr) ? null
        : aesDecryptByBytes(Base64.decodeBase64(encryptStr), decryptKey);
  }
}
