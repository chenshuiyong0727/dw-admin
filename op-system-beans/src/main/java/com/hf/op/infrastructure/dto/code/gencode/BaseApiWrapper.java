package com.hf.op.infrastructure.dto.code.gencode;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Api 基础类
 *
 * 尽量 与本地 服务的 entity 保持一致（除去不想要暴露给 web的字段）
 *
 * api层级的 wrapper 也是对于数据安全性的一次包装
 *
 * Entity 增加的 deleted 字段， 不需要同步更新到 Wrapper的Model中 Wrapper的Model 只是用于 对外展示
 *
 * @author Parker
 * @date 2019-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ContentRowHeight(16)
@HeadRowHeight(21)
@HeadFontStyle(fontName = "Arial", color = 9, fontHeightInPoints = 10)
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 23)
@ColumnWidth(22)
public abstract class BaseApiWrapper implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId
  @ApiModelProperty(value = "ID")
  @ExcelIgnore
  //@ExcelProperty(value = "ID", order = 1000)
  //@ExcelInfo
  private Long id;

  /**
   * 创建人
   */
  @ApiModelProperty(value = "创建人")
  @ExcelIgnore
  //@ExcelProperty(value = "创建人", order = 1001)
  //@ExcelInfo
  private String createUserName;

  /**
   * 创建时间
   */
  @ApiModelProperty(value = "创建时间")
  @ExcelIgnore
  //@ExcelProperty(value = "创建时间", order = 1002)
  //@ExcelInfo
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  /**
   * 更新人
   */
  @ApiModelProperty(value = "修改人")
  @ExcelIgnore
  //@ExcelProperty(value = "修改人", order = 1003)
  //@ExcelInfo
  private String updateUserName;

  /**
   * 更新时间
   */
  @ApiModelProperty(value = "修改时间")
  @ExcelIgnore
  //@ExcelProperty(value = "修改时间", order = 1004)
  //@ExcelInfo
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
}
