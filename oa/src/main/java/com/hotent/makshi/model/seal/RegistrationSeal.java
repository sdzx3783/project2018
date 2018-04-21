package com.hotent.makshi.model.seal;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:注册证印章 Model对象
 */
public class RegistrationSeal extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *借用人ID
	 */
	protected String  borrowerID;
	/**
	 *印章编号
	 */
	protected String  regist_seal_id;
	/**
	 *借用人
	 */
	protected String  borrower;
	/**
	 *持有人
	 */
	protected Long  userId;
	/**
	 *是否删除
	 */
	protected String  deleted;
	/**
	 * 印章名称
	 * @return
	 */
	protected String seal_name;
	
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setBorrowerID(String borrowerID) 
	{
		this.borrowerID = borrowerID;
	}
	/**
	 * 返回 借用人ID
	 * @return
	 */
	public String getBorrowerID() 
	{
		return this.borrowerID;
	}
	public void setRegist_seal_id(String regist_seal_id) 
	{
		this.regist_seal_id = regist_seal_id;
	}
	/**
	 * 返回 印章编号
	 * @return
	 */
	public String getRegist_seal_id() 
	{
		return this.regist_seal_id;
	}
	public void setBorrower(String borrower) 
	{
		this.borrower = borrower;
	}
	/**
	 * 返回 借用人
	 * @return
	 */
	public String getBorrower() 
	{
		return this.borrower;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 持有人
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setDeleted(String deleted) 
	{
		this.deleted = deleted;
	}
	/**
	 * 返回 是否删除
	 * @return
	 */
	public String getDeleted() 
	{
		return this.deleted;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RegistrationSeal)) 
		{
			return false;
		}
		RegistrationSeal rhs = (RegistrationSeal) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.borrowerID, rhs.borrowerID)
		.append(this.regist_seal_id, rhs.regist_seal_id)
		.append(this.borrower, rhs.borrower)
		.append(this.userId, rhs.userId)
		.append(this.deleted, rhs.deleted)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.borrowerID) 
		.append(this.regist_seal_id) 
		.append(this.borrower) 
		.append(this.userId) 
		.append(this.deleted) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("borrowerID", this.borrowerID) 
		.append("regist_seal_id", this.regist_seal_id) 
		.append("borrower", this.borrower) 
		.append("userId", this.userId) 
		.append("deleted", this.deleted) 
		.toString();
	}

}