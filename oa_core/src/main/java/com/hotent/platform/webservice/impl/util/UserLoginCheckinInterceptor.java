package com.hotent.platform.webservice.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.util.ReturnCode;
import com.hotent.platform.webservice.util.UserLoginUtil;
import com.hotent.platform.webservice.util.WebUtil;


public class UserLoginCheckinInterceptor extends AbstractPhaseInterceptor<Message>{

	public UserLoginCheckinInterceptor() {
		super(Phase.RECEIVE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleMessage(Message  message) throws Fault {
//		String methodName= getMethod(message);
        //排除拦截的方法
        List<String> list = new ArrayList<String>();
        list.add("/service/UserLogin/login");
//        list.add("forgotPassword");
        if(!list.contains((String) message.get(XMLMessage.REQUEST_URI))) {
            //根据请求方式区分取得GET和POST的参数
//            if (message.get(message.HTTP_REQUEST_METHOD).equals("GET")) {
                //get参数，=&格式
                String reqParams = (String) message.get(message.QUERY_STRING);
              //  System.out.println("请求的参数：" + reqParams);
                //注:toMap为自定义方法,实现将String转成map
//                reqParamsMap = StringUtils.toMap(reqParams, "&");
                Map<String, String> params = WebUtil.paramToMap(reqParams);
                String account = params.get("account");
        		String accessToken = params.get("accessToken");

        		if (!UserLoginUtil.checkLogin(account, accessToken)) {
        			throw new ApiExcetpion(ReturnCode.NOT_LOGIN);
        		}
            /*} else if (message.get(message.HTTP_REQUEST_METHOD).equals("POST")) {
				
				}
                
            }*/
        }
	}
	private String getMethod(Message message) {
        //通过分析webservice的uri取得实际执行的方法,该webservice使用cxf的RESTFul形式发布
        String requestUri = (String) message.get(XMLMessage.REQUEST_URI);
        String[] methods = StringUtils.split(requestUri,"/");
    //    System.out.println("********method name:" + requestUri);
        return methods!=null && methods.length>0?methods[methods.length-1]:"";
    }

}
