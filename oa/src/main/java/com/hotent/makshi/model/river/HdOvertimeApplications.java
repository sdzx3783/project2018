package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:加班申请表单 Model对象
 */
public class HdOvertimeApplications extends WfBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5744759112250369975L;
	//主键
	protected Long id;
	
	// 姓名
	protected String name;

	// 用户Id
	protected Long userId;
	// 加班总天数
	protected Double overTimeDays;

		//调休天数
	protected Double adjustDays;
		
		// 剩余未调休天数
	protected Double leftAdjustDays;
	
	//年份
	protected String appliYear;
	public String getAppliYear() {
		return appliYear;
	}
	public void setAppliYear(String appliYear) {
		this.appliYear = appliYear;
	}
	protected String  manager;
	
	public Double getOverTimeDays() {
		return overTimeDays;
	}
	public void setOverTimeDays(Double overTimeDays) {
		this.overTimeDays = overTimeDays;
	}
	public Double getAdjustDays() {
		return adjustDays;
	}
	public void setAdjustDays(Double adjustDays) {
		this.adjustDays = adjustDays;
	}
	public Double getLeftAdjustDays() {
		return leftAdjustDays;
	}
	public void setLeftAdjustDays(Double leftAdjustDays) {
		this.leftAdjustDays = leftAdjustDays;
	}
	public HdOvertimeApplications(){
		super();
	}

	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	/**
//	 *申请人
//	 */
//	protected String  applicant;
//	/**
//	 *申请人ID
//	 */
//	protected String  applicantID;
//	/**
//	 *所在部门ID
//	 */
//	protected String  departmentID;
//	/**
//	 *项目负责人ID
//	 */
//	protected String  project_leaderID;
//	/**
//	 *片区负责人审核ID
//	 */
//	protected String  area_headID;
//	/**
//	 *部门负责人审核ID
//	 */
//	protected String  managerID;
//
//	/**
//	 *岗位
//	 */
//	protected String  position;
//	/**
//	 *加班申请日期
//	 */
//	protected java.util.Date  overTime_appliDate;
//	/**
//	 *加班类型
//	 */
//	protected String  type;
//	/**
//	 *加班开始时间
//	 */
//	protected java.util.Date  overTime_start;
//	/**
//	 *加班开始上下午
//	 */
//	protected String  overTime_start_point;
//	/**
//	 *是否包含上半夜
//	 */
//	protected String  include_front;
//	/**
//	 *加班结束时间
//	 */
//	protected java.util.Date  overTime_end;
//	/**
//	 *加班结束上下午
//	 */
//	protected String  overTime_end_point;
//	/**
//	 *是否包含下半夜
//	 */
//	protected String  include_later;
//	/**
//	 *加班事由
//	 */
//	protected String  overTime_reason;
//	/**
//	 *备注
//	 */
//	protected String  overTime_remark;
//	/**
//	 *附件
//	 */
//	protected String  overTime_attachment;
//	/**
//	 *是否走完
//	 */
//	protected String  finish;
//	/**
//	 *是否删除
//	 */
//	protected String  isdeleted;
//	/**
//	 *所在部门
//	 */
//	protected String  department;
//	/**
//	 *项目负责人
//	 */
//	protected String  project_leader;
//	/**
//	 *片区负责人审核
//	 */
//	protected String  area_head;
//	/**
//	 *部门负责人审核
//	 */

	//protected Long  runId=0L;
	
	
	
