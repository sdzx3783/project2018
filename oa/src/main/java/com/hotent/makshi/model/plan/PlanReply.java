package com.hotent.makshi.model.plan;

import java.util.Date;

public class PlanReply {
    private Integer id;

    private Integer planId;

    private String reply;

    private String cuser;

    private Date ctime;
    
    private Integer isdelete;

    
    public PlanReply(){
    	
    }
    
    public PlanReply(Integer planId, String reply, String cuser, Date ctime,
			Integer isdelete) {
		super();
		this.planId = planId;
		this.reply = reply;
		this.cuser = cuser;
		this.ctime = ctime;
		this.isdelete = isdelete;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
    
    
}