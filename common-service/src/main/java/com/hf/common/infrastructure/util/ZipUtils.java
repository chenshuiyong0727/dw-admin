package com.hf.common.infrastructure.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件流转压缩包工具类
 *
 * @author Parker
 * @date 2020-01-07
 */
@Slf4j
public class ZipUtils {

  public static final String FILE_PATH = "path";
  public static final String FILE_NAME = "name";
  public static final String FILE_DATA = "data";
  /**
   * 缓存区大小
   */
  private static final int BUFFER_SIZE = 2 * 1024;

  /**
   * 压缩核心方法
   */
  private static void compress(
      ZipOutputStream zos, String path, String name, String data) throws Exception {
    byte[] buf = new byte[BUFFER_SIZE];
    zos.putNextEntry(new ZipEntry(path + name));
    int len;
    ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    while ((len = in.read(buf)) != -1) {
      zos.write(buf, 0, len);
    }
    zos.closeEntry();
    in.close();
  }


  /**
   * 文本直接转zip压缩成文件
   *
   * @param list -> map -> path 路径; name 文件名; data 具体文本内容;
   * @param out 传入输出流
   * @throws RuntimeException 抛出异常
   */
  public static void toZip(
      List<Map<String, String>> list, OutputStream out) throws RuntimeException {
    ZipOutputStream zos = null;
    try {
      zos = new ZipOutputStream(out, StandardCharsets.UTF_8);
      for (Map<String, String> map : list) {
        String path = map.get("path");
        String name = map.get("name");
        String data = map.get("data");
        compress(zos, path, name, data);
      }
    } catch (Exception e) {
      throw new RuntimeException("zip error from ZipUtils", e);
    } finally {
      if (zos != null) {
        try {
          zos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    List<Map<String, String>> list = new ArrayList<>();
    OutputStream outputStream = new FileOutputStream("/Users/system/Documents/脚本/opsli/test.zip");
    Map<String, String> m1 = new HashMap<String, String>(10) {{
      put("path", "/f1/f2/f3/");
      put("name", "1.txt");
      put("data", "abcdefg");
    }};
    Map<String, String> m2 = new HashMap<String, String>(10) {{
      put("path", "/f1/f2/f3/f4/");
      put("name", "2.txt");
      put("data", "abcdefg");
    }};
    Map<String, String> m3 = new HashMap<String, String>(10) {{
      put("path", "");
      put("name", "3.txt");
      put("data", "abcdefg");
    }};

    list.add(m1);
    list.add(m2);
    list.add(m3);
    toZip(list, outputStream);
    outputStream.close();
  }
}