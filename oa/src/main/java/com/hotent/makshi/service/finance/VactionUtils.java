package com.hotent.makshi.service.finance;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.utils.Constants;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.SysCalendar;
import com.hotent.platform.service.worktime.CalendarSettingService;
import com.hotent.platform.service.worktime.SysCalendarService;

public class VactionUtils {

	private static Logger logger = LoggerFactory.getLogger(VactionUtils.class);
	private static SysCalendarService sysCalendarService;
	private static CalendarSettingService calendarSettingService;
	private static WorkHourApplicationService workHourApplicationService;
	
	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	public static void main(String[] args) throws ParseException {
		List<String> monthBetween = getMonthBetween("2017-12-01", "2017-12-01");
		for (String string : monthBetween) {
			System.out.println(string);
		}
	}

	public static double calVacDays(String startTimeViewTemp, String endTimeViewTemp, String leava_type) {
		// double s=0;
		String leave_start = startTimeViewTemp.substring(0, 10);
		String leave_end = endTimeViewTemp.substring(0, 10);
		Integer levea_start_select = Constants.LEAVEMORNING;
		Integer levea_end_select = Constants.LEAVEMORNING;
		if (startTimeViewTemp.endsWith("下午")) {
			levea_start_select = Constants.LEAVEAFTERNOON;
		}
		if (endTimeViewTemp.endsWith("下午")) {
			levea_end_select = Constants.LEAVEAFTERNOON;
		}
		Date leaveStartDate = DateUtils.parseDateS(leave_start);
		Date leaveEndDate = DateUtils.parseDateS(leave_end);
		if (sysCalendarService == null) {
			sysCalendarService = AppUtil.getBean(SysCalendarService.class);
		}
		if (calendarSettingService == null) {
			calendarSettingService = AppUtil.getBean(CalendarSettingService.class);
		}
		try {
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
			if (leava_type != null && (leava_type.equals("婚假") || leava_type.equals("丧假") || leava_type.equals("产假") || leava_type.equals("陪产假"))) {
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
				return countDay;
			}
			// end
			
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
			return countDay;

		} catch (Exception e) {
			logger.error("计算请假天数异常：" + "开始时间" + startTimeViewTemp + " 结束时间：" + endTimeViewTemp + " 请假类型：" + leava_type);
			return 0;
		}
	}

