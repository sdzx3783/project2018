

package com.hotent.makshi.controller.worksheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.finance.Workstats;
import com.hotent.makshi.model.worksheet.WorkCount;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.model.worksheet.WorkSheet;
import com.hotent.makshi.service.finance.CalendarException;
import com.hotent.makshi.service.finance.WorkHourStatsService;
import com.hotent.makshi.service.worksheet.WorkLeaveService;
import com.hotent.makshi.service.worksheet.WorkSheetService;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.SysCalendar;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.worktime.CalendarSettingService;
import com.hotent.platform.service.worktime.SysCalendarService;
/**
 * 对象功能:考勤统计
 */
@Controller
@RequestMapping("/makshi/worksheet/workCount/")
public class WorkCountController extends BaseController
{
	@Resource
	private WorkLeaveService workLeaveService;
	
	@Resource
	private CalendarSettingService calendarSettingService;
	
	@Resource
	private WorkSheetService workSheetService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Autowired
	private SysCalendarService sysCalendarService;
	
	@Resource
	private SysRoleService roleService;
	@Resource
	private WorkHourStatsService workHourStatsService;
	/**
	 * 取得缺勤表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看缺勤表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView mv = getAutoView();
		String queryMonth = RequestUtil.getString(request, "queryMonth");
		String orgID = RequestUtil.getString(request, "orgID");
		if(StringUtils.isEmpty(queryMonth)){
			queryMonth=DateUtils.formatDateS(new Date()).substring(0,7);
		}
		if(StringUtils.isEmpty(orgID)){
			orgID="10000007780597";//默认深水咨询
		}
		ISysUser currentUser = ContextUtil.getCurrentUser();
		boolean hadReadRight=false;
		List<SysRole> byUser = roleService.getByUserId(currentUser.getUserId());
		for (SysRole sysRole : byUser) {
			if(sysRole!=null && sysRole.getRoleName().contains("考勤统计查询")){
				hadReadRight=true;
			}
		}
		QueryFilter queryFilter = new QueryFilter(request,"workCountItem");
		Map<String, Object> filters = queryFilter.getFilters();
		filters.put("queryMonth", queryMonth);
		filters.put("orgID", orgID);
		if(!hadReadRight){
			filters.put("userid", currentUser.getUserId());
		}
		mv.addObject("hadReadRight", hadReadRight);
		SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgID));
		String orgPath = sysOrg.getPath();
		List<com.hotent.makshi.model.finance.WorkCount> workStatsCountList = null;
		try {
			workStatsCountList=workHourStatsService.getWorkStatsCountList(queryMonth,orgPath,queryFilter);
		} catch (CalendarException e) {
			mv.addObject("errorMsg", e.getMessage()).addObject("queryMonth", queryMonth).addObject("org", sysOrg.getOrgName()).addObject("orgID", sysOrg.getOrgId());
			return mv;
		}
		return mv.addObject("workStatsCountList", workStatsCountList).addObject("queryMonth",queryMonth).addObject("org", sysOrg.getOrgName()).addObject("orgID", sysOrg.getOrgId());
		/*SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		String yearMonth=DateUtils.getFormatCurrentTime("yyyy-MM");//获取当前年月)
		QueryFilter fitler=new QueryFilter(request,"workCountItem");
		if(null!=fitler.getFilters().get("yearMonth") &&
				!"".equals(fitler.getFilters().get("yearMonth"))){
			yearMonth=fitler.getFilters().get("yearMonth").toString();
		}
		//设置角色
		List<SysRole> roles=roleService.getByUserId(sysUser.getUserId());
		String alias=null;
		if(null!=roles && roles.size()>0){
			for(SysRole role:roles){
				if(role.getAlias().equals("bpm_hr_manager")){
					alias=role.getAlias();
				}
			}
			if(null==alias){
				alias=roles.get(0).getAlias();
			}
		}
		if(alias!=null && !alias.equals("bpm_hr_manager")){
			fitler.addFilter("userId", sysUser.getUserId());
		}
		//获取默认日历
		SysCalendar calendar=sysCalendarService.getDefaultCalendar();
		if(null==calendar || null==calendar.getId()){
			logger.info("暂未配置默认日历，请联系管理员配置默认日历");
			ModelAndView mv=this.getAutoView().addObject("yearMonth", yearMonth).addObject("alias",alias==null?null:alias);
			return mv;
		}
		
		
		//获取用户数据
		List<SysUser> userList=sysUserService.getAllAllUserByOrg(fitler);
		List<WorkCount> countList=new ArrayList<WorkCount>();
		for(SysUser user:userList){//遍历用户，统计数据
			Map<String,Object> params=new HashMap<String,Object>();
			List<WorkSheet> sheetAll=new ArrayList<WorkSheet>();
			List<WorkLeave> leaveAll=new ArrayList<WorkLeave>();
			
			Integer year=Integer.parseInt(yearMonth.substring(0, 4));//获取年份
			String monthStr=yearMonth.substring(5, 7);
			Integer month=Integer.parseInt(monthStr);//获取月份
			
			//获取当月的工作日期数据
			List<CalendarSetting> calSetlist   = calendarSettingService.getCalByIdYearMon(calendar.getId(), year, month);
			Double workDay=0.0;
			if(null==calSetlist || calSetlist.size()==0){
				logger.info("默认日历"+year+"-"+monthStr+",未设置工作日历，请设置再进行统计");
				ModelAndView mv=this.getAutoView().addObject("yearMonth", yearMonth).addObject("alias",alias==null?null:alias)
						.addObject("not_config_calendar", "该月未配置工作日历，请联系管理员配置");
				return mv;
			}else{
				for(Iterator<CalendarSetting> it=calSetlist.iterator();it.hasNext();){
					CalendarSetting setting=it.next();
					if(setting.getWorkTimeId()!=0){
						String calDay=setting.getCalDay();
						Calendar cal = Calendar.getInstance();
						cal.setTime(DateUtils.parseDateS(calDay));
						if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){//周六
							workDay+=0.5;
						}else{
							workDay+=1;
//						}
					}else{
						it.remove();
					}
				}
			}
			//获取当前统计月的第一天
			String monthFirstDay=yearMonth+"-01";
			String lastMonthDay=yearMonth+"-31";
			
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
			count.setWorkDay(workDay);//设置上班天数
			count.setYear(year);
			count.setYearMonth(yearMonth);
			count.setSdate(DateUtils.parseDateS(DateUtils.transDate(lastMonthDay)));//设置统计时间
			
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
		ModelAndView mv=this.getAutoView().addObject("workCountList",countList)
				.addObject("yearMonth", yearMonth).addObject("alias",alias==null?null:alias);*/
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
			Integer leaveState=Constants.LEAVESTATEARR[0];//缺勤状态，未缺勤
			if(leaveMap.containsKey(calDay)){
				WorkLeave leave=leaveMap.get(calDay);
				if(leave.getLeave_range()==Constants.LEAVERANGEALLDAY){//缺勤全天
					leaveCount++;
					leaveState=Constants.LEAVESTATEARR[1];//缺勤一天
				}else{//缺勤半天
					leaveCount+=0.5;
					leaveState=Constants.LEAVESTATEARR[2];//缺勤半天
				}
			}
			//缺勤一天，不做其他统计了
			if(leaveState==Constants.LEAVESTATEARR[1]){
				continue;
			}
			
