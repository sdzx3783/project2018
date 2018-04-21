package com.hotent.makshi.model.finance;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hotent.core.model.BaseModel;
import com.hotent.makshi.utils.DateUtils;

public class WorkCount extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2402921760581405122L;
	private String userid;
	private String username;
	private String account;
	private String orgid;
	private String orgname;
	private String path;
	private String userstatus;//实习状态
	private double workcount;//考勤
	private String workdaystr;//填考勤的所有日期（逗号分隔）
	private Integer isleader;
	/*private Integer ischarge;*/
	private Integer isnew;
	private double workDays;//工作日
	private Date entrydate;
	private double overdays;
	
	public String getWorkdaystr() {
		return workdaystr;
	}
	public void setWorkdaystr(String workdaystr) {
		this.workdaystr = workdaystr;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	public double getWorkDays() {
		return workDays;
	}
	public void setWorkDays(double workDays) {
		this.workDays = workDays;
	}
	public Integer getIsnew() {
		return isnew;
	}
	public void setIsnew(Integer isnew) {
		this.isnew = isnew;
	}
	private String remark;

	private double yearleave;//年假
	private double personalleave;//事假
	private double sickleave;//病假
	private double otherleave;//婚假、产假、丧假等（天）
	private double absence;//缺勤
	private int overhours;//加班小时
	private double allvactions;//请假总数
	
	private String overtimeglobalnoStr;//加班流程工单号 逗号分隔
	private String vactionglobalnoStr;//请假流程工单号 逗号分隔
	
	
	public double getOverdays() {
		return overdays;
	}
	public void setOverdays(double overdays) {
		this.overdays = overdays;
	}
	public double getAllvactions() {
		return yearleave+personalleave+sickleave+otherleave;
	}
	public void setAllvactions(double allvactions) {
		this.allvactions = allvactions;
	}
	public String getOvertimeglobalnoStr() {
		return overtimeglobalnoStr;
	}
	public void setOvertimeglobalnoStr(String overtimeglobalnoStr) {
		this.overtimeglobalnoStr = overtimeglobalnoStr;
	}
	public String getVactionglobalnoStr() {
		return vactionglobalnoStr;
	}
	public void setVactionglobalnoStr(String vactionglobalnoStr) {
		this.vactionglobalnoStr = vactionglobalnoStr;
	}
	public int getOverhours() {
		return overhours;
	}
	public void setOverhours(int overhours) {
		this.overhours = overhours;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public double getWorkcount() {
		return workcount;
	}
	public void setWorkcount(double workcount) {
		this.workcount = workcount;
	}
	public Integer getIsleader() {
		return isleader;
	}
	public void setIsleader(Integer isleader) {
		this.isleader = isleader;
	}
	/*public Integer getIscharge() {
		return ischarge;
	}
	public void setIscharge(Integer ischarge) {
		this.ischarge = ischarge;
	}*/
	public String getRemark() {
		String temp="";
		if(StringUtils.isNotEmpty(vactionglobalnoStr)){
			temp+="请假工单号：（"+vactionglobalnoStr+"）";
		}
		if(StringUtils.isNotEmpty(overtimeglobalnoStr)){
			temp+=" 加班工单号：（"+overtimeglobalnoStr+"）";
		}
		if(isnew==1 && ("实习".equals(userstatus) || "试用员工".equals(userstatus))){
			temp+=" "+userstatus+" 入职日期："+DateUtils.formatDateS(entrydate);
		}
		return temp;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getYearleave() {
		return yearleave;
	}
	public void setYearleave(double yearleave) {
		this.yearleave = yearleave;
	}
	public double getPersonalleave() {
		return personalleave;
	}
	public void setPersonalleave(double personalleave) {
		this.personalleave = personalleave;
	}
	public double getSickleave() {
		return sickleave;
	}
	public void setSickleave(double sickleave) {
		this.sickleave = sickleave;
	}
	public double getOtherleave() {
		return otherleave;
	}
	public void setOtherleave(double otherleave) {
		this.otherleave = otherleave;
	}
	public double getAbsence() {
		if(absence<0){
			absence=0;
		}
		return absence;
	}
	public void setAbsence(double absence) {
		this.absence = absence;
	}
	
	
}
