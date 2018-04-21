package com.hotent.platform.model.bpm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;


/**
 * 对象功能:触发新流程配置 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:miao
 * 创建时间:2015-05-28 11:20:59
 */
@XmlRootElement(name="bpmTriggerDef")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNewFlowTrigger extends BaseModel{
	// ID
	@XmlAttribute
	protected Long id;
	// 名子
	@XmlAttribute
	protected String name;
	// 节点
	@XmlAttribute
	protected String nodeid;
	// 触发动作
	@XmlAttribute
	protected String action;
	// 流程key
	@XmlAttribute
	protected String flowkey;
	// 被触发流程
	@XmlAttribute
	protected String triggerflowkey;
	@XmlAttribute
	protected String triggerflowname;
	// 映射配置
	@XmlElement
	protected String jsonmaping;
	// 触发表单jason
	@XmlElement
	protected String triggerJson;
	// 描述
	@XmlAttribute
	protected String note;

	public void setId(Long id){
		this.id = id;
	}
	/**
	 * 返回 ID
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 返回 名子
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setNodeid(String nodeid){
		this.nodeid = nodeid;
	}
	/**
	 * 返回 节点
	 * @return
	 */
	public String getNodeid() {
		return this.nodeid;
	}
	public void setAction(String action){
		this.action = action;
	}
	/**
	 * 返回 触发动作
	 * @return
	 */
	public String getAction() {
		return this.action;
	}
	public void setFlowkey(String flowkey){
		this.flowkey = flowkey;
	}
	/**
	 * 返回 流程key
	 * @return
	 */
	public String getFlowkey() {
		return this.flowkey;
	}
	public void setTriggerflowkey(String triggerflowkey){
		this.triggerflowkey = triggerflowkey;
	}
	/**
	 * 返回 被触发流程
	 * @return
	 */
	public String getTriggerflowkey() {
		return this.triggerflowkey;
	}
	public void setJsonmaping(String jsonmaping){
		this.jsonmaping = jsonmaping;
	}
	/**
	 * 返回 映射配置
	 * @return
	 */
	public String getJsonmaping() {
		return this.jsonmaping;
	}

   	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTriggerflowname() {
		return triggerflowname;
	}
	public void setTriggerflowname(String triggerflowname) {
		this.triggerflowname = triggerflowname;
	}
	public String getTriggerJson() {
		return triggerJson;
	}
	public void setTriggerJson(String triggerJson) {
		this.triggerJson = triggerJson;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BpmNewFlowTrigger)) 
		{
			return false;
		}
		BpmNewFlowTrigger rhs = (BpmNewFlowTrigger) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.nodeid, rhs.nodeid)
		.append(this.action, rhs.action)
		.append(this.flowkey, rhs.flowkey)
		.append(this.triggerflowkey, rhs.triggerflowkey)
		.append(this.jsonmaping, rhs.jsonmaping)
		.append(this.note, rhs.note)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.nodeid) 
		.append(this.action) 
		.append(this.flowkey) 
		.append(this.triggerflowkey) 
		.append(this.jsonmaping) 
		.append(this.note) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("nodeid", this.nodeid) 
		.append("action", this.action) 
		.append("flowkey", this.flowkey) 
		.append("triggerflowkey", this.triggerflowkey) 
		.append("jsonmaping", this.jsonmaping) 
 		.append("desc", this.note) 
		.toString();
	}
   
  

}