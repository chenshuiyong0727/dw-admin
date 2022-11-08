package com.open.api.req;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hf.common.infrastructure.util.AESUtil;
import com.hf.common.infrastructure.util.MD5Utils;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 曾仕斌
 * @function 数据接口请求消息体
 * @date 2021/10/06
 */
@Data
@Slf4j
public class ApiDataRequestMsg {

  @JsonProperty(value = "account_id")
  private String accountId;

  @JsonProperty(value = "request_id")
  private String requestId;

  private String data;

  private Long timestamp;

  private String sign;

  /**
   * 签名
   */
  public String sign(String key) {
    if (StringUtilLocal.isEmpty(key)) {
      return null;
    }
    String str =
        "account_id=" + accountId + "&request_id=" + requestId + "&data=" + data + "&timestamp="
            + timestamp;
    String signStr = MD5Utils.getMD5(str + "&key=" + key, "UTF-8");
    return signStr;
  }

  /**
   * 解密
   */
  public String decode(String key) {
    if (StringUtilLocal.isEmpty(key) || StringUtilLocal.isEmpty(data)) {
      return null;
    }
    return AESUtil.desEncryptWithKey(data, key);
  }

  @Override
  public String toString() {
    return "ApiDataRequestMsg{" +
        "accountId='" + accountId + '\'' +
        ", requestId='" + requestId + '\'' +
        ", timestamp=" + timestamp +
        ", sign='" + sign + '\'' +
        '}';
  }

  public static void main(String[] args) {
    String str = "{\"loginAccount\":\"11111111\",\"loginPassword\":\"88888888\"}\"";
    ApiDataRequestMsg apiDataRequestMsg = new ApiDataRequestMsg();
    apiDataRequestMsg.setAccountId("1093834927859961856");
    apiDataRequestMsg.setRequestId("123456717");
    Long time = System.currentTimeMillis();
    log.info(time + "");
    apiDataRequestMsg.setTimestamp(time);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("loginAccount", "11111111");
    String data = AESUtil.encryptWithKey(JSONObject.toJSONString(jsonObject), "a21c501f6d344e3c");
    log.info("data:" + data);
    apiDataRequestMsg.setData(data);
    String sign = apiDataRequestMsg.sign("a21c501f6d344e3c");
    System.out.println(sign);
  }
}
