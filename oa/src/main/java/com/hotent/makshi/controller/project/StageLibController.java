package com.hotent.makshi.controller.project;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.StageLibrary;
import com.hotent.makshi.model.project.StageTaskLibrary;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.StageLibraryService;
import com.hotent.makshi.service.project.StageTaskLibraryService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;
@Controller
@RequestMapping("/makshi/project/stagelib/")
public class StageLibController extends BaseController{
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private StageLibraryService stageLibraryService;
	@Resource
	private StageTaskLibraryService stageTaskLibraryService;
	@Autowired
	private ClassifyLibraryService classifyLibService;
	@RequestMapping("list")
	@Action(description = "获取阶段库")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = getAutoView();
		List<SysOrg> orgs = sysOrgService.getOrgsAll();
		List<StageLibrary> stagelibList=stageLibraryService.getAll(new QueryFilter(request,"stagelib"));
		for (StageLibrary stageLibrary : stagelibList) {
			int count = stageTaskLibraryService.getCount(stageLibrary.getStageno());
			stageLibrary.setTasknum(count);
		}
		mv.addObject("orgs", orgs).addObject("stagelibList", stagelibList);
		return mv;
	}
	
	@RequestMapping("selector")
	@Action(description = "获取阶段库")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = getAutoView();
		Long classifyid = RequestUtil.getLong(request, "classifyid");
		ClassifyLibrary classifyLibrary = classifyLibService.getById(classifyid);
		Long orgid = classifyLibrary.getOrgID();
		/*List<SysOrg> orgs = sysOrgService.getOrgsAll();*/
		QueryFilter queryFilter = new QueryFilter(request,"stagelib");
		queryFilter.getFilters().put("createorgid", orgid);
		queryFilter.getFilters().put("classifyid", classifyid);
		List<StageLibrary> stagelibList=stageLibraryService.getAll(queryFilter);
		for (StageLibrary stageLibrary : stagelibList) {
			int count = stageTaskLibraryService.getCount(stageLibrary.getStageno());
			stageLibrary.setTasknum(count);
		}
		mv.addObject("orgid", orgid).addObject("stagelibList", stagelibList).addObject("classifyid", classifyid);
		return mv;
	}
	
	@RequestMapping("edit")
	@Action(description = "获取阶段库")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		int stageno = RequestUtil.getInt(request, "stageno");
		if(stageno!=0){
			StageLibrary stageLibrary = stageLibraryService.getById(Long.valueOf(stageno+""));
			mv.addObject("stageLib", stageLibrary);
		}
		return mv;
	}
	
	@RequestMapping("save")
	@Action(description = "保存阶段库")
	public void save(HttpServletRequest request, HttpServletResponse response ,StageLibrary stageLib) throws Exception {
		String resultMsg = null;
		try {
			if(stageLib.getStageno()==null){
				Long currentUserId = ContextUtil.getCurrentUserId();
				stageLib.setCuser(currentUserId);
				Long createorgID = RequestUtil.getLong(request, "createorgID");
				stageLib.setCreateorgid(createorgID);
				/*if(currentUserId==1){
				//超级管理员
				stageLib.setCreateorg("深水咨询");
				List<SysOrg> list = sysOrgService.getByOrgNameNotDel("深水咨询");
				if(list!=null && list.size()>0){
					stageLib.setCreateorgid(list.get(0).getOrgId());
				}
			}*/
				stageLib.setIsdelete(0);
				stageLib.setCtime(new Date());
				stageLibraryService.add(stageLib);
				resultMsg = getText("添加", "阶段库信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				StageLibrary stageLibTemp = stageLibraryService.getById(Long.valueOf(stageLib.getStageno()+""));
				if(stageLibTemp!=null){
					stageLibTemp.setStagename(stageLib.getStagename());
					stageLibTemp.setRemark(stageLib.getRemark());
					Long createorgID = RequestUtil.getLong(request, "createorgID");
					stageLibTemp.setCreateorg(stageLib.getCreateorg());
					stageLibTemp.setCreateorgid(createorgID);
					stageLibraryService.update(stageLibTemp);
					resultMsg = getText("修改", "阶段库信息");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				}
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	@RequestMapping("update")
	@Action(description = "更新阶段库的名称和阶段任务排序")
	public void update(HttpServletRequest request, HttpServletResponse response ,StageLibrary stageLib) throws Exception {
		String resultMsg = null;
		try {
			Long currentUserId = ContextUtil.getCurrentUserId();
			stageLib.setUuser(currentUserId);
			stageLib.setUtime(new Date());
			stageLibraryService.update(stageLib);
			//更新任务排序
			Map<Integer, Integer> orderMap = stageLib.getOrderMap();
			if(orderMap!=null && orderMap.size()>0){
				Set<Integer> keySet = orderMap.keySet();
				Iterator<Integer> iterator = keySet.iterator();
				while(iterator.hasNext()){
					Integer taskId = iterator.next();
					Integer order = orderMap.get(taskId);
					if(taskId!=null || order!=null){
						stageTaskLibraryService.updateTaskOrder(taskId,order);
					}
				}
			}
			resultMsg = getText("更新", "阶段库信息");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("toset")
	@Action(description = "配置")
	public ModelAndView toset(HttpServletRequest request, HttpServletResponse response ,StageLibrary stageLib) throws Exception {
		long stageno = RequestUtil.getLong(request, "stageno");
		StageLibrary stageLibrary = stageLibraryService.getById(stageno);
		QueryFilter filter=new QueryFilter(request,"taskItem");
		filter.getFilters().put("stageno", (int)stageno);
//		List<StageTaskLibrary> taskLibs = stageTaskLibraryService.getTasklibByStageno((int) stageno);
		List<StageTaskLibrary> taskLibs = stageTaskLibraryService.getTasklibByStageno(filter);
		filter.setForWeb();
		ModelAndView mv=getAutoView();
		mv.addObject("stageLib", stageLibrary).addObject("taskLibs", taskLibs);
		return mv;
	}
	
	@RequestMapping("addtask")
	@Action(description = "新增任务")
	public ModelAndView addtask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long stageno = RequestUtil.getLong(request, "stageno");
		Long id = RequestUtil.getLong(request, "id");
		StageLibrary stageLibrary = stageLibraryService.getById(stageno);
		StageTaskLibrary taskLibrary = stageTaskLibraryService.getById(id);
		ModelAndView mv=getAutoView();
		
		mv.addObject("stageLib", stageLibrary).addObject("taskLib", taskLibrary);
		return mv;
	}
	
	@RequestMapping("viewtask")
	@Action(description = "新增任务")
	public ModelAndView viewtask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long stageno = RequestUtil.getLong(request, "stageno");
		Long id = RequestUtil.getLong(request, "id");
		StageLibrary stageLibrary = stageLibraryService.getById(stageno);
		StageTaskLibrary taskLibrary = stageTaskLibraryService.getById(id);
		ModelAndView mv=getAutoView();
		
		mv.addObject("stageLib", stageLibrary).addObject("taskLib", taskLibrary);
		return mv;
	}
	
	@RequestMapping("tasksave")
	@Action(description = "保存任务库")
	public void tasksave(HttpServletRequest request, HttpServletResponse response,StageTaskLibrary stageTaskLib) throws Exception {
		String resultMsg=null;
		try{
			int stageno = RequestUtil.getInt(request, "stageno");
			if(stageno==0){
				resultMsg = getText("添加", "参数错误");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			if(stageTaskLib.getIsexamine()==null){
				stageTaskLib.setIsexamine(false);
			}
			if(stageTaskLib.getIsmore()==null){
				stageTaskLib.setIsmore(false);
			}
			if(stageTaskLib.getId()==null){
				//插入
				stageTaskLib.setIsdelete(0);
				stageTaskLib.setStageno(stageno);
				stageTaskLib.setOrder(0);
				stageTaskLibraryService.add(stageTaskLib);
				resultMsg = getText("添加", "阶段任务库信息");
			}else{
				//更新
				stageTaskLibraryService.update(stageTaskLib);
				resultMsg = getText("更新", "阶段任务库信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		}catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("delTask")
	@Action(description = "删除任务库")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			stageTaskLibraryService.delByIds(lAryId);
			
			message = new ResultMessage(ResultMessage.Success, "删除任务库成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除任务库失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	@RequestMapping("delstage")
	@Action(description = "删除阶段库")
	public void delstage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "stageno");
			stageLibraryService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除阶段库成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除阶段库失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

}
