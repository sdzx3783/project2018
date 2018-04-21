package com.hotent.makshi.model.reninfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:拟租信息 Model对象
 */
public class RentInfo extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *拟租户型
	 */
	protected String  house_type;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *拟租房地区
	 */
	protected String  area;
	/**
	 *附件
	 */
	protected String  attachment;
	
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
	
	public void setHouse_type(String house_type) 
	{
		this.house_type = house_type;
	}
	/**
	 * 返回 拟租户型
	 * @return
	 */
	public String getHouse_type() 
	{
		return this.house_type;
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
	public void setArea(String area) 
	{
		this.area = area;
	}
	/**
	 * 返回 拟租房地区
	 * @return
	 */
	public String getArea() 
	{
		return this.area;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RentInfo)) 
		{
			return false;
		}
		RentInfo rhs = (RentInfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.house_type, rhs.house_type)
		.append(this.number, rhs.number)
		.append(this.area, rhs.area)
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
		.append(this.house_type) 
		.append(this.number) 
		.append(this.area) 
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
		.append("house_type", this.house_type) 
		.append("number", this.number) 
		.append("area", this.area) 
		.append("attachment", this.attachment) 
		.toString();
	}

}