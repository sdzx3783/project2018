package com.hotent.makshi.model.seal;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:公章外借申请表 Model对象
 */
public class SealBorrow extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *申请部门ID
	 */
	protected String  departmentID;
	/**
	 *申请人
	 */
	protected String  application_person;
	/**
	 *申请部门
	 */
	protected String  department;
	/**
	 *外出携带公章事由
	 */
	protected String  reason;
	/**
	 *前往单位
	 */
	protected String  go_to_unit;
	/**
	 *使用印章名称
	 */
	protected String  seal_name;
	/**
	 *借出时间
	 */
	protected java.util.Date  borrow_time;
	/**
	 *预计归还时间
	 */
	protected java.util.Date  return_time;
	/**
	 *上传附件
	 */
	protected String  attachment;
	protected Long  runId=0L;
	
	protected String globalNo;
	protected java.util.Date createTime;
	protected String status;
	protected java.util.Date backDate;
	
	
	public java.util.Date getBackDate() {
		return backDate;
	}
	public void setBackDate(java.util.Date backDate) {
		this.backDate = backDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGlobalNo() {
		return globalNo;
	}
	public void setGlobalNo(String globalNo) {
		this.globalNo = globalNo;
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
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 外出携带公章事由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setGo_to_unit(String go_to_unit) 
	{
		this.go_to_unit = go_to_unit;
	}
	/**
	 * 返回 前往单位
	 * @return
	 */
	public String getGo_to_unit() 
	{
		return this.go_to_unit;
	}
	public void setSeal_name(String seal_name) 
	{
		this.seal_name = seal_name;
	}
	/**
	 * 返回 使用印章名称
	 * @return
	 */
	public String getSeal_name() 
	{
		return this.seal_name;
	}
	public void setBorrow_time(java.util.Date borrow_time) 
	{
		this.borrow_time = borrow_time;
	}
	/**
	 * 返回 借出时间
	 * @return
	 */
	public java.util.Date getBorrow_time() 
	{
		return this.borrow_time;
	}
	public void setReturn_time(java.util.Date return_time) 
	{
		this.return_time = return_time;
	}
	/**
	 * 返回 预计归还时间
	 * @return
	 */
	public java.util.Date getReturn_time() 
	{
		return this.return_time;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 上传附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
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
		if (!(object instanceof SealBorrow)) 
		{
			return false;
		}
		SealBorrow rhs = (SealBorrow) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.remark, rhs.remark)
		.append(this.departmentID, rhs.departmentID)
		.append(this.application_person, rhs.application_person)
		.append(this.department, rhs.department)
		.append(this.reason, rhs.reason)
		.append(this.go_to_unit, rhs.go_to_unit)
		.append(this.seal_name, rhs.seal_name)
		.append(this.borrow_time, rhs.borrow_time)
		.append(this.return_time, rhs.return_time)
		.append(this.attachment, rhs.attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.remark) 
		.append(this.departmentID) 
		.append(this.application_person) 
		.append(this.department) 
		.append(this.reason) 
		.append(this.go_to_unit) 
		.append(this.seal_name) 
		.append(this.borrow_time) 
		.append(this.return_time) 
		.append(this.attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("remark", this.remark) 
		.append("departmentID", this.departmentID) 
		.append("application_person", this.application_person) 
		.append("department", this.department) 
		.append("reason", this.reason) 
		.append("go_to_unit", this.go_to_unit) 
		.append("seal_name", this.seal_name) 
		.append("borrow_time", this.borrow_time) 
		.append("return_time", this.return_time) 
		.append("attachment", this.attachment) 
		.toString();
	}

}