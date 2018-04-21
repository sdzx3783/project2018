package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:自定义任务工时 Model对象
 */
public class CustomTaskHour extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *任务名称
	 */
	protected String  taskName;
	/**
	 *工作内容
	 */
	protected String  work_content;
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
	
	
	private Integer task_work_rate;
	
	public Integer getTask_work_rate() {
		return task_work_rate;
	}
	public void setTask_work_rate(Integer task_work_rate) {
		this.task_work_rate = task_work_rate;
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
	public void setWork_content(String work_content) 
	{
		this.work_content = work_content;
	}
	/**
	 * 返回 工作内容
	 * @return
	 */
	public String getWork_content() 
	{
		return this.work_content;
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
		if (!(object instanceof CustomTaskHour)) 
		{
			return false;
		}
		CustomTaskHour rhs = (CustomTaskHour) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.taskName, rhs.taskName)
		.append(this.work_content, rhs.work_content)
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
		.append(this.taskName) 
		.append(this.work_content) 
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
		.append("taskName", this.taskName) 
		.append("work_content", this.work_content) 
		.append("work_hour", this.work_hour) 
		.append("overtime_hour", this.overtime_hour) 
		.append("progress_rate", this.progress_rate) 
		.append("remark", this.remark) 
		.toString();
	}

}