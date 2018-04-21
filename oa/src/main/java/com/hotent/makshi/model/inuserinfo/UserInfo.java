package com.hotent.makshi.model.inuserinfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:员工入住信息 Model对象
 */
public class UserInfo extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *入住人姓名
	 */
	protected String  user_name;
	
	/**
	 *是否负责人
	 */
	protected String  isresponsible;
	/**
	 *入住家属
	 */
	protected String  family;
	/**
	 *入住时间
	 */
	protected java.util.Date  in_date;
	/**
	 *离开时间
	 */
	protected java.util.Date  left_date;
	/**
	 * 房间编号
	 */
	protected String house_id;
	
	public String gethouse_id() {
		return house_id;
	}
	public void sethouse_id(String house_id) {
		this.house_id = house_id;
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
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 入住人姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setIsresponsible(String isresponsible) 
	{
		this.isresponsible = isresponsible;
	}
	/**
	 * 返回 是否负责人
	 * @return
	 */
	public String getIsresponsible() 
	{
		return this.isresponsible;
	}
	public void setFamily(String family) 
	{
		this.family = family;
	}
	/**
	 * 返回 是否
	 * @return
	 */
	public String getFamily() 
	{
		return this.family;
	}
	public void setIn_date(java.util.Date in_date) 
	{
		this.in_date = in_date;
	}
	/**
	 * 返回 入住时间
	 * @return
	 */
	public java.util.Date getIn_date() 
	{
		return this.in_date;
	}
	public void setLeft_date(java.util.Date left_date) 
	{
		this.left_date = left_date;
	}
	/**
	 * 返回 离开时间
	 * @return
	 */
	public java.util.Date getLeft_date() 
	{
		return this.left_date;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof UserInfo)) 
		{
			return false;
		}
		UserInfo rhs = (UserInfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_name, rhs.user_name)
		.append(this.isresponsible, rhs.isresponsible)
		.append(this.family, rhs.family)
		.append(this.in_date, rhs.in_date)
		.append(this.left_date, rhs.left_date)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_name) 
		.append(this.isresponsible) 
		.append(this.family) 
		.append(this.in_date) 
		.append(this.left_date) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_name", this.user_name) 
		.append("isresponsible", this.isresponsible) 
		.append("family", this.family) 
		.append("in_date", this.in_date) 
		.append("left_date", this.left_date) 
		.toString();
	}

}