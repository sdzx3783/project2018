package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:专业职称 Model对象
 */
public class EntryZyzc extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *职称名称
	 */
	protected String  zcmc;
	/**
	 *发证机构
	 */
	protected String  fzjg;
	/**
	 *职称专业
	 */
	protected String  zczy;
	/**
	 *取的职称时间
	 */
	protected java.util.Date  qdzcsj;
	/**
	 *附件
	 */
	protected String  fj;
	
	protected String zcbh;
	
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
	
	public void setZcmc(String zcmc) 
	{
		this.zcmc = zcmc;
	}
	/**
	 * 返回 职称名称
	 * @return
	 */
	public String getZcmc() 
	{
		return this.zcmc;
	}
	public void setFzjg(String fzjg) 
	{
		this.fzjg = fzjg;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getFzjg() 
	{
		return this.fzjg;
	}
	public void setZczy(String zczy) 
	{
		this.zczy = zczy;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getZczy() 
	{
		return this.zczy;
	}
	public void setQdzcsj(java.util.Date qdzcsj) 
	{
		this.qdzcsj = qdzcsj;
	}
	/**
	 * 返回 取的职称时间
	 * @return
	 */
	public java.util.Date getQdzcsj() 
	{
		return this.qdzcsj;
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
		if (!(object instanceof EntryZyzc)) 
		{
			return false;
		}
		EntryZyzc rhs = (EntryZyzc) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.zcmc, rhs.zcmc)
		.append(this.fzjg, rhs.fzjg)
		.append(this.zczy, rhs.zczy)
		.append(this.qdzcsj, rhs.qdzcsj)
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
		.append(this.zcmc) 
		.append(this.fzjg) 
		.append(this.zczy) 
		.append(this.qdzcsj) 
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
		.append("zcmc", this.zcmc) 
		.append("fzjg", this.fzjg) 
		.append("zczy", this.zczy) 
		.append("qdzcsj", this.qdzcsj) 
		.append("fj", this.fj) 
		.toString();
	}
	public String getZcbh() {
		return zcbh;
	}
	public void setZcbh(String zcbh) {
		this.zcbh = zcbh;
	}

}