package com.test.op;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author
 * @function
 * @date 2021/11/19
 */
public class Test {

  public static void main(String[] args) {
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //String jwt = "1639014372".concat("000");
    //String exp = sdf.format(new Date(Long.parseLong(String.valueOf(jwt))));      // 时间戳转换成时间

    List<String> data = Arrays
        .asList("/foo", "/bar", "/foo/admin", "/foo/cust", "/bar/erp", "/bar/erp/call",
            "/foo/cust/profile");
    Collections.sort(data, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    for (String s : data) {
      int length = s.split("/").length - 1;
      for (int i = 0; i < length; i++) {
        System.out.print("-- ");
      }
      System.out.println(s);
    }

    buildAccountJson();

    JSONObject codeJson = new JSONObject();
    codeJson.put("verification_code", "1234");
    codeJson.put("secret_key", "58d10555a17a4039");
    System.out.println(codeJson.toJSONString());

    System.out.println(System.currentTimeMillis());

    JSONObject roleJson = new JSONObject();
    List<JSONObject> roleList = new ArrayList<>();
    JSONObject jsonRole = new JSONObject();
    jsonRole.put("id", "1");
    jsonRole.put("role_name", "超级管理员");
    jsonRole.put("remark", "超级管理员");
    jsonRole.put("update_time", "2021-11-18 00:00:00");
    jsonRole.put("update_user_name", "超级管理员");
    roleList.add(jsonRole);
    roleJson.put("list", roleList);

    JSONObject jsonPageInfo = new JSONObject();
    jsonPageInfo.put("total_count", 1);
    jsonPageInfo.put("page_size", 1);
    jsonPageInfo.put("page_num", 1);
    roleJson.put("page_info", jsonPageInfo);
    System.out.println(roleJson);
  }

  public static String buildAccountJson() {
    JSONObject dataJson = new JSONObject();
    dataJson.put("login_id", "1");
    dataJson.put("token", "0183b1eef01ddfd623869c343e078c0ee45a14e14d7b38d88e555af5730a9899");
    dataJson
        .put("refresh_token", "0183b1eef01ddfd623869c343e078c0ee45a14e14d7b38d88e555af5730a9900");
    dataJson.put("token_expire_time", "2037-01-01 00:00:00");
    List<JSONObject> funtionList = new ArrayList<>();
    JSONObject jsonObjectNode = new JSONObject();
    jsonObjectNode.put("id", "4000");
    jsonObjectNode.put("function_key", "system_manage");
    jsonObjectNode.put("function_name", "系统管理");
    jsonObjectNode.put("function_desc", "系统管理");
    jsonObjectNode.put("route", "");
    jsonObjectNode.put("sort", "5000000000");
    jsonObjectNode.put("operation_type", "node");
    jsonObjectNode.put("location_path", "system_manage");
    jsonObjectNode.put("function_label", "系统管理");
    jsonObjectNode.put("function_type", "1");
    funtionList.add(jsonObjectNode);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", "4001");
    jsonObject.put("function_key", "organization_manage");
    jsonObject.put("function_name", "组织管理");
    jsonObject.put("function_desc", "组织管理");
    jsonObject.put("route", null);
    jsonObject.put("sort", "5010000000");
    jsonObject.put("operation_type", "stretch");
    jsonObject.put("location_path", "system_manage:organization_manage");
    jsonObject.put("function_label", "组织管理");
    jsonObject.put("function_type", "1");
    funtionList.add(jsonObject);

    JSONObject jsonObject1 = new JSONObject();
    jsonObject1.put("id", "4002");
    jsonObject1.put("function_key", "get:v1:sys:departments");
    jsonObject1.put("function_name", "组织结构");
    jsonObject1.put("function_desc", "部门管理");
    jsonObject1.put("route", "/organization/structure");
    jsonObject1.put("sort", "5010100000");
    jsonObject1.put("operation_type", "list");
    jsonObject1.put("location_path", "system_manage:organization_manage:departments");
    jsonObject1.put("function_label", "组织结构");
    jsonObject1.put("function_type", "1");
    funtionList.add(jsonObject1);

    JSONObject jsonObject2 = new JSONObject();
    jsonObject2.put("id", "4003");
    jsonObject2.put("function_key", "get:v1:sys:departments");
    jsonObject2.put("function_name", "查询部门");
    jsonObject2.put("function_desc", "查询部门");
    jsonObject2.put("route", "");
    jsonObject2.put("sort", "5010100010");
    jsonObject2.put("operation_type", "query");
    jsonObject2.put("location_path", "system_manage:organization_manage:departments:query");
    jsonObject2.put("function_label", "查询");
    jsonObject2.put("function_type", "2");
    funtionList.add(jsonObject2);

    JSONObject jsonObject3 = new JSONObject();
    jsonObject3.put("id", "4004");
    jsonObject3.put("function_key", "reset:v1:sys:departments");
    jsonObject3.put("function_name", "重置");
    jsonObject3.put("function_desc", "重置");
    jsonObject3.put("route", "");
    jsonObject3.put("sort", "5010100013");
    jsonObject3.put("operation_type", "reset");
    jsonObject3.put("location_path", "system_manage:organization_manage:departments:reset");
    jsonObject3.put("function_label", "重置");
    jsonObject3.put("function_type", "2");
    funtionList.add(jsonObject3);

    JSONObject jsonObject4 = new JSONObject();
    jsonObject4.put("id", "4005");
    jsonObject4.put("function_key", "post:v1:sys:departments");
    jsonObject4.put("function_name", "新增部门");
    jsonObject4.put("function_desc", "新增部门");
    jsonObject4.put("route", "/organization/structure/add");
    jsonObject4.put("sort", "5010100040");
    jsonObject4.put("operation_type", "post");
    jsonObject4.put("location_path", "system_manage:organization_manage:departments:post");
    jsonObject4.put("function_label", "新增");
    jsonObject4.put("function_type", "3");
    funtionList.add(jsonObject4);

    JSONObject jsonObject5 = new JSONObject();
    jsonObject5.put("id", "4006");
    jsonObject5.put("function_key", "put:v1:sys:departments");
    jsonObject5.put("function_name", "修改部门");
    jsonObject5.put("function_desc", "修改部门");
    jsonObject5.put("route", "");
    jsonObject5.put("sort", "5010100075");
    jsonObject5.put("operation_type", "put");
    jsonObject5.put("location_path", "system_manage:organization_manage:departments:put");
    jsonObject5.put("function_label", "修改");
    jsonObject5.put("function_type", "4");
    funtionList.add(jsonObject5);

    JSONObject jsonObjectIcon = new JSONObject();
    jsonObjectIcon.put("id", "5000");
    jsonObjectIcon.put("function_key", "personal_account");
    jsonObjectIcon.put("function_name", "个人账户");
    jsonObjectIcon.put("function_desc", "个人账户");
    jsonObjectIcon.put("route", "");
    jsonObjectIcon.put("sort", "6000000000");
    jsonObjectIcon.put("operation_type", "icon");
    jsonObjectIcon.put("location_path", "personal_account");
    jsonObjectIcon.put("function_label", "我的账号");
    jsonObjectIcon.put("function_type", "1");
    funtionList.add(jsonObjectIcon);

    JSONObject jsonObjectIcon1 = new JSONObject();
    jsonObjectIcon1.put("id", "5001");
    jsonObjectIcon1.put("function_key", "my_account");
    jsonObjectIcon1.put("function_name", "我的账号");
    jsonObjectIcon1.put("function_desc", "我的账号");
    jsonObjectIcon1.put("route", null);
    jsonObjectIcon1.put("sort", "6010000000");
    jsonObjectIcon1.put("operation_type", "stretch");
    jsonObjectIcon1.put("location_path", "personal_account:my_account");
    jsonObjectIcon1.put("function_label", "我的账号");
    jsonObjectIcon1.put("function_type", "1");
    funtionList.add(jsonObjectIcon1);

    JSONObject jsonObjectIcon2 = new JSONObject();
    jsonObjectIcon2.put("id", "5002");
    jsonObjectIcon2.put("function_key", "get:v1:sys:users");
    jsonObjectIcon2.put("function_name", "个人信息");
    jsonObjectIcon2.put("function_desc", "个人信息");
    jsonObjectIcon2.put("route", "/myAccount/myInfo");
    jsonObjectIcon2.put("sort", "6010100000");
    jsonObjectIcon2.put("operation_type", "get");
    jsonObjectIcon2.put("location_path", "personal_account:my_account:user");
    jsonObjectIcon2.put("function_label", "个人信息");
    jsonObjectIcon2.put("function_type", "1");
    funtionList.add(jsonObjectIcon2);

    JSONObject jsonObjectIcon3 = new JSONObject();
    jsonObjectIcon3.put("id", "5003");
    jsonObjectIcon3.put("function_key", "put:v1:sys:users:pwd");
    jsonObjectIcon3.put("function_name", "修改密码");
    jsonObjectIcon3.put("function_desc", "修改密码");
    jsonObjectIcon3.put("route", "/myAccount/changePassword");
    jsonObjectIcon3.put("sort", "6010200000");
    jsonObjectIcon3.put("operation_type", "put");
    jsonObjectIcon3.put("location_path", "personal_account:my_account:pwd");
    jsonObjectIcon3.put("function_label", "修改密码");
    jsonObjectIcon3.put("function_type", "1");
    funtionList.add(jsonObjectIcon3);

    JSONObject jsonObjectIcon4 = new JSONObject();
    jsonObjectIcon4.put("id", "6000");
    jsonObjectIcon4.put("function_key", "wait_work");
    jsonObjectIcon4.put("function_name", "待办页面");
    jsonObjectIcon4.put("function_desc", "待办页面");
    jsonObjectIcon4.put("route", "");
    jsonObjectIcon4.put("sort", "7000000000");
    jsonObjectIcon4.put("operation_type", "icon");
    jsonObjectIcon4.put("location_path", "wait_work");
    jsonObjectIcon4.put("function_label", "待办页面");
    jsonObjectIcon4.put("function_type", "1");
    funtionList.add(jsonObjectIcon4);

    JSONObject jsonObjectIcon5 = new JSONObject();
    jsonObjectIcon5.put("id", "6001");
    jsonObjectIcon5.put("function_key", "get:v1:wait:work");
    jsonObjectIcon5.put("function_name", "全部待办");
    jsonObjectIcon5.put("function_desc", "全部待办");
    jsonObjectIcon5.put("sort", "7010000000");
    jsonObjectIcon5.put("route", "/waitWork/allWaitWork");
    jsonObjectIcon5.put("operation_type", "list");
    jsonObjectIcon5.put("location_path", "wait_work:all_wait_work:work");
    jsonObjectIcon5.put("function_label", "全部待办");
    jsonObjectIcon5.put("function_type", "1");
    funtionList.add(jsonObjectIcon5);

    dataJson.put("function_list", funtionList);

    List<JSONObject> projectList = new ArrayList<>();
    JSONObject jsonObjectPro = new JSONObject();
    jsonObjectPro.put("project_id", "1");
    jsonObjectPro.put("customer_id", "1");
    jsonObjectPro.put("project_name", "系统测试项目");
    projectList.add(jsonObjectPro);
    dataJson.put("project_list", projectList);
    System.out.println(dataJson.toJSONString());
    return dataJson.toJSONString();
  }


}
