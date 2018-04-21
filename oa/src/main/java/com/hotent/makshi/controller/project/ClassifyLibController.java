package com.hotent.makshi.controller.project;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.ClassifyStageTask;
import com.hotent.makshi.model.project.FlowChartJsonResult;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ClassifyStageService;
import com.hotent.makshi.service.project.ClassifyStageTaskService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.system.SysOrgService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/project/classifylib/")
public class ClassifyLibController extends BaseController{
	
	@Autowired
	private ClassifyLibraryService classifyLibService;
	@Resource
	private SysOrgService sysOrgService;
	@Autowired
	private ClassifyStageService classifyStageService;
	@Autowired
	private ClassifyStageTaskService classifyStageTaskService;
	@Autowired
	private ProjectService projectService;
	@RequestMapping("list")
	@Action(description = "获取分类库")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = getAutoView();
		QueryFilter queryFilter = new QueryFilter(request, "classifylib");
		List<ClassifyLibrary> list = classifyLibService.getAll(queryFilter);
		ParamEncoder paramEncoder = new ParamEncoder("classifylib");
		for (ClassifyLibrary classifyLibrary : list) {
			long stagenum = classifyStageService.getCountByClassifyid(classifyLibrary.getId());
			classifyLibrary.setStagenum(stagenum);
			long tasknum = classifyStageTaskService.getTaskCountByClassifyid(classifyLibrary.getId());
			classifyLibrary.setTasknum(tasknum);
			long pronum = projectService.getCountByClassifyLibId(classifyLibrary.getId());
			classifyLibrary.setPronum(pronum);
		}
		List<SysOrg> orgs = sysOrgService.getOrgsAll();
		mv.addObject("classifyliblist", list).addObject("orgs", orgs).addObject("tableIdCode", paramEncoder.encodeParameterName(""));
		return mv;
	}
	
	@RequestMapping("edit")
	@Action(description = "获取分类库")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = getAutoView();
		Boolean isHaveStage=false;
		ClassifyLibrary classifyLibrary = classifyLibService.getById(id);
		QueryFilter queryFilter = new QueryFilter(request, "classifyStage");
		List<ClassifyStage> classifyStageList = classifyStageService.getByClassifyId(id,queryFilter);
		List<ClassifyStage> byClassifyId = classifyStageService.getByClassifyId(id);
		if(byClassifyId!=null && byClassifyId.size()>0){
			isHaveStage=true;
		}
		queryFilter.setForWeb();
		return mv.addObject("classifyLib", classifyLibrary).addObject("classifyStageList", classifyStageList).addObject("isHaveStage", isHaveStage);
	}
	
	@RequestMapping("add")
	@Action(description = "新建分类库")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long id=RequestUtil.getLong(request, "id", 0l);
		ClassifyLibrary byId = classifyLibService.getById(id);
		return mv.addObject("classifyLib", byId);
	}
	
	@RequestMapping("save")
	@Action(description = "新建分类库")
	public void save(HttpServletRequest request, HttpServletResponse response,ClassifyLibrary classifyLibrary) throws Exception {
		String resultMsg = null;
		try {
			PrintWriter out = response.getWriter();
			if(classifyLibrary.getId()==null){
				Long currentUserId = ContextUtil.getCurrentUserId();
				//Long currentOrgId = ContextUtil.getCurrentOrgId()==0l?-1l:ContextUtil.getCurrentOrgId();//主岗位组织id
				classifyLibrary.setCuser(currentUserId);
				classifyLibrary.setCtime(new Date());
				classifyLibrary.setIsused(0);
				classifyLibrary.setIsdelete(0);
				classifyLibrary.setId(UniqueIdUtil.genId());
				//添加父子层级关系
				if(classifyLibrary.getSupid()==null){
					classifyLibrary.setSupid(0l);
				}
				classifyLibrary.setOrder(0);
				ClassifyLibrary byId = classifyLibService.getById(classifyLibrary.getSupid());
				if(byId==null){
					classifyLibrary.setPath(classifyLibrary.getSupid() + "." + classifyLibrary.getId() + ".");
					classifyLibrary.setPathname("/" + classifyLibrary.getName());
				}else{
					classifyLibrary.setPath(byId.getPath() + classifyLibrary.getId() + ".");
					classifyLibrary.setPathname(byId.getPathname() + "/" + classifyLibrary.getName());
				}
				classifyLibService.add(classifyLibrary);
				/*resultMsg = getText("添加", "阶段库信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);*/
				String result="{result:1,id:"+classifyLibrary.getId()+",operate:'add'}";
				out.print(result);
				return ;
			}else{
				int savetype = RequestUtil.getInt(request, "savetype",0);
				if(savetype==1){
					classifyLibrary.setUtime(new Date());
					classifyLibrary.setUuser(ContextUtil.getCurrentUserId());
					long stagenum = classifyStageService.getCountByClassifyid(classifyLibrary.getId());
					if(stagenum>0){//如果已有阶段 不能修改组织
						classifyLibrary.setOrgID(null);
						classifyLibrary.setOrg(null);
					}
					classifyLibService.update(classifyLibrary);
					/*resultMsg = getText("更新", "分类库");*/
					String result="{result:1,operate:'edit'}";
					out.print(result);
					return ;
				}else{
					classifyLibrary.setUtime(new Date());
					classifyLibrary.setUuser(ContextUtil.getCurrentUserId());
					classifyLibService.update(classifyLibrary);
					ClassifyLibrary temp = classifyLibService.getById(classifyLibrary.getId());
					if(temp.getIsused()==1){
						//已启用就不能更新排序了
					}else{
						//更新阶段排序
						Map<Long, Integer> orderMap = classifyLibrary.getOrderMap();
						if(orderMap!=null && orderMap.size()>0){
							Set<Long> keySet = orderMap.keySet();
							Iterator<Long> iterator = keySet.iterator();
							while(iterator.hasNext()){
								Long classifyStageid = iterator.next();
								Integer classifyStageorder = orderMap.get(classifyStageid);
								if(classifyStageid!=null || classifyStageorder!=null){
									classifyStageService.updateTaskOrder(classifyStageid,classifyStageorder);
								}
							}
						}
					}
					resultMsg = getText("更新", "阶段库信息");
				}
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping(value = "updateClassifyLibOrder" ,method=RequestMethod.POST)
	@Action(description = "更新分类库排序字段")
	public String updateClassifyLibOrder(HttpServletRequest request, HttpServletResponse response,ClassifyLibrary classifyLibrary) throws Exception {
		//更新阶段排序
		Map<Long, Integer> orderMap = classifyLibrary.getOrderMap();
		if(orderMap!=null && orderMap.size()>0){
			Set<Long> keySet = orderMap.keySet();
			Iterator<Long> iterator = keySet.iterator();
			while(iterator.hasNext()){
				Long classifyid = iterator.next();
				Integer classifylibOrder = orderMap.get(classifyid);
				if(classifyid!=null || classifylibOrder!=null){
					classifyLibService.updateOrder(classifyid,classifylibOrder);
				}
			}
		}
		return "forward:/makshi/project/classifylib/list.ht";
	}
	
	@RequestMapping("addClassifyStage")
	@Action(description = "添加分类库阶段信息")
	public void addClassifyStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "stageno");
			Long classifyid = RequestUtil.getLong(request, "classifyid");
			ClassifyLibrary byId = classifyLibService.getById(classifyid);
			if(byId==null || byId.getIsdelete()==1){
				throw new RuntimeException("分类库不存在或者已被删除！");
			}
			if(1==byId.getIsused()){
				throw new RuntimeException("分类库已被启用！");
			}
			classifyStageService.addStageByStageLibIds(lAryId,classifyid);
			resultMsg = getText("添加", "分类库阶段信息");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "操作失败," + e.getMessage(), ResultMessage.Fail);
		}
	}
	@RequestMapping("check")
	@Action(description = "检查分类库阶段和任务")
	public void check(HttpServletRequest request, HttpServletResponse response,ClassifyLibrary classifylib) throws Exception {
		String resultMsg = null;
		try {
			if(classifylib.getId()!=null){
				ResultMessage checkStage = classifyLibService.checkStage(classifylib.getId());
				resultMsg = checkStage.getMessage();
				if(checkStage.getResult()==1){
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				}else{
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				}
			}else{
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
		
	}
	
	@RequestMapping("stageTaskCheck")
	@Action(description = "检查分类库阶段任务")
	public void stageTaskCheck(HttpServletRequest request, HttpServletResponse response,ClassifyStage classifyStage) throws Exception {
		String resultMsg = null;
		try {
			if(classifyStage.getId()!=null){
				ResultMessage checkStage = classifyStageService.checkTask(classifyStage.getId());
				resultMsg = checkStage.getMessage();
				if(checkStage.getResult()==1){
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				}else{
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				}
			}else{
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
		
	}
	
	@RequestMapping("publish")
	@Action(description = "启用分类库")
	public void publish(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		try {
			Long classifyid = RequestUtil.getLong(request, "id");
			ClassifyLibrary byId = classifyLibService.getById(classifyid);
			if(byId==null || byId.getIsdelete()==1){
				throw new RuntimeException("分类库不存在或者已被删除！");
			}
			if(1==byId.getIsused()){
				throw new RuntimeException("分类库已被启用！");
			}
			ResultMessage checkStage = classifyLibService.checkStage(byId.getId());
			if(checkStage.getResult()==1){
				byId.setIsused(1);
				classifyLibService.update(byId);
				resultMsg=getText("启用成功");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				resultMsg = checkStage.getMessage();
				writeResultMessage(response.getWriter(), "启用失败,"+resultMsg, ResultMessage.Fail);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), "操作失败," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("classifyStageSave")
	@Action(description = "保存分类库阶段")
	public void classifyStageSave(HttpServletRequest request, HttpServletResponse response,ClassifyStage classifyStage) throws Exception {
		String resultMsg = null;
		Long classifyId = RequestUtil.getLong(request, "classifyId");
		ClassifyLibrary classifyLib = classifyLibService.getById(classifyId);
		try {
			if(classifyStage.getId()!=null){
				if(classifyLib==null || classifyLib.getIsdelete()==1){
					resultMsg=getText("操作失败");
					throw new RuntimeException("分类库不存在或已被删除！");
				}
				if(classifyLib.getIsused()==1){
					//已启用 只能修改名称
					//已启用 不能修改
					/*classifyStage.setStagetype(null);
					classifyStage.setPrestage(null);
					classifyStage.setAfterstage(null);
					classifyStageService.update(classifyStage,false);//不维护前后置关系
*/				}else{
					Long[] pre = RequestUtil.getLongAry(request, "prestageid");
					Long[] after = RequestUtil.getLongAry(request, "afterstageid");
					
					if(pre==null || pre.length==0){
						classifyStage.setPrestage("");
					}else{
						classifyStage.setPrestage(StringUtils.join(pre, ","));
					}
					if(after==null || after.length==0){
						classifyStage.setAfterstage("");
					}else{
						classifyStage.setAfterstage(StringUtils.join(after, ","));
					}
					classifyStageService.update(classifyStage,true);
				}
				
				resultMsg = getText("保存", "阶段信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				resultMsg=getText("操作失败");
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("classifyStageTaskSave")
	@Action(description = "保存分类库阶段任务")
	public void classifyStageTaskSave(HttpServletRequest request, HttpServletResponse response,ClassifyStageTask classifyStageTask) throws Exception {
		String resultMsg = null;
		try {
			if(classifyStageTask.getId()!=null){
				Long classifylibid = RequestUtil.getLong(request, "classifylibid");
				ClassifyLibrary classifyLib = classifyLibService.getById(classifylibid);
				if(classifyLib==null || classifyLib.getIsdelete()==1){
					resultMsg=getText("操作失败");
					throw new RuntimeException("分类库不存在或已被删除！");
				}
				if(1==classifyLib.getIsused()){
					//已启用 只能修改名称
					//已启用 不能修改
					/*classifyStageTask.setAfterclassifystagetaskid(null);
					classifyStageTask.setQueryclassifystagetaskid(null);
					classifyStageTask.setPreclassifystagetaskid(null);
					classifyStageTaskService.update(classifyStageTask,false);*/
				}else{
					
					Long[] pre = RequestUtil.getLongAry(request, "prestagetaskid");
					Long[] after = RequestUtil.getLongAry(request, "afterstagetaskid");
					Long[] query = RequestUtil.getLongAry(request, "querystagetaskid");
					if(pre==null || pre.length==0){
						classifyStageTask.setPreclassifystagetaskid("");
					}else{
						classifyStageTask.setPreclassifystagetaskid(StringUtils.join(pre, ","));
					}
					if(after==null || after.length==0){
						classifyStageTask.setAfterclassifystagetaskid("");
					}else{
						classifyStageTask.setAfterclassifystagetaskid(StringUtils.join(after, ","));
					}
					if(query==null || query.length==0){
						classifyStageTask.setQueryclassifystagetaskid("");
					}else{
						classifyStageTask.setQueryclassifystagetaskid(StringUtils.join(query, ","));
					}
					classifyStageTaskService.update(classifyStageTask,true);
				}
				resultMsg = getText("保存", "阶段任务信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}else{
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("editStage")
	@Action(description = "设置分类库阶段前驱后驱")
	public ModelAndView editStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "classifystageid");
		ModelAndView mv = getAutoView();
		ClassifyStage classifyStage = classifyStageService.getById(id);
		ClassifyLibrary classifyLibrary=null;
		List<ClassifyStage> prestageList=new ArrayList<>();
		List<ClassifyStage> afterstageList=new ArrayList<>();
		if(classifyStage!=null){
			Long classifylibraryid = classifyStage.getClassifylibraryid();
			classifyLibrary= classifyLibService.getById(classifylibraryid);
			String prestage = classifyStage.getPrestage();
			String afterstage = classifyStage.getAfterstage();
			if(prestage!=null && prestage.trim().length()>0){
				String[] split = prestage.trim().split(",");
				for (String string : split) {
					ClassifyStage byId = classifyStageService.getById(Long.parseLong(string));
					if(byId!=null){
						prestageList.add(byId);
					}
				}
			}
			if(afterstage!=null && afterstage.trim().length()>0){
				String[] split = afterstage.trim().split(",");
				for (String string : split) {
					ClassifyStage byId = classifyStageService.getById(Long.parseLong(string));
					if(byId!=null){
						afterstageList.add(byId);
					}
				}
			}
			
		}
		return mv.addObject("classifyLib", classifyLibrary).addObject("classifyStage", classifyStage)
				.addObject("prestageList", prestageList).addObject("afterstageList", afterstageList);
	}
	
	@RequestMapping("setStageTask")
	@Action(description = "设置分类库阶段任务前驱后驱")
	public ModelAndView setStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = getAutoView();
		ClassifyStageTask classifyStageTask = classifyStageTaskService.getById(id);
		ClassifyStage classifyStage=null;
		ClassifyLibrary classifyLibrary=null;
		List<ClassifyStageTask> prestagetaskList=new ArrayList<>();
		List<ClassifyStageTask> afterstagetaskList=new ArrayList<>();
		List<ClassifyStageTask> querystagetaskList=new ArrayList<>();
		if(classifyStageTask!=null){
			Long classifystageid = classifyStageTask.getClassifystageid();
			classifyStage = classifyStageService.getById(classifystageid);
			if(classifyStage!=null){
				Long classifylibraryid = classifyStage.getClassifylibraryid();
				classifyLibrary= classifyLibService.getById(classifylibraryid);
			}
			String prestagetask = classifyStageTask.getPreclassifystagetaskid();
			String afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
			String querystagetask = classifyStageTask.getQueryclassifystagetaskid();
			if(prestagetask!=null && prestagetask.trim().length()>0){
				String[] split = prestagetask.trim().split(",");
				for (String string : split) {
					ClassifyStageTask byId = classifyStageTaskService.getById(Long.parseLong(string));
					if(byId!=null){
						prestagetaskList.add(byId);
					}
				}
			}
			if(afterstagetask!=null && afterstagetask.trim().length()>0){
				String[] split = afterstagetask.trim().split(",");
				for (String string : split) {
					ClassifyStageTask byId = classifyStageTaskService.getById(Long.parseLong(string));
					if(byId!=null){
						afterstagetaskList.add(byId);
					}
				}
			}
			if(querystagetask!=null && querystagetask.trim().length()>0){
				String[] split = querystagetask.trim().split(",");
				for (String string : split) {
					ClassifyStageTask byId = classifyStageTaskService.getById(Long.parseLong(string));
					if(byId!=null){
						querystagetaskList.add(byId);
					}
				}
			}
			
		}
		return mv.addObject("classifyLib", classifyLibrary).addObject("classifyStage", classifyStage).addObject("classifyStageTask", classifyStageTask)
				.addObject("prestagetaskList", prestagetaskList).addObject("afterstagetaskList", afterstagetaskList)
				.addObject("querystagetaskList", querystagetaskList);
	}
	
	@RequestMapping("delStage")
	@Action(description = "删除分类库阶段")
	public void delStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			Long classifyId = RequestUtil.getLong(request, "classifyId");
			ClassifyLibrary byId = classifyLibService.getById(classifyId);
			if(byId==null){
				throw new RuntimeException("分类库不存在或已被删除！");
			}
			if(1==byId.getIsused()){
				throw new RuntimeException("分类库已被启用！");
			}
			classifyStageService.delByIds(lAryId);
			//删除阶段下的任务
			classifyStageTaskService.delByStageIds(lAryId);
			
			message = new ResultMessage(ResultMessage.Success, "删除阶段成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除阶段失败，" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	@RequestMapping("stageSelector")
	@Action(description = "分类库阶段前置后置节点选择器")
	public ModelAndView stageSelector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long classifyid = RequestUtil.getLong(request, "classifyid");
		Long classifystageid=RequestUtil.getLong(request, "classifystageid");
		int selectType = RequestUtil.getInt(request, "selectType");
		ClassifyStage classifyStage = classifyStageService.getById(classifystageid);
		List<ClassifyStage> classifyStageList=null;
		if(classifyStage!=null){
			classifyStageList = classifyStageService.getByClassifyId(classifyid);
			List<String> filterArr=new ArrayList<>();
			filterArr.add(classifyStage.getId()+"");
			
			//阶段前置节点和后置节点不能同时包含某个阶段
			/*if(classifyStage.getPrestage()!=null && classifyStage.getPrestage().trim().length()>0){
				String[] split = classifyStage.getPrestage().split(",");
				for (String string : split) {
					filterArr.add(string);
				}
			}
			if(classifyStage.getAfterstage()!=null && classifyStage.getAfterstage().trim().length()>0){
				String[] split = classifyStage.getAfterstage().split(",");
				for (String string : split) {
					filterArr.add(string);
				}
			}*/
			Iterator<ClassifyStage> iterator = classifyStageList.iterator();
			while(iterator.hasNext()){
				ClassifyStage next = iterator.next();
				if(filterArr.contains(next.getId()+"")){
					iterator.remove();
				}else{
					if(selectType==0){//前置节点不能有结束阶段
						if(next.getStagetype()==3){
							iterator.remove();
						}
					}else if(selectType==1){//后置节点不能有开始阶段
						if(next.getStagetype()==1){
							iterator.remove();
						}
					}
				}
			}
		}
		
		mv.addObject("classifyStageList", classifyStageList).addObject("classifystageid", classifystageid)
		.addObject("classifyid", classifyid).addObject("selectType", selectType);
		return mv;
	}
	
	@RequestMapping("stageTaskSelector")
	@Action(description = "分类库阶段任务前置后置节点选择器")
	public ModelAndView stageTaskSelector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long classifystagetaskid = RequestUtil.getLong(request, "classifystagetaskid");
		Long classifystageid=RequestUtil.getLong(request, "classifystageid");
		int selectType = RequestUtil.getInt(request, "selectType");
		ClassifyStageTask classifyStageTask = classifyStageTaskService.getById(classifystagetaskid);
		List<ClassifyStageTask> classifyStageTaskList=null;
		if(classifyStageTask!=null){
			classifyStageTaskList = classifyStageTaskService.getStageTaskByStageId(classifystageid);
			List<String> filterArr=new ArrayList<>();
			filterArr.add(classifyStageTask.getId()+"");
			
			/*if(selectType==2){//可查看节点选择类型
				String queryclassifystagetaskid = classifyStageTask.getQueryclassifystagetaskid();
				if(queryclassifystagetaskid!=null && queryclassifystagetaskid.trim().length()>0){
					String[] split = queryclassifystagetaskid.split(",");
					for (String string : split) {
						filterArr.add(string);
					}
				}
			}else{
				//阶段任务前置节点和后置节点不能同时包含某个阶段
				if(classifyStageTask.getPreclassifystagetaskid()!=null && classifyStageTask.getPreclassifystagetaskid().trim().length()>0){
					String[] split = classifyStageTask.getPreclassifystagetaskid().split(",");
					for (String string : split) {
						filterArr.add(string);
					}
				}
				if(classifyStageTask.getAfterclassifystagetaskid()!=null && classifyStageTask.getAfterclassifystagetaskid().trim().length()>0){
					String[] split = classifyStageTask.getAfterclassifystagetaskid().split(",");
					for (String string : split) {
						filterArr.add(string);
					}
				}
				
			}*/
			
			Iterator<ClassifyStageTask> iterator = classifyStageTaskList.iterator();
			while(iterator.hasNext()){
				ClassifyStageTask next = iterator.next();
				if(filterArr.contains(next.getId()+"")){
					iterator.remove();
				}
			}
		}
		
		mv.addObject("classifyStageTaskList", classifyStageTaskList).addObject("classifystageid", classifystageid)
		.addObject("classifystagetaskid", classifystagetaskid).addObject("selectType", selectType);
		return mv;
	}
	
	/**
	 * 过滤重复的前后置节点的id 再返回拼接后的字符串(防止使用前后置节点选择器出现重复问题)
	 * @param prestage
	 * @return
	 */
	private String filterStr(String prestage) {
		String rtvalue="";
		if(StringUtils.isEmpty(prestage)){
			return "";
		}else{
			String[] split = prestage.trim().split(",");
			List<String> listTemp=new ArrayList<>();
			for (String string : split) {
				if(!listTemp.contains(string)){
					listTemp.add(string);
				}
			}
			if(listTemp.size()>0){
				String join = StringUtils.join(listTemp, ",");
				return join;
			}
			return rtvalue;
		}
	}

	@RequestMapping("stageTaskList")
	@Action(description = "分类库阶段任务列表")
	public ModelAndView stageTaskList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long classifystageid = RequestUtil.getLong(request, "classifystageid");
		Long classifylibid = RequestUtil.getLong(request, "classifylibid");
		ClassifyLibrary classifyLib = classifyLibService.getById(classifylibid);
		QueryFilter queryFilter = new QueryFilter(request,"taskItem");
		queryFilter.getFilters().put("classifystageid", classifystageid);
		queryFilter.getFilters().put("isdelete", 0);
		//List<ClassifyStageTask> stageTaskList = classifyStageTaskService.getStageTaskByStageId(classifystageid);
		List<ClassifyStageTask> stageTaskList = classifyStageTaskService.getStageTaskByStageId(queryFilter);
		queryFilter.setForWeb();
		ModelAndView mv=getAutoView();
		mv.addObject("stageTaskList", stageTaskList).addObject("classifystageid", classifystageid).addObject("classifyLib", classifyLib);
		return mv;
	}
	
	@RequestMapping("editStageTask")
	@Action(description = "分类库阶段任务添加和修改")
	public ModelAndView editStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
  		Long classifystageid = RequestUtil.getLong(request, "classifystageid");
		ClassifyStage classifyStage = classifyStageService.getById(classifystageid);
		ModelAndView mv=getAutoView();
		if(classifyStage!=null){
			mv.addObject("stagename", classifyStage.getStagename())
			.addObject("classifystageid", classifyStage.getId());
	
			Long classifylibraryid = classifyStage.getClassifylibraryid();
			ClassifyLibrary classifyLibrary = classifyLibService.getById(classifylibraryid);
			if(classifyLibrary!=null){
				mv.addObject("classifyname", classifyLibrary.getName()).addObject("classifylibid", classifyLibrary.getId());
			}
		}
		Long id = RequestUtil.getLong(request, "id");
		ClassifyStageTask classifyStageTask = classifyStageTaskService.getById(id);
		
		mv.addObject("classifyStageTask", classifyStageTask);
		return mv;
	}
	
	@RequestMapping("viewStageTask")
	@Action(description = "分类库阶段任务查看")
	public ModelAndView viewStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
  		Long classifystageid = RequestUtil.getLong(request, "classifystageid");
		ClassifyStage classifyStage = classifyStageService.getById(classifystageid);
		ModelAndView mv=getAutoView();
		if(classifyStage!=null){
			mv.addObject("stagename", classifyStage.getStagename())
			.addObject("classifystageid", classifyStage.getId());
	
			Long classifylibraryid = classifyStage.getClassifylibraryid();
			ClassifyLibrary classifyLibrary = classifyLibService.getById(classifylibraryid);
			if(classifyLibrary!=null){
				mv.addObject("classifyname", classifyLibrary.getName()).addObject("classifylibid", classifyLibrary.getId());
			}
		}
		Long id = RequestUtil.getLong(request, "id");
		ClassifyStageTask classifyStageTask = classifyStageTaskService.getById(id);
		
		mv.addObject("classifyStageTask", classifyStageTask);
		return mv;
	}
	
	@RequestMapping("stageTaskSave")
	@Action(description = "保存任务库")
	public void stageTaskSave(HttpServletRequest request, HttpServletResponse response,ClassifyStageTask classifyStageTask) throws Exception {
		String resultMsg=null;
		try{
			Long classifystageid = RequestUtil.getLong(request, "classifystageid");
			Long classifylibid = RequestUtil.getLong(request, "classifylibid");
			ClassifyLibrary byId = classifyLibService.getById(classifylibid);
			if(byId==null || byId.getIsdelete()==1){
				resultMsg=getText("操作失败");
				throw new RuntimeException("分类不存在或者已经被删除！");
			}
			if(byId.getIsused()==1){
				resultMsg=getText("操作失败");
				throw new RuntimeException("分类已经被启用！");
			}
			if(classifystageid==0){
				resultMsg = getText("参数错误");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return ;
			}
			if(classifyStageTask.getIsexamine()==null){
				classifyStageTask.setIsexamine(false);
			}
			if(classifyStageTask.getIsmore()==null){
				classifyStageTask.setIsmore(false);
			}
			if(classifyStageTask.getId()==null){
				//插入
				classifyStageTask.setIsdelete(0);
				classifyStageTask.setPreclassifystagetaskid("");
				classifyStageTask.setAfterclassifystagetaskid("");
				classifyStageTask.setQueryclassifystagetaskid("");
				classifyStageTask.setOrder(0);
				//取当前分类库阶段下任务编号最大的任务
				int max=classifyStageTaskService.getMaxtasknoByClassifystageid(classifystageid);
				classifyStageTask.setTaskno(max+1);
				classifyStageTaskService.add(classifyStageTask);
				resultMsg = getText("添加", "阶段任务库信息");
			}else{
				//更新
				classifyStageTaskService.update(classifyStageTask);
				resultMsg = getText("更新", "阶段任务库信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		}catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	@RequestMapping("delStageTask")
	@Action(description = "删除分类库阶段任务")
	public void delStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long classifylibid = RequestUtil.getLong(request, "classifylibid");
		ResultMessage message = null;
		try {
			ClassifyLibrary classifyLib = classifyLibService.getById(classifylibid);
			if(classifyLib==null || classifyLib.getIsdelete()==1){
				throw new RuntimeException("分类库不存在或已被删除！");
			}
			if(1==classifyLib.getIsused()){
				throw new RuntimeException("分类库已被启用！");
			}
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			classifyStageTaskService.delByIds(lAryId);
			
			message = new ResultMessage(ResultMessage.Success, "删除任务成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除任务失败," + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	@RequestMapping("del")
	@Action(description = "删除分类库")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		
		ResultMessage message = null;
		try {
			
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			
			classifyLibService.delByIds(lAryId);
			
			message = new ResultMessage(ResultMessage.Success, "删除分类库成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除分类库失败," + ex.getMessage());
		}
		response.getWriter().print(message);
		//addMessage(message, request);
		//response.sendRedirect(preUrl);
	}
	
	@RequestMapping("stageFlowchart")
	@Action(description = "阶段流程图")
	public ModelAndView stageFlowchart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long classifyid = RequestUtil.getLong(request, "id");
		ResultMessage checkStage = classifyLibService.checkStage(classifyid);
		ModelAndView mv = getAutoView();
		if(checkStage.getResult()==1){
			FlowChartJsonResult structflowChartData = classifyLibService.structflowChartData(classifyid);
			JSONObject fromObject = JSONObject.fromObject(structflowChartData);
			return mv.addObject("data", fromObject).addObject("id", classifyid);
		}else{
			return mv.addObject("data", null);
		}
	}
	
	@RequestMapping("taskFlowchart")
	@Action(description = "任务流程图")
	public ModelAndView taskFlowchart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long stageid = RequestUtil.getLong(request, "id");
		long classifyid = RequestUtil.getLong(request, "classifyid");
		ResultMessage checkStage = classifyStageService.checkTask(stageid);
		ModelAndView mv = getAutoView();
		if(checkStage.getResult()==1){
			FlowChartJsonResult structflowChartData = classifyStageService.structflowChartData(stageid);
			JSONObject fromObject = JSONObject.fromObject(structflowChartData);
			return mv.addObject("data", fromObject).addObject("id", stageid).addObject("classifyid", classifyid);
		}else{
			return mv.addObject("data", null);
		}
	}
	
}
