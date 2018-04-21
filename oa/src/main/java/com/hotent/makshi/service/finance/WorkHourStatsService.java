package com.hotent.makshi.service.finance;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.core.util.DateUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.finance.OverTime;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.finance.WorkCount;
import com.hotent.makshi.model.finance.WorkOrgDto;
import com.hotent.makshi.model.finance.Workstats;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.dao.worktime.CalendarSettingDao;
import com.hotent.platform.dao.worktime.VacationDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.worktime.CalendarSetting;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.worktime.VacationService;
import com.sun.tools.classfile.Annotation.element_value;


@Service
public class WorkHourStatsService
{
	@Resource
	private WorkHourApplicationService workHourApplicationService;
	@Resource
	private CalendarSettingDao calendarSettingDao;
	@Resource
	private VacationDao vacationDao;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private VacationService vacationService;
	private static Logger logger=LoggerFactory.getLogger(WorkHourStatsService.class);
	public List<CalendarSetting> getNeedWorkedCalByIdYearMon(String queryMonth){
		int year=0;
		int month=0;
		year=Integer.valueOf(queryMonth.split("-")[0]);
		month=Integer.valueOf(queryMonth.split("-")[1]);
		Map<String,Integer> params=new HashMap<>();
		params.put("year", year);
		params.put("month", month);
		List<CalendarSetting> bySqlKey = calendarSettingDao.getBySqlKey("getNeedWorkedCalByIdYearMon", params);
		if(bySqlKey!=null && bySqlKey.size()>0){
			return bySqlKey;
		}else{
			return new ArrayList<>();
		}
	}
	
