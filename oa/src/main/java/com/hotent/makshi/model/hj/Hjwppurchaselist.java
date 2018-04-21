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
public class Hjwppurchaselist extends WfBaseModel
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
	 *质量要求
	 */
	protected String  price;
	
	/**
	 * 领用人
	 */
	protected String lname;
	/**
	 * 采购日期
	 */
	protected java.util.Date date;
	/**
	 * 领用部门
	 */
	protected String ldepartment;
	
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getLdepartment() {
		return ldepartment;
	}
	public void setLdepartment(String ldepartment) {
		this.ldepartment = ldepartment;
	}
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
	 * 返回 质量要求
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
		if (!(object instanceof Hjwppurchaselist)) 
		{
			return false;
		}
		Hjwppurchaselist rhs = (Hjwppurchaselist) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.specification, rhs.specification)
		.append(this.number, rhs.number)
		.append(this.price, rhs.price)
		.append(this.lname, rhs.lname)
		.append(this.date, rhs.date)
		.append(this.ldepartment, rhs.ldepartment)
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
		.append(this.lname)
		.append(this.date)
		.append(this.ldepartment)
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
		.append("lname",this.lname)
		.append("date",this.date)
		.append("ldepartment",this.ldepartment)
		.toString();
	}

}