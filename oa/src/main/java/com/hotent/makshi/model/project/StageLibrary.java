package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class StageLibrary implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6781295957244566908L;

	private Integer stageno;

    private String stagename;

    private String createorg;

    private Long createorgid;//所属部门

    private String remark;

    private Date ctime;

    private Long cuser;

    private Date utime;

    private Long uuser;

    private Integer isdelete;

    private Integer tasknum;//附加字段 任务数
    
    private Map<Integer,Integer> orderMap;//任务排序字段集合
    
    
    public Integer getTasknum() {
		return tasknum;
	}

	public void setTasknum(Integer tasknum) {
		this.tasknum = tasknum;
	}

	public Map<Integer, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<Integer, Integer> orderMap) {
		this.orderMap = orderMap;
	}

	public Integer getStageno() {
        return stageno;
    }

    public void setStageno(Integer stageno) {
        this.stageno = stageno;
    }

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
    }

    public String getCreateorg() {
        return createorg;
    }

    public void setCreateorg(String createorg) {
        this.createorg = createorg;
    }

    public Long getCreateorgid() {
        return createorgid;
    }

    public void setCreateorgid(Long createorgid) {
        this.createorgid = createorgid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getCuser() {
        return cuser;
    }

    public void setCuser(Long cuser) {
        this.cuser = cuser;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Long getUuser() {
        return uuser;
    }

    public void setUuser(Long uuser) {
        this.uuser = uuser;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}