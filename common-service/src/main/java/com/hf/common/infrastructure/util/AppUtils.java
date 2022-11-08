package com.hf.common.infrastructure.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.hf.common.infrastructure.constant.CommonConstant;
import com.hf.common.infrastructure.vo.AppInfo;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 外部应用工具类 Created by chensy on 22-6-29.
 */
public class AppUtils {

  public final static AppInfo SSO_APP_INFO = new AppInfo("0001",
      "6df8613d89136jfs87ec9d06359334651", "sso系统");

  public final static AppInfo MINI_APP_INFO = new AppInfo("1001",
      "6df866jfs87ec9d01213d891334651xx", "小程序管理系统");

  public final static AppInfo PLATE_APP_INFO = new AppInfo("1002",
      "6df8665d37ec9d01213d34623ddx8913", "运营平台");

  public final static AppInfo WIKI_APP_INFO = new AppInfo("2001",
      "6df8665332sdg54434623ddx89123d3bd", "WIKI");

  public final static AppInfo PORTAL_APP_INFO = new AppInfo("2002",
      "6df8665332ddx89123hfdsaq233de3bd", "门户");

  public static boolean isValid(ServerHttpRequest request) {
    boolean isAppValid = false;
    String authorization = request.getHeaders().getFirst(CommonConstant.AUTHORIZATION);
    if (!StringUtils.isEmpty(authorization)) {
      String[] author = new String[0];
      try {
        Decoder decoder = Base64.getDecoder();
        author = (new String((decoder).decode(authorization.split(" ")[1])))
            .split(":");
      } catch (Exception e) {
        e.printStackTrace();
      }
      String appId = null;
      String tocken = null;
      AppInfo matchedAppInfo = null;
      if (author != null && author.length > 1) {
        appId = author[0];
        tocken = author[1];
        matchedAppInfo = verifyAppInfo(new AppInfo(appId, tocken));
        if (matchedAppInfo != null) {
          isAppValid = true;
        }
      }
    }
    return isAppValid;
    //return true;
  }

  public static AppInfo verifyAppInfo
      (AppInfo appInfo) {
    List<AppInfo> appInfoList = getAppList();
    AppInfo sAppInfo = null;
    AppInfo matchedAppInfo = null;
    for (int i = 0; i < appInfoList.size(); i++) {
      sAppInfo = appInfoList.get(i);
      if (sAppInfo.toString().equals(appInfo.toString())) {
        matchedAppInfo = sAppInfo;
        break;
      }
    }
    return matchedAppInfo;
  }

  public static List<AppInfo> getAppList() {
    List<AppInfo> appInfoList = new ArrayList<AppInfo>();
    appInfoList.add(MINI_APP_INFO);
    appInfoList.add(PLATE_APP_INFO);
    appInfoList.add(WIKI_APP_INFO);
    appInfoList.add(PORTAL_APP_INFO);
    return appInfoList;
  }

  public static AppInfo getAppInfoById(String appId) {
    AppInfo app = null;
    List<AppInfo> appInfoList = getAppList();
    for (AppInfo appInfo : appInfoList) {
      if (appInfo.getAppId().equals(appId)) {
        app = appInfo;
        break;
      }
    }
    return app;
  }

}
