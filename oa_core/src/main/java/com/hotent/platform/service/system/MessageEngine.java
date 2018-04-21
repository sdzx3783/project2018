package com.hotent.platform.service.system;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hotent.core.mail.MailUtil;
import com.hotent.core.mail.model.Mail;
import com.hotent.core.mail.model.MailSeting;
import com.hotent.core.sms.IShortMessage;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.model.system.MessageLog;
import com.hotent.platform.model.system.MessageSend;

/**
 * 消息引擎
 * 
 * @author ht
 * 
 */
public class MessageEngine {

	private final Log logger = LogFactory.getLog(MessageEngine.class);
	private MailUtil mailUtil;
	
	private IShortMessage shortMessage;

	

	public void setMailUtil(MailUtil mailSender) {
		this.mailUtil = mailSender;
	}

	/**
	 * 发送邮件
	 * 
	 * @param msg
	 *            邮件对象
	 * @param ifHtml
	 *            是否为html内容
	 */
	public void sendMail(Mail mail) {
		Integer state = MessageLog.STATE_SUCCESS;
		try {
			MailSeting mailSetting=AppUtil.getBean(MailSeting.class);
			
			mail.setSenderAddress(mailSetting.getMailAddress());
			mail.setSenderName(mailSetting.getNickName());
			mail.setSendDate(new Date());
			
			mailUtil.send(mail);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			state = MessageLog.STATE_FAIL;
		}
		// 保存发送消息日志
		MessageLogService messageLogService = (MessageLogService) AppUtil
				.getBean(MessageLogService.class);
		messageLogService
				.addMessageLog(mail.getSubject(),mail.getReceiverAddresses(),
						MessageLog.MAIL_TYPE, state);
	}

	/**
	 * 发送手机短信
	 * 
	 * @param mobiles
	 * @param content
	 */
	public void sendSms(List<String> mobiles, String content) {
		Integer state = MessageLog.STATE_SUCCESS;
		try {
			if (this.shortMessage == null)
				this.shortMessage = (IShortMessage) AppUtil
						.getBean(IShortMessage.class);
			boolean result = shortMessage.sendSms(mobiles, content);
			state = result ? MessageLog.STATE_SUCCESS : MessageLog.STATE_FAIL;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			state = MessageLog.STATE_FAIL;
		}
		// 保存发送消息日志
		MessageLogService messageLogService = (MessageLogService) AppUtil
				.getBean(MessageLogService.class);
		messageLogService.addMessageLog(content,
				StringUtils.join(mobiles, ","), MessageLog.MOBILE_TYPE, state);
	}

	/**
	 * 发送内部消息
	 * 
	 * @param messageSend
	 */
	public void sendInnerMessage(MessageSend messageSend) {
		Integer state = MessageLog.STATE_SUCCESS;
		try {
			MessageSendService messageSendService = (MessageSendService) AppUtil
					.getBean(MessageSendService.class);
			messageSendService.addMessageSend(messageSend);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			state = MessageLog.STATE_FAIL;
		}
		// 保存发送消息日志
		MessageLogService messageLogService = (MessageLogService) AppUtil
				.getBean(MessageLogService.class);
		messageLogService.addMessageLog(messageSend.getSubject(),
				messageSend.getReceiverName(), MessageLog.INNER_TYPE, state);
	}

}
