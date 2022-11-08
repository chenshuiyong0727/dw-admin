package com.open.api.resp;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.AESUtil;
import com.hf.common.infrastructure.util.MD5Utils;
import com.hf.common.infrastructure.util.StringUtilLocal;
import lombok.Data;

/**
 * @author 曾仕斌
 * @function 接口请求响应消息体
 * @date 2021/5/17
 */
@Data
public class ApiResponseMsg extends ResponseMsg {

  /**
   * 响应时间戳
   */
  Long timestamp = System.currentTimeMillis();

  /**
   * 签名
   */
  String sign;

  @JsonProperty(value = "account_id")
  private String accountId;

  @JsonProperty(value = "request_id")
  private String requestId;

  @JsonProperty(value = "response_id")
  private String responseId;

  /**
   * 签名
   */
  public String sign(String key) {
    if (StringUtilLocal.isEmpty(key)) {
      return null;
    }
    StringBuilder str = new StringBuilder();
    str.append("account_id=" + accountId + "&response_id=" + responseId + "&request_id=" + requestId
        + "&code=" + code);
    if (StringUtilLocal.isNotEmpty(msg)) {
      str.append("&msg=" + msg);
    }
    if (null != subCode) {
      str.append("&sub_code=" + subCode);
    }
    if (StringUtilLocal.isNotEmpty(subMsg)) {
      str.append("&sub_msg=" + subMsg);
    }
    if (null != data && StringUtilLocal.isNotEmpty(data.toString())) {
      str.append("&data=" + data);
    }
    str.append("&timestamp=" + timestamp);
    String signStr = MD5Utils.getMD5(str.toString() + "&key=" + key, "UTF-8");
    return signStr;
  }

  /**
   * 解密
   */
  public String encode(String key) {
    if (StringUtilLocal.isEmpty(key) || null == data || StringUtilLocal.isEmpty(data.toString())) {
      return null;
    }
    String result = AESUtil.encryptWithKey(data.toString(), key);
    return result;
  }

  @Override
  public String toString() {
    return "ApiResponseMsg{" +
        "timestamp=" + timestamp +
        ", sign='" + sign + '\'' +
        ", accountId='" + accountId + '\'' +
        ", requestId='" + requestId + '\'' +
        ", responseId='" + responseId + '\'' +
        ", code=" + code +
        ", msg='" + msg + '\'' +
        ", subCode=" + subCode +
        ", subMsg='" + subMsg + '\'' +
        '}';
  }

  public static void main(String[] args) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("loginAccount", "11111111");
    jsonObject.put("loginPassword", "88888888");
    String data = JSONObject.toJSONString(jsonObject);
    ApiResponseMsg apiResponseMsg = new ApiResponseMsg();
    apiResponseMsg.setData(data);
    String key = "a21c501f6d344e3c";
    String subKey = key.substring(0, 16);
    System.out.println("subKey:" + subKey);
    String encodeStr = apiResponseMsg.encode(key);
    System.out.println("length:" + encodeStr.length() + ",str:" + encodeStr);
  }
}
