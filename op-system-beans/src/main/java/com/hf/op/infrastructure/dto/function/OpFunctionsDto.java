package com.hf.op.infrastructure.dto.function;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author wpq
 * @function 权限树形列表
 * @Date 2022/02/10
 */
@Data
public class OpFunctionsDto implements Serializable {

  private static final long serialVersionUID = -7315374644349468325L;
  private String oldKey;
  private String newKey;
  private String oldName;
  private String newName;
  private List<Long> ids;

}
