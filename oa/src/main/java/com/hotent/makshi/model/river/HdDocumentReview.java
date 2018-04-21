package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:文件 Model对象
 */
public class HdDocumentReview extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *项目部ID
	 */
	protected String  departmentID;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *项目负责人ID
	 */
	protected String  project_leaderID;
	/**
	 *部门负责人ID
	 */
	protected String  department_headsID;
	/**
	 *总工办/分管领导ID
	 */
	protected String  finalCheckerID;
	/**
	 *文件名称
	 */
	protected String  file_name;
	/**
	 *编号
	 */
	protected String  number;
	/**
	 *项目部
	 */
	protected String  department;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请时间
	 */
	protected java.util.Date  date;
	/**
	 *审批类型
	 */
	protected String  type;
	/**
	 *紧急程度
	 */
	protected String  level;
	/**
	 *内容摘要
	 */
	protected String  content;
	/**
	 *申请意见
	 */
	protected String  opinion;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *项目负责人
	 */
	protected String  project_leader;
	/**
	 *部门负责人
	 */
	protected String  department_heads;
	/**
	 *是否需要审定
	 */
	protected String  isNeedCheck;
	/**
	 *总工办/分管领导
	 */
	protected String  finalChecker;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 项目部ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
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
	public void setProject_leaderID(String project_leaderID) 
	{
		this.project_leaderID = project_leaderID;
	}
	/**
	 * 返回 项目负责人ID
	 * @return
	 */
	public String getProject_leaderID() 
	{
		return this.project_leaderID;
	}
	public void setDepartment_headsID(String department_headsID) 
	{
		this.department_headsID = department_headsID;
	}
	/**
	 * 返回 部门负责人ID
	 * @return
	 */
	public String getDepartment_headsID() 
	{
		return this.department_headsID;
	}
	public void setFinalCheckerID(String finalCheckerID) 
	{
		this.finalCheckerID = finalCheckerID;
	}
	/**
	 * 返回 总工办/分管领导ID
	 * @return
	 */
	public String getFinalCheckerID() 
	{
		return this.finalCheckerID;
	}
	public void setFile_name(String file_name) 
	{
		this.file_name = file_name;
	}
	/**
	 * 返回 文件名称
	 * @return
	 */
	public String getFile_name() 
	{
		return this.file_name;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}
	/**
	 * 返回 编号
	 * @return
	 */
	public String getNumber() 
	{
		return this.number;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 项目部
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
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
	public void setDate(java.util.Date date) 
	{
		this.date = date;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getDate() 
	{
		return this.date;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 审批类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setLevel(String level) 
	{
		this.level = level;
	}
	/**
	 * 返回 紧急程度
	 * @return
	 */
	public String getLevel() 
	{
		return this.level;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 内容摘要
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setOpinion(String opinion) 
	{
		this.opinion = opinion;
	}
	/**
	 * 返回 申请意见
	 * @return
	 */
	public String getOpinion() 
	{
		return this.opinion;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setProject_leader(String project_leader) 
	{
		this.project_leader = project_leader;
	}
	/**
	 * 返回 项目负责人
	 * @return
	 */
	public String getProject_leader() 
	{
		return this.project_leader;
	}
	public void setDepartment_heads(String department_heads) 
	{
		this.department_heads = department_heads;
	}
	/**
	 * 返回 部门负责人
	 * @return
	 */
	public String getDepartment_heads() 
	{
		return this.department_heads;
	}
	public void setIsNeedCheck(String isNeedCheck) 
	{
		this.isNeedCheck = isNeedCheck;
	}
	/**
	 * 返回 是否需要审定
	 * @return
	 */
	public String getIsNeedCheck() 
	{
		return this.isNeedCheck;
	}
	public void setFinalChecker(String finalChecker) 
	{
		this.finalChecker = finalChecker;
	}
	/**
	 * 返回 总工办/分管领导
	 * @return
	 */
	public String getFinalChecker() 
	{
		return this.finalChecker;
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
		if (!(object instanceof HdDocumentReview)) 
		{
			return false;
		}
		HdDocumentReview rhs = (HdDocumentReview) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.departmentID, rhs.departmentID)
		.append(this.applicantID, rhs.applicantID)
		.append(this.project_leaderID, rhs.project_leaderID)
		.append(this.department_headsID, rhs.department_headsID)
		.append(this.finalCheckerID, rhs.finalCheckerID)
		.append(this.file_name, rhs.file_name)
		.append(this.number, rhs.number)
		.append(this.department, rhs.department)
		.append(this.applicant, rhs.applicant)
		.append(this.date, rhs.date)
		.append(this.type, rhs.type)
		.append(this.level, rhs.level)
		.append(this.content, rhs.content)
		.append(this.opinion, rhs.opinion)
		.append(this.attachment, rhs.attachment)
		.append(this.project_leader, rhs.project_leader)
		.append(this.department_heads, rhs.department_heads)
		.append(this.isNeedCheck, rhs.isNeedCheck)
		.append(this.finalChecker, rhs.finalChecker)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.departmentID) 
		.append(this.applicantID) 
		.append(this.project_leaderID) 
		.append(this.department_headsID) 
		.append(this.finalCheckerID) 
		.append(this.file_name) 
		.append(this.number) 
		.append(this.department) 
		.append(this.applicant) 
		.append(this.date) 
		.append(this.type) 
		.append(this.level) 
		.append(this.content) 
		.append(this.opinion) 
		.append(this.attachment) 
		.append(this.project_leader) 
		.append(this.department_heads) 
		.append(this.isNeedCheck) 
		.append(this.finalChecker) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("departmentID", this.departmentID) 
		.append("applicantID", this.applicantID) 
		.append("project_leaderID", this.project_leaderID) 
		.append("department_headsID", this.department_headsID) 
		.append("finalCheckerID", this.finalCheckerID) 
		.append("file_name", this.file_name) 
		.append("number", this.number) 
		.append("department", this.department) 
		.append("applicant", this.applicant) 
		.append("date", this.date) 
		.append("type", this.type) 
		.append("level", this.level) 
		.append("content", this.content) 
		.append("opinion", this.opinion) 
		.append("attachment", this.attachment) 
		.append("project_leader", this.project_leader) 
		.append("department_heads", this.department_heads) 
		.append("isNeedCheck", this.isNeedCheck) 
		.append("finalChecker", this.finalChecker) 
		.toString();
	}

}