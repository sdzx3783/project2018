package com.hotent.makshi.model.inhouseregistration;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.inuserinfo.UserInfo;
/**
 * 对象功能:租房入住人员登记表 Model对象
 */
public class InHouseRegistration extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *租房编号
	 */
	protected String  house_id;
	/**
	 *房屋地址
	 */
	protected String  address;
	
	/**
	 *租房入住人员登记表列表
	 */
	protected List<UserInfo> userInfoList=new ArrayList<UserInfo>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setHouse_id(String house_id) 
	{
		this.house_id = house_id;
	}
	/**
	 * 返回 租房编号
	 * @return
	 */
	public String getHouse_id() 
	{
		return this.house_id;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 房屋地址
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setUserInfoList(List<UserInfo> userInfoList) 
	{
		this.userInfoList = userInfoList;
	}
	/**
	 * 返回 租房入住人员登记表列表
	 * @return
	 */
	public List<UserInfo> getUserInfoList() 
	{
		return this.userInfoList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof InHouseRegistration)) 
		{
			return false;
		}
		InHouseRegistration rhs = (InHouseRegistration) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.house_id, rhs.house_id)
		.append(this.address, rhs.address)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.house_id) 
		.append(this.address) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("house_id", this.house_id) 
		.append("address", this.address) 
		.toString();
	}

}