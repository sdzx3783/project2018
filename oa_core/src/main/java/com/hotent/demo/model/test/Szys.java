package com.hotent.demo.model.test;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:数字运算 Model对象
 */
public class Szys extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *数字一
	 */
	protected Long  szy;
	/**
	 *数字二
	 */
	protected Long  sze;
	/**
	 *合计
	 */
	protected Long  hj;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSzy(Long szy) 
	{
		this.szy = szy;
	}
	/**
	 * 返回 数字一
	 * @return
	 */
	public Long getSzy() 
	{
		return this.szy;
	}
	public void setSze(Long sze) 
	{
		this.sze = sze;
	}
	/**
	 * 返回 数字二
	 * @return
	 */
	public Long getSze() 
	{
		return this.sze;
	}
	public void setHj(Long hj) 
	{
		this.hj = hj;
	}
	/**
	 * 返回 合计
	 * @return
	 */
	public Long getHj() 
	{
		return this.hj;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Szys)) 
		{
			return false;
		}
		Szys rhs = (Szys) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.szy, rhs.szy)
		.append(this.sze, rhs.sze)
		.append(this.hj, rhs.hj)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.szy) 
		.append(this.sze) 
		.append(this.hj) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("szy", this.szy) 
		.append("sze", this.sze) 
		.append("hj", this.hj) 
		.toString();
	}

}