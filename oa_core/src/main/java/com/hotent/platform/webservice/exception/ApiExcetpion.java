package com.hotent.platform.webservice.exception;

import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONObject;

public class ApiExcetpion extends AppRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5041051029622355702L;

	JSONObject resultMap = new JSONObject();

	public ApiExcetpion(ReturnCode returnCode) {
		super("");
		resultMap.put("data", null);
		resultMap.put("return_code", returnCode.getReturnCode());
		resultMap.put("return_msg", returnCode.getReturnMsg());
	}

	public ApiExcetpion(ReturnCode returnCode, String returnMsg) {
		super("");
		resultMap.put("data", null);
		resultMap.put("return_code", returnCode.getReturnCode());
		resultMap.put("return_msg", returnMsg);
	}

	public ApiExcetpion(Integer returnCode, String returnMsg) {
		super("");
		resultMap.put("data", null);
		resultMap.put("return_code", returnCode);
		resultMap.put("return_msg", returnMsg);
	}

	public ApiExcetpion(String msg) {
		super(msg);
	}

	public JSONObject getResultMap() {
		return resultMap;
	}

	public void setResultMap(JSONObject resultMap) {
		this.resultMap = resultMap;
	}
}
