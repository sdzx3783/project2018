package com.hotent.platform.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.scheduler.SchedulerService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateFormatUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringPool;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.system.SysPlanExchangeDao;
import com.hotent.platform.dao.system.SysPlanParticipantsDao;
import com.hotent.platform.dao.system.SysPlanSubscribeDao;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysPlan;
import com.hotent.platform.model.system.SysPlanExchange;
import com.hotent.platform.model.system.SysPlanParticipants;
import com.hotent.platform.model.system.SysPlanSubscribe;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmDefAuthorizeService;
import com.hotent.platform.service.bpm.BpmProCopytoService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.IUserUnderService;
import com.hotent.platform.service.system.SysPlanService;
import com.hotent.platform.service.system.SysUserService;
/**
 *<pre>
 * 对象功能:自定义别名脚本表 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2013-12-19 11:26:03
 *</pre>
 */
@Controller
@RequestMapping("/platform/system/sysPlan/")
public class SysPlanController extends BaseController
{

	@Resource
	private SysPlanService sysPlanService;
	
	@Resource
	private SysPlanParticipantsDao sysPlanParticipantsDao;
	
	@Resource
	private SysPlanExchangeDao sysPlanExchangeDao;
	
	@Resource
	private SysPlanSubscribeDao sysPlanSubscribeDao;
	
	@Resource
	private BpmProCopytoService bpmProCopytoService;
	
	@Resource
	private ProcessRunService  processRunService;
	
	@Resource
	private BpmDefAuthorizeService bpmDefAuthorizeService;
	
	@Resource
	private IUserUnderService iUserUnderService;
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SchedulerService schedulerService;
	
	
	/**
	 * 添加或更新日程表。
	 * @param request
	 * @param response
	 * @param sysPlan 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新日程表")
	public void save(HttpServletRequest request, HttpServletResponse response,SysPlan sysPlan) throws Exception{
		String resultMsg="保存日程失败！";		
		try{
			String participantIds = RequestUtil.getString(request, "participantIds", "");
			String participants = RequestUtil.getString(request, "participants", "");
			Date startTime = RequestUtil.getDate(request, "startTime" ,StringPool.DATE_FORMAT_DATETIME_NOSECOND);
			Date endTime = RequestUtil.getDate(request, "endTime" ,StringPool.DATE_FORMAT_DATETIME_NOSECOND);
			
			sysPlan.setEndTime(endTime);
			sysPlan.setStartTime(startTime);
			String taskStartTime = RequestUtil.getString(request, "startTime", "");
			PrintWriter out=response.getWriter();	
			String jobName = "自定义消息发送";
			String trigName = sysPlan.getDescription();
			String planJson = "{\"type\":1,\"timeInterval\":\""+taskStartTime+"\"}";
			//判断触发器是否存在
			boolean rtn=schedulerService.isTriggerExists(trigName);
			if(rtn)
			{
				ResultMessage obj=new ResultMessage(ResultMessage.Fail,"指定的计划名称已经存在!");
				out.print(obj.toString());
			}
			try {
				SysUser sendUser  = (SysUser)ContextUtil.getCurrentUser();
				jobName += sendUser.getUserId().toString(); 
				schedulerService.addTrigger(jobName, trigName, planJson);
			} catch (SchedulerException e) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail,"添加计划失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(e);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
			
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("participantIds", participantIds);
			params.put("participants", participants);
			resultMsg = sysPlanService.saveSysPlan(sysPlan, params);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 取得月份的我提交日程列表,并response列表的JSON字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listBySubmitAndMonth")
	@Action(description="取得月份的我提交日程列表，并response列表的JSON字符串")
	public void listBySubmitAndMonth(HttpServletRequest request,HttpServletResponse response){	
		try {
			String json = getSysPlanListJson(request, response,"submit");
			response.getWriter().print(json);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 取得月份的我负责的日程列表,并response列表的JSON字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByChargeAndMonth")
	@Action(description="取得月份的我负责的日程列表,并response列表的JSON字符串")
	public void listByChargeAndMonth(HttpServletRequest request,HttpServletResponse response){	
		try {
			String json = getSysPlanListJson(request, response,"charge");
			response.getWriter().print(json);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 取得月份的我参与的日程列表,并response列表的JSON字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByParticipantAndMonth")
	@Action(description="取得月份的我参与的日程列表,并response列表的JSON字符串")
	public void listByParticipantAndMonth(HttpServletRequest request,HttpServletResponse response){	
		try {
			String json = getSysPlanListJson(request, response,"participant");
			response.getWriter().print(json);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 取得月份的我订阅的日程列表,并response列表的JSON字符串
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listBySubscribeAndMonth")
	@Action(description="取得月份的我订阅的日程列表,并response列表的JSON字符串")
	public void listBySubscribeAndMonth(HttpServletRequest request,HttpServletResponse response){	
		try {
			String json = getSysPlanListJson(request, response,"subscribe");
			response.getWriter().print(json);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 获取首页日程数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("my_scheduleDay_index")
	@Action(description="取得首页我的日程数据,并response列表的JSON字符串")
	public void myPlanIndex(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("orderField", "P.STARTTIME");
		queryFilter.addFilter("orderSeq"," asc");
		queryFilter.addFilter("userId", userId);
		queryFilter.getPageBean().setPagesize(5000);
		List<SysPlan> myPlanList=sysPlanService.getMyList(queryFilter);
		String rstJson="";
		if(null!=myPlanList && myPlanList.size()>0){
//			List<Map<String,Object>> rstLit=new ArrayList<Map<String,Object>>();
//			Map<String,String> dataMap=new TreeMap<String,String>();
			
			Map<String,List<SysPlan>> startMapList=new HashMap<String,List<SysPlan>>();
			
			
			Map<String,List<SysPlan>> endMapList=new HashMap<String,List<SysPlan>>();

			Set<String> dateSet=new TreeSet<String>();
			for(SysPlan plan:myPlanList){
			/*	Map<String,Object> rstMap=new HashMap<String,Object>();
				rstMap.put("date", DateUtils.getFormatDateTime(plan.getStartTime(),"yyyy/MM/dd"));
				rstMap.put("value", plan.getTaskName());
				rstLit.add(rstMap);*/
				String startDate= DateUtils.getFormatDateTime(plan.getStartTime(),"yyyy/MM/dd");
				String endDate=null;
				if(plan.getEndTime()!=null){
					endDate=DateUtils.getFormatDateTime(plan.getEndTime(),"yyyy/MM/dd");
				}
				
				
				List<SysPlan> curDateStartPlanList=null;
				if(!startMapList.containsKey(startDate)){
					curDateStartPlanList=new ArrayList<SysPlan>();
				}else{
					curDateStartPlanList=startMapList.get(startDate);
				}
				curDateStartPlanList.add(plan);
				startMapList.put(startDate, curDateStartPlanList);
				dateSet.add(startDate);
				
