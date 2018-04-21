package com.hotent.platform.service.jms.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.mail.model.Mail;
import com.hotent.core.model.MessageModel;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.NodeMsgTemplateService;
import com.hotent.platform.service.system.MessageEngine;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.util.MessageUtil;

public class MailMessageHandler implements IMessageHandler {

	private final Log logger = LogFactory.getLog(MailMessageHandler.class);
	@Resource
	private MessageEngine messageEngine;
	@Resource
	SysTemplateService sysTemplateService;
	@Resource
	NodeMsgTemplateService nodeMsgTemplateService;
	@Resource
	FreemarkEngine freemarkEngine;

	@Override
	public String getTitle() {
		return "邮件";
	}

	@Override
	public void handMessage(MessageModel model) {
		Mail mail=new Mail();
		String subject=MessageUtil.getSubject(model);
		String content=MessageUtil.getContent(model,true);
		mail.setSubject(subject);
		mail.setContent(content);
		
		String[] toAddress=model.getTo();
		String[] bcc=model.getBcc();
		String[] cc=model.getCc();

		if (model.getTo() != null && model.getTo().length > 0) {
			mail.setReceiverAddresses(StringUtils.join(toAddress, ","));
			if(BeanUtils.isNotEmpty(bcc))
				mail.setBcCAddresses(StringUtils.join(bcc, ","));
			if(BeanUtils.isNotEmpty(cc))
				mail.setCopyToAddresses(StringUtils.join(cc, ","));
		} else {
			String eamilStr = "";
			if (model.getReceiveUser() != null)
				eamilStr = ((SysUser) model.getReceiveUser()).getEmail();
			if (StringUtil.isEmpty(eamilStr) || !StringUtil.isEmail(eamilStr))
				return;// 判断一下邮箱是否为空或不是邮箱，则直接返回
			mail.setReceiverAddresses(eamilStr);
		}
		messageEngine.sendMail(mail);
		logger.debug("MailModel");
	}



	/**
	 * 默认不勾选邮件
	 */
	@Override
	public boolean getIsDefaultChecked() {
		return false;
	}

}
