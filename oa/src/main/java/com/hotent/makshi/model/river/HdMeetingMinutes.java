package com.hotent.makshi.model.river;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:会议纪要 Model对象
 */
public class HdMeetingMinutes extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *会议主持人ID
	 */
	protected String  moderatorID;
	/**
	 *纪要提交人ID
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
	 *总工/分管领导ID
	 */
	protected String  finalCheckerID;
	/**
	 *会议名称
	 */
	protected String  conference_name;
	/**
	 *编号
	 */
	protected String  number;
	/**
	 *会议时间
	 */
	protected java.util.Date  date;
	/**
	 *会议主持人
	 */
	protected String  moderator;
	/**
	 *参会人员
	 */
	protected String  participants;
	/**
	 *纪要提交人
	 */
	protected String  name;
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
	 *总工/分管领导
	 */
	protected String  finalChecker;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setModeratorID(String moderatorID) 
	{
		this.moderatorID = moderatorID;
	}
	/**
	 * 返回 会议主持人ID
	 * @return
	 */
	public String getModeratorID() 
	{
		return this.moderatorID;
	}
	public void setNameID(String nameID) 
	{
		this.nameID = nameID;
	}
	/**
	 * 返回 纪要提交人ID
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
	 * 返回 总工/分管领导ID
	 * @return
	 */
	public String getFinalCheckerID() 
	{
		return this.finalCheckerID;
	}
	public void setConference_name(String conference_name) 
	{
		this.conference_name = conference_name;
	}
	/**
	 * 返回 会议名称
	 * @return
	 */
	public String getConference_name() 
	{
		return this.conference_name;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}
	/**
	 * 返回 编号
	 * @return
	 */
	public String getNumber() 
	{
		return this.number;
	}
	public void setDate(java.util.Date date) 
	{
		this.date = date;
	}
	/**
	 * 返回 会议时间
	 * @return
	 */
	public java.util.Date getDate() 
	{
		return this.date;
	}
	public void setModerator(String moderator) 
	{
		this.moderator = moderator;
	}
	/**
	 * 返回 会议主持人
	 * @return
	 */
	public String getModerator() 
	{
		return this.moderator;
	}
	public void setParticipants(String participants) 
	{
		this.participants = participants;
	}
	/**
	 * 返回 参会人员
	 * @return
	 */
	public String getParticipants() 
	{
		return this.participants;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 纪要提交人
	 * @return
	 */
	public String getName() 
	{
		return this.name;
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
	 * 返回 总工/分管领导
	 * @return
	 */
	public String getFinalChecker() 
	{
		return this.finalChecker;
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
		if (!(object instanceof HdMeetingMinutes)) 
		{
			return false;
		}
		HdMeetingMinutes rhs = (HdMeetingMinutes) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.moderatorID, rhs.moderatorID)
		.append(this.nameID, rhs.nameID)
		.append(this.project_leaderID, rhs.project_leaderID)
		.append(this.department_headsID, rhs.department_headsID)
		.append(this.finalCheckerID, rhs.finalCheckerID)
		.append(this.conference_name, rhs.conference_name)
		.append(this.number, rhs.number)
		.append(this.date, rhs.date)
		.append(this.moderator, rhs.moderator)
		.append(this.participants, rhs.participants)
		.append(this.name, rhs.name)
		.append(this.note, rhs.note)
		.append(this.attachment, rhs.attachment)
		.append(this.project_leader, rhs.project_leader)
		.append(this.department_heads, rhs.department_heads)
		.append(this.isNeedCheck, rhs.isNeedCheck)
		.append(this.finalChecker, rhs.finalChecker)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.moderatorID) 
		.append(this.nameID) 
		.append(this.project_leaderID) 
		.append(this.department_headsID) 
		.append(this.finalCheckerID) 
		.append(this.conference_name) 
		.append(this.number) 
		.append(this.date) 
		.append(this.moderator) 
		.append(this.participants) 
		.append(this.name) 
		.append(this.note) 
		.append(this.attachment) 
		.append(this.project_leader) 
		.append(this.department_heads) 
		.append(this.isNeedCheck) 
		.append(this.finalChecker) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("moderatorID", this.moderatorID) 
		.append("nameID", this.nameID) 
		.append("project_leaderID", this.project_leaderID) 
		.append("department_headsID", this.department_headsID) 
		.append("finalCheckerID", this.finalCheckerID) 
		.append("conference_name", this.conference_name) 
		.append("number", this.number) 
		.append("date", this.date) 
		.append("moderator", this.moderator) 
		.append("participants", this.participants) 
		.append("name", this.name) 
		.append("note", this.note) 
		.append("attachment", this.attachment) 
		.append("project_leader", this.project_leader) 
		.append("department_heads", this.department_heads) 
		.append("isNeedCheck", this.isNeedCheck) 
		.append("finalChecker", this.finalChecker) 
		.toString();
	}

}