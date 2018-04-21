package com.hotent.makshi.model.hr;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:工作经历 Model对象
 */
public class EntryGzjl extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *起止时间
	 */
	protected Date  qzsj;
	
	protected Date  endDate;
	
	/**
	 *工作单位
	 */
	protected String  gzdw;
	/**
	 *部门岗位
	 */
	protected String  bmgw;
	/**
	 *技术职务
	 */
	protected String  jszw;
	/**
	 *附件
	 */
	protected String  fj;
	
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
	
	public void setQzsj(Date qzsj) 
	{
		this.qzsj = qzsj;
	}
	/**
	 * 返回 起止时间
	 * @return
	 */
	public Date getQzsj() 
	{
		return this.qzsj;
	}
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setGzdw(String gzdw) 
	{
		this.gzdw = gzdw;
	}
	/**
	 * 返回 工作单位
	 * @return
	 */
	public String getGzdw() 
	{
		return this.gzdw;
	}
	public void setBmgw(String bmgw) 
	{
		this.bmgw = bmgw;
	}
	/**
	 * 返回 部门岗位
	 * @return
	 */
	public String getBmgw() 
	{
		return this.bmgw;
	}
	public void setJszw(String jszw) 
	{
		this.jszw = jszw;
	}
	/**
	 * 返回 技术职务
	 * @return
	 */
	public String getJszw() 
	{
		return this.jszw;
	}
	public void setFj(String fj) 
	{
		this.fj = fj;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFj() 
	{
		return this.fj;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof EntryGzjl)) 
		{
			return false;
		}
		EntryGzjl rhs = (EntryGzjl) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.qzsj, rhs.qzsj)
		.append(this.gzdw, rhs.gzdw)
		.append(this.bmgw, rhs.bmgw)
		.append(this.jszw, rhs.jszw)
		.append(this.fj, rhs.fj)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.qzsj) 
		.append(this.gzdw) 
		.append(this.bmgw) 
		.append(this.jszw) 
		.append(this.fj) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("qzsj", this.qzsj) 
		.append("gzdw", this.gzdw) 
		.append("bmgw", this.bmgw) 
		.append("jszw", this.jszw) 
		.append("fj", this.fj) 
		.toString();
	}

}