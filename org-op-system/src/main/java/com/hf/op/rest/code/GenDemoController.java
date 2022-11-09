package com.hf.op.rest.code;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.code.GenDemoDto;
import com.hf.op.infrastructure.dto.code.GenDemoExportDto;
import com.hf.op.infrastructure.dto.code.GenDemoRqDto;
import com.hf.op.service.impl.code.GenDemoServiceImpl;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 案例 控制器
 *
 * @author chensy
 * @date 2022-09-08 15:08:04
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/demo")
public class GenDemoController {

  private GenDemoServiceImpl service;

  public GenDemoController(GenDemoServiceImpl service) {
    this.service = service;
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody GenDemoDto dto) {
    return service.add(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenDemoDto dto) {
    return service.update(dto);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenDemoDto dto) {
    return service.status(dto);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenDemoRqDto dto = HfBeanUtil.populate(new GenDemoRqDto(), request);
    return service.page(dto);
  }

  /**
   * 获取数据
   */
  @GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value = "id") Long id) {
    return service.detail(id);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return service.remove(id);
  }

  /**
   * 批量删除
   */
  @PutMapping("/batch/remove")
  public ResponseMsg batchRemove(@RequestBody List<Long> ids) {
    return new ResponseMsg(service.batchRemove(ids));
  }

  /**
   * 导出数据
   */
  @PostMapping("/export")
  public void export(HttpServletResponse response, HttpServletRequest request) {
    String res = HfBeanUtil.getJsonRequest(request);
    GenDemoRqDto dto = JSON.parseObject(res, GenDemoRqDto.class);
    List<GenDemoExportDto> list = service.queryExportPage(dto);
    if (CollUtil.isNotEmpty(list)) {
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
          GenDemoExportDto.class, list);
      try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
            "attachment;filename*=utf-8'zh_cn'" + URLEncoder
                .encode("案例列表_" + DateUtil.today() + ".xlsx", "UTF-8"));
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
