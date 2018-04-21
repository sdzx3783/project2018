package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.Date;

public class ProjectTaskLogs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2922099184620656072L;

	private Long id;

	private Integer taskid;

	private Long runid;

	private Date ctime;
	
	private Integer type;
	
	private String projectName;//附加字段
	private String taskName;//附加字段
	private String submittor ;//附加字段 提交人
	private String status;//附加字段 任务状态
	private String uploadFile;//附加字段 任务上传文件json
	
	
	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getSubmittor() {
		return submittor;
	}

	public void setSubmittor(String submittor) {
		this.submittor = submittor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Long getRunid() {
		return runid;
	}

	public void setRunid(Long runid) {
		this.runid = runid;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}