//	public void setApplicantID(String applicantID) 
//	{
//		this.applicantID = applicantID;
//	}
//	/**
//	 * 返回 申请人ID
//	 * @return
//	 */
//	public String getApplicantID() 
//	{
//		return this.applicantID;
//	}
//	public void setDepartmentID(String departmentID) 
//	{
//		this.departmentID = departmentID;
//	}
//	/**
//	 * 返回 所在部门ID
//	 * @return
//	 */
//	public String getDepartmentID() 
//	{
//		return this.departmentID;
//	}
//	public void setProject_leaderID(String project_leaderID) 
//	{
//		this.project_leaderID = project_leaderID;
//	}
//	/**
//	 * 返回 项目负责人ID
//	 * @return
//	 */
//	public String getProject_leaderID() 
//	{
//		return this.project_leaderID;
//	}
//	public void setArea_headID(String area_headID) 
//	{
//		this.area_headID = area_headID;
//	}
//	/**
//	 * 返回 片区负责人审核ID
//	 * @return
//	 */
//	public String getArea_headID() 
//	{
//		return this.area_headID;
//	}
//	public void setManagerID(String managerID) 
//	{
//		this.managerID = managerID;
//	}
//	/**
//	 * 返回 部门负责人审核ID
//	 * @return
//	 */
//	public String getManagerID() 
//	{
//		return this.managerID;
//	}
//	public void setApplicant(String applicant) 
//	{
//		this.applicant = applicant;
//	}
//	/**
//	 * 返回 申请人
//	 * @return
//	 */
//	public String getApplicant() 
//	{
//		return this.applicant;
//	}
//	public void setPosition(String position) 
//	{
//		this.position = position;
//	}
//	/**
//	 * 返回 岗位
//	 * @return
//	 */
//	public String getPosition() 
//	{
//		return this.position;
//	}
//	public void setOverTime_appliDate(java.util.Date overTime_appliDate) 
//	{
//		this.overTime_appliDate = overTime_appliDate;
//	}
//	/**
//	 * 返回 加班申请日期
//	 * @return
//	 */
//	public java.util.Date getOverTime_appliDate() 
//	{
//		return this.overTime_appliDate;
//	}
//	public void setType(String type) 
//	{
//		this.type = type;
//	}
//	/**
//	 * 返回 加班类型
//	 * @return
//	 */
//	public String getType() 
//	{
//		return this.type;
//	}
//	public void setOverTime_start(java.util.Date overTime_start) 
//	{
//		this.overTime_start = overTime_start;
//	}
//	/**
//	 * 返回 加班开始时间
//	 * @return
//	 */
//	public java.util.Date getOverTime_start() 
//	{
//		return this.overTime_start;
//	}
//	public void setOverTime_start_point(String overTime_start_point) 
//	{
//		this.overTime_start_point = overTime_start_point;
//	}
//	/**
//	 * 返回 加班开始上下午
//	 * @return
//	 */
//	public String getOverTime_start_point() 
//	{
//		return this.overTime_start_point;
//	}
//	public void setInclude_front(String include_front) 
//	{
//		this.include_front = include_front;
//	}
//	/**
//	 * 返回 是否包含上半夜
//	 * @return
//	 */
//	public String getInclude_front() 
//	{
//		return this.include_front;
//	}
//	public void setOverTime_end(java.util.Date overTime_end) 
//	{
//		this.overTime_end = overTime_end;
//	}
//	/**
//	 * 返回 加班结束时间
//	 * @return
//	 */
//	public java.util.Date getOverTime_end() 
//	{
//		return this.overTime_end;
//	}
//	public void setOverTime_end_point(String overTime_end_point) 
//	{
//		this.overTime_end_point = overTime_end_point;
//	}
//	/**
//	 * 返回 加班结束上下午
//	 * @return
//	 */
//	public String getOverTime_end_point() 
//	{
//		return this.overTime_end_point;
//	}
//	public void setInclude_later(String include_later) 
//	{
//		this.include_later = include_later;
//	}
//	/**
//	 * 返回 是否包含下半夜
//	 * @return
//	 */
//	public String getInclude_later() 
//	{
//		return this.include_later;
//	}
//	public void setOverTime_reason(String overTime_reason) 
//	{
//		this.overTime_reason = overTime_reason;
//	}
//	/**
//	 * 返回 加班事由
//	 * @return
//	 */
//	public String getOverTime_reason() 
//	{
//		return this.overTime_reason;
//	}
//	public void setOverTime_remark(String overTime_remark) 
//	{
//		this.overTime_remark = overTime_remark;
//	}
//	/**
//	 * 返回 备注
//	 * @return
//	 */
//	public String getOverTime_remark() 
//	{
//		return this.overTime_remark;
//	}
//	public void setOverTime_attachment(String overTime_attachment) 
//	{
//		this.overTime_attachment = overTime_attachment;
//	}
//	/**
//	 * 返回 附件
//	 * @return
//	 */
//	public String getOverTime_attachment() 
//	{
//		return this.overTime_attachment;
//	}
//	public void setFinish(String finish) 
//	{
//		this.finish = finish;
//	}
//	/**
//	 * 返回 是否走完
//	 * @return
//	 */
//	public String getFinish() 
//	{
//		return this.finish;
//	}
//	public void setIsdeleted(String isdeleted) 
//	{
//		this.isdeleted = isdeleted;
//	}
//	/**
//	 * 返回 是否删除
//	 * @return
//	 */
//	public String getIsdeleted() 
//	{
//		return this.isdeleted;
//	}
//	public void setDepartment(String department) 
//	{
//		this.department = department;
//	}
//	/**
//	 * 返回 所在部门
//	 * @return
//	 */
//	public String getDepartment() 
//	{
//		return this.department;
//	}
//	public void setProject_leader(String project_leader) 
//	{
//		this.project_leader = project_leader;
//	}
//	/**
//	 * 返回 项目负责人
//	 * @return
//	 */
//	public String getProject_leader() 
//	{
//		return this.project_leader;
//	}
//	public void setArea_head(String area_head) 
//	{
//		this.area_head = area_head;
//	}
//	/**
//	 * 返回 片区负责人审核
//	 * @return
//	 */
//	public String getArea_head() 
//	{
//		return this.area_head;
//	}
//	public void setManager(String manager) 
//	{
//		this.manager = manager;
//	}
//	/**
//	 * 返回 部门负责人审核
//	 * @return
//	 */
//	public String getManager() 
//	{
//		return this.manager;
//	}
//	public Long getRunId() {
//		return runId;
//	}
//	public void setRunId(Long runId) {
//		this.runId = runId;
//	}
//   	/**
//	 * @see java.lang.Object#equals(Object)
//	 */
//	public boolean equals(Object object) 
//	{
//		if (!(object instanceof HdOvertimeApplications)) 
//		{
//			return false;
//		}
//		HdOvertimeApplications rhs = (HdOvertimeApplications) object;
//		return new EqualsBuilder()
//		.append(this.id, rhs.id)
//		.append(this.applicantID, rhs.applicantID)
//		.append(this.departmentID, rhs.departmentID)
//		.append(this.project_leaderID, rhs.project_leaderID)
//		.append(this.area_headID, rhs.area_headID)
//		.append(this.managerID, rhs.managerID)
//		.append(this.applicant, rhs.applicant)
//		.append(this.position, rhs.position)
//		.append(this.overTime_appliDate, rhs.overTime_appliDate)
//		.append(this.type, rhs.type)
//		.append(this.overTime_start, rhs.overTime_start)
//		.append(this.overTime_start_point, rhs.overTime_start_point)
//		.append(this.include_front, rhs.include_front)
//		.append(this.overTime_end, rhs.overTime_end)
//		.append(this.overTime_end_point, rhs.overTime_end_point)
//		.append(this.include_later, rhs.include_later)
//		.append(this.overTime_reason, rhs.overTime_reason)
//		.append(this.overTime_remark, rhs.overTime_remark)
//		.append(this.overTime_attachment, rhs.overTime_attachment)
//		.append(this.finish, rhs.finish)
//		.append(this.isdeleted, rhs.isdeleted)
//		.append(this.department, rhs.department)
//		.append(this.project_leader, rhs.project_leader)
//		.append(this.area_head, rhs.area_head)
//		.append(this.manager, rhs.manager)
//		.isEquals();
//	}
//
//	/**
//	 * @see java.lang.Object#hashCode()
//	 */
//	public int hashCode() 
//	{
//		return new HashCodeBuilder(-82280557, -700257973)
//		.append(this.id)
//		.append(this.applicantID) 
//		.append(this.departmentID) 
//		.append(this.project_leaderID) 
//		.append(this.area_headID) 
//		.append(this.managerID) 
//		.append(this.applicant) 
//		.append(this.position) 
//		.append(this.overTime_appliDate) 
//		.append(this.type) 
//		.append(this.overTime_start) 
//		.append(this.overTime_start_point) 
//		.append(this.include_front) 
//		.append(this.overTime_end) 
//		.append(this.overTime_end_point) 
//		.append(this.include_later) 
//		.append(this.overTime_reason) 
//		.append(this.overTime_remark) 
//		.append(this.overTime_attachment) 
//		.append(this.finish) 
//		.append(this.isdeleted) 
//		.append(this.department) 
//		.append(this.project_leader) 
//		.append(this.area_head) 
//		.append(this.manager) 
//		.toHashCode();
//	}
//
//	/**
//	 * @see java.lang.Object#toString()
//	 */
//	public String toString() 
//	{
//		return new ToStringBuilder(this)
//		.append("id",this.id)
//		.append("applicantID", this.applicantID) 
//		.append("departmentID", this.departmentID) 
//		.append("project_leaderID", this.project_leaderID) 
//		.append("area_headID", this.area_headID) 
//		.append("managerID", this.managerID) 
//		.append("applicant", this.applicant) 
//		.append("position", this.position) 
//		.append("overTime_appliDate", this.overTime_appliDate) 
//		.append("type", this.type) 
//		.append("overTime_start", this.overTime_start) 
//		.append("overTime_start_point", this.overTime_start_point) 
//		.append("include_front", this.include_front) 
//		.append("overTime_end", this.overTime_end) 
//		.append("overTime_end_point", this.overTime_end_point) 
//		.append("include_later", this.include_later) 
//		.append("overTime_reason", this.overTime_reason) 
//		.append("overTime_remark", this.overTime_remark) 
//		.append("overTime_attachment", this.overTime_attachment) 
//		.append("finish", this.finish) 
//		.append("isdeleted", this.isdeleted) 
//		.append("department", this.department) 
//		.append("project_leader", this.project_leader) 
//		.append("area_head", this.area_head) 
//		.append("manager", this.manager) 
//		.toString();
//	}

}