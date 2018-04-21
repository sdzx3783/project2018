package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:设备报废申请 Model对象
 */
public class HjEquScrappingApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
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
	 *维修主管审核ID
	 */
	protected String  mai_supervisorID;
	/**
	 *部门副经理审核ID
	 */
	protected String  deputy_managerID;
	/**
	 *申请人
	 */
	protected String  name;
	/**
	 *申请日期
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
	 *设备名称
	 */
	protected String  n_name;
	/**
	 *数量
	 */
	protected String  number;
	/**
	 *购入时间
	 */
	protected java.util.Date  d_date;
	/**
	 *报废原因
	 */
	protected String  discard_reason;
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
	 *维修主管审核
	 */
	protected String  mai_supervisor;
	/**
	 *部门副经理审核
	 */
	protected String  deputy_manager;
	/**
	 *项目任务id
	 */
	protected String  projectTaskId;
	/**
	 * 购入价格
	 */
	protected Double price;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

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
	 * 返回 申请人ID
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
	public void setMai_supervisorID(String mai_supervisorID) 
	{
		this.mai_supervisorID = mai_supervisorID;
	}
	/**
	 * 返回 维修主管审核ID
	 * @return
	 */
	public String getMai_supervisorID() 
	{
		return this.mai_supervisorID;
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
	 * 返回 申请人
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
	 * 返回 申请日期
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
	 * 返回 设备名称
	 * @return
	 */
	public String getN_name() 
	{
		return this.n_name;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public String getNumber() 
	{
		return this.number;
	}
	public void setD_date(java.util.Date d_date) 
	{
		this.d_date = d_date;
	}
	/**
	 * 返回 购入时间
	 * @return
	 */
	public java.util.Date getD_date() 
	{
		return this.d_date;
	}
	public void setDiscard_reason(String discard_reason) 
	{
		this.discard_reason = discard_reason;
	}
	/**
	 * 返回 报废原因
	 * @return
	 */
	public String getDiscard_reason() 
	{
		return this.discard_reason;
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
	public void setMai_supervisor(String mai_supervisor) 
	{
		this.mai_supervisor = mai_supervisor;
	}
	/**
	 * 返回 维修主管审核
	 * @return
	 */
	public String getMai_supervisor() 
	{
		return this.mai_supervisor;
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
		if (!(object instanceof HjEquScrappingApplication)) 
		{
			return false;
		}
		HjEquScrappingApplication rhs = (HjEquScrappingApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.chargerID, rhs.chargerID)
		.append(this.mai_supervisorID, rhs.mai_supervisorID)
		.append(this.deputy_managerID, rhs.deputy_managerID)
		.append(this.name, rhs.name)
		.append(this.date, rhs.date)
		.append(this.department, rhs.department)
		.append(this.project_name, rhs.project_name)
		.append(this.n_name, rhs.n_name)
		.append(this.number, rhs.number)
		.append(this.d_date, rhs.d_date)
		.append(this.discard_reason, rhs.discard_reason)
		.append(this.note, rhs.note)
		.append(this.enclosure, rhs.enclosure)
		.append(this.charger, rhs.charger)
		.append(this.mai_supervisor, rhs.mai_supervisor)
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
		.append(this.chargerID) 
		.append(this.mai_supervisorID) 
		.append(this.deputy_managerID) 
		.append(this.name) 
		.append(this.date) 
		.append(this.department) 
		.append(this.project_name) 
		.append(this.n_name) 
		.append(this.number) 
		.append(this.d_date) 
		.append(this.discard_reason) 
		.append(this.note) 
		.append(this.enclosure) 
		.append(this.charger) 
		.append(this.mai_supervisor) 
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
		.append("chargerID", this.chargerID) 
		.append("mai_supervisorID", this.mai_supervisorID) 
		.append("deputy_managerID", this.deputy_managerID) 
		.append("name", this.name) 
		.append("date", this.date) 
		.append("department", this.department) 
		.append("project_name", this.project_name) 
		.append("n_name", this.n_name) 
		.append("number", this.number) 
		.append("d_date", this.d_date) 
		.append("discard_reason", this.discard_reason) 
		.append("note", this.note) 
		.append("enclosure", this.enclosure) 
		.append("charger", this.charger) 
		.append("mai_supervisor", this.mai_supervisor) 
		.append("deputy_manager", this.deputy_manager) 
		.append("projectTaskId", this.projectTaskId) 
		.toString();
	}

}