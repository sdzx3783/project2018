package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.utils.CustomDateSerializer;
import com.hotent.makshi.utils.ShortDateSerializer;

/**
 * 对象功能:周计划 Model对象
 */
@JsonIgnoreProperties(value={"createBy","createtime","updatetime","updateBy","id","refId"})
public class WeekPlan extends BaseModel {
	// 主键
	protected Long id;
	protected Long refId;
	/**
	 * 评审专家ID
	 */
	//protected String assessorsID;
	/**
	 * 任务名称
	 */
	protected String taskName;
	/**
	 * 任务被分配人
	 */
	protected String assigners;

	/**
	 * 任务被分配人ID
	 */
	protected String assignersID;
	/**
	 * 会议安排时间
	 */
	protected java.util.Date meeting_time;
	/**
	 * 会议地点
	 */
	protected String meeting_addr;
	/**
	 * 评审专家
	 */
	protected String assessors;

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

	/*public void setAssessorsID(String assessorsID) {
		this.assessorsID = assessorsID;
	}

	*//**
	 * 返回 评审专家ID
	 * 
	 * @return
	 *//*
	public String getAssessorsID() {
		return this.assessorsID;
	}*/

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 返回 任务名称
	 * 
	 * @return
	 */
	public String getTaskName() {
		return this.taskName;
	}

	public void setAssigners(String assigners) {
		this.assigners = assigners;
	}

	/**
	 * 返回 任务被分配人
	 * 
	 * @return
	 */
	public String getAssigners() {
		return this.assigners;
	}

	public void setMeeting_time(java.util.Date meeting_time) {
		this.meeting_time = meeting_time;
	}

	/**
	 * 返回 会议安排时间
	 * 
	 * @return
	 */
	@JsonSerialize(using = ShortDateSerializer.class)
	public java.util.Date getMeeting_time() {
		return this.meeting_time;
	}

	public void setMeeting_addr(String meeting_addr) {
		this.meeting_addr = meeting_addr;
	}

	/**
	 * 返回 会议地点
	 * 
	 * @return
	 */
	public String getMeeting_addr() {
		return this.meeting_addr;
	}

	public void setAssessors(String assessors) {
		this.assessors = assessors;
	}

	public String getAssignersID() {
		return assignersID;
	}

	public void setAssignersID(String assignersID) {
		this.assignersID = assignersID;
	}

	/**
	 * 返回 评审专家
	 * 
	 * @return
	 */
	public String getAssessors() {
		return this.assessors;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WeekPlan)) {
			return false;
		}
		WeekPlan rhs = (WeekPlan) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.taskName, rhs.taskName).append(this.assigners, rhs.assigners).append(this.assignersID, rhs.assignersID)
				.append(this.meeting_time, rhs.meeting_time).append(this.meeting_addr, rhs.meeting_addr)
				.append(this.assessors, rhs.assessors).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.assignersID).append(this.taskName)
				.append(this.assigners).append(this.meeting_time).append(this.meeting_addr).append(this.assessors)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("taskName", this.taskName).append("assigners", this.assigners).append("assignersID",this.assignersID)
				.append("meeting_time", this.meeting_time).append("meeting_addr", this.meeting_addr)
				.append("assessors", this.assessors).toString();
	}

}