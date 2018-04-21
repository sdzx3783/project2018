package com.hotent.makshi.controller.finance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.TimeUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.finance.WorkOrgDto;
import com.hotent.makshi.model.finance.Workstats;
import com.hotent.makshi.service.finance.CalendarException;
import com.hotent.makshi.service.finance.WorkHourApplicationService;
import com.hotent.makshi.service.finance.WorkHourStatsService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.worktime.VacationService;
import com.sun.tools.jdi.LinkedHashMap;
@Controller
@RequestMapping("/makshi/finance/workHourStats/")
public class WorkHourStatsController extends BaseController{
	@Resource
	private WorkHourApplicationService workHourApplicationService;
	@Resource
	private WorkHourStatsService workHourStatsService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private VacationService vacationService;
	@RequestMapping("orgStats")
	@Action(description="考勤报表")
	public  ModelAndView stats(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView autoView = getAutoView();
		String queryMonth = RequestUtil.getString(request, "queryMonth");
		String orgID = RequestUtil.getString(request, "orgID");
		if(StringUtils.isEmpty(queryMonth)){
			queryMonth=DateUtils.formatDateS(new Date()).substring(0,7);
		}
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		if(StringUtils.isEmpty(orgID)){
			if(currentOrg!=null){
				orgID = currentOrg.getOrgId()+"";
			}else{
				orgID="10000007780616";//默认办公室
			}
		}
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		//取得当月第一天为星期几
		int firstDay=TimeUtil.getWeekDayOfMonth(year, mon);
		// 取法定节假日
		Map<Integer, String> vacationMap = vacationService.getByYearMon(year+"-"+mon+"-01", year+"-"+mon+"-"+mondDays);
		Map<String, String> vacationStrMap=new HashMap<>();
		Set<Entry<Integer,String>> entrySet = vacationMap.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			Integer key = entry.getKey();
			vacationStrMap.put(year+"-"+String.format("%02d", mon)+"-"+String.format("%02d", key),entry.getValue());
		}
		SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgID));
		autoView.addObject("orgID", sysOrg.getOrgId()).addObject("org", sysOrg.getOrgName());
		String orgPath = sysOrg.getPath();
		Map<String, List<Workstats>> userWorkStats = workHourStatsService.getUserWorkStats(queryMonth,orgPath,vacationMap);
		String[] dayStrs=new String[]{"日","一","二","三","四","五","六"};
		String s = dayStrs[firstDay-1];
		List<String> daysStr=new ArrayList<>();
		daysStr.add(s);
		int i=firstDay;
		while(true){
			if(daysStr.size()==mondDays){
				break;
			}
			daysStr.add(dayStrs[i++%7]);
		}
		return autoView.addObject("userWorkStats", userWorkStats).addObject("mondDays", mondDays)
				.addObject("firstDay", firstDay).addObject("daysStr", daysStr).addObject("queryMonth", queryMonth)
				.addObject("vacationMap", vacationStrMap);
		
	}
	
	
	@RequestMapping("exportOrgStatsExcel")
	public void exportOrgStatsExcel(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String queryMonth = RequestUtil.getString(request, "queryMonth");
		String orgID = RequestUtil.getString(request, "orgID");
		if(StringUtils.isEmpty(queryMonth)){
			queryMonth=DateUtils.formatDateS(new Date()).substring(0,7);
		}
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		if(StringUtils.isEmpty(orgID)){
			if(currentOrg!=null){
				orgID = currentOrg.getOrgId()+"";
			}else{
				orgID="10000007780616";//默认办公室
			}
		}
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		//取得当月第一天为星期几
		int firstDay=TimeUtil.getWeekDayOfMonth(year, mon);
		// 取法定节假日
		Map<Integer, String> vacationMap = vacationService.getByYearMon(year+"-"+mon+"-01", year+"-"+mon+"-"+mondDays);
		Map<String, String> vacationStrMap=new HashMap<>();
		Set<Entry<Integer,String>> entrySet = vacationMap.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			Integer key = entry.getKey();
			vacationStrMap.put(year+"-"+String.format("%02d", mon)+"-"+String.format("%02d", key),entry.getValue());
		}
		SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgID));
		String orgPath = sysOrg.getPath();
		Map<String, List<Workstats>> userWorkStats = workHourStatsService.getUserWorkStats(queryMonth,orgPath,vacationMap);
		String[] dayStrs=new String[]{"日","一","二","三","四","五","六"};
		String s = dayStrs[firstDay-1];
		List<String> daysStr=new ArrayList<>();
		daysStr.add(s);
		int i=firstDay;
		while(true){
			if(daysStr.size()>=mondDays){
				break;
			}
			daysStr.add(dayStrs[i++%7]);
		}
		InputStream export = workHourStatsService.export(userWorkStats, sysOrg.getOrgName(), mondDays, daysStr, queryMonth,vacationStrMap);
		String filename = "考勤统计表"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		//设置文件输出类型
	    response.setContentType(request.getServletContext().getMimeType(filename));  
	    response.setHeader("Content-disposition", "attachment; filename="  
	        + sheetName); 
	    //设置输出长度
	    response.setHeader("Content-Length", String.valueOf(export.available()));  
	    //获取输入流
	    BufferedInputStream bis = new BufferedInputStream(export);  
	    //输出流
	    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    //关闭流
	    bis.close();
	    bos.close();  
	}
	
	@RequestMapping("orgStatsCount")
	@Action(description="考勤报表")
	public  ModelAndView getWorkStatsCount(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView autoView = getAutoView();
		String queryMonth = RequestUtil.getString(request, "queryMonth");
		String orgID = RequestUtil.getString(request, "orgID");
		if(StringUtils.isEmpty(queryMonth)){
			queryMonth=DateUtils.formatDateS(new Date()).substring(0,7);
		}
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		if(StringUtils.isEmpty(orgID)){
			if(currentOrg!=null){
				orgID = currentOrg.getOrgId()+"";
			}else{
				orgID="10000007780616";//默认办公室
			}
		}
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		
		SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgID));
		autoView.addObject("orgID", sysOrg.getOrgId()).addObject("org", sysOrg.getOrgName());
		String orgPath = sysOrg.getPath();
		boolean isareaLevelOrg = (orgPath.split("\\.").length>3)?true:false;//是否查询片区部门
		List<WorkOrgDto> workStatsCount = null;
		try {
			// 取法定节假日
			Map<Integer, String> vacationMap = vacationService.getByYearMon(year+"-"+mon+"-01", year+"-"+mon+"-"+mondDays);
			workStatsCount=workHourStatsService.getWorkStatsCount(queryMonth,orgPath,isareaLevelOrg);
		} catch (CalendarException e) {
			autoView.addObject("errorMsg", e.getMessage()).addObject("queryMonth", queryMonth).addObject("month", mon).addObject("year", year);
			return autoView;
		}
		return autoView.addObject("workStatsCount", workStatsCount).addObject("mondDays", mondDays)
				.addObject("queryMonth", queryMonth).addObject("month", mon).addObject("year", year)
				.addObject("isareaLevelOrg", isareaLevelOrg);
		
	}
	
	@RequestMapping("exportOrgStatsCountExcel")
	public void exportOrgStatsCountExcel(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		ModelAndView autoView = getAutoView();
		String queryMonth = RequestUtil.getString(request, "queryMonth");
		String orgID = RequestUtil.getString(request, "orgID");
		if(StringUtils.isEmpty(queryMonth)){
			queryMonth=DateUtils.formatDateS(new Date()).substring(0,7);
		}
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		if(StringUtils.isEmpty(orgID)){
			if(currentOrg!=null){
				orgID = currentOrg.getOrgId()+"";
			}else{
				orgID="10000007780616";//默认办公室
			}
		}
		//取得本月的天数
		int year=Integer.valueOf(queryMonth.split("-")[0]);
		int mon=Integer.valueOf(queryMonth.split("-")[1]);
		int mondDays =TimeUtil.getDaysOfMonth(year, mon);
		
		SysOrg sysOrg = sysOrgService.getById(Long.valueOf(orgID));
		autoView.addObject("orgID", sysOrg.getOrgId()).addObject("org", sysOrg.getOrgName());
		String orgPath = sysOrg.getPath();
		boolean isareaLevelOrg = (orgPath.split("\\.").length>3)?true:false;//是否查询片区部门
		List<WorkOrgDto> workStatsCount = workHourStatsService.getWorkStatsCount(queryMonth,orgPath,isareaLevelOrg);
		InputStream export = workHourStatsService.exportWorkCount(workStatsCount, sysOrg.getOrgName(),year,mon,isareaLevelOrg);
		String filename = "考勤统计表"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		//设置文件输出类型
	    response.setContentType(request.getServletContext().getMimeType(filename));  
	    response.setHeader("Content-disposition", "attachment; filename="  
	        + sheetName); 
	    //设置输出长度
	    response.setHeader("Content-Length", String.valueOf(export.available()));  
	    //获取输入流
	    BufferedInputStream bis = new BufferedInputStream(export);  
	    //输出流
	    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    //关闭流
	    bis.close();
	    bos.close();  
	}

}
