package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.waterprotectpark.WeekPlan;
import com.hotent.makshi.utils.CustomDateSerializer;
/**
 * 对象功能:技术评审周计划(水保部) Model对象
 */
public class TechReviewWeekPlan extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *计划安排人ID
	 */
	protected String  plan_arrangerID;
	/**
	 *执行人ID
	 */
	protected String  plan_executorID;
	/**
	 *起始时间
	 */
	protected java.util.Date  start_time;
	/**
	 *结束时间
	 */
	protected java.util.Date  end_time;
	/**
	 *计划安排人
	 */
	protected String  plan_arranger;
	/**
	 *执行人
	 */
	protected String  plan_executor;
	/**
	 *周次
	 */
	protected Long  week;
	/**
	 *时间
	 */
	protected java.util.Date  applicantTime;
	/**
	 *表单id
	 */
	protected Long  week_plan_RefId;
	
	/**
	 *技术评审周计划(水保部)列表
	 */
	protected List<WeekPlan> weekPlanSbbList=new ArrayList<WeekPlan>();
	
	private String refRunIds;//附加字段 用于获取流程运行id
	
	private Boolean isInExamine;//是否有流程处于审批中
	
	public Boolean getIsInExamine() {
		return isInExamine;
	}
	public void setIsInExamine(Boolean isInExamine) {
		this.isInExamine = isInExamine;
	}
	public String getRefRunIds() {
		return refRunIds;
	}
	public void setRefRunIds(String refRunIds) {
		this.refRunIds = refRunIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPlan_arrangerID(String plan_arrangerID) 
	{
		this.plan_arrangerID = plan_arrangerID;
	}
	/**
	 * 返回 计划安排人ID
	 * @return
	 */
	public String getPlan_arrangerID() 
	{
		return this.plan_arrangerID;
	}
	public void setPlan_executorID(String plan_executorID) 
	{
		this.plan_executorID = plan_executorID;
	}
	/**
	 * 返回 执行人ID
	 * @return
	 */
	public String getPlan_executorID() 
	{
		return this.plan_executorID;
	}
	public void setStart_time(java.util.Date start_time) 
	{
		this.start_time = start_time;
	}
	/**
	 * 返回 起始时间
	 * @return
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public java.util.Date getStart_time() 
	{
		return this.start_time;
	}
	public void setEnd_time(java.util.Date end_time) 
	{
		this.end_time = end_time;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public java.util.Date getEnd_time() 
	{
		return this.end_time;
	}
	public void setPlan_arranger(String plan_arranger) 
	{
		this.plan_arranger = plan_arranger;
	}
	/**
	 * 返回 计划安排人
	 * @return
	 */
	public String getPlan_arranger() 
	{
		return this.plan_arranger;
	}
	public void setPlan_executor(String plan_executor) 
	{
		this.plan_executor = plan_executor;
	}
	/**
	 * 返回 执行人
	 * @return
	 */
	public String getPlan_executor() 
	{
		return this.plan_executor;
	}
	public void setWeek(Long week) 
	{
		this.week = week;
	}
	/**
	 * 返回 周次
	 * @return
	 */
	public Long getWeek() 
	{
		return this.week;
	}
	public void setApplicantTime(java.util.Date applicantTime) 
	{
		this.applicantTime = applicantTime;
	}
	/**
	 * 返回 时间
	 * @return
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public java.util.Date getApplicantTime() 
	{
		return this.applicantTime;
	}
	public void setWeek_plan_RefId(Long week_plan_RefId) 
	{
		this.week_plan_RefId = week_plan_RefId;
	}
	/**
	 * 返回 表单id
	 * @return
	 */
	public Long getWeek_plan_RefId() 
	{
		return this.week_plan_RefId;
	}
	public void setWeekPlanSbbList(List<WeekPlan> weekPlanSbbList) 
	{
		this.weekPlanSbbList = weekPlanSbbList;
	}
	/**
	 * 返回 技术评审周计划(水保部)列表
	 * @return
	 */
	public List<WeekPlan> getWeekPlanSbbList() 
	{
		return this.weekPlanSbbList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof TechReviewWeekPlan)) 
		{
			return false;
		}
		TechReviewWeekPlan rhs = (TechReviewWeekPlan) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.plan_arrangerID, rhs.plan_arrangerID)
		.append(this.plan_executorID, rhs.plan_executorID)
		.append(this.start_time, rhs.start_time)
		.append(this.end_time, rhs.end_time)
		.append(this.plan_arranger, rhs.plan_arranger)
		.append(this.plan_executor, rhs.plan_executor)
		.append(this.week, rhs.week)
		.append(this.applicantTime, rhs.applicantTime)
		.append(this.week_plan_RefId, rhs.week_plan_RefId)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.plan_arrangerID) 
		.append(this.plan_executorID) 
		.append(this.start_time) 
		.append(this.end_time) 
		.append(this.plan_arranger) 
		.append(this.plan_executor) 
		.append(this.week) 
		.append(this.applicantTime) 
		.append(this.week_plan_RefId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("plan_arrangerID", this.plan_arrangerID) 
		.append("plan_executorID", this.plan_executorID) 
		.append("start_time", this.start_time) 
		.append("end_time", this.end_time) 
		.append("plan_arranger", this.plan_arranger) 
		.append("plan_executor", this.plan_executor) 
		.append("week", this.week) 
		.append("applicantTime", this.applicantTime) 
		.append("week_plan_RefId", this.week_plan_RefId) 
		.toString();
	}

}