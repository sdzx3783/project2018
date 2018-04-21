package com.hotent.makshi.schedule;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

public class AnuualVacationInitJob implements Job{
	private static final Logger log=Logger.getLogger(AnuualVacationJob.class);
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		log.info("初始化"+year+"年剩余年假任务开始：");
		AnuualLeaveService anuualLeaveService = AppUtil.getBean(AnuualLeaveService.class);
		SysUserService sysUserService = AppUtil.getBean(SysUserService.class);
		List<SysUser> all = sysUserService.getAll();
		for (SysUser sysUser : all) {
			try {
				//初始化去年剩余年假
				anuualLeaveService.tranferAnuualVacation(sysUser.getUserId());
			} catch (Exception e) {
				log.error("初始化"+year+"年剩余年假任务结束：用户id为"+sysUser.getUserId()+"失败原因:"+e.getMessage());
			}
		}
		log.info("初始化"+year+"年剩余年假任务结束：");
	}
	
}
