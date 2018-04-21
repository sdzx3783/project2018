package com.hotent.platform.webservice.impl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.impl.BaseRuelt;
import com.hotent.platform.webservice.util.ReturnCode;
import com.hotent.platform.webservice.util.WebUtil;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class OutFaultInterceptor extends AbstractPhaseInterceptor<Message> {
    
	private static final Logger logger = LoggerFactory.getLogger("OutFaultInterceptor"); 
	
	private static final List<String> DEFAULT_TYPES = 
	        Arrays.asList(new String[]{"multipart/related", 
	                                   "multipart/mixed",
	                                   "multipart/alternative",
	                                   "multipart/form-data"});
	
    public OutFaultInterceptor() {
        super(Phase.PRE_STREAM);

    }

    public void handleMessage(Message message) throws Fault {
        Fault fault = (Fault)message.getContent(Exception.class);
        Throwable ex = fault.getCause();
        
        HttpServletResponse response = (HttpServletResponse)message.getExchange().getInMessage()
                .get(AbstractHTTPDestination.HTTP_RESPONSE);
        JSONObject jsonResp = new JSONObject();
        BaseRuelt resp = null;
        //捕获拦截器中自定义异常
        if (ex instanceof ApiExcetpion) {                   
        	jsonResp=((ApiExcetpion) ex).getResultMap();
        }else{
        	logger.error("拦截器异常：" + ex.getMessage());
        	resp = new BaseRuelt();
        	resp.setResultMap(null, ReturnCode.SYSTEM_ERROR);
        	jsonResp=resp.getResultMap();
        }
        
        Exchange exchange = message.getExchange();
        Message request = exchange.getInMessage();
        //判断输出格式
        String contentType = (String) request.get(Message.CONTENT_TYPE);  
        String queryString = null;
        String type = null;
        if (request.get(org.apache.cxf.message.Message.QUERY_STRING) != null) {
        	if (contentType!=null && DEFAULT_TYPES.contains(contentType.split(";")[0])) {
            	try {
    				queryString = URLDecoder.decode((String) request.get(org.apache.cxf.message.Message.QUERY_STRING),"UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				logger.error("url解码错误",ex);
    				jsonResp.put("return_msg", "url解码错误");
    			}
            }else
        	queryString = (String) request.get(org.apache.cxf.message.Message.QUERY_STRING);
            Map<String,String> params = WebUtil.paramToMap(queryString);
            type = params.get("_type");
		}
                
        //输出       
        try {
        	byte[] outByte = null;
        	if (type==null || type.equals("json")) {
        		outByte= jsonResp.toString().getBytes("utf8");	
        		response.setContentType("application/json;charset=UTF-8");
        		response.setHeader("Content-Encoding", "gzip");
			}else{
		/*		JSON json = JSONSerializer.toJSON(jsonResp.toString()); */
				XMLSerializer xmlSerializer = new XMLSerializer();
				xmlSerializer.setRootName("data");
				outByte=xmlSerializer.write(jsonResp).getBytes("utf8");
			}        	
        	//判断是否压缩(异常类 data都是 null 暂时不需要压缩了)
        	boolean useGzip = false;
        	Map<String, List<String>> requestHeaders = CastUtils.cast((Map<?, ?>)request
        			.get(Message.PROTOCOL_HEADERS));
        	if (requestHeaders != null) {
        		List<String> acceptEncodingHeader = CastUtils.cast(HttpHeaderHelper
        				.getHeader(requestHeaders, HttpHeaderHelper.ACCEPT_ENCODING));  
        		if (acceptEncodingHeader != null && acceptEncodingHeader.size()>0 && acceptEncodingHeader.get(0).contains("gzip")) {            	
        			useGzip = true;
        		}
        	}
	        if (useGzip && outByte != null) {
	    		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    		GZIPOutputStream gzip = new GZIPOutputStream(out);
	    		gzip.write(outByte);
	    		gzip.close();
	    		outByte = out.toByteArray();
			}
	        response.getOutputStream().write(outByte);		          		       
	        response.getOutputStream().flush();
        } catch (IOException iex) {
            // ignore
        }
        //日志
        logger.info("错误返回："+jsonResp.toString());
        request.get(org.apache.cxf.message.Message.REQUEST_URI);
        logger.error("请求uri："+request.get(Message.REQUEST_URI)+"?"+queryString+",请求方式："+Message.HTTP_REQUEST_METHOD);	
        message.getInterceptorChain().abort();
    }

}
