package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.contract.InvoiceDetail;
/**
 * 对象功能:合同付款申请 Model对象
 */
public class ContractPaymentApplication extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *申请部门ID
	 */
	protected String  departmentID;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请部门
	 */
	protected String  department;
	/**
	 *申请时间
	 */
	protected java.util.Date  application_time;
	/**
	 *紧急程度
	 */
	protected String  urgency_level;
	/**
	 *付款方式
	 */
	protected String  payment_method;
	/**
	 *收款单位
	 */
	protected String  collection_unit;
	/**
	 *开户银行
	 */
	protected String  bank_account;
	/**
	 *开户账号
	 */
	protected String  account_open;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *合同总金额
	 */
	protected String  total_money;
	/**
	 *已付款金额
	 */
	protected String  paid_money;
	/**
	 *未付款金额
	 */
	protected String  unpaid_money;
	/**
	 *本次付款金额
	 */
	protected String  this_paid_money;
	/**
	 *剩余未付款金额
	 */
	protected String  surplus_money;
	/**
	 *费用类别
	 */
	protected String  expense_category;
	/**
	 *付款银行
	 */
	protected String  payment_bank;
	/**
	 *备注
	 */
	protected String  remark;
	
	/**
	 *合同付款申请列表
	 */
	protected List<InvoiceDetail> invoiceDetailList=new ArrayList<InvoiceDetail>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 申请部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
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
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 申请部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setApplication_time(java.util.Date application_time) 
	{
		this.application_time = application_time;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getApplication_time() 
	{
		return this.application_time;
	}
	public void setUrgency_level(String urgency_level) 
	{
		this.urgency_level = urgency_level;
	}
	/**
	 * 返回 紧急程度
	 * @return
	 */
	public String getUrgency_level() 
	{
		return this.urgency_level;
	}
	public void setPayment_method(String payment_method) 
	{
		this.payment_method = payment_method;
	}
	/**
	 * 返回 付款方式
	 * @return
	 */
	public String getPayment_method() 
	{
		return this.payment_method;
	}
	public void setCollection_unit(String collection_unit) 
	{
		this.collection_unit = collection_unit;
	}
	/**
	 * 返回 收款单位
	 * @return
	 */
	public String getCollection_unit() 
	{
		return this.collection_unit;
	}
	public void setBank_account(String bank_account) 
	{
		this.bank_account = bank_account;
	}
	/**
	 * 返回 开户银行
	 * @return
	 */
	public String getBank_account() 
	{
		return this.bank_account;
	}
	public void setAccount_open(String account_open) 
	{
		this.account_open = account_open;
	}
	/**
	 * 返回 开户账号
	 * @return
	 */
	public String getAccount_open() 
	{
		return this.account_open;
	}
	public void setContract_num(String contract_num) 
	{
		this.contract_num = contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getContract_num() 
	{
		return this.contract_num;
	}
	public void setContract_name(String contract_name) 
	{
		this.contract_name = contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getContract_name() 
	{
		return this.contract_name;
	}
	public void setTotal_money(String total_money) 
	{
		this.total_money = total_money;
	}
	/**
	 * 返回 合同总金额
	 * @return
	 */
	public String getTotal_money() 
	{
		return this.total_money;
	}
	public void setPaid_money(String paid_money) 
	{
		this.paid_money = paid_money;
	}
	/**
	 * 返回 已付款金额
	 * @return
	 */
	public String getPaid_money() 
	{
		return this.paid_money;
	}
	public void setUnpaid_money(String unpaid_money) 
	{
		this.unpaid_money = unpaid_money;
	}
	/**
	 * 返回 未付款金额
	 * @return
	 */
	public String getUnpaid_money() 
	{
		return this.unpaid_money;
	}
	public void setThis_paid_money(String this_paid_money) 
	{
		this.this_paid_money = this_paid_money;
	}
	/**
	 * 返回 本次付款金额
	 * @return
	 */
	public String getThis_paid_money() 
	{
		return this.this_paid_money;
	}
	public void setSurplus_money(String surplus_money) 
	{
		this.surplus_money = surplus_money;
	}
	/**
	 * 返回 剩余未付款金额
	 * @return
	 */
	public String getSurplus_money() 
	{
		return this.surplus_money;
	}
	public void setExpense_category(String expense_category) 
	{
		this.expense_category = expense_category;
	}
	/**
	 * 返回 费用类别
	 * @return
	 */
	public String getExpense_category() 
	{
		return this.expense_category;
	}
	public void setPayment_bank(String payment_bank) 
	{
		this.payment_bank = payment_bank;
	}
	/**
	 * 返回 付款银行
	 * @return
	 */
	public String getPayment_bank() 
	{
		return this.payment_bank;
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
	public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) 
	{
		this.invoiceDetailList = invoiceDetailList;
	}
	/**
	 * 返回 合同付款申请列表
	 * @return
	 */
	public List<InvoiceDetail> getInvoiceDetailList() 
	{
		return this.invoiceDetailList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractPaymentApplication)) 
		{
			return false;
		}
		ContractPaymentApplication rhs = (ContractPaymentApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.applicant, rhs.applicant)
		.append(this.department, rhs.department)
		.append(this.application_time, rhs.application_time)
		.append(this.urgency_level, rhs.urgency_level)
		.append(this.payment_method, rhs.payment_method)
		.append(this.collection_unit, rhs.collection_unit)
		.append(this.bank_account, rhs.bank_account)
		.append(this.account_open, rhs.account_open)
		.append(this.contract_num, rhs.contract_num)
		.append(this.contract_name, rhs.contract_name)
		.append(this.total_money, rhs.total_money)
		.append(this.paid_money, rhs.paid_money)
		.append(this.unpaid_money, rhs.unpaid_money)
		.append(this.this_paid_money, rhs.this_paid_money)
		.append(this.surplus_money, rhs.surplus_money)
		.append(this.expense_category, rhs.expense_category)
		.append(this.payment_bank, rhs.payment_bank)
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
		.append(this.applicantID) 
		.append(this.departmentID) 
		.append(this.applicant) 
		.append(this.department) 
		.append(this.application_time) 
		.append(this.urgency_level) 
		.append(this.payment_method) 
		.append(this.collection_unit) 
		.append(this.bank_account) 
		.append(this.account_open) 
		.append(this.contract_num) 
		.append(this.contract_name) 
		.append(this.total_money) 
		.append(this.paid_money) 
		.append(this.unpaid_money) 
		.append(this.this_paid_money) 
		.append(this.surplus_money) 
		.append(this.expense_category) 
		.append(this.payment_bank) 
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
		.append("applicantID", this.applicantID) 
		.append("departmentID", this.departmentID) 
		.append("applicant", this.applicant) 
		.append("department", this.department) 
		.append("application_time", this.application_time) 
		.append("urgency_level", this.urgency_level) 
		.append("payment_method", this.payment_method) 
		.append("collection_unit", this.collection_unit) 
		.append("bank_account", this.bank_account) 
		.append("account_open", this.account_open) 
		.append("contract_num", this.contract_num) 
		.append("contract_name", this.contract_name) 
		.append("total_money", this.total_money) 
		.append("paid_money", this.paid_money) 
		.append("unpaid_money", this.unpaid_money) 
		.append("this_paid_money", this.this_paid_money) 
		.append("surplus_money", this.surplus_money) 
		.append("expense_category", this.expense_category) 
		.append("payment_bank", this.payment_bank) 
		.append("remark", this.remark) 
		.toString();
	}

}