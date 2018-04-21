package com.hotent.platform.webservice.impl.util;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.hotent.core.api.util.ContextUtil;

/**
 * web service 后置监听器。
 * @author RAY
 *
 */
public class AfterInterceptor extends AbstractPhaseInterceptor<Message> {
	
	public AfterInterceptor(){
		super(Phase.SETUP);
	}

	@Override
	public void handleMessage(Message msg) throws Fault {
		ContextUtil.clearAll();
	}

}
