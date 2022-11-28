package com.hf.op.rest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.hf.common.infrastructure.dto.StatusDto;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.department.GoodsOrderDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderExportDto;
import com.hf.op.infrastructure.dto.department.GoodsOrderRqDto;
import com.hf.op.service.impl.GoodsOrderServiceImpl;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品订单信息 控制器
 * @author chensy
 * @date 2022-11-15 17:39:00
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/goodsOrder")
public class  GoodsOrderController {

  private GoodsOrderServiceImpl service;

  public GoodsOrderController(GoodsOrderServiceImpl service){
    this.service = service;
  }

  /**
   * 出售
   * @param dto
   * @return
   */
	@PostMapping("/sellGoods")
  public ResponseMsg sellGoods(@RequestBody GoodsOrderDto dto){
    Assert.notNull(dto, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(dto.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    Assert.notNull(dto.getStatus(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    return service.sellGoods(dto);
  }

  /**
   * 新增数据
   * @param dto
   * @return
   */
	@PostMapping("")
  public ResponseMsg add(@RequestBody GoodsOrderDto dto){
    return service.add(dto);
  }

  /**
   * 修改数据
   * @param dto
   * @return
   */
	@PutMapping("")
  public ResponseMsg update(@RequestBody GoodsOrderDto dto){
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
    GoodsOrderRqDto dto = HfBeanUtil.populate(new GoodsOrderRqDto(),request);
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
    GoodsOrderRqDto dto = JSON.parseObject(res, GoodsOrderRqDto.class);
    List<GoodsOrderExportDto> list = service.queryExportPage(dto);
    if (CollUtil.isNotEmpty(list)) {
      Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),
        GoodsOrderExportDto.class, list);
      try {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
          "attachment;filename*=utf-8'zh_cn'" + URLEncoder
          .encode("商品订单信息列表_" + DateUtil.today() + ".xlsx", "UTF-8"));
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

  @GetMapping("/indexData")
  public ResponseMsg indexData(){
    return service.indexData();
  }

}
