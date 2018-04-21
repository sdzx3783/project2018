package com.hotent.makshi.model.assetrepair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:资产维护表 Model对象
 */
public class AssetRepair extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *验收人ID
	 */
	protected String  check_personID;
	/**
	 *资产编号
	 */
	protected String  asset_id;
	/**
	 *保养单位
	 */
	protected String  repari_unit;
	/**
	 *保养日期
	 */
	protected java.util.Date  date;
	/**
	 *验收人
	 */
	protected String  check_person;
	/**
	 *保养内容
	 */
	protected String  repair_conten;
	/**
	 *备注
	 */
	protected String  remarks;
	/**
	 *申请人
	 */
	protected String  user_name;
	/**
	 *审批状态
	 */
	protected String  state;
	/**
	 *申请时间
	 */
	protected java.util.Date  application_date;
	/**
	 *工单号
	 */
	protected String  globalflowno;
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
	 * 返回 验收人ID
	 * @return
	 */
	public String getCheck_personID() 
	{
		return this.check_personID;
	}
	public void setAsset_id(String asset_id) 
	{
		this.asset_id = asset_id;
	}
	/**
	 * 返回 资产编号
	 * @return
	 */
	public String getAsset_id() 
	{
		return this.asset_id;
	}
	public void setRepari_unit(String repari_unit) 
	{
		this.repari_unit = repari_unit;
	}
	/**
	 * 返回 保养单位
	 * @return
	 */
	public String getRepari_unit() 
	{
		return this.repari_unit;
	}
	public void setDate(java.util.Date date) 
	{
		this.date = date;
	}
	/**
	 * 返回 保养日期
	 * @return
	 */
	public java.util.Date getDate() 
	{
		return this.date;
	}
	public void setCheck_person(String check_person) 
	{
		this.check_person = check_person;
	}
	/**
	 * 返回 验收人
	 * @return
	 */
	public String getCheck_person() 
	{
		return this.check_person;
	}
	public void setRepair_conten(String repair_conten) 
	{
		this.repair_conten = repair_conten;
	}
	/**
	 * 返回 保养内容
	 * @return
	 */
	public String getRepair_conten() 
	{
		return this.repair_conten;
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
	public void setApplication_date(Date application_date) 
	{
		this.application_date = application_date;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public Date getApplication_date() 
	{
		return this.application_date;
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
		if (!(object instanceof AssetRepair)) 
		{
			return false;
		}
		AssetRepair rhs = (AssetRepair) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.check_personID, rhs.check_personID)
		.append(this.asset_id, rhs.asset_id)
		.append(this.repari_unit, rhs.repari_unit)
		.append(this.date, rhs.date)
		.append(this.check_person, rhs.check_person)
		.append(this.repair_conten, rhs.repair_conten)
		.append(this.remarks, rhs.remarks)
		.append(this.user_name, rhs.user_name)
		.append(this.state, rhs.state)
		.append(this.application_date, rhs.application_date)
		.append(this.globalflowno, rhs.globalflowno)
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
		.append(this.asset_id) 
		.append(this.repari_unit) 
		.append(this.date) 
		.append(this.check_person) 
		.append(this.repair_conten) 
		.append(this.remarks) 
		.append(this.user_name) 
		.append(this.state) 
		.append(this.application_date) 
		.append(this.globalflowno) 
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
		.append("asset_id", this.asset_id) 
		.append("repari_unit", this.repari_unit) 
		.append("date", this.date) 
		.append("check_person", this.check_person) 
		.append("repair_conten", this.repair_conten) 
		.append("remarks", this.remarks) 
		.append("user_name", this.user_name) 
		.append("state", this.state) 
		.append("application_date", this.application_date) 
		.append("globalflowno", this.globalflowno) 
		.toString();
	}

}