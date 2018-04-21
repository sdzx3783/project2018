package com.hotent.makshi.model.operation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:月报管理 Model对象
 */
public class MonthlyManage extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *提交人ID
	 */
	protected String  editerID;
	/**
	 *月报名称
	 */
	protected String  name;
	/**
	 *项目名称
	 */
	protected String  item;
	/**
	 *片区
	 */
	protected String  grop;
	/**
	 *开始日期
	 */
	protected String  month;
	/**
	 *提交人
	 */
	protected String  editer;
	/**
	 *提交日期
	 */
	protected java.util.Date  edit_date;
	/**
	 *备注
	 */
	protected String  remarks;
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
	
	public void setEditerID(String editerID) 
	{
		this.editerID = editerID;
	}
	/**
	 * 返回 提交人ID
	 * @return
	 */
	public String getEditerID() 
	{
		return this.editerID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 月报名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setItem(String item) 
	{
		this.item = item;
	}
	/**
	 * 返回 项目名称
	 * @return
	 */
	public String getItem() 
	{
		return this.item;
	}
	public void setGrop(String grop) 
	{
		this.grop = grop;
	}
	/**
	 * 返回 片区
	 * @return
	 */
	public String getGrop() 
	{
		return this.grop;
	}
	public void setMonth(String month) 
	{
		this.month = month;
	}
	/**
	 * 返回 开始日期
	 * @return
	 */
	public String getMonth() 
	{
		return this.month;
	}
	public void setEditer(String editer) 
	{
		this.editer = editer;
	}
	/**
	 * 返回 提交人
	 * @return
	 */
	public String getEditer() 
	{
		return this.editer;
	}
	public void setEdit_date(java.util.Date edit_date) 
	{
		this.edit_date = edit_date;
	}
	/**
	 * 返回 提交日期
	 * @return
	 */
	public java.util.Date getEdit_date() 
	{
		return this.edit_date;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemarks() 
	{
		return this.remarks;
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
		if (!(object instanceof MonthlyManage)) 
		{
			return false;
		}
		MonthlyManage rhs = (MonthlyManage) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.editerID, rhs.editerID)
		.append(this.name, rhs.name)
		.append(this.item, rhs.item)
		.append(this.grop, rhs.grop)
		.append(this.month, rhs.month)
		.append(this.editer, rhs.editer)
		.append(this.edit_date, rhs.edit_date)
		.append(this.remarks, rhs.remarks)
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
		.append(this.editerID) 
		.append(this.name) 
		.append(this.item) 
		.append(this.grop) 
		.append(this.month) 
		.append(this.editer) 
		.append(this.edit_date) 
		.append(this.remarks) 
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
		.append("editerID", this.editerID) 
		.append("name", this.name) 
		.append("item", this.item) 
		.append("grop", this.grop) 
		.append("month", this.month) 
		.append("editer", this.editer) 
		.append("edit_date", this.edit_date) 
		.append("remarks", this.remarks) 
		.append("attachment", this.attachment) 
		.toString();
	}

}