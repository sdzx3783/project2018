package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:径流实验申请(水保示范园) Model对象
 */
public class RiverExperiment extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *实验人员ID
	 */
	protected String  experimenterID;
	/**
	 *取样人员ID
	 */
	protected String  sampleManID;
	/**
	 *部门负责人审核ID
	 */
	protected String  chargerID;
	/**
	 *部门领导ID
	 */
	protected String  orgleaderID;
	/**
	 *实验名称
	 */
	protected String  name;
	/**
	 *实验人员
	 */
	protected String  experimenter;
	/**
	 *取样时间
	 */
	protected java.util.Date  samlpeTime;
	/**
	 *取样人员
	 */
	protected String  sampleMan;
	/**
	 *实验时间
	 */
	protected java.util.Date  experiTime;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *部门负责人审核
	 */
	protected String  charger;
	/**
	 *部门领导
	 */
	protected String  orgleader;
	
	protected Integer  from;
	
	
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setExperimenterID(String experimenterID) 
	{
		this.experimenterID = experimenterID;
	}
	/**
	 * 返回 实验人员ID
	 * @return
	 */
	public String getExperimenterID() 
	{
		return this.experimenterID;
	}
	public void setSampleManID(String sampleManID) 
	{
		this.sampleManID = sampleManID;
	}
	/**
	 * 返回 取样人员ID
	 * @return
	 */
	public String getSampleManID() 
	{
		return this.sampleManID;
	}
	public void setChargerID(String chargerID) 
	{
		this.chargerID = chargerID;
	}
	/**
	 * 返回 部门负责人审核ID
	 * @return
	 */
	public String getChargerID() 
	{
		return this.chargerID;
	}
	public void setOrgleaderID(String orgleaderID) 
	{
		this.orgleaderID = orgleaderID;
	}
	/**
	 * 返回 部门领导ID
	 * @return
	 */
	public String getOrgleaderID() 
	{
		return this.orgleaderID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 实验名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setExperimenter(String experimenter) 
	{
		this.experimenter = experimenter;
	}
	/**
	 * 返回 实验人员
	 * @return
	 */
	public String getExperimenter() 
	{
		return this.experimenter;
	}
	public void setSamlpeTime(java.util.Date samlpeTime) 
	{
		this.samlpeTime = samlpeTime;
	}
	/**
	 * 返回 取样时间
	 * @return
	 */
	public java.util.Date getSamlpeTime() 
	{
		return this.samlpeTime;
	}
	public void setSampleMan(String sampleMan) 
	{
		this.sampleMan = sampleMan;
	}
	/**
	 * 返回 取样人员
	 * @return
	 */
	public String getSampleMan() 
	{
		return this.sampleMan;
	}
	public void setExperiTime(java.util.Date experiTime) 
	{
		this.experiTime = experiTime;
	}
	/**
	 * 返回 实验时间
	 * @return
	 */
	public java.util.Date getExperiTime() 
	{
		return this.experiTime;
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
	public void setCharger(String charger) 
	{
		this.charger = charger;
	}
	/**
	 * 返回 部门负责人审核
	 * @return
	 */
	public String getCharger() 
	{
		return this.charger;
	}
	public void setOrgleader(String orgleader) 
	{
		this.orgleader = orgleader;
	}
	/**
	 * 返回 部门领导
	 * @return
	 */
	public String getOrgleader() 
	{
		return this.orgleader;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RiverExperiment)) 
		{
			return false;
		}
		RiverExperiment rhs = (RiverExperiment) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.experimenterID, rhs.experimenterID)
		.append(this.sampleManID, rhs.sampleManID)
		.append(this.chargerID, rhs.chargerID)
		.append(this.orgleaderID, rhs.orgleaderID)
		.append(this.name, rhs.name)
		.append(this.experimenter, rhs.experimenter)
		.append(this.samlpeTime, rhs.samlpeTime)
		.append(this.sampleMan, rhs.sampleMan)
		.append(this.experiTime, rhs.experiTime)
		.append(this.remark, rhs.remark)
		.append(this.attachment, rhs.attachment)
		.append(this.charger, rhs.charger)
		.append(this.orgleader, rhs.orgleader)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.experimenterID) 
		.append(this.sampleManID) 
		.append(this.chargerID) 
		.append(this.orgleaderID) 
		.append(this.name) 
		.append(this.experimenter) 
		.append(this.samlpeTime) 
		.append(this.sampleMan) 
		.append(this.experiTime) 
		.append(this.remark) 
		.append(this.attachment) 
		.append(this.charger) 
		.append(this.orgleader) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("experimenterID", this.experimenterID) 
		.append("sampleManID", this.sampleManID) 
		.append("chargerID", this.chargerID) 
		.append("orgleaderID", this.orgleaderID) 
		.append("name", this.name) 
		.append("experimenter", this.experimenter) 
		.append("samlpeTime", this.samlpeTime) 
		.append("sampleMan", this.sampleMan) 
		.append("experiTime", this.experiTime) 
		.append("remark", this.remark) 
		.append("attachment", this.attachment) 
		.append("charger", this.charger) 
		.append("orgleader", this.orgleader) 
		.toString();
	}

}