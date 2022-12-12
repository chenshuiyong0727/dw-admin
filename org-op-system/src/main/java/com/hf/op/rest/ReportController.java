package com.hf.op.rest;

import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.service.impl.GoodsOrderServiceImpl;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表 控制器
 *
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/report")
public class ReportController {

  private GoodsOrderServiceImpl service;

  public ReportController(GoodsOrderServiceImpl service) {
    this.service = service;
  }

  /**
   * 入库报表列表
   */
  @GetMapping("/putInStorage")
  public ResponseMsg pageGoods(HttpServletRequest request) {
    GoodsOrderRqDto dto = HfBeanUtil.populate(new GoodsOrderRqDto(), request);
    return service.putInStorage(dto);
  }
}
