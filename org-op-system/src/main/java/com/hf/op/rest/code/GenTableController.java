package com.hf.op.rest.code;


import cn.hutool.core.convert.Convert;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.resp.ServerErrorConst;
import com.hf.common.infrastructure.util.HfBeanUtil;
import com.hf.common.infrastructure.util.ListBeanUtil;
import com.hf.op.infrastructure.dto.code.GenTableDto;
import com.hf.op.infrastructure.dto.code.gencode.GenTableAndColumnModel;
import com.hf.op.service.impl.code.GenTableServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
 * gen_table的实体类——控制器
 *
 * @author system
 * @date 2022-08-23
 */
@RestController
@RequestMapping(value = "v1/code/genTable")
public class GenTableController {


  private GenTableServiceImpl genTableServiceImpl;

  public GenTableController(GenTableServiceImpl genTableServiceImpl) {
    this.genTableServiceImpl = genTableServiceImpl;
  }

  /**
   * 获取数据
   */
  @GetMapping("/{id}")
  public ResponseMsg detail(@PathVariable(value = "id") Long id) {
    return genTableServiceImpl.detail(id);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("")
  public ResponseMsg page(HttpServletRequest request) {
    GenTableDto dto = HfBeanUtil.populate(new GenTableDto(), request);
    return genTableServiceImpl.page(dto);
  }

  /**
   * 获取数据列表
   */
  @GetMapping("getTables")
  public ResponseMsg getTables(HttpServletRequest request) {
    GenTableDto dto = HfBeanUtil.populate(new GenTableDto(), request);
    return genTableServiceImpl.getTables(dto);
  }

  /**
   * 新增数据
   */
  @PostMapping("/importTables")
  public ResponseMsg importTables(@RequestBody GenTableDto dto) {
    Assert.hasText(dto.getTableNames(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    String[] tableNameArray = Convert.toStrArray(dto.getTableNames());
    Assert.notNull(tableNameArray, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    return genTableServiceImpl.importTables(tableNameArray);
  }

  /**
   * 修改数据
   */
  @PutMapping("")
  public ResponseMsg update(@RequestBody GenTableAndColumnModel model) {
    Assert.notNull(model.getId(), ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    return genTableServiceImpl.update(model);
  }

  /**
   * 修改状态
   */
  @PutMapping("/status")
  public ResponseMsg status(@RequestBody GenTableDto dto) {
    return genTableServiceImpl.status(dto);
  }

  /**
   * 移除数据
   */
  @DeleteMapping("/{id}")
  public ResponseMsg remove(@PathVariable(value = "id") Long id) {
    return genTableServiceImpl.remove(id);
  }

  /**
   * 批量删除
   */
  @PutMapping("/batch/remove")
  public ResponseMsg batchRemove(@RequestBody List<GenTableDto> list) {
    Assert.notNull(list, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    List<Long> ids = ListBeanUtil.toList(list, "id");
    Assert.notNull(ids, ServerErrorConst.ERR_PARAM_EMPTY_MSG);
    genTableServiceImpl.removeByIds(ids);
    return new ResponseMsg();
  }
}
