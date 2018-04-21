package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:家庭成员 Model对象
 */
public class EntryJtcy extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *关系
	 */
	protected String  gx;
	/**
	 *姓名
	 */
	protected String  xm;
	/**
	 *性别
	 */
	protected String  xb;
	/**
	 *出生年
	 */
	protected String  csn;
	/**
	 *出生年月
	 */
	protected String  csny;
	/**
	 *联系电话
	 */
	protected String  lxdh;
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
	
	public void setGx(String gx) 
	{
		this.gx = gx;
	}
	/**
	 * 返回 关系
	 * @return
	 */
	public String getGx() 
	{
		return this.gx;
	}
	public void setXm(String xm) 
	{
		this.xm = xm;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getXm() 
	{
		return this.xm;
	}
	public void setXb(String xb) 
	{
		this.xb = xb;
	}
	/**
	 * 返回 性别
	 * @return
	 */
	public String getXb() 
	{
		return this.xb;
	}
	public void setCsn(String csn) 
	{
		this.csn = csn;
	}
	/**
	 * 返回 出生年
	 * @return
	 */
	public String getCsn() 
	{
		return this.csn;
	}
	public void setCsny(String csny) 
	{
		this.csny = csny;
	}
	/**
	 * 返回 出生年月
	 * @return
	 */
	public String getCsny() 
	{
		return this.csny;
	}
	public void setLxdh(String lxdh) 
	{
		this.lxdh = lxdh;
	}
	/**
	 * 返回 联系电话
	 * @return
	 */
	public String getLxdh() 
	{
		return this.lxdh;
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
		if (!(object instanceof EntryJtcy)) 
		{
			return false;
		}
		EntryJtcy rhs = (EntryJtcy) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.gx, rhs.gx)
		.append(this.xm, rhs.xm)
		.append(this.xb, rhs.xb)
		.append(this.csn, rhs.csn)
		.append(this.csny, rhs.csny)
		.append(this.lxdh, rhs.lxdh)
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
		.append(this.gx) 
		.append(this.xm) 
		.append(this.xb) 
		.append(this.csn) 
		.append(this.csny) 
		.append(this.lxdh) 
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
		.append("gx", this.gx) 
		.append("xm", this.xm) 
		.append("xb", this.xb) 
		.append("csn", this.csn) 
		.append("csny", this.csny) 
		.append("lxdh", this.lxdh) 
		.append("fj", this.fj) 
		.toString();
	}

}