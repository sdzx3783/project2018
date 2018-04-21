package com.hotent.platform.webservice.util;

import org.activiti.engine.ActivitiInclusiveGateWayException;

public enum ReturnCode {
	SUCCESS(0, "SUCCESS"), 
	SYSTEM_ERROR(1000, "服务器错误"), 
	SYSTEM_BUSY(1001, "服务器繁忙，请稍后重试或与服务商联系"), 
	AUTHORITY_ERROR(1002, "非法操作，通常是没有权限使用此项服务"), 
	SIGN_ERROR(1003, "签名参数sign校验失败"), 
	OPENID_ERROR(1004, "openid不存在或者非法"), 
	INTERFACE_ERROR(1005, "请求的接口无效"),
	FREQUENCY_ERROR(1006, "您操作太频繁了，请先停下来喝杯茶，稍后再继续吧！"),
	METHOD_NOT_SUPPORTED(1007,"请求方式错误"),
	NO_DATA_FOUND(1008, "没有找到数据"),
	NOT_LOGIN(1009, "未登录"),
	AUTHENTICATION_FAILURE(1010, "认证失败"),
	EXCEPTION_ERROR(1011, "异常错误"),
	PRAMS_IS_NULL(2001, "必填参数为空"),
	PRAMS_ERROR(2002, "请求参数无效"),
	PRAMS_CONTAIN_ILLEGAL_CHARACTER(2003, "参数含有非法字符"),
	RESULT_TOO_LONG(2004, "查询的数量超过最大限制"),
	SYSTEM_RESTRICTION(2005, "接口限制"),
	LIMIT_FILE_SIZE(2006, "超过文件最大限制"),
	LOGIN_ERROR(2100, "用户登录失败"),
	REGISTER_ERROR(2101, "注册用户失败"),
	PWD_ENCRY_ERROR(2102, "密码加密失败"),
	/*NICKNAME_TOO_LONG(2103, "昵称太长了"),
	WX_NET_ERROR(2401, "网络原因微信登录失败，请稍后重试"),
	WX_CODE_NULL(2402, "code参数为空"),
	USER_MOBILE_NULL(2403, "手机号不能为空"),
	SEND_MESSAGE_FAIL(2404, "短信发送失败"),
	CACHE_CODE_NULL(2405, "请重新获取验证码"),
	CODE_IS_WRONG(2406, "验证码不正确"),
	MOBILE_CHANGE_FAIL(2407, "手机号码设置失败"),
	MOBILE_FORMAT_ERROR(2408, "手机格式错误"),
	PRAISE_ADD_ERROR(2409, "点赞出错"),
	PRAISE_CANCEL_ERROR(2410, "取消点赞出错"),
	CONCERN_ADD_ERROR(2411, "添加关注出错"),
	CONCERN_CANCEL_ERROR(2412, "取消关注出错"),*/
	//oa return_code
	TASK_COMPLETED_ERROR(3101,"该任务已被执行"),
	PROCESSINSTANCE_FORBID(3102,"流程实例已经禁止，该任务不能办理"),
	TASK_ASSIGNEE_ERROR(3103,"对不起,你不是这个任务的执行人,不能处理此任务"),
	TASK_LOCKEDBYOTHER(3104,"该任务已被其他人锁定"),
	BPMVAR_NOTEXIST_ERROR(3105,"流程参数不存在"),
	ACTIVITI_INCLUSIVEGATEWAY_EXCEPTION(3106,"网关异常"),
	FILE_FOEMAT_ERROR(4100, "文件格式错误！"),
	FILE_UPLOAD_ERROR(4101, "文件上传失败！");
	
	private Integer returnCode;
	private String returnMsg;

	ReturnCode(Integer returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
