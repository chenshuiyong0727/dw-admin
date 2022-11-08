package com.hf.common.infrastructure.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.hf.common.infrastructure.vo.HumpPageInfo;
import com.hf.common.infrastructure.vo.HumpPageVo;
import com.hf.common.infrastructure.vo.UnderlinePageInfo;
import com.hf.common.infrastructure.vo.UnderlinePageVo;
import org.springframework.util.CollectionUtils;

/**
 * @author zengshibin
 * @function 分页工具类
 * @date 2022/2/7
 */
public class PageUtil {

  /**
   * 获取驼峰分页结果
   */
  public static HumpPageVo getHumpPage(IPage page) {
    if (null == page || CollectionUtils.isEmpty(page.getRecords())) {
      HumpPageVo humpPageVo = new HumpPageVo();
      humpPageVo.setList(Lists.newArrayList());
      HumpPageInfo humpPageInfo = new HumpPageInfo();
      humpPageInfo.setTotalCount(0);
      humpPageInfo.setPageSize(0);
      humpPageInfo.setPageNum(0);
      humpPageVo.setPageInfo(humpPageInfo);
      return null;
    }
    HumpPageVo humpPageVo = new HumpPageVo();
    humpPageVo.setList(page.getRecords());
    HumpPageInfo humpPageInfo = new HumpPageInfo();
    humpPageInfo.setTotalCount(page.getTotal());
    humpPageInfo.setPageSize(page.getSize());
    humpPageInfo.setPageNum(page.getCurrent());
    humpPageVo.setPageInfo(humpPageInfo);
    return humpPageVo;
  }

  /**
   * 获取下划线分页结果
   */
  public static UnderlinePageVo getUnderlinePage(IPage page) {
    if (null == page || CollectionUtils.isEmpty(page.getRecords())) {
      return null;
    }
    UnderlinePageVo underlinePageVo = new UnderlinePageVo();
    underlinePageVo.setList(page.getRecords());
    UnderlinePageInfo underlinePageInfo = new UnderlinePageInfo();
    underlinePageInfo.setTotalCount(page.getTotal());
    underlinePageInfo.setPageSize(page.getSize());
    underlinePageInfo.setPageNum(page.getCurrent());
    underlinePageVo.setPageInfo(underlinePageInfo);
    return underlinePageVo;
  }
}
