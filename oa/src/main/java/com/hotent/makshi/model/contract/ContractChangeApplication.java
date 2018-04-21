package com.hotent.makshi.model.contract;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:合同人员变更申请 Model对象
 */
public class ContractChangeApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *变更前
	 */
	protected String  change_before;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *变更后
	 */
	protected String  change_after;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *变更时间
	 */
	protected java.util.Date  change_time;
	/**
	 *合同相关人员
	 */
	protected String  contract_related_person;
	/**
	 *申请原因
	 */
	protected String  application_reason;
	/**
	 *变更内容
	 */
	protected String  change_content;
	/**
	 *附件
	 */
	protected String  file;
	
	protected String  contract_account;
	
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContract_account() {
		return contract_account;
	}
	public void setContract_account(String contract_account) {
		this.contract_account = contract_account;
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
	public void setChange_before(String change_before) 
	{
		this.change_before = change_before;
	}
	/**
	 * 返回 变更前
	 * @return
	 */
	public String getChange_before() 
	{
		return this.change_before;
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
	public void setChange_after(String change_after) 
	{
		this.change_after = change_after;
	}
	/**
	 * 返回 变更后
	 * @return
	 */
	public String getChange_after() 
	{
		return this.change_after;
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
	public void setChange_time(java.util.Date change_time) 
	{
		this.change_time = change_time;
	}
	/**
	 * 返回 变更时间
	 * @return
	 */
	public java.util.Date getChange_time() 
	{
		return this.change_time;
	}
	public void setContract_related_person(String contract_related_person) 
	{
		this.contract_related_person = contract_related_person;
	}
	/**
	 * 返回 合同相关人员
	 * @return
	 */
	public String getContract_related_person() 
	{
		return this.contract_related_person;
	}
	public void setApplication_reason(String application_reason) 
	{
		this.application_reason = application_reason;
	}
	/**
	 * 返回 申请原因
	 * @return
	 */
	public String getApplication_reason() 
	{
		return this.application_reason;
	}
	public void setChange_content(String change_content) 
	{
		this.change_content = change_content;
	}
	/**
	 * 返回 变更内容
	 * @return
	 */
	public String getChange_content() 
	{
		return this.change_content;
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
		if (!(object instanceof ContractChangeApplication)) 
		{
			return false;
		}
		ContractChangeApplication rhs = (ContractChangeApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.contract_num, rhs.contract_num)
		.append(this.change_before, rhs.change_before)
		.append(this.contract_name, rhs.contract_name)
		.append(this.change_after, rhs.change_after)
		.append(this.applicant, rhs.applicant)
		.append(this.change_time, rhs.change_time)
		.append(this.contract_related_person, rhs.contract_related_person)
		.append(this.application_reason, rhs.application_reason)
		.append(this.change_content, rhs.change_content)
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
		.append(this.contract_num) 
		.append(this.change_before) 
		.append(this.contract_name) 
		.append(this.change_after) 
		.append(this.applicant) 
		.append(this.change_time) 
		.append(this.contract_related_person) 
		.append(this.application_reason) 
		.append(this.change_content) 
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
		.append("contract_num", this.contract_num) 
		.append("change_before", this.change_before) 
		.append("contract_name", this.contract_name) 
		.append("change_after", this.change_after) 
		.append("applicant", this.applicant) 
		.append("change_time", this.change_time) 
		.append("contract_related_person", this.contract_related_person) 
		.append("application_reason", this.application_reason) 
		.append("change_content", this.change_content) 
		.append("file", this.file) 
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