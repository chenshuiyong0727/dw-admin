package com.hf.common.infrastructure.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5的算法在RFC1321 中定义 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： MD5 ("") =
 * d41d8cd98f00b204e9800998ecf8427e MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") =
 * 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 MD5
 * ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 *
 *
 * 传入参数：一个字节数组 传出参数：字节数组的 MD5 结果字符串
 */
public class MD5Utils {

  private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

  private static final String ENCRYPT_ALGORITHM_MD5 = "MD5"; //MD5加密
  private static final String ENCRYPT_ALGORITHM_SHA1 = "SHA-1"; //SHA1加密

  /**
   * @description MD5加密
   */
  public static String getMD5(String source) {
    return encrypt(ENCRYPT_ALGORITHM_MD5, source.getBytes());
  }

  /**
   * @description 指定字符集md5加密
   */
  public static String getMD5(String source, String charset) {
    try {
      return encrypt(ENCRYPT_ALGORITHM_MD5, source.getBytes(charset));
    } catch (UnsupportedEncodingException e) {
      logger.error("指定编码加密出错！" + e.getMessage(), e);
    }

    return null;
  }

  /**
   * @description SHA1加密
   */
  public static String getSHA1(String source) {
    return encrypt(ENCRYPT_ALGORITHM_SHA1, source.getBytes());
  }

  /**
   * @description 取得md5 16位（网络上常用的做法是取32位md5加密串中间16位）
   */
  public static String getMD5Hash(String code) {
    return MD5Utils.getMD5(code).substring(8, 24);
  }

  /**
   * @param len 位数
   * @description 取得md5 指定位数HASH串
   */
  public static String getMD5Hash(String code, int len) {
    return MD5Utils.getMD5(code).substring(8, 8 + len);
  }

  /**
   * @description 根据不同算法加密
   */
  public static String encrypt(String algorithm, byte[] source) {
    String s = null;
    char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      MessageDigest md = MessageDigest
          .getInstance(algorithm);
      md.update(source);
      byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
      // 用字节表示就是 16 个字节
      char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
      // 所以表示成 16 进制需要 32 个字符
      int k = 0; // 表示转换结果中对应的字符位置
      for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
        // 转换成 16 进制字符的转换
        byte byte0 = tmp[i]; // 取第 i 个字节
        str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
        // >>> 为逻辑右移，将符号位一起右移
        str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
      }
      s = new String(str); // 换后的结果转换为字符串

    } catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  }

  /**
   * 转16进制
   */
  public static String toHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      String s = Integer.toHexString(0xFF & b);
      if (s.length() < 2) {
        sb.append("0");
      }
      sb.append(s);
    }
    return sb.toString();
  }

  /**
   * 16进制MD5
   */
  public static String hexMD5(byte[] data) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");

      md5.reset();
      md5.update(data);
      byte[] digest = md5.digest();

      return toHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error - this implementation of Java doesn't support MD5.");
    }
  }

  /**
   * 16进制MD5
   */
  public static String hexMD5(ByteBuffer buf, int offset, int len) {
    byte[] b = new byte[len];
    for (int i = 0; i < len; i++) {
      b[i] = buf.get(offset + i);
    }
    return hexMD5(b);
  }

  public static void main(String[] args) {
    /**
     JSONObject dataJson = new JSONObject();
     dataJson.put("name","test");
     System.out.println("===="+dataJson);
     String str = "123456";
     String salt = "test";
     **/
    String pwd = "hjjn@2021";
    String salt = "hjjnPwd";
    String pwdre = pwd + salt;
    System.out.println(MD5Utils.getMD5(pwdre, "UTF-8"));
  }

}
