package com.hotent.platform.event.listener;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.platform.event.def.EventUtil;
import com.hotent.platform.event.def.ProcessEndEvent;
import com.hotent.platform.model.bpm.BpmNodeSql;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.thread.TaskThreadService;

/**
 * 流程结束监听事件。
 * 
 * @author ray
 * 
 */
@Service
public class ProcessEndEventListener implements ApplicationListener<ProcessEndEvent>, Ordered {

	@Override
	public void onApplicationEvent(ProcessEndEvent event) {
		ProcessRun processRun = (ProcessRun) event.getSource();
		ExecutionEntity ent = event.getExecutionEntity();
		// 更新业务数据。
		
		ProcessCmd processCmd = TaskThreadService.getProcessCmd();
		//如果定时计划结束流程,没有mainDate和processCmd 就不执行节点sql 了
		if(processCmd == null)return ;
		// 抛出nodesql事件 
		String actdefId = processCmd.getActDefId();
		String nodeId=event.getExecutionEntity().getActivityId();
		EventUtil.publishNodeSqlEvent(actdefId, nodeId, BpmNodeSql.ACTION_END, (Map) processCmd.getTransientVar("mainData"));
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
