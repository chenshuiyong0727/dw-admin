package com.hf.op.rest.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.code.GenTemplateDto;
import com.hf.op.service.impl.code.GenTemplateServiceImpl;
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
 * gen_template的实体类——控制器
 *
 * @author system
 * @date 2022-08-23
 */
@RestController
@RequestMapping(value = "v1/code/genTemplate")
public class GenTemplateController {


  private GenTemplateServiceImpl genTemplateServiceImpl;

  public GenTemplateController(GenTemplateServiceImpl genTemplateServiceImpl) {
    this.genTemplateServiceImpl = genTemplateServiceImpl;
  }

  /**
   * 获取数据
   */
  @GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value = "id") Long id) {
    return genTemplateServiceImpl.detail(id);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenTemplateDto dto = HfBeanUtil.populate(new GenTemplateDto(), request);
    return genTemplateServiceImpl.page(dto);
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody GenTemplateDto dto) {
    return genTemplateServiceImpl.add(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenTemplateDto dto) {
    return genTemplateServiceImpl.update(dto);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenTemplateDto dto) {
    return genTemplateServiceImpl.status(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return genTemplateServiceImpl.remove(id);
  }
}
