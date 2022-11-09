package com.hf.op.rest.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.code.GenTableColumnDto;
import com.hf.op.service.impl.code.GenTableColumnServiceImpl;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * gen_table_column的实体类——控制器
 *
 * @author system
 * @date 2022-08-23
 */
@RestController
@RequestMapping(value = "v1/code/genTableColumn")
public class GenTableColumnController {


  private GenTableColumnServiceImpl service;

  public GenTableColumnController(GenTableColumnServiceImpl service) {
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
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenTableColumnDto dto = HfBeanUtil.populate(new GenTableColumnDto(), request);
    return service.page(dto);
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody GenTableColumnDto dto) {
    return service.add(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenTableColumnDto dto) {
    return service.update(dto);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenTableColumnDto dto) {
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
