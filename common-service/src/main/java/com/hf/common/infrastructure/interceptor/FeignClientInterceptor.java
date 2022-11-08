package com.hf.common.infrastructure.interceptor;

import com.alibaba.fastjson.JSON;
import com.hf.common.domain.UserInfo;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.ThreadLocalContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author 曾仕斌
 * @function 拦截器
 * @date 2022/3/10
 */
@Component
public class FeignClientInterceptor implements RequestInterceptor {

  private static final Logger log = LoggerFactory.getLogger(FeignClientInterceptor.class);

  public FeignClientInterceptor() {
  }

  /**
   * 设置用户编号和名字
   */
  @Override
  public void apply(RequestTemplate requestTemplate) {
    Object userInfo = ThreadLocalContext.get().get(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER);
    if (null != userInfo) {
      UserInfo user = (UserInfo) userInfo;
      if (user != null) {
        try {
          String userJson = JSON.toJSONString(user);
          requestTemplate.header(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER,
              new String[]{URLEncoder.encode(userJson, "UTF-8")});
        } catch (UnsupportedEncodingException e) {
          log.error("用户信息设置错误", e);
        }
      }
    }
    String traceId = MDC.get(CommonConstant.TRACE_ID);
    if (StringUtilLocal.isNotEmpty(traceId)) {
      requestTemplate.header(CommonConstant.TRACE_ID, traceId);
    } else {
      requestTemplate
          .header(CommonConstant.TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
    }
    String accountId = ThreadLocalContext.getStrValue(CommonConstant.ACCOUNT_ID);
    if (StringUtilLocal.isNotEmpty(accountId)) {
      requestTemplate.header(CommonConstant.ACCOUNT_ID, accountId);
    }
    String requestId = ThreadLocalContext.getStrValue(CommonConstant.REQUEST_ID);
    if (StringUtilLocal.isNotEmpty(requestId)) {
      requestTemplate.header(CommonConstant.REQUEST_ID, requestId);
    }
    String customerId = ThreadLocalContext.getStrValue(CommonConstant.CUSTOMER_ID);
    if (StringUtilLocal.isNotEmpty(customerId)) {
      requestTemplate.header(CommonConstant.CUSTOMER_ID, customerId);
    }
  }

}
