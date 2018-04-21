package com.hotent.makshi.model.contract;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:合同作废申请 Model对象
 */
public class ContractCancelApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *归属部门ID
	 */
	protected String  contract_departmentID;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *归属部门
	 */
	protected String  contract_department;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *项目总监
	 */
	protected String  project_director;
	/**
	 *甲方
	 */
	protected String  first_party;
	/**
	 *投资总额（万元）
	 */
	protected String  total_investment;
	/**
	 *签约时间
	 */
	protected java.util.Date  singing_time;
	
	protected String  project_directorID;
	
	protected String  handle_person;
	
	protected String  handle_personID;
	
	protected String  responsible_person;
	
	protected String  responsible_personID;
	
	protected String  account;
	
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	/**
	 *申请作废原因
	 */
	protected String  cancel_reason;
	
	
	protected Long  runId=0L;
	
	
	
	public String getProject_directorID() {
		return project_directorID;
	}
	public void setProject_directorID(String project_directorID) {
		this.project_directorID = project_directorID;
	}
	public String getHandle_person() {
		return handle_person;
	}
	public void setHandle_person(String handle_person) {
		this.handle_person = handle_person;
	}
	public String getHandle_personID() {
		return handle_personID;
	}
	public void setHandle_personID(String handle_personID) {
		this.handle_personID = handle_personID;
	}
	public String getResponsible_person() {
		return responsible_person;
	}
	public void setResponsible_person(String responsible_person) {
		this.responsible_person = responsible_person;
	}
	public String getResponsible_personID() {
		return responsible_personID;
	}
	public void setResponsible_personID(String responsible_personID) {
		this.responsible_personID = responsible_personID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setContract_departmentID(String contract_departmentID) 
	{
		this.contract_departmentID = contract_departmentID;
	}
	/**
	 * 返回 归属部门ID
	 * @return
	 */
	public String getContract_departmentID() 
	{
		return this.contract_departmentID;
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
	public void setContract_department(String contract_department) 
	{
		this.contract_department = contract_department;
	}
	/**
	 * 返回 归属部门
	 * @return
	 */
	public String getContract_department() 
	{
		return this.contract_department;
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
	public void setProject_director(String project_director) 
	{
		this.project_director = project_director;
	}
	/**
	 * 返回 项目总监
	 * @return
	 */
	public String getProject_director() 
	{
		return this.project_director;
	}
	public void setFirst_party(String first_party) 
	{
		this.first_party = first_party;
	}
	/**
	 * 返回 甲方
	 * @return
	 */
	public String getFirst_party() 
	{
		return this.first_party;
	}
	public void setTotal_investment(String total_investment) 
	{
		this.total_investment = total_investment;
	}
	/**
	 * 返回 投资总额（万元）
	 * @return
	 */
	public String getTotal_investment() 
	{
		return this.total_investment;
	}
	public void setSinging_time(java.util.Date singing_time) 
	{
		this.singing_time = singing_time;
	}
	/**
	 * 返回 签约时间
	 * @return
	 */
	public Date getSinging_time() 
	{
			return this.singing_time;
	}
	public void setCancel_reason(String cancel_reason) 
	{
		this.cancel_reason = cancel_reason;
	}
	/**
	 * 返回 申请作废原因
	 * @return
	 */
	public String getCancel_reason() 
	{
		return this.cancel_reason;
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
		if (!(object instanceof ContractCancelApplication)) 
		{
			return false;
		}
		ContractCancelApplication rhs = (ContractCancelApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.contract_departmentID, rhs.contract_departmentID)
		.append(this.contract_num, rhs.contract_num)
		.append(this.contract_department, rhs.contract_department)
		.append(this.contract_name, rhs.contract_name)
		.append(this.project_director, rhs.project_director)
		.append(this.first_party, rhs.first_party)
		.append(this.total_investment, rhs.total_investment)
		.append(this.singing_time, rhs.singing_time)
		.append(this.cancel_reason, rhs.cancel_reason)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.contract_departmentID) 
		.append(this.contract_num) 
		.append(this.contract_department) 
		.append(this.contract_name) 
		.append(this.project_director) 
		.append(this.first_party) 
		.append(this.total_investment) 
		.append(this.singing_time) 
		.append(this.cancel_reason) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("contract_departmentID", this.contract_departmentID) 
		.append("contract_num", this.contract_num) 
		.append("contract_department", this.contract_department) 
		.append("contract_name", this.contract_name) 
		.append("project_director", this.project_director) 
		.append("first_party", this.first_party) 
		.append("total_investment", this.total_investment) 
		.append("singing_time", this.singing_time) 
		.append("cancel_reason", this.cancel_reason) 
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
	

}