package com.hotent.makshi.schedule;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.makshi.service.operation.OverAndAdjustService;
import com.hotent.makshi.service.operation.OverTimeAndAdjustService;

public class OverTimeAndAdjustJob implements Job{
	private static final Logger log=Logger.getLogger(OverTimeAndAdjustJob.class);
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		log.info("清除"+year+"年加班调休开始：");
		OverAndAdjustService overAndAdjustService = AppUtil.getBean(OverAndAdjustService.class);
		OverTimeAndAdjustService overTimeAndAdjustService = AppUtil.getBean(OverTimeAndAdjustService.class);
		overTimeAndAdjustService.cleanInfo();
		overAndAdjustService.cleanInfo();
		log.info("清除"+year+"年加班调休结束");
	}
	
}
