package com.hf.op.infrastructure.resp;

import java.io.Serializable;

/**
 * @author 曾仕斌
 * @function 校验错误枚举类
 * @date 2021/11/08
 */
public enum OpRespCodeEnum implements Serializable {

  USER_NOT_EXIST(12000, "用户不存在!"),
  USER_FUNCTIONS_NOT_EXIST(12001, "权限列表不存在!"),
  USER_NOT_HAVE_RIGHT(12002, "用户没有该操作权限!"),
  PARENT_DEPT_NOT_EXIST(12003, "上级部门不存在!"),
  DEPT_NOT_EXIST(12004, "部门不存在!"),
  DEPT_NAME_EXIST(12006, "部门名称已存在!"),
  DEPT_EXIST_USER(12007, "当前部门存在用户!"),
  DEPT_EXIST_SON_DEPT(12008, "当前部门存在下级部门!"),
  EXP_VERITY_CODE(12009, "验证码过期!"),
  ERROR_VERITY_CODE(12009, "验证码过期!"),
  POST_NAME_EXIST(12010, "岗位名称已存在!"),
  POST_NOT_EXIST(12011, "岗位不存在!"),
  POST_EXIST_USER(12012, "当前岗位存在用户!"),
  ROLE_NAME_EXIST(12013, "角色名称已存在!"),
  ROLE_NOT_EXIST(12014, "角色不存在!"),
  MENU_NOT_EXIST(12025, "菜单不存在!"),
  MENU_HAVE_CHILD(12026, "存在子菜单，不能删除!"),
  FAIL_CREATE_AUTHUSER(12015, "创建远程账号失败!"),
  FAIL_UPDATE_AUTHUSER(12016, "更新远程账号失败!"),
  FAIL_UPDATE_AUTHUSER_STATUS(12017, "更新远程账号状态失败!"),
  USER_ROLES_NOT_EXIST(12018, "角色列表不存在!"),
  FAIL_RESET_AUTHUSER_PWD(12019, "远程重置密码失败!"),
  ERR_FORMAT_PWD_MESSAGE(12020, "密码格式不正确!"),
  ERR_SAME_PWD_MESSAGE(12021, "新旧密码不能一样!"),
  ERR_REPEAT_PWD_MESSAGE(12022, "确认密码不一致!"),
  FAIL_EDIT_AUTHUSER_PWD(12023, "远程修改密码失败!"),
  SYSTEM_LIST_NOT_EXIST(12025, "系统列表不存在!"),
  PARENT_DEPT_DISABLE(12003, "上级部门不存在，或已禁用!"),
  PROJECT_FLOW_LIMIT_CREATE_ERROR(12024, "请添加1~10个业务流!");

  private int code;
  private String msg;

  OpRespCodeEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
