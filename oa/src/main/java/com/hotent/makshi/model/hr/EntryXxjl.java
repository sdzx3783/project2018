package com.hotent.makshi.model.hr;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:学习经历 Model对象
 */
public class EntryXxjl extends WfBaseModel
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
	 *就读学校或机构
	 */
	protected String  jdxxhjg;
	/**
	 *专业
	 */
	protected String  zy;
	/**
	 *所获证书
	 */
	protected String  shzs;
	/**
	 *附件
	 */
	protected String  fj;
	
	protected String  xxlx;
	
	
	public String getXxlx() {
		return xxlx;
	}
	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
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
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public void setJdxxhjg(String jdxxhjg) 
	{
		this.jdxxhjg = jdxxhjg;
	}
	/**
	 * 返回 就读学校或机构
	 * @return
	 */
	public String getJdxxhjg() 
	{
		return this.jdxxhjg;
	}
	public void setZy(String zy) 
	{
		this.zy = zy;
	}
	/**
	 * 返回 专业
	 * @return
	 */
	public String getZy() 
	{
		return this.zy;
	}
	public void setShzs(String shzs) 
	{
		this.shzs = shzs;
	}
	/**
	 * 返回 所获证书
	 * @return
	 */
	public String getShzs() 
	{
		return this.shzs;
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
		if (!(object instanceof EntryXxjl)) 
		{
			return false;
		}
		EntryXxjl rhs = (EntryXxjl) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.qzsj, rhs.qzsj)
		.append(this.jdxxhjg, rhs.jdxxhjg)
		.append(this.zy, rhs.zy)
		.append(this.shzs, rhs.shzs)
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
		.append(this.jdxxhjg) 
		.append(this.zy) 
		.append(this.shzs) 
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
		.append("jdxxhjg", this.jdxxhjg) 
		.append("zy", this.zy) 
		.append("shzs", this.shzs) 
		.append("fj", this.fj) 
		.toString();
	}
	

	

}