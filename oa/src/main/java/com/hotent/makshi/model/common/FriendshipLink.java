package com.hotent.makshi.model.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:友情链接 Model对象
 */
public class FriendshipLink extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3226776015551219462L;
	//主键
	protected Long id;
	/**
	 *链接名称
	 */
	protected String  name;
	/**
	 *链接优先级
	 */
	protected Long  priority;
	/**
	 *链接地址
	 */
	protected String  url;
	/**
	 *创建时间´
	 */
	protected java.util.Date  ctime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 链接名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setPriority(Long priority) 
	{
		this.priority = priority;
	}
	/**
	 * 返回 链接优先级
	 * @return
	 */
	public Long getPriority() 
	{
		return this.priority;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	/**
	 * 返回 链接地址
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}
	public void setCtime(java.util.Date ctime) 
	{
		this.ctime = ctime;
	}
	/**
	 * 返回 创建时间´
	 * @return
	 */
	public java.util.Date getCtime() 
	{
		return this.ctime;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof FriendshipLink)) 
		{
			return false;
		}
		FriendshipLink rhs = (FriendshipLink) object;
		return new EqualsBuilder()
		.append(this.id,rhs.id)
		.append(this.name, rhs.name)
		.append(this.priority, rhs.priority)
		.append(this.url, rhs.url)
		.append(this.ctime, rhs.ctime)
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
		.append(this.priority) 
		.append(this.url) 
		.append(this.ctime) 
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
		.append("priority", this.priority) 
		.append("url", this.url) 
		.append("ctime", this.ctime) 
		.toString();
	}

}