package com.hotent.makshi.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.makshi.service.sms.SmsMsgRecordReadyService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.msgutil.SmsUtil;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.util.PropUtils;

/**
 * Created by anthony on 2017/10/31.
 * <p>
 * 短信提醒待办事项
 */
public class SmsRemindToDoJob implements Job {

	private static final Logger log = Logger.getLogger(SmsRemindToDoJob.class);
	// 一发发送100条短信内容
	private static final int PAGE_SIZE = 100;
	private static final String SMS_INFO_1 = "，截止到当前时间";
	private static final String SMS_INFO_2 = "，您在OA系统有";
	private static final String SMS_INFO_3 = "条待办任务，请及时处理。";

	/**
	 * 定时任务具体实现逻辑
	 *
	 * @param jobExecutionContext
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		sendSms();
	}

	private int getPages(int total, int pageSize) {
		int pages = total / pageSize;
		if (total % pageSize != 0) {
			pages += 1;
		}
		return pages;
	}

	private void sendSms() {
		SysUserService sysUserService = AppUtil.getBean(SysUserService.class);
		int count = sysUserService.countUsers4SMS();
		if (count == 0)
			return;
		SmsMsgRecordReadyService smsMsgRecordReadyService = AppUtil.getBean(SmsMsgRecordReadyService.class);
		BpmService bpmService = AppUtil.getBean(BpmService.class);
		log.info("begin to send SMS 4 undone job. Count =" + count);
		DateFormat dataFormat = new SimpleDateFormat(SmsUtil.TIME_PATTERN);
		String dateTime = dataFormat.format(new Date());
		for (int i = 1; i <= getPages(count, PAGE_SIZE); i++) {
			Map<String, Integer> params = new HashMap<String, Integer>();
			params.put("indexStart", (i - 1) * PAGE_SIZE);
			params.put("pageSize", PAGE_SIZE);
			List<SysUser> users = sysUserService.getUsers4SMSbyPage(params);
			if (CollectionUtils.isEmpty(users))
				return;
			for (SysUser user : users) {
				// 计算用户待办事项
				int taskCount = bpmService.getMyUndoneTasksCount(user.getUserId());
				if (taskCount > 0) {
					log.info("userid=" + user.getUserId() + ";undone=" + taskCount);
					String smsContent = user.getFullname() + SMS_INFO_1 + dateTime + SMS_INFO_2 + taskCount + SMS_INFO_3;
					smsMsgRecordReadyService.add(user.getUserId(), user.getFullname(), user.getMobile(), smsContent);
				}
			}

		}
	}
}
