package com.hotent.makshi.model.title;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:个人执业资格初始注册及转入 Model对象
 */
public class Practiceregist extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *员工编号
	 */
	protected String  user_num;
	/**
	 *发证日期
	 */
	protected java.util.Date  issue_date;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *资格证书编号
	 */
	protected String  certificate_num;
	/**
	 *执业资格证名称
	 */
	protected String  certificate_name;
	/**
	 *有效日期
	 */
	protected java.util.Date  effective_date;
	/**
	 *注册编号
	 */
	protected String  regist_num;
	/**
	 *证件状态
	 */
	protected String  certificate_status;
	/**
	 *发证机构
	 */
	protected String  issuing_authority;
	/**
	 *是否已考过安全证
	 */
	protected String  is_safe;
	/**
	 *执业资格证专业
	 */
	protected String  major;
	/**
	 *附件
	 */
	protected String  file;
	/**
	 *备注
	 */
	protected String  remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUser_num(String user_num) 
	{
		this.user_num = user_num;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_num() 
	{
		return this.user_num;
	}
	public void setIssue_date(java.util.Date issue_date) 
	{
		this.issue_date = issue_date;
	}
	/**
	 * 返回 发证日期
	 * @return
	 */
	public java.util.Date getIssue_date() 
	{
		return this.issue_date;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setCertificate_num(String certificate_num) 
	{
		this.certificate_num = certificate_num;
	}
	/**
	 * 返回 资格证书编号
	 * @return
	 */
	public String getCertificate_num() 
	{
		return this.certificate_num;
	}
	public void setCertificate_name(String certificate_name) 
	{
		this.certificate_name = certificate_name;
	}
	/**
	 * 返回 执业资格证名称
	 * @return
	 */
	public String getCertificate_name() 
	{
		return this.certificate_name;
	}
	public void setEffective_date(java.util.Date effective_date) 
	{
		this.effective_date = effective_date;
	}
	/**
	 * 返回 有效日期
	 * @return
	 */
	public java.util.Date getEffective_date() 
	{
		return this.effective_date;
	}
	public void setRegist_num(String regist_num) 
	{
		this.regist_num = regist_num;
	}
	/**
	 * 返回 注册编号
	 * @return
	 */
	public String getRegist_num() 
	{
		return this.regist_num;
	}
	public void setCertificate_status(String certificate_status) 
	{
		this.certificate_status = certificate_status;
	}
	/**
	 * 返回 证件状态
	 * @return
	 */
	public String getCertificate_status() 
	{
		return this.certificate_status;
	}
	public void setIssuing_authority(String issuing_authority) 
	{
		this.issuing_authority = issuing_authority;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getIssuing_authority() 
	{
		return this.issuing_authority;
	}
	public void setIs_safe(String is_safe) 
	{
		this.is_safe = is_safe;
	}
	/**
	 * 返回 是否已考过安全证
	 * @return
	 */
	public String getIs_safe() 
	{
		return this.is_safe;
	}
	public void setMajor(String major) 
	{
		this.major = major;
	}
	/**
	 * 返回 执业资格证专业
	 * @return
	 */
	public String getMajor() 
	{
		return this.major;
	}
	public void setFile(String file) 
	{
		this.file = file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFile() 
	{
		return this.file;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Practiceregist)) 
		{
			return false;
		}
		Practiceregist rhs = (Practiceregist) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_num, rhs.user_num)
		.append(this.issue_date, rhs.issue_date)
		.append(this.applicant, rhs.applicant)
		.append(this.certificate_num, rhs.certificate_num)
		.append(this.certificate_name, rhs.certificate_name)
		.append(this.effective_date, rhs.effective_date)
		.append(this.regist_num, rhs.regist_num)
		.append(this.certificate_status, rhs.certificate_status)
		.append(this.issuing_authority, rhs.issuing_authority)
		.append(this.is_safe, rhs.is_safe)
		.append(this.major, rhs.major)
		.append(this.file, rhs.file)
		.append(this.remark, rhs.remark)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_num) 
		.append(this.issue_date) 
		.append(this.applicant) 
		.append(this.certificate_num) 
		.append(this.certificate_name) 
		.append(this.effective_date) 
		.append(this.regist_num) 
		.append(this.certificate_status) 
		.append(this.issuing_authority) 
		.append(this.is_safe) 
		.append(this.major) 
		.append(this.file) 
		.append(this.remark) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_num", this.user_num) 
		.append("issue_date", this.issue_date) 
		.append("applicant", this.applicant) 
		.append("certificate_num", this.certificate_num) 
		.append("certificate_name", this.certificate_name) 
		.append("effective_date", this.effective_date) 
		.append("regist_num", this.regist_num) 
		.append("certificate_status", this.certificate_status) 
		.append("issuing_authority", this.issuing_authority) 
		.append("is_safe", this.is_safe) 
		.append("major", this.major) 
		.append("file", this.file) 
		.append("remark", this.remark) 
		.toString();
	}

}