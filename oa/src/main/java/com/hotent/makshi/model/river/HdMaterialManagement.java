package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:部门内部物资管理 Model对象
 */
public class HdMaterialManagement extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *项目部ID
	 */
	protected String  departmentID;
	/**
	 *使用人ID
	 */
	protected String  userID;
	/**
	 *物资名称
	 */
	protected String  item_name;
	/**
	 *规格型号
	 */
	protected String  model;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *权属单位
	 */
	protected String  ownership_unit;
	/**
	 *项目部
	 */
	protected String  department;
	/**
	 *使用人
	 */
	protected String  user;
	/**
	 *备注
	 */
	protected String  remarks;
	/**
	 *状态
	 */
	protected String  state;
	/**
	 *附件
	 */
	protected String  enclosure;
	
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
	public void setUserID(String userID) 
	{
		this.userID = userID;
	}
	/**
	 * 返回 使用人ID
	 * @return
	 */
	public String getUserID() 
	{
		return this.userID;
	}
	public void setItem_name(String item_name) 
	{
		this.item_name = item_name;
	}
	/**
	 * 返回 物资名称
	 * @return
	 */
	public String getItem_name() 
	{
		return this.item_name;
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
	public void setOwnership_unit(String ownership_unit) 
	{
		this.ownership_unit = ownership_unit;
	}
	/**
	 * 返回 权属单位
	 * @return
	 */
	public String getOwnership_unit() 
	{
		return this.ownership_unit;
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
	public void setUser(String user) 
	{
		this.user = user;
	}
	/**
	 * 返回 使用人
	 * @return
	 */
	public String getUser() 
	{
		return this.user;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemarks() 
	{
		return this.remarks;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof HdMaterialManagement)) 
		{
			return false;
		}
		HdMaterialManagement rhs = (HdMaterialManagement) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.departmentID, rhs.departmentID)
		.append(this.userID, rhs.userID)
		.append(this.item_name, rhs.item_name)
		.append(this.model, rhs.model)
		.append(this.number, rhs.number)
		.append(this.ownership_unit, rhs.ownership_unit)
		.append(this.department, rhs.department)
		.append(this.user, rhs.user)
		.append(this.remarks, rhs.remarks)
		.append(this.state, rhs.state)
		.append(this.enclosure, rhs.enclosure)
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
		.append(this.userID) 
		.append(this.item_name) 
		.append(this.model) 
		.append(this.number) 
		.append(this.ownership_unit) 
		.append(this.department) 
		.append(this.user) 
		.append(this.remarks) 
		.append(this.state) 
		.append(this.enclosure) 
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
		.append("userID", this.userID) 
		.append("item_name", this.item_name) 
		.append("model", this.model) 
		.append("number", this.number) 
		.append("ownership_unit", this.ownership_unit) 
		.append("department", this.department) 
		.append("user", this.user) 
		.append("remarks", this.remarks) 
		.append("state", this.state) 
		.append("enclosure", this.enclosure) 
		.toString();
	}

}