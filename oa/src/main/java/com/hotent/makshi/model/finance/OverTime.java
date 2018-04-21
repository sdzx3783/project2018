package com.hotent.makshi.model.finance;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hotent.core.model.BaseModel;


public class OverTime extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1516917509737322152L;
	private Long id;
	private Date startTime;
	private Date endTime;
	private String userid;
	private String username;
	private int legalovertimes;//法定加班工时
	private String globalflowno;//工单号
	private Date applicationdate;
	
	
	public Date getApplicationdate() {
		return applicationdate;
	}
	public void setApplicationdate(Date applicationdate) {
		this.applicationdate = applicationdate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getLegalovertimes() {
		return legalovertimes;
	}
	public void setLegalovertimes(int legalovertimes) {
		this.legalovertimes = legalovertimes;
	}
	public String getGlobalflowno() {
		return globalflowno;
	}
	public void setGlobalflowno(String globalflowno) {
		this.globalflowno = globalflowno;
	}
	
}