	public static Map<String, CalendarSetting> getCalDayMap(List<CalendarSetting> calSetlist) {
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
	 * 
	 * @param startDate 不带时分秒
	 * @param endDate 不带时分秒
	 * @param levea_start_select
	 * @param levea_end_select
	 * @param leave_type
	 * @param leave_days
	 * @param userid
	 * @return
	 */
	public static Map<Integer,String> validVaction(Date startDate,Date endDate,Integer levea_start_select,Integer levea_end_select,String leave_type,double leave_days,Long userid){
		Map<Integer,String> result=new HashMap<>();
		String msg="";
		if(leave_days<=0){
			msg="请假天数不能小于等于0";
		}
		if(startDate.after(endDate)){
			msg="请假开始时间不能大于结束时间";
		}else if(startDate.equals(endDate) && levea_start_select==Constants.LEAVEAFTERNOON && levea_end_select==Constants.LEAVEMORNING){
			msg="请假开始时间不能大于结束时间";
		}
		if("婚假".equals(leave_type) && leave_days>3){
			msg="婚假请假天数不能大于3天!";
		}
		if("陪产假".equals(leave_type) && leave_days>15){
			msg="陪产假请假天数不能大于15天!";
		}
		if("产假".equals(leave_type) && leave_days>178){
			msg="产假请假天数不能大于178天!";
		}
		if("丧假".equals(leave_type) && leave_days>3){
			msg="丧假请假天数不能大于3天!";
		}
		if(StringUtil.isNotEmpty(msg)){
			result.put(ResultMessage.Fail, msg);
			return result;
		}
		if(workHourApplicationService==null){
			workHourApplicationService=AppUtil.getBean(WorkHourApplicationService.class);
		}
		List<Vaction> hadStartedVactionByUserId = workHourApplicationService.getHadStartedVactionByUserId(userid);
		boolean existConflict=false;
		for (Vaction vaction : hadStartedVactionByUserId) {
			boolean existDateConflict = isExistDateConflict(vaction,startDate,endDate,levea_start_select,levea_end_select);
			if(existDateConflict){
				existConflict=true;
				break;
			}
		}
		if(existConflict){
			msg="请假日期区间已存在请假记录!";
		}
		if(StringUtil.isEmpty(msg)){
			result.put(ResultMessage.Success, "ok");
		}else{
			result.put(ResultMessage.Fail, msg);
		}
		return result;
	}
	private static  boolean isExistDateConflict(Vaction vaction,Date startDate,Date endDate,Integer levea_start_select,Integer levea_end_select){
		boolean f=false;
		String formatDateS = DateUtils.formatDateS(startDate);
		String formatDateE = DateUtils.formatDateS(endDate);
		String startTimeView = vaction.getStartTimeView();//2018-01-22-下午
		String endTimeView = vaction.getEndTimeView();
		if(StringUtils.isEmpty(startTimeView) || StringUtils.isEmpty(endTimeView)){
			return f;
		}
		String startAmOrBm = startTimeView.split("-")[3];//下午或上午
		String endAmOrBm = endTimeView.split("-")[3];
		String startTime = startTimeView.substring(0,10);
		String endTime = endTimeView.substring(0,10);
		if(formatDateS.compareTo(startTime)<0
				&& startTime.compareTo(formatDateE)<0
				&& formatDateE.compareTo(endTime)<0){
			f=true;
			
		}else if(startTime.compareTo(formatDateS)<0
				&& formatDateS.compareTo(endTime)<0
				&& endTime.compareTo(formatDateE)<0){
			f=true;
		}else if(formatDateS.compareTo(startTime)<0
				&& endTime.compareTo(formatDateE)<0){
			f=true;
		}else if(startTime.compareTo(formatDateS)<0
				&& formatDateE.compareTo(endTime)<0){
			f=true;
		}else if(startTime.compareTo(endTime)<0
				&& endTime.equals(formatDateS)){
			if("上午".equals(endAmOrBm) && levea_start_select.intValue()==Constants.LEAVEAFTERNOON){
				
			}else{
				f=true;
			}
		}else if(formatDateS.compareTo(formatDateE)<0
				&& formatDateE.equals(startTime)){
			if("下午".equals(startAmOrBm) && levea_end_select.intValue()==Constants.LEAVEMORNING){
				
			}else{
				f=true;
			}
		}else if(formatDateS.equals(startTime)){
			if(formatDateE.equals(formatDateS) && formatDateE.equals(endTime) && startAmOrBm.equals(endAmOrBm) && levea_start_select.intValue()==levea_end_select.intValue()
					&& (("上午".equals(startAmOrBm) &&  levea_start_select.intValue()==Constants.LEAVEAFTERNOON) ||
							("下午".equals(startAmOrBm) &&  levea_start_select.intValue()==Constants.LEAVEMORNING) )){
				//请假同一天 上午和下午不同 才不冲突
			}else{
				f=true;
			}
		}else if(formatDateE.equals(endTime)){
			if(formatDateS.equals(formatDateE) && endTime.equals(startTime) && startAmOrBm.equals(endAmOrBm) && levea_start_select.intValue()==levea_end_select.intValue()
					&& (("上午".equals(startAmOrBm) &&  levea_start_select.intValue()==Constants.LEAVEAFTERNOON) ||
							("下午".equals(startAmOrBm) &&  levea_start_select.intValue()==Constants.LEAVEMORNING))){
			}else{
				f=true;
			}
		}
		return f;
	}
}
