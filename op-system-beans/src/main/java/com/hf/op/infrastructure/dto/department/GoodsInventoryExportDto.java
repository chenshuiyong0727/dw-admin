package com.hf.op.infrastructure.dto.department;

import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.io.Serializable;
import lombok.Data;

/**
  * 商品库存 exportDto
  * @author chensy
  * @date 2022-11-12 20:10:34
  */
@Data
public class GoodsInventoryExportDto implements Serializable {

  private static final long serialVersionUID = 1743800917859287586L;

  /**
    * 库存编号
    */
  @Excel(name = "库存编号")
  private Long id;

  /**
    * 商品编号
    */
  @Excel(name = "商品编号")
  private Long goodsId;

  /**
    * 尺码编号
    */
  @Excel(name = "尺码编号")
  private Long sizeId;

  /**
    * 库存
    */
  @Excel(name = "库存")
  private Integer inventory;

  /**
    * 创建时间
    */
  @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private Integer createTime;

  /**
    * 更新时间
    */
  @Excel(name = "更新时间", databaseFormat = "yyyy-MM-dd HH:mm:ss:ss", format = "yyyy/MM/dd HH:mm", width = 20)
  private LocalDateTime updateTime;

}
