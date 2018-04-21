package com.hotent.makshi.model.worksheet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:缺勤表 Model对象
 */
public class WorkLeave extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *姓名ID
	 */
	protected String  fullnameID;
	/**
	 *部门ID
	 */
	protected String  orgID;
	/**
	 *岗位ID
	 */
	protected String  roleID;
	/**
	 *姓名
	 */
	protected String  fullname;
	/**
	 *部门
	 */
	protected String  org;
	/**
	 *岗位
	 */
	protected String  rolename;
	/**
	 *类型
	 */
	protected String  type;
	/**
	 *缺勤时间
	 */
	protected java.util.Date  leave_time;
	/**
	 *登记时间
	 */
	protected java.util.Date  create_time;
	
	/**
	 * 缺勤时间段
	 * 0全天
	 * 1上午
	 * 2下午
	 */
	protected Integer leave_range;
	/**
	 *备注
	 */
	protected String  remark;
	
	protected Integer count_flag;
	
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
	 * 返回 姓名ID
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
	 * 返回 姓名
	 * @return
	 */
	public String getFullname() 
	{
		return this.fullname;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	
	public void setCreate_time(java.util.Date create_time) 
	{
		this.create_time = create_time;
	}
	/**
	 * 返回 登记时间
	 * @return
	 */
	public java.util.Date getCreate_time() 
	{
		return this.create_time;
	}
	public java.util.Date getLeave_time() {
		return leave_time;
	}
	public void setLeave_time(java.util.Date leave_time) {
		this.leave_time = leave_time;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof WorkLeave)) 
		{
			return false;
		}
		WorkLeave rhs = (WorkLeave) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fullnameID, rhs.fullnameID)
		.append(this.orgID, rhs.orgID)
		.append(this.roleID, rhs.roleID)
		.append(this.fullname, rhs.fullname)
		.append(this.org, rhs.org)
		.append(this.rolename, rhs.rolename)
		.append(this.type, rhs.type)
		.append(this.leave_time, rhs.leave_time)
		.append(this.create_time, rhs.create_time)
		.append(this.remark, rhs.remark)
		.append(this.leave_range, rhs.leave_range)
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
		.append(this.orgID) 
		.append(this.roleID) 
		.append(this.fullname) 
		.append(this.org) 
		.append(this.rolename) 
		.append(this.type) 
		.append(this.leave_time) 
		.append(this.create_time) 
		.append(this.remark) 
		.append(this.leave_range)
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
		.append("orgID", this.orgID) 
		.append("roleID", this.roleID) 
		.append("fullname", this.fullname) 
		.append("org", this.org) 
		.append("rolename", this.rolename) 
		.append("type", this.type) 
		.append("leave_start_time", this.leave_time) 
		.append("create_time", this.create_time) 
		.append("remark", this.remark) 
		.append("leave_range",this.leave_range)
		.toString();
	}
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public Integer getLeave_range() {
		return leave_range;
	}
	public void setLeave_range(Integer leave_range) {
		this.leave_range = leave_range;
	}
	public Integer getCount_flag() {
		return count_flag;
	}
	public void setCount_flag(Integer count_flag) {
		this.count_flag = count_flag;
	}

}