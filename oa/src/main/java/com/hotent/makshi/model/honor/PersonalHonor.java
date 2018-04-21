package com.hotent.makshi.model.honor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:个人荣誉 Model对象
 */
public class PersonalHonor extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *员工荣誉编号
	 */
	protected String  honor_num;
	/**
	 * 姓名
	 */
	protected String name;
	
	private Boolean isEditers;
	
	protected String nameID;
	/**
	 *员工编号
	 */
	protected String  user_num;
	/**
	 *奖项名称
	 */
	protected String  honor_name;
	/**
	 *奖项级别
	 */
	protected String  honor_level;
	/**
	 *颁发机构
	 */
	protected String  issuing_authority;
	/**
	 *颁发日期
	 */
	protected java.util.Date  issue_date;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *查询网址
	 */
	protected String  query_url;
	/**
	 * 附件
	 * @return
	 */
	protected String file;
	
	
	public Boolean getIsEditers() {
		return isEditers;
	}
	public void setIsEditers(Boolean isEditers) {
		this.isEditers = isEditers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameID() {
		return nameID;
	}
	public void setNameID(String nameID) {
		this.nameID = nameID;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setHonor_num(String honor_num) 
	{
		this.honor_num = honor_num;
	}
	/**
	 * 返回 员工荣誉编号
	 * @return
	 */
	public String getHonor_num() 
	{
		return this.honor_num;
	}
	public void setUser_num(String user_num) 
	{
		this.user_num = user_num;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_num() 
	{
		return this.user_num;
	}
	public void setHonor_name(String honor_name) 
	{
		this.honor_name = honor_name;
	}
	/**
	 * 返回 奖项名称
	 * @return
	 */
	public String getHonor_name() 
	{
		return this.honor_name;
	}
	public void setHonor_level(String honor_level) 
	{
		this.honor_level = honor_level;
	}
	/**
	 * 返回 奖项级别
	 * @return
	 */
	public String getHonor_level() 
	{
		return this.honor_level;
	}
	public void setIssuing_authority(String issuing_authority) 
	{
		this.issuing_authority = issuing_authority;
	}
	/**
	 * 返回 颁发机构
	 * @return
	 */
	public String getIssuing_authority() 
	{
		return this.issuing_authority;
	}
	public void setIssue_date(java.util.Date issue_date) 
	{
		this.issue_date = issue_date;
	}
	/**
	 * 返回 颁发日期
	 * @return
	 */
	public java.util.Date getIssue_date() 
	{
		return this.issue_date;
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
	public void setQuery_url(String query_url) 
	{
		this.query_url = query_url;
	}
	/**
	 * 返回 查询网址
	 * @return
	 */
	public String getQuery_url() 
	{
		return this.query_url;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PersonalHonor)) 
		{
			return false;
		}
		PersonalHonor rhs = (PersonalHonor) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.honor_num, rhs.honor_num)
		.append(this.user_num, rhs.user_num)
		.append(this.honor_name, rhs.honor_name)
		.append(this.honor_level, rhs.honor_level)
		.append(this.issuing_authority, rhs.issuing_authority)
		.append(this.issue_date, rhs.issue_date)
		.append(this.remark, rhs.remark)
		.append(this.query_url, rhs.query_url)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.honor_num) 
		.append(this.name) 
		.append(this.user_num) 
		.append(this.honor_name) 
		.append(this.honor_level) 
		.append(this.issuing_authority) 
		.append(this.issue_date) 
		.append(this.remark) 
		.append(this.query_url) 
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
		.append("honor_num", this.honor_num) 
		.append("user_num", this.user_num) 
		.append("honor_name", this.honor_name) 
		.append("honor_level", this.honor_level) 
		.append("issuing_authority", this.issuing_authority) 
		.append("issue_date", this.issue_date) 
		.append("remark", this.remark) 
		.append("query_url", this.query_url) 
		.toString();
	}

}