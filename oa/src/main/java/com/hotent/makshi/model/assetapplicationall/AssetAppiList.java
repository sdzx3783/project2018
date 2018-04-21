package com.hotent.makshi.model.assetapplicationall;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:申购列表 Model对象
 */
public class AssetAppiList extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *条码编号
	 */
	protected String  card_number;
	/**
	 *条码
	 */
	protected String  assetId;
	/**
	 *备注
	 */
	protected String  remarks;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
	public void setCard_number(String card_number) 
	{
		this.card_number = card_number;
	}
	/**
	 * 返回 条码编号
	 * @return
	 */
	public String getCard_number() 
	{
		return this.card_number;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AssetAppiList)) 
		{
			return false;
		}
		AssetAppiList rhs = (AssetAppiList) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.card_number, rhs.card_number)
		.append(this.assetId, rhs.assetId)
		.append(this.remarks, rhs.remarks)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.card_number) 
		.append(this.assetId) 
		.append(this.remarks) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("card_number", this.card_number) 
		.append("assetId", this.assetId) 
		.append("remarks", this.remarks) 
		.toString();
	}

}