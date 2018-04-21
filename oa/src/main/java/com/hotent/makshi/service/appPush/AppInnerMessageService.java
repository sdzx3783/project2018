package com.hotent.makshi.service.appPush;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotent.core.jms.MessageProducer;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.appPush.AppPushJob;
import com.hotent.makshi.service.AppInnerMessageInterface;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.ProcessRunDao;
import com.hotent.platform.dao.form.BpmFormHandlerDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.NodeMsgTemplateService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskUserService;
import com.hotent.platform.service.form.BpmFormTableService;
import com.hotent.platform.service.system.SysTemplateService;

@Service
public class AppInnerMessageService implements AppInnerMessageInterface{
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
	
	@Transactional
	/**
	 * app在线的用户才发送
	 */
	public void addMyMessage(String pushType,Long orgId,Long userId,Long runId,Long taskId,String context,String url,String ticker,String title){
		QueryFilter queryFilter = new QueryFilter(new net.sf.json.JSONObject());
		queryFilter.addFilter("userId", userId);
		//app在线的用户才发送
		if(appPushUserService.getAll(queryFilter).size()>0){
			AppPushJob appPushJob = new AppPushJob();
			appPushJob.setUserId(userId);
			appPushJob.setRunId(runId);
			appPushJob.setTaskId(taskId);
			appPushJob.setOrgId(orgId);
			appPushJob.setPushType(pushType);
			appPushJob.setContent(context);
			appPushJob.setUrl(url);
			appPushJob.setTicker(ticker);
			appPushJob.setTitle(title);
			appPushJobService.add(appPushJob);
		}
	}
}
