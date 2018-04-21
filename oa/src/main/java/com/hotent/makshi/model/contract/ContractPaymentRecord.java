package com.hotent.makshi.model.contract;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:合同付款记录 Model对象
 */
public class ContractPaymentRecord extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *申请付款时间
	 */
	protected java.util.Date  apply_payment_date;
	/**
	 *申请付款金额
	 */
	protected String  apply_payment_money;
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
	 *付款时间
	 */
	protected java.util.Date  payment_time;
	/**
	 *实际支付金额
	 */
	protected String  actual_amount_paid;
	
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
	
	public void setApply_payment_date(java.util.Date apply_payment_date) 
	{
		this.apply_payment_date = apply_payment_date;
	}
	/**
	 * 返回 申请付款时间
	 * @return
	 */
	public java.util.Date getApply_payment_date() 
	{
		return this.apply_payment_date;
	}
	public void setApply_payment_money(String apply_payment_money) 
	{
		this.apply_payment_money = apply_payment_money;
	}
	/**
	 * 返回 申请付款金额
	 * @return
	 */
	public String getApply_payment_money() 
	{
		return this.apply_payment_money;
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
	public void setPayment_time(java.util.Date payment_time) 
	{
		this.payment_time = payment_time;
	}
	/**
	 * 返回 付款时间
	 * @return
	 */
	public java.util.Date getPayment_time() 
	{
		return this.payment_time;
	}
	public void setActual_amount_paid(String actual_amount_paid) 
	{
		this.actual_amount_paid = actual_amount_paid;
	}
	/**
	 * 返回 实际支付金额
	 * @return
	 */
	public String getActual_amount_paid() 
	{
		return this.actual_amount_paid;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractPaymentRecord)) 
		{
			return false;
		}
		ContractPaymentRecord rhs = (ContractPaymentRecord) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.apply_payment_date, rhs.apply_payment_date)
		.append(this.apply_payment_money, rhs.apply_payment_money)
		.append(this.operator, rhs.operator)
		.append(this.operatorID, rhs.operatorID)
		.append(this.status, rhs.status)
		.append(this.payment_time, rhs.payment_time)
		.append(this.actual_amount_paid, rhs.actual_amount_paid)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.apply_payment_date) 
		.append(this.apply_payment_money) 
		.append(this.operator) 
		.append(this.operatorID) 
		.append(this.status) 
		.append(this.payment_time) 
		.append(this.actual_amount_paid) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("apply_payment_date", this.apply_payment_date) 
		.append("apply_payment_money", this.apply_payment_money) 
		.append("operator", this.operator) 
		.append("operatorID", this.operatorID) 
		.append("status", this.status) 
		.append("payment_time", this.payment_time) 
		.append("actual_amount_paid", this.actual_amount_paid) 
		.toString();
	}
	
	
	public ContractPaymentRecord(){
		
	}
	
	public ContractPaymentRecord( Long refId, Date apply_payment_date,
			String apply_payment_money, String operator, String operatorID,
			String status) {
		super();
		this.refId = refId;
		this.apply_payment_date = apply_payment_date;
		this.apply_payment_money = apply_payment_money;
		this.operator = operator;
		this.operatorID = operatorID;
		this.status = status;
	}	
	
	public ContractPaymentRecord( Long refId, Date apply_payment_date,
			String apply_payment_money, String operator, String operatorID,
			String status, Date payment_time, String actual_amount_paid) {
		super();
		this.refId = refId;
		this.apply_payment_date = apply_payment_date;
		this.apply_payment_money = apply_payment_money;
		this.operator = operator;
		this.operatorID = operatorID;
		this.status = status;
		this.payment_time = payment_time;
		this.actual_amount_paid = actual_amount_paid;
	}

	
	
}