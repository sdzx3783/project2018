/**   
 * @Title: BaseAction.java 
 * @Package net.makshi.site.action 
 * @Description: TODO
 * @author Sherwin  
 * @date 2012-5-17 下午7:51:39 
 * @version V1.0
 * @Copyright (c)2012  MaiShi technology Co.Ltd. 
 */
package com.hotent.platform.webservice.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: BaseAction
 * @Description: TODO
 */
public class BaseRuelt {
	
	public JSONObject resultMap = new JSONObject();
	
	public HttpServletRequest request = RequestContextHolder.getRequestAttributes() == null ? null
			: ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	
	public JSONObject getResultMap() {
		return resultMap;
	}
	
	/**
	 * return the json data
	 * 
	 * @param data
	 *            the return data
	 * @param returnCode
	 *            the enum of the return code
	 */
	public void setResultMap(Object data, ReturnCode returnCode) {
		setResultMap(data, returnCode.getReturnCode(), returnCode.getReturnMsg());
	}

	/**
	 * return the json data
	 * 
	 * @param data
	 *            the return data
	 * @param returnCode
	 *            the enum of the return code
	 */
	public void setResultMap(Object data, Integer returnCode, String returnMsg) {
	/*	boolean useGzip = false;
		String acceptEncodingHeader = request.getHeader("Accept-Encoding");
		if (acceptEncodingHeader != null && acceptEncodingHeader.contains("gzip")) {
			useGzip = true;
		}

		// 本地不用压缩
		if ("dev".equals(System.getenv("RUN_ENV"))) {
			useGzip = false;
		}
		byte[] outByte = null;
		if (useGzip && data != null && returnCode.equals(ReturnCode.SUCCESS)) {
			 
			try {
				if (data instanceof List) {
					outByte = JSONArray.fromObject(data).toString().getBytes("utf8");
				} else {
					outByte = JSONObject.fromObject(data).toString().getBytes("utf8");
				}
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				GZIPOutputStream gzip = new GZIPOutputStream(out);
				gzip.write(outByte);
				gzip.close();
				outByte = out.toByteArray();
				resultMap.put("data", outByte);				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else{
		}*/
		resultMap.put("data", data);
		resultMap.put("return_code", returnCode);
		resultMap.put("return_msg", returnMsg);
	}
	
}
