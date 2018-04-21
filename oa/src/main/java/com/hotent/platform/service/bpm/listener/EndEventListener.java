package com.hotent.platform.service.bpm.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.common.IndiDireManager;
import com.hotent.makshi.service.fetcher.IFetcher;
import com.hotent.makshi.service.sms.SmsMsgRecordReadyService;
import com.hotent.platform.dao.bpm.BpmBusLinkDao;
import com.hotent.platform.dao.bpm.BpmDefinitionDao;
import com.hotent.platform.dao.bpm.BpmFormRunDao;
import com.hotent.platform.dao.bpm.TaskDao;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.event.def.ProcessEndEvent;
import com.hotent.platform.model.bpm.BpmBusLink;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysTemplate;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.msgutil.SmsUtil;
import com.hotent.platform.service.bpm.BpmProCopytoService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.TaskMessageService;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.service.system.SysTemplateService;

/**
 * 结束事件监听器。
 * 
 * @author ray
 *
 */
@Service
public class EndEventListener extends BaseNodeEventListener implements ApplicationContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(EndEventListener.class);

	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmFormRunDao bpmFormRunDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private BpmProCopytoService bpmProCopytoService;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysTemplateService sysTemplateService;
	@Resource
	private TaskMessageService taskMessageService;
	@Resource
	private BpmBusLinkDao bpmBusLinkDao;
	@Resource
	private BpmDefinitionDao bpmDefinitiondao;
	@Autowired
	private SmsMsgRecordReadyService smsMsgRecordReadyService;

	private ApplicationContext applicationContext;

	@Override
	protected void execute(DelegateExecution execution, String actDefId, String nodeId) {
		ExecutionEntity ent = (ExecutionEntity) execution;
		if (!ent.isEnded())
			return;

		// 当前的excutionId和主线程相同时。
		if (ent.getId().equals(ent.getProcessInstanceId()) && ent.getParentId() == null) {
			ProcessRun processRun = processRunService.getByActInstanceId(new Long(ent.getProcessInstanceId()));
			handEnd(ent, processRun);
			// 发布流程结束事件。
			ProcessEndEvent ev = new ProcessEndEvent(processRun);
			ev.setExecutionEntity(ent);
			// 发布流程结束事件。
			AppUtil.publishEvent(ev);
			String businessKey = processRun.getBusinessKey();// 流程中提交表单数据id
			String tableName = processRun.getTableName().toLowerCase();// 转小写
			if (IndiDireManager.tMap.containsKey(tableName)) {// 包含当前流程表的配置
				String fetcherName = IndiDireManager.tMap.get(tableName).getFetcher();
				IFetcher fetcher = (IFetcher) applicationContext.getBean(fetcherName);
				try {
					fetcher.fetcheData(tableName, businessKey);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

	private void handEnd(ExecutionEntity ent, ProcessRun processRun) {
		// 更新流程实例状态。
		updProcessRunStatus(processRun);
		// 删除知会任务。
		delNotifyTask(ent);

		// 更新业务中间表。
		updBusLink(processRun);
		// 发送操送
		copyTo(ent, processRun);

		// 发送短信
		finProSendSMS(processRun);

	}

	/**
	 * 抄送。
	 * 
	 * @param ent
	 * @param processRun
	 */
	private void copyTo(ExecutionEntity ent, ProcessRun processRun) {
		ProcessCmd processCmd = TaskThreadService.getProcessCmd();
		// 根据主键值获取流程定义
		BpmDefinition bpmDefinition = bpmDefinitiondao.getById(processRun.getDefId());
		// 获取流程变量
		Map<String, Object> vars = ent.getVariables();

		// 催办结束流程时候,processCmd 为空
		String currentUserId = processCmd == null ? "0" : processCmd.getCurrentUserId().toString();
		// 添加抄送任务以及发送提醒
		try {
			bpmProCopytoService.handlerCopyTask(processRun, vars, currentUserId, bpmDefinition);
			// 处理发送提醒消息给发起人
			if (StringUtil.isNotEmpty(bpmDefinition.getInformStart())) {
				handSendMsgToStartUser(processRun, bpmDefinition);
			}
		} catch (Exception e) {
			log.error("错误信息",e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 更新业务中间表数据状态。
	 * 
	 * @param processRun
	 */
	private void updBusLink(ProcessRun processRun) {
		Long businessKey = new Long(processRun.getBusinessKey());
		BpmBusLink bpmBusLink = new BpmBusLink();
		SysUser user = (SysUser) ContextUtil.getCurrentUser();
		if (user != null) {
			bpmBusLink.setBusUpdid(user.getUserId());
			bpmBusLink.setBusUpd(user.getFullname());
		}
		bpmBusLink.setBusStatus(BpmBusLink.BUS_STATUS_END);
		bpmBusLink.setBusUpdtime(new Date());
		bpmBusLink.setBusPk(businessKey);
		bpmBusLinkDao.updateStatus(bpmBusLink);
	}

	/**
	 * 处理发送提醒消息给发起人
	 * 
	 * @throws Exception
	 */
	private void handSendMsgToStartUser(ProcessRun processRun, BpmDefinition bpmDefinition) throws Exception {
		String informStart = bpmDefinition.getInformStart();
		if (StringUtil.isEmpty(informStart))
			return;

		String subject = processRun.getSubject();
		if (BeanUtils.isEmpty(processRun))
			return;
		Long startUserId = processRun.getCreatorId();
		SysUser user = sysUserDao.getById(startUserId);
		List<SysUser> receiverUserList = new ArrayList<SysUser>();
		receiverUserList.add(user);

		Map<String, String> msgTempMap = sysTemplateService.getTempByFun(SysTemplate.USE_TYPE_NOTIFY_STARTUSER);
		taskMessageService.sendMessage(null, receiverUserList, informStart, msgTempMap, subject, "", null, processRun.getRunId(), null);
	}

	/**
	 * 流程终止时删除流程任务。
	 * 
	 * <pre>
	 * 	1.删除流程实例任务。
	 *  2.删除任务的参与者。
	 *  3.删除流程表单运行情况
	 * </pre>
	 * 
	 * @param ent
	 */
	private void delNotifyTask(ExecutionEntity ent) {
		Long instanceId = new Long(ent.getProcessInstanceId());
		// 删除知会任务
		taskDao.delSubCustTaskByInstId(instanceId);
		// 删除流程表单运行情况
		bpmFormRunDao.delByInstanceId(String.valueOf(instanceId));
	}

	/**
	 * 更新流程运行状态。
	 * 
	 * <pre>
	 * 1.更新流程运行状态为完成。
	 * 2.计算流程过程的时间。
	 * </pre>
	 * 
	 * @param ent
	 */
	private void updProcessRunStatus(ProcessRun processRun) {
		if (BeanUtils.isEmpty(processRun))
			return;
		// 设置流程状态为完成。
		processRun.setStatus(ProcessRun.STATUS_FINISH);
		processRunService.update(processRun);
	}

	@Override
	protected Integer getScriptType() {
		return BpmConst.EndScript;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext = arg0;
	}

	/**
	 * 流程结束后给流程创建者发送短信
	 * 
	 * @param processRun
	 */
	private void finProSendSMS(ProcessRun processRun) {
		if (processRun == null) {
			return;
		}
		
		SysUser user = sysUserDao.getById(processRun.getCreatorId());
		if (user == null) {
			log.info("user is null , can not send sms");
			return;
		}
		if (user.getUserId() == 1) {
			log.info("no need to send sms to Admin:" + user.getUserId());
			return;
		}

		DateFormat dataFormat = new SimpleDateFormat(SmsUtil.TIME_PATTERN);

		String smsContent = user.getFullname() + "，您在OA系统提交的流程“" + processRun.getProcessName() + "——" + dataFormat.format(processRun.getCreatetime()) + "”已审批结束，请知悉。";

		// TEST 先不发短信。测试流程
		// log.info("测试流程结束，发短信：" + smsContent);

		// 2017-12-20 不发送短信，只存到特定的数据库，然后定时下发
		// SmsUtil.toSendSMSbyUser(user, smsService, smsContent);
		smsMsgRecordReadyService.add(user.getUserId(), user.getFullname(), user.getMobile(), smsContent);
	}

}
