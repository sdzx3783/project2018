package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:项目月度收入 Model对象
 */
public class ProjectMonthIncome extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *项目id
	 */
	protected String  proid;
	/**
	 *月份
	 */
	protected String  month;
	/**
	 *创建时间
	 */
	protected java.util.Date  ctime;
	/**
	 *修改时间
	 */
	protected java.util.Date  utime;
	/**
	 *月份收入
	 */
	protected Double  income;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setProid(String proid) 
	{
		this.proid = proid;
	}
	/**
	 * 返回 项目id
	 * @return
	 */
	public String getProid() 
	{
		return this.proid;
	}
	public void setMonth(String month) 
	{
		this.month = month;
	}
	/**
	 * 返回 月份
	 * @return
	 */
	public String getMonth() 
	{
		return this.month;
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
	public void setIncome(Double income) 
	{
		this.income = income;
	}
	/**
	 * 返回 月份收入
	 * @return
	 */
	public Double getIncome() 
	{
		return this.income;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ProjectMonthIncome)) 
		{
			return false;
		}
		ProjectMonthIncome rhs = (ProjectMonthIncome) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.proid, rhs.proid)
		.append(this.month, rhs.month)
		.append(this.ctime, rhs.ctime)
		.append(this.utime, rhs.utime)
		.append(this.income, rhs.income)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.proid) 
		.append(this.month) 
		.append(this.ctime) 
		.append(this.utime) 
		.append(this.income) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("proid", this.proid) 
		.append("month", this.month) 
		.append("ctime", this.ctime) 
		.append("utime", this.utime) 
		.append("income", this.income) 
		.toString();
	}

}