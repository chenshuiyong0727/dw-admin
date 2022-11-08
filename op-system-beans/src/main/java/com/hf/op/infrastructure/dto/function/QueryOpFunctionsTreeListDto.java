package com.hf.op.infrastructure.dto.function;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wpq
 * @function 权限树形列表
 * @Date 2022/02/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOpFunctionsTreeListDto implements Serializable {

  private static final long serialVersionUID = -7315374644949468325L;

  private Long id;
  private Long pid;
  private String pname = "根目录";
  private String functionKey;
  private String functionName;
  private String route;
  private Integer systemId;
  private Integer functionType;
  private String operationType;
  private String locationPath;
  private String otherLocationPath;
  private String functionDesc;
  private String functionLabel;
  private Long sort;
  private Boolean hasChildren = false;
  private List<QueryOpFunctionsTreeListDto> child;

}
