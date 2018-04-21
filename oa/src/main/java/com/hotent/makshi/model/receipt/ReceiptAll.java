package com.hotent.makshi.model.receipt;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:收文汇总表 Model对象
 */
public class ReceiptAll extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *初审人员ID
	 */
	protected String  first_check_personID;
	/**
	 *办文责任人ID
	 */
	protected String  responsible_personID;
	/**
	 *办文人员ID
	 */
	protected String  handle_personID;
	/**
	 *收文编号
	 */
	protected String  receipt_id;
	/**
	 *收文日期
	 */
	protected String  time;
	/**
	 *来文标题
	 */
	protected String  title;
	/**
	 *来文单位
	 */
	protected String  dispatch_unit;
	/**
	 *紧急程度
	 */
	protected String  urgency_degree;
	/**
	 *来文字号
	 */
	protected String  type;
	/**
	 *办理时限
	 */
	protected java.util.Date  time_limit;
	/**
	 *相关内容
	 */
	protected String  content;
	/**
	 *初审人员
	 */
	protected String  first_check_person;
	/**
	 *办文责任人
	 */
	protected String  responsible_person;
	/**
	 *办文人员
	 */
	protected String  handle_person;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *备注
	 */
	protected String  remarks;
	/**
	 *表单状态
	 */
	protected String checkState;
	/**
	 *表单状态
	 */
	protected String globalFlowNo;
	
	/**
	 * 阅读状态
	 * @return
	 */
	protected String readState;
	protected String sendState;
	protected String  state;
	protected Long  runId=0L;
	
	public String getReadState() {
		return readState;
	}
	public void setReadState(String readState) {
		this.readState = readState;
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFirst_check_personID(String first_check_personID) 
	{
		this.first_check_personID = first_check_personID;
	}
	/**
	 * 返回 初审人员ID
	 * @return
	 */
	public String getFirst_check_personID() 
	{
		return this.first_check_personID;
	}
	public void setResponsible_personID(String responsible_personID) 
	{
		this.responsible_personID = responsible_personID;
	}
	/**
	 * 返回 办文责任人ID
	 * @return
	 */
	public String getResponsible_personID() 
	{
		return this.responsible_personID;
	}
	public void setHandle_personID(String handle_personID) 
	{
		this.handle_personID = handle_personID;
	}
	/**
	 * 返回 办文人员ID
	 * @return
	 */
	public String getHandle_personID() 
	{
		return this.handle_personID;
	}
	public void setReceipt_id(String receipt_id) 
	{
		this.receipt_id = receipt_id;
	}
	/**
	 * 返回 收文编号
	 * @return
	 */
	public String getReceipt_id() 
	{
		return this.receipt_id;
	}
	public void setTime(String time) 
	{
		this.time = time;
	}
	/**
	 * 返回 收文日期
	 * @return
	 */
	public String getTime() 
	{
		return this.time;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 来文标题
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
	}
	public void setDispatch_unit(String dispatch_unit) 
	{
		this.dispatch_unit = dispatch_unit;
	}
	/**
	 * 返回 来文单位
	 * @return
	 */
	public String getDispatch_unit() 
	{
		return this.dispatch_unit;
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
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 来文字号
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setTime_limit(java.util.Date time_limit) 
	{
		this.time_limit = time_limit;
	}
	/**
	 * 返回 办理时限
	 * @return
	 */
	public java.util.Date getTime_limit() 
	{
		return this.time_limit;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 相关内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setFirst_check_person(String first_check_person) 
	{
		this.first_check_person = first_check_person;
	}
	/**
	 * 返回 初审人员
	 * @return
	 */
	public String getFirst_check_person() 
	{
		return this.first_check_person;
	}
	public void setResponsible_person(String responsible_person) 
	{
		this.responsible_person = responsible_person;
	}
	/**
	 * 返回 办文责任人
	 * @return
	 */
	public String getResponsible_person() 
	{
		return this.responsible_person;
	}
	public void setHandle_person(String handle_person) 
	{
		this.handle_person = handle_person;
	}
	/**
	 * 返回 办文人员
	 * @return
	 */
	public String getHandle_person() 
	{
		return this.handle_person;
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
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemarks() 
	{
		return this.remarks;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 表单状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
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
		if (!(object instanceof ReceiptAll)) 
		{
			return false;
		}
		ReceiptAll rhs = (ReceiptAll) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.first_check_personID, rhs.first_check_personID)
		.append(this.responsible_personID, rhs.responsible_personID)
		.append(this.handle_personID, rhs.handle_personID)
		.append(this.receipt_id, rhs.receipt_id)
		.append(this.time, rhs.time)
		.append(this.title, rhs.title)
		.append(this.dispatch_unit, rhs.dispatch_unit)
		.append(this.urgency_degree, rhs.urgency_degree)
		.append(this.type, rhs.type)
		.append(this.time_limit, rhs.time_limit)
		.append(this.content, rhs.content)
		.append(this.first_check_person, rhs.first_check_person)
		.append(this.responsible_person, rhs.responsible_person)
		.append(this.handle_person, rhs.handle_person)
		.append(this.attachment, rhs.attachment)
		.append(this.remarks, rhs.remarks)
		.append(this.state, rhs.state)
		.append(this.checkState, rhs.checkState)
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
		.append(this.first_check_personID) 
		.append(this.responsible_personID) 
		.append(this.handle_personID) 
		.append(this.receipt_id) 
		.append(this.time) 
		.append(this.title) 
		.append(this.dispatch_unit) 
		.append(this.urgency_degree) 
		.append(this.type) 
		.append(this.time_limit) 
		.append(this.content) 
		.append(this.first_check_person) 
		.append(this.responsible_person) 
		.append(this.handle_person) 
		.append(this.attachment) 
		.append(this.remarks) 
		.append(this.state) 
		.append(this.checkState)
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
		.append("first_check_personID", this.first_check_personID) 
		.append("responsible_personID", this.responsible_personID) 
		.append("handle_personID", this.handle_personID) 
		.append("receipt_id", this.receipt_id) 
		.append("time", this.time) 
		.append("title", this.title) 
		.append("dispatch_unit", this.dispatch_unit) 
		.append("urgency_degree", this.urgency_degree) 
		.append("type", this.type) 
		.append("time_limit", this.time_limit) 
		.append("content", this.content) 
		.append("first_check_person", this.first_check_person) 
		.append("responsible_person", this.responsible_person) 
		.append("handle_person", this.handle_person) 
		.append("attachment", this.attachment) 
		.append("remarks", this.remarks) 
		.append("state", this.state) 
		.append("checkState",this.checkState)
		.append("sendState",this.sendState)
		.toString();
	}

}