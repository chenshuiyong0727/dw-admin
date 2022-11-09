package com.hf.op.rest.code;


import com.alibaba.fastjson.JSON;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.code.GenLogsDto;
import com.hf.op.service.impl.code.GenLogsServiceImpl;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * gen_logs的实体类——控制器
 *
 * @author system
 * @date 2022-08-23
 */
@RestController
@RequestMapping(value = "v1/code/genLogs")
public class GenLogsController {


  private GenLogsServiceImpl service;

  public GenLogsController(GenLogsServiceImpl service) {
    this.service = service;
  }

  /**
   * 获取数据
   */
  @GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value = "id") Long id) {
    return service.detail(id);
  }

  /**
   * 获取数据
   */
  @GetMapping("/getByTableId/{tableId}")
  public ResponseMsg getByTableId(@PathVariable(value = "tableId") Long tableId) {
    return service.getByTableId(tableId);
  }

  @PostMapping("/create")
  public void export(HttpServletResponse response, HttpServletRequest request) {
    String res = HfBeanUtil.getJsonRequest(request);
    GenLogsDto dto = JSON.parseObject(res, GenLogsDto.class);
    service.create(dto, response);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenLogsDto dto = HfBeanUtil.populate(new GenLogsDto(), request);
    return service.page(dto);
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody GenLogsDto dto) {
    return new ResponseMsg().setData(new HashMap().put("id", service.add(dto).getId()));

  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenLogsDto dto) {
    return service.update(dto);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenLogsDto dto) {
    return service.status(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return service.remove(id);
  }
}
