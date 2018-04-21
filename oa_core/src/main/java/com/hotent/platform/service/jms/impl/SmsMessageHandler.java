package com.hotent.platform.service.jms.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.model.MessageModel;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.MessageEngine;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.util.MessageUtil;
import com.hotent.platform.service.util.ServiceUtil;

public class SmsMessageHandler implements IMessageHandler {

	private final Log logger = LogFactory.getLog(SmsMessageHandler.class);
	@Resource
	private MessageEngine messageEngine;
	@Resource
	SysTemplateService sysTemplateService;

	@Override
	public String getTitle() {
		return "短信";
		//return ContextUtil.getMessages("message.sms");
	}

	@Override
	public void handMessage(MessageModel model) {
		String strMobile = "";
		if (model.getReceiveUser() != null)
			strMobile =((SysUser) model.getReceiveUser()).getMobile();
		//手机号为空或不是手机号，直接返回
		if (StringUtil.isEmpty(strMobile) || !StringUtil.isMobile(strMobile)) return;
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(strMobile);
		messageEngine.sendSms(mobiles, MessageUtil.getContent(model,false));
		logger.debug("Sms");
	}

	

	@Override
	public boolean getIsDefaultChecked() {
		return false;
	}

}
