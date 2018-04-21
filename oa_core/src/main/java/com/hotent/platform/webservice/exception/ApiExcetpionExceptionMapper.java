package com.hotent.platform.webservice.exception;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ApiExcetpionExceptionMapper implements ExceptionMapper<ApiExcetpion> {
	
	public static Logger logger = LoggerFactory.getLogger("ApiExcetpionExceptionMapper");
	
    public Response toResponse(ApiExcetpion ex) {
        StackTraceElement[] trace = new StackTraceElement[1];
        trace[0] = ex.getStackTrace()[0];
        ex.setStackTrace(trace);
//        System.out.println("处理自定义的异常");
        logger.error("全局异常  返回结果："+ex.resultMap.toString());
        ResponseBuilder rb = Response.status(Response.Status.OK);
        rb.type("application/json;charset=UTF-8");
        rb.entity(ex.resultMap.toString());
        rb.language(Locale.SIMPLIFIED_CHINESE);
        Response r = rb.build();
        return r;
    }

}