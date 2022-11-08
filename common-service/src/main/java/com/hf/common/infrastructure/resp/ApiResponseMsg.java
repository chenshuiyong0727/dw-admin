package com.hf.common.infrastructure.resp;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.util.AESUtil;
import com.hf.common.infrastructure.util.MD5Utils;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.ThreadLocalContext;
import java.util.UUID;
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

  public ApiResponseMsg() {
    super();
  }

  public ApiResponseMsg(Integer subCode, String subMsg) {
    super(subCode, subMsg);
  }

  public static ApiResponseMsg createSysErrorApiResp(Integer returnCode, String msg) {
    ApiResponseMsg responseMsg = new ApiResponseMsg();
    responseMsg.setCode(returnCode);
    responseMsg.setMsg(msg);
    responseMsg.setRequestId(getRequestId());
    responseMsg.setAccountId(getAccoutId());
    responseMsg.setResponseId(UUID.randomUUID().toString().replaceAll("-", ""));
    return responseMsg;
  }

  public static ApiResponseMsg createBusinessErrorApiResp(Integer subCode, String subMsg) {
    ApiResponseMsg responseMsg = new ApiResponseMsg();
    responseMsg.setSubCode(subCode);
    responseMsg.setSubMsg(subMsg);
    responseMsg.setRequestId(getRequestId());
    responseMsg.setAccountId(getAccoutId());
    responseMsg.setResponseId(UUID.randomUUID().toString().replaceAll("-", ""));
    return responseMsg;
  }

  /**
   * 获取请求编号
   */
  private static String getRequestId() {
    Object requestId = ThreadLocalContext.get().get(CommonConstant.REQUEST_ID);
    if (null == requestId) {
      return null;
    }
    return requestId.toString();
  }

  /**
   * 获取用户编号
   */
  private static String getAccoutId() {
    Object accountId = ThreadLocalContext.get().get(CommonConstant.ACCOUNT_ID);
    if (null == accountId) {
      return null;
    }
    return accountId.toString();
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

  public ApiResponseMsg successSetData(Object data) {
    ApiResponseMsg responseMsg = new ApiResponseMsg();
    responseMsg.setRequestId(getRequestId());
    responseMsg.setAccountId(getAccoutId());
    responseMsg.setResponseId(UUID.randomUUID().toString().replaceAll("-", ""));
    responseMsg.setData(data);
    return responseMsg;
  }

  /**
   * @description 接口返回 json格式数据
   * @method successSetDataJson
   * @date: 2022/4/14 16:42
   * @author: chensy
   */
  public ApiResponseMsg successSetDataJson(Object data) {
    ApiResponseMsg responseMsg = new ApiResponseMsg();
    responseMsg.setRequestId(getRequestId());
    responseMsg.setAccountId(getAccoutId());
    responseMsg.setResponseId(UUID.randomUUID().toString().replaceAll("-", ""));
    if (data != null) {
      String res = JSONObject.toJSONString(data);
      responseMsg.setData(res);
    }
    return responseMsg;
  }

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
      str.append("sub_code=" + subCode);
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
}
