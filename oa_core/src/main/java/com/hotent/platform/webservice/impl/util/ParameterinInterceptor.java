package com.hotent.platform.webservice.impl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.util.AuthenticationCore;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.ReturnCode;
import com.hotent.platform.webservice.util.WebUtil;

public class ParameterinInterceptor extends AbstractPhaseInterceptor<Message> {

	public ParameterinInterceptor() {
		super(Phase.RECEIVE);
		// TODO Auto-generated constructor stub
	}
	private final String openAndroid="larva_android";
	private final String openIos="larva_ios";
	private static final Logger logger = LoggerFactory.getLogger(ParameterinInterceptor.class);

	private static final String[] whiteUrl = {};
	private static final String appKeyAndroid = PropUtils.getProperty("app_key_android", "api");
	private static final String appKeyIOS = PropUtils.getProperty("app_key_ios", "api");

//	private static final String[] invalidChar = { ">", "<", "\'", "\"", "\\", "%", ";" };
	private static final String[] invalidChar = { ">", "<", "\'", "%", ";" };

	private static final List<String> DEFAULT_TYPES = Arrays.asList(
			new String[] { "multipart/related", "multipart/mixed", "multipart/alternative", "multipart/form-data" });
	
	@Override
	public void handleMessage(Message message) throws Fault{
		String reqParams = "";
		if (message.get(message.HTTP_REQUEST_METHOD).equals("GET")) {
			reqParams = (String) message.get(message.QUERY_STRING);
		}else if (message.get(message.HTTP_REQUEST_METHOD).equals("POST")) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String contentType = (String) message.get(Message.CONTENT_TYPE);
			StringBuffer sb = new StringBuffer();
			ServletInputStream is = null;
			if (contentType != null && DEFAULT_TYPES.contains(contentType.split(";")[0])) {
				// TODO 如果以后文件上传也经过这里 那这里要做处理（暂时文件上传走的是oa的SysFileController方法）
			} else if (contentType != null && contentType.equals("application/x-www-form-urlencoded")) {
				String ver = request.getParameter("ver");
				if(StringUtils.isBlank(ver)){
					ver = "0";
				}
				Map<String, String[]> requestMap = request.getParameterMap();
				for (Entry<String, String[]> entry : requestMap.entrySet()) {
					reqParams += "&" + entry.getKey() + "=" + entry.getValue()[0];
				}
				reqParams = reqParams.indexOf("&")==0?reqParams.substring(1):reqParams;
			} else {
				try {
					is = request.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String s = "";
					while ((s = br.readLine()) != null) {
						sb.append(s);
					}
					reqParams = sb.toString();
					// result = verify(reqParams);
				} catch (IOException e) {
					logger.error("参数错误！");
					throw new ApiExcetpion(ReturnCode.PRAMS_ERROR);
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							logger.error("关闭流失败！" + e);
						}
					}

				}
			}
		}
        //根据请求方式区分取得GET和POST的参数
//        if (message.get(message.HTTP_REQUEST_METHOD).equals("GET")) {
            //get参数，=&格式
//            String reqParams = (String) message.get(message.QUERY_STRING);
			/*try {
				if(StringUtils.isNotEmpty(reqParams)){					
					reqParams = URLDecoder.decode(reqParams ,"UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ApiExcetpion(ReturnCode.SYSTEM_ERROR,e.getMessage());
			}*/
		//1 判断post请求是否是之前版本（android 版本号12 或者没有版本号  ios 版本号 1.0.4或者没有版本号）url地址传参 最新的pos请求都只用body传参  是的话用get的方式获取url
		Map<String, String> params = WebUtil.paramToMap(reqParams);
		String ver = params.get("ver");
		if(StringUtils.isBlank(ver)){
			ver = "0";
		}
		String openId = params.get("openid");
		int verInt = Integer.valueOf(ver.replaceAll("[^0-9]", ""));
		if((openAndroid.equals(openId)&&verInt<=12)||(openIos.equals(openId)&&verInt<=104)){
			reqParams = (String) message.get(message.QUERY_STRING);
		}
		message.put(message.QUERY_STRING, reqParams);
        logger.info("请求的参数：" + reqParams);
            //注:toMap为自定义方法,实现将String转成map
