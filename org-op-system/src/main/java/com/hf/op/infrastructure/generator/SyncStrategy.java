package com.hf.op.infrastructure.generator;

import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import com.hf.op.infrastructure.generator.enums.DataBaseType;

/**
 * 同步策略
 *
 * @author parker
 * @date 2020-11-18 11:47
 */
public interface SyncStrategy {

  /**
   * 获得分类
   *
   * @return DataBaseType
   */
  DataBaseType getType();

  /**
   * 执行 同步操作
   *
   * @param model 模型
   */
  void execute(GenTableAndColumnModel model);

}
