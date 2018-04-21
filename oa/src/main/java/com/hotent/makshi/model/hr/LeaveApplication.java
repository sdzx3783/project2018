package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.utils.DateUtils;
/**
 * 对象功能:请假申请流程表单 Model对象
 */
public class LeaveApplication extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *所属部门ID
	 */
	protected String  departmentID;
	/**
	 *职位ID
	 */
	protected String  positionID;
	/**
	 *工号
	 */
	protected String  user_num;
	/**
	 *参加工作时间
	 */
	protected java.util.Date  join_work_time;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *入职时间
	 */
	protected java.util.Date  induction_time;
	/**
	 *申请日期
	 */
	protected java.util.Date  application_date;
	/**
	 *婚否
	 */
	protected String  isMarry;
	/**
	 *所属部门
	 */
	protected String  department;
	/**
	 *请假类型
	 */
	protected String  leave_type;
	/**
	 *请假天数
	 */
	protected String leave_days;
	/**
	 *职位
	 */
	protected String  position;
	/**
	 *请假时间
	 */
	protected java.util.Date  startTime;
	/**
	 *结束时间
	 */
	protected java.util.Date  endTime;
	/**
	 *请假事由
	 */
	protected String  reason;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *上传相关证明
	 */
	protected String  file;
	
	//工单号
	protected String globalFlowNo;
	//审批状体
	protected String processStatus;
	//请假时长
	protected String days;
	
	private String leaveStartSolt;
	
	private String leaveEndSolt;
	
	
	
	protected Long  runId=0L;
	
	public String getLeave_days() {
		return leave_days;
	}
	public void setLeave_days(String leave_days) {
		this.leave_days = leave_days;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 所属部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setPositionID(String positionID) 
	{
		this.positionID = positionID;
	}
	/**
	 * 返回 职位ID
	 * @return
	 */
	public String getPositionID() 
	{
		return this.positionID;
	}
	public void setUser_num(String user_num) 
	{
		this.user_num = user_num;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getUser_num() 
	{
		return this.user_num;
	}
	public void setJoin_work_time(java.util.Date join_work_time) 
	{
		this.join_work_time = join_work_time;
	}
	/**
	 * 返回 参加工作时间
	 * @return
	 */
	public java.util.Date getJoin_work_time() 
	{
		return this.join_work_time;
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
	public void setInduction_time(java.util.Date induction_time) 
	{
		this.induction_time = induction_time;
	}
	/**
	 * 返回 入职时间
	 * @return
	 */
	public java.util.Date getInduction_time() 
	{
		return this.induction_time;
	}
	public void setApplication_date(java.util.Date application_date) 
	{
		this.application_date = application_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getApplication_date() 
	{
		return this.application_date;
	}
	public void setIsMarry(String isMarry) 
	{
		this.isMarry = isMarry;
	}
	/**
	 * 返回 婚否
	 * @return
	 */
	public String getIsMarry() 
	{
		return this.isMarry;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 所属部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setLeave_type(String leave_type) 
	{
		this.leave_type = leave_type;
	}
	/**
	 * 返回 请假类型
	 * @return
	 */
	public String getLeave_type() 
	{
		return this.leave_type;
	}
	public void setPosition(String position) 
	{
		this.position = position;
	}
	/**
	 * 返回 职位
	 * @return
	 */
	public String getPosition() 
	{
		return this.position;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 请假时间
	 * @return
	 */
	public java.util.Date getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 请假事由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
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
	 * 返回 上传相关证明
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
		if (!(object instanceof LeaveApplication)) 
		{
			return false;
		}
		LeaveApplication rhs = (LeaveApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.departmentID, rhs.departmentID)
		.append(this.positionID, rhs.positionID)
		.append(this.user_num, rhs.user_num)
		.append(this.join_work_time, rhs.join_work_time)
		.append(this.applicant, rhs.applicant)
		.append(this.induction_time, rhs.induction_time)
		.append(this.application_date, rhs.application_date)
		.append(this.isMarry, rhs.isMarry)
		.append(this.department, rhs.department)
		.append(this.leave_type, rhs.leave_type)
		.append(this.position, rhs.position)
		.append(this.startTime, rhs.startTime)
		.append(this.endTime, rhs.endTime)
		.append(this.reason, rhs.reason)
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
		.append(this.departmentID) 
		.append(this.positionID) 
		.append(this.user_num) 
		.append(this.join_work_time) 
		.append(this.applicant) 
		.append(this.induction_time) 
		.append(this.application_date) 
		.append(this.isMarry) 
		.append(this.department) 
		.append(this.leave_type) 
		.append(this.position) 
		.append(this.startTime) 
		.append(this.endTime) 
		.append(this.reason) 
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
		.append("departmentID", this.departmentID) 
		.append("positionID", this.positionID) 
		.append("user_num", this.user_num) 
		.append("join_work_time", this.join_work_time) 
		.append("applicant", this.applicant) 
		.append("induction_time", this.induction_time) 
		.append("application_date", this.application_date) 
		.append("isMarry", this.isMarry) 
		.append("department", this.department) 
		.append("leave_type", this.leave_type) 
		.append("position", this.position) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("reason", this.reason) 
		.append("remark", this.remark) 
		.append("file", this.file) 
		.toString();
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getDays() {
		
		
		return ""+(DateUtils.daysDiff(this.getStartTime(), this.getEndTime()));
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getLeaveStartSolt() {
		return leaveStartSolt;
	}
	public void setLeaveStartSolt(String leaveStartSolt) {
		this.leaveStartSolt = leaveStartSolt;
	}
	public String getLeaveEndSolt() {
		return leaveEndSolt;
	}
	public void setLeaveEndSolt(String leaveEndSolt) {
		this.leaveEndSolt = leaveEndSolt;
	}

}