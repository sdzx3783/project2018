package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:运营日报审核表 Model对象
 */
public class HjOperationDaily extends WfBaseModel
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
	 *现场负责人审核ID
	 */
	protected String  chargerID;
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
	 *日报名称
	 */
	protected String  n_name;
	/**
	 *日报内容
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
	 *现场负责人审核
	 */
	protected String  charger;
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
	public void setChargerID(String chargerID) 
	{
		this.chargerID = chargerID;
	}
	/**
	 * 返回 现场负责人审核ID
	 * @return
	 */
	public String getChargerID() 
	{
		return this.chargerID;
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
	 * 返回 日报名称
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
	 * 返回 日报内容
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
	public void setCharger(String charger) 
	{
		this.charger = charger;
	}
	/**
	 * 返回 现场负责人审核
	 * @return
	 */
	public String getCharger() 
	{
		return this.charger;
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
		if (!(object instanceof HjOperationDaily)) 
		{
			return false;
		}
		HjOperationDaily rhs = (HjOperationDaily) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.chargerID, rhs.chargerID)
		.append(this.name, rhs.name)
		.append(this.date, rhs.date)
		.append(this.department, rhs.department)
		.append(this.project_name, rhs.project_name)
		.append(this.n_name, rhs.n_name)
		.append(this.content, rhs.content)
		.append(this.note, rhs.note)
		.append(this.enclosure, rhs.enclosure)
		.append(this.charger, rhs.charger)
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
		.append(this.chargerID) 
		.append(this.name) 
		.append(this.date) 
		.append(this.department) 
		.append(this.project_name) 
		.append(this.n_name) 
		.append(this.content) 
		.append(this.note) 
		.append(this.enclosure) 
		.append(this.charger) 
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
		.append("chargerID", this.chargerID) 
		.append("name", this.name) 
		.append("date", this.date) 
		.append("department", this.department) 
		.append("project_name", this.project_name) 
		.append("n_name", this.n_name) 
		.append("content", this.content) 
		.append("note", this.note) 
		.append("enclosure", this.enclosure) 
		.append("charger", this.charger) 
		.append("projectTaskId", this.projectTaskId) 
		.toString();
	}

}