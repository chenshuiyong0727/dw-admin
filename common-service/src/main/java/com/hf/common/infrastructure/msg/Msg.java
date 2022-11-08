package com.hf.common.infrastructure.msg;

import com.hf.common.infrastructure.util.StringUtilLocal;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 消息类
 * @date 2021/2/04
 */
@Data
public class Msg implements Serializable {

  /**
   * 版本
   */
  private String version = "1.0";

  /**
   * 请求编号
   */
  private String requestId;

  /**
   * 签名
   */
  private String sign;

  /**
   * 响应内容，JSON格式
   */
  private String data;

  /**
   * 时间戳,1970-1-1到当前时间的毫秒数
   */
  private Long timestamp;

  /**
   * 创建消息体
   */
  public static Msg createMsg(String data, String version, String requestId) {
    Msg msg = null;
    if (StringUtilLocal.isNotEmpty(data)) {
      msg = new Msg();
      msg.data = data;
    } else {
      return null;
    }
    if (StringUtilLocal.isNotEmpty(version)) {
      msg.version = version;
    }
    if (StringUtilLocal.isNotEmpty(requestId)) {
      msg.requestId = requestId;
    } else {
      msg.requestId = UUID.randomUUID().toString().replace("-", "");
    }
    msg.setTimestamp(System.currentTimeMillis());
    return msg;
  }

  @Override
  public String toString() {
    return "Msg{" +
        "version='" + version + '\'' +
        ", requestId='" + requestId + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
