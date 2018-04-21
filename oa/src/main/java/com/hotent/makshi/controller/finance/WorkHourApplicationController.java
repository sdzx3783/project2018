

package com.hotent.makshi.controller.finance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fr.base.core.json.JSONArray;
import com.hotent.core.api.bpm.IBpmDefinitionService;
import com.hotent.core.api.bpm.model.IBpmDefinition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.dao.finance.ProjectTaskHourDao;
import com.hotent.makshi.model.finance.CustomTaskHour;
import com.hotent.makshi.model.finance.ProjectMonthIncome;
import com.hotent.makshi.model.finance.ProjectTaskHour;
import com.hotent.makshi.model.finance.UserWorkCost;
import com.hotent.makshi.model.finance.Vaction;
import com.hotent.makshi.model.finance.WorkHourApplication;
import com.hotent.makshi.model.finance.WorkHourException;
import com.hotent.makshi.model.operation.OverAndAdjust;
import com.hotent.makshi.service.finance.ProjectMonthIncomeService;
import com.hotent.makshi.service.finance.UserWorkCostService;
import com.hotent.makshi.service.finance.WorkHourApplicationService;
import com.hotent.makshi.service.operation.OverAndAdjustService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.makshi.webservice.api.WorkHourService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.webservice.util.ReturnCode;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 对象功能:工时填报申请 控制器类
 */
@Controller
@Scope("prototype")
@RequestMapping("/makshi/finance/workHourApplication/")
public class WorkHourApplicationController extends BaseController
{
	@Resource 
	private SysOrgService sysOrgService;
	@Resource
	private WorkHourApplicationService workHourApplicationService;
	@Resource
	private OverAndAdjustService overAndAdjustService;
	@Resource
	private ProcessRunService processRunService;
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
	private static Logger log=Logger.getLogger(WorkHourApplicationController.class);
	
