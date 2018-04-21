package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:物品采购信息 Model对象
 */
public class HjpurchaseList extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *物品名称
	 */
	protected String  name;
	/**
	 *规格型号
	 */
	protected String  specification;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *单价
	 */
	protected String  price;
	
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
	 * 返回 物品名称
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
	public void setPrice(String price) 
	{
		this.price = price;
	}
	/**
	 * 返回 单价
	 * @return
	 */
	public String getPrice() 
	{
		return this.price;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof HjpurchaseList)) 
		{
			return false;
		}
		HjpurchaseList rhs = (HjpurchaseList) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.specification, rhs.specification)
		.append(this.number, rhs.number)
		.append(this.price, rhs.price)
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
		.append(this.number) 
		.append(this.price) 
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
		.append("number", this.number) 
		.append("price", this.price) 
		.toString();
	}

}