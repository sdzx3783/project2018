package com.hotent.makshi.model.userinfo;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:注册资格 Model对象
 */
public class RegistrationQualification extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8128177140078511220L;
	//主键
	protected Long id;
	protected Long refId;
	protected Long userId;
	protected Long linkId;
	
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	/**
	 *注册证书编号
	 */
	protected String  certificate_regist_id;
	/**
	 *注册专业
	 */
	protected String  regist_major;
	/**
	 *第二专业
	 */
	protected String  regist_secondMajor;
	/**
	 *第三专业
	 */
	protected String  regist_thirdMajor;
	/**
	 *发证机构
	 */
	protected String  send_unit;
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
	/**
	 *发证机构
	 */
	protected String  regist_send_unit;
	/**
	 * 借用人
	 * @return
	 */
	protected String isBorrowed;
	/**
	 * 借用人id
	 * @return
	 */
	protected String borrower;
	
	protected String switchs;
	
	
	public String getRegist_secondMajor() {
		return regist_secondMajor;
	}
	public void setRegist_secondMajor(String regist_secondMajor) {
		this.regist_secondMajor = regist_secondMajor;
	}
	public String getRegist_thirdMajor() {
		return regist_thirdMajor;
	}
	public void setRegist_thirdMajor(String regist_thirdMajor) {
		this.regist_thirdMajor = regist_thirdMajor;
	}
	public String getSwitchs() {
		return switchs;
	}
	public void setSwitchs(String switchs) {
		this.switchs = switchs;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getSend_unit() {
		return send_unit;
	}
	public void setSend_unit(String send_unit) {
		this.send_unit = send_unit;
	}
	public String getIsBorrowed() {
		return isBorrowed;
	}
	public void setIsBorrowed(String string) {
		this.isBorrowed = string;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RegistrationQualification)) 
		{
			return false;
		}
		RegistrationQualification rhs = (RegistrationQualification) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.certificate_regist_id, rhs.certificate_regist_id)
		.append(this.regist_major, rhs.regist_major)
		.append(this.get_date, rhs.get_date)
		.append(this.last_effectice_date, rhs.last_effectice_date)
		.append(this.isregist, rhs.isregist)
		.append(this.regist_attachment, rhs.regist_attachment)
		.append(this.regist_send_unit, rhs.regist_send_unit)
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
		.append(this.get_date) 
		.append(this.last_effectice_date) 
		.append(this.isregist) 
		.append(this.regist_attachment) 
		.append(this.regist_send_unit) 
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
		.append("get_date", this.get_date) 
		.append("last_effectice_date", this.last_effectice_date) 
		.append("isregist", this.isregist) 
		.append("regist_attachment", this.regist_attachment) 
		.append("regist_send_unit", this.regist_send_unit) 
		.toString();
	}

}