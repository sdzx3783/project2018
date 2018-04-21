/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.controller.finance.WorkHourApplicationController;
import com.hotent.makshi.dao.finance.CustomTaskHourDao;
import com.hotent.makshi.dao.finance.ProjectTaskHourDao;
import com.hotent.makshi.model.finance.CustomTaskHour;
import com.hotent.makshi.model.finance.ProjectTaskHour;
import com.hotent.makshi.model.finance.WorkHourApplication;
import com.hotent.makshi.model.finance.WorkHourException;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.service.finance.WorkHourApplicationService;
import com.hotent.makshi.service.worksheet.WorkCommonService;
import com.hotent.makshi.service.worksheet.WorkLeaveService;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;

/**
 * @author sammy
 *  
 */
@Component("workHourFetcher")
public class WorkHourFetcher implements IFetcher {
	@Resource
	private WorkHourApplicationService workService;
	@Resource
	private ProjectTaskHourDao proHourDao;
	@Resource
	private CustomTaskHourDao cusHourDao;
	@Override
	public void fetcheData(String tableName, String businessKey) throws Exception {
		WorkHourApplication workHourApplication = workService.getById(Long.valueOf(businessKey));
		if(workHourApplication!=null){
			Double d=0d;
			Date applicant_time = workHourApplication.getApplicant_time();
			Integer basehour = workHourApplication.getBasehour();
			if(basehour==null){
				workHourApplication.setBasehour(7);//默认为7
			}
			Integer totalrate=0;
			NumberFormat ddf=NumberFormat.getNumberInstance() ;
			ddf.setMaximumFractionDigits(2);
			List<ProjectTaskHour> projectTaskHourList = proHourDao.getByMainId(workHourApplication.getId());
			if(BeanUtils.isNotEmpty(projectTaskHourList)){
				for (ProjectTaskHour projectTaskHour : projectTaskHourList) {
					Integer project_work_rate = projectTaskHour.getProject_work_rate();
					if(project_work_rate==null){
						project_work_rate=0;
						projectTaskHour.setProject_work_rate(0);
					}
					totalrate+=project_work_rate;
					Double calWorkHourByRate =WorkHourApplicationController.calWorkHourByRate(project_work_rate,applicant_time,basehour);
					projectTaskHour.setWork_hour(calWorkHourByRate.toString());
					d+=calWorkHourByRate;
					proHourDao.update(projectTaskHour);
				}
			}
			
			List<CustomTaskHour> customTaskHourList= cusHourDao.getByMainId(workHourApplication.getId());
			if(BeanUtils.isNotEmpty(customTaskHourList)){
				for(CustomTaskHour customTaskHour:customTaskHourList){
					Integer task_work_rate = customTaskHour.getTask_work_rate();
					if(task_work_rate==null){
						task_work_rate=0;
						customTaskHour.setTask_work_rate(0);
					}
					totalrate+=task_work_rate;
					Double calWorkHourByRate = WorkHourApplicationController.calWorkHourByRate(task_work_rate,applicant_time,basehour);
					customTaskHour.setWork_hour(calWorkHourByRate.toString());
					d+=calWorkHourByRate;
					cusHourDao.update(customTaskHour);
				}
			}
			workHourApplication.setWork_hour_rate(totalrate);
			workHourApplication.setWork_hour_total(d.toString());
			workService.update(workHourApplication);
		}
	}
	
}
