package com.hotent.makshi.model.contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.utils.DateUtils;
/**
 * 对象功能:合同开票申请 Model对象
 */
public class ContractBillingApplication extends BaseModel
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
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *开票类型
	 */
	protected String  billing_type;
	/**
	 *发票抬头
	 */
	protected String  head;
	/**
	 *摘要
	 */
	protected String  abstracts;
	/**
	 *开票金额
	 */
	protected String  billing_money;
	/**
	 *税号
	 */
	protected String  tax_num;
	/**
	 *发票号
	 */
	protected String  billing_num;
	/**
	 *开户银行
	 */
	protected String  bank_account;
	/**
	 *开户账号
	 */
	protected String  account_open;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  file;
	
	/**
	 *合同开票申请列表
	 */
	protected List<Enterinfo> enterinfoList=new ArrayList<Enterinfo>();
	
	
	
	public List<Enterinfo> getEnterinfoList() {
		return enterinfoList;
	}
	public void setEnterinfoList(List<Enterinfo> enterinfoList) {
		this.enterinfoList = enterinfoList;
	}

	protected String buyerName;
	protected String goodsName;
	protected String taxNum;
	protected String buyerAddress;
	protected String buyerPhone;
	protected String buyerBank;
	protected String buyerAccount;
	protected String salerBank;
	protected String salerAccount;
	protected String ticketId;
	protected String ticketTaker;
	protected String ticketTakerID;
	protected java.util.Date enterTime;
	protected Double enterNumber;
	
	private String globalflowno;
	
	public String getGlobalflowno() {
		return globalflowno;
	}
	public void setGlobalflowno(String globalflowno) {
		this.globalflowno = globalflowno;
	}
	public String getBuyerAccount() {
		return buyerAccount;
	}
	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTaxNum() {
		return taxNum;
	}
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getBuyerBank() {
		return buyerBank;
	}
	public void setBuyerBank(String buyerBank) {
		this.buyerBank = buyerBank;
	}
	public String getSalerBank() {
		return salerBank;
	}
	public void setSalerBank(String salerBank) {
		this.salerBank = salerBank;
	}
	public String getSalerAccount() {
		return salerAccount;
	}
	public void setSalerAccount(String salerAccount) {
		this.salerAccount = salerAccount;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketTaker() {
		return ticketTaker;
	}
	public void setTicketTaker(String ticketTaker) {
		this.ticketTaker = ticketTaker;
	}
	public String getTicketTakerID() {
		return ticketTakerID;
	}
	public void setTicketTakerID(String ticketTakerID) {
		this.ticketTakerID = ticketTakerID;
	}
	public java.util.Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(java.util.Date enterTime) {
		this.enterTime = enterTime;
	}
	public Double getEnterNumber() {
		return enterNumber;
	}
	public void setEnterNumber(Double enterNumber) {
		this.enterNumber = enterNumber;
	}
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
	public void setBilling_type(String billing_type) 
	{
		this.billing_type = billing_type;
	}
	/**
	 * 返回 开票类型
	 * @return
	 */
	public String getBilling_type() 
	{
		return this.billing_type;
	}
	public void setHead(String head) 
	{
		this.head = head;
	}
	/**
	 * 返回 发票抬头
	 * @return
	 */
	public String getHead() 
	{
		return this.head;
	}
	
	public void setBilling_money(String billing_money) 
	{
		this.billing_money = billing_money;
	}
	/**
	 * 返回 开票金额
	 * @return
	 */
	public String getBilling_money() 
	{
		return this.billing_money;
	}
	public void setTax_num(String tax_num) 
	{
		this.tax_num = tax_num;
	}
	/**
	 * 返回 税号
	 * @return
	 */
	public String getTax_num() 
	{
		return this.tax_num;
	}
	public void setBilling_num(String billing_num) 
	{
		this.billing_num = billing_num;
	}
	/**
	 * 返回 发票号
	 * @return
	 */
	public String getBilling_num() 
	{
		return this.billing_num;
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
	public void setFile(String file) 
	{
		this.file = file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFile() 
	{
		return this.file;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractBillingApplication)) 
		{
			return false;
		}
		ContractBillingApplication rhs = (ContractBillingApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.applicant, rhs.applicant)
		.append(this.department, rhs.department)
		.append(this.application_time, rhs.application_time)
		.append(this.urgency_level, rhs.urgency_level)
		.append(this.contract_num, rhs.contract_num)
		.append(this.contract_name, rhs.contract_name)
		.append(this.billing_type, rhs.billing_type)
		.append(this.head, rhs.head)
		.append(this.abstracts, rhs.abstracts)
		.append(this.billing_money, rhs.billing_money)
		.append(this.tax_num, rhs.tax_num)
		.append(this.billing_num, rhs.billing_num)
		.append(this.bank_account, rhs.bank_account)
		.append(this.account_open, rhs.account_open)
		.append(this.remark, rhs.remark)
		.append(this.file, rhs.file)
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
		.append(this.contract_num) 
		.append(this.contract_name) 
		.append(this.billing_type) 
		.append(this.head) 
		.append(this.abstracts) 
		.append(this.billing_money) 
		.append(this.tax_num) 
		.append(this.billing_num) 
		.append(this.bank_account) 
		.append(this.account_open) 
		.append(this.remark) 
		.append(this.file) 
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
		.append("contract_num", this.contract_num) 
		.append("contract_name", this.contract_name) 
		.append("billing_type", this.billing_type) 
		.append("head", this.head) 
		.append("abstracts", this.abstracts) 
		.append("billing_money", this.billing_money) 
		.append("tax_num", this.tax_num) 
		.append("billing_num", this.billing_num) 
		.append("bank_account", this.bank_account) 
		.append("account_open", this.account_open) 
		.append("remark", this.remark) 
		.append("file", this.file) 
		.toString();
	}
	
}