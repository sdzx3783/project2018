package com.hotent.makshi.model.assetapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:资产申购汇总表 Model对象
 */
public class AssetAll extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *一级部门ID
	 */
	protected String  fist_departmentID;
	/**
	 *使用人ID
	 */
	protected String  use_nameID;
	/**
	 *二级部门或项目部ID
	 */
	protected String  second_departmentID;
	/**
	 *申请人
	 */
	protected String  user_name;
	protected String  user_id;
	/**
	 *一级部门
	 */
	protected String  fist_department;
	/**
	 *使用人
	 */
	protected String  use_name;
	/**
	 *二级部门或项目部
	 */
	protected String  second_department;
	/**
	 *固定资产名称
	 */
	protected String  asset_name;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *规格型号
	 */
	protected String  specifications;
	/**
	 *质量要求及验收标准
	 */
	protected String  standard;
	/**
	 *类型
	 */
	protected String  type;
	/**
	 *采购方式
	 */
	protected String  buy_type;
	/**
	 *资产信息附件
	 */
	protected String  attachment;
	/**
	 *条码
	 */
	protected String  device_id;
	/**
	 *申请时间
	 */
	protected java.util.Date  application_time;
	/**
	 *审批状态
	 */
	protected String  state;
	/**
	 *工单号
	 */
	protected String  globalflowno;
	/**
	 * 资产类别
	 */
	protected String assetType;
	/**
	 * 资产编号
	 * @return
	 */
	protected String assetId;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFist_departmentID(String fist_departmentID) 
	{
		this.fist_departmentID = fist_departmentID;
	}
	/**
	 * 返回 一级部门ID
	 * @return
	 */
	public String getFist_departmentID() 
	{
		return this.fist_departmentID;
	}
	public void setUse_nameID(String use_nameID) 
	{
		this.use_nameID = use_nameID;
	}
	/**
	 * 返回 使用人ID
	 * @return
	 */
	public String getUse_nameID() 
	{
		return this.use_nameID;
	}
	public void setSecond_departmentID(String second_departmentID) 
	{
		this.second_departmentID = second_departmentID;
	}
	/**
	 * 返回 二级部门或项目部ID
	 * @return
	 */
	public String getSecond_departmentID() 
	{
		return this.second_departmentID;
	}
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setFist_department(String fist_department) 
	{
		this.fist_department = fist_department;
	}
	/**
	 * 返回 一级部门
	 * @return
	 */
	public String getFist_department() 
	{
		return this.fist_department;
	}
	public void setUse_name(String use_name) 
	{
		this.use_name = use_name;
	}
	/**
	 * 返回 使用人
	 * @return
	 */
	public String getUse_name() 
	{
		return this.use_name;
	}
	public void setSecond_department(String second_department) 
	{
		this.second_department = second_department;
	}
	/**
	 * 返回 二级部门或项目部
	 * @return
	 */
	public String getSecond_department() 
	{
		return this.second_department;
	}
	public void setAsset_name(String asset_name) 
	{
		this.asset_name = asset_name;
	}
	/**
	 * 返回 固定资产名称
	 * @return
	 */
	public String getAsset_name() 
	{
		return this.asset_name;
	}
	public void setNumber(Long number) 
	{
		this.number = number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public Long getNumber() 
	{
		return this.number;
	}
	public void setSpecifications(String specifications) 
	{
		this.specifications = specifications;
	}
	/**
	 * 返回 规格型号
	 * @return
	 */
	public String getSpecifications() 
	{
		return this.specifications;
	}
	public void setStandard(String standard) 
	{
		this.standard = standard;
	}
	/**
	 * 返回 质量要求及验收标准
	 * @return
	 */
	public String getStandard() 
	{
		return this.standard;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setBuy_type(String buy_type) 
	{
		this.buy_type = buy_type;
	}
	/**
	 * 返回 采购方式
	 * @return
	 */
	public String getBuy_type() 
	{
		return this.buy_type;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 资产信息附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setDevice_id(String device_id) 
	{
		this.device_id = device_id;
	}
	/**
	 * 返回 条码
	 * @return
	 */
	public String getDevice_id() 
	{
		return this.device_id;
	}
	public void setApplication_time(Date application_time) 
	{
		this.application_time = application_time;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public Date getApplication_time() 
	{
		return this.application_time;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 审批状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
	}
	public void setGlobalflowno(String globalflowno) 
	{
		this.globalflowno = globalflowno;
	}
	/**
	 * 返回 工单号
	 * @return
	 */
	public String getGlobalflowno() 
	{
		return this.globalflowno;
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
		if (!(object instanceof AssetAll)) 
		{
			return false;
		}
		AssetAll rhs = (AssetAll) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.fist_departmentID, rhs.fist_departmentID)
		.append(this.use_nameID, rhs.use_nameID)
		.append(this.second_departmentID, rhs.second_departmentID)
		.append(this.user_name, rhs.user_name)
		.append(this.fist_department, rhs.fist_department)
		.append(this.use_name, rhs.use_name)
		.append(this.second_department, rhs.second_department)
		.append(this.asset_name, rhs.asset_name)
		.append(this.number, rhs.number)
		.append(this.specifications, rhs.specifications)
		.append(this.standard, rhs.standard)
		.append(this.type, rhs.type)
		.append(this.buy_type, rhs.buy_type)
		.append(this.attachment, rhs.attachment)
		.append(this.device_id, rhs.device_id)
		.append(this.application_time, rhs.application_time)
		.append(this.state, rhs.state)
		.append(this.globalflowno, rhs.globalflowno)
		.append(this.assetType, rhs.assetType)
		.append(this.assetId, rhs.assetId)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.fist_departmentID) 
		.append(this.use_nameID) 
		.append(this.second_departmentID) 
		.append(this.user_name) 
		.append(this.fist_department) 
		.append(this.use_name) 
		.append(this.second_department) 
		.append(this.asset_name) 
		.append(this.number) 
		.append(this.specifications) 
		.append(this.standard) 
		.append(this.type) 
		.append(this.buy_type) 
		.append(this.attachment) 
		.append(this.device_id) 
		.append(this.application_time) 
		.append(this.state) 
		.append(this.globalflowno) 
		.append(this.assetType) 
		.append(this.assetId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("fist_departmentID", this.fist_departmentID) 
		.append("use_nameID", this.use_nameID) 
		.append("second_departmentID", this.second_departmentID) 
		.append("user_name", this.user_name) 
		.append("fist_department", this.fist_department) 
		.append("use_name", this.use_name) 
		.append("second_department", this.second_department) 
		.append("asset_name", this.asset_name) 
		.append("number", this.number) 
		.append("specifications", this.specifications) 
		.append("standard", this.standard) 
		.append("type", this.type) 
		.append("buy_type", this.buy_type) 
		.append("attachment", this.attachment) 
		.append("device_id", this.device_id) 
		.append("application_time", this.application_time) 
		.append("state", this.state) 
		.append("globalflowno", this.globalflowno) 
		.append("assetType", this.assetType)
		.append("assetId", this.assetId)
		.toString();
	}

}