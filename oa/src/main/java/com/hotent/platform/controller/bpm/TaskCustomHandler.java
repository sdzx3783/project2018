/*package com.hotent.platform.controller.bpm;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.activiti.engine.ActivitiInclusiveGateWayException;
import org.activiti.engine.ActivitiVarNotFoundException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.api.bpm.IBpmDefinitionService;
import com.hotent.core.api.bpm.model.IBpmDefinition;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormTable;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmDefinitionService;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmNodeSqlService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.form.BpmFormDefService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.SysErrorLogService;
import com.hotent.platform.service.util.ServiceUtil;

@Controller
@RequestMapping("/makshi/bpm/task/")
@Action(ownermodel = SysAuditModelType.PROCESS_MANAGEMENT)
public class TaskCustomHandler {
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
	private SysErrorLogService sysErrorLogService;
	@Resource
	private BpmDefinitionDao bpmDefinitionDao;
	@Resource
	private ProcessRunDao runDao;
	@Resource
	private BpmFormDefService bpmFormDefService;
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;
	@RequestMapping("complete")
	public @ResponseBody ResultMessage complete(@QueryParam("taskId") String taskId) throws Exception {

		logger.debug("任务完成跳转....");

		SysUser curUser = (SysUser) ContextUtil.getCurrentUser();

		ResultMessage resultMessage = null;
		TaskEntity task = bpmService.getTask(taskId);
		
		if (task == null) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "此任务已经执行了!");
			return resultMessage;
		}
		String actDefId = task.getProcessDefinitionId();
		BpmDefinition bpmDefinition = bpmDefinitionService.getByActDefId(actDefId);
		if (BpmDefinition.STATUS_INST_DISABLED.equals(bpmDefinition.getStatus())) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "流程实例已经禁止，该任务不能办理！");
			return resultMessage;
		}
		Long userId = curUser.getUserId();

		//ProcessCmd taskCmd = BpmUtil.getProcessCmd(request);
		ProcessCmd taskCmd = getProcessCmd(taskId,"1",false);
		
		taskCmd.setCurrentUserId(userId.toString());

		String assignee = task.getAssignee();
		// 非管理员,并且没有任务的权限。
		boolean isAdmin = taskCmd.getIsManage().shortValue() == 1;
		if (!isAdmin) {
			boolean rtn = processRunService.getHasRightsByTask(new Long(taskId), userId);
			if (!rtn) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "对不起,你不是这个任务的执行人,不能处理此任务!");
				return resultMessage;
			}
		}

		// 记录日志。
		logger.info(taskCmd.toString());
		if (ServiceUtil.isAssigneeNotEmpty(assignee) && !task.getAssignee().equals(userId.toString()) && !isAdmin) {
			resultMessage = new ResultMessage(ResultMessage.Fail, "该任务已被其他人锁定");
		} else {
			try {
				processRunService.nextProcess(taskCmd);
				resultMessage = new ResultMessage(ResultMessage.Success, "任务成功完成!");

			} catch (ActivitiVarNotFoundException ex) {
				resultMessage = new ResultMessage(ResultMessage.Fail, "请检查变量是否存在:" + ex.getMessage());
				logger.error(ex.getMessage());
			} catch (ActivitiInclusiveGateWayException ex) {
				resultMessage = new ResultMessage(ResultMessage.Fail, ex.getMessage());
				logger.error(ex.getMessage());
			} catch (Exception ex) {
				log.error("错误信息",ex);
				logger.error(ex.getMessage());
			}
		}
		return resultMessage;
	}
	private ProcessCmd getProcessCmd(String taskId,String opType,boolean isMobile) throws Exception {
		ProcessCmd cmd = new ProcessCmd();
		cmd.setTaskId(taskId);
		temp = request.getParameter("formData");
		if (StringUtil.isNotEmpty(temp)) {
		  cmd.setFormData(temp);
		}
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String instanceId = taskEntity.getProcessInstanceId();
		ProcessRun processRun = runDao.getByActInstanceId(new Long(instanceId));
		cmd.setProcessRun(processRun);
		BpmFormDef bpmFormDef = bpmFormDefService.getById(processRun.getFormDefId());
		BpmFormTable bpmFormTable=bpmFormTableService.getTableById(bpmFormDef.getTableId(),1);
		BpmFormData data = bpmFormHandlerDao.getByKey(bpmFormTable, processRun.getBusinessKey(), false);
		// 把主表数据放到cmd中,主要为了待会抛出的Nodesql事件
		BpmNodeSqlService.handleData(cmd, data);
		
		Map<String,Object> formDataMap=new HashMap<>();
		cmd.setFormDataMap(formDataMap);
		cmd.addTransientVar("bpm_definition", null);
		String back="0";
		if (StringUtil.isNotEmpty(back)) {
		  Integer rtn = Integer.valueOf(Integer.parseInt(back));
		  cmd.setBack(rtn);
		}
		String voteContent="同意";
		cmd.setVoteContent(voteContent);
		cmd.setVoteAgree(new Short(opType));
		if (isMobile) {
		  cmd.setFromMobile(true);
		}
		return cmd;
	}
}
*/