package com.hotent.makshi.model.worksheet;

import java.util.Date;

public class AnnualVacation {
	/**
	 * 年份
	 */
	protected String year;
	/**
	 *姓名ID
	 */
	protected String  fullnameID;
	
	/**
	 *姓名
	 */
	protected String  fullname;
	
	/**
	 *部门ID
	 */
	protected String  orgID;
	
	/**
	 *部门
	 */
	protected String  org;
	
	/**
	 * 开始工作时间
	 */
	private Date startWorkTime;
	
	/**
	 * 年假
	 */
	protected Double annualVacation;
	
	/**
	 * 已使用年假
	 */
	protected Double usedAnnualVacation;
	
	/**
	 * 剩余年假
	 */
	protected Double restAnnualVacation;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFullnameID() {
		return fullnameID;
	}

	public void setFullnameID(String fullnameID) {
		this.fullnameID = fullnameID;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Double getAnnualVacation() {
		return annualVacation;
	}

	public void setAnnualVacation(Double annualVacation) {
		this.annualVacation = annualVacation;
	}

	public Date getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(Date startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public Double getUsedAnnualVacation() {
		return usedAnnualVacation;
	}

	public void setUsedAnnualVacation(Double usedAnnualVacation) {
		this.usedAnnualVacation = usedAnnualVacation;
	}

	public Double getRestAnnualVacation() {
		return restAnnualVacation;
	}

	public void setRestAnnualVacation(Double restAnnualVacation) {
		this.restAnnualVacation = restAnnualVacation;
	}
	
	
}
