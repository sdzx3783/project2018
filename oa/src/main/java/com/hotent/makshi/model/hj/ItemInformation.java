package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:物品采购信息 Model对象
 */
public class ItemInformation extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *物品名称
	 */
	protected String  name;
	/**
	 *规格型号
	 */
	protected String  model;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *质量要求
	 */
	protected String  demand;
	
	/**
	 * 采购日期
	 */
	protected java.util.Date  date;
	/**
	 * 领用人
	 */
	protected String lname;
	/**
	 *领用部门 
	 */
	protected String department;
	
	
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 物品名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setModel(String model) 
	{
		this.model = model;
	}
	/**
	 * 返回 规格型号
	 * @return
	 */
	public String getModel() 
	{
		return this.model;
	}
	public void setNumber(Long number) 
	{
		this.number = number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public Long getNumber() 
	{
		return this.number;
	}
	public void setDemand(String demand) 
	{
		this.demand = demand;
	}
	/**
	 * 返回 质量要求
	 * @return
	 */
	public String getDemand() 
	{
		return this.demand;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ItemInformation)) 
		{
			return false;
		}
		ItemInformation rhs = (ItemInformation) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.model, rhs.model)
		.append(this.number, rhs.number)
		.append(this.demand, rhs.demand)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.name) 
		.append(this.model) 
		.append(this.number) 
		.append(this.demand) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("name", this.name) 
		.append("model", this.model) 
		.append("number", this.number) 
		.append("demand", this.demand) 
		.toString();
	}

}