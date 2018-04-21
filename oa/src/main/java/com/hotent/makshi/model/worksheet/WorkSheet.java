package com.hotent.makshi.model.worksheet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:考勤记录 Model对象
 */
public class WorkSheet extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1194638050040258384L;
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
		 *打卡时间
		 */
		protected java.util.Date  clock_time;
		/**
		 *星期
		 */
		protected String  week;
		/**
		 *类型
		 */
		protected String  type;
		/**
		 *创建时间
		 */
		protected java.util.Date  create_time;
		
		protected Integer count_flag;
		/**
		 *备注
		 */
		protected String  remark;
		
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
		public void setOrgID(String orgID) 
		{
			this.orgID = orgID;
		}
		/**
		 * 返回 部门ID
		 * @return
		 */
		public String getOrgID() 
		{
			return this.orgID;
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
		public void setOrg(String org) 
		{
			this.org = org;
		}
		/**
		 * 返回 部门
		 * @return
		 */
		public String getOrg() 
		{
			return this.org;
		}
		public void setClock_time(java.util.Date clock_time) 
		{
			this.clock_time = clock_time;
		}
		/**
		 * 返回 打卡时间
		 * @return
		 */
		public java.util.Date getClock_time() 
		{
			return this.clock_time;
		}
		public void setWeek(String week) 
		{
			this.week = week;
		}
		/**
		 * 返回 星期
		 * @return
		 */
		public String getWeek() 
		{
			return this.week;
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
		 * 返回 创建时间
		 * @return
		 */
		public java.util.Date getCreate_time() 
		{
			return this.create_time;
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
			if (!(object instanceof WorkSheet)) 
			{
				return false;
			}
			WorkSheet rhs = (WorkSheet) object;
			return new EqualsBuilder()
			.append(this.id, rhs.id)
			.append(this.fullnameID, rhs.fullnameID)
			.append(this.orgID, rhs.orgID)
			.append(this.roleID, rhs.roleID)
			.append(this.fullname, rhs.fullname)
			.append(this.org, rhs.org)
			.append(this.rolename, rhs.rolename)
			.append(this.clock_time, rhs.clock_time)
			.append(this.week, rhs.week)
			.append(this.type, rhs.type)
			.append(this.create_time, rhs.create_time)
			.append(this.remark, rhs.remark)
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
			.append(this.clock_time) 
			.append(this.week) 
			.append(this.type) 
			.append(this.create_time) 
			.append(this.remark) 
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
			.append("clock_time", this.clock_time) 
			.append("week", this.week) 
			.append("type", this.type) 
			.append("create_time", this.create_time) 
			.append("remark", this.remark) 
			.toString();
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public Integer getCount_flag() {
			return count_flag;
		}
		public void setCount_flag(Integer count_flag) {
			this.count_flag = count_flag;
		}

}