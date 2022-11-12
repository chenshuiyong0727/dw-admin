package com.hf.op.rest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.alibaba.fastjson.JSON;
import com.hf.op.infrastructure.dto.department.GoodsInventoryDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryExportDto;
import com.hf.op.infrastructure.dto.department.GoodsInventoryRqDto;
import com.hf.op.service.GoodsInventoryServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 商品库存 控制器
 * @author chensy
 * @date 2022-11-12 20:10:34
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/goodsInventory")
public class  GoodsInventoryController {

  private GoodsInventoryServiceImpl service;

  public GoodsInventoryController(GoodsInventoryServiceImpl service){
    this.service = service;
  }

  /**
   * 新增数据
   * @param dto
   * @return
   */
	@PostMapping("")
  public ResponseMsg add(@RequestBody GoodsInventoryDto dto){
    return service.add(dto);
  }

  /**
   * 修改数据
   * @param dto
   * @return
   */
	@PutMapping("")
  public ResponseMsg update(@RequestBody GoodsInventoryDto dto){
    return service.update(dto);
  }

  /**
   * 修改状态
   * @param dto
   * @return
   */
	@PutMapping("/status")
  public ResponseMsg status(@RequestBody StatusDto dto){
    return service.status(dto);
  }

  /**
   * 获取数据列表
   * @param request
   * @return
   */
	@GetMapping("")
  public ResponseMsg page(HttpServletRequest request){
    GoodsInventoryRqDto dto = HfBeanUtil.populate(new GoodsInventoryRqDto(),request);
    return service.page(dto);
  }

  /**
   * 获取数据
   * @return
   */
	@GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value="id") Long id){
    return service.detail(id);
  }

  /**
   * 移除数据
   * @return
   */
	@DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value="id") Long id){
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
   * @return
   */
	@PostMapping("/export")
  public void export(HttpServletResponse response, HttpServletRequest request) {
    String res = HfBeanUtil.getJsonRequest(request);
    GoodsInventoryRqDto dto = JSON.parseObject(res, GoodsInventoryRqDto.class);
    List<GoodsInventoryExportDto> list = service.queryExportPage(dto);
    if (CollUtil.isNotEmpty(list)) {
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
        GoodsInventoryExportDto.class, list);
      try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
          "attachment;filename*=utf-8'zh_cn'" + URLEncoder
          .encode("商品库存列表_" + DateUtil.today() + ".xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
        log.error("export" , e);
      } finally {
        try {
          workbook.close();
          response.getOutputStream().close();
        } catch (IOException e) {
          e.printStackTrace();
          log.error("export" , e);
        }
      }
    }
  }

}
