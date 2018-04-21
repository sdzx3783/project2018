package com.hotent.makshi.service.vacation;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.userinfo.UserInfomationDao;
import com.hotent.makshi.dao.vacation.AnuualLeaveDao;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.model.vacation.AnuualLeave;
import com.hotent.makshi.model.vacation.CompanyYearVaction;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.platform.service.system.SysUserService;

@Service
public class AnuualLeaveService extends BaseService<AnuualLeave> {
	@Resource
	private AnuualLeaveDao dao;
	@Resource
	private UserInfomationDao userInfomationDao;
	@Resource
	private UserInfomationService userInfoService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CompanyYearVactionService companyYearVactionService;
	//private static ThreadLocal<Map<String,Date>> yearDate=new ThreadLocal<>();// key:年份 value:年假结束日期
	
	/*private  Date getYearDate(String year){
		Date d=null;
		Map<String, Date> map = yearDate.get();
		if(map.containsKey(year)){
			d=map.get(year);
		}else{
			List<Vacation> vactionByYearAndName = companyYearVactionService.getVactionByYearAndName(year, "年假");
			Date lastYearDate=null;//年假最后一天
			if(vactionByYearAndName!=null && vactionByYearAndName.size()>0){
				for (Vacation vacation : vactionByYearAndName) {
					Date endTime = vacation.getEndTime();
					if(lastYearDate==null){
						lastYearDate=endTime;
					}else if(lastYearDate.before(endTime)){
						lastYearDate=endTime;
					}
				}
			}
			if(lastYearDate!=null){
				map.put(year, lastYearDate);
				d=lastYearDate;
			}
		}
		return d;
	}*/
	public AnuualLeaveService(){
		
	}
	
	@Override
	protected IEntityDao<AnuualLeave, Long> getEntityDao() {
		return dao;
	}
	
	public AnuualLeave getByUserId(Long userid){
		List<AnuualLeave> bySqlKey = dao.getBySqlKey("getByUserId", userid);
		return (bySqlKey!=null && bySqlKey.size()>0) ? bySqlKey.get(0):null;
	}
	
