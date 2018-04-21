package com.hotent.makshi.model.title;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:个人执业印章申请 Model对象
 */
public class Practicesteal extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *持证人ID
	 */
	protected String  fullnameID;
	/**
	 *持证人
	 */
	protected String  fullname;
	/**
	 *员工编号
	 */
	protected String  user_id;
	/**
	 *工号
	 */
	protected String  gh;
	/**
	 *印章名称
	 */
	protected String  steal_name;
	/**
	 *工程名称
	 */
	protected String  project_name;
	/**
	 *申请事由
	 */
	protected String  reason;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *印章编号
	 */
	protected String  steal_num;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *申请人
	 */
	protected String  applicant;
	protected Long  runId=0L;
	/**
	 * 审批状态
	 * @return
	 */
	protected String status;
	protected Date appliDate;
	
	
	
	public Date getAppliDate() {
		return appliDate;
	}
	public void setAppliDate(Date appliDate) {
		this.appliDate = appliDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFullnameID(String fullnameID) 
	{
		this.fullnameID = fullnameID;
	}
	/**
	 * 返回 持证人ID
	 * @return
	 */
	public String getFullnameID() 
	{
		return this.fullnameID;
	}
	public void setFullname(String fullname) 
	{
		this.fullname = fullname;
	}
	/**
	 * 返回 持证人
	 * @return
	 */
	public String getFullname() 
	{
		return this.fullname;
	}
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
	}
	public void setGh(String gh) 
	{
		this.gh = gh;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getGh() 
	{
		return this.gh;
	}
	public void setSteal_name(String steal_name) 
	{
		this.steal_name = steal_name;
	}
	/**
	 * 返回 印章名称
	 * @return
	 */
	public String getSteal_name() 
	{
		return this.steal_name;
	}
	public void setProject_name(String project_name) 
	{
		this.project_name = project_name;
	}
	/**
	 * 返回 工程名称
	 * @return
	 */
	public String getProject_name() 
	{
		return this.project_name;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 申请事由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
	public void setSteal_num(String steal_num) 
	{
		this.steal_num = steal_num;
	}
	/**
	 * 返回 印章编号
	 * @return
	 */
	public String getSteal_num() 
	{
		return this.steal_num;
	}
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Practicesteal)) 
		{
			return false;
		}
		Practicesteal rhs = (Practicesteal) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fullnameID, rhs.fullnameID)
		.append(this.fullname, rhs.fullname)
		.append(this.user_id, rhs.user_id)
		.append(this.gh, rhs.gh)
		.append(this.steal_name, rhs.steal_name)
		.append(this.project_name, rhs.project_name)
		.append(this.reason, rhs.reason)
		.append(this.remark, rhs.remark)
		.append(this.steal_num, rhs.steal_num)
		.append(this.applicantID, rhs.applicantID)
		.append(this.applicant, rhs.applicant)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.fullnameID) 
		.append(this.fullname) 
		.append(this.user_id) 
		.append(this.gh) 
		.append(this.steal_name) 
		.append(this.project_name) 
		.append(this.reason) 
		.append(this.remark) 
		.append(this.steal_num) 
		.append(this.applicantID) 
		.append(this.applicant) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("fullnameID", this.fullnameID) 
		.append("fullname", this.fullname) 
		.append("user_id", this.user_id) 
		.append("gh", this.gh) 
		.append("steal_name", this.steal_name) 
		.append("project_name", this.project_name) 
		.append("reason", this.reason) 
		.append("remark", this.remark) 
		.append("steal_num", this.steal_num) 
		.append("applicantID", this.applicantID) 
		.append("applicant", this.applicant) 
		.toString();
	}

}