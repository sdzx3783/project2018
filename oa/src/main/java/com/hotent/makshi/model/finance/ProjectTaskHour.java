package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:项目任务工时 Model对象
 */
public class ProjectTaskHour extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *任务名称
	 */
	protected String  projectName;
	/**
	 *项目id
	 */
	protected String  proid;
	/**
	 *任务名称
	 */
	protected String  taskName;
	/**
	 *任务id
	 */
	protected String  taskid;
	/**
	 *正常工时
	 */
	protected String  work_hour;
	/**
	 *加班工时
	 */
	protected String  overtime_hour;
	/**
	 *进度
	 */
	protected String  progress_rate;
	/**
	 *备注
	 */
	protected String  remark;
	
	private String workcount;
	private String overcount;
	/**
	 * 项目工时
	 */
	private Integer project_work_rate;
	
	public Integer getProject_work_rate() {
		return project_work_rate;
	}
	public void setProject_work_rate(Integer project_work_rate) {
		this.project_work_rate = project_work_rate;
	}
	public String getWorkcount() {
		return workcount;
	}
	public void setWorkcount(String workcount) {
		this.workcount = workcount;
	}
	public String getOvercount() {
		return overcount;
	}
	public void setOvercount(String overcount) {
		this.overcount = overcount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
	public void setProjectName(String projectName) 
	{
		this.projectName = projectName;
	}
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getProjectName() 
	{
		return this.projectName;
	}
	public void setProid(String proid) 
	{
		this.proid = proid;
	}
	/**
	 * 返回 项目id
	 * @return
	 */
	public String getProid() 
	{
		return this.proid;
	}
	public void setTaskName(String taskName) 
	{
		this.taskName = taskName;
	}
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getTaskName() 
	{
		return this.taskName;
	}
	public void setTaskid(String taskid) 
	{
		this.taskid = taskid;
	}
	/**
	 * 返回 任务id
	 * @return
	 */
	public String getTaskid() 
	{
		return this.taskid;
	}
	public void setWork_hour(String work_hour) 
	{
		this.work_hour = work_hour;
	}
	/**
	 * 返回 正常工时
	 * @return
	 */
	public String getWork_hour() 
	{
		return this.work_hour;
	}
	public void setOvertime_hour(String overtime_hour) 
	{
		this.overtime_hour = overtime_hour;
	}
	/**
	 * 返回 加班工时
	 * @return
	 */
	public String getOvertime_hour() 
	{
		return this.overtime_hour;
	}
	public void setProgress_rate(String progress_rate) 
	{
		this.progress_rate = progress_rate;
	}
	/**
	 * 返回 进度
	 * @return
	 */
	public String getProgress_rate() 
	{
		return this.progress_rate;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ProjectTaskHour)) 
		{
			return false;
		}
		ProjectTaskHour rhs = (ProjectTaskHour) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.projectName, rhs.projectName)
		.append(this.proid, rhs.proid)
		.append(this.taskName, rhs.taskName)
		.append(this.taskid, rhs.taskid)
		.append(this.work_hour, rhs.work_hour)
		.append(this.overtime_hour, rhs.overtime_hour)
		.append(this.progress_rate, rhs.progress_rate)
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
		.append(this.projectName) 
		.append(this.proid) 
		.append(this.taskName) 
		.append(this.taskid) 
		.append(this.work_hour) 
		.append(this.overtime_hour) 
		.append(this.progress_rate) 
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
		.append("projectName", this.projectName) 
		.append("proid", this.proid) 
		.append("taskName", this.taskName) 
		.append("taskid", this.taskid) 
		.append("work_hour", this.work_hour) 
		.append("overtime_hour", this.overtime_hour) 
		.append("progress_rate", this.progress_rate) 
		.append("remark", this.remark) 
		.toString();
	}

}