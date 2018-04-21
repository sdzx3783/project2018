package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:注册资格 Model对象
 */
public class EntryZzzg extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *注册证书编号
	 */
	protected String  certificate_regist_id;
	/**
	 *注册专业
	 */
	protected String  regist_major;
	/**
	 *发证机构
	 */
	protected String  regist_send_unit;
	/**
	 *发证日期
	 */
	protected java.util.Date  get_date;
	/**
	 *证书有效期
	 */
	protected java.util.Date  last_effectice_date;
	/**
	 *是否注册
	 */
	protected String  isregist;
	/**
	 *附件
	 */
	protected String  regist_attachment;
	
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
	
	public void setCertificate_regist_id(String certificate_regist_id) 
	{
		this.certificate_regist_id = certificate_regist_id;
	}
	/**
	 * 返回 注册证书编号
	 * @return
	 */
	public String getCertificate_regist_id() 
	{
		return this.certificate_regist_id;
	}
	public void setRegist_major(String regist_major) 
	{
		this.regist_major = regist_major;
	}
	/**
	 * 返回 注册专业
	 * @return
	 */
	public String getRegist_major() 
	{
		return this.regist_major;
	}
	public void setRegist_send_unit(String regist_send_unit) 
	{
		this.regist_send_unit = regist_send_unit;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getRegist_send_unit() 
	{
		return this.regist_send_unit;
	}
	public void setGet_date(java.util.Date get_date) 
	{
		this.get_date = get_date;
	}
	/**
	 * 返回 发证日期
	 * @return
	 */
	public java.util.Date getGet_date() 
	{
		return this.get_date;
	}
	public void setLast_effectice_date(java.util.Date last_effectice_date) 
	{
		this.last_effectice_date = last_effectice_date;
	}
	/**
	 * 返回 证书有效期
	 * @return
	 */
	public java.util.Date getLast_effectice_date() 
	{
		return this.last_effectice_date;
	}
	public void setIsregist(String isregist) 
	{
		this.isregist = isregist;
	}
	/**
	 * 返回 是否注册
	 * @return
	 */
	public String getIsregist() 
	{
		return this.isregist;
	}
	public void setRegist_attachment(String regist_attachment) 
	{
		this.regist_attachment = regist_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getRegist_attachment() 
	{
		return this.regist_attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof EntryZzzg)) 
		{
			return false;
		}
		EntryZzzg rhs = (EntryZzzg) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.certificate_regist_id, rhs.certificate_regist_id)
		.append(this.regist_major, rhs.regist_major)
		.append(this.regist_send_unit, rhs.regist_send_unit)
		.append(this.get_date, rhs.get_date)
		.append(this.last_effectice_date, rhs.last_effectice_date)
		.append(this.isregist, rhs.isregist)
		.append(this.regist_attachment, rhs.regist_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.certificate_regist_id) 
		.append(this.regist_major) 
		.append(this.regist_send_unit) 
		.append(this.get_date) 
		.append(this.last_effectice_date) 
		.append(this.isregist) 
		.append(this.regist_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("certificate_regist_id", this.certificate_regist_id) 
		.append("regist_major", this.regist_major) 
		.append("regist_send_unit", this.regist_send_unit) 
		.append("get_date", this.get_date) 
		.append("last_effectice_date", this.last_effectice_date) 
		.append("isregist", this.isregist) 
		.append("regist_attachment", this.regist_attachment) 
		.toString();
	}

}