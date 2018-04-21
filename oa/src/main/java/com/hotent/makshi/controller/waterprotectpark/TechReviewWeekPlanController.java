

package com.hotent.makshi.controller.waterprotectpark;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.platform.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.page.PageList;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.waterprotectpark.TechReviewWeekPlan;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.waterprotectpark.TechReviewWeekPlanService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.model.waterprotectpark.WeekPlan;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:技术评审周计划(水保部) 控制器类
 */
@Controller
@RequestMapping("/makshi/waterprotectpark/techReviewWeekPlanSbb/")
public class TechReviewWeekPlanController extends BaseController
{
	@Resource
	private TechReviewWeekPlanService techReviewWeekPlanSbbService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private ClassifyLibraryService ClassifyLibraryService;
	/**
	 * 添加或更新技术评审周计划(水保部)。
	 * @param request
	 * @param response
	 * @param techReviewWeekPlanSbb 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新技术评审周计划(水保部)")
	public void save(HttpServletRequest request, HttpServletResponse response,TechReviewWeekPlan techReviewWeekPlanSbb) throws Exception
	{
		String resultMsg=null;
		try{
			if(techReviewWeekPlanSbb.getId()==null){
				techReviewWeekPlanSbbService.save(techReviewWeekPlanSbb);			
				resultMsg=getText("添加","技术评审周计划(水保部)");
			}else{
			    techReviewWeekPlanSbbService.save(techReviewWeekPlanSbb);
				resultMsg=getText("更新","技术评审周计划(水保部)");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得技术评审周计划(水保部)分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看技术评审周计划(水保部)分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<TechReviewWeekPlan> list=techReviewWeekPlanSbbService.getAll(new QueryFilter(request,"techReviewWeekPlanSbbItem"));
		for (TechReviewWeekPlan techReviewWeekPlan : list) {
			String refRunIds = techReviewWeekPlan.getRefRunIds();
			if(StringUtils.isNotEmpty(refRunIds)){
				String[] split = refRunIds.trim().split(",");
				techReviewWeekPlan.setIsInExamine(false);
				for (String runId : split) {
					ProcessRun processRun = processRunService.getById(Long.parseLong(runId));
					List<ProcessTask> proTasks = null;
					if(BeanUtils.isNotEmpty(processRun)){ 
						if(processRun.getActInstId()==null) continue;
						proTasks = bpmService.getTasks(processRun.getActInstId());
						if(proTasks!=null && proTasks.size()>0){
							techReviewWeekPlan.setIsInExamine(true);
							break;
						}
					}
				}
			}
		}
		ModelAndView mv=this.getAutoView().addObject("techReviewWeekPlanSbbList",list);
		ISysUser currentUser = ContextUtil.getCurrentUser();
		
		return mv.addObject("curUserId", currentUser.getUserId()).addObject("curUserName", currentUser.getFullname());
	}
	
	/**
	 * 删除技术评审周计划(水保部)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除技术评审周计划(水保部)")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			techReviewWeekPlanSbbService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除技术评审周计划(水保部)及其从表成功!");
			techReviewWeekPlanSbbService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除技术评审周计划(水保部)成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑技术评审周计划(水保部)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑技术评审周计划(水保部)")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		TechReviewWeekPlan techReviewWeekPlanSbb=techReviewWeekPlanSbbService.getById(id);
		List<WeekPlan> weekPlanSbbList=techReviewWeekPlanSbbService.getWeekPlanSbbList(id);
		
		return getAutoView().addObject("techReviewWeekPlanSbb",techReviewWeekPlanSbb)
							.addObject("weekPlanSbbList",weekPlanSbbList)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得技术评审周计划(水保部)明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看技术评审周计划(水保部)明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		TechReviewWeekPlan techReviewWeekPlanSbb=techReviewWeekPlanSbbService.getById(id);
		List<WeekPlan> weekPlanSbbList=techReviewWeekPlanSbbService.getWeekPlanSbbList(id);
		return getAutoView().addObject("techReviewWeekPlanSbb",techReviewWeekPlanSbb)
							.addObject("weekPlanSbbList",weekPlanSbbList);
	}
	
	/**
	 * 取得技术评审周计划(水保部)明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getJson")
	@Action(description="查看技术评审周计划(水保部)明细")
	public @ResponseBody TechReviewWeekPlan getJson(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		TechReviewWeekPlan techReviewWeekPlanSbb=techReviewWeekPlanSbbService.getById(id);
		List<WeekPlan> weekPlanSbbList=techReviewWeekPlanSbbService.getWeekPlanSbbList(id);
		if(techReviewWeekPlanSbb!=null){
			techReviewWeekPlanSbb.setWeekPlanSbbList(weekPlanSbbList);
		}
		return techReviewWeekPlanSbb;
	}
	
	@RequestMapping("processRunHistory")
	@Action(description="查看技术评审周计划(水保部)明细")
	public ModelAndView processRunHistory(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		TechReviewWeekPlan techReviewWeekPlanSbb=techReviewWeekPlanSbbService.getById(id);
		String refRunIdsById = techReviewWeekPlanSbbService.getRefRunIdsById(id);
		List<ProcessRun> runs=new ArrayList<>();
		if(techReviewWeekPlanSbb!=null){
			techReviewWeekPlanSbb.setRefRunIds(refRunIdsById);
			String refRunIds = techReviewWeekPlanSbb.getRefRunIds();
			if(StringUtils.isNotEmpty(refRunIds)){
				String[] split = refRunIds.split(",");
				for (String run : split) {
					ProcessRun processRun = processRunService.getById(Long.parseLong(run));
					List<ProcessTask> proTasks = null;
					if(BeanUtils.isNotEmpty(processRun)){ 
						if(processRun.getActInstId()==null) continue;
						proTasks = bpmService.getTasks(processRun.getActInstId());
						processRun.setTaskName("已结束");
						if(proTasks!=null && proTasks.size()>0){
							processRun.setTaskName(proTasks.get(0).getName());
						}
						runs.add(processRun);
					}
				}
			}
		}
		return getAutoView().addObject("runs", runs);
	}
	/**
	 * 取得技术评审周计划(水保部)明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getWeekPlan")
	@Action(description="获取周计划(水保部)")
	public @ResponseBody List<WeekPlan> getWeekPlan(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Date startTime = RequestUtil.getDate(request, "startTime");
		Date endTime =RequestUtil.getDate(request, "endTime");
		String formatDateS = DateUtils.formatDateS(endTime);
		endTime=DateUtils.parseDateL(formatDateS+" 23:59:59");
		Map<String,Object> param=new HashMap<>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		List <WeekPlan> list=new ArrayList<>();
		QueryFilter queryFilter=new QueryFilter(request);
		queryFilter.setPageBean(null);
		Map<String, Object> filters = queryFilter.getFilters();
		filters.clear();
		filters.put("orgID", 10000007780656l);
		filters.put("name", "水保技术评审类");
		List<ClassifyLibrary> libList = ClassifyLibraryService.getAll(queryFilter);
		if(libList!=null && libList.size()>0){
			param.put("classifyLibId", libList.get(0).getId());
			list = techReviewWeekPlanSbbService.getWeekPlanInfo(param);
		}
		return list;
	}
	
}