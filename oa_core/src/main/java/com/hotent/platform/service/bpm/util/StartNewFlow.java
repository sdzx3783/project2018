package com.hotent.platform.service.bpm.util;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.WorkFlowException;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.ProcessRunService;

import dm.jdbc.dbaccess.Auth;

/**
 * 触发新流程线程类
 * @author miao
 * 
 */
@Component @Scope("prototype")
public class StartNewFlow  implements Runnable  {
    private static Logger logger = LoggerFactory.getLogger(StartNewFlow.class);
    private CountDownLatch latch; 
    private Exception exception;
    private ProcessRun processRun;
	@Resource
	ProcessRunService processRunService;
	
	ProcessCmd cmd ;
	SysUser user ;


	@Override
	public void run() {
		if(cmd == null || user == null)  throw new WorkFlowException("启动新流程失败！ new flow Cmd or starUser cannot be null");
		try {
			ContextUtil.setCurrentUser((ISysUser) user);
			ProcessRun run = processRunService.startProcess(cmd);
			logger.debug("新流程启动成功！ runId:" + run.getRunId());
			latch.countDown();
		} catch (Exception e) {
			this.exception = e;
			latch.countDown();
			logger.error("启动新流程流程失败！"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	

	public void setCmd(ProcessCmd cmd) {
		this.cmd = cmd;
	}
	
	public void setUser(SysUser user) {
		this.user = user;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}





	public Exception getException() {
		return exception;
	}





	public void setException(Exception exception) {
		this.exception = exception;
	}





	public ProcessRun getProcessRun() {
		return processRun;
	}


}