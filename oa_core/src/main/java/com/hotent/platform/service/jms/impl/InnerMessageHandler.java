package com.hotent.platform.service.jms.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.model.MessageModel;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.service.AppInnerMessageInterface;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.service.system.MessageEngine;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.util.MessageUtil;

public class InnerMessageHandler implements IMessageHandler {

	private final Log logger = LogFactory.getLog(InnerMessageHandler.class);
	@Resource
	private MessageEngine messageEngine;
	@Resource
	SysTemplateService sysTemplateService;
	@Resource
	private AppInnerMessageInterface appInnerMessageService;
	
	@Override
	public String getTitle() {
		return " 站内消息";
	}

	@Override
	public void handMessage(MessageModel model) {
		MessageSend message = new MessageSend();
		if (model.getReceiveUser() == null || model.getSendUser() == null) return;
		message.setId(UniqueIdUtil.genId());
		message.setUserName(MessageUtil.getSendUserName(model));// 发送人姓名
		message.setUserId(model.getSendUser().getUserId());// 发送人ID
		message.setSendTime(model.getSendDate());
		message.setMessageType(MessageSend.MESSAGETYPE_FLOWTASK);
		message.setContent(MessageUtil.getContent(model,true));
		String contextText = MessageUtil.getContentText(model,false);
		message.setContentText(contextText);
		message.setSubject(MessageUtil.getSubject(model));
		message.setCreateBy(model.getSendUser().getUserId());// 创建人ID
		message.setRid(model.getReceiveUser().getUserId());// 接收人ID
		message.setReceiverName(model.getReceiveUser().getFullname());// 接收人姓名
		message.setCreatetime(model.getSendDate() == null ? new Date() : model.getSendDate());
		messageEngine.sendInnerMessage(message);
		//添加 app站内消息推送 added by martic
		appInnerMessageService.addMyMessage("0",null,model.getReceiveUser().getUserId(),null,null,contextText,"{\"url\":\"oaapp_messagelist:\"}","新的站内消息","站内消息");
		logger.debug("InnerMessage");
	}

	@Override
	public boolean getIsDefaultChecked() {
		return true;
	}

}
