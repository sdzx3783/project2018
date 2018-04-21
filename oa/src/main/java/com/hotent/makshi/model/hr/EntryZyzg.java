package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:执业资格 Model对象
 */
public class EntryZyzg extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *执业资格名称
	 */
	protected String  zyzgmc;
	/**
	 *发证机构
	 */
	protected String  fzjg;
	/**
	 *执业资格证专业
	 */
	protected String  zyzgzzy;
	/**
	 *取的证书时间
	 */
	protected java.util.Date  qdzssj;
	/**
	 *是否转入本公司
	 */
	protected String  sfzrbgs;
	/**
	 *附件
	 */
	protected String  fj;
	
	protected String zyzcbh;
	
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
	
	public void setZyzgmc(String zyzgmc) 
	{
		this.zyzgmc = zyzgmc;
	}
	/**
	 * 返回 执业资格名称
	 * @return
	 */
	public String getZyzgmc() 
	{
		return this.zyzgmc;
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
	public void setZyzgzzy(String zyzgzzy) 
	{
		this.zyzgzzy = zyzgzzy;
	}
	/**
	 * 返回 执业资格证专业
	 * @return
	 */
	public String getZyzgzzy() 
	{
		return this.zyzgzzy;
	}
	public void setQdzssj(java.util.Date qdzssj) 
	{
		this.qdzssj = qdzssj;
	}
	/**
	 * 返回 取的证书时间
	 * @return
	 */
	public java.util.Date getQdzssj() 
	{
		return this.qdzssj;
	}
	public void setSfzrbgs(String sfzrbgs) 
	{
		this.sfzrbgs = sfzrbgs;
	}
	/**
	 * 返回 是否转入本公司
	 * @return
	 */
	public String getSfzrbgs() 
	{
		return this.sfzrbgs;
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
		if (!(object instanceof EntryZyzg)) 
		{
			return false;
		}
		EntryZyzg rhs = (EntryZyzg) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.zyzgmc, rhs.zyzgmc)
		.append(this.fzjg, rhs.fzjg)
		.append(this.zyzgzzy, rhs.zyzgzzy)
		.append(this.qdzssj, rhs.qdzssj)
		.append(this.sfzrbgs, rhs.sfzrbgs)
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
		.append(this.zyzgmc) 
		.append(this.fzjg) 
		.append(this.zyzgzzy) 
		.append(this.qdzssj) 
		.append(this.sfzrbgs) 
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
		.append("zyzgmc", this.zyzgmc) 
		.append("fzjg", this.fzjg) 
		.append("zyzgzzy", this.zyzgzzy) 
		.append("qdzssj", this.qdzssj) 
		.append("sfzrbgs", this.sfzrbgs) 
		.append("fj", this.fj) 
		.toString();
	}
	public String getZyzcbh() {
		return zyzcbh;
	}
	public void setZyzcbh(String zyzcbh) {
		this.zyzcbh = zyzcbh;
	}

}