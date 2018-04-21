package com.hotent.makshi.model.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plan {
    private Integer id;

    private Integer cycle;

    private Date date;

    private String dateRange;

    private String file;

    private String summary;

    private String cuser;

    private Date ctime;

    private String uuser;

    private Date utime;

    private String content;
    
    private Integer ispub;
    private Integer isdelete;
    
    private boolean isSummary;
    private boolean isReply;
    
    private List<PlanReply> replyList;
    
    private List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange == null ? null : dateRange.trim();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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

    public String getUuser() {
        return uuser;
    }

    public void setUuser(String uuser) {
        this.uuser = uuser == null ? null : uuser.trim();
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public List<PlanReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<PlanReply> replyList) {
		this.replyList = replyList;
	}

	public boolean getIsReply() {
		return isReply;
	}

	public void setIsReply(boolean isReply) {
		this.isReply = isReply;
	}

	public boolean getIsSummary() {
		if(this.summary==null || this.summary.equals("")){
			return false;
		}
		return true;
	}

	public void setIsSummary(boolean isSummary) {
		this.isSummary = isSummary;
	}

	public Integer getIspub() {
		return ispub;
	}

	public void setIspub(Integer ispub) {
		this.ispub = ispub;
	}

	public List<PlanDetail> getPlanDetailList() {
		return planDetailList;
	}

	public void setPlanDetailList(List<PlanDetail> planDetailList) {
		this.planDetailList = planDetailList;
	}

    
}