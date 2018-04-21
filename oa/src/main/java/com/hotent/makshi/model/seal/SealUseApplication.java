package com.hotent.makshi.model.seal;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:公章使用申请表 Model对象
 */
public class SealUseApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *是否直接盖章
	 */
	protected String  direct_seal;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *申请时间
	 */
	protected java.util.Date  appiDate;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *申请部门ID
	 */
	protected String  departmentID;
	/**
	 *员工编号
	 */
	protected String  usreId;
	/**
	 *申请人
	 */
	protected String  application_person;
	/**
	 *申请部门
	 */
	protected String  department;
	/**
	 *材料内容
	 */
	protected String  contents;
	/**
	 *份数
	 */
	protected String  number;
	/**
	 *关联合同编号
	 */
	protected String  contract_id;
	/**
	 *关联合同名称
	 */
	protected String  contract_name;
	/**
	 *工程进度
	 */
	protected String  project_point;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *类型
	 */
	protected String  type;
	protected Long  runId=0L;
	
	protected String glowbalNo;
	
	protected java.util.Date createTime;
	
	protected String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGlowbalNo() {
		return glowbalNo;
	}
	public void setGlowbalNo(String glowbalNo) {
		this.glowbalNo = glowbalNo;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDirect_seal(String direct_seal) 
	{
		this.direct_seal = direct_seal;
	}
	/**
	 * 返回 是否直接盖章
	 * @return
	 */
	public String getDirect_seal() 
	{
		return this.direct_seal;
	}
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
	}
	public void setAppiDate(java.util.Date appiDate) 
	{
		this.appiDate = appiDate;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getAppiDate() 
	{
		return this.appiDate;
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
	public void setUsreId(String usreId) 
	{
		this.usreId = usreId;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUsreId() 
	{
		return this.usreId;
	}
	public void setApplication_person(String application_person) 
	{
		this.application_person = application_person;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplication_person() 
	{
		return this.application_person;
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
	public void setContents(String contents) 
	{
		this.contents = contents;
	}
	/**
	 * 返回 材料内容
	 * @return
	 */
	public String getContents() 
	{
		return this.contents;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}
	/**
	 * 返回 份数
	 * @return
	 */
	public String getNumber() 
	{
		return this.number;
	}
	public void setContract_id(String contract_id) 
	{
		this.contract_id = contract_id;
	}
	/**
	 * 返回 关联合同编号
	 * @return
	 */
	public String getContract_id() 
	{
		return this.contract_id;
	}
	public void setContract_name(String contract_name) 
	{
		this.contract_name = contract_name;
	}
	/**
	 * 返回 关联合同名称
	 * @return
	 */
	public String getContract_name() 
	{
		return this.contract_name;
	}
	public void setProject_point(String project_point) 
	{
		this.project_point = project_point;
	}
	/**
	 * 返回 工程进度
	 * @return
	 */
	public String getProject_point() 
	{
		return this.project_point;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
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
		if (!(object instanceof SealUseApplication)) 
		{
			return false;
		}
		SealUseApplication rhs = (SealUseApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.direct_seal, rhs.direct_seal)
		.append(this.account, rhs.account)
		.append(this.appiDate, rhs.appiDate)
		.append(this.remark, rhs.remark)
		.append(this.departmentID, rhs.departmentID)
		.append(this.usreId, rhs.usreId)
		.append(this.application_person, rhs.application_person)
		.append(this.department, rhs.department)
		.append(this.contents, rhs.contents)
		.append(this.number, rhs.number)
		.append(this.contract_id, rhs.contract_id)
		.append(this.contract_name, rhs.contract_name)
		.append(this.project_point, rhs.project_point)
		.append(this.attachment, rhs.attachment)
		.append(this.type, rhs.type)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.direct_seal) 
		.append(this.account) 
		.append(this.appiDate) 
		.append(this.remark) 
		.append(this.departmentID) 
		.append(this.usreId) 
		.append(this.application_person) 
		.append(this.department) 
		.append(this.contents) 
		.append(this.number) 
		.append(this.contract_id) 
		.append(this.contract_name) 
		.append(this.project_point) 
		.append(this.attachment) 
		.append(this.type) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("direct_seal", this.direct_seal) 
		.append("account", this.account) 
		.append("appiDate", this.appiDate) 
		.append("remark", this.remark) 
		.append("departmentID", this.departmentID) 
		.append("usreId", this.usreId) 
		.append("application_person", this.application_person) 
		.append("department", this.department) 
		.append("contents", this.contents) 
		.append("number", this.number) 
		.append("contract_id", this.contract_id) 
		.append("contract_name", this.contract_name) 
		.append("project_point", this.project_point) 
		.append("attachment", this.attachment) 
		.append("type", this.type) 
		.toString();
	}

}