package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:调休表 Model对象
 */
public class HdPaidLeave extends WfBaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3310415561169265324L;
	//主键
	protected Long id;
	
	//开始时间
	protected Date startDate;
	//结束时间
	protected Date endDate;
	//开始上下午
	protected Integer morning;
	//结束上下午
	protected Integer afternoon;
	//天数
	protected Double days;
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMorning() {
		return morning;
	}
	public void setMorning(Integer morning) {
		this.morning = morning;
	}
	public Integer getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(Integer afternoon) {
		this.afternoon = afternoon;
	}
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}

	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *项目负责人ID
	 */
	protected String  project_leaderID;
	/**
	 *片区负责人ID
	 */
	protected String  area_headID;
	/**
	 *部门负责人ID
	 */
	protected String  managerID;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *岗位
	 */
	protected String  position;
	/**
	 *调休申请日期
	 */
	protected java.util.Date  adjust_appliDate;
	/**
	 *调休事由
	 */
	protected String  adjust_reson;
	/**
	 *调休开始时间
	 */
	protected java.util.Date  adjust_start;
	/**
	 *调休开始上下午
	 */
	protected String  adjust_start_point;
	/**
	 *调休结束时间
	 */
	protected java.util.Date  adjust_end;
	/**
	 *调休结束上下午
	 */
	protected String  adjust_end_point;
	/**
	 *备注
	 */
	protected String  adjust_remark;
	/**
	 *附件
	 */
	protected String  adjust_attachment;
	/**
	 *项目负责人
	 */
	protected String  project_leader;
	/**
	 *片区负责人
	 */
	protected String  area_head;
	/**
	 *部门负责人
	 */
	protected String  manager;
	/**
	 *是否走完
	 */
	protected String  finish;
	/**
	 *是否删除
	 */
	protected String  isdeleted;
	protected Long  runId=0L;
	
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
	public void setProject_leaderID(String project_leaderID) 
	{
		this.project_leaderID = project_leaderID;
	}
	/**
	 * 返回 项目负责人ID
	 * @return
	 */
	public String getProject_leaderID() 
	{
		return this.project_leaderID;
	}
	public void setArea_headID(String area_headID) 
	{
		this.area_headID = area_headID;
	}
	/**
	 * 返回 片区负责人ID
	 * @return
	 */
	public String getArea_headID() 
	{
		return this.area_headID;
	}
	public void setManagerID(String managerID) 
	{
		this.managerID = managerID;
	}
	/**
	 * 返回 部门负责人ID
	 * @return
	 */
	public String getManagerID() 
	{
		return this.managerID;
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
	public void setPosition(String position) 
	{
		this.position = position;
	}
	/**
	 * 返回 岗位
	 * @return
	 */
	public String getPosition() 
	{
		return this.position;
	}
	public void setAdjust_appliDate(java.util.Date adjust_appliDate) 
	{
		this.adjust_appliDate = adjust_appliDate;
	}
	/**
	 * 返回 调休申请日期
	 * @return
	 */
	public java.util.Date getAdjust_appliDate() 
	{
		return this.adjust_appliDate;
	}
	public void setAdjust_reson(String adjust_reson) 
	{
		this.adjust_reson = adjust_reson;
	}
	/**
	 * 返回 调休事由
	 * @return
	 */
	public String getAdjust_reson() 
	{
		return this.adjust_reson;
	}
	public void setAdjust_start(java.util.Date adjust_start) 
	{
		this.adjust_start = adjust_start;
	}
	/**
	 * 返回 调休开始时间
	 * @return
	 */
	public java.util.Date getAdjust_start() 
	{
		return this.adjust_start;
	}
	public void setAdjust_start_point(String adjust_start_point) 
	{
		this.adjust_start_point = adjust_start_point;
	}
	/**
	 * 返回 调休开始上下午
	 * @return
	 */
	public String getAdjust_start_point() 
	{
		return this.adjust_start_point;
	}
	public void setAdjust_end(java.util.Date adjust_end) 
	{
		this.adjust_end = adjust_end;
	}
	/**
	 * 返回 调休结束时间
	 * @return
	 */
	public java.util.Date getAdjust_end() 
	{
		return this.adjust_end;
	}
	public void setAdjust_end_point(String adjust_end_point) 
	{
		this.adjust_end_point = adjust_end_point;
	}
	/**
	 * 返回 调休结束上下午
	 * @return
	 */
	public String getAdjust_end_point() 
	{
		return this.adjust_end_point;
	}
	public void setAdjust_remark(String adjust_remark) 
	{
		this.adjust_remark = adjust_remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getAdjust_remark() 
	{
		return this.adjust_remark;
	}
	public void setAdjust_attachment(String adjust_attachment) 
	{
		this.adjust_attachment = adjust_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAdjust_attachment() 
	{
		return this.adjust_attachment;
	}
	public void setProject_leader(String project_leader) 
	{
		this.project_leader = project_leader;
	}
	/**
	 * 返回 项目负责人
	 * @return
	 */
	public String getProject_leader() 
	{
		return this.project_leader;
	}
	public void setArea_head(String area_head) 
	{
		this.area_head = area_head;
	}
	/**
	 * 返回 片区负责人
	 * @return
	 */
	public String getArea_head() 
	{
		return this.area_head;
	}
	public void setManager(String manager) 
	{
		this.manager = manager;
	}
	/**
	 * 返回 部门负责人
	 * @return
	 */
	public String getManager() 
	{
		return this.manager;
	}
	public void setFinish(String finish) 
	{
		this.finish = finish;
	}
	/**
	 * 返回 是否走完
	 * @return
	 */
	public String getFinish() 
	{
		return this.finish;
	}
	public void setIsdeleted(String isdeleted) 
	{
		this.isdeleted = isdeleted;
	}
	/**
	 * 返回 是否删除
	 * @return
	 */
	public String getIsdeleted() 
	{
		return this.isdeleted;
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
		if (!(object instanceof HdPaidLeave)) 
		{
			return false;
		}
		HdPaidLeave rhs = (HdPaidLeave) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.project_leaderID, rhs.project_leaderID)
		.append(this.area_headID, rhs.area_headID)
		.append(this.managerID, rhs.managerID)
		.append(this.applicant, rhs.applicant)
		.append(this.position, rhs.position)
		.append(this.adjust_appliDate, rhs.adjust_appliDate)
		.append(this.adjust_reson, rhs.adjust_reson)
		.append(this.adjust_start, rhs.adjust_start)
		.append(this.adjust_start_point, rhs.adjust_start_point)
		.append(this.adjust_end, rhs.adjust_end)
		.append(this.adjust_end_point, rhs.adjust_end_point)
		.append(this.adjust_remark, rhs.adjust_remark)
		.append(this.adjust_attachment, rhs.adjust_attachment)
		.append(this.project_leader, rhs.project_leader)
		.append(this.area_head, rhs.area_head)
		.append(this.manager, rhs.manager)
		.append(this.finish, rhs.finish)
		.append(this.isdeleted, rhs.isdeleted)
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
		.append(this.project_leaderID) 
		.append(this.area_headID) 
		.append(this.managerID) 
		.append(this.applicant) 
		.append(this.position) 
		.append(this.adjust_appliDate) 
		.append(this.adjust_reson) 
		.append(this.adjust_start) 
		.append(this.adjust_start_point) 
		.append(this.adjust_end) 
		.append(this.adjust_end_point) 
		.append(this.adjust_remark) 
		.append(this.adjust_attachment) 
		.append(this.project_leader) 
		.append(this.area_head) 
		.append(this.manager) 
		.append(this.finish) 
		.append(this.isdeleted) 
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
		.append("project_leaderID", this.project_leaderID) 
		.append("area_headID", this.area_headID) 
		.append("managerID", this.managerID) 
		.append("applicant", this.applicant) 
		.append("position", this.position) 
		.append("adjust_appliDate", this.adjust_appliDate) 
		.append("adjust_reson", this.adjust_reson) 
		.append("adjust_start", this.adjust_start) 
		.append("adjust_start_point", this.adjust_start_point) 
		.append("adjust_end", this.adjust_end) 
		.append("adjust_end_point", this.adjust_end_point) 
		.append("adjust_remark", this.adjust_remark) 
		.append("adjust_attachment", this.adjust_attachment) 
		.append("project_leader", this.project_leader) 
		.append("area_head", this.area_head) 
		.append("manager", this.manager) 
		.append("finish", this.finish) 
		.append("isdeleted", this.isdeleted) 
		.toString();
	}

}