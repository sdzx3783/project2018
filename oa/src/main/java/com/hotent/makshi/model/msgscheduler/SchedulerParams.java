package com.hotent.makshi.model.msgscheduler;

import java.util.ArrayList;
import java.util.List;

public class SchedulerParams {
	
	private Long id;
	
	private String jobName;
	
	private String trigger;

	private List<SendMsgUser> sendMsgUserList = new ArrayList<SendMsgUser>();;
	
	public List<SendMsgUser> getSendMsgUserList() {
		return sendMsgUserList;
	}

	public void setSendMsgUserList(List<SendMsgUser> sendMsgUserList) {
		this.sendMsgUserList = sendMsgUserList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	
}