			if(clockMap.containsKey(calDay)){//当天已签到或补录
				WorkSheet workSheet=clockMap.get(calDay);//获取考勤记录
				if(workSheet.getType()!=null &&
						workSheet.getType().equals("补录")){
					if(leaveState==Constants.LEAVESTATEARR[2]){//缺勤半天，补录也只算半天
						reClockCount+=0.5;
					}else{
						reClockCount++;
					}
				}else{
					if(leaveState==Constants.LEAVESTATEARR[2]){//缺勤半天，签到也只算半天
						clockCount+=0.5;
					}else{
						clockCount++;
					}
				}
			}else{//未打卡时间
				if(leaveState==Constants.LEAVESTATEARR[2]){//缺勤半天，未打卡也只算半天
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
	
	public String convetDouble(String value){
		if(doubleIsInt(value)){
			return Integer.parseInt(value)+"";
		}else{
			return value;
		}
	}
	
	
	/**
	 * 判断一个double能否转换为一个int
	 * @param num double
	 * @return boolean
	 */
	private boolean doubleIsInt(String numStr)
	{
	    Boolean flag = false;// 没碰到小数点时候标记是false
	    int n = 0;// 计数器
	 
	    char[] charArray = numStr.toCharArray();
	 
	    for (char c : charArray)
	    {
	        if (c == '.')
	        {
	            flag = true;
	            continue;
	        }
	        if (flag && Integer.valueOf(String.valueOf(c)) > 0)
	        {
	            n++;
	        }
	    }
	 
	    if (n > 0)
	    {
	        return false;
	    }
	 
	    return true;
	}
	
	
}