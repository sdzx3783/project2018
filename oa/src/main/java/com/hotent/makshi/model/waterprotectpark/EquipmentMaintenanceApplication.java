package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:设施设备维修申请(水保园流程) Model对象
 */
public class EquipmentMaintenanceApplication extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *职能组ID
	 */
	protected String  orgID;
	/**
	 *部门负责人ID
	 */
	protected String  orgChargerID;
	/**
	 *部门领导ID
	 */
	protected String  orgLeaderID;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请时间
	 */
	protected java.util.Date  applicationTime;
	/**
	 *职能组
	 */
	protected String  org;
	/**
	 *故障发生时间
	 */
	protected java.util.Date  faultTime;
	/**
	 *设备名称
	 */
	protected String  equipmentName;
	/**
	 *存在问题及故障部位
	 */
	protected String  problemDesc;
	/**
	 *维修方案
	 */
	protected String  maintenancePlan;
	/**
	 *维修费用
	 */
	protected Double  maintenanceCost;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *部门负责人
	 */
	protected String  orgCharger;
	/**
	 *部门领导
	 */
	protected String  orgLeader;
	
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
	
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setOrgID(String orgID) 
	{
		this.orgID = orgID;
	}
	/**
	 * 返回 职能组ID
	 * @return
	 */
	public String getOrgID() 
	{
		return this.orgID;
	}
	public void setOrgChargerID(String orgChargerID) 
	{
		this.orgChargerID = orgChargerID;
	}
	/**
	 * 返回 部门负责人ID
	 * @return
	 */
	public String getOrgChargerID() 
	{
		return this.orgChargerID;
	}
	public void setOrgLeaderID(String orgLeaderID) 
	{
		this.orgLeaderID = orgLeaderID;
	}
	/**
	 * 返回 部门领导ID
	 * @return
	 */
	public String getOrgLeaderID() 
	{
		return this.orgLeaderID;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setApplicationTime(java.util.Date applicationTime) 
	{
		this.applicationTime = applicationTime;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getApplicationTime() 
	{
		return this.applicationTime;
	}
	public void setOrg(String org) 
	{
		this.org = org;
	}
	/**
	 * 返回 职能组
	 * @return
	 */
	public String getOrg() 
	{
		return this.org;
	}
	public void setFaultTime(java.util.Date faultTime) 
	{
		this.faultTime = faultTime;
	}
	/**
	 * 返回 故障发生时间
	 * @return
	 */
	public java.util.Date getFaultTime() 
	{
		return this.faultTime;
	}
	public void setEquipmentName(String equipmentName) 
	{
		this.equipmentName = equipmentName;
	}
	/**
	 * 返回 设备名称
	 * @return
	 */
	public String getEquipmentName() 
	{
		return this.equipmentName;
	}
	public void setProblemDesc(String problemDesc) 
	{
		this.problemDesc = problemDesc;
	}
	/**
	 * 返回 存在问题及故障部位
	 * @return
	 */
	public String getProblemDesc() 
	{
		return this.problemDesc;
	}
	public void setMaintenancePlan(String maintenancePlan) 
	{
		this.maintenancePlan = maintenancePlan;
	}
	/**
	 * 返回 维修方案
	 * @return
	 */
	public String getMaintenancePlan() 
	{
		return this.maintenancePlan;
	}
	public void setMaintenanceCost(Double maintenanceCost) 
	{
		this.maintenanceCost = maintenanceCost;
	}
	/**
	 * 返回 维修费用
	 * @return
	 */
	public Double getMaintenanceCost() 
	{
		return this.maintenanceCost;
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
	public void setOrgCharger(String orgCharger) 
	{
		this.orgCharger = orgCharger;
	}
	/**
	 * 返回 部门负责人
	 * @return
	 */
	public String getOrgCharger() 
	{
		return this.orgCharger;
	}
	public void setOrgLeader(String orgLeader) 
	{
		this.orgLeader = orgLeader;
	}
	/**
	 * 返回 部门领导
	 * @return
	 */
	public String getOrgLeader() 
	{
		return this.orgLeader;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof EquipmentMaintenanceApplication)) 
		{
			return false;
		}
		EquipmentMaintenanceApplication rhs = (EquipmentMaintenanceApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.orgID, rhs.orgID)
		.append(this.orgChargerID, rhs.orgChargerID)
		.append(this.orgLeaderID, rhs.orgLeaderID)
		.append(this.applicant, rhs.applicant)
		.append(this.applicationTime, rhs.applicationTime)
		.append(this.org, rhs.org)
		.append(this.faultTime, rhs.faultTime)
		.append(this.equipmentName, rhs.equipmentName)
		.append(this.problemDesc, rhs.problemDesc)
		.append(this.maintenancePlan, rhs.maintenancePlan)
		.append(this.maintenanceCost, rhs.maintenanceCost)
		.append(this.remark, rhs.remark)
		.append(this.attachment, rhs.attachment)
		.append(this.orgCharger, rhs.orgCharger)
		.append(this.orgLeader, rhs.orgLeader)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.applicantID) 
		.append(this.orgID) 
		.append(this.orgChargerID) 
		.append(this.orgLeaderID) 
		.append(this.applicant) 
		.append(this.applicationTime) 
		.append(this.org) 
		.append(this.faultTime) 
		.append(this.equipmentName) 
		.append(this.problemDesc) 
		.append(this.maintenancePlan) 
		.append(this.maintenanceCost) 
		.append(this.remark) 
		.append(this.attachment) 
		.append(this.orgCharger) 
		.append(this.orgLeader) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("applicantID", this.applicantID) 
		.append("orgID", this.orgID) 
		.append("orgChargerID", this.orgChargerID) 
		.append("orgLeaderID", this.orgLeaderID) 
		.append("applicant", this.applicant) 
		.append("applicationTime", this.applicationTime) 
		.append("org", this.org) 
		.append("faultTime", this.faultTime) 
		.append("equipmentName", this.equipmentName) 
		.append("problemDesc", this.problemDesc) 
		.append("maintenancePlan", this.maintenancePlan) 
		.append("maintenanceCost", this.maintenanceCost) 
		.append("remark", this.remark) 
		.append("attachment", this.attachment) 
		.append("orgCharger", this.orgCharger) 
		.append("orgLeader", this.orgLeader) 
		.toString();
	}

}