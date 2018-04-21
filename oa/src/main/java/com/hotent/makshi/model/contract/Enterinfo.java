package com.hotent.makshi.model.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:到账信息 Model对象
 */
public class Enterinfo extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *到账时间
	 */
	protected java.util.Date  enterTime;
	/**
	 *到账金额(元)
	 */
	protected Long  enterNumber;
	
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
	
	public void setEnterTime(java.util.Date enterTime) 
	{
		this.enterTime = enterTime;
	}
	/**
	 * 返回 到账时间
	 * @return
	 */
	public java.util.Date getEnterTime() 
	{
		return this.enterTime;
	}
	public void setEnterNumber(Long enterNumber) 
	{
		this.enterNumber = enterNumber;
	}
	/**
	 * 返回 到账金额(元)
	 * @return
	 */
	public Long getEnterNumber() 
	{
		return this.enterNumber;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Enterinfo)) 
		{
			return false;
		}
		Enterinfo rhs = (Enterinfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.enterTime, rhs.enterTime)
		.append(this.enterNumber, rhs.enterNumber)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.enterTime) 
		.append(this.enterNumber) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("enterTime", this.enterTime) 
		.append("enterNumber", this.enterNumber) 
		.toString();
	}

}