				if(endDate !=null  && !startDate.equals(endDate)){
					List<SysPlan> curDateEndPlanList=null;
					if(!endMapList.containsKey(endDate)){
						curDateEndPlanList=new ArrayList<SysPlan>();
					}else{
						curDateEndPlanList=endMapList.get(endDate);
					}
					curDateEndPlanList.add(plan);
					endMapList.put(endDate, curDateEndPlanList);
					
					dateSet.add(endDate);
				}
			}
			List<Map<String,String>> jionList=new ArrayList<Map<String,String>>();
			for(String date:dateSet){
				Map<String,String> rstMap=new HashMap<String,String>();
				StringBuffer sb=new StringBuffer();
				if(startMapList.containsKey(date)){
					for(SysPlan plan:startMapList.get(date)){
						sb.append(plan.getTaskName()+",");
					}
				}
				
				if(endMapList.containsKey(date)){
					for(SysPlan plan:endMapList.get(date)){
						sb.append(plan.getTaskName()+" - 结束,");
					}
				}
				rstMap.put("date", date);
				rstMap.put("value", sb.substring(0,sb.length()-1));
				jionList.add(rstMap);
			}
			Map<String,List<Map<String,String>>> jionListMap=new HashMap<>();
			jionListMap.put("finish", new ArrayList<Map<String,String>>());
			jionListMap.put("notfinish", new ArrayList<Map<String,String>>());
			for (Map<String, String> map : jionList) {
				boolean isFinish=true;
				String date = map.get("date");
				List<SysPlan> list = startMapList.get(date);
				if(list!=null && list.size()>0){
					for (SysPlan sysPlan : list) {
						if(sysPlan.getRate()!=null && sysPlan.getRate()==100l){
							continue;
						}else{
							isFinish=false;
							break;
						}
					}
				}
				if(isFinish){
					List<SysPlan> list2 = endMapList.get(date);
					if(list2!=null && list2.size()>0){
						for (SysPlan sysPlan : list2) {
							if(sysPlan.getRate()!=null && sysPlan.getRate()==100){
								continue;
							}else{
								isFinish=false;
								break;
							}
						}
					}
				}
				if(isFinish){
					jionListMap.get("finish").add(map);
				}else{
					jionListMap.get("notfinish").add(map);
				}
				
				
			}
			
			JSONObject json=JSONObject.fromObject(jionListMap);
			rstJson=json.toString();
		}
		writeResultMessage(response.getWriter(),rstJson,ResultMessage.Success);
	}
	
	@RequestMapping("my_current_schedule")
	@Action(description="取得日程数据,并response列表的JSON字符串")
	public void myCurrentSchedule(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		Date date=RequestUtil.getDate(request, "selectDate",  StringPool.DATE_FORMAT_DATE);
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.addFilter("orderField", "P.STARTTIME");
		queryFilter.addFilter("orderSeq"," asc");
		queryFilter.addFilter("userId", userId);
		queryFilter.getPageBean().setPagesize(3);
		String queryDate=null;
		if(date!=null){
			queryDate=DateUtils.formatDateS(date);
			queryFilter.addFilter("scheduleTime", true);
			queryFilter.addFilter("starttime", DateUtils.formatDateS(date));
			queryFilter.addFilter("endtime", DateUtils.formatDateS(date));
		}else{
			queryFilter.addFilter("beginStarttime",new Date());
		}
		
		List<SysPlan> myPlanList=sysPlanService.getMyList(queryFilter);
		String rstJson="";
		if(null!=myPlanList && myPlanList.size()>0){
			for(SysPlan plan:myPlanList){
				String startDate=DateUtils.formatDateS(plan.getStartTime());
				Calendar cal=Calendar.getInstance();
				cal.setTime(plan.getStartTime());
				int hour=cal.get(Calendar.HOUR);
				String flagStr=" AM";
				if(hour>12){
					hour-=12;
					flagStr=" PM";
				}
				
				if(startDate.equals(queryDate)){
					String projectName=DateUtils.format(plan.getStartTime(), "HH:mm")+flagStr;
					plan.setProjectName(projectName);
				}else{
					String projectName=DateUtils.format(plan.getStartTime(), "dd日 HH:mm")+flagStr;
					plan.setProjectName(projectName);
				}
			}
			JSONArray json=JSONArray.fromObject(myPlanList);
			rstJson=json.toString();
		}
		writeResultMessage(response.getWriter(),rstJson,ResultMessage.Success);
	}
	
	
	
	
	
	
	private String getSysPlanListJson(HttpServletRequest request,HttpServletResponse response,String type) throws ParseException {
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		Date selectDate = RequestUtil.getDate(request, "selectDate", StringPool.DATE_FORMAT_DATE);
		Date startDate = RequestUtil.getDate(request, "startDate", StringPool.DATE_FORMAT_DATE);
		Date endDate = RequestUtil.getDate(request, "endDate", StringPool.DATE_FORMAT_DATE);
		List<SysPlan> sysPlanList =  null;
		
		
		
		if("submit".equals(type)){
			sysPlanList = sysPlanService.getBySubmitId(userId, startDate, endDate, selectDate);
		}else if("charge".equals(type)){
			sysPlanList = sysPlanService.getByChargeId(userId, startDate, endDate, selectDate);
		}else if("participant".equals(type)){
			sysPlanList = sysPlanService.getByParticipantId(userId, startDate, endDate, selectDate);
		}else if("subscribe".equals(type)){
			sysPlanList = sysPlanService.getBySubscribeId(userId, startDate, endDate, selectDate);
		}
		String json = "";
		if(sysPlanList!=null&&sysPlanList.size()>0){
			json = getSysPlanJsonStr(sysPlanList);
		}
		return json;
	}
	
	
	
	
	/**
	 * 进入日程订阅总页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("underMatters")
	@Action(description="进入日程订阅总页面")
	public ModelAndView underMatters(HttpServletRequest request,HttpServletResponse response) throws Exception {	
		ModelAndView mv=this.getAutoView();
		return mv.addObject("userId",ContextUtil.getCurrentUserId());
	}
	
	
	/**
	 * 取得下属负责的日程列表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("underList")
	@Action(description="取得下属负责的日程列表分页列表")
	public ModelAndView underList(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		QueryFilter queryFilter = new QueryFilter(request,"sysPlanItem");
		//Date  = RequestUtil.getDate(request, "");
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		Long queryUserId = RequestUtil.getLong(request, "queryUserId",0);  // 获取当前用户的一下属的id
		if(queryUserId == userId ){
			queryUserId = 0l;
		}
		List<SysPlan> sysPlanList=sysPlanService.getUnderSysPlanByUserId(userId,queryUserId,queryFilter);
		ModelAndView mv=this.getAutoView().addObject("sysPlanList",sysPlanList).addObject("queryUserId",queryUserId);
		return mv;
	}
	
	
	/**
	 * 取得我订阅的日程列表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subscribeList")
	@Action(description="取得我订阅的日程列表分页列表")
	public ModelAndView subscribeList(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		QueryFilter queryFilter = new QueryFilter(request,"sysPlanItem");
		//Date  = RequestUtil.getDate(request, "");
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		List<SysPlan> sysPlanList=sysPlanService.getListBySubscribeId(userId, queryFilter);
		ModelAndView mv=this.getAutoView().addObject("sysPlanList",sysPlanList);
		return mv;
	}
	
	
	
	/**
	 * 取得下属负责列表
	 * @param request
	 * @param response

	 * @throws Exception
	 */
	@RequestMapping("underUserList")
	@Action(description="取得下属负责列表")
	public void underUserList(HttpServletRequest request,HttpServletResponse response){	
		try {
			long queryUserId = RequestUtil.getLong(request, "queryUserId",0);  // 获取用户的一下属json
			if(queryUserId <1){
				queryUserId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
			}		
			List<SysUser> sysUserList = iUserUnderService.getMyUnderUser(queryUserId);
			String json = "";
			if(sysUserList!=null&&sysUserList.size()>0){
				SysUser sysUser = sysUserService.getById(queryUserId);
				json = getUserJsonStr(sysUser, sysUserList);
			}
			response.getWriter().print(json);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * 把日程列表信息转为JSON数组信息
	 */
	private String getUserJsonStr(SysUser parentSysUser, List<SysUser> sysUserList){	
		JSONArray jsonAry = new JSONArray();
		
		//主用
		long userId = ContextUtil.getCurrentUserId(); // 获取当前用户的id
		if(userId == parentSysUser.getUserId()){
			JSONObject root =  new JSONObject();
			root.accumulate("id", parentSysUser.getUserId())
			.accumulate("parentId","0")
			.accumulate("title", "我["+parentSysUser.getFullname()+"]")
			.accumulate("name", "我["+parentSysUser.getFullname()+"]")
			.accumulate("sysUser", JSONObject.fromObject(parentSysUser));
			jsonAry.add(root);
		}
		
		
		for (SysUser sysUser : sysUserList) {
			JSONObject json =  new JSONObject();
			JSONObject sysUserJson = JSONObject.fromObject(sysUser);
			json.accumulate("id", sysUser.getUserId())
			.accumulate("parentId",parentSysUser.getUserId())
			.accumulate("title", sysUser.getFullname())
			.accumulate("name", sysUser.getFullname())
			.accumulate("sysUser", sysUserJson);
			jsonAry.add(json);
		} 
		return jsonAry.toString();
	}
	
	
	
	/**
	 * 进入 我提交的日程展示视图
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("submit")
	@Action(description="进入我提交日程展示视图")
	public ModelAndView submit(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		return mv;
	}
	
	
	
	/**
	 * 进入 我负责的日程展示视图
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("charge")
	@Action(description="进入 我负责的日程展示视图")
	public ModelAndView charge(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		return mv;
	}
	
	
	/**
	 * 进入 我订阅的日程展示视图
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subscribe")
	@Action(description="进入我订阅日程展示视图")
	public ModelAndView subscribe(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		return mv;
	}
	
	
	/**
	 * 进入 我参与的日程展示视图
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("participant")
	@Action(description="进入我参与日程展示视图")
	public ModelAndView participant(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		return mv;
	}
	
	
	/**
	 * 进入日程编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="进入日程编辑")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		makeSysPlan(request, response, mv);
		return mv;
	}
	
	
	/**
	 * 进入日程详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="进入日程编辑")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		makeSysPlan(request, response, mv);
		return mv;
	}
	
	
	/**
	 * 进入日程交流页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exchange")
	@Action(description="进入日程交流页面")
	public ModelAndView exchange(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		makeSysPlan(request, response, mv);
		return mv;
	}
	
	/**
	 * 进入参与者日程交流页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("participantToExchange")
	@Action(description="进入参与者日程交流页面")
	public ModelAndView participantToExchange(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		makeSysPlan(request, response, mv);
		return mv;
	}
	
	/**
	 * 进入我订阅的日程交流页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subscribeToExchange")
	@Action(description="进入我订阅的日程交流页面")
	public ModelAndView subscribeToExchange(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		makeSysPlan(request, response, mv);
		return mv;
	}
	
	/**
	 * 进入日程订阅信息查看页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subscribeGet")
	@Action(description="进入日程订阅信息查看页面")
	public ModelAndView subscribeGet(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		mv = makeSysPlan(request, response, mv);
		Long subscribeId = RequestUtil.getLong(request, "subscribeId",0);
		Long queryUserId = RequestUtil.getLong(request, "queryUserId",0);
		mv.addObject("queryUserId", queryUserId);
		if(subscribeId<1){
			return mv;
		}
		Map<String,Object> map = mv.getModelMap();
		SysPlan sysPlan = (SysPlan) map.get("sysPlan");
		if(sysPlan!=null){
			sysPlan.setSubscribeId(subscribeId);
			mv.addObject("sysPlan", sysPlan);
		}
		return mv;
	}
	
	
	/**
	 * 获取日程信息
	 */
	private ModelAndView makeSysPlan(HttpServletRequest request,HttpServletResponse response,ModelAndView mv) throws Exception{	
		if(mv==null){
			mv=this.getAutoView();
		}
		Long id = RequestUtil.getLong(request, "id", 0);
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		String type = RequestUtil.getString(request, "type", "");
		mv.addObject("type", type);
		SysPlan sysPlan = new SysPlan();
		if(id>0){
			//日程
			sysPlan = sysPlanService.getById(id);
			mv.addObject("sysPlan", sysPlan);
			//日程参与者
			List<SysPlanParticipants> sysPlanParticipantsList = sysPlanParticipantsDao.getByPlanId(id);
			String participantIds = "";
			String participants=""; 
			if(sysPlanParticipantsList!=null&&sysPlanParticipantsList.size()>0){
				for (SysPlanParticipants sysPlanParticipants : sysPlanParticipantsList) {
					participantIds += sysPlanParticipants.getParticipantId()+",";
					participants += sysPlanParticipants.getParticipant()+",";
				}
				participantIds = participantIds.substring(0, participantIds.length()-1);
				participants = participants.substring(0, participants.length()-1);
			}
			mv.addObject("participantIds", participantIds);
			mv.addObject("participants", participants);
			//日程交流表
			List<SysPlanExchange> sysPlanExchangeList = sysPlanExchangeDao.getByPlanId(id);
			mv.addObject("sysPlanExchangeList", sysPlanExchangeList);
		}else{
			sysPlan.setSubmitId(ContextUtil.getCurrentUserId());
			sysPlan.setSubmitor(ContextUtil.getCurrentUser().getFullname());
			sysPlan.setChargeId(ContextUtil.getCurrentUserId());
			sysPlan.setCharge(ContextUtil.getCurrentUser().getFullname());
			sysPlan.setCreateTime(new Date());
			if(StringUtils.isNotEmpty(RequestUtil.getString(request, "startTime", ""))
					&& !"undefined".equals(RequestUtil.getString(request, "startTime", ""))){
				
				String startTime=RequestUtil.getString(request, "startTime", "");
				sysPlan.setStartTime(DateUtils.parseDateL(startTime));
			}
			if(StringUtils.isNotEmpty(RequestUtil.getString(request, "endTime", "")) &&
					!"undefined".equals(RequestUtil.getString(request, "endTime", ""))){
				String endTime=RequestUtil.getString(request, "endTime", "");
				sysPlan.setEndTime(DateUtils.parseDateL(endTime));
			}
			mv.addObject("sysPlan", sysPlan);
		}
		return mv;
	}
	
	/**
	 * 添加或更新日程交流信息。
	 * @param request
	 * @param response
	 * @param sysPlan 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveExchange")
	@Action(description=" 添加或更新日程交流信息")
	public void saveExchange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="保存日程交流信息失败！";	
   		SysUser user = (SysUser) ContextUtil.getCurrentUser();
		try{
	   		Long id = RequestUtil.getLong(request, "id", 0);
	   		Long planId = RequestUtil.getLong(request, "planId", 0);
	   		String doc = RequestUtil.getString(request, "doc", "");
	   		String content = RequestUtil.getString(request, "content", "");
			SysPlanExchange  exchange = new SysPlanExchange();
	   		exchange.setId(id);
	   		exchange.setPlanId(planId);
	   		exchange.setContent(content);
	   		exchange.setDoc(doc);
	   		exchange.setSubmitId(user.getUserId());
	   		exchange.setSubmitor(user.getFullname());
	   		exchange.setCreateTime(new Date());
			resultMsg = sysPlanService.saveSysPlanExchange(exchange, null);
			JSONObject json = JSONObject.fromObject(exchange);
			ResultMessage resultMessage = new ResultMessage();
			resultMessage.setMessage(resultMsg);
			resultMessage.setResult(ResultMessage.Success);
			resultMessage.setCause(json.toString());
			writeResultMessage(response.getWriter(), resultMessage);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 进入日程交流编辑交流信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exchangeEdit")
	@Action(description="进入日程交流编辑交流信息页面")
	public ModelAndView exchangeEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		mv = makeSysPlanExchange(request, response, mv);
		return mv;
	}
	
	
	/**
	 * 进入日程交流信息查看页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exchangeGet")
	@Action(description="进入日程交流信息查看页面")
	public ModelAndView exchangeGet(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		mv = makeSysPlanExchange(request, response, mv);
		return mv;
	}
	
	
	/**
	 * 获取日程交流信息
	 */
	private ModelAndView makeSysPlanExchange(HttpServletRequest request,HttpServletResponse response,ModelAndView mv) throws Exception{	
		if(mv==null){
			mv=this.getAutoView();
		}
		Long id = RequestUtil.getLong(request, "id", 0);
		Long planId = RequestUtil.getLong(request, "planId", 0);
		String currentViweDate = RequestUtil.getString(request, "currentViweDate", "");
		mv.addObject("currentViweDate", currentViweDate);
		String type = RequestUtil.getString(request, "type", "");
		mv.addObject("type", type);
		if(id>0L){
			SysPlanExchange sysPlanExchange = sysPlanExchangeDao.getById(id);
			if(sysPlanExchange!=null){
				planId = sysPlanExchange.getPlanId();
			}
			mv.addObject("sysPlanExchange", sysPlanExchange);
		}
		mv.addObject("planId", planId);
		return mv;
	}
	
	
	/**
	 * 删除日程信息。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@Action(description="删除日程信息")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="删除日程失败！";		
		try{
			String ids = RequestUtil.getString(request, "ids", "");
			String [] idArry = ids.split("\\,");
			resultMsg = sysPlanService.deleteSysPlans(idArry);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 删除日程交流信息。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteExchange")
	@Action(description="删除日程交流信息")
	public void deleteExchange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="删除日程交流信息失败！";		
		try{
			String ids = RequestUtil.getString(request, "ids", "");
			String [] idArry = ids.split("\\,");
			resultMsg = sysPlanService.deleteSysPlanExchanges(idArry);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 更新日程的任务情况。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("chargeSysPlans")
	@Action(description="更新日程的任务情况")
	public void chargeSysPlans(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="更新日程状态成功！";		
		try{
			Long rate = RequestUtil.getLong(request, "rate", 0);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("rate", rate);
			String ids = RequestUtil.getString(request, "ids", "");
			String [] idArry = ids.split("\\,");
			resultMsg = sysPlanService.changeSysPlans(idArry,params);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 把日程列表信息转为JSON数组信息
	 */
	private String getSysPlanJsonStr(List<SysPlan> sysPlanList){	
		JSONArray jsonAry = new JSONArray();
		for (int i = 0; i < sysPlanList.size(); i++) {
			SysPlan sysPlan = sysPlanList.get(i);
			JSONObject json =  new JSONObject();
			JSONObject sysPlanJson = JSONObject.fromObject(sysPlan);
			
			
			String clsName=getClassName(sysPlan.getRate());
			
			json.accumulate("id", sysPlan.getId())
			.accumulate("title", sysPlan.getTaskName())
			.accumulate("start", DateFormatUtil.format(sysPlan.getStartTime(), StringPool.DATE_FORMAT_DATETIME))
			.accumulate("end", DateFormatUtil.format(sysPlan.getEndTime(), StringPool.DATE_FORMAT_DATETIME))
			.accumulate("className",clsName)
			.accumulate("planId", sysPlan.getId())
			.accumulate("sysPlan", sysPlanJson);
			
			jsonAry.add(json);
		} 
		return jsonAry.toString();
	}
	
	private String getClassName(Long rate){
		String clsName="";
		Long finish=100L;
		if(BeanUtils.isZeroEmpty(rate)){
			clsName="['event','notstart']";
		}
		else if(finish.equals(rate)){
			clsName="['event','finished']";
		}
		else{
			clsName="['event','running']";
		}
		
		return clsName;
	}
	

	
	/**
	 * 订阅日程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("subscribeSysPlan")
	@Action(description="订阅日程")
	public void subscribeSysPlan(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="订阅日程失败！";		
		try{
			SysUser user = (SysUser) ContextUtil.getCurrentUser();
			Long planId = RequestUtil.getLong(request, "planId", 0);
            SysPlanSubscribe ps = new SysPlanSubscribe();
            ps.setId(UniqueIdUtil.genId());
            ps.setPlanId(planId);
            ps.setSubscribeId(user.getUserId());
            ps.setSubscribe(user.getFullname());
            ps.setSubscribeTime(new Date());
            sysPlanSubscribeDao.add(ps);
            resultMsg="订阅日程成功！";	
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	
	/**
	 * 取消日程订阅
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cancelSysPlan")
	@Action(description="取消日程订阅")
	public void cancelSysPlan(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resultMsg="取消日程订阅失败！";		
		try{
			Long id = RequestUtil.getLong(request, "id", 0);    
            sysPlanSubscribeDao.delById(id);
            resultMsg="取消日程订阅成功！";	
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得流程结束工单的对话框
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("processRunDialog")
	@Action(description="取得流程结束工单的对话框")
	public ModelAndView processRunDialog(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		ModelAndView mv=this.getAutoView();
		String isSingle = RequestUtil.getString(request, "isSingle", "yes");
		return mv.addObject("isSingle", isSingle);
	}
	
	
	
	
	
	/**
	 * 取得流程结束工单的分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("processRunList")
	@Action(description="取得流程结束工单的分页列表")
	public ModelAndView processRunList(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		//查询已结束并与我相关的流程工单
		QueryFilter filter = new QueryFilter(request,"processRunItem");
		filter.addFilter("creatorId", ContextUtil.getCurrentUserId());
		List<ProcessRun> processRunList = processRunService.getMyCompletedAndCptoList(filter);
		
		ModelAndView mv=this.getAutoView().addObject("processRunList",processRunList);
		String isSingle = RequestUtil.getString(request, "isSingle", "yes");
		mv.addObject("isSingle", isSingle);
		return mv;
	}
	
	/**
	 * 取得我负责的日程和我 参与的日程列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myList")
	@Action(description = "查看系统配置参数表分页列表")
	public ModelAndView myList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "sysPlanItem");
		filter.addFilter("userId", ContextUtil.getCurrentUserId());
		List<SysPlan> list = sysPlanService.getMyList(filter);
		
		ModelAndView mv = this.getAutoView().addObject("sysPlanList", list)
			.addObject("curUserId", ContextUtil.getCurrentUserId());
		return mv;
	}
	
	/**
	 * 删除我负责的日程
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除我负责的日程")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String[] lAryId =RequestUtil.getStringAryByStr(request, "ids");
		sysPlanService.deleteSysPlans(lAryId);
		response.sendRedirect(preUrl);
	}
	
}
