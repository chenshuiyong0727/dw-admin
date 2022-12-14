package com.hf.common.infrastructure.aspect;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.hf.common.domain.UserInfo;
import com.hf.common.domain.model.cache.user.CustomerInfo;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.dto.BaseDto;
import com.hf.common.infrastructure.exception.ServiceException;
import com.hf.common.infrastructure.global.cache.CommCacheConst;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.common.infrastructure.util.ThreadLocalContext;
import com.hf.common.infrastructure.util.ValidatorUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerAspect {

  private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);

  private static final String POST_TYPE = "POST";

  private static final String PUT_TYPE = "PUT";

  @CreateCache(name = CommCacheConst.BASE_KEY_ORG
      + "authCustomerAccount", cacheType = CacheType.BOTH)
  @CacheRefresh(refresh = 5, timeUnit = TimeUnit.MINUTES)
  private Cache<String, CustomerInfo> authCustomerAccount;

  /**
   * 拦截控制层
   */
  @Pointcut("execution(* com..*.rest..*(..))")
  public void controllerCut() {
  }

  @Before("controllerCut()")
  public void invokeBefore(JoinPoint point) throws Throwable {

    //设置日志统一编号
    setTraceId();

    //设置用户信息
    setUserInfo();

    setOtherInfo();
    // 验证参数
    validation(point);
  }

  /**
   * 切如 post 请求
   *
   * @param point point
   */
  public void validation(JoinPoint point) throws Throwable {
    Object[] args = point.getArgs();
    try {
      RequestAttributes ra = RequestContextHolder.getRequestAttributes();
      ServletRequestAttributes sra = (ServletRequestAttributes) ra;
      if (sra == null) {
        return;
      }
      HttpServletRequest request = sra.getRequest();
      String method = request.getMethod();

      // 只有 post 请求 才会去验证数据
      if (POST_TYPE.equals(method) || PUT_TYPE.equals(method)) {
        for (Object arg : args) {
          // 参数校验
          if (arg instanceof BaseDto) {
            // 如果是内部调用 则不判断参数非法情况
            ValidatorUtil.verify(arg);
          }
        }
      }
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 设置日志统一编号
   */
  private void setTraceId() {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    String traceId = sra.getRequest().getHeader(CommonConstant.TRACE_ID);
    if (StringUtilLocal.isNotEmpty(traceId)) {
      MDC.put(CommonConstant.TRACE_ID, traceId);
    } else {
      MDC.put(CommonConstant.TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
    }
  }

  /**
   * 设置用户信息
   */
  private void setUserInfo() {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    String userJson = sra.getRequest().getHeader(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER);
    String customerId = sra.getRequest().getHeader(CommonConstant.CUSTOMER_ID);
    if (StringUtils.isNotBlank(userJson)) {
      try {
        userJson = URLDecoder.decode(userJson, "UTF-8");
        UserInfo userInfo = (UserInfo) JSON.parseObject(userJson, UserInfo.class);

        //将UserInfo放入上下文中
        ThreadLocalContext.get().put(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER, userInfo);
      } catch (UnsupportedEncodingException e) {
        log.error("init userInfo error", e);
      }
    } else if (StringUtilLocal.isNotEmpty(customerId)) {

      CustomerInfo customerInfo = authCustomerAccount.get(customerId);
      if (null == customerInfo) {
        return;
      }
      UserInfo userInfo = new UserInfo();
      userInfo.setUserId(customerInfo.getUserId());
      userInfo.setUserRealName(customerInfo.getCustomerName());
      ThreadLocalContext.get().put(CommonConstant.KEY_USERINFO_IN_HTTP_HEADER, userInfo);
      if (customerInfo.getAccountId() != null) {
        ThreadLocalContext.get().put(CommonConstant.ACCOUNT_ID, customerInfo.getAccountId());
      }
      ThreadLocalContext.get().put(CommonConstant.CUSTOMER_ID, customerId);
    }
  }

  /**
   * 设置其他信息
   */
  public void setOtherInfo() {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    String requestId = sra.getRequest().getHeader(CommonConstant.REQUEST_ID);
    ThreadLocalContext.get().put(CommonConstant.REQUEST_ID, requestId);
    String accountId = sra.getRequest().getHeader(CommonConstant.ACCOUNT_ID);
    if (StringUtilLocal.isNotEmpty(accountId)) {
      ThreadLocalContext.get().put(CommonConstant.ACCOUNT_ID, accountId);
    }
    String customerId = sra.getRequest().getHeader(CommonConstant.CUSTOMER_ID);
    if (StringUtilLocal.isNotEmpty(customerId)) {
      ThreadLocalContext.get().put(CommonConstant.CUSTOMER_ID, customerId);
    }
  }

  @After("controllerCut()")
  public void invokeAfter() {

    //必须清除上下文以免影响其他请求
    ThreadLocalContext.get().clear();
  }

}
