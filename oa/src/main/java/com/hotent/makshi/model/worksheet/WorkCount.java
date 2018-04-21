package com.hotent.makshi.model.worksheet;

import java.util.Date;


public class WorkCount {
	
	//主键
	protected Long id;
	/**
	 *姓名ID
	 */
	protected String  fullnameID;
	/**
	 *部门ID
	 */
	protected String  orgID;
	
	/**
	 *姓名
	 */
	protected String  fullname;
	
	/**
	 *部门
	 */
	protected String  org;
	
	/**
	 * 统计年份
	 */
	protected Integer year;
	
	/**
	 * 统计年月
	 */
	private Date createTime;
	
	/**
	 * 上班天数
	 */
	private Double workDay;
	
	/**
	 * 签到次数
	 */
	private Double clockCount;
	
	/**
	 * 补录次数
	 */
	private Double reClockCount;
	
	/**
	 * 缺勤次数
	 */
	private Double leaveCount;
	
	private String yearMonth;
	
	/**
	 * 统计时间
	 */
	private Date sdate;
	
	/*
	 * 未打卡次数
	 */
	private Double noneClockCount;

	public String getFullnameID() {
		return fullnameID;
	}

	public void setFullnameID(String fullnameID) {
		this.fullnameID = fullnameID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Double getClockCount() {
		return clockCount;
	}

	public void setClockCount(Double clockCount) {
		this.clockCount = clockCount;
	}

	public Double getReClockCount() {
		return reClockCount;
	}

	public void setReClockCount(Double reClockCount) {
		this.reClockCount = reClockCount;
	}

	public Double getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(Double leaveCount) {
		this.leaveCount = leaveCount;
	}

	public Double getNoneClockCount() {
		return noneClockCount;
	}

	public void setNoneClockCount(Double noneClockCount) {
		this.noneClockCount = noneClockCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getWorkDay() {
		return workDay;
	}

	public void setWorkDay(Double workDay) {
		this.workDay = workDay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}


	
	
}
