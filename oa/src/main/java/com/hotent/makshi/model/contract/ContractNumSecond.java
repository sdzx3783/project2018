package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:二级监理合同 Model对象
 */
public class ContractNumSecond extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *合同子类型
	 */
	protected String  type;
	/**
	 *合同子编号
	 */
	protected String  num;
	
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
	
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 合同子类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setNum(String num) 
	{
		this.num = num;
	}
	/**
	 * 返回 合同子编号
	 * @return
	 */
	public String getNum() 
	{
		return this.num;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractNumSecond)) 
		{
			return false;
		}
		ContractNumSecond rhs = (ContractNumSecond) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.type, rhs.type)
		.append(this.num, rhs.num)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.type) 
		.append(this.num) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("type", this.type) 
		.append("num", this.num) 
		.toString();
	}

}