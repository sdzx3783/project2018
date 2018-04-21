package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:合同开票记录 Model对象
 */
public class ContractBillingRecord extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *开票时间
	 */
	protected java.util.Date  billing_date;
	/**
	 *发票金额
	 */
	protected String  invoice_money;
	/**
	 *经办人
	 */
	protected String  operator;
	/**
	 *经办人ID
	 */
	protected String  operatorID;
	/**
	 *审批状态
	 */
	protected String  status;
	/**
	 *领票人
	 */
	protected String  bearer;
	/**
	 *领票人ID
	 */
	protected String  bearerID;
	/**
	 *到账时间
	 */
	protected java.util.Date  payment_date;
	/**
	 *到账金额
	 */
	protected String  arrival_money;
	
	protected Long  linkId;
	
	
	
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
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
	
	public void setBilling_date(java.util.Date billing_date) 
	{
		this.billing_date = billing_date;
	}
	/**
	 * 返回 开票时间
	 * @return
	 */
	public java.util.Date getBilling_date() 
	{
		return this.billing_date;
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
	public void setOperator(String operator) 
	{
		this.operator = operator;
	}
	/**
	 * 返回 经办人
	 * @return
	 */
	public String getOperator() 
	{
		return this.operator;
	}
	public void setOperatorID(String operatorID) 
	{
		this.operatorID = operatorID;
	}
	/**
	 * 返回 经办人ID
	 * @return
	 */
	public String getOperatorID() 
	{
		return this.operatorID;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 审批状态
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setBearer(String bearer) 
	{
		this.bearer = bearer;
	}
	/**
	 * 返回 领票人
	 * @return
	 */
	public String getBearer() 
	{
		return this.bearer;
	}
	public void setBearerID(String bearerID) 
	{
		this.bearerID = bearerID;
	}
	/**
	 * 返回 领票人ID
	 * @return
	 */
	public String getBearerID() 
	{
		return this.bearerID;
	}
	public void setPayment_date(java.util.Date payment_date) 
	{
		this.payment_date = payment_date;
	}
	/**
	 * 返回 到账时间
	 * @return
	 */
	public java.util.Date getPayment_date() 
	{
		return this.payment_date;
	}
	public void setArrival_money(String arrival_money) 
	{
		this.arrival_money = arrival_money;
	}
	/**
	 * 返回 到账金额
	 * @return
	 */
	public String getArrival_money() 
	{
		return this.arrival_money;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractBillingRecord)) 
		{
			return false;
		}
		ContractBillingRecord rhs = (ContractBillingRecord) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.billing_date, rhs.billing_date)
		.append(this.invoice_money, rhs.invoice_money)
		.append(this.operator, rhs.operator)
		.append(this.operatorID, rhs.operatorID)
		.append(this.status, rhs.status)
		.append(this.bearer, rhs.bearer)
		.append(this.bearerID, rhs.bearerID)
		.append(this.payment_date, rhs.payment_date)
		.append(this.arrival_money, rhs.arrival_money)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.billing_date) 
		.append(this.invoice_money) 
		.append(this.operator) 
		.append(this.operatorID) 
		.append(this.status) 
		.append(this.bearer) 
		.append(this.bearerID) 
		.append(this.payment_date) 
		.append(this.arrival_money) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("billing_date", this.billing_date) 
		.append("invoice_money", this.invoice_money) 
		.append("operator", this.operator) 
		.append("operatorID", this.operatorID) 
		.append("status", this.status) 
		.append("bearer", this.bearer) 
		.append("bearerID", this.bearerID) 
		.append("payment_date", this.payment_date) 
		.append("arrival_money", this.arrival_money) 
		.toString();
	}
	
	
	public ContractBillingRecord(){
		
	}
	
	public ContractBillingRecord(Long refId, Date billing_date,
			String invoice_money, String operator, String operatorID,
			String status) {
		super();
		this.refId = refId;
		this.billing_date = billing_date;
		this.invoice_money = invoice_money;
		this.operator = operator;
		this.operatorID = operatorID;
		this.status = status;
	}
	
	
	public ContractBillingRecord(Long refId, Long linkId, Date billing_date,
			String invoice_money, String operator, String operatorID,
			String status, String bearer, String bearerID, Date payment_date,
			String arrival_money) {
		super();
		this.refId = refId;
		this.linkId = linkId;
		this.billing_date = billing_date;
		this.invoice_money = invoice_money;
		this.operator = operator;
		this.operatorID = operatorID;
		this.status = status;
		this.bearer = bearer;
		this.bearerID = bearerID;
		this.payment_date = payment_date;
		this.arrival_money = arrival_money;
	}
	
	

}