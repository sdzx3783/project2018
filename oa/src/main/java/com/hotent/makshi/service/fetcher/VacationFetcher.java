/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.makshi.service.worksheet.WorkCommonService;
import com.hotent.makshi.service.worksheet.WorkLeaveService;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;

/**
 * @author codi
 *  已过时
 */
@Component("vacationFetcher")
public class VacationFetcher implements IFetcher {
	@Autowired
	private WorkLeaveService workLeaveService;
	
	@Autowired
	private WorkCommonService workCommonService;
	@Autowired
	private AnuualLeaveService anuualLeaveService;
	private static Logger logger=Logger.getLogger(VacationFetcher.class);
	
	@Resource
	private SysRoleService roleService;
	
	private String[] vacationArr={"年假","调休","事假","病假","产假","婚假","丧假"};
	

	/**
	 * 添加请假流程数据到缺勤表
	 */
	@Override
	public void fetcheData(String tableName, String businessKey) {
		logger.info("--------请假流程数据转移到业务表开始---------------");
		try{
			Long id=Long.parseLong(businessKey);
			//查询流程数据
			Map<String,Object> taskData=workLeaveService.getVacationById(id);
			
			WorkLeave workLeave=new WorkLeave();
			BeanUtils.copyProperties(workLeave, taskData);
			//触发年假计算事件
			if(StringUtils.isNotEmpty(workLeave.getFullnameID())){
				anuualLeaveService.calculateAnuualVacation(Long.valueOf(workLeave.getFullnameID()));
			}
			String leave_start=taskData.get("startTime").toString();
			String leave_end=taskData.get("endTime").toString();
			Integer levea_start_select=Integer.parseInt(taskData.get("leaveStartRange").toString());
			Integer levea_end_select=Integer.parseInt(taskData.get("leaveEndRange").toString());
			if(workLeave.getType()==null || workLeave.getType().equals("")){//类型为空
				logger.error("请假流程数据用户："+workLeave.getFullname()+",开始时间:"+leave_start+"结束时间："+leave_end+",开始时段："+levea_start_select+",结束时段："+levea_end_select+"的请假类型为空不能入库");
				return;
			}else{
				boolean isInType=false;
				for(String vType:vacationArr){
					if(vType.equals(workLeave.getType())){
						isInType=true;
					}
				}
				if(!isInType){
					logger.error("请假流程数据用户："+workLeave.getFullname()+",开始时间:"+leave_start+"结束时间："+leave_end+",开始时段："+levea_start_select+",结束时段："+levea_end_select
							+",类型："+workLeave.getType()+"，类型不符合规范不予入库");
					return;
				}
			}
			
			
			
			List<SysRole> roles=roleService.getByUserId(Long.parseLong(workLeave.getFullnameID()));
			//设置角色
			if(null!=roles && roles.size()>0){
				boolean isRoleHr=false;//是否是人事专员
				for(SysRole role:roles){
					if(role.getAlias().equals("bpm_hr_manager")){//人事管理角色
						isRoleHr=true;
						workLeave.setRoleID(role.getRoleId().toString());
						workLeave.setRolename(role.getRoleName());
					}
				}
				if(!isRoleHr){
					workLeave.setRoleID(roles.get(0).getRoleId().toString());
					workLeave.setRolename(roles.get(0).getRoleName());
				}
			}
			
			if(leave_start.equals(leave_end)){
				Date leaveDate=DateUtils.parseDateS(leave_end);
				if(!workCommonService.isWorkDay(leaveDate)){//不能添加非工作日的缺勤记录
					return;
				}
				workLeave.setLeave_time(leaveDate);
				if(levea_start_select==Constants.LEAVERANGEARRArr[0]
						&& levea_end_select==Constants.LEAVERANGEARRArr[0]){//缺勤上午
					workLeave.setLeave_range(Constants.LEAVEMORNING);
				}else if(levea_start_select==Constants.LEAVERANGEARRArr[1]
						&& levea_end_select==Constants.LEAVERANGEARRArr[1]){//缺勤下午
					workLeave.setLeave_range(Constants.LEAVEAFTERNOON);
				}else if(levea_start_select==Constants.LEAVERANGEARRArr[0]
						&& levea_end_select==Constants.LEAVERANGEARRArr[1]){//缺勤一天
					workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
				}
				
				try{
					workLeaveService.save(workLeave);
					logger.info("请假流程数据用户："+workLeave.getFullname()+",开始时间:"+leave_start+"结束时间："+leave_end+",开始时段："+levea_start_select+",结束时段："+levea_end_select+",入库成功");
				}catch(Exception e){
					logger.error("请假流程数据用户："+workLeave.getFullname()+",时间："+leave_end+",入库缺勤表异常，",e);
				}
			}else{
				Date leaveStartDate=DateUtils.parseDateS(leave_start);
				Date leaveEndDate=DateUtils.parseDateS(leave_end);
				//判断两个日期相差的时间
				int day=DateUtils.getDiffDaysByDay(leaveStartDate, leaveEndDate);
				boolean isError=false;
				String dateAddRptMsg="已添加过缺勤记录的日期:";
				boolean isAdd=false;//是否有添加数据
				StringBuffer notWorkDay=new StringBuffer("非工作日:");
				for(int i=0;i<=day;i++){
					if(i==0){
						if(levea_start_select==Constants.LEAVERANGEARRArr[0]){//上午就开始请假，则算为请假一天
							workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
						}else if(levea_start_select==Constants.LEAVERANGEARRArr[1]){//下午开始请假，请假下午
							workLeave.setLeave_range(Constants.LEAVEAFTERNOON);
						}
					}else if(i==day){
						if(levea_end_select==Constants.LEAVERANGEARRArr[0]){//结束上午请假，则只请到上午的半天假期
							workLeave.setLeave_range(Constants.LEAVEMORNING);
						}else if(levea_end_select==Constants.LEAVERANGEARRArr[1]){//下午才结束请假，则请假全天
							workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);
						}
					}else{
						workLeave.setLeave_range(Constants.LEAVERANGEALLDAY);//设置默认缺勤范围
					}
					Date curDate=DateUtils.getDateAddDay(leaveStartDate, i);
					//判断是否是工作日
					if(!workCommonService.isWorkDay(curDate)){
						notWorkDay.append(DateUtils.formatDateS(curDate)+",");
						continue;
					}
					
					workLeave.setId(null);
					workLeave.setLeave_time(curDate);
					try{
						workLeaveService.save(workLeave);
						isAdd=true;
					}catch(Exception e){
						dateAddRptMsg+=DateUtils.formatDateS(curDate)+",";
						isError=true;
					}
				}
				if(isError){
					StringBuffer errorMsg=new StringBuffer();
					if(StringUtils.isNotEmpty(notWorkDay.toString())){
						errorMsg.append(notWorkDay.toString().substring(0,notWorkDay.length()-1)).append(";");
					}
					if(StringUtils.isNotEmpty(dateAddRptMsg)){
						errorMsg.append(dateAddRptMsg.toString());
					}
					if(!errorMsg.equals("")){
						errorMsg.append("不能添加缺勤记录");
					}
					if(isAdd){
						errorMsg.append(",其他日期添加缺勤记录成功");
					}
					logger.error("请假流程数据用户："+workLeave.getFullname()+",时间："+leave_end+",入库，"+errorMsg.toString());
				}else{
					logger.info("请假流程数据用户："+workLeave.getFullname()+",开始时间:"+leave_start+"结束时间："+leave_end+",开始时段："+levea_start_select+",结束时段："+levea_end_select+",入库成功");
				}
			}
			logger.info("--------请假流程数据转移到业务表结束---------------");
		}catch (Exception e) {
			logger.error("请假流程数据入库缺勤表异常",e);
		}
	}
}
