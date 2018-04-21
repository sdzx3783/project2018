package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.model.hj.ItemInformation;
/**
 * 对象功能:物品采购申请 Model对象
 */
public class HjArticlePurchasing extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  nameID;
	/**
	 *申请部门ID
	 */
	protected String  departmentID;
	/**
	 *现场负责人审核ID
	 */
	protected String  Person_chargeID;
	/**
	 *部门副经理核定ID
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
	 *申请部门
	 */
	protected String  department;
	/**
	 *采购方式
	 */
	protected String  purchase_way;
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
	protected String  Person_charge;
	/**
	 *部门副经理核定
	 */
	protected String  deputy_manager;
	/**
	 *项目任务id
	 */
	protected String  projectTaskId;
	protected Long  runId=0L;
	
	/**
	 *物品采购申请列表
	 */
	protected List<ItemInformation> itemInformationList=new ArrayList<ItemInformation>();
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
	 * 返回 申请部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setPerson_chargeID(String Person_chargeID) 
	{
		this.Person_chargeID = Person_chargeID;
	}
	/**
	 * 返回 现场负责人审核ID
	 * @return
	 */
	public String getPerson_chargeID() 
	{
		return this.Person_chargeID;
	}
	public void setDeputy_managerID(String deputy_managerID) 
	{
		this.deputy_managerID = deputy_managerID;
	}
	/**
	 * 返回 部门副经理核定ID
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
	 * 返回 申请部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setPurchase_way(String purchase_way) 
	{
		this.purchase_way = purchase_way;
	}
	/**
	 * 返回 采购方式
	 * @return
	 */
	public String getPurchase_way() 
	{
		return this.purchase_way;
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
	public void setPerson_charge(String Person_charge) 
	{
		this.Person_charge = Person_charge;
	}
	/**
	 * 返回 现场负责人审核
	 * @return
	 */
	public String getPerson_charge() 
	{
		return this.Person_charge;
	}
	public void setDeputy_manager(String deputy_manager) 
	{
		this.deputy_manager = deputy_manager;
	}
	/**
	 * 返回 部门副经理核定
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
	public void setItemInformationList(List<ItemInformation> itemInformationList) 
	{
		this.itemInformationList = itemInformationList;
	}
	/**
	 * 返回 物品采购申请列表
	 * @return
	 */
	public List<ItemInformation> getItemInformationList() 
	{
		return this.itemInformationList;
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
		if (!(object instanceof HjArticlePurchasing)) 
		{
			return false;
		}
		HjArticlePurchasing rhs = (HjArticlePurchasing) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.Person_chargeID, rhs.Person_chargeID)
		.append(this.deputy_managerID, rhs.deputy_managerID)
		.append(this.name, rhs.name)
		.append(this.date, rhs.date)
		.append(this.department, rhs.department)
		.append(this.purchase_way, rhs.purchase_way)
		.append(this.note, rhs.note)
		.append(this.enclosure, rhs.enclosure)
		.append(this.Person_charge, rhs.Person_charge)
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
		.append(this.Person_chargeID) 
		.append(this.deputy_managerID) 
		.append(this.name) 
		.append(this.date) 
		.append(this.department) 
		.append(this.purchase_way) 
		.append(this.note) 
		.append(this.enclosure) 
		.append(this.Person_charge) 
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
		.append("Person_chargeID", this.Person_chargeID) 
		.append("deputy_managerID", this.deputy_managerID) 
		.append("name", this.name) 
		.append("date", this.date) 
		.append("department", this.department) 
		.append("Purchase_way", this.purchase_way) 
		.append("note", this.note) 
		.append("enclosure", this.enclosure) 
		.append("Person_charge", this.Person_charge) 
		.append("deputy_manager", this.deputy_manager) 
		.append("projectTaskId", this.projectTaskId) 
		.toString();
	}

}