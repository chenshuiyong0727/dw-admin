package com.hf.common.infrastructure.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 * @author chensy
 * @function 列表复制工具类
 * @date 2022-04-11
 */
public final class ListBeanUtil {

  /**
   * Bean关联设置-列表
   */
  public static final String CONFIG_LIST = "CONFIG_LIST";

  /**
   * Bean关联设置-属性映射
   */
  public static final String CONFIG_PROPERTY_MAP = "CONFIG_PROPERTY_MAP";
  /**
   * 日志
   */
  static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ListBeanUtil.class);
  /**
   * 属性复制工具
   */
  private static final PropertyUtilsBean PROPERTY_UTILS = new PropertyUtilsBean();

  /**
   * 列表复制工具类,不能初始化实例
   */
  private ListBeanUtil() {
    // 列表复制工具类,不能初始化实例
  }

  /**
   * 列表复制（domain与dto之间的互相转换）
   *
   * @param <S> 泛型
   * @param <D> 泛型
   * @param srcList 源数据列表
   * @param destClass 目标数据类型
   * @param ignoreProperties 忽略不复制的属性
   * @return 目标数据列表
   */
  public static <S, D> List<D> listCopy(List<S> srcList, Class<D> destClass,
      String... ignoreProperties)
      throws RuntimeException {
    try {
      List<D> result = new ArrayList<D>();
      if (srcList != null && !srcList.isEmpty()) {
        for (S src : srcList) {
          D dest = destClass.newInstance();
          copyProperties(src, dest, ignoreProperties);
          result.add(dest);
        }
      }
      return result;
    } catch (Exception e) {
      String msg = "数据拷贝出现异常:" + e.getMessage();
      LOG.error(msg, e);
      throw new RuntimeException(msg, e);
    }
  }

  /**
   * @param source 源对象
   * @param target 目标对象
   * @param ignoreProperties 不拷贝的字段
   * @throws RuntimeException 异常
   * @description 对象拷贝，domin和dto有一对多，多对多关系也可自动转换
   */
  public static void copyProperties(Object source, Object target, String... ignoreProperties)
      throws RuntimeException {
    try {
      BeanUtils.copyProperties(source, target, ignoreProperties);
      Map<String, Field> srcListMap = checkSet(source.getClass());
      if (srcListMap == null || srcListMap.isEmpty()) {
        // 只有bean对象有Set类型的属性时，才需要继续进行set集合的拷贝
        return;
      }
      Map<String, Field> dstListMap = checkSet(target.getClass());
      if (dstListMap == null || dstListMap.isEmpty()) {
        // 只有目标对象有Set类型的属性时，才需要继续进行set集合的拷贝
        return;
      }
      List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
      for (Entry<String, Field> entry : srcListMap.entrySet()) {
        String filedName = entry.getKey();
        if (ignoreList == null || ignoreList.contains(filedName)) {
          // 忽略不拷贝的字段
          continue;
        }
        Object value = PROPERTY_UTILS.getProperty(source, filedName);
        // 空数组copy会报错，如果为空，忽略掉
        List<Object> list = new ArrayList<Object>();
        if (list.equals(value) && value instanceof ArrayList) {
          continue;
        }
        Set<?> items = (Set<?>) value;
        Class<?> actualClass = getActualType(dstListMap.get(filedName));
        Set<Object> destSet = copySet(items, actualClass, ignoreProperties);
        PROPERTY_UTILS.setProperty(target, filedName, destSet);
      }
    } catch (Exception e) {
      LOG.error("在做数据拷贝是出现异常！" + e.getMessage(), e);
      throw new RuntimeException("在做数据拷贝是出现异常！");
    }
  }

  /**
   * @param items Set类型属性
   * @param clazz 转换后的类型
   * @param ignoreProperties 忽略的属性
   * @return 转换后的类型集合
   * @throws IllegalAccessException 异常
   * @throws InstantiationException 异常
   * @throws RuntimeException 异常
   * @description 复制bean对象的Set类型属性的数据
   */
  private static Set<Object> copySet(Set<?> items, Class<?> clazz, String... ignoreProperties)
      throws InstantiationException, IllegalAccessException, RuntimeException {
    Set<Object> destSet = new HashSet<Object>();
    if (items != null && !items.isEmpty()) {
      // 集合不为空时，才进行copy操作
      for (Object item : items) {
        Object dest = clazz.newInstance();
        copyProperties(item, dest, ignoreProperties);
        destSet.add(dest);
      }
      return destSet;
    }
    return destSet;

  }

  /**
   * @param <K> 泛型
   * @param <V> 泛型
   * @param list DTO列表
   * @param keyName DTO的某个属性
   * @return 以某个属性为key，以DTO为value的Map集合
   * @throws RuntimeException 异常
   * @description 将一个DTO列表转换成以某个属性为key，以DTO为value的Map集合
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> toMap(List<V> list, String keyName) throws RuntimeException {
    try {
      Map<K, V> map = new HashMap<K, V>();
      if (list != null && !list.isEmpty()) {
        for (V item : list) {
          Object key = PROPERTY_UTILS.getProperty(item, keyName);
          map.put((K) key, item);
        }
      }
      return map;
    } catch (IllegalAccessException e) {
      LOG.error(e.getMessage(), e);
      throw new RuntimeException("List转Map发生异常：没有访问属性" + keyName + "的权限", e);
    } catch (InvocationTargetException e) {
      LOG.error(e.getMessage(), e);
      throw new RuntimeException("List转Map发生异常：" + e.getMessage(), e);
    } catch (NoSuchMethodException e) {
      LOG.error(e.getMessage(), e);
      throw new RuntimeException("List转Map发生异常：属性" + keyName + "没有可访问的公共方法", e);
    }
  }

  /**
   * @param clazz 类
   * @return 返回Set类型的属性集合
   * @description 检查对象是否有Set类型的属性
   */
  private static Map<String, Field> checkSet(Class<?> clazz) {
    Map<String, Field> typeMap = new HashMap<String, Field>();
    Field[] fields = clazz.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      if (field.getType().isAssignableFrom(Set.class)) {
        typeMap.put(field.getName(), field);
      }
    }
    return typeMap;
  }

  /**
   * @param field 参数
   * @return 返回类型
   * @throws ClassNotFoundException 异常
   * @description 获取参数返回的类型
   */
  private static Class<?> getActualType(Field field) throws ClassNotFoundException {
    Type genericType = field.getGenericType();
    if (genericType instanceof ParameterizedType) {
      Type[] types = ((ParameterizedType) genericType).getActualTypeArguments();
      if (types.length > 0) {
        return (Class<?>) types[0];
      }
    }
    return null;
  }

  /**
   * @param <K> 泛型
   * @param <T> 泛型
   * @param list 实体集合
   * @param ids id集合
   * @param column 字段名
   * @return map
   * @throws RuntimeException 业务异常
   * @description 对于1对多的情况，将id对应的集合转换到map中
   */
  public static <K, T> Map<K, List<T>> toMapList(List<T> list, List<K> ids, String column)
      throws RuntimeException {
    try {
      Map<K, List<T>> listMap = new HashMap<K, List<T>>();
      if (list != null && !list.isEmpty()) {
        for (K key : ids) {
          List<T> newList = new ArrayList<T>();
          for (T object : list) {
            if (PROPERTY_UTILS.getProperty(object, column).equals(key)) {
              newList.add(object);
            }
          }
          listMap.put(key, newList);
        }
      }
      return listMap;
    } catch (Exception e) {
      LOG.error("在做数据转换时出现异常！" + e.getMessage(), e);
      throw new RuntimeException("在做数据转换时出现异常！");
    }
  }

  /**
   * @param <S> 泛型
   * @param <T> 泛型
   * @param list 集合
   * @param keyName 字段名
   * @return 集合
   * @throws RuntimeException 业务异常
   * @description 将集合中的keyName字段组装成数组
   */
  @SuppressWarnings("unchecked")
  public static <S, T> List<S> toList(List<T> list, String keyName) throws RuntimeException {
    try {
      List<S> ids = new ArrayList<S>();
      if (list != null && !list.isEmpty()) {
        for (T object : list) {
          if (!ids.contains(PROPERTY_UTILS.getProperty(object, keyName))) {
            ids.add((S) PROPERTY_UTILS.getProperty(object, keyName));
          }
        }
      }
      return ids;
    } catch (Exception e) {
      String msg = "在做数据转换时出现异常: " + e.getMessage();
      LOG.error(msg, e);
      throw new RuntimeException(msg);
    }
  }

  /**
   * @param dto1 对象1
   * @param dto2 对象2
   * @param ignoreProperties 忽略的属性
   * @return 不相同的内容
   * @throws RuntimeException 业务异常
   * @description 对象内容比对
   */
  public static String compare(Object dto1, Object dto2, String... ignoreProperties)
      throws RuntimeException {
    try {

      PropertyDescriptor[] propertyArray1 = PROPERTY_UTILS.getPropertyDescriptors(dto1);
      Map<String, Object> map1 = new HashMap<String, Object>();
      for (PropertyDescriptor property : propertyArray1) {
        String name = property.getName();
        map1.put(name, PROPERTY_UTILS.getProperty(dto1, name));
      }
      PropertyDescriptor[] propertyArray2 = PROPERTY_UTILS.getPropertyDescriptors(dto2);
      Map<String, Object> map2 = new HashMap<String, Object>();
      for (PropertyDescriptor property : propertyArray2) {
        String name = property.getName();
        map2.put(name, PROPERTY_UTILS.getProperty(dto2, name));
      }
      // 获取相同属性
      List<String> samePropertyArray = new ArrayList<String>();
      samePropertyArray.addAll(map1.keySet());
      samePropertyArray.retainAll(map2.keySet());
      StringBuffer buffer = new StringBuffer();
      List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
      for (String property : samePropertyArray) {
        if ("class".equals(property)) {
          continue;
        }
        if (ignoreList.contains(property)) {
          continue;
        }
        Object value1 = map1.get(property);
        Object value2 = map2.get(property);
        if (value1 == null) {
          if (value2 != null) {
            buffer.append(String.format("%s: %s 修改成  %s", property, value1, value2) + " || ");
          }
          continue;
        }
        // 处理金额为四位小数点，修改为两位小数点
        if (value1 != null && value1 instanceof BigDecimal) {
          BigDecimal value11 = (BigDecimal) value1;
          value1 = value11.setScale(2, BigDecimal.ROUND_HALF_UP);
          if (value2 != null) {
            BigDecimal value22 = (BigDecimal) value2;
            value2 = value22.setScale(2, BigDecimal.ROUND_HALF_UP);
          } else {
            value2 = BigDecimal.ZERO;
          }
        }
        if (value2 != null && value2 instanceof BigDecimal) {
          BigDecimal value11 = (BigDecimal) value1;
          value1 = value11.setScale(2, BigDecimal.ROUND_HALF_UP);
          BigDecimal value22 = (BigDecimal) value2;
          value2 = value22.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (!value1.equals(value2)) {
          buffer.append(String.format("%s: %s 修改成 %s", property, value1, value2) + " || ");
        }
      }
      return buffer.toString();
    } catch (Exception e) {
      String msg = "在做数据对比时出现异常: " + e.getMessage();
      LOG.error(msg, e);
      throw new RuntimeException(msg);
    }
  }

  /**
   * @param dto1 对象1
   * @param dto2 对象2
   * @param properties 比较的属性
   * @return 不相同的内容
   * @throws RuntimeException 业务异常
   * @description 对象内容比对
   */
  public static String compareProPerties(Object dto1, Object dto2, String... properties)
      throws RuntimeException {
    try {

      PropertyDescriptor[] propertyArray1 = PROPERTY_UTILS.getPropertyDescriptors(dto1);
      Map<String, Object> map1 = new HashMap<String, Object>();
      for (PropertyDescriptor property : propertyArray1) {
        String name = property.getName();
        map1.put(name, PROPERTY_UTILS.getProperty(dto1, name));
      }
      PropertyDescriptor[] propertyArray2 = PROPERTY_UTILS.getPropertyDescriptors(dto2);
      Map<String, Object> map2 = new HashMap<String, Object>();
      for (PropertyDescriptor property : propertyArray2) {
        String name = property.getName();
        map2.put(name, PROPERTY_UTILS.getProperty(dto2, name));
      }
      // 获取相同属性
      List<String> samePropertyArray = new ArrayList<String>();
      samePropertyArray.addAll(map1.keySet());
      samePropertyArray.retainAll(map2.keySet());
      StringBuffer buffer = new StringBuffer();
      List<String> propertiesList = (properties != null ? Arrays.asList(properties) : null);
      for (String property : samePropertyArray) {
        if ("class".equals(property)) {
          continue;
        }
        if (!propertiesList.contains(property)) {
          continue;
        }
        Object value1 = map1.get(property);
        Object value2 = map2.get(property);
        if (value1 == null) {
          if (value2 != null) {
            buffer.append(String.format("%s: %s 修改为 %s", property, value1, value2) + " || ");
          }
          continue;
        }
        // 处理金额为四位小数点，修改为两位小数点
        if (value1 != null && value1 instanceof BigDecimal) {
          BigDecimal value11 = (BigDecimal) value1;
          value1 = value11.setScale(2, BigDecimal.ROUND_HALF_UP);
          if (value2 != null) {
            BigDecimal value22 = (BigDecimal) value2;
            value2 = value22.setScale(2, BigDecimal.ROUND_HALF_UP);
          } else {
            value2 = BigDecimal.ZERO;
          }
        }
        if (value2 != null && value2 instanceof BigDecimal) {
          BigDecimal value11 = (BigDecimal) value1;
          value1 = value11.setScale(2, BigDecimal.ROUND_HALF_UP);
          BigDecimal value22 = (BigDecimal) value2;
          value2 = value22.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (!value1.equals(value2)) {
          buffer.append(String.format("%s: %s 修改为 %s", property, value1, value2) + " || ");
        }
      }
      return buffer.toString();
    } catch (Exception e) {
      String msg = "在做数据对比时出现异常: " + e.getMessage();
      LOG.error(msg, e);
      throw new RuntimeException(msg);
    }
  }

  /**
   * @param sessionId sessionId
   * @param dtoList 集合
   * @param columnNames 导出的字段数组
   * @return 集合
   * @throws RuntimeException 业务异常
   * @throws NoSuchMethodException NoSuchMethodException
   * @throws InvocationTargetException InvocationTargetException
   * @throws IllegalAccessException IllegalAccessException
   * @description 获取要导出的行数据（此方法只适用只导出本表数据的报表）
   */
  public static <T> List<Map<String, Object>> getExcelValueMap(String sessionId,
      String[] columnNames,
      List<T> dtoList) throws RuntimeException {
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    if (dtoList == null || dtoList.isEmpty()) {
      return dataList;
    }
    try {
      for (T domin : dtoList) {
        Map<String, Object> expMap = new LinkedHashMap<String, Object>();
        for (int i = 0; i < columnNames.length; i++) {
          Object value = PROPERTY_UTILS.getProperty(domin, columnNames[i]);
          expMap.put(columnNames[i], value);
        }
        dataList.add(expMap);
      }
      return dataList;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
