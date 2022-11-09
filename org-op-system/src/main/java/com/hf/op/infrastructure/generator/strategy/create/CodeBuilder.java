package com.hf.op.infrastructure.generator.strategy.create;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hf.common.infrastructure.util.MessUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.ZipUtils;
import com.hf.op.infrastructure.dto.code.gencode.GenBuilderModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTemplateDetailModel;
import com.hf.op.infrastructure.generator.enums.CodeType;
import com.hf.op.infrastructure.generator.utils.EnjoyUtil;
import com.hf.op.infrastructure.generator.utils.GeneratorHandleUtil;
import com.jfinal.kit.Kv;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Java代码构建器
 *
 * @author parker
 * @date 2020-09-13 19:36
 */
@Slf4j
public enum CodeBuilder {

  /**
   * 实例对象
   */
  INSTANCE;

  /**
   * Java文件后缀
   */
  public static final String JAVA_SUFFIX = "java";
  /**
   * 文件名前缀
   */
  public static final String FILE_NAME = "OPSLI-CodeCreate";
  /**
   * 基础路径前缀
   */
  public static final String BASE_PATH = "/代码生成-";
  /**
   * API 地址
   */
  public static final String API_PATH;
  /**
   * 文件夹前缀
   */
  private static final String FOLDER_PREFIX = "/";
  /**
   * 文件夹点前缀
   */
  private static final String POINT_PREFIX = ".";

  static {
    API_PATH = ApiFlag.class.getPackage().getName();
  }

  public static void main(String[] args) {

    String aaa = "aaaaaaaaab${bb}bbbbbbbb${bbaa}bbbbbbbccccccccccccc";
    List<String> placeholderList = MessUtil.getPlaceholderList(aaa);
    for (String s : placeholderList) {
      System.out.println(s);
    }
  }

  public static List<String> getExList() {
    List<String> exFields = new ArrayList<>();
    exFields.add("id");
    exFields.add("data_status");
    exFields.add("create_time");
    exFields.add("create_user_id");
    exFields.add("create_user_name");
    exFields.add("update_time");
    exFields.add("update_user_id");
    exFields.add("update_user_name");
    return exFields;
  }

  /**
   * 构建
   */
  public void build(GenBuilderModel builderModel, HttpServletResponse response,
      List<GenTemplateDetailModel> templateDetailList) {
    if (builderModel == null) {
      return;
    }
    String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
    List<String> exFields = getExList();
    // 处理表数据
    GenBuilderModel genBuilderModel = GeneratorHandleUtil
        .handleData(builderModel, exFields);
    if (genBuilderModel == null) {
      return;
    }

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    String tableName = builderModel.getModel().getTableName();
    OutputStream out = this.getOutputStream(tableName, response, dateStr);
    try {
      if (out != null) {

        List<Map<String, String>> fileList =
            Lists.newArrayListWithCapacity(templateDetailList.size());

        // 循环处理代码模板
        for (GenTemplateDetailModel templateDetailModel : templateDetailList) {
          fileList.add(
              this.createCode(tableName, genBuilderModel, templateDetailModel, dateStr)
          );
        }

        // 生成zip文件
        ZipUtils.toZip(fileList, byteArrayOutputStream);

        // 输出流文件
        IoUtil.write(out, true, byteArrayOutputStream.toByteArray());

      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 生成MapperXML
   *
   * @param builderModel Build 模型
   * @param templateModel 模板模型
   * @param dataStr 数据字符串
   * @return Map
   */
  private Map<String, String> createCode(String tableName, GenBuilderModel builderModel,
      GenTemplateDetailModel templateModel, String dataStr) {
    if (builderModel == null) {
      return MapUtil.empty();
    }

    // 获得分类名称
    CodeType codeType = CodeType.getCodeType(templateModel.getType());
    String typeName = codeType.getDesc();

    // 基础路径
    String basePath = CodeBuilder.BASE_PATH + tableName + '-' + dataStr + FOLDER_PREFIX + typeName;
    // Path路径
    String path = templateModel.getPath();
    if (StrUtil.isNotEmpty(path)) {
      // 替换占位符
      path = handleReplace(path, builderModel);
      // 处理路径 前后加 [/]
      path = this.handlePath(path);
    }

    if (!StringUtilLocal.isEmpty(templateModel.getFileName())
        && (templateModel.getFileName().contains("Entity.java")
        || templateModel.getFileName().contains("Dto.java")
        || templateModel.getFileName().contains("PageVo.java"))
    ) {
      Random random = new Random();
      String res = "";
      for (int i = 0; i < 18; i++) {
        int number = random.nextInt(10);
        if (i == 0 && number == 0) {
          number = 1;
        }
        res = res + number;
      }
      builderModel.setSerialId(res + "L");
    }
    // 代码
    Kv kv = this.createKv(builderModel);
    String codeStr = EnjoyUtil.renderByStr(templateModel.getFileContent(), kv);
    String fileName = handleReplace(templateModel.getFileName(), builderModel);

    Map<String, String> entityMap = Maps.newHashMapWithExpectedSize(3);
    entityMap.put(ZipUtils.FILE_PATH, basePath + path);
    entityMap.put(ZipUtils.FILE_NAME, fileName);
    entityMap.put(ZipUtils.FILE_DATA, codeStr);

    return entityMap;
  }

  /**
   * 创建 Kv
   *
   * @param builderModel Build 模型
   * @return Kv
   */
  private Kv createKv(GenBuilderModel builderModel) {
    return Kv.by("data", builderModel)
        .set("currTime", DateUtil.now())
        .set("apiPath", API_PATH);
  }

  /**
   * 处理 Path 路径
   *
   * @param path 路径
   * @return String
   */
  private String handlePath(String path) {
    if (StrUtil.isEmpty(path)) {
      return path;
    }

    // . 转换为 [/]
    path = StrUtil.replace(path, POINT_PREFIX, FOLDER_PREFIX);

    // 如果 第一位不是 / 则加 /
    path = StrUtil.prependIfMissing(path, FOLDER_PREFIX);

    // 如果最后一位 是 / 则减 /
    path = StrUtil.appendIfMissing(path, FOLDER_PREFIX);

    // 去除 [//]
    return StrUtil.replace(path, "//", FOLDER_PREFIX);
  }

  /**
   * 处理替换占位符
   *
   * @param str 原字符串
   * @param builderModel 模型数据
   * @return String
   */
  private String handleReplace(String str, GenBuilderModel builderModel) {
    // 非法处理
    if (StrUtil.isEmpty(str) || builderModel == null) {
      return str;
    }

    String prefix = "${";
    String suffix = "}";
    List<String> placeholderList = MessUtil.getPlaceholderList(str);
    for (String placeholderField : placeholderList) {
      Object property = BeanUtil.getProperty(builderModel, placeholderField);
      str = StrUtil.replace(str,
          prefix + placeholderField + suffix,
          Convert.toStr(property));
    }
    return str;
  }

  /**
   * 导出文件时为Writer生成OutputStream
   */
  private OutputStream getOutputStream(String tableName, HttpServletResponse response,
      String dataStr) {
    //创建本地文件
    try {
      String fileName = tableName + "-" + dataStr + ".zip";
      response.setHeader("Cache-Control", "no-store, no-cache");
      response.setHeader("Content-Disposition",
          "attachment;filename*=utf-8'zh_cn'" + URLEncoder
              .encode(fileName, "UTF-8"));
      return response.getOutputStream();
    } catch (IOException ignored) {
    }
    return null;
  }

}
