package com.hotent.platform.service.jms.impl;

import javax.annotation.Resource;

import com.hotent.core.jms.IJmsHandler;
import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.jms.MessageHandlerContainer;
import com.hotent.core.model.MessageModel;

public class MessageHandler implements IJmsHandler {
	
	@Resource
	MessageHandlerContainer messageHandlerContainer;

	@Override
	public void handMessage(Object model) {
		MessageModel msgModel=(MessageModel)model;
		String type=msgModel.getInformType();
		IMessageHandler handler= messageHandlerContainer.getHandler(type);
		handler.handMessage(msgModel);

	}

}
