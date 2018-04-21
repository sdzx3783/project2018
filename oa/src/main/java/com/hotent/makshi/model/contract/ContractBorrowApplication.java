package com.hotent.makshi.model.contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.utils.DateUtils;
/**
 * 对象功能:合同借阅申请 Model对象
 */
public class ContractBorrowApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *借阅人ID
	 */
	protected String  borrowerID;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *借阅日期
	 */
	protected java.util.Date  borrow_date;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *预计归还日期
	 */
	protected java.util.Date  expected_return_date;
	/**
	 *借阅人
	 */
	protected String  borrower;
	/**
	 *备注
	 */
	protected String  remark;
	
	protected String contract_amount;
	
	protected String  borrow_reason;
	
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	//归还时间
	protected java.util.Date return_date;
	
	protected Long  runId=0L;
	
	
	
	
	
	public String getContract_amount() {
		return contract_amount;
	}
	public void setContract_amount(String contract_amount) {
		this.contract_amount = contract_amount;
	}
	public String getBorrow_reason() {
		return borrow_reason;
	}
	public void setBorrow_reason(String borrow_reason) {
		this.borrow_reason = borrow_reason;
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
	 * 返回 借阅人ID
	 * @return
	 */
	public String getBorrowerID() 
	{
		return this.borrowerID;
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
	public void setExpected_return_date(java.util.Date expected_return_date) 
	{
		this.expected_return_date = expected_return_date;
	}
	/**
	 * 返回 预计归还日期
	 * @return
	 */
	public Date getExpected_return_date() 
	{
			return this.expected_return_date;
	}
	public void setBorrower(String borrower) 
	{
		this.borrower = borrower;
	}
	/**
	 * 返回 借阅人
	 * @return
	 */
	public String getBorrower() 
	{
		return this.borrower;
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
		if (!(object instanceof ContractBorrowApplication)) 
		{
			return false;
		}
		ContractBorrowApplication rhs = (ContractBorrowApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.borrowerID, rhs.borrowerID)
		.append(this.contract_num, rhs.contract_num)
		.append(this.borrow_date, rhs.borrow_date)
		.append(this.contract_name, rhs.contract_name)
		.append(this.expected_return_date, rhs.expected_return_date)
		.append(this.borrower, rhs.borrower)
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
		.append(this.borrowerID) 
		.append(this.contract_num) 
		.append(this.borrow_date) 
		.append(this.contract_name) 
		.append(this.expected_return_date) 
		.append(this.borrower) 
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
		.append("borrowerID", this.borrowerID) 
		.append("contract_num", this.contract_num) 
		.append("borrow_date", this.borrow_date) 
		.append("contract_name", this.contract_name) 
		.append("expected_return_date", this.expected_return_date) 
		.append("borrower", this.borrower) 
		.append("remark", this.remark) 
		.toString();
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
			return this.createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public java.util.Date getReturn_date() {
		return return_date;
	}
	public void setReturn_date(java.util.Date return_date) {
		this.return_date = return_date;
	}
	
	

}