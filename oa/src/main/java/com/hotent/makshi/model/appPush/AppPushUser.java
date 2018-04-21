package com.hotent.makshi.model.appPush;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:app推送与用户关系表 Model对象
 */
public class AppPushUser extends BaseModel
{
	//主键
	protected String devicetoken;
	/**
	 *用户id
	 */
	protected Long  userId;
	/**
	 *appType ios 苹果, android 安卓
	 */
	protected String  appType;
	
	public String getDevicetoken() {
		return devicetoken;
	}
	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}
	
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 用户id
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setAppType(String appType) 
	{
		this.appType = appType;
	}
	/**
	 * 返回 appType
	 * @return
	 */
	public String getAppType() 
	{
		return this.appType;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AppPushUser)) 
		{
			return false;
		}
		AppPushUser rhs = (AppPushUser) object;
		return new EqualsBuilder()
		.append(this.devicetoken,rhs.devicetoken)
		.append(this.userId, rhs.userId)
		.append(this.appType, rhs.appType)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.devicetoken)
		.append(this.userId) 
		.append(this.appType) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("devicetoken",this.devicetoken)
		.append("userId", this.userId) 
		.append("appType", this.appType) 
		.toString();
	}

}