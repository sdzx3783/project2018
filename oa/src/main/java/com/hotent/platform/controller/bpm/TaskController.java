package com.hotent.platform.controller.bpm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiInclusiveGateWayException;
import org.activiti.engine.ActivitiVarNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.bpm.model.IProcessRun;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.bpm.model.NodeCache;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.bpm.model.ProcessTaskHistory;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.TableModel;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.contract.ContractBillingApplication;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.service.bpmremark.TableremarkService;
import com.hotent.makshi.service.common.BpmCommonService;
import com.hotent.makshi.service.contract.ContractBillingApplicationService;
import com.hotent.makshi.service.project.ProjectStageTaskService;
import com.hotent.makshi.service.seal.SealUseApplicationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.bpm.AuthorizeRight;
import com.hotent.platform.model.bpm.BpmBatchApproval;
import com.hotent.platform.model.bpm.BpmDefAuthorizeType.BPMDEFAUTHORIZE_RIGHT_TYPE;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmGangedSet;
import com.hotent.platform.model.bpm.BpmNodeButton;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.BpmNodeSign;
import com.hotent.platform.model.bpm.BpmRunLog;
import com.hotent.platform.model.bpm.BpmTree;
import com.hotent.platform.model.bpm.CommuReceiver;
import com.hotent.platform.model.bpm.ExecutionStack;
import com.hotent.platform.model.bpm.FormModel;
import com.hotent.platform.model.bpm.NodeTranUser;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskFork;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.bpm.TaskSignData;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.model.system.SysProperty;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmBatchApprovalService;
import com.hotent.platform.service.bpm.BpmDefAuthorizeService;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmFormRunService;
import com.hotent.platform.service.bpm.BpmGangedSetService;
import com.hotent.platform.service.bpm.BpmNodeButtonService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeSignService;
import com.hotent.platform.service.bpm.BpmNodeSignService.BpmNodePrivilegeType;
import com.hotent.platform.service.bpm.BpmNodeUserService;
import com.hotent.platform.service.bpm.BpmProTransToService;
import com.hotent.platform.service.bpm.BpmRunLogService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.BpmTaskExeService;
import com.hotent.platform.service.bpm.CommuReceiverService;
import com.hotent.platform.service.bpm.ExecutionStackService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskApprovalItemsService;
import com.hotent.platform.service.bpm.TaskHistoryService;
import com.hotent.platform.service.bpm.TaskOpinionService;
import com.hotent.platform.service.bpm.TaskReminderService;
import com.hotent.platform.service.bpm.TaskSignDataService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.bpm.impl.BpmActService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormHandlerService;
import com.hotent.platform.service.form.BpmFormRightsService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.form.TaskReadService;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.SysErrorLogService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysPropertyService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.util.ServiceUtil;
import com.hotent.platform.service.worktime.CalendarAssignService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 后台任务管理控制类
 * 
 * @author csx
 * 
 */
