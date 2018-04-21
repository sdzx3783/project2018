package com.hotent.makshi.model.honor;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:集体荣誉 Model对象
 */
public class GroupHonor extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *荣誉编号
	 */
	protected String  honor_num;
	/**
	 *奖项名称
	 */
	protected String  honor_name;
	/**
	 *奖项级别
	 */
	protected String  honor_level;
	/**
	 *获奖项目
	 */
	protected String  award_project;
	/**
	 *发证机构
	 */
	protected String  issuing_authority;
	/**
	 *发证时间
	 */
	protected String  issuing_date;
	/**
	 *颁发形式
	 */
	protected String  get_type;
	/**
	 *附件
	 */
	protected String  file;
	
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
	 * 返回 荣誉编号
	 * @return
	 */
	public String getHonor_num() 
	{
		return this.honor_num;
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
	public void setAward_project(String award_project) 
	{
		this.award_project = award_project;
	}
	/**
	 * 返回 获奖项目
	 * @return
	 */
	public String getAward_project() 
	{
		return this.award_project;
	}
	public void setIssuing_authority(String issuing_authority) 
	{
		this.issuing_authority = issuing_authority;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getIssuing_authority() 
	{
		return this.issuing_authority;
	}
	public void setIssuing_date(String issuing_date) 
	{
		this.issuing_date = issuing_date;
	}
	/**
	 * 返回 发证时间
	 * @return
	 */
	public String getIssuing_date() 
	{
		return this.issuing_date;
	}
	public void setGet_type(String get_type) 
	{
		this.get_type = get_type;
	}
	/**
	 * 返回 颁发形式
	 * @return
	 */
	public String getGet_type() 
	{
		return this.get_type;
	}
	public void setFile(String file) 
	{
		this.file = file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFile() 
	{
		return this.file;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof GroupHonor)) 
		{
			return false;
		}
		GroupHonor rhs = (GroupHonor) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.honor_num, rhs.honor_num)
		.append(this.honor_name, rhs.honor_name)
		.append(this.honor_level, rhs.honor_level)
		.append(this.award_project, rhs.award_project)
		.append(this.issuing_authority, rhs.issuing_authority)
		.append(this.issuing_date, rhs.issuing_date)
		.append(this.get_type, rhs.get_type)
		.append(this.file, rhs.file)
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
		.append(this.honor_name) 
		.append(this.honor_level) 
		.append(this.award_project) 
		.append(this.issuing_authority) 
		.append(this.issuing_date) 
		.append(this.get_type) 
		.append(this.file) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("honor_num", this.honor_num) 
		.append("honor_name", this.honor_name) 
		.append("honor_level", this.honor_level) 
		.append("award_project", this.award_project) 
		.append("issuing_authority", this.issuing_authority) 
		.append("issuing_date", this.issuing_date) 
		.append("get_type", this.get_type) 
		.append("file", this.file) 
		.toString();
	}

}