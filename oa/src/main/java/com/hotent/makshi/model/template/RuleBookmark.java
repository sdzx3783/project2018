package com.hotent.makshi.model.template;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:rule_bookmark Model对象
 */
public class RuleBookmark extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *书签名
	 */
	protected String  mark_name;
	/**
	 *书签值
	 */
	protected String  mark_value;
	
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
	
	public void setMark_name(String mark_name) 
	{
		this.mark_name = mark_name;
	}
	/**
	 * 返回 书签名
	 * @return
	 */
	public String getMark_name() 
	{
		return this.mark_name;
	}
	public void setMark_value(String mark_value) 
	{
		this.mark_value = mark_value;
	}
	/**
	 * 返回 书签值
	 * @return
	 */
	public String getMark_value() 
	{
		return this.mark_value;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RuleBookmark)) 
		{
			return false;
		}
		RuleBookmark rhs = (RuleBookmark) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.mark_name, rhs.mark_name)
		.append(this.mark_value, rhs.mark_value)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.mark_name) 
		.append(this.mark_value) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("mark_name", this.mark_name) 
		.append("mark_value", this.mark_value) 
		.toString();
	}

}