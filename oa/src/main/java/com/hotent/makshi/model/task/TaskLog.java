package com.hotent.makshi.model.task;

import java.util.Date;

public class TaskLog {
    private Integer id;

    private Integer type;

    private String progress;

    private String remark;

    private String cuser;

    private String cuserid;

    private Date ctime;
    
    private Integer taskId;

    /**
     * 意见
     */
    private String opinion;

    /**
     * 0保存，1提交
     */
    private Integer commited;


    /**
     * 附件
     */
    private String attach;
    
    
    public TaskLog(){
    	
    }
    
    

    public TaskLog(Integer type, String progress, String remark, String cuser,
			String cuserid, Date ctime, Integer taskId) {
		super();
		this.type = type;
		this.progress = progress;
		this.remark = remark;
		this.cuser = cuser;
		this.cuserid = cuserid;
		this.ctime = ctime;
		this.taskId = taskId;
	}



	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid == null ? null : cuserid.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getCommited() {
        return commited;
    }

    public void setCommited(Integer commited) {
        this.commited = commited;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}