	public Map<String,List<Workstats>> getUserWorkStats(String queryMonth,String orgPath, Map<Integer, String> vacationMap){
		//获取指定月份指定部门的所有请假记录 和调休记录
		List<Vaction> completedVactionsByOrgPathAndMonth = workHourApplicationService.getCompletedVactionsByOrgPathAndMonth(queryMonth, orgPath);
		List<OverTime> completedOvertimesByOrgPathAndMonth = workHourApplicationService.getCompletedOvertimeByOrgPathAndMonth(queryMonth, orgPath);
		List<CalendarSetting> needWorkedCalByIdYearMon = getNeedWorkedCalByIdYearMon(queryMonth);//为了统计请假天数
		Map<String,String> halfWorkDays=new HashMap<>();//存上半天班的日期
		
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		List<String> needNotWorkDays=new ArrayList<>();
		for (int i=1;i<=mondDays;i++) {
			needNotWorkDays.add(queryMonth+"-"+String.format("%02d", i));
		}
		for (CalendarSetting calendarSetting : needWorkedCalByIdYearMon) {
			String wtName = calendarSetting.getWtName();
			Short days = calendarSetting.getDays();
			if("上午".equals(wtName)){
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "上午");
			}
			if("下午".equals(wtName)){
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "下午");
			}
			needNotWorkDays.remove(queryMonth+"-"+String.format("%02d", days));
		}
		
		//获取指定月份每天指定部门的所有填报记录(一个月份的所有日期)
		List<Workstats> workStatsByOrgPathAndMonth = workHourApplicationService.getWorkStatsByOrgPathAndMonth(queryMonth, orgPath);
		Map<String,List<Workstats>> userDataMap=new LinkedHashMap();
		for (Workstats workstats : workStatsByOrgPathAndMonth) {
			String userid = workstats.getUserid();
			if(userDataMap.containsKey(userid)){
				List<Workstats> list = userDataMap.get(userid);
				struct(workstats, completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth,vacationMap,needWorkedCalByIdYearMon,halfWorkDays,needNotWorkDays);
				list.add(workstats);
			}else{
				List<Workstats> list=new ArrayList<>();
				struct(workstats, completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth,vacationMap,needWorkedCalByIdYearMon,halfWorkDays,needNotWorkDays);
				list.add(workstats);
				userDataMap.put(userid, list);
			}
		}
		return userDataMap;
	}
	
	/*public List<Workstats> getUserWorkStatsList(String queryMonth,String orgPath){
		//获取指定月份指定部门的所有请假记录 和调休记录
		List<Vaction> completedVactionsByOrgPathAndMonth = workHourApplicationService.getCompletedVactionsByOrgPathAndMonth(queryMonth, orgPath);
		List<OverTime> completedOvertimesByOrgPathAndMonth = workHourApplicationService.getCompletedOvertimeByOrgPathAndMonth(queryMonth, orgPath);
		
		//获取指定月份每天指定部门的所有填报记录(一个月份的所有日期)
		List<Workstats> workStatsByOrgPathAndMonth = workHourApplicationService.getWorkStatsByOrgPathAndMonth(queryMonth, orgPath);
		for (Workstats workstats : workStatsByOrgPathAndMonth) {
			struct(workstats, completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth);
		}
		return workStatsByOrgPathAndMonth;
	}*/
	private void struct(Workstats workstats,List<Vaction> vacs, List<OverTime> overtimes, Map<Integer, String> vacationMap, List<CalendarSetting> needWorkedCalByIdYearMon, Map<String, String> halfWorkDays, List<String> needNotWorkDays){
		String day = workstats.getDay();//领导 周六上午打√ 其他日期上午下午都打√
		String weekDay = DateUtils.getWeekDay(DateUtils.parseDateS(day));
		boolean isSal=false;
		boolean isSun=false;
		if("六".equals(weekDay)){
			isSal=true;
		}
		if("日".equals(weekDay)){
			isSun=true;
		}
		int d=Integer.valueOf(day.split("-")[2]);
		Integer isleader = workstats.getIsleader();
		if((!needNotWorkDays.contains(day)) && (isleader==1 || workstats.getHadwork()>0)){
			if(!vacationMap.containsKey(d)){
				if(halfWorkDays.containsKey(day)){
					String string = halfWorkDays.get(day);
					if("上午".equals(string)){
						workstats.setAmOrBm(1);//上午考勤
					}else{
						workstats.setAmOrBm(2);//下午考勤
					}
					workstats.setHadwork(3);
				}else{
					workstats.setHadwork(7);
				}
			}
		}else{
			workstats.setHadwork(0);
		}
		
		structIsleave(workstats, vacs,halfWorkDays,needNotWorkDays);
		structIsOverTime(workstats, overtimes,halfWorkDays,needNotWorkDays);
	}
	
	private void  structIsleave(Workstats workstats,List<Vaction> vacs, Map<String, String> halfWorkDays, List<String> needNotWorkDays){
		Map<String, Object> amMap = workstats.getAmMap();
		Map<String, Object> bmMap = workstats.getBmMap();
		String uid = workstats.getUserid();
		amMap.put(Workstats.ISLEAVEKEY, 0);
		amMap.put(Workstats.LEAVETYPEKEY, "");
		bmMap.put(Workstats.ISLEAVEKEY, 0);
		bmMap.put(Workstats.LEAVETYPEKEY, "");
		boolean isWeek=false;
		String day = workstats.getDay();
		String weekDay = DateUtils.getWeekDay(DateUtils.parseDateS(day));
		if("日".equals(weekDay) || "六".equals(weekDay)){
			isWeek=true;
		}
		for (Vaction vaction : vacs) {
			String userid = vaction.getUserid();
			if(userid.equals(uid)){
				String startTimeView = vaction.getStartTimeView();//2018-01-22-下午
				String endTimeView = vaction.getEndTimeView();
				if(StringUtils.isEmpty(startTimeView) ||StringUtils.isEmpty(endTimeView))
					break;
				int between = isBetween(startTimeView,endTimeView,workstats.getDay());
				if(between!=0){
					String leavetype = vaction.getLeavetype();
					if(needNotWorkDays.contains(day)){//不需要上班的请假不计做请假
							/*(isWeek && 
							(("事假").equals(leavetype) ||("调休").equals(leavetype) 
							||("年假").equals(leavetype)||("病假").equals(leavetype))){
							 */					
					}else{
						if(between==1){
							if(halfWorkDays.containsKey(workstats.getDay())){
								if("上午".equals(halfWorkDays.get(workstats.getDay()))){
									amMap.put(Workstats.ISLEAVEKEY, 1);
									amMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
								}else{
									bmMap.put(Workstats.ISLEAVEKEY, 1);
									bmMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
								}
							}else{
								amMap.put(Workstats.ISLEAVEKEY, 1);
								amMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
								bmMap.put(Workstats.ISLEAVEKEY, 1);
								bmMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
							}
							
						}else if(between==2){
							if(halfWorkDays.containsKey(workstats.getDay()) && "下午".equals(halfWorkDays.get(workstats.getDay()))){
								
							}else{
								amMap.put(Workstats.ISLEAVEKEY, 1);
								amMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
							}
						}else if(between==3){
							if(halfWorkDays.containsKey(workstats.getDay()) && "上午".equals(halfWorkDays.get(workstats.getDay()))){
								
							}else{
								bmMap.put(Workstats.ISLEAVEKEY, 1);
								bmMap.put(Workstats.LEAVETYPEKEY, vaction.getLeavetype());
							}
						}
					}
				}
			}
		}
		
	}
	private void  structIsOverTime(Workstats workstats,List<OverTime> overTimes, Map<String, String> halfWorkDays, List<String> needNotWorkDays){
		Map<String, Object> amMap = workstats.getAmMap();
		Map<String, Object> bmMap = workstats.getBmMap();
		String uid = workstats.getUserid();
		amMap.put(Workstats.ISOVERTIMEKEY, 0);
		bmMap.put(Workstats.ISOVERTIMEKEY, 0);
		String day = workstats.getDay();
		if(!(needNotWorkDays.contains(day) || halfWorkDays.containsKey(day))){
			//不是半天班的加班 或者需要本不需要考勤的日期里加班  不算加班
			return ;
		}
		for (OverTime overTime : overTimes) {
			String userid = overTime.getUserid();
			Date afternoon=DateUtils.parseDateL(day+" 12:00:00");
			Date zeroafternoon=DateUtils.parseDateL(day+" 00:00:00");
			if(userid.equals(uid)){
				Date startTime=null;
				Date endTime=null;
				Date zeroStartTime=null;
				Date zeroEndTime=null;
				try {
					startTime = DateUtils.parse(DateUtils.formatDateL(overTime.getStartTime()));
					endTime = DateUtils.parse(DateUtils.formatDateL(overTime.getEndTime()));
					zeroStartTime = DateUtils.parseDateS(DateUtils.formatDateS(startTime));
					zeroEndTime = DateUtils.parseDateS(DateUtils.formatDateS(endTime));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if(!(zeroafternoon.compareTo(zeroStartTime)>=0
						&& zeroafternoon.compareTo(zeroEndTime)<=0)){
					continue;
				}
				String formatDateS = DateUtils.formatDateS(startTime);
				String formatDateE = DateUtils.formatDateS(endTime);
				int before = startTime.compareTo(afternoon);
				int after = endTime.compareTo(afternoon);
				boolean amIsDay=formatDateS.substring(0, 10).equals(day);//开始时间是都为同一天
				boolean bmIsDay=formatDateE.substring(0, 10).equals(day);//结束时间是都为同一天
				if(before<0){
					if(after>0){
						if(halfWorkDays.containsKey(day)){
							if("上午".equals(halfWorkDays.get(day))){
								bmMap.put(Workstats.ISOVERTIMEKEY, 1);
							}else{
								amMap.put(Workstats.ISOVERTIMEKEY, 1);
							}
						}else{
							amMap.put(Workstats.ISOVERTIMEKEY, 1);
							bmMap.put(Workstats.ISOVERTIMEKEY, 1);
						}
					}else if(after<0){
						if(amIsDay && bmIsDay){
							if(halfWorkDays.containsKey(day) && "上午".equals(halfWorkDays)){
								
							}else{
								amMap.put(Workstats.ISOVERTIMEKEY, 1);
							}
						}
					}else{
						if(halfWorkDays.containsKey(day) && "上午".equals(halfWorkDays)){
							
						}else{
							amMap.put(Workstats.ISOVERTIMEKEY, 1);
						}
					}
				}else if(before==0){
					if(after>0){
						if(halfWorkDays.containsKey(day) && "下午".equals(halfWorkDays)){
							
						}else{
							bmMap.put(Workstats.ISOVERTIMEKEY, 1);
						}
					}
				}else{
					if(after>0){
						if(halfWorkDays.containsKey(day) && "下午".equals(halfWorkDays)){
							
						}else{
							bmMap.put(Workstats.ISOVERTIMEKEY, 1);
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param startTimeView
	 * @param endTimeView
	 * @param targerTime
	 * @return 0代表不包含在日期区间内 1全天包含 2代表上午包含 3代表下午包含
	 */
	private int isBetween(String startTimeView,String endTimeView,String targerTime){
		int status=0;
		String startAmOrBm = startTimeView.split("-")[3];//下午或上午
		String endAmOrBm = endTimeView.split("-")[3];
		String startTime = startTimeView.substring(0,10);
		String endTime = endTimeView.substring(0,10);
		int targerToS = targerTime.compareTo(startTime);
		int eTotarger = endTime.compareTo(targerTime);
		if(targerToS<0 || eTotarger<0){
			return status;
		}
		if(targerToS>0 && eTotarger>0){
			status=1;
			return status;
		}else if(targerToS==0){//开始时间是同一天
			if("下午".equals(startAmOrBm)){
				if(eTotarger>0 ||(eTotarger==0 && "下午".equals(endAmOrBm))){
					status=3;//下午
				}
			}else{//上午
				if(eTotarger>0){
					status=1;//全天
				}else if(eTotarger==0){
					if("上午".equals(endAmOrBm)){
						status=2;//上午
					}else{
						status=1;
					}
				}
			}
		}else if(eTotarger==0){
			if("上午".equals(endAmOrBm)){
				/*if(targerToS<0 ||(targerToS==0 && "上午".equals(startAmOrBm))){*/
				status=2;//上午
				/*}*/
			}else{//下午
				if(targerToS>0){
					status=1;//全天
				}else if(targerToS==0){
					if("下午".equals(startAmOrBm)){
						status=3;//下午
					}else{
						status=1;
					}
				}
			}
		}
		return status;
	}
	
	public InputStream export(Map<String, List<Workstats>> userWorkStats,String orgName,int maxday,List<String> daysStr,String queryMonth, Map<String, String> vacationStrMap) {
		//proIncomeList和userCreateIncomeList为null时 
		InputStream is = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
			XSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			int row=0;
			XSSFRow rowm = sheet.createRow(row);
			XSSFCell cellTiltle = rowm.createCell(0);
			
			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			XSSFCellStyle columnTopStyle = this.getEntryColumnTopStyle(workbook);// 获取列头样式对象
			XSSFCellStyle subTitleStyle = this.getSubTitleStyle(workbook);// 获取子标题标语样式对象
			XSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
			sheet.setColumnWidth(0, 3*256);//姓名
			int cn=1;
			sheet.setColumnWidth(1, 6*256);//
			while(cn<=maxday + 2){
				cn++;
				if(cn==maxday+2){
					//考勤天数
					sheet.setColumnWidth(cn, 12*256);
				}else if(cn==maxday+3){
					//签名
					//sheet.setColumnWidth(cn, 6*256);
				}else{
					sheet.setColumnWidth(cn, 3*256);
				}
			}
			sheet.setColumnWidth(3, 3*256);//
			sheet.setColumnWidth(4, 3*256);//
			sheet.setColumnWidth(5, 3*256);//
			sheet.setColumnWidth(6, 3*256);//
			sheet.setColumnWidth(7, 3*256);//
			sheet.setColumnWidth(8, 3*256);//
			sheet.addMergedRegion(new CellRangeAddress(row, row++, 0,
					maxday + 2));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue("深 水 咨 询 公 司 考 勤 表");
			//标语1：
			XSSFRow firstSubTitle = sheet.createRow(row);
			XSSFCell firstSubTitleCell = firstSubTitle.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0,
					maxday + 2));
			firstSubTitleCell.setCellStyle(subTitleStyle);
			firstSubTitleCell.setCellValue("部门："+orgName+"                                                          "
					+ "                                                                        月份："+queryMonth);
			row++;
			XSSFRow createRow = sheet.createRow(row);
			XSSFCell cell= createRow.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue("姓名");
			sheet.addMergedRegion(new CellRangeAddress(row, row+1, 0,0));
			XSSFCell cell1= createRow.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue("日期");
			for (int i=1;i<=maxday;i++) {
				XSSFCell celTemp= createRow.createCell(i+1);
				celTemp.setCellStyle(style);
				celTemp.setCellValue(i);
				sheet.setColumnWidth(i+1, 5*256);
			}
			XSSFCell lcell1 = createRow.createCell(maxday+2);
			/*XSSFCell lcell2 = createRow.createCell(maxday+3);*/
			//XSSFCell lcell3 = createRow.createCell(maxday+3);
			sheet.addMergedRegion(new CellRangeAddress(row, row+1, maxday+2,maxday+2));
			/*sheet.addMergedRegion(new CellRangeAddress(row, row+1, maxday+3,maxday+3));*/
			//sheet.addMergedRegion(new CellRangeAddress(row, row+1, maxday+3,maxday+3));
			lcell1.setCellStyle(style);
			/*lcell2.setCellStyle(style);*/
			//lcell3.setCellStyle(style);
			lcell1.setCellValue("考勤天数");
			/*lcell2.setCellValue("考勤小时");*/
			//lcell3.setCellValue("签名");
			row++;
			XSSFRow createRow2 = sheet.createRow(row);
			XSSFCell cell2= createRow2.createCell(1);
			cell2.setCellStyle(style);
			cell2.setCellValue("星期");
			for (int i=0;i<daysStr.size();i++) {
				XSSFCell celTemp= createRow2.createCell(i+2);
				celTemp.setCellStyle(style);
				celTemp.setCellValue(daysStr.get(i));
			}
			XSSFCell lastCell = createRow2.createCell(maxday+2);//考勤天数列
			lastCell.setCellStyle(style);
			row++;
			Set<Entry<String,List<Workstats>>> entrySet = userWorkStats.entrySet();
			for (Entry<String, List<Workstats>> entry : entrySet) {
				List<Workstats> value = entry.getValue();
				String username = value.get(0).getUsername();
				XSSFRow rowTemp1 = sheet.createRow(row);
				XSSFRow rowTemp2 = sheet.createRow(row+1);
				XSSFRow rowTemp3 = sheet.createRow(row+2);
				XSSFCell row1Cell1 = rowTemp1.createCell(0);
				row1Cell1.setCellValue(username);
				row1Cell1.setCellStyle(style);
				sheet.addMergedRegion(new CellRangeAddress(row, row+2, 0,0));
				XSSFCell row1Cell2 = rowTemp1.createCell(1);
				XSSFCell row2Cell2 = rowTemp2.createCell(1);
				XSSFCell row3Cell2 = rowTemp3.createCell(1);
				row1Cell2.setCellStyle(style);
				row2Cell2.setCellStyle(style);
				row3Cell2.setCellStyle(style);
				row1Cell2.setCellValue("上");
				row2Cell2.setCellValue("下");
				row3Cell2.setCellValue("加");
				for(int i=0;i<value.size();i++){
					Workstats workstats = value.get(i);
					XSSFCell row1Cell = rowTemp1.createCell(i+2);
					XSSFCell row2Cell = rowTemp2.createCell(i+2);
					XSSFCell row3Cell = rowTemp3.createCell(i+2);
					row1Cell.setCellStyle(style);
					row2Cell.setCellStyle(style);
					row3Cell.setCellStyle(style);
					Integer hadwork = workstats.getHadwork();
					int amOrBm = workstats.getAmOrBm();
					String day = workstats.getDay();
					Map<String, Object> amMap = workstats.getAmMap();
					Map<String, Object> bmMap = workstats.getBmMap();
					if(hadwork!=0){
						if(amOrBm==0 || amOrBm==1){
							row1Cell.setCellValue("√");
						}
						if(amOrBm==0 || amOrBm==2){
							row2Cell.setCellValue("√");
						}
					}
					if(vacationStrMap.containsKey(day)){
						String d = vacationStrMap.get(day);
						row1Cell.setCellValue(d);
						row2Cell.setCellValue(d);
						//row3Cell.setCellValue(d);
					}
					Integer astatus = (Integer) amMap.get(Workstats.ISLEAVEKEY);
					Integer bstatus = (Integer) bmMap.get(Workstats.ISLEAVEKEY);
					if((!vacationStrMap.containsKey(day)) && astatus==1){
						row1Cell.setCellValue((amMap.get(Workstats.LEAVETYPEKEY)==null 
								|| StringUtils.isEmpty((String) amMap.get(Workstats.LEAVETYPEKEY)))?"假":amMap.get(Workstats.LEAVETYPEKEY).toString());
					}
					if((!vacationStrMap.containsKey(day)) && bstatus==1){
						row2Cell.setCellValue((bmMap.get(Workstats.LEAVETYPEKEY)==null 
								|| StringUtils.isEmpty((String) bmMap.get(Workstats.LEAVETYPEKEY)))?"假":bmMap.get(Workstats.LEAVETYPEKEY).toString());
					
					}
					Integer aoverStatys = (Integer) amMap.get(Workstats.ISOVERTIMEKEY);
					Integer boverStatys = (Integer) bmMap.get(Workstats.ISOVERTIMEKEY);
					if(aoverStatys==1){
						row1Cell.setCellValue("加");
					}
					if(boverStatys==1){
						row2Cell.setCellValue("加");
					}
					
				}
				XSSFCell createCell1 = rowTemp1.createCell(value.size()+2);
				//XSSFCell createCell2 = rowTemp1.createCell(value.size()+3);
				/*XSSFCell createCell3 = rowTemp1.createCell(value.size()+4);*/
				createCell1.setCellStyle(style);
				//createCell2.setCellStyle(style);
				/*createCell3.setCellStyle(style);*/
				XSSFCell createCell21 = rowTemp2.createCell(value.size()+2);
				//XSSFCell createCell22 = rowTemp2.createCell(value.size()+3);
				//XSSFCell createCell23 = rowTemp2.createCell(value.size()+4);
				createCell21.setCellStyle(style);
				//createCell22.setCellStyle(style);
				/*createCell23.setCellStyle(style);*/
				XSSFCell createCell31 = rowTemp3.createCell(value.size()+2);
				//XSSFCell createCell32 = rowTemp3.createCell(value.size()+3);
				/*XSSFCell createCell33 = rowTemp3.createCell(value.size()+4);*/
				createCell31.setCellStyle(style);
				//createCell32.setCellStyle(style);
				/*createCell33.setCellStyle(style);*/
				sheet.addMergedRegion(new CellRangeAddress(row, row+2, value.size()+2,value.size()+2));
				/*sheet.addMergedRegion(new CellRangeAddress(row, row+2, value.size()+3,value.size()+3));*/
				//sheet.addMergedRegion(new CellRangeAddress(row, row+2, value.size()+3,value.size()+3));
				
				row=row+3;
			}
			
			//页签：
			XSSFRow lastRow = sheet.createRow(row);
			XSSFCell lastRowCell = lastRow.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0,
					maxday + 2));
			lastRowCell.setCellStyle(subTitleStyle);
			lastRowCell.setCellValue("注：√=上班      加=加班   假=请假（事假、调休、年假、病假、 产假、婚假、 丧假、陪产假）");
			if (workbook != null) {
				try {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					workbook.write(output);
					byte[] ba = output.toByteArray();
					is = new ByteArrayInputStream(ba);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;// 返回的是一个输入流
	}
	
	public XSSFCellStyle getEntryColumnTopStyle(XSSFWorkbook workbook) {

		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 15);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	/*
	 * 列数据信息单元格样式
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	
	/*
	 * 列数据信息单元格样式
	 */
	public XSSFCellStyle getWorkCountStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	public XSSFCellStyle getWorkCountTopStyle(XSSFWorkbook workbook) {

		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 25);
		// 字体加粗
		//font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("宋体");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	/*
	 * 子标题信息单元格样式
	 */
	public XSSFCellStyle getSubTitleStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		// 设置字体大小
		font.setFontHeightInPoints((short) 8);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_DASHED);
		// 设置底边框颜色;
		style.setBottomBorderColor(new XSSFColor(Color.BLACK));
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_DASHED);
		// 设置左边框颜色;
		style.setLeftBorderColor(new XSSFColor(Color.BLACK));
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_DASHED);
		// 设置右边框颜色;
		style.setRightBorderColor(new XSSFColor(Color.BLACK));
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_DASHED);
		// 设置顶边框颜色;
		style.setTopBorderColor(new XSSFColor(Color.BLACK));
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	public List<WorkCount> getWorkStatsCountList(String queryMonth,String orgPath, QueryFilter queryFilter){
		List<Vaction> completedVactionsByOrgPathAndMonth = workHourApplicationService.getCompletedVactionsByOrgPathAndMonth(queryMonth, orgPath);
		List<OverTime> completedOvertimesByOrgPathAndMonth = workHourApplicationService.getCompletedOvertimeByOrgPathAndMonth(queryMonth, orgPath);
		List<CalendarSetting> needWorkedCalByIdYearMon = getNeedWorkedCalByIdYearMon(queryMonth);//为了统计请假天数
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		//List<String> allDaystrByMaxMonthDay = getAllDaystrByMaxMonthDay(queryMonth, mondDays);
		List<String> allNeedWorkDay = new ArrayList<>();
		Map<String,String> halfWorkDays=new HashMap<>();//存上半天班的日期
		List<String> needNotWorkDays=new ArrayList<>();//存不需要上班的日期(节+假)
		for (int i=1;i<=mondDays;i++) {
			needNotWorkDays.add(queryMonth+"-"+String.format("%02d", i));
		}

		// 取法定节假日
		Map<Integer, String> vacationMap = vacationService.getByYearMon(year+"-"+mon+"-01", year+"-"+mon+"-"+mondDays);
		Map<String, String> vacationStrMap=new HashMap<>();
		Set<Entry<Integer,String>> entrySet = vacationMap.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			Integer key = entry.getKey();
			vacationStrMap.put(queryMonth+"-"+String.format("%02d", key),entry.getValue());
		}
		List<String> yearVaction=new ArrayList<>();//本月公司统一放的年假  如果不是本月 集合为空（日历配置：年假）
		double yearVactionDayCount=0;//统计本月统一年假天数
		for (Entry<String, String> entry : vacationStrMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if("年假".equals(value)){
				yearVaction.add(key);
			}
		}
		yearVactionDayCount=yearVaction.size();
		Date minDate=null;
		Date maxDate=null;
		for (OverTime overTime : completedOvertimesByOrgPathAndMonth) {
			Date startTime = overTime.getStartTime();
			Date endTime = overTime.getEndTime();
			if(minDate==null || startTime.before(minDate)){
				minDate=startTime;
			}
			if(maxDate==null || maxDate.before(endTime)){
				maxDate=endTime;
			}
		}
		Map<String, String> byYearMon = new HashMap<>();
		if(minDate!=null && maxDate!=null){
			byYearMon=getByYearMon(DateUtils.formatDateS(minDate), DateUtils.formatDateS(maxDate));
		}
		String queryneedWorkedDay="";
		for (CalendarSetting calendarSetting : needWorkedCalByIdYearMon) {
			Short days = calendarSetting.getDays();
			queryneedWorkedDay+=days+",";
			allNeedWorkDay.add(queryMonth+"-"+String.format("%02d", days));
			String wtName = calendarSetting.getWtName();
			if("上午".equals(wtName)){
				if(yearVaction.contains(queryMonth+"-"+String.format("%02d", days))){
					yearVactionDayCount-=0.5;
				}
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "上午");
			}
			if("下午".equals(wtName)){
				if(yearVaction.contains(queryMonth+"-"+String.format("%02d", days))){
					yearVactionDayCount-=0.5;
				}
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "下午");
			}
			needNotWorkDays.remove(queryMonth+"-"+String.format("%02d", days));
		}
		if(StringUtils.isNotEmpty(queryneedWorkedDay)){
			queryneedWorkedDay=queryneedWorkedDay.substring(0,queryneedWorkedDay.length()-1);
		}else{
			throw new CalendarException("本月没配工作日历，请联系管理员");
		}
		int workDayCount = needWorkedCalByIdYearMon.size();
		List<WorkCount> workStatsCountByOrgPathAndMonth = workHourApplicationService.getWorkStatsCountByOrgPathAndMonth(orgPath,queryneedWorkedDay,queryFilter);
		for (WorkCount workCount : workStatsCountByOrgPathAndMonth) {
			struct(workCount,completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth,workDayCount,needWorkedCalByIdYearMon,queryMonth,byYearMon,mondDays,allNeedWorkDay,halfWorkDays,needNotWorkDays,yearVactionDayCount);
		}
		return workStatsCountByOrgPathAndMonth;
	}
	public List<WorkOrgDto> getWorkStatsCount(String queryMonth,String orgPath,boolean isareaLevelOrg){
		LinkedHashMap<String,WorkOrgDto> map=new LinkedHashMap();
		LinkedHashMap<String,WorkOrgDto> mapContainer=new LinkedHashMap();
		ArrayList<WorkOrgDto> list=new ArrayList();
		//获取指定月份指定部门的所有请假记录 和调休记录
		List<Vaction> completedVactionsByOrgPathAndMonth = workHourApplicationService.getCompletedVactionsByOrgPathAndMonth(queryMonth, orgPath);
		List<OverTime> completedOvertimesByOrgPathAndMonth = workHourApplicationService.getCompletedOvertimeByOrgPathAndMonth(queryMonth, orgPath);
		List<CalendarSetting> needWorkedCalByIdYearMon = getNeedWorkedCalByIdYearMon(queryMonth);//为了统计请假天数
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		//List<String> allDaystrByMaxMonthDay = getAllDaystrByMaxMonthDay(queryMonth,mondDays);
		List<String> allNeedWorkDay = new ArrayList<>();//存所有需要上班的日期
		Map<String,String> halfWorkDays=new HashMap<>();//存上半天班的日期
		List<String> needNotWorkDays=new ArrayList<>();//存不需要上班的日期(节+假)
		for (int i=1;i<=mondDays;i++) {
			needNotWorkDays.add(queryMonth+"-"+String.format("%02d", i));
		}
		// 取法定节假日
		Map<Integer, String> vacationMap = vacationService.getByYearMon(year+"-"+mon+"-01", year+"-"+mon+"-"+mondDays);
		Map<String, String> vacationStrMap=new HashMap<>();
		Set<Entry<Integer,String>>  vacentrySet = vacationMap.entrySet();
		for (Entry<Integer, String> entry : vacentrySet) {
			Integer key = entry.getKey();
			vacationStrMap.put(queryMonth+"-"+String.format("%02d", key),entry.getValue());
		}
		List<String> yearVaction=new ArrayList<>();//本月公司统一放的年假  如果不是本月 集合为空（日历配置：年假）
		double yearVactionDayCount=0;//统计本月统一年假天数
		for (Entry<String, String> entry : vacationStrMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if("年假".equals(value)){
				yearVaction.add(key);
			}
		}
		yearVactionDayCount=yearVaction.size();
		Date minDate=null;
		Date maxDate=null;
		for (OverTime overTime : completedOvertimesByOrgPathAndMonth) {
			Date startTime = overTime.getStartTime();
			Date endTime = overTime.getEndTime();
			if(minDate==null || startTime.before(minDate)){
				minDate=startTime;
			}
			if(maxDate==null || maxDate.before(endTime)){
				maxDate=endTime;
			}
		}
		Map<String, String> byYearMon = new HashMap<>();
		if(minDate!=null && maxDate!=null){
			byYearMon=getByYearMon(DateUtils.formatDateS(minDate), DateUtils.formatDateS(maxDate));
		}
		String queryneedWorkedDay="";
		for (CalendarSetting calendarSetting : needWorkedCalByIdYearMon) {
			Short days = calendarSetting.getDays();
			queryneedWorkedDay+=days+",";
			allNeedWorkDay.add(queryMonth+"-"+String.format("%02d", days));
			String wtName = calendarSetting.getWtName();
			if("上午".equals(wtName)){
				if(yearVaction.contains(queryMonth+"-"+String.format("%02d", days))){
					yearVactionDayCount-=0.5;
				}
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "上午");
			}
			if("下午".equals(wtName)){
				if(yearVaction.contains(queryMonth+"-"+String.format("%02d", days))){
					yearVactionDayCount-=0.5;
				}
				halfWorkDays.put(queryMonth+"-"+String.format("%02d", days), "下午");
			}
			needNotWorkDays.remove(queryMonth+"-"+String.format("%02d", days));
		}
		if(StringUtils.isNotEmpty(queryneedWorkedDay)){
			queryneedWorkedDay=queryneedWorkedDay.substring(0,queryneedWorkedDay.length()-1);
		}else{
			throw new CalendarException("本月没配工作日历，请联系管理员");
		}
		int workDayCount = needWorkedCalByIdYearMon.size();
		List<WorkCount> workStatsCountByOrgPathAndMonth = workHourApplicationService.getWorkStatsCountByOrgPathAndMonth(queryMonth, orgPath,queryneedWorkedDay);
		if(isareaLevelOrg){
			//查询部门级以下
			String orgSecondLevelid=orgPath.split("\\.")[2];
			SysOrg byId = sysOrgService.getById(Long.valueOf(orgSecondLevelid));
			WorkOrgDto workOrgDtoTarget=new WorkOrgDto();
			workOrgDtoTarget.setOrgid(orgSecondLevelid);
			workOrgDtoTarget.setOrgname(byId.getOrgName());
			List<WorkCount> workcounts = workOrgDtoTarget.getWorkcounts();
			for (WorkCount workCount : workStatsCountByOrgPathAndMonth) {
				struct(workCount,completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth,workDayCount,needWorkedCalByIdYearMon,queryMonth,byYearMon,mondDays,allNeedWorkDay,halfWorkDays,needNotWorkDays,yearVactionDayCount);
				workcounts.add(workCount);
			}
			list.add(workOrgDtoTarget);
		}else{
			for (WorkCount workCount : workStatsCountByOrgPathAndMonth) {
				struct(workCount,completedVactionsByOrgPathAndMonth,completedOvertimesByOrgPathAndMonth,workDayCount,needWorkedCalByIdYearMon,queryMonth,byYearMon,mondDays,allNeedWorkDay,halfWorkDays,needNotWorkDays,yearVactionDayCount);
				String path = workCount.getPath();
				String orgid = workCount.getOrgid();
				String orgname = workCount.getOrgname();
				String[] split = path.split("\\.");
				String orgSecondLevelid ="";
				boolean isSecondLevelOrg = (path.split("\\.").length==3)?true:false;
				if(split.length>=3){
					orgSecondLevelid=split[2];//部门二级菜单
				}
				WorkOrgDto workOrgDtoTarget=new WorkOrgDto();
				workOrgDtoTarget.setOrgid(orgid);
				workOrgDtoTarget.setPath(path);
				workOrgDtoTarget.setOrgname(orgname);
				List<WorkCount> workcountsTarget = workOrgDtoTarget.getWorkcounts();
				workcountsTarget.add(workCount);
				if(StringUtils.isNotEmpty(orgSecondLevelid) || WorkOrgDto.COMPANYORGID.equals(orgid)){
					if(WorkOrgDto.COMPANYORGID.equals(orgid)){
						if(!map.containsKey(orgSecondLevelid)){
							map.put(orgSecondLevelid, workOrgDtoTarget);
						}else{
							map.get(orgSecondLevelid).getWorkcounts().add(workCount);
						}
					}else {
						if(!map.containsKey(orgSecondLevelid)){
							map.put(orgSecondLevelid, workOrgDtoTarget);
						}else{
							WorkOrgDto workOrgDtoTemp = map.get(orgSecondLevelid);
							if(isSecondLevelOrg){
								workOrgDtoTemp.getWorkcounts().add(workCount);
							}else{
								if(!mapContainer.containsKey(orgid)){
									List<WorkOrgDto> subworkorg = workOrgDtoTemp.getSubworkorg();
									subworkorg.add(workOrgDtoTarget);
									mapContainer.put(orgid, workOrgDtoTarget);
								}else {
									WorkOrgDto workOrgDto = mapContainer.get(orgid);
									workOrgDto.getWorkcounts().add(workCount);
								}
							}
						}
					}
				}
			}
		}
		
		Set<Entry<String,WorkOrgDto>> entrySet = map.entrySet();
		for (Entry<String, WorkOrgDto> entry : entrySet) {
			list.add(entry.getValue());
		}
		return list;
	}

	private List<String> getAllDaystrByMaxMonthDay(String queryMonth, int mondDays) {
		List<String> listDays=new ArrayList<>();
		for (int i=1 ;i<=mondDays;i++) {
			String day=queryMonth+"-"+String.format("%02d", i);
			listDays.add(day);
		}
		return listDays;
	}

	private void struct(WorkCount workCount, List<Vaction> completedVactionsByOrgPathAndMonth,
			List<OverTime> completedOvertimesByOrgPathAndMonth, int workDayCount, List<CalendarSetting> needWorkedCalByIdYearMon,String queryMonth, Map<String, String> byYearMon, int mondDays, List<String> allNeedWorkDay, Map<String, String> halfWorkDays, List<String> needNotWorkDays, double yearVactionDayCount) {
		String workdaystr = workCount.getWorkdaystr();
		//应出勤=需要出勤日期的天数-只需上半天班的日期的天数*0.5
		if(StringUtils.isEmpty(workdaystr)){
			workCount.setWorkcount(0);
		}else{
			String[] split = workdaystr.split(",");
			double length = split.length;
			for (String string : split) {
				if(halfWorkDays.containsKey(string)){
					length-=0.5;
				}
			}
			workCount.setWorkcount(length);
		}
		if(1==workCount.getIsleader()){//领导不用填考勤
			workCount.setWorkcount(workDayCount-halfWorkDays.size()*0.5);
		}
		workCount.setWorkDays(workDayCount-halfWorkDays.size()*0.5);
		workCount.setYearleave(yearVactionDayCount);
		/*int yearleave=0;//年假
		int personalleave=0;//事假
		int sickleave=0;//病假
		int otherleave=0;//婚假、产假、丧假等（天）
		int absence=0;//缺勤*/
		String userid = workCount.getUserid();
		//计算当月请假天数
		String vacStrForRemark="";
		String overStrForRemark="";
		for (Vaction vaction : completedVactionsByOrgPathAndMonth) {
			if(userid.equals(vaction.getUserid())){
				String globalflowno = vaction.getGlobalflowno();
				if(StringUtils.isEmpty(globalflowno)){
					vacStrForRemark+="无，";
				}else{
					vacStrForRemark+=(globalflowno+"，");
				}
				String leavetype = vaction.getLeavetype();
				double daycount=getVactionDayCount(vaction,needWorkedCalByIdYearMon,queryMonth);
				if(Vaction.YEARLEAVEKEY.equals(leavetype)){
					workCount.setYearleave(daycount+workCount.getYearleave());
				}else if(Vaction.PERSONALLEAVEKEY.equals(leavetype)){
					workCount.setPersonalleave(daycount+workCount.getPersonalleave());
				}else if(Vaction.SICKLEAVEKEY.equals(leavetype)){
					workCount.setSickleave(daycount+workCount.getSickleave());
				}else{
					workCount.setOtherleave(daycount+workCount.getOtherleave());
				}
			}
		}
		
		//统计本月加班天数法定节假日才会出现加班的情况
		for (OverTime overTime : completedOvertimesByOrgPathAndMonth) {
			if(userid.equals(overTime.getUserid())){
				String globalflowno = overTime.getGlobalflowno();
				if(StringUtils.isEmpty(globalflowno)){
					overStrForRemark+="无，";
				}else{
					overStrForRemark+=(globalflowno+"，");
				}
				/*Date startTime = overTime.getStartTime();
				String formatDateS = DateUtils.formatDateS(startTime);*/
				//加班小时统计在加班申请申请日期的月份里(防止跨月加班  多个月份都统计了加班小时)
				workCount.setOverhours(workCount.getOverhours()+overTime.getLegalovertimes());
				
			}
		}
		//计算该用户的加班天数(加班:法定节假日和上半天班的情况下才会出现加班)
		double overTimeDayCount = getOverTimeDayCount(workCount.getUserid(),completedOvertimesByOrgPathAndMonth,needWorkedCalByIdYearMon,queryMonth,byYearMon,halfWorkDays,needNotWorkDays);
		workCount.setOverdays(overTimeDayCount);
		if(StringUtils.isNotEmpty(vacStrForRemark)){
			vacStrForRemark=vacStrForRemark.substring(0,vacStrForRemark.length()-1);
		}
		if(StringUtils.isNotEmpty(overStrForRemark)){
			overStrForRemark=overStrForRemark.substring(0,overStrForRemark.length()-1);
		}
		workCount.setVactionglobalnoStr(vacStrForRemark);
		workCount.setOvertimeglobalnoStr(overStrForRemark);
		//计算缺勤
		//计算非领导职工用户的缺席天数
		double tempOverCalDays=0;
		if(1!=workCount.getIsleader()){
			double absense = getAbsense(workCount,mondDays,workDayCount,byYearMon,allNeedWorkDay,completedVactionsByOrgPathAndMonth,halfWorkDays,needNotWorkDays);
			workCount.setAbsence(absense);
			//获取普通员工在已考勤的日期里请假的情况导致考勤多算了的天数（前面统计的都是考勤的日期的天数总数，没有考虑考勤的日期里包含请假的情况！）
			tempOverCalDays=getCountInHadWorkDates(workCount,allNeedWorkDay,completedVactionsByOrgPathAndMonth,halfWorkDays);
		}
		/*double absence=workDayCount-workCount.getWorkcount()-workCount.getSickleave()-workCount.getPersonalleave()-workCount.getOtherleave()
				-workCount.getYearleave();*/
		if(1==workCount.getIsleader()){//领导不用填考勤
			//领导的出勤天数要减去领导请的假
			workCount.setWorkcount(workCount.getWorkcount()+yearVactionDayCount-(workCount.getSickleave()+workCount.getPersonalleave()+workCount.getOtherleave()
					+workCount.getYearleave()));
		}
		workCount.setWorkcount(workCount.getWorkcount()+overTimeDayCount-tempOverCalDays);//缺勤计算后 在累加加班天数
	}
	/**
	 * 获取普通员工在已考勤的日期里请假的情况导致考勤多算了的天数（前面统计的都是考勤的日期的天数总数，没有考虑考勤的日期里包含请假的情况！）
	 * @param workCount
	 * @param allNeedWorkDay 当月所需要考勤的所有日期集合
	 * @param completedVactionsByOrgPathAndMonth 
	 * @param halfWorkDays 
	 * @return
	 */
	private double getCountInHadWorkDates(WorkCount workCount, List<String> allNeedWorkDayList, List<Vaction> completedVactionsByOrgPathAndMonth, Map<String, String> halfWorkDays) {
		double d=0;
		String userid = workCount.getUserid();
		List<Vaction> oweUser=new ArrayList<>();
		for (Vaction vaction : completedVactionsByOrgPathAndMonth) {
			if(vaction.getUserid().equals(userid)){
				oweUser.add(vaction);
			}
		}
		String workdaystr = workCount.getWorkdaystr();
		List<String> hadWorkDayList=new ArrayList<>();
		List<String> allNeedWorkDay=new ArrayList<>();
		allNeedWorkDay.addAll(allNeedWorkDayList);
		if(StringUtils.isNotEmpty(workdaystr)){
			String[] split = workdaystr.split(",");
			hadWorkDayList=Arrays.asList(split);
		}
		allNeedWorkDay.retainAll(hadWorkDayList);//获取实际需要考勤且已考勤的日期
		if(allNeedWorkDay.isEmpty()){
			return d;
		}
		for (String targetDay : allNeedWorkDay) {
			for (Vaction vaction : oweUser) {
				String startTimeView = vaction.getStartTimeView();//2018-01-22-下午
				String endTimeView = vaction.getEndTimeView();
				if(StringUtils.isEmpty(startTimeView) ||StringUtils.isEmpty(endTimeView))
					break;
				int between = isBetween(startTimeView,endTimeView,targetDay);
				if(between!=0){
					if(between==1){
						//全天请假 同时 填了考勤  
						if(halfWorkDays.containsKey(targetDay)){//实际只需考勤半天  前面代码多计算了半天
							d+=0.5;
						}else{
							//实际只需考勤全天   前面代码多计算了一天
							d+=1;
						}
					}else if(between==2){
						//上午请假 同时 填了考勤  
						if(halfWorkDays.containsKey(targetDay)){//实际只需考勤半天  
							if("上午".equals(halfWorkDays.get(targetDay))){
								//实际只需考勤上午 前面代码多计算了0.5天
								d+=0.5;
							}else{
								//实际只需考勤下午 前面代码没有多计算
							}
						}else{
							//实际只需考勤全天   前面代码多计算了0.5天
							d+=0.5;
						}
					}else if(between==3){
						//下午请假 同时 填了考勤  
						if(halfWorkDays.containsKey(targetDay)){//实际只需考勤半天  
							if("上午".equals(halfWorkDays.get(targetDay))){
								//实际只需考勤上午 前面代码没有多计算
							}else{
								//实际只需考勤下午 前面代码多计算了0.5天
								d+=0.5;
							}
						}else{
							//实际只需考勤全天   前面代码多计算了0.5天
							d+=0.5;
						}
					}
				}
			}
		}
		return d;
	}

	/**
	 * 
	 * @param workCount 
	 * @param mondDays 月份天数
	 * @param workDayCount 月份需要考勤天数
	 * @param byYearMon 节假日集合
	 * @param allDaystrByMaxMonthDay 月份需要考勤的所有日期集合
	 * @param completedVactionsByOrgPathAndMonth 请假流程数据
	 * @param halfWorkDays 
	 * @param needNotWorkDays 
	 * @return
	 */
	private double getAbsense(WorkCount workCount, int mondDays, int workDayCount, Map<String, String> byYearMon, List<String> allNeedWorkDayList, List<Vaction> completedVactionsByOrgPathAndMonth, Map<String, String> halfWorkDays, List<String> needNotWorkDays) {
		double d=0;
		double wc = workCount.getWorkcount();
		if(wc==workDayCount){
			return d;
		}
		String userid = workCount.getUserid();
		List<Vaction> oweUser=new ArrayList<>();
		for (Vaction vaction : completedVactionsByOrgPathAndMonth) {
			if(vaction.getUserid().equals(userid)){
				oweUser.add(vaction);
			}
		}
		String workdaystr = workCount.getWorkdaystr();
		List<String> hadWorkDayList=new ArrayList<>();
		List<String> allNeedWorkDay=new ArrayList<>();
		allNeedWorkDay.addAll(allNeedWorkDayList);
		if(StringUtils.isNotEmpty(workdaystr)){
			String[] split = workdaystr.split(",");
			hadWorkDayList=Arrays.asList(split);
		}
		allNeedWorkDay.removeAll(hadWorkDayList);//移除已考勤的日期 
		if(allNeedWorkDay.size()>0){//没考勤的日期里判断有没有请假 没有请假即代表缺勤
			for (String daystr : allNeedWorkDay) {
				List<String> vacTitleList=new ArrayList<>();
				for (Vaction vaction : oweUser) {
					String startTimeView = vaction.getStartTimeView();
					String endTimeView = vaction.getEndTimeView();
					int between = isBetween(startTimeView, endTimeView, daystr);
					if(between!=0){
						vacTitleList.add(between+"");
					}
				}
				if(vacTitleList.size()==0){
					//全天缺勤
					if(halfWorkDays.containsKey(daystr)){
						d+=0.5;
					}else{
						d+=1;
					}
				}else if(vacTitleList.contains("1") ||(vacTitleList.contains("2") && vacTitleList.contains("3"))){
					//全天请假了 不算缺勤
					
				}else{
					//半天请假 半天缺勤
					if(vacTitleList.contains("2")){
						//上午请假
						if(halfWorkDays.containsKey(daystr) && "上午".equals(halfWorkDays.get(daystr))){
							
						}else{
							d+=0.5;
						}
					}else if(vacTitleList.contains("3")){
						//下午请假
						if(halfWorkDays.containsKey(daystr) && "下午".equals(halfWorkDays.get(daystr))){
							
						}else{
							d+=0.5;
						}
					}
					
				}
			}
		}
		return d;
	}
	
	/**
	 * 通过加班流程计算本月加班时间 (前提: 只有节假日时、上半天班的情况才存在加班  超过12:00   算作一天)
	 * @param userid 
	 * @param completedOvertimesByOrgPathAndMonth
	 * @param needWorkedCalByIdYearMon
	 * @param queryMonth
	 * @param vacationMap
	 * @param halfWorkDays 
	 * @param needNotWorkDays 
	 * @return
	 */
	private double getOverTimeDayCount(String userid, List<OverTime> completedOvertimesByOrgPathAndMonth,
			List<CalendarSetting> needWorkedCalByIdYearMon, String queryMonth, Map<String, String> vacationMap, Map<String, String> halfWorkDays, List<String> needNotWorkDays) {
		double s=0;
		List<OverTime> oweUser=new ArrayList<>();//此用户的加班申请
		for (OverTime overTime : completedOvertimesByOrgPathAndMonth) {
			String uid = overTime.getUserid();
			if(uid.equals(userid)){
				oweUser.add(overTime);
			}
		}
		if(oweUser.size()<=0){
			return s;
		}
		//List<String> date=new ArrayList<>();//本月节假日 日期集合
		LinkedHashMap<String, String> dateMap=new LinkedHashMap<>();//本月节假日(节+假)、半天上班 日期集合
		Map<String,Map<String,Integer>> map=new HashMap<>();//上午、下午是否加班集合
		//Set<String> keySet = vacationMap.keySet();
		for (String e : needNotWorkDays) {
			dateMap.put(e,"全天");
			HashMap<String, Integer> ambpMap=new HashMap<>();
			ambpMap.put("am", 0);
			ambpMap.put("bm", 0);
			map.put(e, ambpMap);
		}
		for (Entry<String, String> entry : halfWorkDays.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			dateMap.put(key, value);
			HashMap<String, Integer> ambpMap=new HashMap<>();
			ambpMap.put("am", 0);
			ambpMap.put("bm", 0);
			map.put(key, ambpMap);
		}
		for (Entry<String, String> en : dateMap.entrySet()) {
			String day=en.getKey();
			String value = en.getValue();
			Date afternoon=DateUtils.parseDateL(day+" 12:00:00");
			Date zeroafternoon=DateUtils.parseDateL(day+" 00:00:00");
			for (OverTime overTime : oweUser) {
				Date startTime=null;
				Date endTime=null;
				Date zeroStartTime=null;
				Date zeroEndTime=null;
				try {
					startTime = DateUtils.parse(DateUtils.formatDateL(overTime.getStartTime()));
					endTime = DateUtils.parse(DateUtils.formatDateL(overTime.getEndTime()));
					zeroStartTime = DateUtils.parseDateS(DateUtils.formatDateS(overTime.getStartTime()));
					zeroEndTime = DateUtils.parseDateS(DateUtils.formatDateS(overTime.getEndTime()));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if(!(zeroafternoon.compareTo(zeroStartTime)>=0
						&& zeroafternoon.compareTo(zeroEndTime)<=0)){
					continue;
				}
				String formatDateS = DateUtils.formatDateS(startTime);
				String formatDateE = DateUtils.formatDateS(endTime);
				int before = startTime.compareTo(afternoon);
				int after = endTime.compareTo(afternoon);
				boolean amIsDay=formatDateS.substring(0, 10).equals(day);//开始时间是都为同一天
				boolean bmIsDay=formatDateE.substring(0, 10).equals(day);//结束时间是都为同一天
				Map<String, Integer> aorbMap = map.get(day);
				if(before<0){
					if(after>0){
						/*amMap.put(Workstats.ISOVERTIMEKEY, 1);
						bmMap.put(Workstats.ISOVERTIMEKEY, 1);*/
						if("上午".equals(value)){
							aorbMap.put("bm", 1);
						}else if("下午".equals(value)){
							aorbMap.put("am", 1);
						}else {
							aorbMap.put("am", 1);
							aorbMap.put("bm", 1);
						}
					}else if(after<0){
						if(amIsDay && bmIsDay){
							//amMap.put(Workstats.ISOVERTIMEKEY, 1);
							if("上午".equals(value)){
							}else if("下午".equals(value)){
								aorbMap.put("am", 1);
							}else {
								aorbMap.put("am", 1);
							}
						}
					}else{
						//amMap.put(Workstats.ISOVERTIMEKEY, 1);
						if("上午".equals(value)){
						}else if("下午".equals(value)){
							aorbMap.put("am", 1);
						}else {
							aorbMap.put("am", 1);
						}
					}
				}else if(before==0){
					if(after>0){
						//bmMap.put(Workstats.ISOVERTIMEKEY, 1);
						if("上午".equals(value)){
							aorbMap.put("bm", 1);
						}else if("下午".equals(value)){
						}else {
							aorbMap.put("bm", 1);
						}
					}
				}else{
					if(after>0){
						//bmMap.put(Workstats.ISOVERTIMEKEY, 1);
						if("上午".equals(value)){
							aorbMap.put("bm", 1);
						}else if("下午".equals(value)){
						}else {
							aorbMap.put("bm", 1);
						}
					}
				}
			}
		}
		Set<Entry<String,Map<String,Integer>>> entrySet = map.entrySet();
		for (Entry<String, Map<String, Integer>> entry : entrySet) {
			Map<String, Integer> value = entry.getValue();
			if(1==value.get("am")){
				s+=0.5;
			}
			if(1==value.get("bm")){
				s+=0.5;
			}
		}
		return s;
	}

	private double getVactionDayCount(Vaction vaction, List<CalendarSetting> needWorkedCalByIdYearMon,String queryMonth) {
		double s=0;
		/*for (CalendarSetting calendarSetting : needWorkedCalByIdYearMon) {
			Short days = calendarSetting.getDays();
			String target=queryMonth+"-"+String.format("%02d", days);
			boolean isWeek=false;
			String weekDay = DateUtils.getWeekDay(DateUtils.parseDateS(target));
			if("日".equals(weekDay) || "六".equals(weekDay)){
				isWeek=true;
			}
			int between = isBetween(vaction.getStartTimeView(), vaction.getEndTimeView(), target);
			if(between>0){
				String leavetype = vaction.getLeavetype();
				if(isWeek && 
						(("事假").equals(leavetype) ||("调休").equals(leavetype) 
						||("年假").equals(leavetype)||("病假").equals(leavetype))){
					//周六周日不计算请假
				}else{
					if(between==1){
						s=s+1;
					}else{
						s=s+0.5;
					}
				}
			}
		}*/
		String startTimeView = vaction.getStartTimeView();
		String endTimeView = vaction.getEndTimeView();
		String startTimeStr=startTimeView.substring(0, 10);
		String endTimeStr = endTimeView.substring(0, 10);
		try {
			List<String> monthBetween = VactionUtils.getMonthBetween(startTimeStr, endTimeStr);
			if(monthBetween.size()>1){
				//取得本月的天数
				int year=Integer.valueOf(queryMonth.split("-")[0]);
				int mon=Integer.valueOf(queryMonth.split("-")[1]);
				int mondDays =TimeUtil.getDaysOfMonth(year, mon);
				
				String startTimeViewTemp=startTimeView;
				String endTimeViewTemp=endTimeView;
				//跨月份了  使用程序计算本月请假天数 和请假流程算法一致
				if(startTimeStr.startsWith(queryMonth)){
					//请假开始月份==查询月份
					endTimeViewTemp=year+"-"+String.format("%02d",mon)+"-"+String.format("%02d",mondDays)+"-下午";
				}else if(endTimeStr.startsWith(queryMonth)){
					//请假结束月份==查询月份
					startTimeViewTemp=endTimeStr.substring(0, 7)+"-01-上午";
				}else{
					//整月都为请假状态
					startTimeViewTemp=year+"-"+String.format("%02d",mon)+"-01-上午";
					endTimeViewTemp=year+"-"+String.format("%02d",mon)+"-"+String.format("%02d",mondDays)+"-下午";
				}
				s=VactionUtils.calVacDays(startTimeViewTemp,endTimeViewTemp,vaction.getLeavetype());
			}else{
				//没跨月份  就按sql查询出的值计算
				s=vaction.getLeavedays();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("请假流程日期格式错误，流程工单号为："+vaction.getGlobalflowno());
		}
		return s;
	}

	public InputStream exportWorkCount(List<WorkOrgDto> workStatsCount, String orgName,int year,int month, boolean isareaLevelOrg) {
		InputStream is = null;
		try {
			DecimalFormat df=new DecimalFormat("##.#");
			XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
			XSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			int row=0;
			sheet.setColumnWidth(0, 8*256);//部门
			sheet.setColumnWidth(1, 12*256);//项目部
			sheet.setColumnWidth(2, 6*256);//序号
			sheet.setColumnWidth(3, 8*256);//编号
			sheet.setColumnWidth(4, 10*256);//姓名
			sheet.setColumnWidth(5, 6*256);//出勤（天）
			sheet.setColumnWidth(6, 6*256);//年休假（天）
			sheet.setColumnWidth(7, 6*256);//事假（天）
			sheet.setColumnWidth(8, 6*256);//婚假、产假、丧假等
			sheet.setColumnWidth(9, 6*256);//旷工（天）
			sheet.setColumnWidth(10, 6*256);//培训出差（天）
			sheet.setColumnWidth(11, 6*256);//病假（天）
			sheet.setColumnWidth(12, 6*256);//法定假日加班（小时）
			sheet.setColumnWidth(13, 20*256);//备注
			XSSFRow rowm = sheet.createRow(row);
			XSSFCell cellTiltle = rowm.createCell(0);
			
			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			XSSFCellStyle columnTopStyle = this.getWorkCountTopStyle(workbook);// 获取列头样式对象
			XSSFCellStyle style = this.getWorkCountStyle(workbook); // 单元格样式对象
			
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0,
					14 - 1));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(year+"年"+month+"月份考勤表");
			row++;
			XSSFRow row1 = sheet.createRow(row);
			XSSFCell createCell1 = row1.createCell(0);
			createCell1.setCellStyle(style);
			createCell1.setCellValue("深水咨询公司");
			for (int i = 1; i < 14; i++) {
				XSSFCell createCell = row1.createCell(i);
				createCell.setCellStyle(style);
			}
			sheet.addMergedRegion(new CellRangeAddress(row, row, 0,
					14 - 1));
			row++;
			XSSFRow createRow2 = sheet.createRow(row);
			String[] titles=new String[]{"部门","项目部","序号","编号","姓名","出勤（天）","年休假（天）"
					,"事假（天）","婚假、产假、丧假等","旷工（天）","培训出差（天）","病假（天）","法定假日加班（小时）","备注"};
			for(int i=0;i<=13;i++){
				XSSFCell createCell = createRow2.createCell(i);
				createCell.setCellStyle(style);
				createCell.setCellValue(titles[i]);
			}
			createRow2.setHeight((short) (60*25));
			row++;
			int cn=1;
			for (WorkOrgDto orgDto : workStatsCount) {
				List<WorkCount> workcounts = orgDto.getWorkcounts();
				List<WorkOrgDto> subworkorg = orgDto.getSubworkorg();
				int recordflag=row;
				if(workcounts.size()>0){
					int i=0;
					for (WorkCount workCount : workcounts) {
						
						String orgname = workCount.getOrgname();
						XSSFRow rowTemp = sheet.createRow(row);
						XSSFCell cell1 = rowTemp.createCell(0);
						if(i==0){
							if(isareaLevelOrg){
								cell1.setCellValue(orgDto.getOrgname());
							}else{
								cell1.setCellValue(orgname);
							}
						}
						cell1.setCellStyle(style);
						XSSFCell cell2 = rowTemp.createCell(1);
						if(isareaLevelOrg){
							cell2.setCellValue(workCount.getOrgname());
						}else{
							cell2.setCellValue("");
						}
						cell2.setCellStyle(style);
						
						XSSFCell cell3 = rowTemp.createCell(2);
						cell3.setCellValue(cn++);
						cell3.setCellStyle(style);
						
						XSSFCell cell4 = rowTemp.createCell(3);
						cell4.setCellValue(workCount.getAccount());
						cell4.setCellStyle(style);
						
						XSSFCell cell5 = rowTemp.createCell(4);
						cell5.setCellValue(workCount.getUsername());
						cell5.setCellStyle(style);
						
						XSSFCell cell6 = rowTemp.createCell(5);
						cell6.setCellValue(df.format(workCount.getWorkcount()));
						cell6.setCellStyle(style);
						
						XSSFCell cell7 = rowTemp.createCell(6);
						cell7.setCellValue(df.format(workCount.getYearleave()));
						cell7.setCellStyle(style);
						
						XSSFCell cell8 = rowTemp.createCell(7);
						cell8.setCellValue(df.format(workCount.getPersonalleave()));
						cell8.setCellStyle(style);
						
						XSSFCell cell9 = rowTemp.createCell(8);
						cell9.setCellValue(df.format(workCount.getOtherleave()));
						cell9.setCellStyle(style);
						
						XSSFCell cell10 = rowTemp.createCell(9);
						cell10.setCellValue(df.format(workCount.getAbsence()));
						cell10.setCellStyle(style);
						
						XSSFCell cell11 = rowTemp.createCell(10);
						cell11.setCellValue("");
						cell11.setCellStyle(style);
						XSSFCell cell12 = rowTemp.createCell(11);
						cell12.setCellValue("");
						cell12.setCellStyle(style);
						XSSFCell cell13 = rowTemp.createCell(12);
						cell13.setCellValue(df.format(workCount.getSickleave()));
						cell13.setCellStyle(style);
						XSSFCell cell14 = rowTemp.createCell(13);
						cell14.setCellValue(workCount.getRemark());
						cell14.setCellStyle(style);
						row++;
						i++;
					}
					if(orgDto.getRowspan().intValue()==orgDto.getWorkcounts().size()){
						if(isareaLevelOrg){
							sheet.addMergedRegion(new CellRangeAddress(recordflag, recordflag+orgDto.getRowspan()-1, 0,
									0));
							sheet.addMergedRegion(new CellRangeAddress(recordflag, recordflag+orgDto.getRowspan()-1, 1,
									1));
						}else{
							sheet.addMergedRegion(new CellRangeAddress(recordflag, recordflag+orgDto.getRowspan()-1, 0,
									1));
						}
					}else{
						sheet.addMergedRegion(new CellRangeAddress(recordflag, recordflag+orgDto.getRowspan()-1, 0,
								0));
						sheet.addMergedRegion(new CellRangeAddress(recordflag, recordflag+orgDto.getWorkcounts().size()-1, 1,
								1));
					}
				}
				for (int j = 0; j < subworkorg.size(); j++) {
					WorkOrgDto workOrgDto = subworkorg.get(j);
					List<WorkCount> subworkcounts = workOrgDto.getWorkcounts();
					for(int z=0;z<subworkcounts.size();z++){
						WorkCount workCount = subworkcounts.get(z);
						String orgname = workCount.getOrgname();
						XSSFRow rowTemp = sheet.createRow(row);
						XSSFCell cell1 = rowTemp.createCell(0);
						cell1.setCellValue("");
						cell1.setCellStyle(style);
						XSSFCell cell2 = rowTemp.createCell(1);
						if(z==0){
							cell2.setCellValue(orgname);
							sheet.addMergedRegion(new CellRangeAddress(row, row+subworkcounts.size()-1, 1,
									1));
						}
						cell2.setCellStyle(style);
						
						XSSFCell cell3 = rowTemp.createCell(2);
						cell3.setCellValue(cn++);
						cell3.setCellStyle(style);
						
						XSSFCell cell4 = rowTemp.createCell(3);
						cell4.setCellValue(workCount.getAccount());
						cell4.setCellStyle(style);
						
						XSSFCell cell5 = rowTemp.createCell(4);
						cell5.setCellValue(workCount.getUsername());
						cell5.setCellStyle(style);
						
						XSSFCell cell6 = rowTemp.createCell(5);
						cell6.setCellValue(workCount.getWorkcount());
						cell6.setCellStyle(style);
						
						XSSFCell cell7 = rowTemp.createCell(6);
						cell7.setCellValue(df.format(workCount.getYearleave()));
						cell7.setCellStyle(style);
						
						XSSFCell cell8 = rowTemp.createCell(7);
						cell8.setCellValue(df.format(workCount.getPersonalleave()));
						cell8.setCellStyle(style);
						
						XSSFCell cell9 = rowTemp.createCell(8);
						cell9.setCellValue(df.format(workCount.getOtherleave()));
						cell9.setCellStyle(style);
						
						XSSFCell cell10 = rowTemp.createCell(9);
						cell10.setCellValue(df.format(workCount.getAbsence()));
						cell10.setCellStyle(style);
						
						XSSFCell cell11 = rowTemp.createCell(10);
						cell11.setCellValue("");
						cell11.setCellStyle(style);
						XSSFCell cell12 = rowTemp.createCell(11);
						cell12.setCellValue("");
						cell12.setCellStyle(style);
						XSSFCell cell13 = rowTemp.createCell(12);
						cell13.setCellValue(df.format(workCount.getSickleave()));
						cell13.setCellStyle(style);
						XSSFCell cell14 = rowTemp.createCell(13);
						cell14.setCellValue(workCount.getRemark());
						cell14.setCellStyle(style);
						row++;
					}
				}
			}
			
			if (workbook != null) {
				try {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					workbook.write(output);
					byte[] ba = output.toByteArray();
					is = new ByteArrayInputStream(ba);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;// 返回的是一个输入流
	}
	
	//获取节假日
	public Map<String, String> getByYearMon(String statTime, String endTime){
	    Map<String, String> map = new HashMap();
	    Date startDate = TimeUtil.convertString(statTime, "yyyy-MM-dd");
	    Date endDate = TimeUtil.convertString(endTime, "yyyy-MM-dd");
	    List<Vacation> valist = vacationDao.getByYearMon(startDate, endDate);
	    for (Vacation va : valist) {
	    	String[] days = DateUtil.getDaysBetweenDate(va.getStatTime().toString(), va.getEndTime().toString());
		    for (String day : days) {
		       map.put(day, va.getName());
		    }
	     }
	     return map;
	 }
}
