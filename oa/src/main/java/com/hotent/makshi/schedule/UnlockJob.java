package com.hotent.makshi.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.makshi.service.finance.WorkHourApplicationService;

public class UnlockJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		WorkHourApplicationService bean = AppUtil.getBean(WorkHourApplicationService.class);
		bean.clearTimeOutLock();
	}
}
