package com.hf.common.infrastructure.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * http util to send data
 *
 * @author xuxueli
 * @version 2015-11-28 15:30:59
 */
@Slf4j
public class HttpClientUtil {


  /**
   * 同步数据接口类型 - 新增
   */
  public static final Integer SYNC_OP_TYPE_ADD = 1;
  /**
   * 同步数据接口类型 - 修改
   */
  public static final Integer SYNC_OP_TYPE_UPDATE = 2;
  /**
   * 同步数据接口类型 - 删除
   */
  public static final Integer SYNC_OP_TYPE_DELETE = 3;

  /**
   * post请求
   */
  public static String post(String url, String json, Map<String, String> headers) {
    log.info("====url ========== " + url);
    log.info("====json ========== " + json);
    HttpPost httpPost = null;
    CloseableHttpClient httpClient = null;
    try {
      httpPost = new HttpPost(url);
      if (json != null) {
        // 构造一个请求实体
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);
      }
      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000)
          .setConnectTimeout(30000).build();
      httpPost.setConfig(requestConfig);
      // headers
      if (headers != null && headers.size() > 0) {
        for (Map.Entry<String, String> headerItem : headers.entrySet()) {
          httpPost.setHeader(headerItem.getKey(), headerItem.getValue());
        }
      }
      httpClient = HttpClients.custom().disableAutomaticRetries().build();
      // parse response
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      if (null != entity) {
        if (response.getStatusLine().getStatusCode() == 200) {
          String responseMsg = EntityUtils.toString(entity, "UTF-8");
          EntityUtils.consume(entity);
          return responseMsg;
        }
        EntityUtils.consume(entity);
      }
      log.info("http statusCode error, statusCode:" + response.getStatusLine().getStatusCode());
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return e.getMessage();
    } finally {
      if (httpPost != null) {
        httpPost.releaseConnection();
      }
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * post 请求
   */
  public static String post(String url, Map<String, Object> params, Map<String, String> headers) {
    ObjectMapper jacksonObjectMapper = new ObjectMapper();
    String sendData = null;
    try {
      sendData = jacksonObjectMapper.writeValueAsString(params);
    } catch (Exception e) {
      log.error("sendData parse error:", e);
    }
    return HttpClientUtil.post(url, sendData, headers);
  }

  public static JSONObject postByJson(String clientApiUrl, String json, String key)
      throws IOException {
    String resultJson = HttpClientUtil.post(clientApiUrl, json, null);
    if (StringUtilLocal.isEmpty(resultJson)) {
      log.error("请求运营平台失败 " + json);
      return null;
    }
    JSONObject jsonObject = new ObjectMapper().readValue(resultJson, JSONObject.class);
    if (jsonObject == null) {
      log.error("请求运营平台失败 " + json);
      return null;
    }
    if (jsonObject.getInteger("code") != 1 || jsonObject.getInteger("sub_code") != 1000
        || StringUtilLocal
        .isEmpty(jsonObject.getString("data"))) {
      log.warn("请求运营平台 不成功 : " + resultJson);
      return null;
    }
    String result = AESUtil.desEncryptWithKey(jsonObject.getString("data"), key).trim();
    log.warn("请求运营平台结果解密 : " + result);
    JSONObject object = JSONObject.parseObject(result);
    return object;
  }

  public static JSONObject postByJsonPortal(String clientApiUrl, String json, String key)
      throws IOException {
    String resultJson = HttpClientUtil.post(clientApiUrl, json, null);
    if (StringUtilLocal.isEmpty(resultJson)) {
      log.error("请求门户平台失败 " + json);
      return null;
    }
    JSONObject jsonObject = new ObjectMapper().readValue(resultJson, JSONObject.class);
    if (jsonObject == null) {
      log.error("请求门户平台失败 " + json);
      return null;
    }
    if (jsonObject.getInteger("code") != 1 || jsonObject.getInteger("sub_code") != 1000
        || StringUtilLocal
        .isEmpty(jsonObject.getString("data"))) {
      log.warn("请求门户平台 不成功 : " + resultJson);
      return null;
    }
    String result = AESUtil.desEncryptWithKey(jsonObject.getString("data"), key).trim();
    log.warn("请求门户平台结果解密 : " + result);
    JSONObject object = JSONObject.parseObject(result);
    return object;
  }

  public static JSONObject postByMap(String clientApiUrl, Map<String, Object> param,
      Map<String, String> headers)
      throws IOException {
    String resultJson = HttpClientUtil.post(clientApiUrl, param, headers);
    if (StringUtilLocal.isEmpty(resultJson)) {
      log.error("请求小程序平台失败 " + JSONObject.toJSONString(param));
      return null;
    }
    JSONObject jsonObject = new ObjectMapper().readValue(resultJson, JSONObject.class);
    if (jsonObject == null) {
      log.error("请求小程序平台失败 " + JSONObject.toJSONString(param));
      return null;
    }
    log.warn("请求小程序平台结果 : " + jsonObject);
    return jsonObject;
  }

  /**
   * <p>默认的报文头</p>
   */
  public static Map<String, String> reqHeader(String user, String password) {
    Map<String, String> headers = Maps.newHashMap();
    headers.put("Authorization", getAuthorizationCode(user, password));
    headers.put("Content-type", "application/json; charset=UTF-8");
    return headers;
  }

  /**
   * 获取接口授权Basic码
   *
   * @author wdh
   */
  public static String getAuthorizationCode(String user, String password) {
    String encodeStr = "";
    try {
      String source = user + ":" + password;
      String base64encodedString = Base64.getEncoder()
          .encodeToString(source.getBytes(EncryptUtils.CHARSET));
      encodeStr = "Basic " + base64encodedString;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return encodeStr;
  }
/*
  public static void main(String[] args) throws IOException {
    Map<String, String> headers = reqHeader(AppUtils.MINI_APP_INFO.getAppId(),
        AppUtils.MINI_APP_INFO.getTocken());
    String url = "http://localhost:28087/gw/op/v1/syncData/addAuthority";

*//*    Map<String, Object> userTherapist = new HashMap<>();
    userTherapist.put("recom", 0);
    userTherapist.put("userId", "chens8");
    userTherapist.put("type", "1");
    userTherapist.put("name", "陈224");
    userTherapist.put("phone", "15860778138");
    userTherapist.put("ssoUserNo", "f308222613784e9dg7eace4fcf7a3f6d");*//*
    Map<String, Object> param = new HashMap<>();
    param.put("authorityId", "1");
    JSONObject object = HttpClientUtil.postByMap(url, param, headers);
    log.info("s ", object);
  }*/
  public static void main(String[] args) throws IOException {
    Map<String, String> headers = reqHeader("0001", "6df8613d89136jfs87ec9d06359334651");
    String url = "https://natt.yimed.cn:11900/admin-api/ssoApi/addUser";

    Map<String, Object> userTherapist = new HashMap<>();
    userTherapist.put("recom", 0);
    userTherapist.put("userId", "chen3s8");
    userTherapist.put("type", "1");
    userTherapist.put("name", "陈224");
    userTherapist.put("phone", "15861378138");
    userTherapist.put("ssoUserNo", "f308222613784e9dg7eace4fcf7a3f6d");
    Map<String, Object> param = new HashMap<>();
    param.put("userTherapist", userTherapist);
    JSONObject object =  HttpClientUtil.postByMap(url, param, headers);
    log.info("s ", object);
  }
}
