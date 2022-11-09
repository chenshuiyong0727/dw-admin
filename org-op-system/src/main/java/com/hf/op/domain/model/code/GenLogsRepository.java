package com.hf.op.domain.model.code;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hf.op.infrastructure.dto.code.GenLogsDto;
import com.hf.op.infrastructure.vo.code.GenLogsPageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 数据接口
 *
 * @author system
 * @date 2022-08-23
 */
@Repository
public interface GenLogsRepository extends BaseMapper<GenLogsEntity> {

  /**
   * @description 查询标签列表
   * @method page
   * @date: 2022-08-23
   */
  IPage<GenLogsPageVo> page(Page page, @Param("dto") GenLogsDto dto);

}
