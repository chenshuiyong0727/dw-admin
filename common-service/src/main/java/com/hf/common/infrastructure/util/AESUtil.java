package com.hf.common.infrastructure.util;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;


/**
 * AES对称加解密
 */
@Slf4j
public class AESUtil {

  /**
   * 加密
   *
   * @param content 需要加密的内容
   */
  public static String encryptWithKey(String content, String strKey) {
    try {
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      int blockSize = cipher.getBlockSize();
      byte[] dataBytes = content.getBytes(StandardCharsets.UTF_8);
      int plaintextLength = dataBytes.length;
      if (plaintextLength % blockSize != 0) {
        plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
      }
      byte[] plaintext = new byte[plaintextLength];
      System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

      SecretKeySpec keyspec = new SecretKeySpec(strKey.getBytes(), "AES");
      String iv = strKey;
      IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

      cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
      byte[] encrypted = cipher.doFinal(plaintext);
      Base64.Encoder encoder = Base64.getMimeEncoder();
      return encoder.encodeToString(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String desEncryptWithKey(String data, String key) {
    try {
      String iv = key;
      Base64.Decoder decoder = Base64.getMimeDecoder();
      byte[] encrypted1 = decoder.decode(data);
//      byte[] encrypted1 = decoder.decode(data.getBytes(StandardCharsets.UTF_8)) ;
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
      IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
      byte[] original = cipher.doFinal(encrypted1);
      String originalString = new String(original, "UTF-8");
      return originalString.trim();
    } catch (Exception e) {
      log.error("exception:", e);
      return null;
    }
  }

  public static void main(String[] args) {
    strTest();
  }

  public static void strTest() {
    JSONObject dataJson = new JSONObject();
    //dataJson.put("name","hjjn@2021");
    String pwd = "hjjn@2021对对对";
    System.out.println("====" + System.currentTimeMillis());
    String key = "6183ead0f54645e1";
    String val = encryptWithKey(pwd, key);
    System.out.println(val);
    String result = desEncryptWithKey(val, key).trim();
    System.out.println("desEncryptWithKey:" + desEncryptWithKey(val, key).trim());
  }
}
