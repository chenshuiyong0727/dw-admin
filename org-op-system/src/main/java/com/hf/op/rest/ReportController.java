package com.hf.op.rest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.department.GoodsOrderInExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderOutExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.service.impl.GoodsOrderServiceImpl;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
   * 销售报表列表
   */
  @GetMapping("/sellList")
  public ResponseMsg sellList(HttpServletRequest request) {
    GoodsOrderRqDto dto = HfBeanUtil.populate(new GoodsOrderRqDto(), request);
    return service.sellList(dto);
  }

  /**
   * 入库报表列表
   */
  @GetMapping("/putInStorage")
  public ResponseMsg pageGoods(HttpServletRequest request) {
    GoodsOrderRqDto dto = HfBeanUtil.populate(new GoodsOrderRqDto(), request);
    return service.putInStorage(dto);
  }


  /**
   * 导出入库数据
   */
  @PostMapping("/exportPutInStorage")
  public void exportPutInStorage(HttpServletResponse response, HttpServletRequest request) {
    String res = HfBeanUtil.getJsonRequest(request);
    GoodsOrderRqDto dto = JSON.parseObject(res, GoodsOrderRqDto.class);
    List<GoodsOrderInExportDto> list = service.exportPutInStorage(dto);
    if (CollUtil.isNotEmpty(list)) {
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
          GoodsOrderInExportDto.class, list);
      try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
            "attachment;filename*=utf-8'zh_cn'" + URLEncoder
                .encode("入库列表_" + DateUtil.today() + ".xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
        log.error("export", e);
      } finally {
        try {
          workbook.close();
          response.getOutputStream().close();
        } catch (IOException e) {
          e.printStackTrace();
          log.error("export", e);
        }
      }
    }
  }

  @PostMapping("/exportPutOutStorage")
  public void exportPutOutStorage(HttpServletResponse response, HttpServletRequest request) {
    String res = HfBeanUtil.getJsonRequest(request);
    GoodsOrderRqDto dto = JSON.parseObject(res, GoodsOrderRqDto.class);
    List<GoodsOrderOutExportDto> list = service.exportPutOutStorage(dto);
    if (CollUtil.isNotEmpty(list)) {
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
          GoodsOrderOutExportDto.class, list);
      try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
            "attachment;filename*=utf-8'zh_cn'" + URLEncoder
                .encode("销售列表_" + DateUtil.today() + ".xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
        log.error("export", e);
      } finally {
        try {
          workbook.close();
          response.getOutputStream().close();
        } catch (IOException e) {
          e.printStackTrace();
          log.error("export", e);
        }
      }
    }
  }
}
