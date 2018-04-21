package com.hotent.makshi.controller.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.ClassifyStageTask;
import com.hotent.makshi.model.project.FieldList;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.project.ProjectCollection;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.model.project.ProjectStageTaskField;
import com.hotent.makshi.model.project.ProjectTaskLogs;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ClassifyStageService;
import com.hotent.makshi.service.project.ClassifyStageTaskService;
import com.hotent.makshi.service.project.ProjectCollectionService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.project.ProjectStageService;
import com.hotent.makshi.service.project.ProjectStageTaskFieldService;
import com.hotent.makshi.service.project.ProjectStageTaskService;
import com.hotent.makshi.service.project.ProjectTaskLogsService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.model.system.SysPlan;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.system.MessageSendService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysPlanService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/makshi/project/project/")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectStageService projectStageService;
	@Autowired
	private ProjectStageTaskService projectStageTaskService;
	@Autowired
	private ClassifyLibraryService classifyLibraryService;
	@Autowired
	private ClassifyStageService classifyStageService;
	@Autowired
	private ClassifyStageTaskService classifyTaskService;
	@Autowired
	private ProjectCollectionService projectCollectionService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmService bpmService;
	@Resource
	private SysFileService fileService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	@Resource
	private MessageSendService msgSendService;
	@Resource
	private ProjectTaskLogsService taskLogsService;
	@Resource
	private ProjectStageTaskFieldService taskFieldService;
	@Resource
	private SysUserService userService;
	@Resource
	private ContractinfoService contractinfoService;
	@Resource
	private SysPlanService sysPlanService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private SysRoleService sysRoleService;

	@RequestMapping("list")
	@Action(description = "获取分类下项目")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		Long classifylibraryid = RequestUtil.getLong(request, "classifylibraryid");
		ClassifyLibrary classifyLibrary = classifyLibraryService.getById(classifylibraryid);
		boolean isCharge = false;// 分类负责人
		if (ContextUtil.isSuperAdmin()) {
			isCharge = true;
		}
		if (!isCharge && classifyLibrary != null) {
			String chargerID = classifyLibrary.getChargerID();
			String[] ids = chargerID.split(",");
			for (String id : ids) {
				if (id.equals(currentUserId + "")) {
					isCharge = true;
					break;
				}
			}
		}
		ModelAndView mv = getAutoView();
		List<Project> list = projectService.getAll(new QueryFilter(request, "projectItem"));
		for (Project project : list) {
			List<ProjectStage> listStage = projectStageService.getProjectStageByProId(project.getId());
			for (ProjectStage stage : listStage) {
				List<ProjectStageTask> tasks = projectStageTaskService.getProjectStageTaskByStageId(stage.getId());
				int ends = 0;
				int taskStatus = 1;
				for (ProjectStageTask task : tasks) {
					if (task == null)
						continue;
					// if((task.getStatus()==null?0:task.getStatus())==1){
					// ends++;
					// }
					if ((task.getStatus() == null ? 0 : task.getStatus()) == 0) {
						taskStatus = 0;
					}
					ends += task.getEndcount();
				}
				stage.setEnds(ends);
				stage.setTaskStatus(taskStatus);
			}
			project.setStages(listStage);

		}

		mv.addObject("list", list).addObject("classifylibraryid", classifylibraryid).addObject("isCharge", isCharge);
		return mv;
	}

	@RequestMapping("myTaskList")
	@Action(description = "我的任务")
	public ModelAndView myTaskList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter queryFilter = new QueryFilter(request, "taskItem");
		queryFilter.getFilters().put("charge", "%" + currentUser.getFullname() + "%");
		String projectname = request.getParameter("projectname");
		if (StringUtils.isNotEmpty(projectname))
			queryFilter.getFilters().put("projectname", "%" + projectname + "%");

		String stagename = request.getParameter("stagename");
		if (StringUtils.isNotEmpty(stagename))
			queryFilter.getFilters().put("stagename", "%" + stagename + "%");

		String taskname = request.getParameter("taskname");
		if (StringUtils.isNotEmpty(taskname))
			queryFilter.getFilters().put("taskname", "%" + taskname + "%");

		String status = request.getParameter("status");
		if (StringUtils.isNotEmpty(status) && Integer.valueOf(status) == -1)
			queryFilter.getFilters().put("status", null);
		return getAutoView().addObject("list", projectStageTaskService.getAll(queryFilter)).addObject("projectname", projectname).addObject("stagename", stagename).addObject("taskname", taskname)
				.addObject("status", status);
	}

	@RequestMapping("getMyTaskListData")
	@Action(description = "我的未完成任务")
	@ResponseBody
	public List<ProjectStageTask> getMyTaskListData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter queryFilter = new QueryFilter(request, "taskItem");
		queryFilter.getFilters().put("charge", "%" + currentUser.getFullname() + "%");
		queryFilter.getFilters().put("status", 1);
		Date date;
		try {
			date = RequestUtil.getDate(request, "select_time", "yyyy-MM-dd");
		} catch (Exception e) {
			return new ArrayList<ProjectStageTask>();
		}
		queryFilter.getFilters().put("select_time", date);
		List<ProjectStageTask> list = projectStageTaskService.getAll(queryFilter);
		for (ProjectStageTask task : list) {
			if (task != null) {
				Integer stageId = task.getProstageid();
				ProjectStage stage = projectStageService.getById(stageId.longValue());
				if (stage != null) {
					task.setStage(stage);
					Integer proId = stage.getProid();
					Project project = projectService.getById(proId.longValue());
					task.setProject(project);
				}
			}
		}
		return list;
	}

	@RequestMapping("getProListData")
	@Action(description = "我的未完成任务")
	@ResponseBody
	public List<Project> getProListData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ISysUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter queryFilter = new QueryFilter(request, "taskItem");
		queryFilter.setPageBean(null);
		queryFilter.getFilters().put("participant", currentUser.getUserId());
		Integer[] status = new Integer[] { 1, 4, 5 };// 在建、建设期、运营期
		queryFilter.getFilters().put("status", status);
		Date date;
		try {
			date = RequestUtil.getDate(request, "select_time", "yyyy-MM-dd");
			if (date == null) {
				return new ArrayList<Project>();
			}
		} catch (Exception e) {
			return new ArrayList<Project>();
		}
		queryFilter.getFilters().put("select_time", date);
		List<Project> myProList = projectService.getProList(queryFilter);
		return myProList;
	}

	@RequestMapping("myProList")
	@Action(description = "获取分类下项目")
	public ModelAndView myProlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = new QueryFilter(request, "projectItem");
		queryFilter.getFilters().put("participant", currentUserId);
		ModelAndView mv = getAutoView();
		List<Project> list = projectService.getMyProList(queryFilter);
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping("myCollections")
	@Action(description = "获取我的关注项目")
	public ModelAndView myCollections(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		QueryFilter queryFilter = new QueryFilter(request, "projectItem");
		queryFilter.getFilters().put("userId", currentUserId);
		ModelAndView mv = getAutoView();
		List<Project> list = projectService.getCollectionedProject(queryFilter);
		queryFilter.setForWeb();
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping("collection")
	@Action(description = "收藏项目")
	public void collection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long projectId = RequestUtil.getLong(request, "id");
		Project project = projectService.getById(projectId);
		Long userId = ContextUtil.getCurrentUser().getUserId();
		if (project != null) {
			ProjectCollection collection = projectCollectionService.getByProjectIdAndUserId(userId, projectId.intValue());
			if (null == collection) {
				collection = new ProjectCollection();
				collection.setCtime(new Date());
				collection.setProjectId(projectId);
				collection.setUserId(userId);
				collection.setState(1);
				projectCollectionService.add(collection);
				response.getWriter().print("ok");
				return;
			} else {
				Integer state = collection.getState();
				if (state == 1) {
					collection.setState(0);
					projectCollectionService.update(collection);
					response.getWriter().print("cancel");
				} else {
					collection.setState(1);
					projectCollectionService.update(collection);
					response.getWriter().print("ok");
				}
				return;
			}
		} else {
			response.getWriter().print("项目已经不存在了！");
		}
	}

	@RequestMapping("cancelCollect")
	@Action(description = "取消收藏项目")
	public void cancelCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long projectId = RequestUtil.getLong(request, "id");
		Project project = projectService.getById(projectId);
		Long userId = ContextUtil.getCurrentUser().getUserId();
		if (project != null) {
			ProjectCollection collection = projectCollectionService.getByProjectIdAndUserId(userId, projectId.intValue());
			if (null == collection) {
				response.getWriter().print("参数错误!");
			} else {
				collection.setState(0);
				projectCollectionService.update(collection);
				response.getWriter().print("ok");
			}
		} else {
			response.getWriter().print("项目已经不存在了！");
		}
	}

	@RequestMapping("save")
	@Action(description = "新建项目")
	public void save(HttpServletRequest request, HttpServletResponse response, Project project) throws Exception {
		String resultMsg = null;
		try {

			if (project.getId() == null) {
				// Long classifyLibraryId = RequestUtil.getLong(request, "classifyLibraryId");//分类ID
				Long currentUserId = ContextUtil.getCurrentUserId();
				project.setCuser(currentUserId + "");
				project.setCtime(new Date());
				project.setIsdelete(0);
				project.setStatus(0);
				projectService.add(project);// 返回主键
				int proId = project.getId();
				// 复制阶段信息,阶段下任务信息
				List<ClassifyStage> clStages = classifyStageService.getByClassifyId(project.getClassifylibraryid());
				for (ClassifyStage stage : clStages) {
					Long clStageId = stage.getId();
					ProjectStage proStage = new ProjectStage(proId, stage.getStagename(), stage.getStageno(), stage.getStagetype(), stage.getOrder(), stage.getId(), stage.getPrestage(),
							stage.getAfterstage());
					projectStageService.add(proStage);
					int proStageId = proStage.getId();
					List<ClassifyStageTask> clTasks = classifyTaskService.getStageTaskByStageId(clStageId);
					for (ClassifyStageTask task : clTasks) {
						ProjectStageTask proStageTask = new ProjectStageTask(proStageId, task.getTaskno(), task.getTaskname(), task.getTemplatefile(), task.getTasktype(), task.getRemark(),
								task.getIsexamine(), task.getIsmore(), task.getOrder(), task.getFields(), task.getUploadfile(), task.getFlowid(), task.getId() + "", task.getPreclassifystagetaskid(),
								task.getAfterclassifystagetaskid(), task.getQueryclassifystagetaskid(), 0);
						projectStageTaskService.add(proStageTask);
					}

				}

				// 发送消息
				if (!StringUtil.isEmpty(project.getMemberID())) {
					MessageSend send1 = new MessageSend();
					send1.setSubject("项目:" + project.getName() + "创建成功");
					send1.setContent("项目:" + project.getName() + "创建成功");
					send1.setContentText("项目:" + project.getName() + "创建成功");
					send1.setMessageType("1");
					msgSendService.addMessageSend(send1, (SysUser) ContextUtil.getCurrentUser(), project.getMemberID(), project.getMember(), "", "");
				}
				if (!StringUtil.isEmpty(project.getApplicantID())) {
					MessageSend send2 = new MessageSend();
					send2.setSubject("项目:" + project.getName() + "创建成功");
					send2.setContent("项目:" + project.getName() + "创建成功");
					send2.setContentText("项目:" + project.getName() + "创建成功");
					send2.setMessageType("1");
					msgSendService.addMessageSend(send2, (SysUser) ContextUtil.getCurrentUser(), project.getApplicantID(), project.getApplicant(), "", "");
				}

				resultMsg = getText("添加", "项目信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			} else {
				project.setUtime(new Date());
				project.setUuser(ContextUtil.getCurrentUserId() + "");
				projectService.update(project);
				resultMsg = getText("更新", "项目信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("importStage")
	@Action(description = "添加项目阶段")
	public void addStage() {
		addStageFromClassify(30000000191184l, 10000000000006l);// 政府采购类--工程 -----质疑处理
		addStageFromClassify(30000000191191l, 10000000000008l);// 政府采购类--货物 -----质疑处理
		addStageFromClassify(30000000191193l, 10000000000010l);// 政府采购类--服务 -----质疑处理

		addStageFromClassify(30000000191184l, 10000000000005l);// 政府采购类--工程 -----中标通知书
		addStageFromClassify(30000000191191l, 10000000000007l);// 政府采购类--货物 -----中标通知书
		addStageFromClassify(30000000191193l, 10000000000009l);// 政府采购类--服务 -----中标通知书

	}

	private void addStageFromClassify(long classifyLibId, long stageid) {
		List<Project> projects = projectService.getByClassifyLibId(classifyLibId);
		for (Project project : projects) {
			int proId = project.getId();
			ClassifyStage stage = classifyStageService.getById(stageid);
			ProjectStage temp = projectStageService.getProjectStageByCstIdAndProId(stageid, proId);
			if (temp == null) {
				Long clStageId = stage.getId();
				ProjectStage proStage = new ProjectStage(proId, stage.getStagename(), stage.getStageno(), stage.getStagetype(), stage.getOrder(), stage.getId(), stage.getPrestage(),
						stage.getAfterstage());
				projectStageService.add(proStage);
				int proStageId = proStage.getId();
				List<ClassifyStageTask> clTasks = classifyTaskService.getStageTaskByStageId(clStageId);
				for (ClassifyStageTask task : clTasks) {
					ProjectStageTask proStageTask = new ProjectStageTask(proStageId, task.getTaskno(), task.getTaskname(), task.getTemplatefile(), task.getTasktype(), task.getRemark(),
							task.getIsexamine(), task.getIsmore(), task.getOrder(), task.getFields(), task.getUploadfile(), task.getFlowid(), task.getId() + "", task.getPreclassifystagetaskid(),
							task.getAfterclassifystagetaskid(), task.getQueryclassifystagetaskid(), 0);
					projectStageTaskService.add(proStageTask);
				}
			}
		}
	}

	@RequestMapping("edit")
	@Action(description = "获取项目")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = getAutoView();
		Project project = projectService.getById(id);
		ClassifyLibrary classifyLibrary = null;
		if (project != null) {
			classifyLibrary = classifyLibraryService.getById(project.getClassifylibraryid());
			// 变更历史原始数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("project", project);// 比较对象

			map.put("queryType", "project");// 类型

			map.put("queryCondition", project.getId() + "");// 查询条件
			// String ipAddr = RequestUtil.getIpAddr(request);
			request.getSession().setAttribute("historyData", map);
		}
		return mv.addObject("project", project).addObject("classifyLibrary", classifyLibrary).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("delProject")
	@Action(description = "删除项目")
	public void delProject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		Project project = projectService.getById(id);
		if (project != null) {
			project.setIsdelete(1);
			projectService.update(project);
			projectStageTaskService.delByProjectId(id);
		}
		resultMsg = getText("删除", "项目");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}

	@RequestMapping("getTask")
	@Action(description = "配置项目")
	public ModelAndView getTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = new ModelAndView("/makshi/project/projectSet.jsp");
		Project project = projectService.getById(id);
		List<ProjectStage> stages = projectStageService.getProjectStageByProId(id.intValue());
		project.setStages(stages);
		for (ProjectStage stage : stages) {
			List<ProjectStageTask> tasks = projectStageTaskService.getProjectStageTaskByStageId(stage.getId());
			stage.setTasks(tasks);
		}
		return mv.addObject("project", project);
	}

	@RequestMapping("setTask")
	@Action(description = "保存配置")
	public void setTask(HttpServletRequest request, HttpServletResponse response, Project project) throws Exception {
		String resultMsg = null;
		// Long classifylibraryid = RequestUtil.getLong(request, "classifylibraryid");
		// ModelAndView mv = new ModelAndView("redirect:/makshi/project/project/list?classifylibraryid="+classifylibraryid);
		ISysUser currentUser = ContextUtil.getCurrentUser();
		List<ProjectStage> stages = project.getStages();
		for (ProjectStage stage : stages) {
			// stageService.update(stage);
			List<ProjectStageTask> tasks = stage.getTasks();
			for (ProjectStageTask task : tasks) {
				if (!StringUtil.isEmpty(task.getCharge()) && task.getStarttime() != null) {
					ProjectStageTask tempTask = projectStageTaskService.getById((long) task.getId());
					SysPlan sysPlanTemp = sysPlanService.getById(tempTask.getSysplanid());
					if (sysPlanTemp == null) {
						sysPlanTemp = new SysPlan();
						long genId = UniqueIdUtil.genId();
						String participantIds = task.getChargeID();
						String participants = task.getCharge();
						sysPlanTemp.setId(genId);
						sysPlanTemp.setEndTime(task.getEndtime());
						sysPlanTemp.setStartTime(task.getStarttime());
						sysPlanTemp.setTaskName(task.getTaskname());
						sysPlanTemp.setSubmitId(currentUser.getUserId());
						sysPlanTemp.setSubmitor(currentUser.getFullname());
						// sysPlanTemp.setCharge(task.getCharge());
						// sysPlanTemp.setChargeId(Long.parseLong(task.getChargeID()));
						sysPlanTemp.setCreateTime(new Date());
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("participantIds", participantIds);
						params.put("participants", participants);
						task.setSysplanid(genId);
						sysPlanService.saveTaskSysPlan(sysPlanTemp, params);
					} else {
						String participantIds = task.getChargeID();
						String participants = task.getCharge();
						sysPlanTemp.setEndTime(task.getEndtime());
						sysPlanTemp.setStartTime(task.getStarttime());
						sysPlanTemp.setTaskName(task.getTaskname());
						sysPlanTemp.setSubmitId(currentUser.getUserId());
						sysPlanTemp.setSubmitor(currentUser.getFullname());
						// sysPlanTemp.setCharge(task.getCharge());
						// sysPlanTemp.setChargeId(Long.parseLong(task.getChargeID()));
						sysPlanTemp.setCreateTime(new Date());
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("participantIds", participantIds);
						params.put("participants", participants);
						sysPlanService.saveSysPlan(sysPlanTemp, params);
					}
				}

				projectStageTaskService.setTask(task);
			}
		}
		resultMsg = getText("配置", "项目信息");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}

	@RequestMapping("projectDetail")
	@Action(description = "项目详情")
	public ModelAndView projectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = new ModelAndView("/makshi/project/projectDetail.jsp");
		Project project = projectService.getById(id);
		if (project != null && StringUtils.isNotEmpty(project.getContractnum())) {
			Contractinfo contractinfoByNum = contractinfoService.getContractinfoByNum(project.getContractnum());
			if (contractinfoByNum != null) {
				project.setContractId(contractinfoByNum.getId());
			}
		}
		List<ProjectStage> stages = projectStageService.getProjectStageByProId(id.intValue());
		boolean collect = projectCollectionService.isCollection(ContextUtil.getCurrentUserId(), id.intValue());
		project.setStages(stages);
		for (ProjectStage stage : stages) {
			List<ProjectStageTask> tasks = projectStageTaskService.getProjectStageTaskByStageId(stage.getId());
			stage.setTasks(tasks);

		}
		return mv.addObject("project", project).addObject("collect", collect);
	}

	@RequestMapping("stageDetail")
	@Action(description = "阶段详情")
	public ModelAndView stageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = new ModelAndView("/makshi/project/stageDetail.jsp");
		ProjectStage stage = projectStageService.getById(id);
		List<ProjectStageTask> tasks = null;
		Project project = null;
		if (stage != null) {
			tasks = projectStageTaskService.getProjectStageTaskByStageId(stage.getId());
			stage.setTasks(tasks);
			project = projectService.getById(stage.getProid().longValue());
		}

		return mv.addObject("stage", stage).addObject("project", project);
	}

	@RequestMapping("toAdd")
	@Action(description = "跳转创建项目页面")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long id = RequestUtil.getLong(request, "id");
		ClassifyLibrary classifyLibrary = classifyLibraryService.getById(id);
		ModelAndView mv = new ModelAndView("/makshi/project/proAdd.jsp");
		return mv.addObject("classifyLibraryId", id).addObject("classifyLibrary", classifyLibrary).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("toTask")
	@Action(description = "跳转填写页面")
	public ModelAndView toTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer taskId = RequestUtil.getInt(request, "taskId");
		ProjectStageTask task = projectStageTaskService.getById(taskId.longValue());
		ModelAndView mv = null;
		if (task.getTasktype() == 1) {
			mv = new ModelAndView("/makshi/project/projectField.jsp");
		} else if (task.getTasktype() == 2) {
			mv = new ModelAndView("/makshi/project/projectUpload.jsp");
		}

		return mv.addObject("task", task);
	}

	@RequestMapping("toEditField")
	@Action(description = "跳转修改字段页面")
	public ModelAndView toEditField(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer taskId = RequestUtil.getInt(request, "taskId");
		String ctime = RequestUtil.getString(request, "ctime");
		List<ProjectStageTaskField> list = taskFieldService.getFieldByTaskAndTime(taskId, ctime);
		// ProjectStageTaskField taskField = taskFieldService.getById(fieldId.longValue());
		ModelAndView mv = new ModelAndView("/makshi/project/projectEditField.jsp");
		;

		return mv.addObject("list", list);
	}

	// 判断该任务是否可执行
	public String checkDo(ProjectStageTask task) {
		Integer stageId = task.getProstageid();
		ProjectStage stage = projectStageService.getById(stageId.longValue());
		if (stage != null) {
			if (StringUtil.isEmpty(stage.getPrestage()) || stage.getStagetype() == 1) {// 没有前驱阶段或者为起始阶段
				if (StringUtil.isEmpty(task.getPretaskid())) {// 没有前驱任务
					return "";
				} else {// 有前驱任务
					String pres = task.getPretaskid();
					String[] preArr = pres.split(",");
					for (String pre : preArr) {
						// ProjectStageTask preTask = taskService.getById(Long.parseLong(pre));
						ProjectStageTask preTask = projectStageTaskService.getStageTaskByStageIdAndCstId(task.getProstageid(), pre);

						if (preTask != null && preTask.getStatus() == 0) {// 前驱任务未完成
							return "前驱任务未完成";
						}
					}
					return "";
				}
			} else {
				// 遍历前驱阶段下每一个任务完成情况
				String pres = stage.getPrestage();
				String[] preArr = pres.split(",");
				for (String pre : preArr) {
					// ProjectStage preStage = stageService.getById(Long.parseLong(pre)); //replace： 修复bug
					ProjectStage preStage = projectStageService.getProjectStageByCstIdAndProId(Long.parseLong(pre), stage.getProid());
					if (preStage == null) {
						return "找不到前驱阶段,配置错误";
					}

					if (preStage.getStatus() == 0) {
						List<ProjectStageTask> tasks = projectStageTaskService.getProjectStageTaskByStageId(preStage.getId());
						for (ProjectStageTask temp : tasks) {
							if (temp.getStatus() == 0) {
								return "前驱阶段中有任务未完成,任务名称:" + temp.getTaskname();
							}
						}
					} else {
						continue;
					}
				}
				return "";
			}
		}
		return "没有该阶段";
	}

	@RequestMapping("checkPre")
	@Action(description = "发起流程前检查前驱任务")
	public void checkPre(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		ProjectStageTask task = projectStageTaskService.getById(id.longValue());
		if (task != null) {
			String checkDo = checkDo(task);
			if (StringUtil.isEmpty(checkDo)) {
				resultMsg = getText("通过", "通过");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			} else {
				resultMsg = getText(checkDo, checkDo);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			}
		}
	}

	@RequestMapping("fieldSave")
	@Action(description = "填写表单")
	public void fieldSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		ProjectStageTask task = projectStageTaskService.getById(id.longValue());
		if (task != null) {
			String checkDo = checkDo(task);
			Date date = new Date();
			if (StringUtil.isEmpty(checkDo)) {
				String fieldJson = RequestUtil.getString(request, "fields");
				// task.setFields(fieldJson);
				JSONArray jsonArr = JSONArray.fromObject(fieldJson);
				for (int i = 0; i < jsonArr.size(); i++) {
					JSONObject object = jsonArr.getJSONObject(i);
					String fieldname = (String) object.get("fieldname");
					String fieldtype = (String) object.get("fieldtype");
					String fieldvalue = "";
					if (fieldtype.equals("attach")) {
						try {
							fieldvalue = ((JSONArray) object.get("fieldvalue")).toString();
						} catch (ClassCastException e) {
							fieldvalue = (String) object.get("fieldvalue");
						}

					} else {
						fieldvalue = (String) object.get("fieldvalue");
					}

					ProjectStageTaskField taskField = new ProjectStageTaskField(task.getId(), fieldname, fieldtype, fieldvalue, ContextUtil.getCurrentUser().getFullname(), date, 0);
					taskFieldService.add(taskField);
				}
				task.setEndcount(task.getEndcount() + 1);
				if (!task.getIsmore()) {
					task.setStatus(1);
				}
				projectStageTaskService.update(task);

				ProjectTaskLogs projectTaskLogs = new ProjectTaskLogs();
				projectTaskLogs.setTaskid(id.intValue());
				projectTaskLogs.setCtime(date);
				projectTaskLogs.setType(1);
				taskLogsService.add(projectTaskLogs);

				resultMsg = getText("填写表单", "填写表单");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			} else {
				resultMsg = getText(checkDo, checkDo);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			}
		}
	}

	@RequestMapping("editField")
	@Action(description = "编辑表单")
	public void editField(HttpServletRequest request, HttpServletResponse response, FieldList list) throws Exception {
		String resultMsg = null;
		if (list != null) {
			List<ProjectStageTaskField> fieldList = list.getList();
			if (fieldList != null && fieldList.size() > 0) {
				for (ProjectStageTaskField taskField : fieldList) {
					if (taskField != null) {
						if (StringUtils.isNotEmpty(taskField.getFieldValue()) && StringUtils.isNotEmpty(taskField.getFieldValueID())) {
							String fieldValueID = taskField.getFieldValueID();
							String fieldValue = taskField.getFieldValue();
							taskField.setFieldValue(fieldValue + "|" + fieldValueID);
						}
						taskFieldService.update(taskField);
					}
				}
			}
			resultMsg = getText("修改表单", "修改表单");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} else {
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
		}

		// ProjectStageTaskField taskField = taskFieldService.getById(id);
		// if(taskField!=null){
		// taskField.setFieldValue(fieldvalue);
		// taskFieldService.update(taskField);
		// resultMsg = getText("修改表单", "修改表单");
		// writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		// }else{
		// writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
		// }
	}

	@RequestMapping("fileSave")
	@Action(description = "上传资料")
	public void fileSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		String fileJson = RequestUtil.getString(request, "files");
		ProjectStageTask task = projectStageTaskService.getById(id.longValue());
		if (task != null && !StringUtil.isEmpty(fileJson)) {
			String checkDo = checkDo(task);
			if (StringUtil.isEmpty(checkDo)) {
				String oldFiles = task.getUploadfile();
				if (StringUtil.isEmpty(oldFiles)) {
					task.setUploadfile(fileJson);
				} else {
					String files = oldFiles.replaceAll("]", ",");
					String temp = fileJson.replaceAll("\\[", "");
					task.setUploadfile(files + temp);
				}
				task.setEndcount(task.getEndcount() + 1);
				if (!task.getIsmore()) {
					task.setStatus(1);
				}
				projectStageTaskService.update(task);

				ProjectTaskLogs projectTaskLogs = new ProjectTaskLogs();
				projectTaskLogs.setTaskid(id.intValue());
				projectTaskLogs.setCtime(new Date());
				projectTaskLogs.setType(2);
				taskLogsService.add(projectTaskLogs);

				resultMsg = getText("上传资料", "上传资料");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			} else {
				resultMsg = getText(checkDo, checkDo);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			}

		}

	}

	@RequestMapping("fileDel")
	@Action(description = "删除资料")
	public void fileDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long fileId = RequestUtil.getLong(request, "fileId");
		Integer taskId = RequestUtil.getInt(request, "taskId");
		SysFile file = fileService.getById(fileId);
		if (file != null) {
			file.setDelFlag((short) 1);
			fileService.update(file);

			ProjectStageTask task = projectStageTaskService.getById(taskId.longValue());
			String uploadfile = task.getUploadfile();
			JSONArray jsonArr = JSONArray.fromObject(uploadfile);
			boolean flag = false;
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject object = jsonArr.getJSONObject(i);
				String id = (String) object.get("id");
				if (id.equals(fileId + "")) {
					jsonArr.remove(i);
					if (jsonArr.size() == 0) {
						task.setUploadfile(" ");
						flag = true;
					}
					break;
				}
			}
			if (!flag) {
				task.setUploadfile(jsonArr.toString());
			}
			Integer endcount = task.getEndcount();
			if (endcount != null && endcount > 0) {
				task.setEndcount(endcount - 1);
			}
			projectStageTaskService.update(task);
		}
		resultMsg = getText("删除资料", "删除资料");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

	}

	@RequestMapping("fieldDel")
	@Action(description = "删除表单填写字段")
	public void fieldDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		int taskId = RequestUtil.getInt(request, "taskId");
		String ctime = RequestUtil.getString(request, "ctime");
		List<ProjectStageTaskField> list = taskFieldService.getFieldByTaskAndTime(taskId, ctime);
		for (ProjectStageTaskField field : list) {
			field.setIsdelete(1);
			taskFieldService.update(field);
		}
		resultMsg = getText("删除填写字段", "删除填写字段");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);

	}

	@RequestMapping("taskDetail")
	@Action(description = "任务详情")
	public ModelAndView taskDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String lastUrl = RequestUtil.getPrePage(request);
		String returnUrl = "";
		ModelAndView mv = new ModelAndView("/makshi/project/taskDetail.jsp");
		ProjectStageTask task = projectStageTaskService.getById(id);
		List<ProcessRun> runs = null;
		Boolean isHjsyb = false;// 判断是否为环境事业部的运维类项目分类 如果是：需要判断权限
		Integer proId = 0;
		List<SysFile> files = null;
		List<ProjectStageTask> queryTasks = null;
		boolean isCharge = false;
		List<ProjectStageTaskField> fieldList = null;
		if (task != null) {
			String chargeID = task.getChargeID();
			Long currentUserId = ContextUtil.getCurrentUserId();
			if (!StringUtil.isEmpty(chargeID) && chargeID.contains(currentUserId + "")) {
				isCharge = true;
			}
			// 表单填写类
			if (task.getTasktype() == 1) {
				// QueryFilter queryFilter = new QueryFilter(request);
				// queryFilter.addFilter("taskId", task.getId());
				// fieldList = taskFieldService.getAll(queryFilter);
				fieldList = taskFieldService.getFieldByTask(task.getId());
			}

			// 资料上传类
			if (task.getTasktype() == 2) {
				files = new ArrayList<SysFile>();
				String uploadfile = task.getUploadfile();
				if (!StringUtil.isEmpty(uploadfile)) {
					JSONArray jsonArray = JSONArray.fromObject(uploadfile);
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String fileId = (String) object.get("id");
						SysFile file = fileService.getById(Long.parseLong(fileId));
						if (file != null) {
							SysUser user = userService.getById(file.getCreatorId());
							if (user != null) {
								file.setCreator(user.getFullname());
							}
						}

						files.add(file);
					}
				}

			}
			ProjectStage stage = projectStageService.getById(task.getProstageid().longValue());
			proId = stage == null ? 0 : stage.getProid();
			if (proId != 0) {
				Project byId = projectService.getById(Long.valueOf(proId + ""));
				if (byId != null) {
					Long classifylibraryid = byId.getClassifylibraryid();
					ClassifyLibrary byId2 = classifyLibraryService.getById(classifylibraryid);
					if (byId2 != null && "运维类项目".equals(byId2.getName()) && byId2.getOrgID() != null && byId2.getOrgID().toString().contains("10000011000078")) {
						isHjsyb = true;
					}
				}
			}
			// 流程审批类
			if (task.getTasktype() == 3) {
				if (!StringUtil.isEmpty(task.getFlowid()) && !StringUtil.isEmpty(task.getFlowrunid())) {
					String flowrunid = task.getFlowrunid();
					String[] runid = flowrunid.split(",");
					runs = new ArrayList<ProcessRun>();
					List<SysRole> byUserId = sysRoleService.getByUserId(currentUserId);
					for (String run : runid) {
						ProcessRun processRun = processRunService.getById(Long.parseLong(run));
						List<ProcessTask> proTasks = null;
						if (BeanUtils.isNotEmpty(processRun)) {
							if (processRun.getActInstId() == null)
								continue;
							proTasks = bpmService.getTasks(processRun.getActInstId());
							processRun.setTaskName("已结束");
							if (proTasks != null && proTasks.size() > 0) {
								processRun.setTaskName(proTasks.get(0).getName());
							}
							processRun.setStatus((short) 0);
							if (isHjsyb) {
								// 判断权限
								if (byUserId != null && byUserId.size() > 0) {
									for (SysRole sysRole : byUserId) {
										if (currentUserId == 1l || ("运维类管理员-环境事业部").equals(sysRole.getRoleName())) {
											processRun.setStatus((short) 1);
											break;
										}
									}
								}
								List<TaskOpinion> optionByRunId = getOptionByRunId(Long.valueOf(run));
								for (TaskOpinion taskOpinion : optionByRunId) {
									Long exeUserId = taskOpinion.getExeUserId();
									if ((currentUserId + "").equals(exeUserId + "")) {
										processRun.setStatus((short) 1);
										break;
									}
									List<SysUser> candidateUsers = taskOpinion.getCandidateUsers();
									for (SysUser sysUser : candidateUsers) {
										if ((sysUser.getUserId() + "").equals(currentUserId + "")) {
											processRun.setStatus((short) 1);
											break;
										}
									}

								}
							}
							runs.add(processRun);
						}
					}
				}

			}
			if (!StringUtil.isEmpty(task.getQuerytaskid())) {
				String querytaskid = task.getQuerytaskid();
				String[] querys = querytaskid.split(",");
				queryTasks = new ArrayList<ProjectStageTask>();
				for (String queryId : querys) {
					ProjectStageTask queryTask = projectStageTaskService.getProjectStageTaskByStageIdAndCstId(task.getProstageid(), queryId);
					queryTasks.add(queryTask);
				}
			}

			int type = RequestUtil.getInt(request, "type", 0);
			if (type == 1) {// 我的任务跳转而来
				returnUrl = "/makshi/project/project/myTaskList.ht";
			} else if (type == 2) {
				returnUrl = "/makshi/portal/orgportal/index.ht?orgId=" + RequestUtil.getLong(request, "portalOrgId");
			} else if (type == 3) {
				returnUrl = "/makshi/river/hdProgrammeApproval/list.ht";
			} else if (type == 4) {
				returnUrl = "/makshi/portal/orgportal/moreProjectProgress.ht?orgId=" + RequestUtil.getLong(request, "portalOrgId");
			} else if (StringUtils.isNotEmpty(lastUrl) && lastUrl.indexOf("projectDetail.ht") != -1) {
				returnUrl = lastUrl;
			} else {
				returnUrl = "/makshi/project/project/stageDetail.ht?id=" + task.getProstageid();
			}
		}

		return mv.addObject("task", task).addObject("queryTasks", queryTasks).addObject("runs", runs).addObject("files", files).addObject("proId", proId).addObject("returnUrl", returnUrl)
				.addObject("isCharge", isCharge).addObject("fieldList", fieldList).addObject("isHjsyb", isHjsyb);

	}

	private List<TaskOpinion> getOptionByRunId(Long runId) {
		// 取得关联的流程实例ID
		List<TaskOpinion> list = taskOpinionService.getByRunId(runId);
		if (null != list && list.size() > 0) {
			for (TaskOpinion taskOpinion : list) {
				if (taskOpinion.getOpinion() != null && !taskOpinion.getOpinion().equals("") && taskOpinion.getOpinion().indexOf("<a") >= 0) {
					String opinion = taskOpinion.getOpinion().replaceAll("</a>", "");
					String hrefStr = opinion.substring(opinion.indexOf("<a"), opinion.indexOf(">") + 1);
					opinion = opinion.replace(hrefStr, "");
					taskOpinion.setOpinion(opinion);
				}
			}
		}
		// 设置代码执行人
		list = taskOpinionService.setTaskOpinionExecutor(list);

		return list;

	}

	@RequestMapping("fieldDetail")
	@Action(description = "任务详情")
	public ModelAndView fieldDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer id = RequestUtil.getInt(request, "id");
		String date = RequestUtil.getString(request, "ctime");
		String returnUrl = RequestUtil.getPrePage(request);
		List<ProjectStageTaskField> list = taskFieldService.getFieldByTaskAndTime(id, date);
		ModelAndView mv = new ModelAndView("/makshi/project/fieldDetail.jsp");
		return mv.addObject("list", list).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("endTask")
	@Action(description = "完成任务")
	public void endTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		// String returnUrl = RequestUtil.getPrePage(request);
		ProjectStageTask task = projectStageTaskService.getById(id.longValue());
		if (task != null) {
			String checkDo = checkDo(task);
			if (StringUtil.isEmpty(checkDo)) {
				boolean flag = true;
				if (task.getTasktype() == 3) {// 表单填写类,有未完成的流程不能结束
					if (!StringUtil.isEmpty(task.getFlowid()) && !StringUtil.isEmpty(task.getFlowrunid())) {
						String flowrunid = task.getFlowrunid();
						String[] runid = flowrunid.split(",");
						for (String run : runid) {
							ProcessRun processRun = processRunService.getById(Long.parseLong(run));
							List<ProcessTask> proTasks = null;
							if (BeanUtils.isNotEmpty(processRun)) {
								if (processRun.getActInstId() == null)
									continue;
								proTasks = bpmService.getTasks(processRun.getActInstId());
								if (proTasks != null && proTasks.size() > 0) {// 流程未完成
									flag = false;
									break;
								}
							}
						}
					}
				}
				if (flag) {
					task.setStatus(1);
					projectStageTaskService.update(task);
					resultMsg = getText("完成任务", "完成任务");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				} else {
					resultMsg = getText("有未完成的流程", "有未完成的流程");
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				}

			} else {
				resultMsg = getText(checkDo, checkDo);
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
			}

		}

	}

	@RequestMapping("restartTask")
	@Action(description = "完成任务")
	public void restartTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		// String returnUrl = RequestUtil.getPrePage(request);
		ProjectStageTask task = projectStageTaskService.getById(id.longValue());
		if (task != null) {
			task.setStatus(0);
			projectStageTaskService.update(task);
			resultMsg = getText("重启任务", "重启任务");
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		}

	}

	@RequestMapping("endStage")
	@Action(description = "关闭阶段")
	public void endStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		Long id = RequestUtil.getLong(request, "id");
		ProjectStage stage = projectStageService.getById(id);
		if (stage != null && stage.getStatus() == 0) {
			List<ProjectStageTask> tasks = projectStageTaskService.getProjectStageTaskByStageId(stage.getId());
			if (tasks != null && tasks.size() > 0) {
				for (ProjectStageTask task : tasks) {
					if (task.getStatus() == 0) {
						task.setStatus(1);
						projectStageTaskService.update(task);
					}
				}
			}
			stage.setStatus(1);
			projectStageService.update(stage);
		}
		resultMsg = getText("关闭阶段", "关闭阶段");
		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
	}

	@RequestMapping("editStageTask")
	@Action(description = "阶段任务添加和修改")
	public ModelAndView editStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long stageid = RequestUtil.getLong(request, "stageid");
		ProjectStage projectStage = projectStageService.getById(stageid);
		ModelAndView mv = getAutoView();
		if (projectStage != null) {
			mv.addObject("stagename", projectStage.getStagename()).addObject("stageid", projectStage.getId());

			Integer proid = projectStage.getProid();
			Project project = projectService.getById(Long.valueOf(proid + ""));
			if (project != null) {
				mv.addObject("projectname", project.getName()).addObject("projectid", project.getId());
			}
		}
		Long id = RequestUtil.getLong(request, "id");
		ProjectStageTask projectStageTask = projectStageTaskService.getById(id);

		mv.addObject("projectStageTask", projectStageTask);
		return mv;
	}

	@RequestMapping("stageTaskSave")
	@Action(description = "保存任务库")
	public void stageTaskSave(HttpServletRequest request, HttpServletResponse response, ProjectStageTask projectStageTask) throws Exception {
		String resultMsg = null;
		try {
			Long stageid = RequestUtil.getLong(request, "prostageid");
			Long projectid = RequestUtil.getLong(request, "projectid");
			Project project = projectService.getById(projectid);
			if (project == null || project.getIsdelete() == 1) {
				resultMsg = getText("操作失败");
				throw new RuntimeException("项目不存在或者已经被删除！");
			}
			if (stageid == 0) {
				resultMsg = getText("参数错误");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				return;
			}
			if (projectStageTask.getIsexamine() == null) {
				projectStageTask.setIsexamine(false);
			}
			if (projectStageTask.getIsmore() == null) {
				projectStageTask.setIsmore(false);
			}
			if (projectStageTask.getId() == null) {
				// 插入
				projectStageTask.setOrder(0);
				projectStageTask.setStatus(0);
				projectStageTask.setIsdelete(0);
				projectStageTask.setPretaskid("");
				projectStageTask.setAftertaskid("");
				projectStageTask.setQuerytaskid("");
				// 取当前项目阶段下任务编号最大的任务
				int max = projectStageTaskService.getMaxtasknoByProjectstageid(stageid.intValue());
				projectStageTask.setTaskno(max + 1);
				long maxCstId = projectStageTaskService.getMaxCstIdByProjectstageid(stageid.intValue());
				projectStageTask.setCstid((maxCstId + 1) + "");
				projectStageTaskService.add(projectStageTask);
				resultMsg = getText("添加", "项目任务库信息");
			} else {
				// 更新
				projectStageTaskService.update(projectStageTask);
				resultMsg = getText("更新", "项目任务库信息");
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("check")
	@Action(description = "检查分类库阶段")
	public void check(HttpServletRequest request, HttpServletResponse response, ProjectStage projectStage) throws Exception {
		String resultMsg = null;
		try {
			if (projectStage.getId() != null) {
				ResultMessage checkStage = projectService.checkStageTask(projectStage.getId());
				resultMsg = checkStage.getMessage();
				if (checkStage.getResult() == 1) {
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
				} else {
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
				}
			} else {
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}

	}

	@RequestMapping("setStageTask")
	@Action(description = "设置分类库阶段任务前驱后驱")
	public ModelAndView setStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		ModelAndView mv = getAutoView();
		ProjectStageTask projectStageTask = projectStageTaskService.getById(id);
		ProjectStage projectStage = null;
		Project project = null;
		List<ProjectStageTask> prestagetaskList = new ArrayList<>();
		List<ProjectStageTask> afterstagetaskList = new ArrayList<>();
		List<ProjectStageTask> querystagetaskList = new ArrayList<>();
		if (projectStageTask != null) {
			Integer projectstageid = projectStageTask.getProstageid();
			projectStage = projectStageService.getById(Long.valueOf(projectstageid + ""));
			if (projectStage != null) {
				Integer proid = projectStage.getProid();
				project = projectService.getById(Long.valueOf(proid + ""));
			}
			String prestagetask = projectStageTask.getPretaskid();
			String afterstagetask = projectStageTask.getAftertaskid();
			String querystagetask = projectStageTask.getQuerytaskid();
			if (prestagetask != null && prestagetask.trim().length() > 0) {
				String[] split = prestagetask.trim().split(",");
				for (String string : split) {
					ProjectStageTask byId = projectStageTaskService.getStageTaskByStageIdAndCstId(projectstageid, string);
					if (byId != null) {
						prestagetaskList.add(byId);
					}
				}
			}
			if (afterstagetask != null && afterstagetask.trim().length() > 0) {
				String[] split = afterstagetask.trim().split(",");
				for (String string : split) {
					ProjectStageTask byId = projectStageTaskService.getStageTaskByStageIdAndCstId(projectstageid, string);
					if (byId != null) {
						afterstagetaskList.add(byId);
					}
				}
			}
			if (querystagetask != null && querystagetask.trim().length() > 0) {
				String[] split = querystagetask.trim().split(",");
				for (String string : split) {
					ProjectStageTask byId = projectStageTaskService.getStageTaskByStageIdAndCstId(projectstageid, string);
					if (byId != null) {
						querystagetaskList.add(byId);
					}
				}
			}

		}
		return mv.addObject("project", project).addObject("projectStage", projectStage).addObject("projectStageTask", projectStageTask).addObject("prestagetaskList", prestagetaskList)
				.addObject("afterstagetaskList", afterstagetaskList).addObject("querystagetaskList", querystagetaskList);
	}

	@RequestMapping("viewStageTask")
	@Action(description = "项目阶段任务查看")
	public ModelAndView viewStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "taskId");
		ProjectStageTask projectStageTask = projectStageTaskService.getById(id);
		ModelAndView mv = getAutoView();
		mv.addObject("projectStageTask", projectStageTask);
		return mv;
	}

	@RequestMapping("stageTaskSelector")
	@Action(description = "分类库阶段任务前置后置节点选择器")
	public ModelAndView stageTaskSelector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = getAutoView();
		Long projectstagetaskid = RequestUtil.getLong(request, "projectstagetaskid");
		Long projectstageid = RequestUtil.getLong(request, "projectstageid");
		int selectType = RequestUtil.getInt(request, "selectType");
		ProjectStageTask projectStageTask = projectStageTaskService.getById(projectstagetaskid);
		List<ProjectStageTask> projectStageTaskList = null;
		if (projectStageTask != null) {
			projectStageTaskList = projectStageTaskService.getStageTaskByStageId(projectstageid.intValue());
			List<String> filterArr = new ArrayList<>();
			filterArr.add(projectStageTask.getId() + "");

			Iterator<ProjectStageTask> iterator = projectStageTaskList.iterator();
			while (iterator.hasNext()) {
				ProjectStageTask next = iterator.next();
				if (filterArr.contains(next.getId() + "")) {
					iterator.remove();
				}
			}
		}

		mv.addObject("projectStageTaskList", projectStageTaskList).addObject("projectstageid", projectstageid).addObject("projectstagetaskid", projectstagetaskid).addObject("selectType", selectType);
		return mv;
	}

	@RequestMapping("projectStageTaskSave")
	@Action(description = "保存项目阶段任务")
	public void projectStageTaskSave(HttpServletRequest request, HttpServletResponse response, ProjectStageTask projectStageTask) throws Exception {
		String resultMsg = null;
		try {
			if (projectStageTask.getId() != null) {
				Long projectid = RequestUtil.getLong(request, "projectid");
				Project project = projectService.getById(projectid);
				if (project == null || project.getIsdelete() == 1) {
					resultMsg = getText("操作失败");
					throw new RuntimeException("项目不存在或已被删除！");
				}
				Long[] pre = RequestUtil.getLongAry(request, "prestagetaskid");
				Long[] after = RequestUtil.getLongAry(request, "afterstagetaskid");
				Long[] query = RequestUtil.getLongAry(request, "querystagetaskid");
				if (pre == null || pre.length == 0) {
					projectStageTask.setPretaskid("");
				} else {
					projectStageTask.setPretaskid(StringUtils.join(pre, ","));
				}
				if (after == null || after.length == 0) {
					projectStageTask.setAftertaskid("");
				} else {
					projectStageTask.setAftertaskid(StringUtils.join(after, ","));
				}
				if (query == null || query.length == 0) {
					projectStageTask.setQuerytaskid("");
				} else {
					projectStageTask.setQuerytaskid(StringUtils.join(query, ","));
				}
				projectStageTaskService.update(projectStageTask, true);

				resultMsg = getText("保存", "阶段任务信息");
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
			} else {
				throw new RuntimeException("参数错误！");
			}
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}

	@RequestMapping("delStageTask")
	@Action(description = "删除分类库阶段任务")
	public void delStageTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		Long projectid = RequestUtil.getLong(request, "projectid");
		ResultMessage message = null;
		try {
			Project project = projectService.getById(projectid);
			if (project == null || project.getIsdelete() == 1) {
				throw new RuntimeException("项目不存在或已被删除！");
			}
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			projectStageTaskService.delByIds(lAryId);

			message = new ResultMessage(ResultMessage.Success, "删除任务成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除任务失败," + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 取得项目变更历史
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getChangeHisView")
	@Action(description = "取得项目变更历史")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		// 获取项目基本信息
		Project project = projectService.getById(id);
		List<WChangeHistory> changeHisList = null;
		if (project != null) {
			changeHisList = changeHistoryService.getListByType("project", id + "");
		}

		String returnUrl = RequestUtil.getPrePage(request);
		ModelAndView mv = new ModelAndView("/makshi/project/proChangeHis.jsp");
		return mv.addObject("project", project).addObject("returnUrl", returnUrl).addObject("changeHisList", changeHisList);
	}

	@RequestMapping("getProjectinfo")
	@Action(description = "取得项目信息")
	public @ResponseBody Map<String, Object> getProjectinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		long taskId = RequestUtil.getLong(request, "taskId");
		Map<String, Object> result = new HashMap<>();
		// 获取项目基本信息
		ProjectStageTask byId = projectStageTaskService.getById(taskId);
		if (byId != null) {
			result.put("projectTaskId", taskId);
			String taskname = byId.getTaskname();
			result.put("taskName", taskname);
			Integer prostageid = byId.getProstageid();
			ProjectStage stage = projectStageService.getById(Long.valueOf(prostageid + ""));
			if (stage != null) {
				String stagename = stage.getStagename();
				result.put("stageName", stagename);
				Integer proid = stage.getProid();
				Project project = projectService.getById(Long.valueOf(proid + ""));
				if (project != null) {
					String name = project.getName();
					result.put("projectName", name);
					String applicant = project.getApplicant();
					String applicantID = project.getApplicantID();
					result.put("charger", applicant);
					result.put("chargerID", applicantID);
					String contractnum = project.getContractnum();
					if (StringUtils.isEmpty(contractnum)) {
						result.put("projectContractNo", "");
					} else {
						result.put("projectContractNo", contractnum);
					}
					Long classifylibraryid = project.getClassifylibraryid();
					ClassifyLibrary classifyLibrary = classifyLibraryService.getById(classifylibraryid);
					if (classifyLibrary != null) {
						result.put("projectClassifyName", classifyLibrary.getName());
					}
				}
			}
		}
		return result;
	}

	@RequestMapping("projectIndex")
	@Action(description = "项目首页")
	public ModelAndView projectIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int status = RequestUtil.getInt(request, "status", -1);
		Long chooseOrgId = (Long) request.getSession().getAttribute("chooseOrgId");// 部门id
		QueryFilter queryFilter = new QueryFilter(request);
		queryFilter.setPageBean(null);
		queryFilter.addFilter("isUsed", 1);
		if (chooseOrgId != -1l) {
			queryFilter.addFilter("orgID", chooseOrgId);
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		List<ClassifyLibrary> classifyList = classifyLibraryService.getAll(queryFilter);
		for (ClassifyLibrary classifyLibrary : classifyList) {
			long pronum = 0l;
			if (status == -1) {
				pronum = projectService.getCountByClassifyLibId(classifyLibrary.getId());
			} else {
				pronum = projectService.getCountByClassifyLibIdAndStatus(classifyLibrary.getId(), status);
			}

			classifyLibrary.setPronum(pronum);
			sb.append("'" + classifyLibrary.getName() + "',");
			sb2.append("{value:" + pronum + ",name:'" + classifyLibrary.getName() + "',id:" + classifyLibrary.getId() + "},");

		}
		String claData = "";
		String serDate = "";
		if (!StringUtil.isEmpty(sb.toString())) {
			claData = (sb.toString()).substring(0, sb.toString().length() - 1);
		}
		if (!StringUtil.isEmpty(sb2.toString())) {
			serDate = (sb2.toString()).substring(0, sb2.toString().length() - 1);
		}

		StringBuffer sbDate = new StringBuffer();
		StringBuffer sbSerDate = new StringBuffer();
		for (int i = 6; i >= 0; i--) {
			String pastDate = DateUtils.getPastDate(i);
			sbDate.append("'" + pastDate + "',");
			List<ProjectTaskLogs> logs = null;
			if (status == -1) {
				logs = taskLogsService.getByCtimeAndOrg(pastDate, chooseOrgId);
			} else {
				logs = taskLogsService.getByCtimeAndOrgAndStatus(pastDate, chooseOrgId, status);
			}
			sbSerDate.append(logs == null ? 0 : logs.size() + ",");
		}
		String xAxisData = "";
		String countStr = "";
		if (!StringUtil.isEmpty(sbDate.toString())) {
			xAxisData = (sbDate.toString()).substring(0, sbDate.toString().length() - 1);
		}
		if (!StringUtil.isEmpty(sbSerDate.toString())) {
			countStr = (sbSerDate.toString()).substring(0, sbSerDate.toString().length() - 1);
		}
		JSONArray classifyArray = JSONArray.fromObject(classifyList);

		ModelAndView mv = new ModelAndView("/makshi/project/projectIndex.jsp");
		return mv.addObject("classifyArray", classifyArray).addObject("claData", claData)// 饼状图legend数据
				.addObject("serDate", serDate)// 饼状图series数据
				.addObject("xAxisData", xAxisData)// 折线图xAxis数据
				.addObject("countStr", countStr)// 折线图series数据
				.addObject("status", status);
	}

	@RequestMapping("getTaskDoCount")
	@ResponseBody
	public String getTaskDoCount(HttpServletRequest request, HttpServletResponse response) {
		int status = RequestUtil.getInt(request, "status", -1);
		long classifyId = RequestUtil.getLong(request, "classifyId");
		StringBuffer sbSerDate = new StringBuffer();
		for (int i = 6; i >= 0; i--) {
			String pastDate = DateUtils.getPastDate(i);
			List<ProjectTaskLogs> logs = taskLogsService.getByCtimeAndClassifyId(pastDate, classifyId, status);
			sbSerDate.append(logs == null ? 0 : logs.size() + ",");

		}
		String countStr = "";
		if (!StringUtil.isEmpty(sbSerDate.toString())) {
			countStr = (sbSerDate.toString()).substring(0, sbSerDate.toString().length() - 1);
		}

		return countStr;
	}

}
