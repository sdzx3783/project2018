package com.hotent.makshi.service.sms;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.makshi.utils.ValidateUtils;
import com.hotent.platform.model.system.MessageLog;
import com.hotent.platform.msgutil.SmsProxy;
import com.hotent.platform.service.system.MessageLogService;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.WebEnvUtil;

@Service("smsSendService")
public class SmsSendService {

	private static final Logger log = Logger.getLogger(SmsSendService.class);
	private static String SMS_ENV_TYPE = "";
	private static String SMS_ENV_TEST_REC = "";
	private static String RUN_ENV="";
	
	@Autowired
	private MessageLogService messageLogService;
	@Autowired
	private SmsProxy smsProxy;

	@PostConstruct
	private void init() {
		log.info("smsutil init param!");
		SMS_ENV_TYPE=PropUtils.getPropertyByDirKeyWithDependByEnv("sms.env.type", "application");
		SMS_ENV_TEST_REC=PropUtils.getPropertyByDirKeyWithDependByEnv("sms.env.test.rec", "application");
	}

	private String getMsgBody(String orgaddr, String mobile, String content, String sendTime, String needreport) {
		String messgeTag = (new StringBuilder("<message><orgaddr>")).append(orgaddr).append("</orgaddr>").append("<mobile>").append(mobile).append("</mobile>").append("<content>").append(content)
				.append("</content>").append("<sendtime>").append(sendTime).append("</sendtime>").append("<needreport>").append(needreport).append("</needreport>").append("</message>").toString();
		return messgeTag;
	}

	private String getSendBody(String msgBodys) {
		return (new StringBuilder("<sendbody>")).append(msgBodys).append("<publicContent></publicContent></sendbody>").toString();
	}

	/**
	 * <?xml version=\"1.0\" encoding=\"GBK\" ?><response><head><code>integer</code><message>string</message></head><body> <msgid>destaddr,msgid</msgid><reserve>string</reserve></body></response>
	 * 返回0失败，其它正常
	 * 
	 * @param responseXml
	 * @return
	 */
	private String getMsgIdByXMLStr(String responseXml) {
		String result = "0";
		if (StringUtils.isEmpty(responseXml))
			return result;
		try {
			Document doc = DocumentHelper.parseText(responseXml);
			Element node = doc.getRootElement();
			Element element = node.element("body");
			String msgid = element.element("msgid").getTextTrim();
			if (StringUtils.isEmpty(msgid) || msgid.indexOf(",") < 0)
				return result;
			return msgid.split(",")[1];
		} catch (DocumentException e) {
			log.error("错误信息",e);
			return result;
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param mobile 手机号码
	 * @param smsContent 短信内容
	 * @return true 成功 false 失败
	 */
	public boolean sendMsg(String mobile, String smsContent) {
		if (!"test".equals(SMS_ENV_TYPE) && !"official".equals(SMS_ENV_TYPE)) {
			log.error("send sms not valid type!");
			return false;
		}
		if ("test".equals(SMS_ENV_TYPE)) {
			mobile = SMS_ENV_TEST_REC;
		}
		try {
			String responseXml = smsProxy.insertDownSms("AjEDZl00Uj4=", "AloDKV12Un5XFFBnUz5XZFw5", "3", getSendBody(getMsgBody("", mobile, smsContent, "", "1")));
			log.info("发送短信返回报文：" + responseXml);
			if (StringUtils.isEmpty(responseXml))
				return false;
			String msgId = getMsgIdByXMLStr(responseXml);
			int SMS_STATUS = 1;
			if ("0".equals(msgId) || msgId.contains("-"))
				SMS_STATUS = 0;
			messageLogService.addSMSLog(smsContent, mobile, MessageLog.MOBILE_TYPE, SMS_STATUS, msgId, Integer.valueOf(-10));
			return SMS_STATUS == 1;
		} catch (RemoteException e) {
			log.error("错误信息",e);
			log.error("sms call send interface error.mobile=" + mobile + ";content=" + smsContent);
			log.error(e.getMessage());
			messageLogService.addSMSLog(smsContent, mobile, MessageLog.MOBILE_TYPE, 0, "0", Integer.valueOf(-10));
			return false;
		}
	}

}
