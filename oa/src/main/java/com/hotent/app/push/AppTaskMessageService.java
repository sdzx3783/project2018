package com.hotent.app.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Service;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.jms.MessageProducer;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.appPush.AppPushJob;
import com.hotent.makshi.model.appPush.AppPushJobHistory;
import com.hotent.makshi.service.appPush.AppPushJobHistoryService;
import com.hotent.makshi.service.appPush.AppPushJobService;
import com.hotent.makshi.service.appPush.AppPushUserService;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.bpm.TaskOpinion;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.NodeMsgTemplateService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.SysTemplateService;
import com.hotent.platform.service.util.ServiceUtil;

@Service
public class AppTaskMessageService {
	@Resource
	private MessageProducer messageProducer;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private TaskUserService taskUserService;
	@Resource
	private SysTemplateService sysTemplateService;
	@Resource
	private BpmDefinitionDao bpmDefinitionDao;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private NodeMsgTemplateService nodeMsgTemplateService;
	@Resource
	private BpmFormHandlerDao bpmFormHandlerDao;
	@Resource
	private ProcessRunDao dao;
	@Resource
	private BpmFormTableService bpmFormTableService;
	@Resource
	private AppPushJobService appPushJobService;
	@Resource
	private AppPushJobHistoryService appPushJobHistoryService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private AppPushUserService appPushUserService;
	
	public void setMessageProducer(MessageProducer producer) {
		this.messageProducer = producer;
	}

	private void pushUser(Map<SysUser, List<Task>> users, SysUser user, Task task) {
		if (users.containsKey(user)) {
			users.get(user).add(task);
		} else {
			List<Task> list = new ArrayList<Task>();
			list.add(task);
			users.put(user, list);
		}
	}

	/**
	 * 保存推送消息
	 */
	public void notify(List<Task> taskList, String informTypes, String subject, Map<String, String> map, String opinion, String parentActDefId,Long runId) throws Exception {
		// 通知类型为空。
		if (taskList == null){
			AppPushJob appPushJob = appPushJobService.getByRunId(runId);
			if(null!=appPushJob){
				setPushJobHis(appPushJob.getUserId(), new AppPushJobHistory(), appPushJob);
				appPushJobService.delById(appPushJob.getId());
			}
			return;
		}
		//遍历任务将用户和任务对应上。
		for (Task task : taskList) {
			String actDefId = task.getProcessDefinitionId();
			String nodeId = task.getTaskDefinitionKey();
			BpmDefinition bpmDefinition = bpmDefinitionDao.getByActDefId(actDefId);
			BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId, parentActDefId);
			
			if (StringUtil.isEmpty(informTypes)) {
				if (StringUtil.isNotEmpty(bpmNodeSet.getInformType())) {
					informTypes = bpmNodeSet.getInformType();
				} else {
					informTypes = bpmDefinition.getInformType();
				}
			}
			if (StringUtil.isEmpty(informTypes)) 	continue;
			String assignee = task.getAssignee();// 执行人
			// 存在指定的用户
			if (ServiceUtil.isAssigneeNotEmpty(assignee)) {
				SysUser user = sysUserDao.getById(Long.parseLong(assignee));
				sendMsg(task, map, user, informTypes, parentActDefId, bpmNodeSet, subject, opinion);
			}
			// 获取该任务的候选用户列表
			else {
				Set<SysUser> cUIds = taskUserService.getCandidateUsers(task.getId());
				for (SysUser user : cUIds) {
					sendMsg(task, map, user, informTypes, parentActDefId, bpmNodeSet, subject, opinion);
				}
			}
		}

	}

	private void sendMsg(Task task, Map<String, String> map, SysUser user, String informTypes, String parentActDefId, BpmNodeSet bpmNodeSet, String subject, String opinion) throws Exception {
		ISysUser curUser =  ContextUtil.getCurrentUser();
		// 是否为代理任务(代理任务发送消息给任务所属人)
		boolean isAgentTask = TaskOpinion.STATUS_AGENT.toString().equals(task.getDescription());
		if (map == null) {
			if (isAgentTask) {
				map = sysTemplateService.getTempByFun(SysTemplate.USE_TYPE_NOTIFYOWNER_AGENT);
			} else {
				map = sysTemplateService.getDefaultTemp();
			}
		}
		if (isAgentTask) {
			user = sysUserDao.getById(Long.parseLong(task.getOwner()));
		}
		// informTypes形如逗号分割字符串：1,2,3
		String infoTypeArray[] = informTypes.split(",");
		String instanceId = task.getProcessInstanceId();
		ProcessRun processRun = dao.getByActInstanceId(new Long(instanceId));
		for (String infoType : infoTypeArray) {
			if(StringUtil.isNotEmpty(infoType)&&("7").equals(infoType)){
				AppPushJob appPushJob = new AppPushJob();
				appPushJob.setUserId(user.getUserId());
				AppPushJobHistory appPushJobHistory = new AppPushJobHistory();
				//判断runId是否存在
				AppPushJob appPushJobExist = appPushJobService.getByRunId(processRun.getRunId());
				appPushJob.setRunId(processRun.getRunId());
				appPushJob.setTaskId(Long.parseLong(task.getId()));
				appPushJob.setPushType("0");
				appPushJob.setOrgId(null);
				appPushJob.setTicker("您有新的代办消息");
				appPushJob.setTitle(processRun.getProcessName()+"-"+processRun.getCreator());
				appPushJob.setContent(task.getName()+"-"+DateUtil.formatDate(processRun.getCreatetime(),"yyyy-MM-dd hh:mm:ss"));
				appPushJob.setUrl("{\"url\":\"oaapp_minetodo:\"}");
				if(null!=appPushJobExist){
					setPushJobHis(user.getUserId(), appPushJobHistory, appPushJobExist);
					appPushJob.setId(appPushJobExist.getId());
					appPushJobService.update(appPushJob);
				}else{
					appPushJobService.add(appPushJob );
				};
			}
		}
	}

	private void setPushJobHis(Long userId, AppPushJobHistory appPushJobHistory, AppPushJob appPushJobExist) {
		appPushJobHistory.setUserId(userId);
		appPushJobHistory.setResetCount(1L);
		appPushJobHistory.setTicker(appPushJobExist.getTicker());
		appPushJobHistory.setTitle(appPushJobExist.getTitle());
		appPushJobHistory.setContent(appPushJobExist.getContent());
		appPushJobHistory.setJobId(appPushJobExist.getId());
		appPushJobHistory.setPushType("0");
		appPushJobHistory.setOrgId(null);
		appPushJobHistory.setRunId(appPushJobExist.getRunId());
		appPushJobHistory.setTaskId(appPushJobExist.getTaskId());
		appPushJobHistory.setUrl(appPushJobExist.getUrl());
		appPushJobHistory.setUserId(appPushJobExist.getUserId());
		appPushJobHistory.setAppType("1");
		appPushJobHistory.setError_code(-1L);
		appPushJobHistory.setAppKey("");
		appPushJobHistory.setAppMastersecret("");
		appPushJobHistoryService.add(appPushJobHistory);
	}

}
