package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:方案审批表 Model对象
 */
public class HdProgrammeApproval extends WfBaseModel
{
	//主键
	protected Long id;
	
	/**
	 *拟稿人
	 */
	protected String  name;
	/**
	 *拟稿时间
	 */
	protected java.util.Date  date;
	/**
	 *任务名称
	 */
	protected String  task_name;
	/**
	 * 项目类型
	 */
	protected String type;
	/**
	 *项目名称
	 */
	protected String  project_name;
	
	/**
	 * 任务id
	 */
	protected Long id1;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId1() {
		return id1;
	}
	public void setId1(Long id1) {
		this.id1 = id1;
	}

	/**
	 *拟稿人ID
	 */
	protected String  nameID;
	/**
	 *项目负责人ID
	 */
	protected String  project_leaderID;
	/**
	 *部门负责人ID
	 */
	protected String  department_headsID;
	/**
	 *总工办/分管领导ID
	 */
	protected String  finalCheckerID;
	
	/**
	 *阶段名称
	 */
	protected String  stage_name;
	

	/**
	 *方案名称
	 */
	protected String  program_name;
	/**
	 *方案内容
	 */
	protected String  content;
	/**
	 *备注
	 */
	protected String  note;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *项目负责人
	 */
	protected String  project_leader;
	/**
	 *部门负责人
	 */
	protected String  department_heads;
	/**
	 *是否需要审定
	 */
	protected String  isNeedCheck;
	/**
	 *总工办/分管领导
	 */
	protected String  finalChecker;
	/**
	 *项目任务id
	 */
	protected String  projectTaskId;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNameID(String nameID) 
	{
		this.nameID = nameID;
	}
	/**
	 * 返回 拟稿人ID
	 * @return
	 */
	public String getNameID() 
	{
		return this.nameID;
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
	public void setDepartment_headsID(String department_headsID) 
	{
		this.department_headsID = department_headsID;
	}
	/**
	 * 返回 部门负责人ID
	 * @return
	 */
	public String getDepartment_headsID() 
	{
		return this.department_headsID;
	}
	public void setFinalCheckerID(String finalCheckerID) 
	{
		this.finalCheckerID = finalCheckerID;
	}
	/**
	 * 返回 总工办/分管领导ID
	 * @return
	 */
	public String getFinalCheckerID() 
	{
		return this.finalCheckerID;
	}
	public void setProject_name(String project_name) 
	{
		this.project_name = project_name;
	}
	/**
	 * 返回 项目名称
	 * @return
	 */
	public String getProject_name() 
	{
		return this.project_name;
	}
	public void setStage_name(String stage_name) 
	{
		this.stage_name = stage_name;
	}
	/**
	 * 返回 阶段名称
	 * @return
	 */
	public String getStage_name() 
	{
		return this.stage_name;
	}
	public void setTask_name(String task_name) 
	{
		this.task_name = task_name;
	}
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getTask_name() 
	{
		return this.task_name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 拟稿人
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setDate(java.util.Date date) 
	{
		this.date = date;
	}
	/**
	 * 返回 拟稿时间
	 * @return
	 */
	public java.util.Date getDate() 
	{
		return this.date;
	}
	public void setProgram_name(String program_name) 
	{
		this.program_name = program_name;
	}
	/**
	 * 返回 方案名称
	 * @return
	 */
	public String getProgram_name() 
	{
		return this.program_name;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 方案内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setNote(String note) 
	{
		this.note = note;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getNote() 
	{
		return this.note;
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
	public void setDepartment_heads(String department_heads) 
	{
		this.department_heads = department_heads;
	}
	/**
	 * 返回 部门负责人
	 * @return
	 */
	public String getDepartment_heads() 
	{
		return this.department_heads;
	}
	public void setIsNeedCheck(String isNeedCheck) 
	{
		this.isNeedCheck = isNeedCheck;
	}
	/**
	 * 返回 是否需要审定
	 * @return
	 */
	public String getIsNeedCheck() 
	{
		return this.isNeedCheck;
	}
	public void setFinalChecker(String finalChecker) 
	{
		this.finalChecker = finalChecker;
	}
	/**
	 * 返回 总工办/分管领导
	 * @return
	 */
	public String getFinalChecker() 
	{
		return this.finalChecker;
	}
	public void setProjectTaskId(String projectTaskId) 
	{
		this.projectTaskId = projectTaskId;
	}
	/**
	 * 返回 项目任务id
	 * @return
	 */
	public String getProjectTaskId() 
	{
		return this.projectTaskId;
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
		if (!(object instanceof HdProgrammeApproval)) 
		{
			return false;
		}
		HdProgrammeApproval rhs = (HdProgrammeApproval) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.project_leaderID, rhs.project_leaderID)
		.append(this.department_headsID, rhs.department_headsID)
		.append(this.finalCheckerID, rhs.finalCheckerID)
		.append(this.project_name, rhs.project_name)
		.append(this.stage_name, rhs.stage_name)
		.append(this.task_name, rhs.task_name)
		.append(this.name, rhs.name)
		.append(this.date, rhs.date)
		.append(this.program_name, rhs.program_name)
		.append(this.content, rhs.content)
		.append(this.note, rhs.note)
		.append(this.attachment, rhs.attachment)
		.append(this.project_leader, rhs.project_leader)
		.append(this.department_heads, rhs.department_heads)
		.append(this.isNeedCheck, rhs.isNeedCheck)
		.append(this.finalChecker, rhs.finalChecker)
		.append(this.projectTaskId, rhs.projectTaskId)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.nameID) 
		.append(this.project_leaderID) 
		.append(this.department_headsID) 
		.append(this.finalCheckerID) 
		.append(this.project_name) 
		.append(this.stage_name) 
		.append(this.task_name) 
		.append(this.name) 
		.append(this.date) 
		.append(this.program_name) 
		.append(this.content) 
		.append(this.note) 
		.append(this.attachment) 
		.append(this.project_leader) 
		.append(this.department_heads) 
		.append(this.isNeedCheck) 
		.append(this.finalChecker) 
		.append(this.projectTaskId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("nameID", this.nameID) 
		.append("project_leaderID", this.project_leaderID) 
		.append("department_headsID", this.department_headsID) 
		.append("finalCheckerID", this.finalCheckerID) 
		.append("project_name", this.project_name) 
		.append("stage_name", this.stage_name) 
		.append("task_name", this.task_name) 
		.append("name", this.name) 
		.append("date", this.date) 
		.append("program_name", this.program_name) 
		.append("content", this.content) 
		.append("note", this.note) 
		.append("attachment", this.attachment) 
		.append("project_leader", this.project_leader) 
		.append("department_heads", this.department_heads) 
		.append("isNeedCheck", this.isNeedCheck) 
		.append("finalChecker", this.finalChecker) 
		.append("projectTaskId", this.projectTaskId) 
		.toString();
	}

}