@Controller
@RequestMapping(value = { "/platform/bpm/task/", "/weixin/bpm/task/" })
@Action(ownermodel = SysAuditModelType.PROCESS_MANAGEMENT)
public class TaskController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(TaskController.class);
	@Resource
	private BpmService bpmService;
	@Resource
	private TaskService taskService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmDefinitionService bpmDefinitionService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private BpmBatchApprovalService bpmBatchApprovalService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private BpmNodeSignService bpmNodeSignService;
	@Resource
	private ExecutionStackService executionStackService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private BpmFormHandlerService bpmFormHandlerService;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private BpmNodeUserService bpmNodeUserService;
	@Resource
	private TaskUserService taskUserService;
	@Resource
	private BpmFormRunService bpmFormRunService;
	@Resource
	private TaskApprovalItemsService taskAppItemService;
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private BpmNodeButtonService bpmNodeButtonService;
	@Resource
	private BpmRunLogService bpmRunLogService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskReadService taskReadService;
	@Resource
	private CommuReceiverService commuReceiverService;
	@Resource
	private BpmGangedSetService bpmGangedSetService;
	@Resource
	private BpmTaskExeService bpmTaskExeService;
	@Resource
	private TaskOpinionService taskOpinionService;
	@Resource
	private SysFileService sysFileService;
	@Resource
	private SysErrorLogService sysErrorLogService;
	@Resource
	private TaskHistoryService taskHistoryService;
	@Resource
	private BpmProTransToService bpmProTransToService;
	@Resource
	private CalendarAssignService calendarAssignService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private BpmDefAuthorizeService bpmDefAuthorizeService;
	@Resource
	private BpmActService bpmActService;
	@Resource
	private SysPropertyService sysPropertyService;
	@Resource
	private TaskReminderService reminderService;
	@Resource
	private BpmFormRightsService bpmFormRightsService;
	@Resource
	private ProjectStageTaskService projectStageTaskService;
	@Resource
	private SealUseApplicationService sealUseApplicationService;
	@Resource
	private TableremarkService tableremarkService;
	@Resource
	private BpmCommonService bpmCommonService;
	@Resource
	private ContractBillingApplicationService contractBillingApplicationService;

	static String NODEPATH = "3.10000000143766.40000000000134.";

	/**
	 * 已办办结事宜列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("alreadyCompletedMattersList")
	@Action(description = "已办办结事宜列表")
	public ModelAndView alreadyCompletedMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		boolean billingPath = nodePath.equals(NODEPATH);
		List<ProcessRun> list = processRunService.getAlreadyCompletedMattersList(filter);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		for (ProcessRun processRun : list) {
			if (null != processRun) {
				String subjectStr = processRun.getSubject();
				if (subjectStr.indexOf("-") > -1) {
					processRun.setSubject(subjectStr.substring(0, subjectStr.indexOf("-")));
				}
				// 获取表名，businesskey
				String businesskey = processRun.getBusinessKey();
				// 合同开票
				if (billingPath) {
					Map<String, Object> billingMap = new HashMap<String, Object>();
					billingMap.put("processTask", processRun);
					ContractBillingApplication contractBillingApplication = contractBillingApplicationService.getById(Long.parseLong(businesskey));
					billingMap.put("contractBillingApplication", contractBillingApplication);
					listMap.add(billingMap);
					continue;
				}
				processRun.setProcessName(processRunService.getProcessNameByTableName(processRun.getProcessName(), processRun.getTableName(), businesskey));
			}
		}
		if (billingPath) {
			return new ModelAndView("/platform/bpm/processRunAlreadyCompletedMattersListOfBilling.jsp").addObject("processRunList", listMap).addObject("hasGlobalFlowNo", hasGlobalFlowNo)
					.addObject("nodePath", nodePath);
		}
		return new ModelAndView("/platform/bpm/processRunAlreadyCompletedMattersList.jsp").addObject("processRunList", list).addObject("hasGlobalFlowNo", hasGlobalFlowNo);
	}

	/**
	 * 已办办结事宜列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("alreadyCompletedMattersExport")
	@Action(description = "已办办结事宜列表")
	public void alreadyCompletedMattersExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, false);
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		boolean billingPath = nodePath.equals(NODEPATH);
		List<ProcessRun> list = processRunService.getAlreadyCompletedMattersList(filter);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		for (ProcessRun processRun : list) {
			if (null != processRun) {
				String subjectStr = processRun.getSubject();
				if (subjectStr.indexOf("-") > -1) {
					processRun.setSubject(subjectStr.substring(0, subjectStr.indexOf("-")));
				}
				// 获取表名，businesskey
				String businesskey = processRun.getBusinessKey();
				// 合同开票
				if (billingPath) {
					Map<String, Object> billingMap = new HashMap<String, Object>();
					billingMap.put("processTask", processRun);
					billingMap.put("contractBillingApplication", contractBillingApplicationService.getById(Long.parseLong(businesskey)));
					listMap.add(billingMap);
					continue;
				}
				processRun.setProcessName(processRunService.getProcessNameByTableName(processRun.getProcessName(), processRun.getTableName(), businesskey));
			}
		}
		if (billingPath) {
			processRunService.exportBillingPath(request, response, nodePath, listMap, hasGlobalFlowNo);
		} else
			processRunService.exportMatters(request, response, nodePath, list, hasGlobalFlowNo);
	}

	/**
	 * 取得起始表单。
	 * 
	 * @param bpmNodeSet
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private FormModel getStartForm(BpmNodeSet bpmNodeSet, String businessKey, String actDefId, String ctxPath, boolean isReCalc, boolean isCopy) throws Exception {
		FormModel formModel = new FormModel();
		if (bpmNodeSet == null || bpmNodeSet.getFormType() == -1)
			return formModel;
		if (bpmNodeSet.getFormType() == BpmConst.OnLineForm) {
			String formKey = bpmNodeSet.getFormKey();
			if (StringUtil.isNotEmpty(formKey)) {
				BpmFormDef bpmFormDef = bpmFormDefService.getDefaultPublishedByFormKey(formKey);
				if (bpmFormDef != null) {
					// String formHtml = bpmFormHandlerService.obtainHtml(bpmFormDef, businessKey, "", actDefId, bpmNodeSet.getNodeId(), ctxPath, "", isReCalc, false, false);

					// --->测试新解释器
					String formHtml = bpmFormDefService.parseHtml(bpmFormDef, businessKey, "", actDefId, bpmNodeSet.getNodeId(), "", isReCalc, false, false, isCopy);
					// <---测试新解释器

					formModel.setFormHtml(formHtml);
				}
			}
		} else {
			String formUrl = bpmNodeSet.getFormUrl();
			// 替换主键。
			formUrl = formUrl.replaceFirst(BpmConst.FORM_PK_REGEX, businessKey);
			if (!formUrl.startsWith("http")) {
				formUrl = ctxPath + formUrl;
			}
			formModel.setFormType(BpmConst.UrlForm);
			formModel.setFormUrl(formUrl);
		}
		return formModel;
	}

	private Map getPageParam(HttpServletRequest request) {
		Map paraMap = RequestUtil.getParameterValueMap(request, false, false);
		paraMap.remove("businessKey");
		paraMap.remove("defId");
		return paraMap;
	}

	private ModelAndView getNotValidView(BpmDefinition bpmDefinition, String businessKey) {
		if (BeanUtils.isEmpty(bpmDefinition))
			return ServiceUtil.getTipInfo("该流程定义已经被删除!");
		if (bpmDefinition.getStatus().equals(BpmDefinition.STATUS_DISABLED) || bpmDefinition.getStatus().equals(BpmDefinition.STATUS_INST_DISABLED))
			return ServiceUtil.getTipInfo("该流程定义已经被禁用!");
		// 判断该业务主键是否已绑定流程实例
		boolean isProcessInstanceExisted = processRunService.isProcessInstanceExisted(businessKey);
		if (isProcessInstanceExisted) {
			return ServiceUtil.getTipInfo("对不起，该流程实例已存在，不需要再次启动!");
		}
		return null;
	}

	/**
	 * 单个流程访问地址权限控制
	 * 
	 * @return
	 */
	@RequestMapping("toStartFlow/{flowKey}")
	public ModelAndView toStartFlow(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "flowKey") String flowKey) throws Exception {
		BpmDefinition def = bpmDefinitionService.getMainByDefKey(flowKey);
		if (def == null)
			return ServiceUtil.getTipInfo("该流程定义已经被删除!");

		return startFlowForm(request, response, def.getDefId());
	}

	/**
	 * 跳转到启动流程页面。<br/>
	 * 
	 * <pre>
	 * 传入参数流程定义id：defId。 
	 * 实现方法： 
	 * 1.根据流程对应ID查询流程定义。 
	 * 2.获取流程定义的XML。
	 * 3.获取流程定义的第一个任务节点。
	 * 4.获取任务节点的流程表单定义。 
	 * 5.显示启动流程表单页面。
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("startFlowForm")
	@Action(description = "跳至启动流程页面")
	public ModelAndView startFlowForm(HttpServletRequest request, HttpServletResponse response, Long defId) throws Exception {
		String businessKey = RequestUtil.getString(request, "businessKey");
		// 复制表单 启动流程
		String copyKey = RequestUtil.getString(request, "copyKey", "");
		ISysUser sysUser = ContextUtil.getCurrentUser();
		String ctxPath = request.getContextPath();
		ModelAndView mv = new ModelAndView("/platform/bpm/taskStartFlowForm.jsp");

		// 流程草稿传入
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		// 从已经完成的流程实例启动流程。
		Long relRunId = RequestUtil.getLong(request, "relRunId", 0L);

		// 构建参数到到JSP页面。
		Map paraMap = getPageParam(request);

		ProcessRun processRun = null;
		BpmDefinition bpmDefinition = null;
		if (StringUtils.isNotEmpty(businessKey) && runId == 0) {
			processRun = processRunService.getByBusinessKey(businessKey);
			if (BeanUtils.isEmpty(processRun)) {// 业务数据模板新增表单后，在列表启动流程，没有流程实例
				defId = RequestUtil.getLong(request, "defId");
			} else {
				defId = processRun.getDefId();
				runId = processRun.getRunId();
			}
		}

		if (runId != 0) {
			processRun = processRunService.getById(runId);
			defId = processRun.getDefId();
		}

		if (defId != null && defId != 0L)
			bpmDefinition = bpmDefinitionService.getById(defId);

		ModelAndView tmpView = getNotValidView(bpmDefinition, businessKey);
		if (tmpView != null)
			return tmpView;

		// 根据已经完成的流程实例取得业务主键。
		String pk = processRunService.getBusinessKeyByRelRunId(relRunId);
		if (StringUtil.isNotEmpty(pk)) {
			businessKey = pk;
		}
		Boolean isFormEmpty = false;
		Boolean isExtForm = false;
		String form = "";
		String actDefId = "";
		// 通过草稿启动流程
		if (BeanUtils.isNotEmpty(processRun) && processRun.getStatus().equals(ProcessRun.STATUS_FORM)) {
			mv.addObject("isDraft", false);
			businessKey = processRun.getBusinessKey();
			Long formDefId = processRun.getFormDefId();
			actDefId = processRun.getActDefId();
			// 是否使用新版本，草稿启动后会记录表单ID,如果
			int isNewVersion = RequestUtil.getInt(request, "isNewVersion", 0);
			if (formDefId != 0L) {
				String tableName = processRun.getTableName();
				if (!tableName.startsWith(TableModel.CUSTOMER_TABLE_PREFIX)) {
					tableName = TableModel.CUSTOMER_TABLE_PREFIX + tableName;
				}
				boolean isExistsData = bpmFormHandlerService.isExistsData(processRun.getDsAlias(), tableName, processRun.getPkName(), processRun.getBusinessKey());
				if (!isExistsData)
					return new ModelAndView("redirect:noData.ht");
			}

			if (StringUtil.isNotEmpty(processRun.getBusinessUrl())) {
				isExtForm = true;
				form = processRun.getBusinessUrl();
				// 替换主键。
				form = processRun.getBusinessUrl().replaceFirst(BpmConst.FORM_PK_REGEX, businessKey);
				if (!form.startsWith("http")) {
					form = ctxPath + form;
				}
			} else {
				if (isNewVersion == 1) {
					BpmFormDef defaultFormDef = bpmFormDefService.getById(formDefId);
					formDefId = bpmFormDefService.getDefaultPublishedByFormKey(defaultFormDef.getFormKey()).getFormDefId();
				}
				String nodeId = "";// 流程第一个节点
				FlowNode flowNode = NodeCache.getFirstNodeId(actDefId);
				if (flowNode != null) {
					nodeId = flowNode.getNodeId();
				}
				BpmFormDef bpmFormDef = bpmFormDefService.getById(formDefId);
				form = bpmFormHandlerService.obtainHtml(bpmFormDef, businessKey, "", actDefId, nodeId, ctxPath, "", true, false, false);
				// 下面调用新的解释器
				form = bpmFormDefService.parseHtml(bpmFormDef, businessKey, "", actDefId, nodeId, "", true, false, false, false);
			}
			// 流程定义里面的启动
		} else {
			boolean isReCalcuate = false;
			if (StringUtil.isNotEmpty(copyKey)) {
				businessKey = copyKey;
				isReCalcuate = true;
			}
			mv.addObject("isDraft", true);
			actDefId = bpmDefinition.getActDefId();

			// 获取表单节点
			BpmNodeSet bpmNodeSet = bpmNodeSetService.getStartBpmNodeSet(actDefId, false);

			FormModel formModel = getStartForm(bpmNodeSet, businessKey, actDefId, ctxPath, isReCalcuate, StringUtil.isNotEmpty(copyKey));
			// 是外部表单
			isFormEmpty = formModel.isFormEmpty();
			isExtForm = formModel.getFormType() > 0;

			if (isExtForm) {
				form = formModel.getFormUrl();
			} else if (formModel.getFormType() == 0) {
				form = formModel.getFormHtml();
			}
			if (BeanUtils.isNotEmpty(bpmNodeSet)) {
				mv.addObject("formKey", bpmNodeSet.getFormKey());
			}
		}
		// 获取按钮
		Map<String, List<BpmNodeButton>> mapButton = bpmNodeButtonService.getMapByStartForm(defId);
		// 帮助文档
		SysFile sysFile = null;
		if (BeanUtils.isNotEmpty(bpmDefinition.getAttachment()))
			sysFile = sysFileService.getById(bpmDefinition.getAttachment());

		// 通过defid和nodeId获取联动设置
		List<BpmGangedSet> bpmGangedSets = bpmGangedSetService.getByDefIdAndNodeId(defId, BpmGangedSet.START_NODEID);
		JSONArray gangedSetJarray = (JSONArray) JSONArray.fromObject(bpmGangedSets);

		if (NodeCache.isMultipleFirstNode(actDefId)) {
			mv.addObject("flowNodeList", NodeCache.getFirstNode(actDefId)).addObject("isMultipleFirstNode", true);
		}

		mv.addObject("bpmDefinition", bpmDefinition).addObject("isExtForm", isExtForm).addObject("isFormEmpty", isFormEmpty).addObject("mapButton", mapButton).addObject("defId", defId)
				.addObject("paraMap", paraMap).addObject("form", form).addObject("runId", runId).addObject("businessKey", StringUtil.isEmpty(copyKey) ? businessKey : "").addObject("sysFile", sysFile)
				.addObject("bpmGangedSets", gangedSetJarray).addObject("curUserId", sysUser.getUserId().toString()).addObject("curUserName", sysUser.getFullname());
		return mv;
	}

	/**
	 * 启动流程。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "startFlow", method = RequestMethod.POST)
	@Action(description = "启动流程")
	public void startFlow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		try {
			ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
			Long userId = ContextUtil.getCurrentUserId();
			// start 开发环境下注释掉下面代码
			if (userId == 1l) {
				out.print(new ResultMessage(ResultMessage.Fail, "超级管理员不能发起流程"));
				return;
			}
			// end
			processCmd.setCurrentUserId(userId.toString());
			if (runId != 0L) {
				ProcessRun processRun = processRunService.getById(runId);
				if (BeanUtils.isEmpty(processRun)) {
					out.print(new ResultMessage(ResultMessage.Fail, "流程草稿不存在或已被清除"));
					return;
				}
				processCmd.setProcessRun((IProcessRun) processRun);
			}
			ProcessRun run = processRunService.startProcess(processCmd);

			// 项目内任务启动流程处理
			Integer taskId = RequestUtil.getInt(request, "projectTaskId", -1);
			if (taskId != -1) {
				ProjectStageTask task = projectStageTaskService.getById(taskId.longValue());
				if (task != null) {
					if (StringUtil.isEmpty(task.getFlowrunid())) {
						task.setFlowrunid(run.getRunId() + "");
					} else {
						task.setFlowrunid(task.getFlowrunid() + "," + run.getRunId());
					}
					task.setEndcount(task.getEndcount() + 1);
					projectStageTaskService.update(task);
				}

			}

			ResultMessage resultMessage = new ResultMessage(ResultMessage.Success, "启动流程成功!", run.getRunId().toString());

			out.print(resultMessage);
		} catch (Exception ex) {
			logger.debug("startFlow:" + ex.getMessage());
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "启动流程失败:\r\n" + str);
				out.print(resultMessage);
			} else {
				String message = ex.getMessage();
				if (StringUtil.isEmpty(message)) {
					Throwable tt = ex.getCause();
					message = tt.getMessage();
				}
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				out.print(resultMessage);
			}
		}
	}

	/**
	 * 保存草稿
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveForm")
	@Action(description = "保存草稿")
	public void saveForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		Long userId = ContextUtil.getCurrentUserId();
		processCmd.setCurrentUserId(userId.toString());

		ResultMessage resultMessage = null;
		try {
			processRunService.saveForm(processCmd);
			resultMessage = new ResultMessage(ResultMessage.Success, "保存草稿成功！");
		} catch (Exception e) {
			String message = ExceptionUtil.getExceptionMessage(e);
			resultMessage = new ResultMessage(ResultMessage.Fail, message);
		}
		out.print(resultMessage);
	}

	/**
	 * 保存表单数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("saveData")
	@Action(description = "保存表单数据", detail = "<#if StringUtils.isNotEmpty(taskId)>" + "保存流程【${SysAuditLinkService.getProcessRunLink(taskId)}】的表单数据" + "<#elseif StringUtils.isNotEmpty(defId)>"
			+ "保存流程定义【${SysAuditLinkService.getBpmDefinitionLink(Long.valueOf(defId))}】至草稿" + "</#if>")
	public void saveData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String userId = ContextUtil.getCurrentUserId().toString();
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		Long runId = RequestUtil.getLong(request, "runId", 0L);
		if (runId != 0L) {
			ProcessRun processRun = processRunService.getById(runId);
			processCmd.setProcessRun((IProcessRun) processRun);
		}
		processCmd.setCurrentUserId(userId);
		ResultMessage resultMessage = null;
		try {
			processRunService.saveData(processCmd);
			// 分项保存数据（公司各类资质借阅流程、合同开票）
			updateRelateds(processCmd);
			resultMessage = new ResultMessage(ResultMessage.Success, "保存表单数据成功！");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "保存表单数据成功！:" + str);
			} else {
				if (ex instanceof NullPointerException) {
					// 由于页面审批完不能自动关闭页面 用户可以审批后再点击保存表单 会出现空指针
					resultMessage = new ResultMessage(ResultMessage.Fail, "该任务已办理！");
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					resultMessage = new ResultMessage(ResultMessage.Fail, message);
				}
			}
		}
		out.print(resultMessage);
	}

	private void updateRelateds(ProcessCmd processCmd) throws Exception {
		bpmCommonService.updateTable(processCmd);

	}

	@RequestMapping("saveOpinion")
	@ResponseBody
	@Action(description = "保存流程沟通或流转意见")
	public String saveOpinion(HttpServletRequest request, HttpServletResponse response, TaskOpinion taskOpinion) throws Exception {
		JSONObject jobject = new JSONObject();
		String informType = RequestUtil.getString(request, "informType");
		boolean isAgree = RequestUtil.getBoolean(request, "isAgree");
		ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);
		taskCmd.setVoteContent(taskOpinion.getOpinion());// 设置意见
		try {
			processRunService.handTransTo(taskOpinion, informType, isAgree, taskCmd);
			jobject.accumulate("result", ResultMessage.Success).accumulate("message", "添加意见成功!");
			return jobject.toString();
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				jobject.accumulate("result", ResultMessage.Fail).accumulate("message", "添加意见失败:" + str);
				return jobject.toString();
			} else {
				logger.error("错误信息",ex);
				String message = ExceptionUtil.getExceptionMessage(ex);
				jobject.accumulate("result", ResultMessage.Fail).accumulate("message", message);
				return jobject.toString();
			}
		}
	}

	/**
	 * 显示任务回退的执行路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("back")
	public ModelAndView back(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isEmpty(taskId))
			return getAutoView();

		TaskEntity taskEntity = bpmService.getTask(taskId);
		String taskToken = (String) taskService.getVariableLocal(taskEntity.getId(), TaskFork.TAKEN_VAR_NAME);
		ExecutionStack executionStack = executionStackService.getLastestStack(taskEntity.getProcessInstanceId(), taskEntity.getTaskDefinitionKey(), taskToken);
		if (executionStack != null && executionStack.getParentId() != null && executionStack.getParentId() != 0) {
			ExecutionStack parentStack = executionStackService.getById(executionStack.getParentId());
			String assigneeNames = "";
			if (StringUtils.isNotEmpty(parentStack.getAssignees())) {
				String[] uIds = parentStack.getAssignees().split("[,]");
				int i = 0;
				for (String uId : uIds) {
					SysUser sysUser = sysUserService.getById(new Long(uId));
					if (sysUser == null)
						continue;
					if (i++ > 0) {
						assigneeNames += ",";
					}
					assigneeNames += sysUser.getFullname();
				}
			}
			request.setAttribute("assigneeNames", assigneeNames);
			request.setAttribute("parentStack", parentStack);
		}

		request.setAttribute("taskId", taskId);

		return getAutoView();
	}

	/**
	 * 任务回退
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("jumpBack")
	public ModelAndView jumpBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		processCmd.setCurrentUserId(ContextUtil.getCurrentUserId().toString());
		processRunService.nextProcess(processCmd);
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 跳至会签页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toSign")
	public ModelAndView toSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		ModelAndView modelView = getAutoView();

		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			String nodeId = bpmService.getExecution(taskEntity.getExecutionId()).getActivityId();
			String actInstId = taskEntity.getProcessInstanceId();

			List<TaskSignData> signDataList = taskSignDataService.getByNodeAndInstanceId(actInstId, nodeId);

			// 获取会签规则
			BpmNodeSign bpmNodeSign = bpmNodeSignService.getByDefIdAndNodeId(taskEntity.getProcessDefinitionId(), nodeId);

			modelView.addObject("signDataList", signDataList);
			modelView.addObject("task", taskEntity);
			modelView.addObject("curUser", ContextUtil.getCurrentUser());
			modelView.addObject("bpmNodeSign", bpmNodeSign);
		}

		return modelView;
	}

	/**
	 * 补签
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addSign")
	@ResponseBody
	public String addSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String signUserIds = request.getParameter("signUserIds");
		String opinion = request.getParameter("opinion");
		String informType = RequestUtil.getString(request, "informType");
		if (StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(signUserIds) && StringUtils.isNotEmpty(opinion)) {
			// 保存意见
			taskSignDataService.addSign(signUserIds, taskId, opinion, informType);
		}
		return SUCCESS;
	}

	/**
	 * 任务自由跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("jump")
	@ResponseBody
	public String jump(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String destTask = request.getParameter("destTask");
		TaskEntity taskEnt = bpmService.getTask(taskId);
		bpmService.transTo(taskEnt, destTask);

		return SUCCESS;
	}

	/**
	 * 跳至会签页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveSign")
	@ResponseBody
	public String saveSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		String isAgree = request.getParameter("isAgree");
		String content = request.getParameter("content");

		taskSignDataService.signVoteTask(taskId, content, new Short(isAgree));

		return SUCCESS;
	}

	@SuppressWarnings("unused")
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");

		// 增加按新的流程分管授权中任务类型的权限获取流程的任务
		Long userId = ContextUtil.getCurrentUserId();
		String isNeedRight = "";

		Map<String, AuthorizeRight> authorizeRightMap = null;
		if (!ContextUtil.isSuperAdmin()) {
			isNeedRight = "yes";
			// 获得流程分管授权与用户相关的信息
			Map<String, Object> actRightMap = bpmDefAuthorizeService.getActRightByUserMap(userId, BPMDEFAUTHORIZE_RIGHT_TYPE.TASK, false, false);
			// 获得流程分管授权与用户相关的信息集合的流程KEY
			String actRights = (String) actRightMap.get("authorizeIds");
			filter.addFilter("actRights", actRights);
		}
		filter.addFilter("isNeedRight", isNeedRight);

		List<TaskEntity> list = bpmService.getTasks(filter);

		request.getSession().setAttribute("isAdmin", true);

		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);

		ModelAndView mv = getAutoView().addObject("taskList", list).addObject("hasGlobalFlowNo", hasGlobalFlowNo);

		return mv;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("forMe")
	public ModelAndView forMe(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		List<?> list = bpmService.getMyTasks(filter);
		Map<String, String> candidateMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		if (BeanUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				ProcessTask task = (ProcessTask) list.get(i);
				if (i == 0) {
					sb.append(task.getId());
				} else {
					sb.append("," + task.getId());
				}
			}
			List<Map> userMapList = bpmService.getHasCandidateExecutor(sb.toString());
			for (Iterator<Map> it = userMapList.iterator(); it.hasNext();) {
				Map map = it.next();
				candidateMap.put(map.get("TASKID").toString(), "1");
			}
		}
		ModelAndView mv = getAutoView().addObject("taskList", list).addObject("candidateMap", candidateMap);
		return mv;
	}

	/**
	 * 待办事项 flex 返回格式eg: [ { "id":"10000005210157", // 项id "type":"1", // 类型，如任务、消息 "startTime":"12/07/2012 00:00:00 AM", // 开始时间 "endTime":"12/08/2012 00:00:00 AM", // 结束时间
	 * "title":"测试流程变量-admin-2012-10-17 11:55:07", // 标题 } ]
	 * 
	 * @throws Exception
	 */
	@RequestMapping("myEvent")
	public void myEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("mode", RequestUtil.getString(request, "mode"));
		param.put("startDate", RequestUtil.getString(request, "startDate"));
		param.put("endDate", RequestUtil.getString(request, "endDate"));
		response.getWriter().print(bpmService.getMyEvents(param));
	}

	@RequestMapping("detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskId = RequestUtil.getLong(request, "taskId");
		Task task = taskService.createTaskQuery().taskId(taskId.toString()).singleResult();
		if (task == null) {
			return new ModelAndView("redirect:notExist.ht");
		}
		String returnUrl = RequestUtil.getPrePage(request);
		// get the process run instance from task
		ProcessRun processRun = processRunService.getByActInstanceId(new Long(task.getProcessInstanceId()));

		BpmDefinition processDefinition = bpmDefinitionService.getByActDefId(processRun.getActDefId());
		ModelAndView modelView = getAutoView();
		modelView.addObject("taskEntity", task).addObject("processRun", processRun).addObject("processDefinition", processDefinition).addObject("returnUrl", returnUrl);
		if (StringUtils.isNotEmpty(processRun.getBusinessUrl())) {
			String businessUrl = StringUtil.formatParamMsg(processRun.getBusinessUrl(), processRun.getBusinessKey()).toString();
			modelView.addObject("businessUrl", businessUrl);
		}
		return modelView;
	}

	/**
	 * 启动任务界面。 根据任务ID获取流程实例，根据流程实例获取表单数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toStart")
	public ModelAndView toStart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		return getToStartView(request, response, mv, 0);
	}

	/**
	 * 管理员使用的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("doNext")
	public ModelAndView doNext(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/platform/bpm/taskToStart.jsp");
		mv = getToStartView(request, response, mv, 1);

		return mv;
	}

	/**
	 * 流程启动页面（修改这个方法请修改手机的页面MobileTaskController.getMyTaskForm()）
	 * 
	 * @param request
	 * @param response
	 * @param mv
	 * @param isManage
	 * @return
	 * @throws Exception
	 */
	private ModelAndView getToStartView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isManage) throws Exception {
		String ctxPath = request.getContextPath();
		SysUser sysUser = (SysUser) ContextUtil.getCurrentUser();
		String taskId = RequestUtil.getString(request, "taskId");
		String instanceId = RequestUtil.getString(request, "instanceId");

		if (StringUtil.isEmpty(taskId) && StringUtil.isEmpty(instanceId)) {
			return ServiceUtil.getTipInfo("没有输入任务或实例ID!");
		}

		// 根据流程实例获取流程任务。
		if (StringUtil.isNotEmpty(instanceId)) {
			List<ProcessTask> list = bpmService.getTasks(instanceId);
			if (BeanUtils.isNotEmpty(list)) {
				taskId = list.get(0).getId();
			}
		}
		// 查找任务节点
		TaskEntity taskEntity = bpmService.getTask(taskId);

		if (taskEntity == null) {
			ProcessTaskHistory taskHistory = taskHistoryService.getById(Long.valueOf(taskId));
			if (taskHistory == null) {
				if (StringUtil.isEmpty(taskId) && StringUtil.isEmpty(instanceId)) {
					return ServiceUtil.getTipInfo("任务ID错误!");
				}
			}
			String actInstId = taskHistory.getProcessInstanceId();
			if (StringUtils.isEmpty(actInstId) && taskHistory.getDescription().equals(TaskOpinion.STATUS_COMMUNICATION.toString())) {
				return ServiceUtil.getTipInfo("此任务为沟通任务,并且此任务已经处理!");
			}
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(actInstId));
			if (processRun == null) {// 实例数据已被删除
				return ServiceUtil.getTipInfo("任务不存在!");
			}
			String url = request.getContextPath() + "/platform/bpm/processRun/info.ht?link=1&runId=" + processRun.getRunId();
			response.sendRedirect(url);
			return null;
		}

		if (TaskOpinion.STATUS_TRANSTO_ING.toString().equals(taskEntity.getDescription()) && taskEntity.getAssignee().equals(sysUser.getUserId().toString())) {
			return ServiceUtil.getTipInfo("对不起,这个任务正在流转中,不能处理此任务!");
		}

		instanceId = taskEntity.getProcessInstanceId();

		if (isManage == 0) {
			boolean hasRights = processRunService.getHasRightsByTask(new Long(taskEntity.getId()), sysUser.getUserId());
			if (!hasRights) {
				return ServiceUtil.getTipInfo("对不起,你不是这个任务的执行人,不能处理此任务!");
			}
		}
		// 更新任务为已读。
		taskReadService.saveReadRecord(Long.parseLong(instanceId), Long.parseLong(taskId));
		// 设置沟通人员或流转人员查看状态。
		commuReceiverService.setCommuReceiverStatus(taskEntity, sysUser);

		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		ProcessRun processRun = processRunService.getByActInstanceId(new Long(instanceId));

		Long defId = bpmDefinition.getDefId();

		/**
		 * 使用API调用的时候获取表单的url进行跳转。
		 */
		if (bpmDefinition.getIsUseOutForm() == 1) {
			String formUrl = bpmFormDefService.getFormUrl(taskId, actDefId, nodeId, processRun.getBusinessKey(), ctxPath);
			if (StringUtils.isEmpty(formUrl)) {
				ModelAndView rtnModel = ServiceUtil.getTipInfo("请设置API调用时表单的url!");
				return rtnModel;
			}
			response.sendRedirect(formUrl);
		}

		// 通过defid和nodeId获取联动设置
		List<BpmGangedSet> bpmGangedSets = bpmGangedSetService.getByDefIdAndNodeId(defId, nodeId);
		JSONArray gangedSetJarray = (JSONArray) JSONArray.fromObject(bpmGangedSets);

		Map<String, Object> variables = taskService.getVariables(taskId);

		String parentActDefId = "";
		if (variables.containsKey(BpmConst.FLOW_PARENT_ACTDEFID)) {// 判断当前是否属于子流程任务
			parentActDefId = variables.get(BpmConst.FLOW_PARENT_ACTDEFID).toString();
		}
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId, parentActDefId);

		String toBackNodeId = "";
		if (StringUtil.isNotEmpty(processRun.getStartNode())) {
			toBackNodeId = processRun.getStartNode();
		} else {
			toBackNodeId = NodeCache.getFirstNodeId(actDefId).getNodeId();
		}
		String form = "";

		Long tempLaunchId = userId;
		// 在沟通和加签的时候 把当前用户对于当前表单的权限设置为传递者的权限。
		if (StringUtils.isEmpty(taskEntity.getExecutionId())) {
			if (taskEntity.getDescription().equals(TaskOpinion.STATUS_TRANSTO.toString())) {
				List<TaskOpinion> taskOpinionList = taskOpinionService.getByActInstId(instanceId);
				if (BeanUtils.isNotEmpty(taskOpinionList)) {
					TaskOpinion taskOpinion = taskOpinionList.get(taskOpinionList.size() - 1);
					List<CommuReceiver> commuReceiverList = commuReceiverService.getByOpinionId(taskOpinion.getOpinionId());
					if (BeanUtils.isNotEmpty(commuReceiverList)) {
						tempLaunchId = taskOpinion.getExeUserId();
					}
				}
			}
		}

		FormModel formModel = bpmFormDefService.getNodeForm(processRun, nodeId, tempLaunchId, ctxPath, variables, false);
		// 如果是沟通任务 那么不允许沟通者有编辑表单的权限
		if (taskEntity.getDescription().equals(TaskOpinion.STATUS_COMMUNICATION.toString())) {
			BpmFormDef bpmFormDef = null;
			if (StringUtil.isNotEmpty(bpmNodeSet.getFormKey())) {
				bpmFormDef = bpmFormDefService.getDefaultPublishedByFormKey(bpmNodeSet.getFormKey());
			}
			form = bpmFormHandlerService.obtainHtml(bpmFormDef, processRun.getBusinessKey(), instanceId, actDefId, nodeId, ctxPath, parentActDefId, false, false, true);
			// form = bpmFormHandlerService.obtainHtml(bpmFormDef, pkValue, instanceId, actDefId, toBackNodeId, ctxPath, parentActDefId, isReCalcute, isPreView, isReadOnly)
			formModel.setFormHtml(form);
		}
		if (!formModel.isValid()) {
			ModelAndView rtnModel = ServiceUtil.getTipInfo("流程定义的流程表单发生了更改,数据无法显示!");
			return rtnModel;
		}

		String detailUrl = formModel.getDetailUrl();

		Boolean isExtForm = (Boolean) (formModel.getFormType() > 0);

		if (formModel.getFormType() == 0)
			form = formModel.getFormHtml();
		else
			form = formModel.getFormUrl();

		Boolean isEmptyForm = formModel.isFormEmpty();

		// 是否会签任务
		boolean isSignTask = bpmService.isSignTask(taskEntity);
		if (isSignTask) {
			handleSignTask(mv, instanceId, nodeId, actDefId, userId);
		}

		// 是否支持回退
		boolean isCanBack = bpmActService.isTaskAllowBack(taskId);
		// 是否转办
		boolean isCanAssignee = bpmTaskExeService.isAssigneeTask(taskEntity, bpmDefinition);

		// 是否执行隐藏路径
		boolean isHidePath = getIsHidePath(bpmNodeSet.getIsHidePath());

		// 是否是执行选择路径跳转
		boolean isHandChoolse = false;
		if (!isHidePath) {
			boolean canChoicePath = bpmService.getCanChoicePath(actDefId, taskId);
			Long startUserId = ContextUtil.getCurrentUserId();
			List<NodeTranUser> nodeTranUserList = bpmService.getNodeTaskUserMap(taskId, startUserId, canChoicePath);
			if (nodeTranUserList.size() > 1) {
				isHandChoolse = true;
			}
		}

		// 获取页面显示的按钮
		Map<String, List<BpmNodeButton>> mapButton = bpmNodeButtonService.getMapByDefNodeId(defId, nodeId);

		// 取常用语
		List<String> taskAppItems = taskAppItemService.getApprovalByDefKeyAndTypeId(bpmDefinition.getDefKey(), bpmDefinition.getTypeId());
		// 获取保存的意见
		TaskOpinion taskOpinion = taskOpinionService.getOpinionByTaskId(Long.parseLong(taskId), userId);

		// 帮助文档
		SysFile sysFile = null;
		if (BeanUtils.isNotEmpty(bpmDefinition.getAttachment())) {
			sysFile = sysFileService.getById(bpmDefinition.getAttachment());
		}

		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);

		return mv.addObject("task", taskEntity).addObject("bpmNodeSet", bpmNodeSet).addObject("processRun", processRun).addObject("bpmDefinition", bpmDefinition).addObject("isSignTask", isSignTask)
				.addObject("isCanBack", isCanBack).addObject("isCanAssignee", isCanAssignee).addObject("isHidePath", isHidePath).addObject("toBackNodeId", toBackNodeId).addObject("form", form)
				.addObject("isExtForm", isExtForm).addObject("isEmptyForm", isEmptyForm).addObject("taskAppItems", taskAppItems).addObject("mapButton", mapButton).addObject("detailUrl", detailUrl)
				.addObject("isManage", isManage).addObject("bpmGangedSets", gangedSetJarray).addObject("sysFile", sysFile).addObject("taskOpinion", taskOpinion)
				.addObject("isHandChoolse", isHandChoolse).addObject("curUserId", sysUser.getUserId().toString()).addObject("curUserName", sysUser.getFullname())
				.addObject("hasGlobalFlowNo", hasGlobalFlowNo).addObject("formKey", bpmNodeSet.getFormKey());
	}

	/**
	 * 产生的沟通意见任务，并发送到沟通人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toStartCommunication")
	public void toStartCommunication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		ResultMessage resultMessage = null;
		String cmpIds = request.getParameter("cmpIds");
		if (StringUtil.isEmpty(cmpIds)) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "请输入通知人!");
			out.print(resultMessage);
			return;
		}
		try {
			String taskId = request.getParameter("taskId");
			String opinion = request.getParameter("opinion");
			String informType = RequestUtil.getString(request, "informType");
			// 保存意见
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));

			processRunService.saveCommuniCation(taskEntity, opinion, informType, cmpIds, processRun.getSubject());
			ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);
			processRunService.handlerFormData(taskCmd, processRun, taskEntity.getTaskDefinitionKey());

			Long runId = processRun.getRunId();

			String memo = "在:【" + processRun.getSubject() + "】,节点【" + taskEntity.getName() + "】,意见:" + opinion;
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_ADDOPINION, memo);

			resultMessage = new ResultMessage(ResultMessage.Success, "成功完成了该任务!");
		} catch (Exception e) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "完成任务失败!");
		}
		out.print(resultMessage);
	}

	/**
	 * 产生的流转任务，并发送到流转人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toStartTransTo")
	public void toStartTransTo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		ResultMessage resultMessage = null;
		String cmpIds = request.getParameter("cmpIds");
		if (StringUtil.isEmpty(cmpIds)) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "请输入通知人!");
			out.print(resultMessage);
			return;
		}
		try {
			String taskId = request.getParameter("taskId");
			String opinion = request.getParameter("opinion");
			String informType = RequestUtil.getString(request, "informType");
			String transType = request.getParameter("transType");
			String action = request.getParameter("action");
			// 保存意见
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));

			processRunService.saveTransTo(taskEntity, opinion, informType, cmpIds, transType, action, processRun);

			// 同时保存表单数据。
			ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);
			taskCmd.setVoteContent(opinion);
			processRunService.handlerFormData(taskCmd, processRun, taskEntity.getTaskDefinitionKey());

			Long runId = processRun.getRunId();

			String memo = "在:【" + processRun.getSubject() + "】,节点【" + taskEntity.getName() + "】,意见:" + opinion;
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_ADDOPINION, memo);

			resultMessage = new ResultMessage(ResultMessage.Success, "成功完成了该任务!");
		} catch (Exception e) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "完成任务失败!");
			logger.error("错误信息",e);
		}
		out.print(resultMessage);
	}

	/**
	 * 是否执行隐藏路径
	 * 
	 * @param isHidePath
	 * @return
	 */
	private boolean getIsHidePath(Short isHidePath) {
		if (BeanUtils.isEmpty(isHidePath))
			return false;
		if (BpmNodeSet.HIDE_PATH.shortValue() == isHidePath.shortValue())
			return true;
		return false;
	}

	/**
	 * 处理会签
	 * 
	 * @param mv
	 * @param instanceId
	 * @param nodeId
	 * @param actDefId
	 * @param userId 当前用户
	 */
	private void handleSignTask(ModelAndView mv, String instanceId, String nodeId, String actDefId, Long userId) {

		List<TaskSignData> signDataList = taskSignDataService.getByNodeAndInstanceId(instanceId, nodeId);
		// 获取会签规则
		BpmNodeSign bpmNodeSign = bpmNodeSignService.getByDefIdAndNodeId(actDefId, nodeId);

		mv.addObject("signDataList", signDataList);
		mv.addObject("bpmNodeSign", bpmNodeSign);
		mv.addObject("curUser", ContextUtil.getCurrentUser());
		// 获取当前组织
		Long orgId = ContextUtil.getCurrentOrgId();

		// "允许直接处理"特权
		boolean isAllowDirectExecute = bpmNodeSignService.checkNodeSignPrivilege(actDefId, nodeId, BpmNodePrivilegeType.ALLOW_DIRECT, userId, orgId);
		// "允许补签"特权
		boolean isAllowRetoactive = bpmNodeSignService.checkNodeSignPrivilege(actDefId, nodeId, BpmNodePrivilegeType.ALLOW_RETROACTIVE, userId, orgId);
		// "一票决断"特权
		boolean isAllowOneVote = bpmNodeSignService.checkNodeSignPrivilege(actDefId, nodeId, BpmNodePrivilegeType.ALLOW_ONE_VOTE, userId, orgId);
		mv.addObject("isAllowDirectExecute", isAllowDirectExecute).addObject("isAllowRetoactive", isAllowRetoactive).addObject("isAllowOneVote", isAllowOneVote);

	}

	@RequestMapping("getForm")
	public ModelAndView getForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ctxPath = request.getContextPath();
		String taskId = RequestUtil.getString(request, "taskId");
		String returnUrl = RequestUtil.getPrePage(request);
		// 查找任务节点
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String action = RequestUtil.getString(request, "action", "");
		if (taskEntity == null) {
			return new ModelAndView("redirect:notExist.ht");
		}
		String instanceId = taskEntity.getProcessInstanceId();
		String taskName = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		Long userId = ContextUtil.getCurrentUserId();

		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);

		ProcessRun processRun = processRunService.getByActInstanceId(new Long(instanceId));

		String form = "";
		Boolean isExtForm = false;
		Boolean isEmptyForm = false;

		Map<String, Object> variables = taskService.getVariables(taskId);

		if (bpmDefinition != null) {
			FormModel formModel = bpmFormDefService.getNodeForm(processRun, taskName, userId, ctxPath, variables, true);

			isExtForm = formModel.getFormType() > 0;
			if (formModel.getFormType() == 0) { // 在线表单
				form = formModel.getFormHtml();
			} else if (formModel.getFormType() == 1) { // url表单
				form = formModel.getFormUrl();
			} else if (formModel.getFormType() == 2) { // 有明细url
				form = formModel.getDetailUrl();
			}

			isEmptyForm = formModel.isFormEmpty();
		}

		return getAutoView().addObject("task", taskEntity).addObject("form", form).addObject("bpmDefinition", bpmDefinition).addObject("isExtForm", isExtForm).addObject("isEmptyForm", isEmptyForm)
				.addObject("action", action).addObject("processRun", processRun).addObject("returnUrl", returnUrl);
	}

	/**
	 * 完成任务
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("complete")
	public void complete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("任务完成跳转....");

		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();

		PrintWriter out = response.getWriter();
		ResultMessage resultMessage = null;
		String taskId = RequestUtil.getString(request, "taskId");
		TaskEntity task = bpmService.getTask(taskId);
		if (task == null) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "此任务已经执行了!");
			out.print(resultMessage);
			return;
		}
		String actDefId = task.getProcessDefinitionId();
		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		if (BpmDefinition.STATUS_INST_DISABLED.equals(bpmDefinition.getStatus())) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "流程实例已经禁止，该任务不能办理！");
			out.print(resultMessage);
			return;
		}
		Long userId = curUser.getUserId();

		ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);

		taskCmd.setCurrentUserId(userId.toString());

		String assignee = task.getAssignee();
		// 非管理员,并且没有任务的权限。
		boolean isAdmin = taskCmd.getIsManage().shortValue() == 1;
		if (!isAdmin) {
			boolean rtn = processRunService.getHasRightsByTask(new Long(taskId), userId);
			if (!rtn) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "对不起,你不是这个任务的执行人,不能处理此任务!");
				out.print(resultMessage);
				return;
			}
		}

		// 记录日志。
		logger.info(taskCmd.toString());
		if (ServiceUtil.isAssigneeNotEmpty(assignee) && !task.getAssignee().equals(userId.toString()) && !isAdmin) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "该任务已被其他人锁定");
		} else {
			String errorUrl = RequestUtil.getErrorUrl(request);
			String ip = RequestUtil.getIpAddr(request);
			try {
				processRunService.nextProcess(taskCmd);
				resultMessage = new ResultMessage(ResultMessage.Success, "任务成功完成!");

			} catch (ActivitiVarNotFoundException ex) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "请检查变量是否存在:" + ex.getMessage());
				// 添加错误消息到日志
				sysErrorLogService.addError(curUser.getAccount(), ip, ex.getMessage(), errorUrl);
			} catch (ActivitiInclusiveGateWayException ex) {
				resultMessage = new ResultMessage(ResultMessage.Fail, ex.getMessage());
				// 添加错误消息到日志
				sysErrorLogService.addError(curUser.getAccount(), ip, ex.getMessage(), errorUrl);
			} catch (Exception ex) {
				logger.error("错误信息",ex);
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					resultMessage = new ResultMessage(ResultMessage.Fail, str);
					// 添加错误消息到日志
					sysErrorLogService.addError(curUser.getAccount(), ip, str, errorUrl);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					resultMessage = new ResultMessage(ResultMessage.Fail, message);
					// 添加错误消息到日志
					sysErrorLogService.addError(curUser.getAccount(), ip, message, errorUrl);
				}
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 锁定任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("claim")
	@Action(description = "锁定任务")
	public void claim(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		// int isAgent = RequestUtil.getInt(request, "isAgent");
		String preUrl = RequestUtil.getPrePage(request);
		String assignee = ContextUtil.getCurrentUserId().toString();
		// 代理任务，则设置锁定的assignee为代理人

		try {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));
			Long runId = processRun.getRunId();
			taskService.claim(taskId, assignee);
			String memo = "流程:" + processRun.getSubject() + ",锁定任务，节点【" + taskEntity.getName() + "】";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_LOCK, memo);
			saveSuccessResultMessage(request.getSession(), "成功锁定任务!");
		} catch (Exception ex) {
			saveSuccessResultMessage(request.getSession(), "任务已经完成或被其他用户锁定!");
		}
		response.sendRedirect(preUrl);
	}

	/**
	 * 解锁任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("unlock")
	@Action(description = "解锁任务")
	public ModelAndView unlock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		if (StringUtils.isNotEmpty(taskId)) {
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));
			Long runId = processRun.getRunId();
			bpmService.updateTaskAssigneeNull(taskId);
			String memo = "流程:" + processRun.getSubject() + ",解锁任务，节点【" + taskEntity.getName() + "】";
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_UNLOCK, memo);
			saveSuccessResultMessage(request.getSession(), "任务已经成功解锁!");
		}
		return new ModelAndView("redirect:forMe.ht");
	}

	/**
	 * 任务跳转窗口显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("freeJump")
	public ModelAndView freeJump(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		// 获取当前节点的选择器限定配置
		ExecutionEntity execution = bpmService.getExecutionByTaskId(taskId);
		String superExecutionId = execution.getSuperExecutionId();
		String parentActDefId = "";
		if (StringUtil.isNotEmpty(superExecutionId)) {
			ExecutionEntity supExecution = bpmService.getExecution(superExecutionId);
			parentActDefId = supExecution.getProcessDefinitionId();
		}
		String nodeId = execution.getActivityId();
		String processDefinitionId = execution.getProcessDefinitionId();
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(processDefinitionId, nodeId, parentActDefId);
		String scope = bpmNodeSet.getScope();

		Map<String, Map<String, String>> jumpNodesMap = bpmService.getJumpNodes(taskId);
		ModelAndView view = this.getAutoView();
		view.addObject("jumpNodeMap", jumpNodesMap).addObject("scope", scope);
		return view;
	}

	/**
	 * 动态创建任务加载显示页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dynamicCreate")
	public ModelAndView dynamicCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		TaskEntity task = bpmService.getTask(taskId);
		return this.getAutoView().addObject("task", task);
	}

	/**
	 * 获取某个流程实例上某个节点的配置执行人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTaskUsers")
	@ResponseBody
	public List<TaskExecutor> getTaskUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 任务Id
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = bpmService.getTask(taskId);

		String nodeId = RequestUtil.getString(request, "nodeId"); // 所选择的节点Id

		if (StringUtil.isEmpty(nodeId)) {
			nodeId = taskEntity.getTaskDefinitionKey(); // 目标节点Id
		}

		String actDefId = taskEntity.getProcessDefinitionId();

		String actInstId = taskEntity.getProcessInstanceId();

		Map<String, Object> vars = runtimeService.getVariables(taskEntity.getExecutionId());

		String startUserId = vars.get(BpmConst.StartUser).toString();

		@SuppressWarnings("unchecked")
		List<TaskExecutor> taskExecutorList = bpmNodeUserService.getExeUserIds(actDefId, actInstId, nodeId, startUserId, "", vars);

		return taskExecutorList;
	}

	/**
	 * 指派任务所属人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Action(description = "任务指派所属人", detail = "<#if StringUtils.isNotEmpty(taskIds)>" + "流程" + "<#list StringUtils.split(taskIds,\",\") as item>" + "【${SysAuditLinkService.getProcessRunLink(item)}】"
			+ "</#list>" + "的任务指派给【${SysAuditLinkService.getSysUserLink(Long.valueOf(userId))}】" + "</#if>")
	@RequestMapping("assign")
	public ModelAndView assign(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String userId = request.getParameter("userId");

		if (StringUtils.isNotEmpty(taskIds)) {
			String[] tIds = taskIds.split("[,]");
			if (tIds != null) {
				for (String tId : tIds) {
					bpmService.assignTask(tId, userId);
				}
			}
		}
		saveSuccessResultMessage(request.getSession(), "成功为指定任务任务分配执行人员!");
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 任务交办设置任务的执行人。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delegate")
	@Action(description = "任务交办", detail = "<#if StringUtils.isNotEmpty(taskId) && StringUtil.isNotEmpty(userId)>" + "交办流程【${SysAuditLinkService.getProcessRunLink(taskId)}】的任务【${taskName}】"
			+ "给用户【${SysAuditLinkService.getSysUserLink(Long.valueOf(userId))}】" + "</#if>")
	public void delegate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String taskId = request.getParameter("taskId");
		String userId = request.getParameter("userId");
		// String delegateDesc = request.getParameter("memo");
		ResultMessage message = null;
		// TODO ZYG 任务交办
		if (StringUtils.isNotEmpty(taskId) && StringUtil.isNotEmpty(userId)) {
			SysAuditThreadLocalHolder.putParamerter("taskName", bpmService.getTask(taskId).getName());
			// processRunService.delegate(taskId, userId, delegateDesc);
			message = new ResultMessage(ResultMessage.Success, "任务交办成功!");
		} else {
			message = new ResultMessage(ResultMessage.Fail, "没有传入必要的参数");
		}
		writer.print(message);
	}

	@RequestMapping("changeAssignee")
	@Action(description = "更改任务执行人")
	public ModelAndView changeAssignee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		TaskEntity taskEntity = bpmService.getTask(taskId);
		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();

		return getAutoView().addObject("taskEntity", taskEntity).addObject("curUser", curUser).addObject("handlersMap", handlersMap);
	}

	@RequestMapping("setAssignee")
	@Action(description = "任务指派")
	public void setAssignee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		String taskId = request.getParameter("taskId");
		String userId = request.getParameter("userId");

		String voteContent = RequestUtil.getString(request, "voteContent");
		String informType = RequestUtil.getString(request, "informType");
		ResultMessage message = null;
		try {
			message = processRunService.updateTaskAssignee(taskId, userId, voteContent, informType);
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, e.getMessage());
		}
		writer.print(message);
	}

	/**
	 * 设置任务的执行人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setDueDate")
	@Action(description = "设置任务到期时间")
	public ModelAndView setDueDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskIds = request.getParameter("taskIds");
		String dueDates = request.getParameter("dueDates");
		if (StringUtils.isNotEmpty(taskIds) && StringUtils.isNotEmpty(dueDates)) {
			String[] tIds = taskIds.split("[,]");
			String[] dates = dueDates.split("[,]");
			if (tIds != null) {
				for (int i = 0; i < dates.length; i++) {
					if (StringUtils.isNotEmpty(dates[i])) {
						Date dueDate = DateUtils.parseDate(dates[i], new String[] { "yyyy-MM-dd HH:mm:ss" });
						bpmService.setDueDate(tIds[i], dueDate);
					}
				}
			}
		}
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 删除任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	@Action(description = "删除任务", execOrder = ActionExecOrder.BEFORE, detail = "<#if StringUtils.isNotEmpty(taskId)>" + "<#assign entity1=bpmService.getTask(taskId)/>"
			+ "用户删除了任务【${entity1.name}】,该任务属于流程【${SysAuditLinkService.getProcessRunLink(taskId)}】" + "</#elseif StringUtils.isNotEmpty(id)>" + "<#list StringUtils.split(id,\",\") as item>"
			+ "<#assign entity2=bpmService.getTask(item)/>" + "用户删除了任务【${entity2.name}】,该任务属于流程【${SysAuditLinkService.getProcessRunLink(item)}】" + "</#list>" + "</#if>")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String taskId = request.getParameter("taskId");
			String[] taskIds = request.getParameterValues("id");
			if (StringUtils.isNotEmpty(taskId)) {
				bpmService.deleteTask(taskId);

				TaskEntity task = bpmService.getTask(taskId);
				ProcessRun processRun = processRunService.getByActInstanceId(new Long(task.getProcessInstanceId()));
				String memo = "用户删除了任务[" + task.getName() + "],该任务属于[" + processRun.getProcessName() + "]流程。";
				bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_DELETETASK, memo);
				taskService.deleteTask(taskId);

			} else if (taskIds != null && taskIds.length != 0) {
				bpmService.deleteTasks(taskIds);
				for (int i = 0; i < taskIds.length; i++) {
					String id = taskIds[i];
					TaskEntity task = bpmService.getTask(id);
					ProcessRun processRun = processRunService.getByActInstanceId(new Long(task.getProcessInstanceId()));
					String memo = "用户删除了任务[" + task.getName() + "],该任务属于[" + processRun.getProcessName() + "]流程。";
					bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_DELETETASK, memo);
					taskService.deleteTask(id);
				}
			}
			message = new ResultMessage(ResultMessage.Success, "删除任务成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除任务失败");
		}
		addMessage(message, request);
		return new ModelAndView("redirect:list.ht");
	}

	/**
	 * 返回某个某个用户代理给当前用户的任务列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("forAgent")
	// public ModelAndView forAgent(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// ModelAndView mv = getAutoView();
	// Long userId = RequestUtil.getLong(request, "userId");
	// QueryFilter filter = new QueryFilter(request, "taskItem");
	// Calendar cal = Calendar.getInstance();
	// Date curTime = cal.getTime();
	// cal.add(Calendar.DATE, -1);
	// Date yesterday = cal.getTime();
	//
	// filter.addFilter("curTime", curTime);
	// filter.addFilter("yesterday", yesterday);
	// List<TaskEntity> list = null;
	// SysUserAgent sysUserAgent = null;
	// // 具体人员的代理
	// if (userId != 0) {
	// sysUserAgent = sysUserAgentService.getById(userId);
	// }
	// if (sysUserAgent != null) {
	//
	// // 代理设置是否过期
	// if (sysUserAgent.getStarttime() != null) {
	// int result = sysUserAgent.getStarttime().compareTo(curTime);
	// if (result > 0) {
	// list = new ArrayList<TaskEntity>();
	// mv = getAutoView().addObject("taskList", list).addObject(
	// "userId", userId);
	// return mv;
	// }
	// }
	// if (sysUserAgent.getEndtime() != null) {
	// cal.add(Calendar.DATE, -1);
	// int result = sysUserAgent.getEndtime().compareTo(yesterday);
	// if (result <= 0) {
	// list = new ArrayList<TaskEntity>();
	// mv = getAutoView().addObject("taskList", list).addObject(
	// "userId", userId);
	// return mv;
	// }
	// }
	// if (sysUserAgent.getIsall().intValue() == SysUserAgent.IS_ALL_FLAG) {// 全部代理
	// list = bpmService.getTaskByUserId(
	// sysUserAgent.getAgentuserid(), filter);
	// } else {// 部分代理
	// StringBuffer actDefId = new StringBuffer("");
	// List<String> notInBpmAgentlist = bpmAgentService
	// .getNotInByAgentId(sysUserAgent.getAgentid());
	// for (String ba : notInBpmAgentlist) {
	// actDefId.append("'").append(ba).append("',");
	// }
	// if (notInBpmAgentlist.size() > 0) {
	// actDefId.deleteCharAt(actDefId.length() - 1);
	// }
	// list = bpmService.getAgentTasks(sysUserAgent.getAgentuserid(),
	// actDefId.toString(), filter);
	// }
	// } else {
	// list = bpmService.getAllAgentTask(ContextUtil.getCurrentUserId(),
	// filter);
	// }
	// mv = getAutoView().addObject("taskList", list).addObject("userId",
	// userId);
	//
	// return mv;
	// }

	/**
	 * 返回目标节点及其节点的处理人员映射列表。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tranTaskUserMap")
	public ModelAndView tranTaskUserMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int isStart = RequestUtil.getInt(request, "isStart", 0);
		String taskId = request.getParameter("taskId");
		String actDefId = request.getParameter("actDefId");

		String scope = "";
		if (StringUtil.isEmpty(taskId)) {
			List<FlowNode> firstNode = NodeCache.getFirstNode(actDefId);
			if (BeanUtils.isNotEmpty(firstNode)) {
				FlowNode flowNode = firstNode.get(0);
				String nodeId = flowNode.getNodeId();
				BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId, "");
				if (BeanUtils.isNotEmpty(bpmNodeSet))
					scope = bpmNodeSet.getScope();
			}
		} else {
			// 获取当前节点的选择器限定配置
			ExecutionEntity execution = null;

			TaskEntity taskEntity = bpmService.getTask(taskId);
			if (taskEntity.getDescription().equals(TaskOpinion.STATUS_TRANSTO.toString())) {// 加签任务
				execution = bpmService.getExecutionByTaskId(taskEntity.getParentTaskId());// 获取它的parentTaskId，这里是放着的加签任务生成的源任务
			} else {
				// 获取当前节点的选择器限定配置
				execution = bpmService.getExecutionByTaskId(taskId);
			}

			String superExecutionId = execution.getSuperExecutionId();
			String parentActDefId = "";
			if (StringUtil.isNotEmpty(superExecutionId)) {
				ExecutionEntity supExecution = bpmService.getExecution(superExecutionId);
				parentActDefId = supExecution.getProcessDefinitionId();
			}
			String nodeId = execution.getActivityId();
			String processDefinitionId = execution.getProcessDefinitionId();
			BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(processDefinitionId, nodeId, parentActDefId);
			if (BeanUtils.isNotEmpty(bpmNodeSet))
				scope = bpmNodeSet.getScope();
		}

		int selectPath = RequestUtil.getInt(request, "selectPath", 1);

		boolean canChoicePath = bpmService.getCanChoicePath(actDefId, taskId);

		Long startUserId = ContextUtil.getCurrentUserId();
		List<NodeTranUser> nodeTranUserList = null;
		if (isStart == 1) {
			Map<String, Object> vars = new HashMap<String, Object>();
			nodeTranUserList = bpmService.getStartNodeUserMap(actDefId, startUserId, vars);
		} else {
			nodeTranUserList = bpmService.getNodeTaskUserMap(taskId, startUserId, canChoicePath);
		}

		return getAutoView().addObject("nodeTranUserList", nodeTranUserList).addObject("selectPath", selectPath).addObject("scope", scope).addObject("canChoicePath", canChoicePath);
	}

	/**
	 * 结合前台任务管理列表，点击某行任务时，显示的任务简单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("miniDetail")
	public ModelAndView miniDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");

		TaskEntity taskEntity = bpmService.getTask(taskId);

		if (taskEntity == null) {
			return new ModelAndView("/platform/bpm/taskNotExist.jsp");
		}

		// 取到任务的侯选人员
		Set<TaskExecutor> candidateUsers = taskUserService.getCandidateExecutors(taskId);

		ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));

		BpmDefinition definition = bpmDefinitionService.getByActDefId(taskEntity.getProcessDefinitionId());

		List<ProcessTask> curTaskList = bpmService.getTasks(taskEntity.getProcessInstanceId());

		return getAutoView().addObject("taskEntity", taskEntity).addObject("processRun", processRun).addObject("candidateUsers", candidateUsers).addObject("processDefinition", definition)
				.addObject("curTaskList", curTaskList);
	}

	/**
	 * 准备更新任务的路径
	 * 
	 * @param request
	 * @param response
	 * @returngetTaskUsers
	 * @throws Exception
	 */
	@RequestMapping("changePath")
	public ModelAndView changePath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");
		TaskEntity taskEntity = bpmService.getTask(taskId);
		Map<String, String> taskNodeMap = bpmService.getTaskNodes(taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();

		return this.getAutoView().addObject("taskEntity", taskEntity).addObject("taskNodeMap", taskNodeMap).addObject("curUser", ContextUtil.getCurrentUser()).addObject("handlersMap", handlersMap);
	}

	/**
	 * 保存变更路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveChangePath")
	@ResponseBody
	public String saveChangePath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessCmd processCmd = BpmUtil.getProcessCmd(request);
		processRunService.nextProcess(processCmd);
		saveSuccessResultMessage(request.getSession(), "更改任务执行的路径!");
		return SUCCESS;
	}

	/**
	 * 结束流程任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("end")
	@Action(description = "结束流程任务", detail = "结束流程【${SysAuditLinkService.getProcessRunLink(taskId)}】的任务")
	public void end(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage resultMessage = null;
		try {
			String taskId = request.getParameter("taskId");
			TaskEntity taskEntity = bpmService.getTask(taskId);
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(taskEntity.getProcessInstanceId()));
			String voteContent = "由" + ContextUtil.getCurrentUser().getFullname() + "进行完成操作！";
			ProcessCmd cmd = new ProcessCmd();
			cmd.setTaskId(taskId);
			cmd.setVoteAgree((short) 0);
			cmd.setVoteContent(voteContent);
			cmd.setOnlyCompleteTask(true);
			processRunService.nextProcess(cmd);
			Long runId = processRun.getRunId();
			String memo = "结束了:" + processRun.getSubject();
			bpmRunLogService.addRunLog(runId, BpmRunLog.OPERATOR_TYPE_ENDTASK, memo);
			resultMessage = new ResultMessage(ResultMessage.Success, "成功完成了该任务!");
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "完成任务失败:" + str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		response.getWriter().print(resultMessage);
	}

	/**
	 * 根据任务结束流程实例。
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("endProcess")
	@Action(description = "根据任务结束流程实例")
	public void endProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		Long taskId = RequestUtil.getLong(request, "taskId");
		String memo = RequestUtil.getString(request, "memo");
		// Long curUserId=ContextUtil.getCurrentUserId();
		TaskEntity taskEnt = bpmService.getTask(taskId.toString());
		if (taskEnt == null) {
			writeResultMessage(out, "此任务已经完成!", ResultMessage.Fail);
		}

		String instanceId = taskEnt.getProcessInstanceId();
		ResultMessage message = null;
		try {
			String nodeId = taskEnt.getTaskDefinitionKey();
			ProcessRun processRun = bpmService.endProcessByInstanceId(new Long(instanceId), nodeId, memo);
			memo = "结束流程:" + processRun.getSubject() + ",结束原因:" + memo;
			bpmRunLogService.addRunLog(processRun.getRunId(), BpmRunLog.OPERATOR_TYPE_ENDTASK, memo);
			message = new ResultMessage(ResultMessage.Success, "结束流程实例成功!");
			writeResultMessage(out, message);
		} catch (Exception ex) {
			logger.error("错误信息",ex);
			message = new ResultMessage(ResultMessage.Fail, ExceptionUtil.getExceptionMessage(ex));
			writeResultMessage(out, message);
		}
	}

	/**
	 * 待办事宜
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pendingMattersList")
	public ModelAndView pendingMattersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		String nodePath = RequestUtil.getString(request, "nodePath");
		boolean billingPath = nodePath.equals(NODEPATH);
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<?> list = bpmService.getMyTasks(filter);
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		// 通过流水号获取表单信息
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object o : list) {
			ProcessTask processTask = (ProcessTask) o;
			String processInstanceId = processTask.getProcessInstanceId();
			String subjectStr = processTask.getSubject();
			if (subjectStr.indexOf("-" + processTask.getCreator()) > -1) {
				processTask.setSubject(subjectStr.replace("-" + processTask.getCreator(), ""));
			}
			// 通过ExecutionId获取表单数据
			if (null != processInstanceId && processInstanceId.length() > 0) {
				ProcessRun processRun = processRunService.getByActInstanceId(Long.valueOf(processInstanceId));
				if (null != processRun) {
					// 获取表名，businesskey
					String businesskey = processRun.getBusinessKey();
					// 合同开票
					if (billingPath) {
						Map<String, Object> billingMap = new HashMap<String, Object>();
						billingMap.put("processTask", processTask);
						billingMap.put("contractBillingApplication", contractBillingApplicationService.getById(Long.parseLong(businesskey)));
						listMap.add(billingMap);
						continue;
					}
					processTask.setProcessName(processRunService.getProcessNameByTableName("", processRun.getTableName(), businesskey));
				}
			}
		}
		if (billingPath) {
			ModelAndView mv = new ModelAndView("/platform/bpm/taskPendingMattersListOfBilling.jsp");
			return mv.addObject("infoMap", listMap).addObject("warningSet", reminderService.getWaringSetMap()).addObject("hasGlobalFlowNo", hasGlobalFlowNo).addObject("nodePath", nodePath);
		}
		return getAutoView().addObject("taskList", list).addObject("warningSet", reminderService.getWaringSetMap()).addObject("hasGlobalFlowNo", hasGlobalFlowNo);
	}

	/**
	 * 待办事宜
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pendingMattersExport")
	public void pendingMattersExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, false);
		String nodePath = RequestUtil.getString(request, "nodePath");
		boolean billingPath = nodePath.equals(NODEPATH);
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<?> list = bpmService.getMyTasks(filter);
		// 是否有全局流水号
		boolean hasGlobalFlowNo = PropertyUtil.getBooleanByAlias(SysProperty.GlobalFlowNo);
		// 通过流水号获取表单信息
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object o : list) {
			ProcessTask processTask = (ProcessTask) o;
			String processInstanceId = processTask.getProcessInstanceId();
			String subjectStr = processTask.getSubject();
			if (subjectStr.indexOf("-" + processTask.getCreator()) > -1) {
				processTask.setSubject(subjectStr.replace("-" + processTask.getCreator(), ""));
			}
			// 通过ExecutionId获取表单数据
			if (null != processInstanceId && processInstanceId.length() > 0) {
				ProcessRun processRun = processRunService.getByActInstanceId(Long.valueOf(processInstanceId));
				if (null != processRun) {
					// 获取表名，businesskey
					String businesskey = processRun.getBusinessKey();
					// 合同开票
					if (billingPath) {
						Map<String, Object> billingMap = new HashMap<String, Object>();
						billingMap.put("processTask", processTask);
						billingMap.put("contractBillingApplication", contractBillingApplicationService.getById(Long.parseLong(businesskey)));
						listMap.add(billingMap);
						continue;
					}
					processTask.setProcessName(processRunService.getProcessNameByTableName("", processRun.getTableName(), businesskey));
				}
			}
		}
		List<ProcessTask> ptList = new ArrayList<ProcessTask>();
		for (Object o : list) {
			ProcessTask processTask = (ProcessTask) o;
			ptList.add(processTask);
		}
		if (billingPath) {
			processRunService.exportPendingBillingPath(request, response, nodePath, listMap, hasGlobalFlowNo);
		} else {
			processRunService.exportPendingMatters(request, response, nodePath, ptList, hasGlobalFlowNo);
		}
	}

	/**
	 * 待办事宜
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pd_mt_db_index")
	public void pendingMattersJsonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "taskItem");
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(1);
		pageBean.setPagesize(5);
		filter.setPageBean(pageBean);
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<TaskEntity> list = bpmService.getMyTasks(filter);
		int count = filter.getPageBean().getTotalCount();
		JSONArray json = JSONArray.fromObject(list);
		String resultMsg = json.toString();
		String rstJson = "{\"total\":" + count + ",\"data\":" + resultMsg + "}";
		writeResultMessage(response.getWriter(), rstJson, ResultMessage.Success);
	}
	/**
	 * 批量审批数量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pd_mt_batch_index")
	public void pd_mt_batch_index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int count=0;
		QueryFilter filter = new QueryFilter(request, false);
		List<?> taskList = bpmService.getMyTasks(filter); //这个考虑下是否这样获取数据
		List<BpmBatchApproval> approvalList = bpmBatchApprovalService.getAll();//这个没有权限，把所有数据取出来 
		Map<String, BpmBatchApproval> map = new LinkedHashMap<String, BpmBatchApproval>();
		List<BpmTree> treeList = new ArrayList<BpmTree>();
		/*if (BeanUtils.isEmpty(taskList) || BeanUtils.isEmpty(approvalList))*/
		if (BeanUtils.isEmpty(approvalList)){
			
		}else{
			for (BpmBatchApproval bpmBatchApproval : approvalList) {
				BpmDefinition def = bpmDefinitionService
						.getMainByDefKey(bpmBatchApproval.getDefKey());
				BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(
						def.getActDefId(), bpmBatchApproval.getNodeId(),"");
				bpmBatchApproval.setSubject(def.getSubject());
				bpmBatchApproval.setNodeName(bpmNodeSet.getNodeName());
				map.put(bpmBatchApproval.getDefKey() + ";"
						+ bpmBatchApproval.getNodeId(), bpmBatchApproval);
			}
			Map<String, List<ProcessTask>> map1 = new HashMap<String, List<ProcessTask>>();
			for (int i = 0; i < taskList.size(); i++) {
				ProcessTask task = (ProcessTask) taskList.get(i);
				String actDefId = task.getProcessDefinitionId();
				String defKey = actDefId.split(":")[0];
				String nodeId = task.getTaskDefinitionKey();
				String key = defKey + ";" + nodeId;
				BpmBatchApproval b = map.get(key);
				if (BeanUtils.isEmpty(b))
					continue;
				List<ProcessTask> list1 = map1.get(key);
				if (BeanUtils.isEmpty(list1))
					list1 = new ArrayList<ProcessTask>();
				list1.add(task);
				map1.put(key, list1);
			}
			Set<Entry<String,List<ProcessTask>>> entrySet = map1.entrySet();
			for (Entry<String, List<ProcessTask>> entry : entrySet) {
				List<ProcessTask> value = entry.getValue();
				count+=value.size();
			}
		}
		
		String resultMsg = "ok";
		String rstJson = "{\"total\":" + count + ",\"data\":" + "\""+resultMsg + "\"}";
		writeResultMessage(response.getWriter(), rstJson, ResultMessage.Success);
	}

	/**
	 * 已办事宜
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pd_mt_yb_index")
	public void alreadyCompletedMattersJsonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter filter = new QueryFilter(request, "processRunItem");
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(1);
		pageBean.setPagesize(5);
		filter.setPageBean(pageBean);
		filter.addFilter("assignee", ContextUtil.getCurrentUserId().toString());// 用户id
		String nodePath = RequestUtil.getString(request, "nodePath");
		if (StringUtils.isNotEmpty(nodePath))
			filter.getFilters().put("nodePath", nodePath + "%");
		List<ProcessRun> list = processRunService.getAlreadyCompletedMattersList(filter);
		int count = filter.getPageBean().getTotalCount();
		JSONArray json = JSONArray.fromObject(list);
		String resultMsg = json.toString();
		String rstJson = "{\"total\":" + count + ",\"data\":" + resultMsg + "}";
		writeResultMessage(response.getWriter(), rstJson, ResultMessage.Success);
	}

	/**
	 * 批量审批任务.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("batComplte")
	public void batComplte(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		ResultMessage resultMessage = null;
		String taskIds = RequestUtil.getString(request, "taskIds");
		String opinion = RequestUtil.getString(request, "opinion");
		try {
			processRunService.nextProcessBat(taskIds, opinion);
			String message = MessageUtil.getMessage();
			resultMessage = new ResultMessage(ResultMessage.Success, message);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail, str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}

	/**
	 * 检测任务是否存在。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("isTaskExsit")
	public void isTaskExsit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long taskId = RequestUtil.getLong(request, "taskId");
		PrintWriter out = response.getWriter();
		TaskEntity taskEnt = bpmService.getTask(taskId.toString());
		if (taskEnt == null) {
			writeResultMessage(out, "此任务已经完成!", ResultMessage.Fail);
		} else {
			writeResultMessage(out, "任务存在!", ResultMessage.Success);
		}
	}

	@RequestMapping("dialog")
	@Action(description = "编辑流程抄送转发")
	public ModelAndView forward(HttpServletRequest request) throws Exception {
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();
		return getAutoView().addObject("handlersMap", handlersMap);

	}

	@RequestMapping("toStartCommunicate")
	@Action(description = "编辑流程抄送转发")
	public ModelAndView toStartCommunicate(HttpServletRequest request) throws Exception {
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();
		return getAutoView().addObject("handlersMap", handlersMap);

	}

	@RequestMapping("toTransTo")
	@Action(description = "编辑流程抄送转发")
	public ModelAndView toTransTo(HttpServletRequest request) throws Exception {
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();
		return getAutoView().addObject("handlersMap", handlersMap);

	}

	@RequestMapping("transToOpinionDialog")
	@Action(description = "编辑流程抄送转发")
	public ModelAndView transToOpinionDialog(HttpServletRequest request) throws Exception {
		Map<String, IMessageHandler> handlersMap = ServiceUtil.getHandlerMap();
		return getAutoView().addObject("handlersMap", handlersMap);

	}

	@RequestMapping("selExecutors")
	@Action(description = "启动流程时可以配置下一个执行人获取第一个节点的配置")
	public ModelAndView selExecutors(HttpServletRequest request) throws Exception {
		String actDefId = RequestUtil.getString(request, "actDefId");
		List<FlowNode> firstNode = NodeCache.getFirstNode(actDefId);
		String scope = "";
		if (BeanUtils.isNotEmpty(firstNode)) {
			FlowNode flowNode = firstNode.get(0);
			String nodeId = flowNode.getNodeId();
			BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId, "");
			if (BeanUtils.isNotEmpty(bpmNodeSet))
				scope = bpmNodeSet.getScope();
		}
		return getAutoView().addObject("scope", scope);
	}

	@RequestMapping("opDialog")
	@Action(description = "填写意见")
	public ModelAndView opDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isRequired = RequestUtil.getString(request, "isRequired");
		String actDefId = RequestUtil.getString(request, "actDefId");
		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		// 获取常用语
		List<String> taskAppItems = taskAppItemService.getApprovalByDefKeyAndTypeId(bpmDefinition.getDefKey(), bpmDefinition.getTypeId());
		return getAutoView().addObject("isRequired", isRequired).addObject("taskAppItems", taskAppItems);
	}

	@RequestMapping("getMyTaskByRunId")
	@ResponseBody
	@Action(description = "根据流程runId获取流程任务")
	public String getMyTaskByRunId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String runId = RequestUtil.getString(request, "runId", "0");
		QueryFilter filter = new QueryFilter(request);
		filter.addFilter("runId", runId);
		List<TaskEntity> list = bpmService.getMyTasks(filter);
		if (list != null && list.size() > 0) {
			return list.get(0).getId();
		}
		return "0";
	}
}