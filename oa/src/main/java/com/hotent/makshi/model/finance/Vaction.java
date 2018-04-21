package com.hotent.makshi.model.finance;

import java.util.Date;

import com.hotent.core.model.BaseModel;


public class Vaction extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -698273527089380843L;
	private Long id;
	private Date startTime;
	private Date endTime;
	private String leaveStartSolt;
	private String leaveEndSolt;
	private String startTimeView;
	private String endTimeView;
	private String userid;
	private String username;
	private String leavetype;
	private double leavedays;//请假天数
	private String globalflowno;
	public static final String YEARLEAVEKEY="年假";
	public static final String PERSONALLEAVEKEY="事假";
	public static final String SICKLEAVEKEY="病假";
	
	public double getLeavedays() {
		return leavedays;
	}
	public void setLeavedays(double leavedays) {
		this.leavedays = leavedays;
	}
	public String getGlobalflowno() {
		return globalflowno;
	}
	public void setGlobalflowno(String globalflowno) {
		this.globalflowno = globalflowno;
	}
	public String getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
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
	public String getStartTimeView() {
		return startTimeView;
	}
	public void setStartTimeView(String startTimeView) {
		this.startTimeView = startTimeView;
	}
	public String getEndTimeView() {
		return endTimeView;
	}
	public void setEndTimeView(String endTimeView) {
		this.endTimeView = endTimeView;
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
	public String getLeaveStartSolt() {
		return leaveStartSolt;
	}
	public void setLeaveStartSolt(String leaveStartSolt) {
		this.leaveStartSolt = leaveStartSolt;
	}
	public String getLeaveEndSolt() {
		return leaveEndSolt;
	}
	public void setLeaveEndSolt(String leaveEndSolt) {
		this.leaveEndSolt = leaveEndSolt;
	}
	
}