	public AnuualLeave  calculateAnuualVacation(Long userid) throws ParseException{
		UserInfomation userInfomation = userInfoService.getUserInfomationByUid(userid);
		SysUser userById = sysUserService.getById(userid);
		if(userInfomation==null){
			return null;
		}
		Date now=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		AnuualLeave anuualLeave = getByUserId(userid);
		if(anuualLeave==null){
			anuualLeave=new AnuualLeave();
			anuualLeave.setId(UniqueIdUtil.genId());
			anuualLeave.setIsadd(0d);
			anuualLeave.setCtime(now);
			anuualLeave.setUtime(now);
			anuualLeave.setCurrentyear(cal.get(Calendar.YEAR)+"");
			anuualLeave.setUserid(userid);
			anuualLeave.setLeaveLastyear((double) 0);
			anuualLeave.setLeaveCurrentyear((double) 0);
			anuualLeave.setHasleaveCurrentyear((double) 0);
			dao.add(anuualLeave);
		}
		anuualLeave = lockByUserId(userid);
		//每年1月1号0点 年假初始化定时任务开始 不能在初始化之前计算最新年假 
		//所以如果当前时间为1月1日：0时0分时-0时30分不做计算操作
		if(isJobSchedulerRunTime(new Date())){
			return anuualLeave;
		}
		//计算今年年假
		Date entrylDate = userInfomation.getStart_work_time();//开始工作日期
		Date startWorkDate = userById.getEntryDate();//入职日期
		String year = DateUtils.getYear(now);
		Date firstDayOfCurrent = DateUtils.parseDateS(year+"-01-01");//当前年份第一天
		//DateUtils
		if(entrylDate!=null){
			//当前年分入职的员工年假：分两种，一种是过年假前入职的，为-5，一种是过年假后入职的，为0
			if(startWorkDate!=null){
				if(year.equals(DateUtils.getYear(startWorkDate))){//当年入职
					Double leavecurrentyear = anuualLeave.getLeaveCurrentyear();
					anuualLeave.setUtime(now);
					anuualLeave.setLeaveCurrentyear(0d);
					if(leavecurrentyear!=0.0d){
						dao.update(anuualLeave);
					}
					Double actualLeaveVacation = anuualLeave.getActualLeaveVacation();
					Double yearVacation = userInfomation.getYearVacation();
					if(yearVacation==null ||(yearVacation!=null && actualLeaveVacation!=null && actualLeaveVacation.doubleValue()!=yearVacation.doubleValue())){
						userInfomationDao.updateLeaveVacation(userid,actualLeaveVacation);
					}
					return anuualLeave;
				}
			}
			Calendar entrydateTemp = Calendar.getInstance();
			entrydateTemp.setTime(new Date(entrylDate.getTime()));
			entrydateTemp.set(Calendar.YEAR, Integer.valueOf(year));
			Date entrylDateOfCurrentYear=entrydateTemp.getTime();//今年的年份：参加工作时间的月份：参加工作时间的日期
			int diffYearDistToDay = getDiffYear(entrylDate,now);
			int diffYearDistFirstDay = getDiffYear(entrylDate,firstDayOfCurrent);
			Double vacationByYears = getVacationByYears(diffYearDistFirstDay);
			Double leavecurrentyear = anuualLeave.getLeaveCurrentyear();
			
			if(diffYearDistToDay>0){
				if(diffYearDistFirstDay==0){
					//当前时间已超过一年，但是是满工作一年后的第一年，算法：当年度在公司剩余公历天数/365天）*5天
					double vactionDayByNow = getVactionDayByNow(now,entrylDateOfCurrentYear);
					if(leavecurrentyear-vactionDayByNow!=0.0d){
						//更新
						anuualLeave.setUtime(now);
						anuualLeave.setLeaveCurrentyear(vactionDayByNow);
						//anuualLeave.setIsadd(vactionDayByNow);
						dao.update(anuualLeave);
					}
				}else{
					if(leavecurrentyear-vacationByYears!=0.0d){
						//更新
						anuualLeave.setUtime(now);
						anuualLeave.setLeaveCurrentyear(vacationByYears);
						//anuualLeave.setIsadd(vacationByYears);
						dao.update(anuualLeave);
					}
				}
				
			}else{//参加工作时间距离当天 相隔小于1年 没有年假
				//anuualLeave.setIsadd(0d);
				anuualLeave.setLeaveCurrentyear(0d);
				anuualLeave.setUtime(now);
				dao.update(anuualLeave);
			}
			
		}else{
			//没有配置开始工作时间
			//返回默认
		}
		
		Double actualLeaveVacation = anuualLeave.getActualLeaveVacation();
		Double yearVacation = userInfomation.getYearVacation();
		if(yearVacation==null ||(yearVacation!=null && actualLeaveVacation!=null && actualLeaveVacation.doubleValue()!=yearVacation.doubleValue())){
			userInfomationDao.updateLeaveVacation(userid,actualLeaveVacation);
		}
		return anuualLeave;
	}



	

	private boolean isJobSchedulerRunTime(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int mon = cal.get(Calendar.MONTH)+1;
		int h = cal.get(Calendar.HOUR);
		int m = cal.get(Calendar.MINUTE);
		if(mon==1 && day==1 && h==0 && Calendar.AM==cal.get(Calendar.AM_PM) && m<30){
			return true;
		}
		return false;
	}
	public AnuualLeave  clearLastAnuualVacation(Long userid) throws ParseException{
		UserInfomation userInfomation = userInfoService.getUserInfomationByUid(userid);
		if(userInfomation==null){
			return null;
		}
		Date now=new Date();
		AnuualLeave anuualLeave = lockByUserId(userid);
		if(anuualLeave!=null){
			Double leaveLastyear = anuualLeave.getLeaveLastyear();
			//年假抵消处理=去年剩余年假-公司发放的年假天数
			String year = DateUtils.getYear(now);
			CompanyYearVaction byYear = companyYearVactionService.getByYear(year);
			double temp=0.0;
			if(byYear!=null){
				temp=byYear.getDays();
			}
			leaveLastyear=leaveLastyear-temp;
			if(leaveLastyear.doubleValue()>0.0){//抵消年假后,去年年假还有剩余,清0处理
				anuualLeave.setIsadd(0-leaveLastyear);//取反数  负值代表：去年剩余年假-今年公司发放年假 仍然大于0 用于第二年年初继续销掉这一年已请的年假hasleaveCurrentyear
				leaveLastyear=0d;
			}
			anuualLeave.setLeaveLastyear(leaveLastyear);//去年年假为负数时，记录该值。
			anuualLeave.setUtime(now);
			dao.update(anuualLeave);
		}
		return anuualLeave;
	}
	
