package com.hf.op.infrastructure.generator.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.common.infrastructure.util.ListDistinctUtil;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.TypeConversionUtil;
import com.hf.common.infrastructure.validator.annotation.ValidatorType;
import com.hf.op.infrastructure.dto.code.gencode.GenBuilderModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import com.hf.op.infrastructure.dto.code.gencode.GenTableColumnModel;
import com.hf.op.infrastructure.generator.enums.DictType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/***
 * 代码生成器 处理工具类
 *
 * @author parker
 * @date 2020-11-18 13:21
 */
public final class GeneratorHandleUtil {

  /**
   * 后端 验证前缀
   */
  private static final String BACKEND_VALIDATE_TYPE_PREFIX = "ValidatorType.";
  /**
   * 验证方法前缀
   */
  private static final String VALIDATE_PREFIX = "validator_";

  private GeneratorHandleUtil() {
  }

  // =================

  /**
   * 处理代码生成器数据
   *
   * @param builderModel 数据
   * @param excludeFields 排除字段
   * @return GenBuilderModel
   */
  public static GenBuilderModel handleData(GenBuilderModel builderModel,
      List<String> excludeFields) {
    if (builderModel == null) {
      return null;
    }

    // 非法处理
    if (CollUtil.isEmpty(excludeFields)) {
      excludeFields = ListUtil.empty();
    }

    // 处理表信息
    GenTableAndColumnModel model = builderModel.getModel();
    // 数据库表名转驼峰
    model.setTableHumpName(
        FieldUtil.upperFirstLetter(
            FieldUtil.underlineToHump(
                model.getTableName()
            )
        )
    );
    // 表字段数据处理
    List<GenTableColumnModel> columnList = model.getColumnList();
    if (!CollUtil.isEmpty(columnList)) {
      List<GenTableColumnModel> queryList = Lists.newArrayList();
      for (GenTableColumnModel columnModel : columnList) {
        // 1. 数据库字段名转驼峰
        columnModel.setFieldHumpName(
            FieldUtil.underlineToHump(
                columnModel.getFieldName()
            )
        );

        // String 转list 验证集合
        List<String> validateTypeList = Convert.toList(String.class, columnModel.getValidateType());
        // 如果非空 则开启非空验证
        if (DictType.NO_YES_YES.getValue().equals(columnModel.getIzNotNull())) {
          validateTypeList.add(ValidatorType.IS_NOT_NULL.toString());
        }
        // 去空项
        validateTypeList.removeIf(StringUtils::isBlank);
        // 去重复
        validateTypeList = ListDistinctUtil.distinct(validateTypeList);

        // 2. 处理字段验证器
        if (!CollUtil.isEmpty(validateTypeList)) {
          List<String> typeList = Lists.newArrayListWithCapacity(validateTypeList.size());
          List<String> typeAndCommaList = Lists.newArrayListWithCapacity(validateTypeList.size());
          for (int i = 0; i < validateTypeList.size(); i++) {
            String validate = validateTypeList.get(i);
            String validateAndComma = validate;

            if (i < validateTypeList.size() - 1) {
              validateAndComma += ", ";
            }

            typeList.add(validate);
            typeAndCommaList.add(validateAndComma);
          }

          columnModel.setValidateTypeList(typeList);
          columnModel.setValidateTypeAndCommaList(typeAndCommaList);
        }

        // 3. 处理 Form 表单字段
        if (DictType.NO_YES_YES.getValue().equals(columnModel.getIzShowForm())) {
          if (CollUtil.isEmpty(model.getFormList())) {
            model.setFormList(Lists.newArrayList());
          }
          model.getFormList().add(columnModel);
        }

        // 4. 处理 Index 页面字段 - QueryList
        if (StringUtils.isNotBlank(columnModel.getQueryType()) &&
            DictType.NO_YES_YES.getValue().equals(columnModel.getIzShowList())
        ) {
          queryList.add(columnModel);
        }

      }

      // 5. 处理 Index 页面字段 - briefQueryList - moreQueryList
      if (!CollUtil.isEmpty(queryList)) {
        if (CollUtil.isEmpty(model.getMoreQueryList())) {
          model.setMoreQueryList(Lists.newArrayList());
        }

        for (int i = 0; i < queryList.size(); i++) {
          model.getMoreQueryList().add(
              queryList.get(i));
        }
      }
      List<String> pkgList = new ArrayList<>();
      for (GenTableColumnModel columnModel : columnList) {
        String fullPath = TypeConversionUtil.getJavaTypeFullPath(columnModel.getFieldType());
        if (StringUtilLocal.isNotEmpty(fullPath)) {
          pkgList.add(fullPath);
        }
      }
      model.setEntityPkgList(ListDistinctUtil.distinct(pkgList));
      // 遍历排除字段
      List<GenTableColumnModel> columnFrontList = ListBeanUtil
          .listCopy(model.getColumnList(), GenTableColumnModel.class);
      model.setColumnFrontList(columnFrontList);
      List<String> finalExcludeFields = excludeFields;
      columnList.removeIf(tmp -> finalExcludeFields.contains(tmp.getFieldName()));
    }

    return builderModel;
  }
}
