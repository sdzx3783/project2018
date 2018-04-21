package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:工时填报申请 Model对象
 */
public class WorkHourApplication extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3191140281670165508L;
	//主键
	protected Long id;
	/**
	 *姓名ID
	 */
	protected String  applicantID;
	/**
	 *所属部门ID
	 */
	protected String  orgID;
	/**
	 *姓名
	 */
	protected String  applicant;
	/**
	 *所属部门
	 */
	protected String  org;
	/**
	 *日期
	 */
	protected java.util.Date  applicant_time;
	/**
	 *累计正常工时
	 */
	protected String  work_hour_total;
	/**
	 *累计加班工时
	 */
	protected String  overtime_hour_total;
	
	private String runStatus;
	
	private Long refRunid;
	
	private String bussinessDataId;
	/**
	 * 累计工时比例
	 */
	private Integer work_hour_rate;
	/**
	 * 工时比例基数
	 */
	private Integer basehour;
	
	private Integer isHadSubmit=0;
	
	
	public Integer getIsHadSubmit() {
		return isHadSubmit;
	}
	public void setIsHadSubmit(Integer isHadSubmit) {
		this.isHadSubmit = isHadSubmit;
	}
	public Integer getBasehour() {
		return basehour;
	}
	public void setBasehour(Integer basehour) {
		this.basehour = basehour;
	}
	public Integer getWork_hour_rate() {
		return work_hour_rate;
	}
	public void setWork_hour_rate(Integer work_hour_rate) {
		this.work_hour_rate = work_hour_rate;
	}
	public String getBussinessDataId() {
		return bussinessDataId;
	}
	public void setBussinessDataId(String bussinessDataId) {
		this.bussinessDataId = bussinessDataId;
	}

	/**
	 *工时填报申请列表
	 */
	protected List<ProjectTaskHour> projectTaskHourList=new ArrayList<ProjectTaskHour>();
	public Long getRefRunid() {
		return refRunid;
	}
	public void setRefRunid(Long refRunid) {
		this.refRunid = refRunid;
	}

	/**
	 *工时填报申请列表
	 */
	protected List<CustomTaskHour> customTaskHourList=new ArrayList<CustomTaskHour>();
	
	
	public String getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 姓名ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setOrgID(String orgID) 
	{
		this.orgID = orgID;
	}
	/**
	 * 返回 所属部门ID
	 * @return
	 */
	public String getOrgID() 
	{
		return this.orgID;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setOrg(String org) 
	{
		this.org = org;
	}
	/**
	 * 返回 所属部门
	 * @return
	 */
	public String getOrg() 
	{
		return this.org;
	}
	public void setApplicant_time(java.util.Date applicant_time) 
	{
		this.applicant_time = applicant_time;
	}
	/**
	 * 返回 日期
	 * @return
	 */
	public java.util.Date getApplicant_time() 
	{
		return this.applicant_time;
	}
	public void setWork_hour_total(String work_hour_total) 
	{
		this.work_hour_total = work_hour_total;
	}
	/**
	 * 返回 累计正常工时
	 * @return
	 */
	public String getWork_hour_total() 
	{
		return this.work_hour_total;
	}
	public void setOvertime_hour_total(String overtime_hour_total) 
	{
		this.overtime_hour_total = overtime_hour_total;
	}
	/**
	 * 返回 累计加班工时
	 * @return
	 */
	public String getOvertime_hour_total() 
	{
		return this.overtime_hour_total;
	}
	public void setProjectTaskHourList(List<ProjectTaskHour> projectTaskHourList) 
	{
		this.projectTaskHourList = projectTaskHourList;
	}
	/**
	 * 返回 工时填报申请列表
	 * @return
	 */
	public List<ProjectTaskHour> getProjectTaskHourList() 
	{
		return this.projectTaskHourList;
	}
	public void setCustomTaskHourList(List<CustomTaskHour> customTaskHourList) 
	{
		this.customTaskHourList = customTaskHourList;
	}
	/**
	 * 返回 工时填报申请列表
	 * @return
	 */
	public List<CustomTaskHour> getCustomTaskHourList() 
	{
		return this.customTaskHourList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof WorkHourApplication)) 
		{
			return false;
		}
		WorkHourApplication rhs = (WorkHourApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.orgID, rhs.orgID)
		.append(this.applicant, rhs.applicant)
		.append(this.org, rhs.org)
		.append(this.applicant_time, rhs.applicant_time)
		.append(this.work_hour_total, rhs.work_hour_total)
		.append(this.overtime_hour_total, rhs.overtime_hour_total)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.applicantID) 
		.append(this.orgID) 
		.append(this.applicant) 
		.append(this.org) 
		.append(this.applicant_time) 
		.append(this.work_hour_total) 
		.append(this.overtime_hour_total) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("applicantID", this.applicantID) 
		.append("orgID", this.orgID) 
		.append("applicant", this.applicant) 
		.append("org", this.org) 
		.append("applicant_time", this.applicant_time) 
		.append("work_hour_total", this.work_hour_total) 
		.append("overtime_hour_total", this.overtime_hour_total) 
		.toString();
	}

}