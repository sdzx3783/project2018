package com.hotent.makshi.model.vacation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:公司年假福利 Model对象
 */
public class CompanyYearVaction extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *创建人ID
	 */
	protected String  creatorID;
	/**
	 *修改人ID
	 */
	protected String  updatorID;
	/**
	 *年份
	 */
	protected String  year;
	/**
	 *年假发放天数
	 */
	protected Double  days;
	/**
	 *创建时间
	 */
	protected java.util.Date  ctime;
	/**
	 *修改时间
	 */
	protected java.util.Date  utime;
	/**
	 *创建人
	 */
	protected String  creator;
	/**
	 *修改人
	 */
	protected String  updator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCreatorID(String creatorID) 
	{
		this.creatorID = creatorID;
	}
	/**
	 * 返回 创建人ID
	 * @return
	 */
	public String getCreatorID() 
	{
		return this.creatorID;
	}
	public void setUpdatorID(String updatorID) 
	{
		this.updatorID = updatorID;
	}
	/**
	 * 返回 修改人ID
	 * @return
	 */
	public String getUpdatorID() 
	{
		return this.updatorID;
	}
	public void setYear(String year) 
	{
		this.year = year;
	}
	/**
	 * 返回 年份
	 * @return
	 */
	public String getYear() 
	{
		return this.year;
	}
	public void setDays(Double days) 
	{
		this.days = days;
	}
	/**
	 * 返回 年假发放天数
	 * @return
	 */
	public Double getDays() 
	{
		return this.days;
	}
	public void setCtime(java.util.Date ctime) 
	{
		this.ctime = ctime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCtime() 
	{
		return this.ctime;
	}
	public void setUtime(java.util.Date utime) 
	{
		this.utime = utime;
	}
	/**
	 * 返回 修改时间
	 * @return
	 */
	public java.util.Date getUtime() 
	{
		return this.utime;
	}
	public void setCreator(String creator) 
	{
		this.creator = creator;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreator() 
	{
		return this.creator;
	}
	public void setUpdator(String updator) 
	{
		this.updator = updator;
	}
	/**
	 * 返回 修改人
	 * @return
	 */
	public String getUpdator() 
	{
		return this.updator;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CompanyYearVaction)) 
		{
			return false;
		}
		CompanyYearVaction rhs = (CompanyYearVaction) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.creatorID, rhs.creatorID)
		.append(this.updatorID, rhs.updatorID)
		.append(this.year, rhs.year)
		.append(this.days, rhs.days)
		.append(this.ctime, rhs.ctime)
		.append(this.utime, rhs.utime)
		.append(this.creator, rhs.creator)
		.append(this.updator, rhs.updator)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.creatorID) 
		.append(this.updatorID) 
		.append(this.year) 
		.append(this.days) 
		.append(this.ctime) 
		.append(this.utime) 
		.append(this.creator) 
		.append(this.updator) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("creatorID", this.creatorID) 
		.append("updatorID", this.updatorID) 
		.append("year", this.year) 
		.append("days", this.days) 
		.append("ctime", this.ctime) 
		.append("utime", this.utime) 
		.append("creator", this.creator) 
		.append("updator", this.updator) 
		.toString();
	}

}