package com.hotent.makshi.model.template;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.template.RuleBookmark;
/**
 * 对象功能:规则管理 Model对象
 */
public class RuleManager extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *规则
	 */
	protected String  name;
	/**
	 *创建人
	 */
	protected String  creator;
	/**
	 *创建人id
	 */
	protected Long  creatorID;
	/**
	 *创建时间
	 */
	protected java.util.Date  ctime;
	
	/**
	 *规则管理列表
	 */
	protected List<RuleBookmark> ruleBookmarkList=new ArrayList<RuleBookmark>();
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
	 * 返回 规则
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setCreator(String creator) 
	{
		this.creator = creator;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreator() 
	{
		return this.creator;
	}
	public void setCreatorID(Long creatorID) 
	{
		this.creatorID = creatorID;
	}
	/**
	 * 返回 创建人id
	 * @return
	 */
	public Long getCreatorID() 
	{
		return this.creatorID;
	}
	public void setCtime(java.util.Date ctime) 
	{
		this.ctime = ctime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCtime() 
	{
		return this.ctime;
	}
	public void setRuleBookmarkList(List<RuleBookmark> ruleBookmarkList) 
	{
		this.ruleBookmarkList = ruleBookmarkList;
	}
	/**
	 * 返回 规则管理列表
	 * @return
	 */
	public List<RuleBookmark> getRuleBookmarkList() 
	{
		return this.ruleBookmarkList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RuleManager)) 
		{
			return false;
		}
		RuleManager rhs = (RuleManager) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.creator, rhs.creator)
		.append(this.creatorID, rhs.creatorID)
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
		.append(this.creator) 
		.append(this.creatorID) 
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
		.append("creator", this.creator) 
		.append("creatorID", this.creatorID) 
		.append("ctime", this.ctime) 
		.toString();
	}

}