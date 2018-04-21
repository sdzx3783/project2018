package com.hotent.makshi.model.hr;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
import com.hotent.core.util.TimeUtil;
/**
 * 对象功能:员工离职表 Model对象
 */
public class UserResignation extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *员工姓名
	 */
	protected String  user_name;
	/**
	 *所在部门
	 */
	protected String  department;
	/**
	 *员工编号
	 */
	protected String  user_id;
	/**
	 *新手机号
	 */
	protected String  user_new_phone_number;
	/**
	 *入职时间
	 */
	protected String  entrydate_time;
	/**
	 *解除劳动合同时间
	 */
	protected String  terminame_time;
	/**
	 *解除劳动合同原因
	 */
	protected String  reason;
	/**
	 *项目部资料移交情况
	 */
	protected String  department_transfer_status;
	/**
	 *项目部资料情况
	 */
	protected String  date_status;
	/**
	 *项目章移交情况
	 */
	protected String  stamp_transfer_status;
	/**
	 *物品移交情况
	 */
	protected String  artical_transfer_status;
	/**
	 *离职原因
	 */
	protected String  resignation_reason;
	/**
	 *当前流程审批人
	 */
	protected String  task_id;
	
	//审批状体
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	//申请人所属部门
	protected String creatorOrgName;
	//入职日期
	protected java.util.Date entryTime;
	//职务
	protected String jobName;
	
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 员工姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 所在部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
	}
	public void setUser_new_phone_number(String user_new_phone_number) 
	{
		this.user_new_phone_number = user_new_phone_number;
	}
	/**
	 * 返回 新手机号
	 * @return
	 */
	public String getUser_new_phone_number() 
	{
		return this.user_new_phone_number;
	}
	public void setEntrydate_time(String entrydate_time) 
	{
		this.entrydate_time = entrydate_time;
	}
	/**
	 * 返回 入职时间
	 * @return
	 */
	public String getEntrydate_time() 
	{
		return this.entrydate_time;
	}
	public void setTerminame_time(String terminame_time) 
	{
		this.terminame_time = terminame_time;
	}
	/**
	 * 返回 解除劳动合同时间
	 * @return
	 */
	public String getTerminame_time() 
	{
		return this.terminame_time;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 解除劳动合同原因
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setDepartment_transfer_status(String department_transfer_status) 
	{
		this.department_transfer_status = department_transfer_status;
	}
	/**
	 * 返回 项目部资料移交情况
	 * @return
	 */
	public String getDepartment_transfer_status() 
	{
		return this.department_transfer_status;
	}
	public void setDate_status(String date_status) 
	{
		this.date_status = date_status;
	}
	/**
	 * 返回 项目部资料情况
	 * @return
	 */
	public String getDate_status() 
	{
		return this.date_status;
	}
	public void setStamp_transfer_status(String stamp_transfer_status) 
	{
		this.stamp_transfer_status = stamp_transfer_status;
	}
	/**
	 * 返回 项目章移交情况
	 * @return
	 */
	public String getStamp_transfer_status() 
	{
		return this.stamp_transfer_status;
	}
	public void setArtical_transfer_status(String artical_transfer_status) 
	{
		this.artical_transfer_status = artical_transfer_status;
	}
	/**
	 * 返回 物品移交情况
	 * @return
	 */
	public String getArtical_transfer_status() 
	{
		return this.artical_transfer_status;
	}
	public void setResignation_reason(String resignation_reason) 
	{
		this.resignation_reason = resignation_reason;
	}
	/**
	 * 返回 离职原因
	 * @return
	 */
	public String getResignation_reason() 
	{
		return this.resignation_reason;
	}
	public void setTask_id(String task_id) 
	{
		this.task_id = task_id;
	}
	/**
	 * 返回 当前流程审批人
	 * @return
	 */
	public String getTask_id() 
	{
		return this.task_id;
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
		if (!(object instanceof UserResignation)) 
		{
			return false;
		}
		UserResignation rhs = (UserResignation) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_name, rhs.user_name)
		.append(this.department, rhs.department)
		.append(this.user_id, rhs.user_id)
		.append(this.user_new_phone_number, rhs.user_new_phone_number)
		.append(this.entrydate_time, rhs.entrydate_time)
		.append(this.terminame_time, rhs.terminame_time)
		.append(this.reason, rhs.reason)
		.append(this.department_transfer_status, rhs.department_transfer_status)
		.append(this.date_status, rhs.date_status)
		.append(this.stamp_transfer_status, rhs.stamp_transfer_status)
		.append(this.artical_transfer_status, rhs.artical_transfer_status)
		.append(this.resignation_reason, rhs.resignation_reason)
		.append(this.task_id, rhs.task_id)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_name) 
		.append(this.department) 
		.append(this.user_id) 
		.append(this.user_new_phone_number) 
		.append(this.entrydate_time) 
		.append(this.terminame_time) 
		.append(this.reason) 
		.append(this.department_transfer_status) 
		.append(this.date_status) 
		.append(this.stamp_transfer_status) 
		.append(this.artical_transfer_status) 
		.append(this.resignation_reason) 
		.append(this.task_id) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_name", this.user_name) 
		.append("department", this.department) 
		.append("user_id", this.user_id) 
		.append("user_new_phone_number", this.user_new_phone_number) 
		.append("entrydate_time", this.entrydate_time) 
		.append("terminame_time", this.terminame_time) 
		.append("reason", this.reason) 
		.append("department_transfer_status", this.department_transfer_status) 
		.append("date_status", this.date_status) 
		.append("stamp_transfer_status", this.stamp_transfer_status) 
		.append("artical_transfer_status", this.artical_transfer_status) 
		.append("resignation_reason", this.resignation_reason) 
		.append("task_id", this.task_id) 
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
	public String getCreateTime() {
		//TimeUtil.getDateString(createTime);
		return TimeUtil.getDateString(createTime);
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
	public String getCreatorOrgName() {
		return creatorOrgName;
	}
	public void setCreatorOrgName(String creatorOrgName) {
		this.creatorOrgName = creatorOrgName;
	}
	public String getEntryTime() {
		if(entryTime!=null){
			return TimeUtil.getDateString(entryTime);
		}
		return "";
	}
	public void setEntryTime(java.util.Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	
}