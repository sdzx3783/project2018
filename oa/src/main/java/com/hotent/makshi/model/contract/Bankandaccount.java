package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:银行账户表 Model对象
 */
public class Bankandaccount extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *开户银行
	 */
	protected String  bankName;
	/**
	 *开户账户
	 */
	protected String  bankAccount;
	
	protected String  bankNameVal;
	
	
	public String getBankNameVal() {
		return bankNameVal;
	}
	public void setBankNameVal(String bankNameVal) {
		this.bankNameVal = bankNameVal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setBankName(String bankName) 
	{
		this.bankName = bankName;
	}
	/**
	 * 返回 开户银行
	 * @return
	 */
	public String getBankName() 
	{
		return this.bankName;
	}
	public void setBankAccount(String bankAccount) 
	{
		this.bankAccount = bankAccount;
	}
	/**
	 * 返回 开户账户
	 * @return
	 */
	public String getBankAccount() 
	{
		return this.bankAccount;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Bankandaccount)) 
		{
			return false;
		}
		Bankandaccount rhs = (Bankandaccount) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.bankName, rhs.bankName)
		.append(this.bankAccount, rhs.bankAccount)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.bankName) 
		.append(this.bankAccount) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("bankName", this.bankName) 
		.append("bankAccount", this.bankAccount) 
		.toString();
	}

}