package com.hf.common.infrastructure.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 外部应用工具类 Created by chensy on 22-6-29.
 */
public class OrderNoUtils {

  //商户id
  protected static String mchId="2210111519330750042";
  //appid
  protected static String appId="2209231328332210028";
  //请求密钥
  protected static String reqKey="z?hwmAL)+(NeX(yikw@#+&Gy91*2r4";
  //响应密钥
  protected static String resKey="Si1e)U-ueS4!c7=*7c)N+NH0&8U@J@";
  private static AtomicLong seq = new AtomicLong(0L);
  private static AtomicLong seqOrderNo = new AtomicLong(0L);
  public static String getOrderId() {
    SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd G
    return String.format("%s%s%06d", "G", fm.format(new Date()), (int) seq.getAndIncrement() % 1000000);
  }
  public static String getFreeOrderId() {
    SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd F
    return String.format("%s%s%06d", "DW", fm.format(new Date()), (int) seq.getAndIncrement() % 1000000);
  }

}
