package com.hotent.makshi.model.title;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:个人证书借阅申请 Model对象
 */
public class CertificateBorrow extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *持证人ID
	 */
	protected String  fullnameID;
	/**
	 *持证人
	 */
	protected String  fullname;
	/**
	 *借阅证书名称
	 */
	protected String  steal_name;
	/**
	 *申请时间
	 */
	protected java.util.Date  ap_date;
	/**
	 *借阅证书类型
	 */
	protected String  type;
	/**
	 *预计归还日期
	 */
	protected java.util.Date  return_date_expect;
	/**
	 *员工编号
	 */
	protected String  user_id;
	/**
	 *工号
	 */
	protected String  gh;
	/**
	 *执业印章有效期
	 */
	protected java.util.Date  effictiveDate;
	/**
	 *归还日期
	 */
	protected java.util.Date  return_date;
	/**
	 *借阅日期
	 */
	protected java.util.Date  borrow_date;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *借阅证书编号
	 */
	protected String  borrow_data_num;
	/**
	 *申请人
	 */
	protected String  applicant;
	protected Long  runId=0L;
	protected Integer finish ;
	protected String  attachment;
	
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFullnameID(String fullnameID) 
	{
		this.fullnameID = fullnameID;
	}
	/**
	 * 返回 持证人ID
	 * @return
	 */
	public String getFullnameID() 
	{
		return this.fullnameID;
	}
	public void setFullname(String fullname) 
	{
		this.fullname = fullname;
	}
	/**
	 * 返回 持证人
	 * @return
	 */
	public String getFullname() 
	{
		return this.fullname;
	}
	public void setSteal_name(String steal_name) 
	{
		this.steal_name = steal_name;
	}
	/**
	 * 返回 借阅证书名称
	 * @return
	 */
	public String getSteal_name() 
	{
		return this.steal_name;
	}
	public void setAp_date(java.util.Date ap_date) 
	{
		this.ap_date = ap_date;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getAp_date() 
	{
		return this.ap_date;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 借阅证书类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setReturn_date_expect(java.util.Date return_date_expect) 
	{
		this.return_date_expect = return_date_expect;
	}
	/**
	 * 返回 预计归还日期
	 * @return
	 */
	public java.util.Date getReturn_date_expect() 
	{
		return this.return_date_expect;
	}
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
	}
	public void setGh(String gh) 
	{
		this.gh = gh;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getGh() 
	{
		return this.gh;
	}
	public void setEffictiveDate(java.util.Date effictiveDate) 
	{
		this.effictiveDate = effictiveDate;
	}
	/**
	 * 返回 执业印章有效期
	 * @return
	 */
	public java.util.Date getEffictiveDate() 
	{
		return this.effictiveDate;
	}
	public void setReturn_date(java.util.Date return_date) 
	{
		this.return_date = return_date;
	}
	/**
	 * 返回 归还日期
	 * @return
	 */
	public java.util.Date getReturn_date() 
	{
		return this.return_date;
	}
	public void setBorrow_date(java.util.Date borrow_date) 
	{
		this.borrow_date = borrow_date;
	}
	/**
	 * 返回 借阅日期
	 * @return
	 */
	public java.util.Date getBorrow_date() 
	{
		return this.borrow_date;
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
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setBorrow_data_num(String borrow_data_num) 
	{
		this.borrow_data_num = borrow_data_num;
	}
	/**
	 * 返回 借阅证书编号
	 * @return
	 */
	public String getBorrow_data_num() 
	{
		return this.borrow_data_num;
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
		if (!(object instanceof CertificateBorrow)) 
		{
			return false;
		}
		CertificateBorrow rhs = (CertificateBorrow) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fullnameID, rhs.fullnameID)
		.append(this.fullname, rhs.fullname)
		.append(this.steal_name, rhs.steal_name)
		.append(this.ap_date, rhs.ap_date)
		.append(this.type, rhs.type)
		.append(this.return_date_expect, rhs.return_date_expect)
		.append(this.user_id, rhs.user_id)
		.append(this.gh, rhs.gh)
		.append(this.effictiveDate, rhs.effictiveDate)
		.append(this.return_date, rhs.return_date)
		.append(this.borrow_date, rhs.borrow_date)
		.append(this.remark, rhs.remark)
		.append(this.applicantID, rhs.applicantID)
		.append(this.borrow_data_num, rhs.borrow_data_num)
		.append(this.applicant, rhs.applicant)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.fullnameID) 
		.append(this.fullname) 
		.append(this.steal_name) 
		.append(this.ap_date) 
		.append(this.type) 
		.append(this.return_date_expect) 
		.append(this.user_id) 
		.append(this.gh) 
		.append(this.effictiveDate) 
		.append(this.return_date) 
		.append(this.borrow_date) 
		.append(this.remark) 
		.append(this.applicantID) 
		.append(this.borrow_data_num) 
		.append(this.applicant) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("fullnameID", this.fullnameID) 
		.append("fullname", this.fullname) 
		.append("steal_name", this.steal_name) 
		.append("ap_date", this.ap_date) 
		.append("type", this.type) 
		.append("return_date_expect", this.return_date_expect) 
		.append("user_id", this.user_id) 
		.append("gh", this.gh) 
		.append("effictiveDate", this.effictiveDate) 
		.append("return_date", this.return_date) 
		.append("borrow_date", this.borrow_date) 
		.append("remark", this.remark) 
		.append("applicantID", this.applicantID) 
		.append("borrow_data_num", this.borrow_data_num) 
		.append("applicant", this.applicant) 
		.toString();
	}

}