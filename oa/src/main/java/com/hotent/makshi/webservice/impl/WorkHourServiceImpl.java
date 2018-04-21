package com.hotent.makshi.webservice.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.controller.finance.WorkHourApplicationController;
import com.hotent.makshi.dao.finance.ProjectTaskHourDao;
import com.hotent.makshi.model.finance.CustomTaskHour;
import com.hotent.makshi.model.finance.ProjectTaskHour;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.finance.WorkHourApplication;
import com.hotent.makshi.model.finance.WorkHourException;
import com.hotent.makshi.model.operation.OverAndAdjust;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.service.finance.ProjectMonthIncomeService;
import com.hotent.makshi.service.finance.UserWorkCostService;
import com.hotent.makshi.service.finance.WorkHourApplicationService;
import com.hotent.makshi.service.operation.OverAndAdjustService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.webservice.AppApiUtil;
import com.hotent.makshi.webservice.api.WorkHourService;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.webservice.exception.ApiExcetpion;
import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

public class WorkHourServiceImpl extends BaseService  implements WorkHourService {
	private static final Logger log=Logger.getLogger(WorkHourServiceImpl.class);
	@Resource
	private AppApiUtil appApiUtil;
	@Resource
	private WorkHourApplicationController workHourApplicationController;
	@Resource
	private WorkHourApplicationService workHourApplicationService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private OverAndAdjustService overAndAdjustService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ProcessRunDao runDao;
	@Resource
	private ProjectTaskHourDao projectTaskHourDao;
	@Resource
	private UserWorkCostService userWorkCostService;
	@Resource
	private ProjectMonthIncomeService projectMonthIncomeService;
	@Autowired
	private ProjectService projectService;
	@Resource 
	private SysOrgService sysOrgService;
	@Override
	public String list(String account) {
		QueryFilter queryFilter = new QueryFilter(request);
		configPageBean(queryFilter);
		SysUser curUser = appApiUtil.getCurrentUser(request);
		if(curUser==null){
			setResultMap(null, ReturnCode.PRAMS_ERROR);	
			return resultMap.toString();
		}
		queryFilter.getFilters().put("curID", curUser.getUserId());
		
		List<WorkHourApplication> list=workHourApplicationService.getAll(queryFilter);
		
		for (WorkHourApplication workHourApplication : list) {
			Long refRunId = workHourApplication.getRefRunid();
			workHourApplication.setRunStatus("未审批");
			if(null!=refRunId){
				workHourApplication.setRunStatus("审批结束");
				ProcessRun processRun = processRunService.getById(refRunId);
				ProcessRun processRunHis = runDao.getByHistoryId(refRunId);
				List<ProcessTask> proTasks = null;
				if(BeanUtils.isNotEmpty(processRun)){
					String businessKey = processRun.getBusinessKey();
					//获取流程关联的业务数据
					WorkHourApplication runData = workHourApplicationService.getById(Long.valueOf(businessKey));
					if(runData!=null){
						workHourApplication.setId(runData.getId());
						workHourApplication.setApplicant_time(runData.getApplicant_time());
						workHourApplication.setWork_hour_total(runData.getWork_hour_total());
						workHourApplication.setOvertime_hour_total(runData.getOvertime_hour_total());
						workHourApplication.setWork_hour_rate(runData.getWork_hour_rate());
					}
					if(processRun.getActInstId()==null) continue;
					proTasks = bpmService.getTasks(processRun.getActInstId());
					if(proTasks!=null && proTasks.size()>0){
						workHourApplication.setRunStatus(proTasks.get(0).getName());
					}
				}else{
					if(processRunHis==null){
						workHourApplication.setRunStatus("流程已被删除");//关联流程已被删除
						workHourApplication.setRefRunid(null);//为了使被删除的流程关联的数据再次被提交
					}
				}
			}
			if(workHourApplication.getRefRunid()!=null){
				workHourApplication.setIsHadSubmit(1);
			}
		}
		setResultMap(list, ReturnCode.SUCCESS);	
		return resultMap.toString();
	}
	@Override
	public String save(String workhour) {
		try{
			String parameter = request.getParameter("data");
			if(StringUtil.isEmpty(parameter)){
				throw new ApiExcetpion(ReturnCode.PRAMS_IS_NULL,"请求参数为空!");
			}
			workhour=parameter;
			JSONObject fromObject = JSONObject.fromObject(workhour);
			Map<String,Class> classMap=new HashMap<>();
			classMap.put("projectTaskHourList", ProjectTaskHour.class);
			classMap.put("customTaskHourList", CustomTaskHour.class);
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"}));
			WorkHourApplication workHourApplication = (WorkHourApplication) JSONObject.toBean(fromObject, WorkHourApplication.class, classMap);
			log.info(ReflectionToStringBuilder.toString(fromObject));
			log.info(ReflectionToStringBuilder.toString(workHourApplication));
			String returnMsg="";
			
			Date applicant_time = workHourApplication.getApplicant_time();
			String applicantID = workHourApplication.getApplicantID();
			if(workHourApplication.getId()!=null){
				if(isHadSubmit(workHourApplication.getId())){
					returnMsg = "保存失败，不能修改已被提交的考勤填报！";
					setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
					return resultMap.toString();
				}
			}
			if(StringUtils.isEmpty(applicantID)){
				returnMsg = "保存失败，申请人不能为空！";
				setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
				return resultMap.toString();
			}
			if(applicant_time==null){
				returnMsg = "保存失败，日期不能为空！";
				setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
				return resultMap.toString();
			}
			try {
				setBaseHour(workHourApplication);
				WorkHourApplicationController.validateAndCalWorkHourRate(workHourApplication);
				validate(applicant_time, Long.valueOf(workHourApplication.getApplicantID()), workHourApplication.getWork_hour_rate());
			} catch (WorkHourException e) {
				returnMsg="保存失败，"+e.getMessage();
				setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
				return resultMap.toString();
			}
			if(workHourApplication.getId()==null){
				List<WorkHourApplication> list = workHourApplicationService.getByApplicantTime(applicant_time,Long.valueOf(applicantID));
				if(list!=null && list.size()>0){
					returnMsg = "保存失败，该日期的工时填报已存在！";
					setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
					return resultMap.toString();
				}
				workHourApplicationService.save(workHourApplication);
				
			}else{
				Long id = workHourApplication.getId();
				List<WorkHourApplication> list = workHourApplicationService.getByApplicantTime(applicant_time,Long.valueOf(applicantID));
				if(list!=null && list.size()>0){
					for (WorkHourApplication workHourApplication2 : list) {
						if(id.compareTo(workHourApplication2.getId())!=0){
							returnMsg = "保存失败，该日期的工时填报已存在！";
							setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(), returnMsg);
							return resultMap.toString();
						}
					}
				}
			    workHourApplicationService.save(workHourApplication);
			}
			returnMsg=workHourApplication.getId()+"";//回显id
			setResultMap(returnMsg, ReturnCode.SUCCESS);
			return resultMap.toString();
		}catch(ApiExcetpion e){
			throw e;
		}catch(Exception e){
			setResultMap(null, ReturnCode.EXCEPTION_ERROR);
			return resultMap.toString();
		}
	}
	
	
	
	private void validate(Date applicant_time, Long userid, Integer work_hour_rate) throws WorkHourException{
		if(work_hour_rate>100){
			throw new WorkHourException("累计工作时间比例不能大于100%");
		}
		//校验今天是否请假 
		List vactions = workHourApplicationService.getVactions(applicant_time,userid);
		boolean isHalfVaction=false;
		if(vactions!=null && vactions.size()>0){
			//填报日期内已请假
			List<Vaction> vacs=vactions;
			for (Vaction vaction : vacs) {
				Date startTime = vaction.getStartTime();
				Date endTime = vaction.getEndTime();
				String startTimeStr = DateUtils.format(startTime, "yyyy-MM-dd");
				String endTimeStr = DateUtils.format(endTime, "yyyy-MM-dd");
				String applicant_timeStr = DateUtils.format(applicant_time, "yyyy-MM-dd");
				if(startTimeStr.equals(endTimeStr)){
					//请假天数小于等于1天
					String leaveStartSolt = vaction.getLeaveStartSolt();
					String leaveEndSolt = vaction.getLeaveEndSolt();
					if(leaveStartSolt!=null 
							&& leaveStartSolt.trim().equals(leaveEndSolt.trim())){
						//请半天假
						isHalfVaction=true;
						break;
					}
				}else{
					if(applicant_timeStr.equals(startTimeStr) && "2".equals(vaction.getLeaveStartSolt())){
						//请半天假
						isHalfVaction=true;
						break;
					}else if(applicant_timeStr.equals(endTimeStr) && "1".equals(vaction.getLeaveEndSolt())){
						//请半天假
						isHalfVaction=true;
						break;
					}
				}
			}
			if(isHalfVaction){
				if(work_hour_rate>50){
					throw new WorkHourException("今天半天请假，累计工作时间比例不能超过50%");
				}
				if(work_hour_rate!=50){
					throw new WorkHourException("今天半天请假，累计工作时间比例需填满50%");
				}
			}else{
				throw new WorkHourException("今天全天请假，不用填考勤填报！");
			}
		}else{
			//没请假
			if(work_hour_rate!=100){
				throw new WorkHourException("累计工作时间比例需填满100%");
			}
		}
		
		//校验今天是否调休
		/*Map<String, Object> map=new HashMap<>();
		map.put("applicant_time", applicant_time);
		map.put("userid", userid);
		List<OverAndAdjust> queryAdjustList = overAndAdjustService.queryAdjustList(map);
		boolean isOverAndAdjust=false;
		if(queryAdjustList!=null && queryAdjustList.size()>0){
			//填报日期内已调休
			for (OverAndAdjust adjust : queryAdjustList) {
				Date startTime = adjust.getStartDate();
				Date endTime = adjust.getEndDate();
				String startTimeStr = DateUtils.format(startTime, "yyyy-MM-dd");
				String endTimeStr = DateUtils.format(endTime, "yyyy-MM-dd");
				String applicant_timeStr = DateUtils.format(applicant_time, "yyyy-MM-dd");
				if(startTimeStr.equals(endTimeStr)){
					//调休天数小于等于1天
					Integer adjustStartSolt = adjust.getMorning();
					Integer adjustEndSolt = adjust.getAfternoon();
					if(adjustStartSolt!=null && adjustEndSolt!=null
							&& adjustStartSolt.intValue()==adjustEndSolt.intValue()){
						//调休半天
						isOverAndAdjust=true;
						break;
					}
				}else{
					if(applicant_timeStr.equals(startTimeStr) && "1".equals(adjust.getMorning())){
						//调休半天
						isOverAndAdjust=true;
						break;
					}
				}
			}
			if(isOverAndAdjust){
				if(work_hour_rate>50){
					throw new WorkHourException("今天半天调休，累计工作时间比例不能超过50%");
				}
			}else{
				throw new WorkHourException("今天全天调休，不用填考勤填报！");
			}
		}*/
	}
	@Override
	public String get(String account, String id) {
		WorkHourApplication workHourApplication=workHourApplicationService.getById(Long.valueOf(id));
		List<ProjectTaskHour> projectTaskHourList=workHourApplicationService.getProjectTaskHourList(Long.valueOf(id));
		List<CustomTaskHour> customTaskHourList=workHourApplicationService.getCustomTaskHourList(Long.valueOf(id));
		if(workHourApplication==null){
			setResultMap(null, ReturnCode.SUCCESS);
			return resultMap.toString();
		}else{
			boolean hadSubmit = isHadSubmit(Long.valueOf(id));
			if(hadSubmit){
				workHourApplication.setIsHadSubmit(1);
			}
			workHourApplication.setCustomTaskHourList(customTaskHourList);
			workHourApplication.setProjectTaskHourList(projectTaskHourList);
			setResultMap(workHourApplication, ReturnCode.SUCCESS);
			return resultMap.toString();
		}
	}
	@Override
	public String getProListData(String account, String select_time) {
		SysUser curUser = appApiUtil.getCurrentUser(request);
		QueryFilter queryFilter = new QueryFilter(request,"taskItem");
		queryFilter.getFilters().put("participant", curUser.getUserId());
		Integer[] status=new Integer[]{1,4,5};//在建、建设期、运营期
		queryFilter.getFilters().put("status", status);
		Date date;
		try {
			date = RequestUtil.getDate(request, "select_time","yyyy-MM-dd");
			if(date==null){
				setResultMap(null, ReturnCode.SUCCESS);
				return resultMap.toString();
			}
		} catch (Exception e) {
			setResultMap(null, ReturnCode.SUCCESS);
			return resultMap.toString();
		}
		queryFilter.getFilters().put("select_time", date);
		List<Project> myProList = projectService.getProList(queryFilter);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"createBy","updateBy","createtime","updatetime","isCharger","serialVersionUID","stages"
				,"applicant","member","memberID","apptime","contractnum","constructnature","reporttime","firstmoney","reportperson"
				,"reportPersonID","proresouce","procompanyLinker","procompanyLinkerPhone","isdelete","ctime","cuser","utime","uuser"
				,"contractname","contractmoney","expectstarttime","charger","chargerID","status","legalperson","legalPersonID","tel"
				,"approvalnumber","approvaltime","remark","expectendtime","procompany","field","isCharger","contractId","applicantID"
				,"fieldArr"});
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
				if(value instanceof Date){    
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
		            return sdf.format(value);  
		        }
				return value;    
			}
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return arg0;
			}
		});
		setResultMap(myProList, ReturnCode.SUCCESS,jsonConfig);
		return resultMap.toString();
	}
	@Override
	public String del(String account, String ids) {
		if(StringUtils.isEmpty(ids)){
			setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(),"参数错误！");
			return resultMap.toString();
		}
		String[] split = StringUtils.split(ids,",");
		List<String> errorMsg=new ArrayList<>();
		for (String id : split) {
			WorkHourApplication workHourApplication=workHourApplicationService.getById(Long.valueOf(id));
			if(workHourApplication==null){
				errorMsg.add("该考勤填报已被删除");
				continue;
			}else{
				if(isHadSubmit(Long.valueOf(id))){
					errorMsg.add(DateUtils.formatDateS(workHourApplication.getApplicant_time())+":考勤填报已提交申请");
					continue;
				}
			}
			workHourApplicationService.delById(Long.valueOf(id));
		}
		
		if(errorMsg.size()>0){
			setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(),errorMsg.get(0));
		}else{
			setResultMap(null, ReturnCode.SUCCESS);
		}
		return resultMap.toString();
	}
	@Override
	public String runStart(String account) {
		SysUser curUser = appApiUtil.getCurrentUser(request);
		if(curUser==null){
			setResultMap(null, ReturnCode.PRAMS_ERROR);	
			return resultMap.toString();
		}
		try {
			ResultMessage rs = workHourApplicationController.runStartForAppApi(request,curUser);
			if(rs.getResult()==ResultMessage.Fail){
				setResultMap(null, ReturnCode.EXCEPTION_ERROR.getReturnCode(),rs.getMessage());	
				return resultMap.toString();
			}
		} catch (Exception e) {
			log.error("错误信息",e);
			setResultMap(null, ReturnCode.EXCEPTION_ERROR);	
			return resultMap.toString();
		}
		setResultMap(null, ReturnCode.SUCCESS);
		return resultMap.toString();
	}
	
	private boolean isHadSubmit(Long id){
		boolean isHadSubmit=false;
		WorkHourApplication workHourApplication=workHourApplicationService.getById(id);
		if(workHourApplication==null){
			isHadSubmit=true;
		}
		String bussinessDataId = workHourApplication.getBussinessDataId();
		if(StringUtil.isNotEmpty(bussinessDataId)){
			//工时已被提交审批
			isHadSubmit=true;
		}
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", id);
		boolean hasExistSubmitedBpm = workHourApplicationService.hasExistSubmitedBpm(filters);
		if(hasExistSubmitedBpm){
			//工时已被提交审批
			isHadSubmit=true;
		}
		return isHadSubmit;
	}
	@Override
	public String isHjsyb(String account) {
		SysUser curUser = appApiUtil.getCurrentUser(request);
		if(curUser==null){
			setResultMap(null, ReturnCode.PRAMS_ERROR);	
			return resultMap.toString();
		}
		Map<String,Integer> map=new HashMap<String, Integer>();
		Integer isHjsyb=0;//0代表否 1代表是
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(curUser.getUserId());
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000011000078")){
				//环境事业部
				isHjsyb=1;
				break;
			}
		}
		map.put("isHjsyb", isHjsyb);
		setResultMap(map, ReturnCode.SUCCESS);
		return resultMap.toString();
	}
	
	private  void setBaseHour(WorkHourApplication workHourApplication) {
		//周日-周五，默认7小时制(环境事业部 增加8、12小时制)
		//周六默认3小时 （环境事业部 增加7、8、12小时制）  除了环境事业部，其他部门都是默认小时制 没有其他选项
		String orgID = workHourApplication.getOrgID();
		int basehour=7;
		boolean isHjsyb=false;
		if(StringUtils.isNotEmpty(orgID)){
			SysOrg byId = sysOrgService.getById(Long.valueOf(orgID));
			if(byId!=null){
				if(StringUtils.isNotEmpty(byId.getPath()) && byId.getPath().contains("10000011000078")){
					isHjsyb=true;
				}
			}
		}
		boolean saturday = isSaturday(workHourApplication.getApplicant_time());
		if(isHjsyb){
			//环境事业部
			if(workHourApplication.getBasehour()==null ||workHourApplication.getBasehour()==0){
				//增加basehour为空值的判断 赋值默认值（防止app传参错误）
				if(saturday){
					basehour=3;
				}else{
					basehour=7;
				}
				workHourApplication.setBasehour(basehour);
			}
		}else{
			//非环境事业部的  后台计算赋值
			if(saturday){
				basehour=3;
			}else {
				basehour=7;
			}
			workHourApplication.setBasehour(basehour);
		}
		
	}
	public static boolean isSaturday(Date applicant_time){
		boolean flag=false;
		Calendar c = Calendar.getInstance();
		c.setTime(applicant_time);
		int dayForWeek= c.get(Calendar.DAY_OF_WEEK)-1;
		if(dayForWeek==6){//7小时工时制 周六 应为3小时
			flag=true;
		}
		return flag;
	}
}
