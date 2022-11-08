package com.hf.common.infrastructure.util;


import com.hf.common.infrastructure.vo.UnderlinePageInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author 曾仕斌
 * @function 本地字符串工具类
 * @date 2021-01-15
 */
public class StringUtilLocal extends StringUtils {

  private final static char SEPARATOR = '_';

  /**
   * 开头部分与其中一个字符串匹配
   *
   * @param str 多个字符串用逗号分隔
   */
  public static boolean isStartWithOneStr(String startStr, String str) {
    if (StringUtilLocal.isEmpty(startStr) || StringUtilLocal.isEmpty(str)) {
      return false;
    }
    for (String s : str.split(",")) {
      if (startStr.startsWith(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断字符串是否为空
   */
  public static boolean isNotEmpty(String str) {
    return !StringUtilLocal.isEmpty(str);
  }

  /**
   * 转小写
   */
  public static String lowerCase(String str) {
    return str == null ? null : str.toLowerCase();
  }

  /**
   * 转大写
   */
  public static String upperCase(String str, Locale locale) {
    return str == null ? null : str.toUpperCase(locale);
  }

  /**
   * 驼峰命名法工具,首字母小写
   *
   * @return toCamelCase(" hello_world ") == "helloWorld"
   */
  public static String toCamelCase(String s) {
    if (s == null) {
      return null;
    }
    s = s.toLowerCase();
    StringBuilder sb = new StringBuilder(s.length());
    boolean upperCase = false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == SEPARATOR) {
        upperCase = true;
      } else if (upperCase) {
        sb.append(Character.toUpperCase(c));
        upperCase = false;
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 驼峰命名法工具,首字母小写
   *
   * @return underlineToCamel(" hello_world ") == "helloWorld"
   */
  public static String underlineToCamel(String param) {
    if (StringUtilLocal.isEmpty(param)) {
      return "";
    } else {
      String temp = param.toLowerCase();
      int len = temp.length();
      StringBuilder sb = new StringBuilder(len);

      for (int i = 0; i < len; ++i) {
        char c = temp.charAt(i);
        if (c == SEPARATOR) {
          ++i;
          if (i < len) {
            sb.append(Character.toUpperCase(temp.charAt(i)));
          }
        } else {
          sb.append(c);
        }
      }

      return sb.toString();
    }
  }

  /**
   * 驼峰命名法工具,首字母大写
   *
   * @return toCapitalizeCamelCase(" hello_world ") == "HelloWorld"
   */
  public static String toCapitalizeCamelCase(String s) {
    if (s == null) {
      return null;
    }
    s = toCamelCase(s);
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }


  public static Boolean isNum(String str) {
    for (int i = str.length(); --i >= 0; ) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }

    List list = new ArrayList();
    String mobiles = "15860770501,15860770502,15860770503,15860770504,15860770505,15860770506,15860770507,15860770508,15860770509,15860770510,15860770511,15860770512,15860770513,15860770514,15860770515,15860770516,15860770517,15860770518,15860770519,15860770520,15860770521,15860770522,15860770523,15860770524,15860770525,15860770526,15860770527,15860770528,15860770529,15860770530,15860770531,15860770532,15860770533,15860770534,15860770535,15860770536,15860770537,15860770538,15860770539,15860770540,15860770541,15860770542,15860770543,15860770544,15860770545,15860770546,15860770547,15860770548,15860770549,15860770550";
    List result = Arrays.asList(mobiles.split(","));
    // for (String mobile : result) {
    List personnel_relations = new ArrayList();
    Map dataMap0 = new HashMap();
    dataMap0.put("id", "");
    dataMap0.put("local_no", UUID.randomUUID().toString());
    dataMap0.put("real_name", UUID.randomUUID().toString().substring(0, 3));
    dataMap0.put("data_status", "1");
    dataMap0.put("relationship_type", "3");
    dataMap0.put("contact_number", 1);
    personnel_relations.add(dataMap0);
    Map dataMapUser1 = new HashMap();
    dataMapUser1.put("personnel_relations", personnel_relations);
    dataMapUser1.put("user_id", "");
    dataMapUser1.put("user_local_no", UUID.randomUUID().toString());
    dataMapUser1.put("project_id", "1083357605298049024");
    dataMapUser1.put("user_real_name", UUID.randomUUID().toString().substring(0, 3));
    dataMapUser1.put("user_mobile", 1);
    dataMapUser1.put("id_card", "350212199205295032");
    dataMapUser1.put("user_account", UUID.randomUUID().toString().substring(0, 10));
    dataMapUser1.put("user_password", UUID.randomUUID().toString().substring(0, 10));
    dataMapUser1.put("gender", "1");
    dataMapUser1.put("birth_date", "1992-02-03");
    dataMapUser1.put("education", "1");
    dataMapUser1.put("address", UUID.randomUUID().toString().substring(0, 3));
    dataMapUser1.put("address_code", "350203");
    dataMapUser1.put("nation", "01");
    dataMapUser1.put("occupation", "101");
    dataMapUser1.put("marital_status", "1");
    dataMapUser1.put("real_name_auth", "1");
    dataMapUser1.put("report_location_no", "10086333");
    dataMapUser1.put("report_location_name", UUID.randomUUID().toString().substring(0, 3));
    dataMapUser1.put("wechat_number", UUID.randomUUID().toString().substring(0, 3));
    dataMapUser1.put("data_status", "1");
    list.add(dataMapUser1);
    //  }
    Map dataMapList = new HashMap();
    dataMapList.put("list", list);
    dataMapList.put("project_id", "1083357605298049024");

    return true;
  }

  @SneakyThrows
  public static void main(String[] args) {
  /*  StopWatch sw = new StopWatch("test");
    sw.start("task1");
    // do something
    Thread.sleep(100);
    sw.stop();
    sw.start("task2");
    // do something
    Thread.sleep(200);
    sw.stop();
    System.out.println("sw.prettyPrint()~~~~~~~~~~~~~~~~~");
    System.out.println(sw.prettyPrint());
    System.out.println(sw.getTotalTimeSeconds());*/
    List<UnderlinePageInfo> list = new ArrayList();
    UnderlinePageInfo underlinePageInfo1 = new UnderlinePageInfo();
    underlinePageInfo1.setPageNum(1);
    UnderlinePageInfo underlinePageInfo2 = new UnderlinePageInfo();
    underlinePageInfo1.setPageNum(1);
    list.add(underlinePageInfo1);
    list.add(underlinePageInfo2);
    System.out.println(CollectionUtils.hasUniqueObject(list));

  }
}
