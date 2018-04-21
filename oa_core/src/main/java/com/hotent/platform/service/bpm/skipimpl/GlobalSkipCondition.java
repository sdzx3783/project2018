package com.hotent.platform.service.bpm.skipimpl;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;

import com.hotent.core.api.org.ISysUserService;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.bpm.setting.ISkipCondition;
import com.hotent.platform.service.bpm.thread.TaskThreadService;


public class GlobalSkipCondition implements ISkipCondition {
	
	@Resource 
	ISysUserService sysUserServiceImpl;

	@Override
	public boolean canSkip(Task task) {
		String assignee=task.getAssignee();
		
		ISysUser user= sysUserServiceImpl.getById(new Long(assignee));
		
		ProcessCmd cmd=TaskThreadService.getProcessCmd();
		cmd.addTransientVar("appproveUser", user);
		return true;
	}

	@Override
	public String getTitle() {
		return "全部跳过";
	}

	@Override
	public ISysUser getExecutor() {
		ProcessCmd cmd=TaskThreadService.getProcessCmd();
		ISysUser user =(ISysUser) cmd.getTransientVar("appproveUser");
		return user;
	}

	

	
}
