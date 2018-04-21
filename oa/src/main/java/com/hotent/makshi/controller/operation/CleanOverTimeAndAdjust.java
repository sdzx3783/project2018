package com.hotent.makshi.controller.operation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.makshi.service.operation.OverAndAdjustService;
import com.hotent.makshi.service.operation.OverTimeAndAdjustService;

public class CleanOverTimeAndAdjust implements Job{

	@Resource
	public OverTimeAndAdjustService overTimeAndAdjustService;
	@Resource
	public OverAndAdjustService overAndAdjustService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		/*overTimeAndAdjustService.cleanInfo();
		overAndAdjustService.cleanInfo();*/
	}
	
}