package com.hf.common.infrastructure.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;


/**
 * @author 曾仕斌
 * @function BEAN工具
 * @date 2021/10/6
 */
public class HfBeanUtil {

  /**
   * 从request拷贝属性到具体的BEAN中
   */
  public static <T> T populate(T bean, HttpServletRequest request) {
    Map<String, String[]> maps = request.getParameterMap();
    HashMap tempMap = Maps.newHashMap(maps);
    for (Iterator<Entry<String, String[]>> it = tempMap.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry<String, String[]> item = it.next();
      String[] value = item.getValue();
      boolean empty = ArrayUtil.isAllEmpty(value);
      if (empty) {
        it.remove();
      }
    }
    try {
      //处理时间格式
      DateConverter dateConverter = new DateConverter();

      //设置日期格式
      dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
      //注册格式
      ConvertUtils.register(dateConverter, Date.class);
      //注册格式
      ConvertUtils.register(new Converter() {
        @Override
        public Object convert(Class aClass, Object o) {
          return DateUtil.parse(String.valueOf(o)).toLocalDateTime();
        }
      }, LocalDateTime.class);
      BeanUtils.populate(bean, tempMap);
    } catch (Exception e) {
      return null;
    }
    return bean;
  }
}
