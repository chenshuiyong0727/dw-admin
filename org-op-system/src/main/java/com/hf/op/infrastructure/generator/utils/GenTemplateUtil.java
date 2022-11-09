package com.hf.op.infrastructure.generator.utils;

import static com.hf.op.infrastructure.generator.enums.OrderConstants.UTIL_ORDER;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.hf.op.infrastructure.dto.code.gencode.GenTemplateDetailModel;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 代码模板
 *
 * @author parker
 * @date 2020-09-22 11:17
 */
@Slf4j
@Order(UTIL_ORDER)
@Component
@Lazy(false)
public class GenTemplateUtil {

//  /**
//   * 增加初始状态开关 防止异常使用
//   */
//  private static boolean IS_INIT;
//  /**
//   * 代码模板明细 Service
//   */
//  private static GenTemplateDetailService genTemplateDetailService;

//  /**
//   * 根据模板ID 模板明细列表
//   *
//   * @param parentId 模板ID
//   * @return List
//   */
//  public static List<GenTemplateDetailModel> getTemplateDetailList(String parentId) {
//    // 判断 工具类是否初始化完成
//    ThrowExceptionUtil.isThrowException(!IS_INIT,
//        CoreMsg.OTHER_EXCEPTION_UTILS_INIT);
//
////        // 缓存Key
////        String cacheKey = CacheUtil.formatKey(CACHE_PREFIX_NAME + parentId);
////
////        Map<String, Object> templateCache = SecurityCache.hGetAll(redisTemplate, cacheKey, (k) -> {
////            // 处理数据库查询数据
////            List<GenTemplateDetailModel> listByParent = genTemplateDetailService.findListByParent(parentId);
////            Map<String, Object> templateMap = Maps.newHashMapWithExpectedSize(listByParent.size());
////            for (GenTemplateDetailModel genTemplateDetailModel : listByParent) {
////                templateMap.put(genTemplateDetailModel.getId(), genTemplateDetailModel);
////            }
////            return templateMap;
////        });
//
//    List<GenTemplateDetailModel> listByParent = genTemplateDetailService.findListByParent(parentId);
//    Map<String, Object> templateMap = Maps.newHashMapWithExpectedSize(listByParent.size());
//    for (GenTemplateDetailModel genTemplateDetailModel : listByParent) {
//      templateMap.put(genTemplateDetailModel.getId().toString(), genTemplateDetailModel);
//    }
//    // 处理集合数据
//    List<GenTemplateDetailModel> wrapperModels = handleDictList(templateMap);
//    return sortWrappers(wrapperModels);
//  }

  /**
   * 模板排序
   *
   * @param wrapperModels 字典Model
   * @return List
   */
  private static List<GenTemplateDetailModel> sortWrappers(
      List<GenTemplateDetailModel> wrapperModels) {
    // 非法判读
    if (CollUtil.isEmpty(wrapperModels)) {
      return ListUtil.empty();
    }

    return ListUtil.sort(wrapperModels,
        (o1, o2) -> CompareUtil.compare(o1.getFileName(), o2.getFileName()));
  }

  /***
   * 处理返回模板明细集合
   * @param tempMap Map
   * @return List
   */
  public static List<GenTemplateDetailModel> handleDictList(Map<String, Object> tempMap) {
    List<GenTemplateDetailModel> wrapperModels = Lists.newArrayList();
    if (CollUtil.isNotEmpty(tempMap)) {
      for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
        // 赋值
        Object data = entry.getValue();
        GenTemplateDetailModel templateDetailModel = Convert
            .convert(GenTemplateDetailModel.class, data);
        wrapperModels.add(templateDetailModel);
      }
    }

    // 返回排序后 list
    return CollUtil.isNotEmpty(wrapperModels) ? sortWrappers(wrapperModels) : wrapperModels;
  }

  // ===================================
//
//  /**
//   * 初始化
//   */
//  @Autowired
//  public void init(GenTemplateDetailService genTemplateDetailService) {
//    GenTemplateUtil.genTemplateDetailService = genTemplateDetailService;
//    IS_INIT = true;
//  }

}
