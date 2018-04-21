package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:发票明细 Model对象
 */
public class InvoiceDetail extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *发票号码
	 */
	protected String  invoice_num;
	/**
	 *发票金额
	 */
	protected String  invoice_money;
	/**
	 *发票日期
	 */
	protected java.util.Date  invoice_date;
	/**
	 *摘要
	 */
	protected String  abstracts;
	
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
	
	public void setInvoice_num(String invoice_num) 
	{
		this.invoice_num = invoice_num;
	}
	/**
	 * 返回 发票号码
	 * @return
	 */
	public String getInvoice_num() 
	{
		return this.invoice_num;
	}
	public void setInvoice_money(String invoice_money) 
	{
		this.invoice_money = invoice_money;
	}
	/**
	 * 返回 发票金额
	 * @return
	 */
	public String getInvoice_money() 
	{
		return this.invoice_money;
	}
	public void setInvoice_date(java.util.Date invoice_date) 
	{
		this.invoice_date = invoice_date;
	}
	/**
	 * 返回 发票日期
	 * @return
	 */
	public java.util.Date getInvoice_date() 
	{
		return this.invoice_date;
	}
	public void setabstracts(String abstracts) 
	{
		this.abstracts = abstracts;
	}
	/**
	 * 返回 摘要
	 * @return
	 */
	public String getabstracts() 
	{
		return this.abstracts;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof InvoiceDetail)) 
		{
			return false;
		}
		InvoiceDetail rhs = (InvoiceDetail) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.invoice_num, rhs.invoice_num)
		.append(this.invoice_money, rhs.invoice_money)
		.append(this.invoice_date, rhs.invoice_date)
		.append(this.abstracts, rhs.abstracts)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.invoice_num) 
		.append(this.invoice_money) 
		.append(this.invoice_date) 
		.append(this.abstracts) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("invoice_num", this.invoice_num) 
		.append("invoice_money", this.invoice_money) 
		.append("invoice_date", this.invoice_date) 
		.append("abstracts", this.abstracts) 
		.toString();
	}

}