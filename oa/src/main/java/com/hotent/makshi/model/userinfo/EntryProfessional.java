package com.hotent.makshi.model.userinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:专业职称 Model对象
 */
public class EntryProfessional extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *起止时间
	 */
	
	protected String  num;
	protected String  name;
	/**
	 *发证机构
	 */
	protected String  organization;
	/**
	 *职称专业
	 */
	protected String  major;
	/**
	 *取得职称时间
	 */
	protected java.util.Date  achieve_time;
	/**
	 *附件
	 */
	protected String  attachment;
	
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
	 * 返回 起止时间
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setOrganization(String organization) 
	{
		this.organization = organization;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getOrganization() 
	{
		return this.organization;
	}
	public void setMajor(String major) 
	{
		this.major = major;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getMajor() 
	{
		return this.major;
	}
	public void setAchieve_time(java.util.Date achieve_time) 
	{
		this.achieve_time = achieve_time;
	}
	/**
	 * 返回 取得职称时间
	 * @return
	 */
	public java.util.Date getAchieve_time() 
	{
		return this.achieve_time;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof EntryProfessional)) 
		{
			return false;
		}
		EntryProfessional rhs = (EntryProfessional) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.organization, rhs.organization)
		.append(this.major, rhs.major)
		.append(this.achieve_time, rhs.achieve_time)
		.append(this.attachment, rhs.attachment)
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
		.append(this.organization) 
		.append(this.major) 
		.append(this.achieve_time) 
		.append(this.attachment) 
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
		.append("organization", this.organization) 
		.append("major", this.major) 
		.append("achieve_time", this.achieve_time) 
		.append("attachment", this.attachment) 
		.toString();
	}
	
	
	public EntryProfessional(){
		
	}
	public EntryProfessional( Long refId,String num, String name,
			String organization, String major, Date achieve_time,
			String attachment) {
		super();
		this.num=num;
		this.refId = refId;
		this.name = name;
		this.organization = organization;
		this.major = major;
		this.achieve_time = achieve_time;
		this.attachment = attachment;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	
	
	
}