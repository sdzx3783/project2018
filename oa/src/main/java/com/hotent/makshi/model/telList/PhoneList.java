package com.hotent.makshi.model.telList;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:手机号码列表 Model对象
 */
public class PhoneList extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *部门ID
	 */
	protected String  departmentID;
	/**
	 *员工编号
	 */
	protected String  user_id;
	/**
	 *状态
	 */
	protected String  state;
	/**
	 *员工姓名
	 */
	protected String  user_name;
	/**
	 *部门
	 */
	protected String  department;
	/**
	/**
	 *定额
	 */
	protected String  limit;
	/**
	 *手机号码
	 */
	protected Long  phone_number;
	/**
	 *限额
	 */
	protected String  max_position;
	/**
	 *短号
	 */
	protected Long  short_munber;
	/**
	 *备注
	 */
	protected String  remarks;
	/**
	 *套餐
	 */
	protected String  packages;
	
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
	 * 返回 部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
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
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 员工姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setLimit(String limit) 
	{
		this.limit = limit;
	}
	/**
	 * 返回 定额
	 * @return
	 */
	public String getLimit() 
	{
		return this.limit;
	}
	public void setPhone_number(Long phone_number) 
	{
		this.phone_number = phone_number;
	}
	/**
	 * 返回 手机号码
	 * @return
	 */
	public Long getPhone_number() 
	{
		return this.phone_number;
	}
	public void setMax_position(String max_position) 
	{
		this.max_position = max_position;
	}
	/**
	 * 返回 限额
	 * @return
	 */
	public String getMax_position() 
	{
		return this.max_position;
	}
	public void setShort_munber(Long short_munber) 
	{
		this.short_munber = short_munber;
	}
	/**
	 * 返回 短号
	 * @return
	 */
	public Long getShort_munber() 
	{
		return this.short_munber;
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
	public void setPackages(String packages) 
	{
		this.packages = packages;
	}
	/**
	 * 返回 套餐
	 * @return
	 */
	public String getPackages() 
	{
		return this.packages;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PhoneList)) 
		{
			return false;
		}
		PhoneList rhs = (PhoneList) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.departmentID, rhs.departmentID)
		.append(this.user_id, rhs.user_id)
		.append(this.state, rhs.state)
		.append(this.user_name, rhs.user_name)
		.append(this.department, rhs.department)
		.append(this.limit, rhs.limit)
		.append(this.phone_number, rhs.phone_number)
		.append(this.max_position, rhs.max_position)
		.append(this.short_munber, rhs.short_munber)
		.append(this.remarks, rhs.remarks)
		.append(this.packages, rhs.packages)
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
		.append(this.user_id) 
		.append(this.state) 
		.append(this.user_name) 
		.append(this.department) 
		.append(this.limit) 
		.append(this.phone_number) 
		.append(this.max_position) 
		.append(this.short_munber) 
		.append(this.remarks) 
		.append(this.packages) 
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
		.append("user_id", this.user_id) 
		.append("state", this.state) 
		.append("user_name", this.user_name) 
		.append("department", this.department) 
		.append("limit", this.limit) 
		.append("phone_number", this.phone_number) 
		.append("max_position", this.max_position) 
		.append("short_munber", this.short_munber) 
		.append("remarks", this.remarks) 
		.append("packages", this.packages) 
		.toString();
	}

}