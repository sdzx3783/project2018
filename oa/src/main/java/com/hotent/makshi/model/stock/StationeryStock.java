package com.hotent.makshi.model.stock;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:文具库存表 Model对象
 */
public class StationeryStock extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *名称
	 */
	protected String  name;
	/**
	 *规格型号
	 */
	protected String  specification;
	/**
	 *数量
	 */
	protected String  purchase_number;
	/**
	 *上月采购
	 */
	protected String  purchase_last_month;
	/**
	 *上月发出
	 */
	protected String  send_last_month;
	/**
	 *库存
	 */
	protected String  stock;
	
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
	
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
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
	public void setPurchase_number(String purchase_number) 
	{
		this.purchase_number = purchase_number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public String getPurchase_number() 
	{
		return this.purchase_number;
	}
	public void setPurchase_last_month(String purchase_last_month) 
	{
		this.purchase_last_month = purchase_last_month;
	}
	/**
	 * 返回 上月采购
	 * @return
	 */
	public String getPurchase_last_month() 
	{
		return this.purchase_last_month;
	}
	public void setSend_last_month(String send_last_month) 
	{
		this.send_last_month = send_last_month;
	}
	/**
	 * 返回 上月发出
	 * @return
	 */
	public String getSend_last_month() 
	{
		return this.send_last_month;
	}
	public void setStock(String stock) 
	{
		this.stock = stock;
	}
	/**
	 * 返回 库存
	 * @return
	 */
	public String getStock() 
	{
		return this.stock;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof StationeryStock)) 
		{
			return false;
		}
		StationeryStock rhs = (StationeryStock) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.specification, rhs.specification)
		.append(this.purchase_number, rhs.purchase_number)
		.append(this.purchase_last_month, rhs.purchase_last_month)
		.append(this.send_last_month, rhs.send_last_month)
		.append(this.stock, rhs.stock)
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
		.append(this.specification) 
		.append(this.purchase_number) 
		.append(this.purchase_last_month) 
		.append(this.send_last_month) 
		.append(this.stock) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("name", this.name) 
		.append("specification", this.specification) 
		.append("purchase_number", this.purchase_number) 
		.append("purchase_last_month", this.purchase_last_month) 
		.append("send_last_month", this.send_last_month) 
		.append("stock", this.stock) 
		.toString();
	}

}