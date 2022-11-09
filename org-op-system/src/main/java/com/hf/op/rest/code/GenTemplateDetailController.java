package com.hf.op.rest.code;


import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.op.infrastructure.dto.code.GenTemplateDetailDto;
import com.hf.op.service.impl.code.GenTemplateDetailServiceImpl;
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
 * gen_template_detail的实体类——控制器
 *
 * @author system
 * @date 2022-08-23
 */
@RestController
@RequestMapping(value = "v1/code/genTemplateDetail")
public class GenTemplateDetailController {


  private GenTemplateDetailServiceImpl genTemplateDetailServiceImpl;

  public GenTemplateDetailController(GenTemplateDetailServiceImpl genTemplateDetailServiceImpl) {
    this.genTemplateDetailServiceImpl = genTemplateDetailServiceImpl;
  }

  /**
   * 获取数据
   */
  @GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value = "id") Long id) {
    return genTemplateDetailServiceImpl.detail(id);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenTemplateDetailDto dto = HfBeanUtil.populate(new GenTemplateDetailDto(), request);
    return genTemplateDetailServiceImpl.page(dto);
  }

  /**
   * 新增数据
   */
  @PostMapping("")
  public ResponseMsg add(@RequestBody GenTemplateDetailDto dto) {
    return genTemplateDetailServiceImpl.add(dto);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenTemplateDetailDto dto) {
    return genTemplateDetailServiceImpl.update(dto);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenTemplateDetailDto dto) {
    return genTemplateDetailServiceImpl.status(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return genTemplateDetailServiceImpl.remove(id);
  }
}
