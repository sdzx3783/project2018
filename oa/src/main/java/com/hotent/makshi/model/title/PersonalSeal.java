package com.hotent.makshi.model.title;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:个人执业印章 Model对象
 */
public class PersonalSeal extends BaseModel
{
	//主键
	protected Long id;
	/**
	 * linkId
	 */
	protected Long linkId;
	/**
	 *印章编号
	 */
	protected String  seal_num;
	/**
	 *印章名称
	 */
	protected String  seal_name;
	/**
	 *当前借用人
	 */
	protected String  borrower;
	protected String  borrowerID;
	
    protected java.util.Date borrowDate; 
	/**
	 *所有人
	 */
	protected String  holder;
	protected String  holderID;
	/**
	 *状态
	 */
	protected String  status;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 * 有效日期
	 * @return
	 */
	protected java.util.Date effictiveDate;
	/**
	 * 是否转入
	 * @return
	 */
	protected String switchs;
	
	
	public String getSwitchs() {
		return switchs;
	}
	public java.util.Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(java.util.Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public void setSwitchs(String switchs) {
		this.switchs = switchs;
	}
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	public java.util.Date getEffictiveDate() {
		return effictiveDate;
	}
	public void setEffictiveDate(java.util.Date effictiveDate) {
		this.effictiveDate = effictiveDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSeal_num(String seal_num) 
	{
		this.seal_num = seal_num;
	}
	/**
	 * 返回 印章编号
	 * @return
	 */
	public String getSeal_num() 
	{
		return this.seal_num;
	}
	public void setSeal_name(String seal_name) 
	{
		this.seal_name = seal_name;
	}
	/**
	 * 返回 印章名称
	 * @return
	 */
	public String getSeal_name() 
	{
		return this.seal_name;
	}
	public void setBorrower(String borrower) 
	{
		this.borrower = borrower;
	}
	/**
	 * 返回 当前借用人
	 * @return
	 */
	public String getBorrower() 
	{
		return this.borrower;
	}
	public void setHolder(String holder) 
	{
		this.holder = holder;
	}
	/**
	 * 返回 所有人
	 * @return
	 */
	public String getHolder() 
	{
		return this.holder;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
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
		if (!(object instanceof PersonalSeal)) 
		{
			return false;
		}
		PersonalSeal rhs = (PersonalSeal) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.seal_num, rhs.seal_num)
		.append(this.seal_name, rhs.seal_name)
		.append(this.borrower, rhs.borrower)
		.append(this.holder, rhs.holder)
		.append(this.status, rhs.status)
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
		.append(this.seal_num) 
		.append(this.seal_name) 
		.append(this.borrower) 
		.append(this.holder) 
		.append(this.status) 
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
		.append("seal_num", this.seal_num) 
		.append("seal_name", this.seal_name) 
		.append("borrower", this.borrower) 
		.append("holder", this.holder) 
		.append("status", this.status) 
		.append("remark", this.remark) 
		.toString();
	}
	public String getBorrowerID() {
		return borrowerID;
	}
	public void setBorrowerID(String borrowerID) {
		this.borrowerID = borrowerID;
	}
	public String getHolderID() {
		return holderID;
	}
	public void setHolderID(String holderID) {
		this.holderID = holderID;
	}

}