	/**
	 * 更新年假当前年份  更新今年年假为去年年假 清空leave_currentyear字段 初始化isadd字段 清空今年已休年假 
	 * @param userid
	 * @return
	 * @throws ParseException
	 */
	public AnuualLeave  tranferAnuualVacation(Long userid) throws ParseException{
		UserInfomation userInfomation = userInfoService.getUserInfomationByUid(userid);
		if(userInfomation==null){
			return null;
		}
		Date now=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		AnuualLeave anuualLeave = lockByUserId(userid);
		if(anuualLeave!=null){
			Double leaveCurrentyear = anuualLeave.getLeaveCurrentyear();
			Double hasleaveCurrentyear = anuualLeave.getHasleaveCurrentyear();
			Double leaveLastyear = anuualLeave.getLeaveLastyear();
			Double isadd = anuualLeave.getIsadd();
			//结算去年实际剩余年假=去年剩余年假+今年剩余年假-已请的年假
			if(isadd<0d){
				//isadd为负值时  leaveLastyear一定是0
				double temp=-isadd;
				hasleaveCurrentyear=hasleaveCurrentyear-temp;//用去年年假扣除已请年假
				if(hasleaveCurrentyear<=0d){
					hasleaveCurrentyear=0d;
				}
				leaveLastyear=0d;
			}
			double lastactualleaveDays=leaveLastyear+leaveCurrentyear-hasleaveCurrentyear;
			anuualLeave.setLeaveLastyear(lastactualleaveDays);
			anuualLeave.setLeaveCurrentyear(0d);
			anuualLeave.setCurrentyear(year+"");
			anuualLeave.setHasleaveCurrentyear(0d);
			anuualLeave.setIsadd(0d);
			anuualLeave.setUtime(now);
			dao.update(anuualLeave);
		}
		return anuualLeave;
	}
	
	
	private AnuualLeave lockByUserId(Long userid) {
		List<AnuualLeave> bySqlKey = dao.getBySqlKey("lockByUserId", userid);
		return (bySqlKey!=null && bySqlKey.size()>0) ? bySqlKey.get(0):null;
	}

	/**
	 * 获取两个日期相隔的年数(整年)
	 * @return
	 * @throws ParseException 
	 */
	private static int getDiffYear(Date begin,Date end) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String formatBegin = sdf.format(begin);
		String formatEnd = sdf.format(end);
		begin=sdf.parse(formatBegin);
		end=sdf.parse(formatEnd);
		
		Calendar endCal=Calendar.getInstance();
		endCal.setTime(end);
		Calendar beginCal=Calendar.getInstance();
		beginCal.setTime(begin);
		//计算本月最后一天
		Calendar endTemp=Calendar.getInstance();
		endTemp.setTime(end);
		endTemp.add(Calendar.MONTH, 1);
		endTemp.add(Calendar.DAY_OF_MONTH, -1);
		int end_lastDay=endTemp.get(Calendar.DAY_OF_MONTH);
		Calendar beginTemp=Calendar.getInstance();
		beginTemp.setTime(begin);
		beginTemp.add(Calendar.MONTH, 1);
		beginTemp.add(Calendar.DAY_OF_MONTH, -1);
		int begin_lastDay=beginTemp.get(Calendar.DAY_OF_MONTH);
		
		
		int diff=endCal.get(Calendar.YEAR)-beginCal.get(Calendar.YEAR);
		if(diff>0){
			int temp=endCal.get(Calendar.MONTH)-beginCal.get(Calendar.MONTH);
			if(temp<0){
				diff=diff-1;
			}else if(temp==0){
				int temp1=endCal.get(Calendar.DAY_OF_MONTH)-beginCal.get(Calendar.DAY_OF_MONTH);
				if(end_lastDay==Calendar.DAY_OF_MONTH && begin_lastDay==Calendar.DAY_OF_MONTH){
					//都是本月最后一天
				}else if(temp1<0){
					diff-=diff;
				}
			}
		}
		return diff;
	}
	/**
	 * 根据距离参加工作时间的年数计算应有的年假
	 * @param year 距离参加工作多少年
	 * @return
	 */
	private double getVacationByYears(int year){
		double num=0d;
		if(year>=1 && year<10){
			num=5d;
		}else if(year>=10 && year<20){
			num=10d;
		}else if(year>=20){
			num=15d;
		}
		return num;
	}
	
	private double getVactionDayByNow(Date now,Date entrylDate) {
		double num=0d;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		int diffDaysByDay = DateUtils.getDiffDaysByDay(entrylDate, now);
		if(diffDaysByDay>=0){
			num=(diffDaysByDay*5)/365;
		}
		return Double.valueOf(nf.format(num));
	}
	
}