	private static final long expiretime=1*60*1000;
	//private AtomicInteger succ_cnt=0;
	//private AtomicInteger fail_cnt=0;
	/**
	 * 添加或更新工时填报申请。
	 * @param request
	 * @param response
	 * @param workHourApplication 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新工时填报申请")
	public void save(HttpServletRequest request, HttpServletResponse response,WorkHourApplication workHourApplication) throws Exception
	{
		String resultMsg=null;
		try{
			Date applicant_time = workHourApplication.getApplicant_time();
			if(applicant_time==null){
				resultMsg = getText("保存", "保存失败，日期不能为空！");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			try {
				setBaseHour(workHourApplication);
				validateAndCalWorkHourRate(workHourApplication);
				validate(applicant_time, Long.valueOf(workHourApplication.getApplicantID()), workHourApplication.getWork_hour_rate());
			} catch (WorkHourException e) {
				resultMsg=getText("保存失败，"+e.getMessage());
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			
			if(workHourApplication.getId()==null){
				List<WorkHourApplication> list = workHourApplicationService.getByApplicantTime(applicant_time,ContextUtil.getCurrentUserId());
				if(list!=null && list.size()>0){
					resultMsg = getText("保存失败，该日期的工时填报已存在！");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
					return ;
				}
				workHourApplicationService.save(workHourApplication);
				resultMsg=getText("添加","工时填报申请");
			}else{
				Long id = workHourApplication.getId();
				List<WorkHourApplication> list = workHourApplicationService.getByApplicantTime(applicant_time,ContextUtil.getCurrentUserId());
				if(list!=null && list.size()>0){
					for (WorkHourApplication workHourApplication2 : list) {
						if(id.compareTo(workHourApplication2.getId())!=0){
							resultMsg = getText("保存", "保存失败，该日期的工时填报已存在！");
							writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
							return ;
						}
					}
				}
			    workHourApplicationService.save(workHourApplication);
				resultMsg=getText("更新","工时填报申请");
			}
			resultMsg=workHourApplication.getId()+"";//回显id
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 校验工时填报
	 * @param workHourApplication
	 */
	public static void validateAndCalWorkHourRate (WorkHourApplication workHourApplication) throws WorkHourException{
		Double d=0d;
		Date applicant_time = workHourApplication.getApplicant_time();
		/*if(basehour==null){
			workHourApplication.setBasehour(7);//默认为7
		}*/
		Integer basehour = workHourApplication.getBasehour();
		Integer totalrate=0;
		NumberFormat ddf=NumberFormat.getNumberInstance() ;
		ddf.setMaximumFractionDigits(2);
		List<ProjectTaskHour> projectTaskHourList=workHourApplication.getProjectTaskHourList();
		if(BeanUtils.isNotEmpty(projectTaskHourList)){
			for (ProjectTaskHour projectTaskHour : projectTaskHourList) {
				Integer project_work_rate = projectTaskHour.getProject_work_rate();
				if(project_work_rate==null){
					//throw new WorkHourException("项目工时不能为空！");
					project_work_rate=0;
					projectTaskHour.setProject_work_rate(project_work_rate);
				}
				totalrate+=project_work_rate;
				Double calWorkHourByRate = calWorkHourByRate(project_work_rate,applicant_time,basehour);
				projectTaskHour.setWork_hour(calWorkHourByRate.toString());
				d+=calWorkHourByRate;
			}
		}
		List<CustomTaskHour> customTaskHourList=workHourApplication.getCustomTaskHourList();
		if(BeanUtils.isNotEmpty(customTaskHourList)){
			for(CustomTaskHour customTaskHour:customTaskHourList){
				Integer task_work_rate = customTaskHour.getTask_work_rate();
				if(task_work_rate==null){
					//throw new WorkHourException("任务工时不能为空！");
					task_work_rate=0;
					customTaskHour.setTask_work_rate(task_work_rate);
				}
				totalrate+=task_work_rate;
				Double calWorkHourByRate = calWorkHourByRate(task_work_rate,applicant_time,basehour);
				customTaskHour.setWork_hour(calWorkHourByRate.toString());
				d+=calWorkHourByRate;
			}
		}
		workHourApplication.setWork_hour_rate(totalrate);
		workHourApplication.setWork_hour_total(d.toString());
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
	
	public static Double calWorkHourByRate(Integer rate,Date applicant_time,Integer basehour){
		int base=7;
		if(basehour!=null){
			base=basehour;
		}
		if((double)rate*base==0){
			return 0d;
		}
		return ((double)rate*base)/100;
	}
	
	
	/**
	 * 取得工时填报申请分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看工时填报申请分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter queryFilter = new QueryFilter(request,"workHourApplicationItem");
		queryFilter.getFilters().put("curID", ContextUtil.getCurrentUserId());
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
		}
		ModelAndView mv=this.getAutoView().addObject("workHourApplicationList",list);
		return mv;
	}
	
	/**
	 * 删除工时填报申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除工时填报申请")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			workHourApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除工时填报申请及其从表成功!");
			workHourApplicationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除工时填报申请成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑工时填报申请
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑工时填报申请")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		ISysUser currentUser = ContextUtil.getCurrentUser();
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		String returnUrl=RequestUtil.getPrePage(request);
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(currentUser.getUserId());
		boolean isHjsyb=false;
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000011000078")){
				//环境事业部
				isHjsyb=true;
				break;
			}
		}
		WorkHourApplication workHourApplication=workHourApplicationService.getById(id);
		if(null==workHourApplication){
			workHourApplication=new WorkHourApplication();
			workHourApplication.setApplicant_time(new Date());
			workHourApplication.setApplicant(currentUser.getFullname());
			workHourApplication.setApplicantID(currentUser.getUserId()+"");
			if(currentOrg!=null){
				workHourApplication.setOrg(currentOrg.getOrgName());
				workHourApplication.setOrgID(currentOrg.getOrgId()+"");
			}
		}
		boolean canStartFlow=true;
		//TO DO
		String bussinessDataId = workHourApplication.getBussinessDataId();
		if(StringUtil.isNotEmpty(bussinessDataId)){
			//工时已被提交审批
			canStartFlow=false;
		}
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", id);
		boolean hasExistSubmitedBpm = workHourApplicationService.hasExistSubmitedBpm(filters);
		if(hasExistSubmitedBpm){
			//工时已被提交审批
			canStartFlow=false;
		}
		if(workHourApplication==null || workHourApplication.getId()==null){
			//新建
			canStartFlow=true;
		}
		
		List<ProjectTaskHour> projectTaskHourList=workHourApplicationService.getProjectTaskHourList(id);
		List<CustomTaskHour> customTaskHourList=workHourApplicationService.getCustomTaskHourList(id);
		
		return getAutoView().addObject("workHourApplication",workHourApplication)
							.addObject("projectTaskHourList",projectTaskHourList)
							.addObject("customTaskHourList",customTaskHourList)
							.addObject("returnUrl",returnUrl).addObject("canStartFlow", canStartFlow)
							.addObject("isHjsyb", isHjsyb);
	}

	/**
	 * 取得工时填报申请明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看工时填报申请明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ISysUser currentUser = ContextUtil.getCurrentUser();
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(currentUser.getUserId());
		boolean isHjsyb=false;
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000011000078")){
				//环境事业部
				isHjsyb=true;
				break;
			}
		}
		Long id=RequestUtil.getLong(request,"id");
		WorkHourApplication workHourApplication=workHourApplicationService.getById(id);
		List<ProjectTaskHour> projectTaskHourList=workHourApplicationService.getProjectTaskHourList(id);
		List<CustomTaskHour> customTaskHourList=workHourApplicationService.getCustomTaskHourList(id);
		return getAutoView().addObject("workHourApplication",workHourApplication)
							.addObject("projectTaskHourList",projectTaskHourList)
							.addObject("customTaskHourList",customTaskHourList).addObject("isHjsyb", isHjsyb);
	}
	
	@RequestMapping("runStart")
	@Action(description="查看工时填报申请明细")
	public void  runStart(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out = response.getWriter();
		Long[] ary = RequestUtil.getLongAry(request,"id");
		if(ary==null){
			out.print(new ResultMessage(ResultMessage.Fail, "参数错误！"));
			return ;
		}
		if(ary.length==1){
			out.print(run(ary[0]));
			return ;
		}
		//String errorMsg="";
		final AtomicInteger succ_cnt = new AtomicInteger(0);
		final AtomicInteger fail_cnt = new AtomicInteger(0);
		int length = ary.length;
		if(length>4){
			out.print(new ResultMessage(ResultMessage.Fail, "一次最多只能提交4条数据！"));
			return ;
		}
		boolean timeOut=false;
		final ISysUser currentUser = ContextUtil.getCurrentUser();
		ConcurrentLinkedQueue<Thread> tasks=new ConcurrentLinkedQueue<>();
		//final CountDownLatch latch=new CountDownLatch(length);
		for (final Long id : ary) {
			Thread thread = new Thread(){
				@Override
				public void run() {
					ContextUtil.setCurrentUser(currentUser);
					ResultMessage runMess = WorkHourApplicationController.this.run(id);
					if(runMess.getResult()==ResultMessage.Fail){
						fail_cnt.getAndIncrement();
					}else{
						succ_cnt.getAndIncrement();
					}
					//latch.countDown();
				}
			};
			tasks.add(thread);
		}
		
		while(true){
			Thread poll = tasks.poll();
			if(poll==null){
				break;
			}
			long startTime=new Date().getTime();
			poll.start();
			while(poll.getState()!=Thread.State.TERMINATED && new Date().getTime()<=startTime+expiretime){
				Thread.sleep(300);
			}
			if(poll.getState()!=Thread.State.TERMINATED ){
				timeOut=true;
				poll.interrupt();//超时强制结束线程
			}
		}
		//Thread.sleep(1000);//等待1秒
		if(!timeOut){
			if(fail_cnt.get()>0){
				out.print(new ResultMessage(ResultMessage.Fail, "有"+fail_cnt+"条工时填报启动流程失败!"));
			}else {
				out.print(new ResultMessage(ResultMessage.Success, "启动流程成功!"));
			}
		}else{
			out.print(new ResultMessage(ResultMessage.Fail, "服务器忙，请稍候再试!"));
		}
		
	}
	
	public ResultMessage runStartForAppApi(HttpServletRequest request, final SysUser currentUser) throws Exception
	{
		//Long[] ary = RequestUtil.getLongAry(request,"id");
		String parameter = request.getParameter("id");
		if(StringUtils.isEmpty(parameter)){
			return new ResultMessage(ResultMessage.Fail, "参数错误!");
		}
		String[] ids = parameter.split(",");
		if(ids==null || ids.length==0){
			return new ResultMessage(ResultMessage.Fail, "参数错误!");
		}
		Long[] ary=new Long[ids.length];
		for(int i=0;i<ids.length;i++){
			try {
				ary[i]=Long.valueOf(ids[i]);
			} catch (Exception e) {
				return new ResultMessage(ResultMessage.Fail, "参数错误!");
			}
		}
		if(ary.length==1){
			ContextUtil.setCurrentUser(currentUser);
			ResultMessage runOne = run(ary[0]);
			if(runOne.getResult()==ResultMessage.Fail){
				String mess = runOne.getMessage();
				return new ResultMessage(ResultMessage.Fail, mess);
			}
			return new ResultMessage(ResultMessage.Success, "提交成功!");
		}
		final AtomicInteger succ_cnt = new AtomicInteger(0);
		final AtomicInteger fail_cnt = new AtomicInteger(0);
		int length = ary.length;
		if(length>4){
			return new ResultMessage(ResultMessage.Fail,"一次最多只能提交4条数据！");
		}
		//final CountDownLatch latch=new CountDownLatch(length);
		ConcurrentLinkedQueue<Thread> tasks=new ConcurrentLinkedQueue<>();
		boolean timeOut=false;
		for (final Long id : ary) {
			Thread thread = new Thread(){
				@Override
				public void run() {
					ContextUtil.setCurrentUser(currentUser);
					ResultMessage runMess = WorkHourApplicationController.this.run(id);
					if(runMess.getResult()==ResultMessage.Fail){
						fail_cnt.getAndIncrement();
					}else{
						succ_cnt.getAndIncrement();
					}
					
				}
			};
			tasks.add(thread);
		}
		while(true){
			Thread poll = tasks.poll();
			if(poll==null){
				break;
			}
			long startTime=new Date().getTime();
			poll.start();
			//Thread.sleep(300);
			while(poll.getState()!=Thread.State.TERMINATED && new Date().getTime()<=startTime+expiretime){
				Thread.sleep(300);
			}
			//System.out.println(poll.getState()==Thread.State.TERMINATED);
			if(poll.getState()!=Thread.State.TERMINATED ){
				timeOut=true;
				poll.interrupt();//超时强制结束线程
			}
		}
		//boolean await = latch.await(expiretime, TimeUnit.MINUTES);
		Thread.sleep(1000);//等待1秒 等待相应的流程数据更新
		if(!timeOut){
			if(fail_cnt.get()>0){
				return new ResultMessage(ResultMessage.Fail, "有"+fail_cnt+"条工时填报启动流程失败!");
			}else {
				return new ResultMessage(ResultMessage.Success, "提交成功!");
			}
		}else{
			return new ResultMessage(ResultMessage.Fail, "服务器忙，请稍候再试！");
		}
	}
	
	private  ResultMessage run(Long id){
		boolean needUnlock=false;
		boolean hadError=false;
		try {
			
			ProcessCmd processCmd = new ProcessCmd();
					//BpmUtil.getProcessCmd(request);
			String formData = getFormData(id);
			if(formData==null){
				return new ResultMessage(ResultMessage.Fail, "该工时填报不存在");
			}
			if("error".equals(formData)){
				return new ResultMessage(ResultMessage.Fail, "该工时已被提交审批");
			}
			Long userId = ContextUtil.getCurrentUserId();
			processCmd.setFormData(formData);
			Map<String,Object> formDataMap=new HashMap<>();
			formDataMap.put("curUserId", userId+"");
			formDataMap.put("curUserName", userId+"");
			formDataMap.put("actDefId", "gstbsq:1:50000000000532");
			formDataMap.put("defId", "50000000000533");
			formDataMap.put("businessKey", "");
			formDataMap.put("startNode", "");
			formDataMap.put("formKey", "gstb");
			formDataMap.put("formData", formData);
			processCmd.setFormDataMap(formDataMap);
			//start 开发环境下注释掉下面代码
			if(userId==1l){
				return new ResultMessage(ResultMessage.Fail, "超级管理员不能发起流程");
			}
			//end
			processCmd.setCurrentUserId(userId.toString());
			IBpmDefinitionService bpmDefinitionService = (IBpmDefinitionService)AppUtil.getBean(IBpmDefinitionService.class);
			IBpmDefinition bpmDefinition = null;
			   
			String temp = (String) formDataMap.get("actDefId");
			if (StringUtil.isNotEmpty(temp)) {
				processCmd.setActDefId(temp);
				bpmDefinition = bpmDefinitionService.getByActDefId(temp);
			}
			processCmd.addTransientVar("bpm_definition", bpmDefinition);
			if (BeanUtils.isNotEmpty(bpmDefinition)) {
				 String informType = "";
				 informType = bpmDefinition.getInformType();
				 processCmd.setInformType(informType);
			}
			temp = (String) formDataMap.get("businessKey");
			if (StringUtil.isNotEmpty(temp)) {
				processCmd.setBusinessKey(temp);
			}
			processCmd.setVoteContent("");
			temp = (String) formDataMap.get("startNode");
			if (StringUtil.isNotEmpty(temp)) {
				 processCmd.setStartNode(temp);
			}
			processCmd.setRelRunId(0L);
			boolean lockById = workHourApplicationService.lockById(id);
			if(!lockById) {
				return new ResultMessage(ResultMessage.Fail, "该工时已被提交审批");
			}
			needUnlock=true;
			ProcessRun run = processRunService.startProcess(processCmd);
			
			return new ResultMessage(ResultMessage.Success, "启动流程成功!");
			
		} catch (Exception ex) {
			hadError=true;
			log.error("考勤填报提交出现异常: ",ex);
			String str = MessageUtil.getMessage();
			//考勤提交后流程失败后 我的待办会多一个流程实例（流程默认失败保存的数据），考勤需要删除这个流程及业务数据 防止用户再次在我的待办提交
			workHourApplicationService.clearProcessRun(id);
			
			if (StringUtil.isNotEmpty(str)) {
				return new ResultMessage(ResultMessage.Fail, "启动流程失败:\r\n" + str);
			} else {
				String message = ex.getMessage();
				if(StringUtil.isEmpty(message)){
					message="流程启动异常！";
				}
				return new ResultMessage(ResultMessage.Fail, message);
			}
		}finally {
			if(needUnlock && hadError) {
				//有异常且是获取锁之后抛的异常才需要解锁  再删除流程实例时也应该释放锁
				workHourApplicationService.unlock(id);
			}
		}
	}
	
	private String getFormData(Long id){
		WorkHourApplication workHourApplication=workHourApplicationService.getById(id);
		if(workHourApplication==null){
			return null;
		}
		String bussinessDataId = workHourApplication.getBussinessDataId();
		if(StringUtil.isNotEmpty(bussinessDataId)){
			//工时已被提交审批
			return "error";
		}
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", id);
		boolean hasExistSubmitedBpm = workHourApplicationService.hasExistSubmitedBpm(filters);
		if(hasExistSubmitedBpm){
			//工时已被提交审批
			return "error";
		}
		workHourApplication.setBussinessDataId(id+"");
		List<ProjectTaskHour> projectTaskHourList=workHourApplicationService.getProjectTaskHourList(id);
		List<CustomTaskHour> customTaskHourList=workHourApplicationService.getCustomTaskHourList(id);
		workHourApplication.setProjectTaskHourList(projectTaskHourList);
		workHourApplication.setCustomTaskHourList(customTaskHourList);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"id","refRunid","runStatus","createBy","createtime","updateBy","updatetime","projectTaskHourList","customTaskHourList"});
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
		JSONObject fromObject = JSONObject.fromObject(workHourApplication, jsonConfig);
		JSONObject prosub=new JSONObject();
		prosub.put("tableName", "project_task_hour");
		JSONArray jsonArray=new JSONArray();
		for (ProjectTaskHour projectTaskHour : projectTaskHourList) {
			JSONObject jo=new JSONObject();
			jo.put("projectName", projectTaskHour.getProjectName());
			jo.put("proid", projectTaskHour.getProid());
			jo.put("taskName", projectTaskHour.getTaskName());
			jo.put("work_hour", projectTaskHour.getWork_hour());
			jo.put("project_work_rate", projectTaskHour.getProject_work_rate()+"");
			jo.put("overtime_hour", projectTaskHour.getOvertime_hour());
			jo.put("progress_rate", projectTaskHour.getProgress_rate());
			jo.put("remark", projectTaskHour.getRemark());
			jsonArray.put(jo);
		}
		prosub.put("fields", jsonArray.toString());
		JSONObject cussub=new JSONObject();
		cussub.put("tableName", "custom_task_hour");
		JSONArray jsonArr=new JSONArray();
		for (CustomTaskHour customTaskHour : customTaskHourList) {
			JSONObject jo=new JSONObject();
			jo.put("taskName", customTaskHour.getTaskName());
			jo.put("work_content", customTaskHour.getWork_content());
			jo.put("work_hour", customTaskHour.getWork_hour());
			jo.put("overtime_hour", customTaskHour.getOvertime_hour());
			jo.put("task_work_rate", customTaskHour.getTask_work_rate()+"");
			jo.put("progress_rate", customTaskHour.getProgress_rate());
			jo.put("remark", customTaskHour.getRemark());
			jsonArr.put(jo);
		}
		cussub.put("fields", jsonArr.toString());
		JSONObject jsonData=new JSONObject();
		JSONObject main=new JSONObject();
		JSONArray sub = new JSONArray();
		sub.put(prosub);
		sub.put(cussub);
		main.put("fields", fromObject.toString());
		jsonData.put("main", main.toString());
		jsonData.put("sub", sub.toString());
		jsonData.put("opinion", "[]");
		log.info(jsonData.toString());
		return jsonData.toString();
	}
	
	//项目工时
	@RequestMapping("workHourStats")
	@Action(description="查看工时填报统计明细")
	public ModelAndView workHourStats(HttpServletRequest request, HttpServletResponse response) throws Exception{
		QueryFilter queryFilter = new QueryFilter(request);
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if(queryTime!=null && StringUtils.isNotEmpty(queryTime.toString())){
			filters.put("queryTime", queryTime);
		}else{
			queryTime=DateUtils.format(new Date(), "yyyy-MM");
			filters.put("queryTime", queryTime);
		}
		String orgid = RequestUtil.getString(request, "orgID","-1");
		filters.put("orgid", orgid);
		return view(getAutoView(), queryTime, filters,request,response);
	}
	
	//项目工时
	@RequestMapping("proIncomeStats")
	@Action(description="查看工时填报统计和项目收入统计")
	public ModelAndView proIncomeStats(HttpServletRequest request, HttpServletResponse response) throws Exception{
		QueryFilter queryFilter = new QueryFilter(request);
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if(queryTime!=null && StringUtils.isNotEmpty(queryTime.toString())){
			filters.put("queryTime", queryTime);
		}else{
			queryTime=DateUtils.format(new Date(), "yyyy-MM");
			filters.put("queryTime", queryTime);
		}
		String orgid = RequestUtil.getString(request, "orgID","-1");
		filters.put("orgid", orgid);
		return view(getAutoView(),queryTime,filters,request,response);
	}

	private ModelAndView view(ModelAndView mv, String queryTime, Map<String, Object> filters,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String month=queryTime.split("-")[1];
		List<List<Map<String,String>>> data=new ArrayList<>();
		//查出某个月份所有的项目
		List<ProjectTaskHour> queryProHour = projectTaskHourDao.queryProHour(filters);
		List<Map<String, String>> userData = projectTaskHourDao.getUserIds(filters);
		if(userData!=null && userData.size()>0){
			for (Map<String, String> user : userData) {
				List<Map<String,String>> mapList=new ArrayList<>();
				String userid = user.get("userid");
				if(StringUtils.isNotEmpty(userid)){
					filters.put("userid",user.get("userid"));
					List<ProjectTaskHour> list = projectTaskHourDao.queryProHour(filters);
					for (ProjectTaskHour pro : queryProHour) {
						Map<String,String> ma=new HashMap<>();
						ma.put("username", user.get("username"));
						ma.put("userid", user.get("userid"));
						ma.put("workhour", "0");
						ma.put("overhour", "0");
						ma.put("proid", pro.getProid());
						ma.put("projectname", pro.getProjectName());
						for (ProjectTaskHour projectTaskHour : list) {
							String proid = projectTaskHour.getProid();
							if(proid.equals(pro.getProid())){
								ma.put("workhour", projectTaskHour.getWorkcount());
								ma.put("overhour", projectTaskHour.getOvercount());
							}
						}
						mapList.add(ma);
					}
				}
				data.add(mapList);
			}
		}
		
		List<Map<String,String>> userCostData=new ArrayList<>();//显示用户工时成本数据
		for (Map<String, String> user : userData) {
			Map<String,String> cost=new HashMap<>(); 
			cost.put("userid", user.get("userid"));
			cost.put("username", user.get("username"));
			cost.put("workcost", "0");
			cost.put("overcost", "0");
			List<UserWorkCost> list = userWorkCostService.getByUserIdAndMonth(Long.valueOf(user.get("userid")), queryTime);
			if(list!=null && list.size()>0){
				UserWorkCost userWorkCost = list.get(0);
				cost.put("workcost",
						StringUtils.isEmpty(userWorkCost.getWork_hour_cost().toString())?"0":userWorkCost.getWork_hour_cost().toString());
				cost.put("overcost",
						StringUtils.isEmpty(userWorkCost.getOver_hour_cost().toString())?"0":userWorkCost.getOver_hour_cost().toString());
			}
			userCostData.add(cost);
		}
		//显示项目成本统计
		Map<String,List<Map<String,String>>> proMap=new HashMap<>();
		for (List<Map<String,String>> mapData : data) {
			for (Map<String, String> map : mapData) {
				String proid = map.get("proid");
				if(!proMap.containsKey(proid)){
					List<Map<String, String>> proDataList=new ArrayList<>();
					proDataList.add(map);
					proMap.put(proid, proDataList);
				}else{
					List<Map<String, String>> list = proMap.get(proid);
					list.add(map);
				}
			}
		}
		List<List<String>> dataStats=new ArrayList<>();
		for (ProjectTaskHour pro : queryProHour) {
			List<String> proDataList=new ArrayList<>();
			proDataList.add(pro.getProjectName());
			double monthCount=0d;
			double yearCount=0d;
			for (Map<String,String> userCost : userCostData) {
				String userid = userCost.get("userid");
				List<Map<String, String>> list = proMap.get(pro.getProid());
				for (Map<String, String> map : list) {
					if(map.get("userid").equals(userid)){
						//项目-某人
						String workhour = map.get("workhour");
						String overhour = map.get("overhour");
						String workcost = userCost.get("workcost");
						String overcost = userCost.get("overcost");
						String cal = cal(workhour,overhour,workcost,overcost);
						proDataList.add(cal);
						monthCount+=Double.valueOf(cal);
						double yearmoncount = userWorkCostService.getYearCostByUserIdAndMon(pro.getProid(),userid,queryTime);
						yearCount+=yearmoncount;
					}
				}
				
			}
			proDataList.add(String.valueOf(monthCount));
			proDataList.add(String.valueOf(yearCount));
			
			//当年累计 数据库查询
			dataStats.add(proDataList);
		}
		Map<String,Double> lastcount=new LinkedHashMap();
		for (List<String> row : dataStats) {
			for(int i=0;i<row.size();i++){
				if(i>0){
					Double d = lastcount.get(i+"");
					if(d==null){
						d=0d;
					}
					d=d+Double.valueOf(row.get(i));
					lastcount.put(i+"", d);
				}
			}
		}
		Iterator<Entry<String, Double>> iterator = lastcount.entrySet().iterator();
		List<String> lastlist=new ArrayList<>();
		lastlist.add("");
		while(iterator.hasNext()){
			lastlist.add(String.valueOf(iterator.next().getValue()));
		}
		dataStats.add(lastlist);
		boolean isNeedProIncomeStats = mv.getViewName().contains("ProIncomeStats") || mv.getViewName().contains("ExportProIncomeStatsExcel");
		
		//显示项目收入
		if(isNeedProIncomeStats){
			List<Map<String,String>> proIncomeData=new ArrayList<>();//显示用户工时成本数据
			for (ProjectTaskHour pt : queryProHour) {
				Map<String,String> cost=new HashMap<>(); 
				cost.put("proid", pt.getProid());
				cost.put("projectName", pt.getProjectName());
				cost.put("income", "0");
				List<ProjectMonthIncome> proIncomelist=projectMonthIncomeService.getByProIdAndMonth(pt.getProid(), queryTime);
				if(proIncomelist!=null && proIncomelist.size()>0){
					ProjectMonthIncome projectMonthIncome = proIncomelist.get(0);
					cost.put("income",
							StringUtils.isEmpty(projectMonthIncome.getIncome().toString())?"0":projectMonthIncome.getIncome().toString());
				}
				proIncomeData.add(cost);
			}
			mv.addObject("proIncomeData", proIncomeData);
			//统计员工累计创收
			List<String> proIds=new ArrayList<>();
			for (ProjectTaskHour proHour : queryProHour) {
				String proid = proHour.getProid();
				proIds.add(proid);
			}
			List<List<Map<String,String>>> personalCreatedIncomelist = workHourApplicationService.getListMapByUserIdAndPros(proIds,queryTime,userData);
			mv.addObject("personalCreatedIncomelist", personalCreatedIncomelist);
		
			if(mv.getViewName().contains("ExportProIncomeStatsExcel")){
				//导出Excel
				exportProIncome(data,queryProHour,queryTime,userCostData,dataStats,month,proIncomeData,personalCreatedIncomelist,request,response);
				return null;
			}
		}
		if(mv.getViewName().contains("ExportWorkCostStatsExcel")){
			//导出Excel
			exportProIncome(data,queryProHour,queryTime,userCostData,dataStats,month,null,null,request,response);
			return null;
		}
		return mv.addObject("data", data).addObject("pros",queryProHour)
				.addObject("queryTime", queryTime).addObject("userCostData", userCostData)
				.addObject("dataStats", dataStats).addObject("month", month);
	}


	private void exportProIncome(List<List<Map<String, String>>> data, List<ProjectTaskHour> queryProHour,
			String queryTime, List<Map<String, String>> userCostData, List<List<String>> dataStats, String month, List<Map<String, String>> proIncomeData,
			List<List<Map<String, String>>> personalCreatedIncomelist, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		BufferedInputStream bis = null;  
	    BufferedOutputStream bos = null;  
		List<Object[]> proHourList=new ArrayList<>();
		List<Object[]> userHourCostList=new ArrayList<>();
		List<Object[]> proIncomeList=new ArrayList<>();//工时统计页面导出不需要该项
		List<Object[]> proCostList=new ArrayList<>();
		List<Object[]> userCreateIncomeList=new ArrayList<>();//工时统计页面导出不需要该项
		//装载数据proHourList
		List<Object> temp=new ArrayList<>();
		for(int i=0;i<queryProHour.size();i++){
			ProjectTaskHour pro = queryProHour.get(i);
			if(i==0){
				temp.add("员工");
			}
			temp.add(pro.getProjectName()+"正常工时");
			temp.add(pro.getProjectName()+"加班工时");
		}
		proHourList.add(temp.toArray());
		temp.clear();
		Iterator<List<Map<String, String>>> iterator = data.iterator();
		while(iterator.hasNext()){
			temp.clear();
			List<Map<String,String>> next = iterator.next();
			for(int i=0;i<next.size();i++){
				Map<String, String> map = next.get(i);
				if(i==0){
					String username = map.get("username");
					temp.add(username);
				}
				temp.add(map.get("workhour"));
				temp.add(map.get("overhour"));
			}
			proHourList.add(temp.toArray());
		}
		temp.clear();
		temp.add("员工");
		temp.add("正常单位工时成本");
		temp.add("加班单位工时成本");
		userHourCostList.add(temp.toArray());
		temp.clear();
		//装载数据userHourCostList
		for(int i=0;i<userCostData.size();i++){
			Map<String, String> map = userCostData.get(i);
			temp.clear();
			temp.add(map.get("username"));
			temp.add(map.get("workcost"));
			temp.add(map.get("overcost"));
			userHourCostList.add(temp.toArray());
		}
		//装载数据proIncomeList
		temp.clear();
		if(proIncomeData==null){
			proIncomeList=null;
		}else{
			temp.add("项目");
			temp.add("项目收入");
			proIncomeList.add(temp.toArray());
			temp.clear();
			for(int i=0;i<proIncomeData.size();i++){
				Map<String, String> map = proIncomeData.get(i);
				temp.clear();
				temp.add(map.get("projectName"));
				temp.add(map.get("income"));
				proIncomeList.add(temp.toArray());
			}
		}
		//装载数据proCostList
		temp.clear();
		for(int i=0;i<userCostData.size();i++){
			Map<String, String> map = userCostData.get(i);
			if(i==0){
				temp.add("");
			}
			temp.add(map.get("username"));
		}
		temp.add("本月合计");
		temp.add("当年累计");
		proCostList.add(temp.toArray());
		temp.clear();
		for (List<String> list : dataStats) {
			temp.clear();
			for (String string : list) {
				temp.add(string);
			}
			proCostList.add(temp.toArray());
		}
		temp.clear();
		//装载数据userCreateIncomeList
		if(personalCreatedIncomelist==null){
			userCreateIncomeList=null;
		}else{
			for(int i=0;i<queryProHour.size();i++){
				ProjectTaskHour pro = queryProHour.get(i);
				if(i==0){
					temp.add("");
				}
				temp.add(pro.getProjectName());
			}
			temp.add("当月合计");
			temp.add("当年累计");
			userCreateIncomeList.add(temp.toArray());
			for(int i=0;i<personalCreatedIncomelist.size();i++){
				temp.clear();
				List<Map<String, String>> list = personalCreatedIncomelist.get(i);
				for(int y=0;y<list.size();y++){
					Map<String, String> map = list.get(y);
					if(y==0){
						temp.add(map.get("username"));
					}
					temp.add(map.get("createdincome"));
				}
				userCreateIncomeList.add(temp.toArray());
			}
			temp.clear();
		}
		
		
		String title=queryTime+"月份项目收入汇总统计表";
		String[] subtitle=new String[]{queryTime+"月项目工时",queryTime+"月员工工时成本"
				,queryTime+"月项目收入",queryTime+"月项目工时成本",queryTime+"月员工创收"};
		InputStream export = workHourApplicationService.export(proHourList, userHourCostList, proIncomeList,proCostList,userCreateIncomeList, title, subtitle);
		
		
		
		String filename = queryTime+"月份项目收入汇总统计表"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		//设置文件输出类型
	    response.setContentType(request.getServletContext().getMimeType(filename));  
	    response.setHeader("Content-disposition", "attachment; filename="  
	        + sheetName); 
	    //设置输出长度
	    response.setHeader("Content-Length", String.valueOf(export.available()));  
	    //获取输入流
	    bis = new BufferedInputStream(export);  
	    //输出流
	    bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    //关闭流
	    bis.close();
	    bos.close();  
	}


	@RequestMapping("exportProIncomeStatsExcel")
	public void exportProIncomeStatsExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		QueryFilter queryFilter = new QueryFilter(request);
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if(queryTime!=null && StringUtils.isNotEmpty(queryTime.toString())){
			filters.put("queryTime", queryTime);
		}else{
			queryTime=DateUtils.format(new Date(), "yyyy-MM");
			filters.put("queryTime", queryTime);
		}
		String orgid = RequestUtil.getString(request, "orgID","-1");
		filters.put("orgid", orgid);
		view(getAutoView(),queryTime,filters,request,response);
	}
	
	@RequestMapping("exportWorkCostStatsExcel")
	public void exportWorkCostStatsExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		QueryFilter queryFilter = new QueryFilter(request);
		Map<String, Object> filters = queryFilter.getFilters();
		String queryTime = RequestUtil.getString(request, "queryTime");
		if(queryTime!=null && StringUtils.isNotEmpty(queryTime.toString())){
			filters.put("queryTime", queryTime);
		}else{
			queryTime=DateUtils.format(new Date(), "yyyy-MM");
			filters.put("queryTime", queryTime);
		}
		String orgid = RequestUtil.getString(request, "orgID","-1");
		filters.put("orgid", orgid);
		view(getAutoView(),queryTime,filters,request,response);
	}
	

	private String cal(String workhour, String overhour, String workcost, String overcost) {
		double wh=0;
		double oh=0;
		double wc=0;
		double oo=0;
		try {
			wh=Double.valueOf(workhour);
		} catch (NumberFormatException e) {
		}
		try {
			oh=Double.valueOf(overhour);
		} catch (NumberFormatException e) {
		}
		try {
			wc=Double.valueOf(workcost);
		} catch (NumberFormatException e) {
		}
		try {
			oo=Double.valueOf(overcost);
		} catch (NumberFormatException e) {
		}
		double val=wh*wc+oh*oo;
		return String.valueOf(val);
	}


	@ResponseBody  
	@RequestMapping(value="uploadUserCostExcel")  
	public void uploadUserCostExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
		PrintWriter out = response.getWriter();
		String month = multipartRequest.getParameter("month");
		if(StringUtils.isEmpty(month)){
			month=DateUtils.format(new Date(), "yyyy-MM");
		}
        InputStream in =null;
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
    		out.print(new ResultMessage(ResultMessage.Fail, "文件不存在！"));
    		return ;
        }
        in = file.getInputStream();
        try {
			workHourApplicationService.importExcel(in,file.getOriginalFilename(),month);
			out.print(new ResultMessage(ResultMessage.Success, "员工工时成本导入成本！"));
		} catch (Exception e) {
			out.print(new ResultMessage(ResultMessage.Fail, e.getMessage()));
    		return ;
		}finally{
			if(in!=null){
				in.close();
			}
		}
	}
	
	@ResponseBody  
	@RequestMapping(value="uploadProjectIncomeExcel")  
	public void uploadProjectIncomeExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
		PrintWriter out = response.getWriter();
		String month = multipartRequest.getParameter("month");
		if(StringUtils.isEmpty(month)){
			month=DateUtils.format(new Date(), "yyyy-MM");
		}
        InputStream in =null;
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
    		out.print(new ResultMessage(ResultMessage.Fail, "文件不存在！"));
    		return ;
        }
        in = file.getInputStream();
        try {
			workHourApplicationService.importProIncomeExcel(in,file.getOriginalFilename(),month);
			out.print(new ResultMessage(ResultMessage.Success, "项目收入导入成功！"));
		} catch (Exception e) {
			out.print(new ResultMessage(ResultMessage.Fail, e.getMessage()));
    		return ;
		}finally{
			if(in!=null){
				in.close();
			}
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
}