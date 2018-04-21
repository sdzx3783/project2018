package com.hotent.makshi.model.dispatch;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:发文总表 Model对象
 */
public class Dispatch 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3447533005903293640L;
	//主键
	protected Long id;
	/**
	 *核稿人ID
	 */
	protected String  check_personID;
	/**
	 *会签人员ID
	 */
	protected String  countersign_personID;
	/**
	 *发文字号
	 */
	protected String  dispatch_id;
	/**
	 *发文时间
	 */
	protected java.util.Date  time;
	/**
	 *发文标题
	 */
	protected String  title;
	/**
	 *发文类型
	 */
	protected String  type;
	/**
	 *紧急程度
	 */
	protected String  urgency_degree;
	/**
	 *主题词
	 */
	protected String  keyword;
	/**
	 *秘密等级
	 */
	protected String  secret_level;
	/**
	 *主送单位
	 */
	protected String  send_unit;
	/**
	 *抄送单位
	 */
	protected String  cc_unit;
	/**
	 *正文
	 */
	protected String  dispatch_content;
	/**
	 *核稿人
	 */
	protected String  check_person;
	/**
	 *会签人员
	 */
	protected String  countersign_person;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *拟稿人：ID
	 */
	protected String  draft_personID;
	/**
	 *拟稿人：
	 */
	protected String  draft_person;
	
	/**
	 * 审批状态
	 */
	protected String state;
	/**
	 * 
	 */
	protected String checkState;
	
	protected String redState;
	
	protected String examineState;
	
	protected String sealState;
	
	protected String sendState;
	
	protected String disOrgs;
	
	protected String cc_unit_person;
	protected String cc_unit_personID;
	protected String send_unit_person;
	protected String send_unit_personID;
	protected String send_unit_department;
	protected String send_unit_departmentID;
	protected String cc_unit_department;
	protected String cc_unit_departmentID;
	
	protected Long dicId;
	
	protected Long docId;
	
	public Long getDicId() {
		return dicId;
	}
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public String getCc_unit_person() {
		return cc_unit_person;
	}
	public void setCc_unit_person(String cc_unit_person) {
		this.cc_unit_person = cc_unit_person;
	}
	public String getCc_unit_personID() {
		return cc_unit_personID;
	}
	public void setCc_unit_personID(String cc_unit_personID) {
		this.cc_unit_personID = cc_unit_personID;
	}
	public String getSend_unit_person() {
		return send_unit_person;
	}
	public void setSend_unit_person(String send_unit_person) {
		this.send_unit_person = send_unit_person;
	}
	public String getSend_unit_personID() {
		return send_unit_personID;
	}
	public void setSend_unit_personID(String send_unit_personID) {
		this.send_unit_personID = send_unit_personID;
	}
	public String getSend_unit_department() {
		return send_unit_department;
	}
	public void setSend_unit_department(String send_unit_department) {
		this.send_unit_department = send_unit_department;
	}
	public String getSend_unit_departmentID() {
		return send_unit_departmentID;
	}
	public void setSend_unit_departmentID(String send_unit_departmentID) {
		this.send_unit_departmentID = send_unit_departmentID;
	}
	public String getCc_unit_department() {
		return cc_unit_department;
	}
	public void setCc_unit_department(String cc_unit_department) {
		this.cc_unit_department = cc_unit_department;
	}
	public String getCc_unit_departmentID() {
		return cc_unit_departmentID;
	}
	public void setCc_unit_departmentID(String cc_unit_departmentID) {
		this.cc_unit_departmentID = cc_unit_departmentID;
	}
	public String getDisOrgs() {
		return disOrgs;
	}
	public void setDisOrgs(String disOrgs) {
		this.disOrgs = disOrgs;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	public String getSealState() {
		return sealState;
	}
	public void setSealState(String sealState) {
		this.sealState = sealState;
	}
	public String getRedState() {
		return redState;
	}
	public void setRedState(String redState) {
		this.redState = redState;
	}
	public String getExamineState() {
		return examineState;
	}
	public void setExamineState(String examineState) {
		this.examineState = examineState;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCheck_personID(String check_personID) 
	{
		this.check_personID = check_personID;
	}
	/**
	 * 返回 核稿人ID
	 * @return
	 */
	public String getCheck_personID() 
	{
		return this.check_personID;
	}
	public void setCountersign_personID(String countersign_personID) 
	{
		this.countersign_personID = countersign_personID;
	}
	/**
	 * 返回 会签人员ID
	 * @return
	 */
	public String getCountersign_personID() 
	{
		return this.countersign_personID;
	}
	public void setDispatch_id(String dispatch_id) 
	{
		this.dispatch_id = dispatch_id;
	}
	/**
	 * 返回 发文字号
	 * @return
	 */
	public String getDispatch_id() 
	{
		return this.dispatch_id;
	}
	public void setTime(Date time) 
	{
		this.time = time;
	}
	/**
	 * 返回 发文时间
	 * @return
	 */
	public Date getTime() 
	{
		return this.time;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 发文标题
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 发文类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setUrgency_degree(String urgency_degree) 
	{
		this.urgency_degree = urgency_degree;
	}
	/**
	 * 返回 紧急程度
	 * @return
	 */
	public String getUrgency_degree() 
	{
		return this.urgency_degree;
	}
	public void setKeyword(String keyword) 
	{
		this.keyword = keyword;
	}
	/**
	 * 返回 主题词
	 * @return
	 */
	public String getKeyword() 
	{
		return this.keyword;
	}
	public void setSecret_level(String secret_level) 
	{
		this.secret_level = secret_level;
	}
	/**
	 * 返回 秘密等级
	 * @return
	 */
	public String getSecret_level() 
	{
		return this.secret_level;
	}
	public void setSend_unit(String send_unit) 
	{
		this.send_unit = send_unit;
	}
	/**
	 * 返回 主送单位
	 * @return
	 */
	public String getSend_unit() 
	{
		return this.send_unit;
	}
	public void setCc_unit(String cc_unit) 
	{
		this.cc_unit = cc_unit;
	}
	/**
	 * 返回 抄送单位
	 * @return
	 */
	public String getCc_unit() 
	{
		return this.cc_unit;
	}
	public void setDispatch_content(String dispatch_content) 
	{
		this.dispatch_content = dispatch_content;
	}
	/**
	 * 返回 正文
	 * @return
	 */
	public String getDispatch_content() 
	{
		return this.dispatch_content;
	}
	public void setCheck_person(String check_person) 
	{
		this.check_person = check_person;
	}
	/**
	 * 返回 核稿人
	 * @return
	 */
	public String getCheck_person() 
	{
		return this.check_person;
	}
	public void setCountersign_person(String countersign_person) 
	{
		this.countersign_person = countersign_person;
	}
	/**
	 * 返回 会签人员
	 * @return
	 */
	public String getCountersign_person() 
	{
		return this.countersign_person;
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
	public void setDraft_personID(String draft_personID) 
	{
		this.draft_personID = draft_personID;
	}
	/**
	 * 返回 拟稿人：ID
	 * @return
	 */
	public String getDraft_personID() 
	{
		return this.draft_personID;
	}
	public void setDraft_person(String draft_person) 
	{
		this.draft_person = draft_person;
	}
	/**
	 * 返回 拟稿人：
	 * @return
	 */
	public String getDraft_person() 
	{
		return this.draft_person;
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
		if (!(object instanceof Dispatch)) 
		{
			return false;
		}
		Dispatch rhs = (Dispatch) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.check_personID, rhs.check_personID)
		.append(this.countersign_personID, rhs.countersign_personID)
		.append(this.dispatch_id, rhs.dispatch_id)
		.append(this.time, rhs.time)
		.append(this.title, rhs.title)
		.append(this.type, rhs.type)
		.append(this.urgency_degree, rhs.urgency_degree)
		.append(this.keyword, rhs.keyword)
		.append(this.secret_level, rhs.secret_level)
		.append(this.send_unit, rhs.send_unit)
		.append(this.cc_unit, rhs.cc_unit)
		.append(this.dispatch_content, rhs.dispatch_content)
		.append(this.check_person, rhs.check_person)
		.append(this.countersign_person, rhs.countersign_person)
		.append(this.attachment, rhs.attachment)
		.append(this.draft_personID, rhs.draft_personID)
		.append(this.draft_person, rhs.draft_person)
		.append(this.state, rhs.state)
		.append(this.checkState, rhs.checkState)
		.append(this.redState, rhs.redState)
		.append(this.examineState, rhs.examineState)
		.append(this.sendState, rhs.sendState)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.check_personID) 
		.append(this.countersign_personID) 
		.append(this.dispatch_id) 
		.append(this.time) 
		.append(this.title) 
		.append(this.type) 
		.append(this.urgency_degree) 
		.append(this.keyword) 
		.append(this.secret_level) 
		.append(this.send_unit) 
		.append(this.cc_unit) 
		.append(this.dispatch_content) 
		.append(this.check_person) 
		.append(this.countersign_person) 
		.append(this.attachment) 
		.append(this.draft_personID) 
		.append(this.draft_person) 
		.append(this.state) 
		.append(this.checkState) 
		.append(this.redState) 
		.append(this.examineState) 
		.append(this.sendState)
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("check_personID", this.check_personID) 
		.append("countersign_personID", this.countersign_personID) 
		.append("dispatch_id", this.dispatch_id) 
		.append("time", this.time) 
		.append("title", this.title) 
		.append("type", this.type) 
		.append("urgency_degree", this.urgency_degree) 
		.append("keyword", this.keyword) 
		.append("secret_level", this.secret_level) 
		.append("send_unit", this.send_unit) 
		.append("cc_unit", this.cc_unit) 
		.append("dispatch_content", this.dispatch_content) 
		.append("check_person", this.check_person) 
		.append("countersign_person", this.countersign_person) 
		.append("attachment", this.attachment) 
		.append("draft_personID", this.draft_personID) 
		.append("draft_person", this.draft_person) 
		.append("state",this.state) 
		.append("checkState",this.checkState) 
		.append("redState",this.redState) 
		.append("examineState",this.examineState) 
		.append("sendState",this.sendState)
		.toString();
	}

}