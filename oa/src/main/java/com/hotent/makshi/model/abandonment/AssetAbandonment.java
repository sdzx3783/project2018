package com.hotent.makshi.model.abandonment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:资产报废表 Model对象
 */
public class AssetAbandonment extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *使用人ID
	 */
	protected String  use_nameID;
	/**
	 *申请人
	 */
	protected String  application_name;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *申请部门
	 */
	protected String  department;
	/**
	 *使用人
	 */
	protected String  use_name;
	/**
	 *固定资产编号
	 */
	protected String  asset_id;
	/**
	 *固定资产名称
	 */
	protected String  asset_name;
	/**
	 *规格型号
	 */
	protected String  specification;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *质量要求及验收标准
	 */
	protected String  standard;
	/**
	 *固定资产取得方式
	 */
	protected String  get_type;
	/**
	 *购入时间
	 */
	protected java.util.Date  buy_date;
	/**
	 *报废原因
	 */
	protected String  reason;
	/**
	 *审批状态
	 */
	protected String  state;
	/**
	 *工单号
	 */
	protected String  globalflowno;
	/**
	 *申请时间
	 */
	protected java.util.Date  application_time;
	protected String  attachment;
	
	
	protected Long  runId=0L;
	
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setApplication_name(String application_name) 
	{
		this.application_name = application_name;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplication_name() 
	{
		return this.application_name;
	}
	public void setFirst_department(String first_department) 
	{
		this.department = first_department;
	}
	/**
	 * 返回 一级部门
	 * @return
	 */
	public String getFirst_department() 
	{
		return this.department;
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
	public void setAsset_id(String asset_id) 
	{
		this.asset_id = asset_id;
	}
	/**
	 * 返回 固定资产编号
	 * @return
	 */
	public String getAsset_id() 
	{
		return this.asset_id;
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
	public void setGet_type(String get_type) 
	{
		this.get_type = get_type;
	}
	/**
	 * 返回 固定资产取得方式
	 * @return
	 */
	public String getGet_type() 
	{
		return this.get_type;
	}
	public void setSpecification(String specification) 
	{
		this.specification = specification;
	}
	/**
	 * 返回 规格型号
	 * @return
	 */
	public String getSpecification() 
	{
		return this.specification;
	}
	public void setBuy_date(java.util.Date buy_date) 
	{
		this.buy_date = buy_date;
	}
	/**
	 * 返回 购入时间
	 * @return
	 */
	public java.util.Date getBuy_date() 
	{
		return this.buy_date;
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
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 报废原因
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
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
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	
	
	
   	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AssetAbandonment)) 
		{
			return false;
		}
		AssetAbandonment rhs = (AssetAbandonment) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.use_nameID, rhs.use_nameID)
		.append(this.application_name, rhs.application_name)
		.append(this.department, rhs.department)
		.append(this.use_name, rhs.use_name)
		.append(this.department, rhs.department)
		.append(this.asset_id, rhs.asset_id)
		.append(this.standard, rhs.standard)
		.append(this.asset_name, rhs.asset_name)
		.append(this.get_type, rhs.get_type)
		.append(this.specification, rhs.specification)
		.append(this.buy_date, rhs.buy_date)
		.append(this.number, rhs.number)
		.append(this.reason, rhs.reason)
		.append(this.state, rhs.state)
		.append(this.globalflowno, rhs.globalflowno)
		.append(this.application_time, rhs.application_time)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.use_nameID) 
		.append(this.application_name) 
		.append(this.department) 
		.append(this.use_name) 
		.append(this.department) 
		.append(this.asset_id) 
		.append(this.standard) 
		.append(this.asset_name) 
		.append(this.get_type) 
		.append(this.specification) 
		.append(this.buy_date) 
		.append(this.number) 
		.append(this.reason) 
		.append(this.state) 
		.append(this.globalflowno) 
		.append(this.application_time) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("use_nameID", this.use_nameID) 
		.append("application_name", this.application_name) 
		.append("first_department", this.department) 
		.append("use_name", this.use_name) 
		.append("second_department", this.department) 
		.append("asset_id", this.asset_id) 
		.append("standard", this.standard) 
		.append("asset_name", this.asset_name) 
		.append("get_type", this.get_type) 
		.append("specification", this.specification) 
		.append("buy_date", this.buy_date) 
		.append("number", this.number) 
		.append("reason", this.reason) 
		.append("state", this.state) 
		.append("globalflowno", this.globalflowno) 
		.append("application_time", this.application_time) 
		.toString();
	}

}