//            reqParamsMap = StringUtils.toMap(reqParams, "&");
//			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		logger.debug("请求uri：" + message.get(message.REQUEST_URL) + "?" + reqParams + ",请求方式：" + message.get(message.HTTP_REQUEST_METHOD));
        if(!verify(reqParams)){
        	logger.error("认证失败");
			throw new ApiExcetpion(ReturnCode.SIGN_ERROR);
		}
		if (xssEncode(reqParams,(String) message.get(XMLMessage.REQUEST_URI))) {
			logger.info("参数含有非法字符");
			throw new ApiExcetpion(ReturnCode.PRAMS_CONTAIN_ILLEGAL_CHARACTER);
		}
//    		message.remove(message.QUERY_STRING);
//    		message.put(message.QUERY_STRING, reqParams);
       /* } else if (message.get(message.HTTP_REQUEST_METHOD).equals("POST")) {
            
        }*/
	};

	/**
	 * 
	 * 验证消息是否是合法的请求
	 * 
	 * @param params
	 *            参数数组
	 * @return 验证结果
	 */
	private boolean verify(String queryString) {
		logger.debug("queryString=" + queryString);
		if (StringUtils.isEmpty(queryString))
			return false;
		Map<String, String> params = WebUtil.paramToMap(queryString);
		if (StringUtils.isEmpty(params.get("sign")) || StringUtils.isEmpty(params.get("sign_type"))) {
			logger.info("sign、sign_type 不能为空!sign:"+params.get("sign")+"sign_type:"+params.get("sign_type"));
			throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL,"sign、sign_type 不能为空");
		}
		String mysign = getMysign(params);
		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign");
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		String sWord = "ClinetSign=" + sign + "###OpenapiSign=" + mysign + "\n 访问参数："
				+ AuthenticationCore.createLinkString(params);
		logger.info("mysign=" + mysign);

		// 验证
		// mysign与sign不等，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 验证消息是否是合法的请求
	 * 
	 * @param params
	 *            参数数组
	 * @return 验证结果
	 */
	private boolean verify(Map<String, String> params) {
		String mysign = getMysign(params);
		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign");
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		String sWord = "ClinetSign=" + sign + "###OpenapiSign=" + mysign + "\n 访问参数："
				+ AuthenticationCore.createLinkString(params);

		// 验证
		// mysign与sign不等，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @return 生成的签名结果
	 */
	private String getMysign(Map<String, String> params) {
		Map<String, String> sParaNew = AuthenticationCore.paraFilter(params);// 过滤空值、sign与sign_type参数
		String openid = params.get("openid");
		if (openid == null || openid.length() <= 0) {
			logger.info("openid不存在或者非法 openid="+openid);
			throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL,"openid不存在或者非法");
		}
		if (!openAndroid.equalsIgnoreCase(openid) && !openIos.equalsIgnoreCase(openid)) {
			logger.info("openid不存在或者非法 openid="+openid);
			throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL,"openid不存在或者非法");
		}
		String appKey = "";
		if (openAndroid.equalsIgnoreCase(openid)) {
			appKey = appKeyAndroid;
		} else {
			appKey = appKeyIOS;
		}
		String mysign = AuthenticationCore.buildMysign(sParaNew, appKey);// 获得签名结果
		return mysign;
	}

	private Map<String, String> transValueType(Map<String, String[]> map) {
		Map<String, String> result = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>(map.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String[] value = map.get(key);
			result.put(key, value[0]);
		}
		return result;

	}

	private boolean xssEncode(String queryString, String requestUri) {
		if (!isFilter(requestUri)) {
			if (queryString == null || queryString.length() <= 0) {
				return false;
			}

			for (String s : invalidChar) {
				String encodeRequestUri = URLDecoder.decode(queryString);
				if (encodeRequestUri.indexOf(s) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isFilter(String url) {

		for (int i = 0; i < whiteUrl.length; i++) {
			String str = whiteUrl[i];
			if (url.indexOf(str) != -1) {
				return true;
			}
		}
		return false;
	}
	
}
