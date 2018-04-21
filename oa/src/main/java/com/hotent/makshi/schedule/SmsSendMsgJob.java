package com.hotent.makshi.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.model.sms.SmsMsgRecordReady;
import com.hotent.makshi.model.sms.SmsMsgRecordSended;
import com.hotent.makshi.service.sms.SmsMsgRecordReadyService;
import com.hotent.makshi.service.sms.SmsMsgRecordSendedService;
import com.hotent.makshi.service.sms.SmsSendService;
import com.hotent.makshi.utils.ValidateUtils;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.WebEnvUtil;

public class SmsSendMsgJob implements Job {
	private final Logger log = Logger.getLogger(this.getClass());

	
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		String propertyByDirKey="test";
		propertyByDirKey=PropUtils.getPropertyByDirKeyWithDependByEnv("sms.env.type", "application");
		// 测试环境不下发,如果要下发，请看看数据库有多少条数据，防止下发很多垃圾短信
		if (StringUtils.equals(propertyByDirKey, "test"))
			return;
		try {
			SmsMsgRecordReadyService smsMsgRecordReadyService = AppUtil.getBean(SmsMsgRecordReadyService.class);
			SmsSendService smsSendService = AppUtil.getBean(SmsSendService.class);
			List<SmsMsgRecordReady> smss = smsMsgRecordReadyService.getRecords(50);
			if (CollectionUtils.isEmpty(smss))
				return;
			for (SmsMsgRecordReady sms : smss) {
				if (StringUtils.isEmpty(sms.getMobile()) || StringUtils.isEmpty(sms.getContent()) || (!ValidateUtils.Mobile(sms.getMobile()))) {
					smsMsgRecordReadyService.delById(sms.getId());
					continue;
				}
				boolean result = smsSendService.sendMsg(sms.getMobile(), sms.getContent());
				int i = 1;
				while (!result && i < 3) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						log.error("错误信息", e);
					}
					result = smsSendService.sendMsg(sms.getMobile(), sms.getContent());
					i++;
				}
				smsMsgRecordReadyService.delById(sms.getId());
				addSendRecord(sms, (result ? 1 : 0));
			}
		} catch (Exception e) {
			log.error("错误信息", e);
		} catch (Error e) {
		} catch (Throwable e) {
		}
	}

	private void addSendRecord(SmsMsgRecordReady sms, int status) {
		SmsMsgRecordSendedService smsMsgRecordSendedService = AppUtil.getBean(SmsMsgRecordSendedService.class);
		SmsMsgRecordSended re = new SmsMsgRecordSended();
		re.setId(UniqueIdUtil.genId());
		re.setContent(sms.getContent());
		re.setUserid(sms.getUserid());
		re.setUsername(sms.getUsername());
		re.setMobile(sms.getMobile());
		re.setStatus(status);
		smsMsgRecordSendedService.add(re);
	}

}
