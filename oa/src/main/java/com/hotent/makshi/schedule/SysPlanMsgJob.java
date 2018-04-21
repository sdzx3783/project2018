package com.hotent.makshi.schedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hotent.core.util.AppUtil;
import com.hotent.makshi.dao.msgscheduler.SendMsgUserDao;
import com.hotent.makshi.model.msgscheduler.SchedulerParams;
import com.hotent.makshi.model.msgscheduler.SendMsgUser;
import com.hotent.makshi.service.msgscheduler.SchedulerParamsService;
import com.hotent.platform.model.system.MessageSend;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.TaskMessageService;
import com.hotent.platform.service.system.MessageSendService;
import com.hotent.platform.service.system.SysUserService;

/**
 * 自定义消息提醒
 * 
 * @author zami
 *
 */
public class SysPlanMsgJob implements Job {

	private static Logger logger = Logger.getLogger(SysPlanMsgJob.class);

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		String triggerName = jobExecutionContext.getTrigger().getKey().getName();

		SysUserService sysUserService = AppUtil.getBean(SysUserService.class);

		TaskMessageService taskMessageService = AppUtil.getBean(TaskMessageService.class);

		SchedulerParamsService schedulerParamsService = AppUtil.getBean(SchedulerParamsService.class);

		SendMsgUserDao sendMsgUserDao = AppUtil.getBean(SendMsgUserDao.class);

		MessageSendService msgSendService = AppUtil.getBean(MessageSendService.class);

		List<SchedulerParams> list = schedulerParamsService.getByTriggerName(triggerName);
		List<SendMsgUser> userList = new ArrayList<SendMsgUser>();
		if (list.size() > 0) {
			userList = sendMsgUserDao.getByMainId(list.get(0).getId());
			// 接收者
			SysUser sendUser = new SysUser();
			List<SysUser> receiverUserList = new ArrayList<SysUser>();
			for (SendMsgUser sendMsgUser : userList) {
				Long userId = sendMsgUser.getUserId();
				sendUser = sysUserService.getById(userId);
				receiverUserList.add(sendUser);
			}
			/*
			 * //消息方式 String informTypes = "3"; //标题 String title = "planMsgJob"; //内容 String content = triggerName;
			 * taskMessageService.sendMessage(sendUser,receiverUserList,informTypes,title,content);
			 */

			for (SysUser sysUser : receiverUserList) {
				MessageSend send = new MessageSend();
				send.setSubject("日程提醒");
				send.setMessageType("1");
				SysUser sendSysUser = new SysUser();
				sendSysUser.setUserId(0L);
				sendSysUser.setFullname("系统消息");
				try {
					msgSendService.addMessageSend(send, sendSysUser, sysUser.getUserId().toString(), "", "", "");
				} catch (Exception e) {
					logger.error("错误信息", e);
				}
			}
		} else {
			logger.error(("消息发送失败,triggerName:" + triggerName));
		}
	}
}
