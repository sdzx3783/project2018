package com.hotent.makshi.model.userinfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:家庭成员 Model对象
 */
public class EntryFamily extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *关系
	 */
	protected String  relationship;
	/**
	 *成员姓名
	 */
	protected String  person_name;
	/**
	 *成员性别
	 */
	protected String  person_sex;
	/**
	 *成员出生年
	 */
	protected String  person_birthday_year;
	/**
	 *成员工作单位
	 */
	protected String  person_workspace;
	/**
	 *成员联系电话
	 */
	protected String  person_phone;
	/**
	 *成员附件
	 */
	protected String  person_attachment;
	
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
	
	public void setRelationship(String relationship) 
	{
		this.relationship = relationship;
	}
	/**
	 * 返回 关系
	 * @return
	 */
	public String getRelationship() 
	{
		return this.relationship;
	}
	public void setPerson_name(String person_name) 
	{
		this.person_name = person_name;
	}
	/**
	 * 返回 成员姓名
	 * @return
	 */
	public String getPerson_name() 
	{
		return this.person_name;
	}
	public void setPerson_sex(String person_sex) 
	{
		this.person_sex = person_sex;
	}
	/**
	 * 返回 成员性别
	 * @return
	 */
	public String getPerson_sex() 
	{
		return this.person_sex;
	}
	public void setPerson_birthday_year(String person_birthday_year) 
	{
		this.person_birthday_year = person_birthday_year;
	}
	/**
	 * 返回 成员出生年
	 * @return
	 */
	public String getPerson_birthday_year() 
	{
		return this.person_birthday_year;
	}
	public void setPerson_workspace(String person_workspace) 
	{
		this.person_workspace = person_workspace;
	}
	/**
	 * 返回 成员工作单位
	 * @return
	 */
	public String getPerson_workspace() 
	{
		return this.person_workspace;
	}
	public void setPerson_phone(String person_phone) 
	{
		this.person_phone = person_phone;
	}
	/**
	 * 返回 成员联系电话
	 * @return
	 */
	public String getPerson_phone() 
	{
		return this.person_phone;
	}
	public void setPerson_attachment(String person_attachment) 
	{
		this.person_attachment = person_attachment;
	}
	/**
	 * 返回 成员附件
	 * @return
	 */
	public String getPerson_attachment() 
	{
		return this.person_attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof EntryFamily)) 
		{
			return false;
		}
		EntryFamily rhs = (EntryFamily) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.relationship, rhs.relationship)
		.append(this.person_name, rhs.person_name)
		.append(this.person_sex, rhs.person_sex)
		.append(this.person_birthday_year, rhs.person_birthday_year)
		.append(this.person_workspace, rhs.person_workspace)
		.append(this.person_phone, rhs.person_phone)
		.append(this.person_attachment, rhs.person_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.relationship) 
		.append(this.person_name) 
		.append(this.person_sex) 
		.append(this.person_birthday_year) 
		.append(this.person_workspace) 
		.append(this.person_phone) 
		.append(this.person_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("relationship", this.relationship) 
		.append("person_name", this.person_name) 
		.append("person_sex", this.person_sex) 
		.append("person_birthday_year", this.person_birthday_year) 
		.append("person_workspace", this.person_workspace) 
		.append("person_phone", this.person_phone) 
		.append("person_attachment", this.person_attachment) 
		.toString();
	}
	
	
	public EntryFamily(){
		
	}
	
	public EntryFamily(Long refId, String relationship,
			String person_name, String person_sex, String person_birthday_year,
			String person_workspace, String person_phone,
			String person_attachment) {
		super();
		this.refId = refId;
		this.relationship = relationship;
		this.person_name = person_name;
		this.person_sex = person_sex;
		this.person_birthday_year = person_birthday_year;
		this.person_workspace = person_workspace;
		this.person_phone = person_phone;
		this.person_attachment = person_attachment;
	}

}