package com.hotent.makshi.model.qualification;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:借阅资质 Model对象
 */
public class Jyzz extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *资质类型
	 */
	protected String  listType;
	/**
	 *资质名称
	 */
	protected String  listName;
	/**
	 *归还时间
	 */
	protected java.util.Date  listDate;
	/**
	 *是否归还
	 */
	protected String  listBack;
	/**
	 *资质Id
	 */
	protected String  listId;
	
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
	
	public void setListType(String listType) 
	{
		this.listType = listType;
	}
	/**
	 * 返回 资质类型
	 * @return
	 */
	public String getListType() 
	{
		return this.listType;
	}
	public void setListName(String listName) 
	{
		this.listName = listName;
	}
	/**
	 * 返回 资质名称
	 * @return
	 */
	public String getListName() 
	{
		return this.listName;
	}
	public void setListDate(java.util.Date listDate) 
	{
		this.listDate = listDate;
	}
	/**
	 * 返回 归还时间
	 * @return
	 */
	public java.util.Date getListDate() 
	{
		return this.listDate;
	}
	public void setListBack(String listBack) 
	{
		this.listBack = listBack;
	}
	/**
	 * 返回 是否归还
	 * @return
	 */
	public String getListBack() 
	{
		return this.listBack;
	}
	public void setListId(String listId) 
	{
		this.listId = listId;
	}
	/**
	 * 返回 资质Id
	 * @return
	 */
	public String getListId() 
	{
		return this.listId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Jyzz)) 
		{
			return false;
		}
		Jyzz rhs = (Jyzz) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.listType, rhs.listType)
		.append(this.listName, rhs.listName)
		.append(this.listDate, rhs.listDate)
		.append(this.listBack, rhs.listBack)
		.append(this.listId, rhs.listId)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.listType) 
		.append(this.listName) 
		.append(this.listDate) 
		.append(this.listBack) 
		.append(this.listId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("listType", this.listType) 
		.append("listName", this.listName) 
		.append("listDate", this.listDate) 
		.append("listBack", this.listBack) 
		.append("listId", this.listId) 
		.toString();
	}

}