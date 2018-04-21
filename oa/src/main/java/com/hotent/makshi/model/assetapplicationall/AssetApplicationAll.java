package com.hotent.makshi.model.assetapplicationall;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:资产申购汇总表 Model对象
 */
public class AssetApplicationAll extends WfBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键
	protected Long id;
	/**
	 *使用人ID
	 */
	protected String  use_nameID;
	/**
	 *申请人
	 */
	protected String  user_name;
	/**
	 *所在部门
	 */
	protected String  fist_department;
	/**
	 *使用人
	 */
	protected String  use_name;
	/**
	 *员工编号
	 */
	protected String  user_id;
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
	 *资产类别
	 */
	protected String  assetType;
	/**
	 *条码
	 */
	protected String  assetId;
	/**
	 *条码
	 */
	protected String  remarks;
	/**
	 *资产信息附件
	 */
	protected String  attachment;
	protected Long  runId=0L;
	
	/**
	 *审批状态
	 */
	protected String  state;
	/**
	 *工号
	 */
	protected String  account;
	
	protected String globalflowno;
	
	protected java.util.Date application_time;
	
	/**
	 * 条码编号
	 * @return
	 */
	protected String card_number;
	
	protected List<AssetAppiList> assetAppiListList=new ArrayList<AssetAppiList>();
	
	public void setAssetAppiListList(List<AssetAppiList> assetAppiListList) 
	{
		this.assetAppiListList = assetAppiListList;
	}
	/**
	 * 返回 资产申购汇总表列表
	 * @return
	 */
	public List<AssetAppiList> getAssetAppiListList() 
	{
		return this.assetAppiListList;
	}
	public String getGlobalflowno() {
		return globalflowno;
	}
	public void setGlobalflowno(String globalflowno) {
		this.globalflowno = globalflowno;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public java.util.Date getApplication_time() {
		return application_time;
	}
	public void setApplication_time(java.util.Date application_time) {
		this.application_time = application_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * 返回 所在部门
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
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
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
	public void setAssetType(String assetType) 
	{
		this.assetType = assetType;
	}
	/**
	 * 返回 资产类别
	 * @return
	 */
	public String getAssetType() 
	{
		return this.assetType;
	}
	public void setAssetId(String assetId) 
	{
		this.assetId = assetId;
	}
	/**
	 * 返回 条码
	 * @return
	 */
	public String getAssetId() 
	{
		return this.assetId;
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
		if (!(object instanceof AssetApplicationAll)) 
		{
			return false;
		}
		AssetApplicationAll rhs = (AssetApplicationAll) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.use_nameID, rhs.use_nameID)
		.append(this.user_name, rhs.user_name)
		.append(this.fist_department, rhs.fist_department)
		.append(this.use_name, rhs.use_name)
		.append(this.user_id, rhs.user_id)
		.append(this.asset_name, rhs.asset_name)
		.append(this.number, rhs.number)
		.append(this.specifications, rhs.specifications)
		.append(this.standard, rhs.standard)
		.append(this.type, rhs.type)
		.append(this.buy_type, rhs.buy_type)
		.append(this.assetType, rhs.assetType)
		.append(this.assetId, rhs.assetId)
		.append(this.attachment, rhs.attachment)
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
		.append(this.user_name) 
		.append(this.fist_department) 
		.append(this.use_name) 
		.append(this.user_id) 
		.append(this.asset_name) 
		.append(this.number) 
		.append(this.specifications) 
		.append(this.standard) 
		.append(this.type) 
		.append(this.buy_type) 
		.append(this.assetType) 
		.append(this.assetId) 
		.append(this.attachment) 
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
		.append("user_name", this.user_name) 
		.append("fist_department", this.fist_department) 
		.append("use_name", this.use_name) 
		.append("user_id", this.user_id) 
		.append("asset_name", this.asset_name) 
		.append("number", this.number) 
		.append("specifications", this.specifications) 
		.append("standard", this.standard) 
		.append("type", this.type) 
		.append("buy_type", this.buy_type) 
		.append("assetType", this.assetType) 
		.append("assetId", this.assetId) 
		.append("attachment", this.attachment) 
		.toString();
	}

}