
package com.hotent.makshi.controller.worksheet;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.finance.WorkHourApplicationDao;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.model.vacation.AnuualLeave;
import com.hotent.makshi.model.worksheet.AnnualVacation;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.service.finance.VactionUtils;
import com.hotent.makshi.service.finance.WorkHourApplicationService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.service.vacation.AnuualLeaveService;
import com.hotent.makshi.service.worksheet.WorkLeaveService;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.SysCalendar;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.worktime.CalendarSettingService;
import com.hotent.platform.service.worktime.SysCalendarService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.util.ReturnCode;

/**
 * 对象功能:考勤统计
 */
@Controller
@RequestMapping(value = { "/makshi/worksheet/annualVacation/", "/weixin/worksheet/annualVacation/" })
public class AnnualVacationController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SysUserService sysUserService;

	@Resource
	private UserInfomationService userInfoService;

	@Resource
	private SysRoleService roleService;

	private Integer annualVacationArr[] = { 5, 10, 15 };// 年假数量

	private Double MONTHWORKDAY = 30.00;// 月工作天的标准

	@Resource
	private SysOrgService sysOrgService;

	@Autowired
	private WorkLeaveService workLeaveService;

	@Autowired
	private SysCalendarService sysCalendarService;

	@Resource
	private CalendarSettingService calendarSettingService;
	@Resource
	private AnuualLeaveService anuualLeaveService;
	@Resource
	private WorkHourApplicationService workhourService;
	@RequestMapping("annual_vacation_days")
	@Action(description = "获取个人年假数量")
	public void annualVacationDays(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		/*
		 * String year=DateUtils.getFormatCurrentTime("yyyy");//获取当前年 Date compareDate=new Date(); AnnualVacation annualVacation =getVacation(sysUser, year, compareDate);
		 * if(annualVacation.getRestAnnualVacation()==-1){ annualVacation.setRestAnnualVacation(0.00); }
		 * writeResultMessage(response.getWriter(),annualVacation.getRestAnnualVacation()+"",ResultMessage.Success);
		 */
		AnuualLeave anuualLeave = anuualLeaveService.calculateAnuualVacation(sysUser.getUserId());
		if (anuualLeave == null) {
			anuualLeave = new AnuualLeave(0d, 0d, 0d, 0d);
		}
		writeResultMessage(response.getWriter(), anuualLeave.getActualLeaveVacation() + "", ResultMessage.Success);
	}

	@RequestMapping("leave_vacation_days")
	@Action(description = "获取请假天数")
	public void leaveVacationDays(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// app调用标识
		boolean isapp = request.getRequestURI().indexOf("/weixin/worksheet/annualVacation/") > -1;
		if (isapp) {
			response.setContentType("application/json");
		}
		PrintWriter writer = response.getWriter();
		try {
			String leave_start = request.getParameter("leave_start");
			String leave_end = request.getParameter("leave_end");
			String levea_startString = request.getParameter("levea_start_select");
			String levea_endString = request.getParameter("levea_end_select");
			String leava_type = request.getParameter("leava_type");
			// 让app调用接口与请假流程保持一致
			if (isapp) {
				leave_start = request.getParameter("startTime");
				leave_end = request.getParameter("endTime");
				levea_startString = request.getParameter("leaveStartSolt");
				levea_endString = request.getParameter("leaveEndSolt");
				leava_type = request.getParameter("leave_type");
			}
			Integer levea_start_select = Integer.parseInt(levea_startString);
			Integer levea_end_select = Integer.parseInt(levea_endString);
			Date leaveStartDate = DateUtils.parseDateS(leave_start);
			Date leaveEndDate = DateUtils.parseDateS(leave_end);
			
			
			// 判断两个日期相差的时间
			int day = DateUtils.getDiffDaysByDay(leaveStartDate, leaveEndDate);

			// 获取日历空间设置的日期
			SysCalendar calendar = sysCalendarService.getDefaultCalendar();
			List<CalendarSetting> calSetlist = calendarSettingService.getCalByIdCalDay(calendar.getId(), leave_start, leave_end);
			/*
			 * List<CalendarSetting> calSetlistTemp = new ArrayList<CalendarSetting>(); for(CalendarSetting calendarSetting:calSetlist){ String calDay = calendarSetting.getCalDay(); SimpleDateFormat
			 * sdf = new SimpleDateFormat("yyyy-MM-dd"); String weekDay = DateUtils.getWeekDay(sdf.parse(calDay)); if(!weekDay.equals("六")&&!weekDay.equals("日")){ calSetlistTemp.add(calendarSetting);
			 * } } calSetlist = calSetlistTemp;
			 */
			// 婚假、丧假、产假，陪产假是按自然日计算的 当用户选择这几种类型的时候。不跨越 法定节假日 要把周末和法定节假日算到请假里面-update by sammy-2017-12-06
			if (leava_type != null 
					&& (leava_type.equals("婚假") || leava_type.equals("丧假") || leava_type.equals("产假") || leava_type.equals("陪产假"))) {
				double countDay = day;
				if (day == 0) {// 同一天
					if (levea_start_select.intValue() == levea_end_select.intValue()) {// 都是缺勤上午 算0.5天
						countDay = 0.5;
					} else if (levea_start_select.intValue() == Constants.LEAVEMORNING && levea_end_select.intValue() == Constants.LEAVEAFTERNOON) {// 缺勤下午则缺勤一天
						countDay += 1.0;
					} else {
						countDay = 0.0;
					}
				} else {// 不是同一天
					if (levea_start_select.intValue() == Constants.LEAVEMORNING && levea_end_select.intValue() == Constants.LEAVEMORNING) {
						countDay += 0.5;
					} else if (levea_start_select.intValue() == Constants.LEAVEMORNING && levea_end_select.intValue() == Constants.LEAVEAFTERNOON) {
						countDay += 1.0;
					} else if (levea_start_select.intValue() == Constants.LEAVEAFTERNOON && levea_end_select.intValue() == Constants.LEAVEMORNING) {
						countDay += 0.0;
					} else {
						countDay += 0.5;
					}

				}
				if (isapp) {
					writer.println("{\"return_code\":0,\"return_msg\": \"SUCCESS\",\"data\":{\"leave_days\":" + countDay + "}}");
				} else {
					writeResultMessage(response.getWriter(), countDay + "", ResultMessage.Success);
				}
				return;
			}
			//请假类型为事假、调休、年假、病假这4种类型，当请假时间包含周六时，请假天数需把周六计算在内，至于是算半边还是1天，则与工作日历设置关联，如果工作日历设置的该周六是上半天班的，请请假天数只算半天；如果工作日历设置该周六是上一天班的，请假天数则算1天。
			double countDay = 0.0;
			if (leava_type != null && 
					(leava_type.equals("事假") || leava_type.equals("调休") || leava_type.equals("年假") || leava_type.equals("病假"))) {
				if(BeanUtils.isNotEmpty(calSetlist)){
					String beginWorkDate=calSetlist.get(0).getCalDay();
					String endWorkDate=calSetlist.get(calSetlist.size()-1).getCalDay();
					if(beginWorkDate.equals(endWorkDate)){//请假是同一天
						CalendarSetting calendarSetting = calSetlist.get(0);
						String wtName = calendarSetting.getWtName();
						if(beginWorkDate.equals(leave_start)){
							if(levea_start_select.intValue()==Constants.LEAVEMORNING && levea_end_select.intValue()==Constants.LEAVEAFTERNOON){
								if("上午".equals(wtName) || "下午".equals(wtName)){
									countDay=0.5;
								}else{
									countDay=1.0;
								}
							}else if(levea_start_select.intValue()==Constants.LEAVEAFTERNOON && levea_end_select.intValue()==Constants.LEAVEAFTERNOON){
								if("上午".equals(wtName)){
									countDay=0.0;
								}else{
									countDay=0.5;
								}
							}else if(levea_start_select.intValue()==Constants.LEAVEMORNING && levea_end_select.intValue()==Constants.LEAVEMORNING){
								if("下午".equals(wtName)){
									countDay=0.0;
								}else{
									countDay=0.5;
								}
							}
						}else{
							if(beginWorkDate.equals(leave_end)){
								//开始时间不相同 结束时间相同
								if(levea_end_select.intValue()==Constants.LEAVEMORNING){
									countDay=0.5;
								}else{
									countDay=1;
								}
							}else{
								//开始时间不相同 结束时间相同
								countDay=1;
							}
						}
						
					}else{//请假日期跨度大于一天
						for (int i = 0; i < calSetlist.size(); i++) {
							CalendarSetting calendarSetting = calSetlist.get(i);
							String calDay = calendarSetting.getCalDay();
							String wtName = calendarSetting.getWtName();
							boolean isWholeDay=true;
							if("上午".equals(wtName) || "下午".equals(wtName)){
								isWholeDay=false;
							}
							if(beginWorkDate.equals(calDay)){
								if(beginWorkDate.equals(leave_start)){
									if(isWholeDay){
										if(levea_start_select.intValue()==Constants.LEAVEAFTERNOON){
											countDay+=0.5;
										}else{
											countDay+=1;
										}
									}else{
										if("上午".equals(wtName) && levea_start_select.intValue()==Constants.LEAVEMORNING){
											countDay+=0.5;
										}else if("下午".equals(wtName)){
											countDay+=0.5;
										}
									}
								}else{
									countDay+=1;
								}
							}else if(endWorkDate.equals(calDay)){
								if(endWorkDate.equals(leave_end)){
									if(isWholeDay){
										if(levea_end_select.intValue()==Constants.LEAVEMORNING){
											countDay+=0.5;
										}else{
											countDay+=1;
										}
									}else{
										if("下午".equals(wtName) && levea_end_select.intValue()==Constants.LEAVEAFTERNOON){
											countDay+=0.5;
										}else if("上午".equals(wtName)){
											countDay+=0.5;
										}
									}
								}else{
									countDay+=1;
								}
								
							}else{
								if(isWholeDay){
									countDay+=1;
								}else{
									countDay+=0.5;
								}
							}
						}
					}
				}
			}
			// end
			/*if (!(leava_type != null && leava_type.equals("产假"))) {
				for (Iterator<CalendarSetting> it = calSetlist.iterator(); it.hasNext();) {
					CalendarSetting cst = it.next();
					Date curDate = DateUtils.parseDateS(cst.getCalDay());
					if (DateUtils.getWeekDay(curDate).equals("六")) {
						it.remove();
					}
				}
			}

			double countDay = 0.0;
			Map<String, CalendarSetting> calendarSetMap = getCalDayMap(calSetlist);
			if (null == calendarSetMap || calendarSetMap.size() == 0) {
				if (isapp) {
					writer.println("{\"return_code\":0,\"return_msg\": \"SUCCESS\",\"data\":{\"leave_days\":" + countDay + "}}");
				} else {
					writeResultMessage(response.getWriter(), countDay + "", ResultMessage.Success);
				}
				return;
			}
			String leaveStartDateStr = leave_start.replaceAll("-", "");
			// Calendar cal = Calendar.getInstance();
			if (leave_start.equals(leave_end)) {// 同一天
				if (levea_start_select.intValue() == levea_end_select.intValue()) {// 都是缺勤上午 算0.5天
					countDay = 0.5;
				} else if (levea_start_select.intValue() == Constants.LEAVEMORNING && levea_end_select.intValue() == Constants.LEAVEAFTERNOON) {// 缺勤下午则缺勤一天
					
					 * cal.setTime(DateUtils.parseDateS(leave_start)); if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){//周六 countDay+=0.5; }else{
					 
					countDay += 1;
					// }
				} else {
					countDay = 0.0;
				}
			} else {
				for (int i = 0; i <= day; i++) {
					String curDate = DateUtils.transDate(DateUtils.getDateAddDay(leaveStartDateStr, i));
					if (calendarSetMap.containsKey(curDate)) {
						// cal.setTime(DateUtils.parseDateS(curDate));
						if (i == 0) {
							if (levea_start_select.intValue() == Constants.LEAVEMORNING) {// 上午 算一天
								// cal.setTime(DateUtils.parseDateS(curDate));
								// if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){//周六
								// countDay+=0.5;
								// }else{
								countDay += 1;
								// }
							} else if (levea_start_select.intValue() == Constants.LEAVEAFTERNOON) {// 下午算半天
								countDay += 0.5;
							}
						} else if (i == day) {
							if (levea_end_select.intValue() == Constants.LEAVEMORNING) {// 上午 算半天
								countDay += 0.5;
							} else if (levea_end_select.intValue() == Constants.LEAVEAFTERNOON) {// 下午算一天
								// if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){//周六
								// countDay+=0.5;
								// }else{
								countDay += 1;
								// }
							}
						} else {
							// if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){//周六
							// countDay+=0.5;
							// }else{
							countDay += 1;
							// }
						}
					}
				}
			}*/
			if (isapp) {
				writer.println("{\"return_code\":0,\"return_msg\": \"SUCCESS\",\"data\":{\"leave_days\":" + countDay + "}}");
			} else {
				writeResultMessage(response.getWriter(), countDay + "", ResultMessage.Success);
			}
		} catch (Exception e) {
			log.error("错误信息", e);
			if (isapp) {
				writer.println("{\"return_code\":1011,\"return_msg\": \"获取请假天数出错！\",\"data\":{}}");
			} else {
				throw e;
			}
		}
	}

	public Map<String, CalendarSetting> getCalDayMap(List<CalendarSetting> calSetlist) {
		if (null != calSetlist && calSetlist.size() > 0) {
			Map<String, CalendarSetting> rstMap = new HashMap<String, CalendarSetting>();
			for (CalendarSetting cal : calSetlist) {
				if (null != cal.getCalDay()) {
					rstMap.put(cal.getCalDay(), cal);
				}
			}
			return rstMap;
		} else {
			return null;
		}
	}

	/**
	 * 取得缺勤表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看缺勤表分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		String year = DateUtils.getFormatCurrentTime("yyyy");// 获取当前年
		QueryFilter fitler = new QueryFilter(request, "vacationCountItem");
		if (null != fitler.getFilters().get("year") && !"".equals(fitler.getFilters().get("year"))) {
			year = fitler.getFilters().get("year").toString();
		}
		// 设置角色
		List<SysRole> roles = roleService.getByUserId(sysUser.getUserId());
		String alias = null;
		if (null != roles && roles.size() > 0) {
			for (SysRole role : roles) {
				if (role.getAlias().equals("bpm_hr_manager")) {
					alias = role.getAlias();
				}
			}
			if (null == alias) {
				alias = roles.get(0).getAlias();
			}
		}
		if (alias != null && !alias.equals("bpm_hr_manager")) {
			fitler.addFilter("userId", sysUser.getUserId());
		}
		String curYear = DateUtils.getFormatCurrentTime("yyyy");// 获取当前年
		Date compareDate = new Date();
		if (!year.equals(curYear)) {// 非当前年，则统计当年的12月31号
			compareDate = DateUtils.parseDateS(year + "-12-31");
		}

		// Date curDate=new Date();
		// 获取用户数据
		List<SysUser> userList = sysUserService.getAllAllUserByOrg(fitler);
		List<AnnualVacation> vacationList = new ArrayList<AnnualVacation>();
		for (SysUser user : userList) {
			AnnualVacation vacation = getVacation(user, curYear, compareDate);
			vacationList.add(vacation);
		}
		ModelAndView mv = this.getAutoView().addObject("vacationCountList", vacationList).addObject("year", year).addObject("alias", alias == null ? null : alias);
		return mv;
	}

	public AnnualVacation getVacation(SysUser user, String year, Date compareDate) {
		AnnualVacation vacation = new AnnualVacation();

		vacation.setFullnameID(user.getUserId().toString());
		vacation.setFullname(user.getFullname());
		vacation.setYear(year);

		List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(user.getUserId());
		// 设置部门
		if (sysOrgs != null && sysOrgs.size() > 0) {
			vacation.setOrgID(sysOrgs.get(0).getOrgId().toString());
			vacation.setOrg(sysOrgs.get(0).getOrgName());
		}
		String beginDate = year + "-01-01";
		String endDate = year + "-12-31";
		// 已休年假
		Double usedAnnualVacation = getUsedAnnualVacation(user.getUserId(), beginDate, endDate);
		vacation.setUsedAnnualVacation(usedAnnualVacation);
		UserInfomation userInfomation = userInfoService.getUserInfomationByUid(user.getUserId());
		// 查不到用户资料或用户工作时间未设置，设置年假为-1
		if (userInfomation.getStart_work_time() == null) {// user.getEntryDate()改成参加工作时间
			vacation.setAnnualVacation(-1.0);
		} else {
			Date startWorkTime = userInfomation.getStart_work_time();// 参加工作时间 user.getEntryDate()改成参加工作时间
			String startWorkDate = DateUtils.formatDateS(startWorkTime);

			// 参加工作1年后的时间
			Integer annualVacation = 0;
			Date oneYearLater = DateUtils.parseDateS(DateUtils.transDate(DateUtils.getDateAddYear2(startWorkDate.replaceAll("-", ""), 1)));// 1年后的时间
			Date tenYearLater = DateUtils.parseDateS(DateUtils.transDate(DateUtils.getDateAddYear2(startWorkDate.replaceAll("-", ""), 10)));// 10年后的时间
			Date twentyYearLater = DateUtils.parseDateS(DateUtils.transDate(DateUtils.getDateAddYear2(startWorkDate.replaceAll("-", ""), 20)));// 20年后的时间
			Double sickOrLeaveVacation = getSickOrLeaveVacation(user.getUserId());// 累计病假或事假天数
			if (twentyYearLater.before(compareDate)) {// 已满20年
				if (sickOrLeaveVacation > MONTHWORKDAY * 4) {// 累计工作满20年以上的员工，请病假累计4个月以上的。
					annualVacation = 0;
				} else {
					annualVacation = annualVacationArr[2];
				}
			} else if (twentyYearLater.after(compareDate) && tenYearLater.before(compareDate)) {// 10-20年
				if (sickOrLeaveVacation > MONTHWORKDAY * 3) {// 累计工作满10年不满20年的员工，请病假累计3个月以上的。
					annualVacation = 0;
				} else {
					annualVacation = annualVacationArr[1];
				}
			} else if (tenYearLater.after(compareDate) && oneYearLater.before(compareDate)) {// 1-10年
				// 累计工作满1年不满10年的员工，请病假累计2个月以上的 不满足休假
				if (sickOrLeaveVacation > MONTHWORKDAY * 2) {
					annualVacation = 0;
				} else {
					annualVacation = annualVacationArr[0];
					Calendar cal = Calendar.getInstance();
					cal.setTime(oneYearLater);
					Integer oneYearLaterYear = cal.get(Calendar.YEAR);// 入职后一年的年份
					if (oneYearLaterYear.toString().equals(year)) {// 统计年为当年年份
						compareDate = DateUtils.parseDateS(year + "-12-31");
						Integer days = DateUtils.getDiffDaysByDay(oneYearLater, compareDate);
						Double v = days.doubleValue() / 365 * annualVacation;
						annualVacation = v.intValue();
					}
				}
			}
			vacation.setAnnualVacation(annualVacation.doubleValue());
		}
		vacation.setRestAnnualVacation(vacation.getAnnualVacation() - vacation.getUsedAnnualVacation());
		return vacation;
	}

	/**
	 * 获取事假或病假的时间
	 * 
	 * @return
	 */
	private Double getSickOrLeaveVacation(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> typeList = new ArrayList<String>();
		params.put("fullnameID", userId);
		typeList.add("病假");
		params.put("typeList", typeList);
		List<WorkLeave> leaveList = workLeaveService.getWorkLeaveList(params);
		if (leaveList != null && leaveList.size() > 0) {
			Double leaveCount = 0.00;
			for (WorkLeave leave : leaveList) {
				if (leave.getLeave_range() == Constants.LEAVERANGEALLDAY) {// 请全天的假期
					leaveCount += 1;
				} else {
					leaveCount += 0.5;
				}
			}
			return leaveCount;
		}
		return 0.00;
	}

	/**
	 * 根据用户和时间段查询用户已休年假数
	 * 
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private Double getUsedAnnualVacation(Long userId, String beginDate, String endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> typeList = new ArrayList<String>();
		params.put("fullnameID", userId);
		typeList.add("年假");
		params.put("typeList", typeList);
		params.put("beginleave_time", DateUtils.parseDateS(beginDate));
		params.put("endleave_time", DateUtils.parseDateS(endDate));
		List<WorkLeave> leaveList = workLeaveService.getWorkLeaveList(params);
		if (leaveList != null && leaveList.size() > 0) {
			Double leaveCount = 0.00;
			for (WorkLeave leave : leaveList) {
				if (leave.getLeave_range() == Constants.LEAVERANGEALLDAY) {// 请全天的假期
					leaveCount += 1;
				} else {
					leaveCount += 0.5;
				}
			}
			return leaveCount;
		}
		return 0.00;
	}
	@RequestMapping("validLeave")
	@Action(description = "校验请假参数")
	public void  validVac(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String leave_start = request.getParameter("leave_start");
		String leave_end = request.getParameter("leave_end");
		String levea_startString = request.getParameter("levea_start_select");
		String levea_endString = request.getParameter("levea_end_select");
		try{
			Integer levea_start_select = Integer.parseInt(levea_startString);
			Integer levea_end_select = Integer.parseInt(levea_endString);
			Date leaveStartDate = DateUtils.parseDateS(leave_start);
			Date leaveEndDate = DateUtils.parseDateS(leave_end);
			String leave_type = request.getParameter("levea_type");
			String leava_daystr = request.getParameter("levea_days");
			double leave_days = Double.parseDouble(leava_daystr);
			Map<Integer, String> validVaction = VactionUtils.validVaction(leaveStartDate, leaveEndDate, levea_start_select, levea_end_select, leave_type, leave_days, ContextUtil.getCurrentUserId());
			if(validVaction.containsKey(ResultMessage.Fail)){
				
				writeResultMessage(response.getWriter(), validVaction.get(ResultMessage.Fail), ResultMessage.Fail);
			}else{
				writeResultMessage(response.getWriter(), validVaction.get(ResultMessage.Success), ResultMessage.Success);
			}
		}catch(Exception e){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
			} else {
				str = ExceptionUtil.getExceptionMessage(e);
			}
			writeResultMessage(response.getWriter(), str, ResultMessage.Fail);
		}
		
		
	}
	
}