package com.hotent.makshi.service.worksheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.makshi.model.worksheet.WorkCount;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.model.worksheet.WorkSheet;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.SysCalendar;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.worktime.CalendarSettingService;
import com.hotent.platform.service.worktime.SysCalendarService;
import com.hotent.makshi.utils.Constants;

@Service
public class WorkCommonService {
	
	@Autowired
	private SysCalendarService sysCalendarService;
	
	@Autowired
	private CalendarSettingService calendarSettingService;
	
	private static Logger logger=Logger.getLogger(WorkCommonService.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	@Resource
	private WorkSheetService workSheetService;
	
	@Resource
	private WorkLeaveService workLeaveService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	/**
	 * 统计开始时间
	 */
	public static String countStartDate="20170101";
	
	private static Integer LEAVERANGEALLDAY=0;//缺勤一天
	
	private Integer leaveStateArr[]={0,1,2};//缺勤状态，0：未缺勤，1：缺勤一天，2：缺勤半天
	
	@Resource
	private WorkCountService workCountService;
	
	/**
	 * 考勤统计
	 */
	public void workCount(){
		//获取默认日历
		SysCalendar calendar=sysCalendarService.getDefaultCalendar();
		if(null==calendar || null==calendar.getId()){
			logger.info("暂未配置默认日历，请联系管理员配置默认日历");
			return;
		}
		
		//查询未统计的出勤数据
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("count_flag", Constants.COUNT_FLAG_FALSE);
		//List<WorkSheet> workSheetList=workSheetService.getWorkSheetList(params);
		//查询未统计的缺勤数据
		//List<WorkLeave> workLeaveList=workLeaveService.getWorkLeaveList(params);
	}
	
	/**
	 * 历史考勤统计
	 */
	public void hisWorkCount(){
		//获取默认日历
		SysCalendar calendar=sysCalendarService.getDefaultCalendar();
		if(null==calendar || null==calendar.getId()){
			logger.info("暂未配置默认日历，请联系管理员配置默认日历");
			return;
		}
		
		//获取用户数据
		List<SysUser> userList=sysUserService.getAll();
		//获取当前日期
		String curDate=DateUtils.getFormatCurrentTime("yyyyMMdd");
		//获取统计时间到至今的每月的最后一天的日期列表
		List<String> lastDayOfMonthList=DateUtils.getMonthEndDateList(Integer.parseInt(countStartDate), Integer.parseInt(curDate));
		Date thisCountDate=new Date();
		for(SysUser user:userList){//遍历用户，统计数据
			List<WorkCount> countList=new ArrayList<WorkCount>();
			Map<String,Object> params=new HashMap<String,Object>();
			List<WorkSheet> sheetAll=new ArrayList<WorkSheet>();
			List<WorkLeave> leaveAll=new ArrayList<WorkLeave>();
			
			for(String lastMonthDay:lastDayOfMonthList){//遍历需要统计的月份最后一天日期
				Integer year=Integer.parseInt(lastMonthDay.substring(0, 4));//获取年份
				String monthStr=lastMonthDay.substring(4, 6);
				Integer month=Integer.parseInt(monthStr);//获取月份
				
				//获取当月的工作日期数据
				List<CalendarSetting> calSetlist   = calendarSettingService.getCalByIdYearMon(calendar.getId(), year, month);
				if(null==calSetlist || calSetlist.size()==0){
					logger.info("默认日历"+year+"-"+monthStr+",未设置工作日历，请设置再进行统计");
					continue;
				}
				//获取当前统计月的第一天
				String monthFirstDay=DateUtils.transDate(lastMonthDay).substring(0,7)+"-01";
				
				//获取当前用户当月第一天和最后一天的出勤数据
				params.put("beginclock_time", monthFirstDay);
				params.put("endclock_time", DateUtils.transDate(lastMonthDay));
				params.put("fullnameID", user.getUserId());
				List<WorkSheet> workSheetList=workSheetService.getWorkSheetList(params);
				
				//获取当前用户当月第一天和最后一天的缺勤记录
				params.clear();
				params.put("beginleave_time", monthFirstDay);
				params.put("endleave_time", DateUtils.transDate(lastMonthDay));
				params.put("fullnameID", user.getUserId());
				List<WorkLeave> workLeaveList=workLeaveService.getWorkLeaveList(params);
				
				//设置数据
				WorkCount count=new WorkCount();
				count.setFullnameID(user.getUserId().toString());
				count.setFullname(user.getFullname());
				count.setWorkDay(calSetlist.size()+0.0);//设置上班天数
				count.setYear(year);
				count.setSdate(DateUtils.parseDateS(DateUtils.transDate(lastMonthDay)));//设置统计时间
				count.setCreateTime(thisCountDate);
				
				List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(user.getUserId());
				//设置部门
				if(sysOrgs!=null && sysOrgs.size()>0){
					count.setOrgID(sysOrgs.get(0).getOrgId().toString());
					count.setOrg(sysOrgs.get(0).getOrgName());
				}
				
				//设置统计数据
				countWorkData(calSetlist, workSheetList, workLeaveList, count);
				
				if(null!=workSheetList && workSheetList.size()>0){
					sheetAll.addAll(workSheetList);
				}
				if(null!=workLeaveList && workLeaveList.size()>0){
					leaveAll.addAll(workLeaveList);
				}
				countList.add(count);
			}
			//数据批量入库
			if(countList.size()>0){
				params.clear();
				params.put("countList", countList);
				workCountService.batchInsert(params);
			}
			
			//批量修改考勤统计和缺勤统计数据
			if(sheetAll.size()>0){
				for(WorkSheet sheet:sheetAll){
					sheet.setCount_flag(Constants.COUNT_FLAG_TRUE);//已统计
				}
				params.clear();
				params.put("sheetList", sheetAll);
				workSheetService.batchInsert(params);
			}
			if(leaveAll.size()>0){
				for(WorkLeave leave:leaveAll){//标记已统计
					leave.setCount_flag(Constants.COUNT_FLAG_TRUE);
				}
				params.clear();
				params.put("leaveList", leaveAll);
				workLeaveService.batchInsert(params);
			}
			
		}
	}
	
	/**
	 * 统计考勤记录
	 * @param calSetlist
	 * @param workSheetList
	 * @param workLeaveList
	 * @param count
	 */
	private void countWorkData(List<CalendarSetting> calSetlist,List<WorkSheet> workSheetList,List<WorkLeave> workLeaveList,WorkCount count){
		Map<String,WorkSheet> clockMap=getClockDateMap(workSheetList);//出勤时间列表
		Map<String,WorkLeave> leaveMap=getLeveaDateMap(workLeaveList);//缺勤时间列表
		Double clockCount=0.00;
		Double leaveCount=0.00;
		Double noneClockCount=0.00;
		Double reClockCount=0.00;
		for(CalendarSetting set:calSetlist){
			String calDay=set.getCalDay();//设置的上班时间
			Integer leaveState=leaveStateArr[0];//缺勤状态，未缺勤
			if(leaveMap.containsKey(calDay)){
				WorkLeave leave=leaveMap.get(calDay);
				if(leave.getLeave_range()==LEAVERANGEALLDAY){//缺勤全天
					leaveCount++;
					leaveState=leaveStateArr[1];//缺勤一天
				}else{//缺勤半天
					leaveCount+=0.5;
					leaveState=leaveStateArr[2];//缺勤半天
				}
			}
			//缺勤一天，不做其他统计了
			if(leaveState==leaveStateArr[1]){
				continue;
			}
			
			if(clockMap.containsKey(calDay)){//当天已签到或补录
				WorkSheet workSheet=clockMap.get(calDay);//获取考勤记录
				if(workSheet.getType()!=null &&
						workSheet.getType().equals("补录")){
					if(leaveState==leaveStateArr[2]){//缺勤半天，补录也只算半天
						reClockCount+=0.5;
					}else{
						reClockCount++;
					}
				}else{
					if(leaveState==leaveStateArr[2]){//缺勤半天，签到也只算半天
						clockCount+=0.5;
					}else{
						clockCount++;
					}
				}
			}else{//未打卡时间
				if(leaveState==leaveStateArr[2]){//缺勤半天，未打卡也只算半天
					noneClockCount+=0.5;
				}else{
					noneClockCount++;
				}
			}
		}
		count.setClockCount(clockCount);
		count.setLeaveCount(leaveCount);
		count.setNoneClockCount(noneClockCount);
		count.setReClockCount(reClockCount);
	}
	
	/**
	 * 获取签到时间map
	 * @param workSheetList
	 * @return
	 */
	private Map<String,WorkSheet> getClockDateMap(List<WorkSheet> workSheetList){
		Map<String,WorkSheet> clockMap=new HashMap<String,WorkSheet>();
		if(workSheetList==null || workSheetList.size()==0){return clockMap;}//为空
		for(WorkSheet ws:workSheetList){
			String cockDate=DateUtils.formatDateS(ws.getClock_time());
			clockMap.put(cockDate, ws);
		}
		return clockMap;
	}
	
	/**
	 * 获取缺勤时间列表
	 * @param workLeaveList
	 * @return
	 */
	private Map<String,WorkLeave> getLeveaDateMap(List<WorkLeave> workLeaveList){
		Map<String,WorkLeave> leaveMap=new HashMap<String,WorkLeave>();
		if(workLeaveList==null || workLeaveList.size()==0){return leaveMap;}//为空
		for(WorkLeave wl:workLeaveList){
			String leaveDate=DateUtils.formatDateS(wl.getLeave_time());
			leaveMap.put(leaveDate, wl);
		}
		return leaveMap;
	}
	
	
	/**
	 * 判断日期是否是工作日期
	 * @param curDate
	 * @return
	 */
	public Boolean isWorkDay(Date date){
		//获取默认日历
		SysCalendar calendar=sysCalendarService.getDefaultCalendar();
		String year=DateUtils.getFormatDateTime(date, "yyyy");
		String month=DateUtils.getFormatDateTime(date, "MM");
		String dateStr=DateUtils.formatDateS(date);
		if(null!=calendar && calendar.getId()!=null){
			List<CalendarSetting> calSetlist   = calendarSettingService.getCalByIdYearMon(calendar.getId(), Integer.parseInt(year), Integer.parseInt(month));//获取单月的上班时间
			if(null==calSetlist || calSetlist.size()==0){//单月工作日历无数据，不能签到
				return false;
			}else{
				Map<String,CalendarSetting> workDateMap=getWorkDateMap(calSetlist);
				if(!workDateMap.containsKey(dateStr)){//单月工作日历不包含当前，不能签到
					return false;
				}else{
					CalendarSetting setting=workDateMap.get(dateStr);
					if(setting.getWorkTimeId()!=0){//上班时间
						return true;
					}else{
						return false;
					}
				}
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 构建日历日期map
	 * @param calSetlist
	 * @return
	 */
	private Map<String,CalendarSetting> getWorkDateMap(List<CalendarSetting> calSetlist){
		 Map<String,CalendarSetting> rstMap=new HashMap<String,CalendarSetting>();
		for(CalendarSetting setting:calSetlist){
			rstMap.put(setting.getCalDay(), setting);
		}
		return rstMap;
	}

}
