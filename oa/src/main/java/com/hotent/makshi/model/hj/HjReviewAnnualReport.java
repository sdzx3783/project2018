package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:jgndbgsh Model对象
 */
public class HjReviewAnnualReport extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *提交人ID
	 */
	protected String  nameID;
	/**
	 *所属部门ID
	 */
	protected String  departmentID;
	/**
	 *项目负责人审核ID
	 */
	protected String  Project_leaderID;
	/**
	 *部门副经理审核ID
	 */
	protected String  deputy_managerID;
	/**
	 *提交人
	 */
	protected String  name;
	/**
	 *提交日期
	 */
	protected java.util.Date  date;
	/**
	 *所属部门
	 */
	protected String  department;
	/**
	 *项目名称
	 */
	protected String  project_name;
	/**
	 *年报名称
	 */
	protected String  n_name;
	/**
	 *年报内容
	 */
	protected String  content;
	/**
	 *备注
	 */
	protected String  note;
	/**
	 *附件
	 */
	protected String  enclosure;
	/**
	 *项目负责人审核
	 */
	protected String  project_leader;
	/**
	 *部门副经理审核
	 */
	protected String  deputy_manager;
	/**
	 *项目任务id
	 */
	protected String  projectTaskId;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNameID(String nameID) 
	{
		this.nameID = nameID;
	}
	/**
	 * 返回 提交人ID
	 * @return
	 */
	public String getNameID() 
	{
		return this.nameID;
	}
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 所属部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setProject_leaderID(String Project_leaderID) 
	{
		this.Project_leaderID = Project_leaderID;
	}
	/**
	 * 返回 项目负责人审核ID
	 * @return
	 */
	public String getProject_leaderID() 
	{
		return this.Project_leaderID;
	}
	public void setDeputy_managerID(String deputy_managerID) 
	{
		this.deputy_managerID = deputy_managerID;
	}
	/**
	 * 返回 部门副经理审核ID
	 * @return
	 */
	public String getDeputy_managerID() 
	{
		return this.deputy_managerID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 提交人
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setDate(java.util.Date date) 
	{
		this.date = date;
	}
	/**
	 * 返回 提交日期
	 * @return
	 */
	public java.util.Date getDate() 
	{
		return this.date;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 所属部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setProject_name(String project_name) 
	{
		this.project_name = project_name;
	}
	/**
	 * 返回 项目名称
	 * @return
	 */
	public String getProject_name() 
	{
		return this.project_name;
	}
	public void setN_name(String n_name) 
	{
		this.n_name = n_name;
	}
	/**
	 * 返回 年报名称
	 * @return
	 */
	public String getN_name() 
	{
		return this.n_name;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 年报内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setNote(String note) 
	{
		this.note = note;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getNote() 
	{
		return this.note;
	}
	public void setEnclosure(String enclosure) 
	{
		this.enclosure = enclosure;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getEnclosure() 
	{
		return this.enclosure;
	}
	public void setProject_leader(String project_leader) 
	{
		this.project_leader = project_leader;
	}
	/**
	 * 返回 项目负责人审核
	 * @return
	 */
	public String getProject_leader() 
	{
		return this.project_leader;
	}
	public void setDeputy_manager(String deputy_manager) 
	{
		this.deputy_manager = deputy_manager;
	}
	/**
	 * 返回 部门副经理审核
	 * @return
	 */
	public String getDeputy_manager() 
	{
		return this.deputy_manager;
	}
	public void setProjectTaskId(String projectTaskId) 
	{
		this.projectTaskId = projectTaskId;
	}
	/**
	 * 返回 项目任务id
	 * @return
	 */
	public String getProjectTaskId() 
	{
		return this.projectTaskId;
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
		if (!(object instanceof HjReviewAnnualReport)) 
		{
			return false;
		}
		HjReviewAnnualReport rhs = (HjReviewAnnualReport) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.Project_leaderID, rhs.Project_leaderID)
		.append(this.deputy_managerID, rhs.deputy_managerID)
		.append(this.name, rhs.name)
		.append(this.date, rhs.date)
		.append(this.department, rhs.department)
		.append(this.project_name, rhs.project_name)
		.append(this.n_name, rhs.n_name)
		.append(this.content, rhs.content)
		.append(this.note, rhs.note)
		.append(this.enclosure, rhs.enclosure)
		.append(this.project_leader, rhs.project_leader)
		.append(this.deputy_manager, rhs.deputy_manager)
		.append(this.projectTaskId, rhs.projectTaskId)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.nameID) 
		.append(this.departmentID) 
		.append(this.Project_leaderID) 
		.append(this.deputy_managerID) 
		.append(this.name) 
		.append(this.date) 
		.append(this.department) 
		.append(this.project_name) 
		.append(this.n_name) 
		.append(this.content) 
		.append(this.note) 
		.append(this.enclosure) 
		.append(this.project_leader) 
		.append(this.deputy_manager) 
		.append(this.projectTaskId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("nameID", this.nameID) 
		.append("departmentID", this.departmentID) 
		.append("Project_leaderID", this.Project_leaderID) 
		.append("deputy_managerID", this.deputy_managerID) 
		.append("name", this.name) 
		.append("date", this.date) 
		.append("department", this.department) 
		.append("project_name", this.project_name) 
		.append("n_name", this.n_name) 
		.append("content", this.content) 
		.append("note", this.note) 
		.append("enclosure", this.enclosure) 
		.append("Project_leader", this.project_leader) 
		.append("deputy_manager", this.deputy_manager) 
		.append("projectTaskId", this.projectTaskId) 
		.toString();
	}

}