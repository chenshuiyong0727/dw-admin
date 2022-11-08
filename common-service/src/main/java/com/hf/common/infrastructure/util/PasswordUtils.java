package com.hf.common.infrastructure.util;

import java.util.UUID;


/**
 * @Author wpq
 * @Date 2021/05/12
 * @Description 密码工具类
 */
public class PasswordUtils {

  /**
   * 匹配密码
   *
   * @param salt 盐
   * @param rawPass 明文
   * @param encPass 密文
   * @return 是否匹配
   */
  public static boolean matches(String salt, String rawPass, String encPass) {
    return new PasswordEncoder(salt).matches(encPass, rawPass);
  }

  /**
   * 明文密码加密
   *
   * @param rawPass 明文
   * @param salt 盐
   * @reture 加密后
   */
  public static String encode(String rawPass, String salt) {
    return new PasswordEncoder(salt).encode(rawPass);
  }

  /**
   * 获取加密盐
   *
   * @return 盐值
   */
  public static String getSalt() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
  }

  public static int randomPwd() {
    return (int) ((Math.random() * 9 + 1) * 100000);
  }

  public static void main(String[] args) {
    int pwd = (int) ((Math.random() * 9 + 1) * 100000);
    String salt = PasswordUtils.getSalt();
    String encode = PasswordUtils.encode("hjjn@2021", salt);
    System.out.println(salt);
    System.out.println(encode);
    boolean i = PasswordUtils
        .matches("a8c998376dab4479bb45", "hjjn@2021", "5ad9f3d8a4dceb600055c48a8b24b346");
    System.out.println(i);
  }

}
