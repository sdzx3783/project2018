package com.hotent.makshi.webservice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.hotent.core.page.PageBean;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;

public class BaseService {
	private final Logger log = Logger.getLogger(this.getClass());
	public JSONObject resultMap = new JSONObject();
	@Context
	public HttpServletRequest request;

	/**
	 * return the json data
	 * 
	 * @param data the return data
	 * @param returnCode the enum of the return code
	 */
	public void setResultMap(Object data, ReturnCode returnCode) {
		setResultMap(data, returnCode.getReturnCode(), returnCode.getReturnMsg());
	}

	public void setResultMap(Object data, ReturnCode returnCode, JsonConfig jsonConfig) {
		setResultMap(data, returnCode.getReturnCode(), returnCode.getReturnMsg(), jsonConfig);
	}

	/**
	 * return the json data
	 * 
	 * @param data the return data
	 * @param returnCode the enum of the return code
	 */
	public void setResultMap(Object data, Integer returnCode, String returnMsg) {
		boolean useGzip = false;
		String acceptEncodingHeader = request.getHeader("Accept-Encoding");
		if (acceptEncodingHeader != null && acceptEncodingHeader.contains("gzip")) {
			useGzip = true;
		}

		/*
		 * if ("dev".equals(System.getenv("RUN_ENV"))) { useGzip = false; }
		 */
		String dataStr = "";
		if (data != null) {
			try {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "createBy", "updateBy", "createtime", "updatetime" });
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
					@Override
					public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
						if (value instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							return sdf.format(value);
						}
						return value;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return arg0;
					}
				});
				jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class type) {
						return null;
					}
				});
				jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class type) {
						return null;
					}
				});
				jsonConfig.registerDefaultValueProcessor(Double.class, new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class type) {
						return null;
					}
				});
				if (data instanceof List) {
					dataStr = JSONArray.fromObject(data, jsonConfig).toString();
				} else if (data instanceof String) {
					dataStr = (String) data;
				} else {
					dataStr = JSONObject.fromObject(data, jsonConfig).toString();
				}
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
		resultMap.put("data", dataStr);
		resultMap.put("return_code", returnCode);
		resultMap.put("return_msg", returnMsg);
	}

	public void setResultMap(Object data, Integer returnCode, String returnMsg, JsonConfig jsonConfig) {
		boolean useGzip = false;
		String acceptEncodingHeader = request.getHeader("Accept-Encoding");
		if (acceptEncodingHeader != null && acceptEncodingHeader.contains("gzip")) {
			useGzip = true;
		}
		String dataStr = "";
		if (data != null) {
			try {
				if (data instanceof List) {
					dataStr = JSONArray.fromObject(data, jsonConfig).toString();
				} else if (data instanceof String) {
					dataStr = (String) data;
				} else {
					dataStr = JSONObject.fromObject(data, jsonConfig).toString();
				}
			} catch (Exception e) {
				log.error("错误信息", e);
			}
		}
		resultMap.put("data", dataStr);
		resultMap.put("return_code", returnCode);
		resultMap.put("return_msg", returnMsg);
	}

	public void configPageBean(QueryFilter queryFilter) {
		PageBean pageBean = queryFilter.getPageBean();
		int pageNo = RequestUtil.getInt(request, "pageNo", 1);
		int pageSize = RequestUtil.getInt(request, "pageSize", 15);
		pageBean.setCurrentPage(pageNo);
		pageBean.setPagesize(pageSize);
	}
}
