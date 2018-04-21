package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:员工工时成本 Model对象
 */
public class UserWorkCost extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *用户id
	 */
	protected String  userid;
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
	 *正常工时成本
	 */
	protected Double  work_hour_cost;
	/**
	 *加班工时成本
	 */
	protected Double  over_hour_cost;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUserid(String userid) 
	{
		this.userid = userid;
	}
	/**
	 * 返回 用户id
	 * @return
	 */
	public String getUserid() 
	{
		return this.userid;
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
	public void setWork_hour_cost(Double work_hour_cost) 
	{
		this.work_hour_cost = work_hour_cost;
	}
	/**
	 * 返回 正常工时成本
	 * @return
	 */
	public Double getWork_hour_cost() 
	{
		return this.work_hour_cost;
	}
	public void setOver_hour_cost(Double over_hour_cost) 
	{
		this.over_hour_cost = over_hour_cost;
	}
	/**
	 * 返回 加班工时成本
	 * @return
	 */
	public Double getOver_hour_cost() 
	{
		return this.over_hour_cost;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof UserWorkCost)) 
		{
			return false;
		}
		UserWorkCost rhs = (UserWorkCost) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.userid, rhs.userid)
		.append(this.month, rhs.month)
		.append(this.ctime, rhs.ctime)
		.append(this.utime, rhs.utime)
		.append(this.work_hour_cost, rhs.work_hour_cost)
		.append(this.over_hour_cost, rhs.over_hour_cost)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.userid) 
		.append(this.month) 
		.append(this.ctime) 
		.append(this.utime) 
		.append(this.work_hour_cost) 
		.append(this.over_hour_cost) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("userid", this.userid) 
		.append("month", this.month) 
		.append("ctime", this.ctime) 
		.append("utime", this.utime) 
		.append("work_hour_cost", this.work_hour_cost) 
		.append("over_hour_cost", this.over_hour_cost) 
		.toString();
	}

}