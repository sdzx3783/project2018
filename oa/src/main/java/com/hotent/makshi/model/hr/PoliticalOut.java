package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:党员档案转出 Model对象
 */
public class PoliticalOut extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人
	 */
	protected String  appi_person;
	/**
	 *申请日期
	 */
	protected java.util.Date  appi_date;
	/**
	 *姓名
	 */
	protected String  user_name;
	/**
	 *性别
	 */
	protected String  sex;
	/**
	 *所在支部
	 */
	protected String  belong_branch;
	/**
	 *申请转至支部
	 */
	protected String  to_branch;
	/**
	 *党费已交至
	 */
	protected String  pay_month;
	/**
	 *党支部意见
	 */
	protected String  branch_point;
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
	
	public void setAppi_person(String appi_person) 
	{
		this.appi_person = appi_person;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getAppi_person() 
	{
		return this.appi_person;
	}
	public void setAppi_date(java.util.Date appi_date) 
	{
		this.appi_date = appi_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getAppi_date() 
	{
		return this.appi_date;
	}
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setSex(String sex) 
	{
		this.sex = sex;
	}
	/**
	 * 返回 性别
	 * @return
	 */
	public String getSex() 
	{
		return this.sex;
	}
	public void setBelong_branch(String belong_branch) 
	{
		this.belong_branch = belong_branch;
	}
	/**
	 * 返回 所在支部
	 * @return
	 */
	public String getBelong_branch() 
	{
		return this.belong_branch;
	}
	public void setTo_branch(String to_branch) 
	{
		this.to_branch = to_branch;
	}
	/**
	 * 返回 申请转至支部
	 * @return
	 */
	public String getTo_branch() 
	{
		return this.to_branch;
	}
	public void setPay_month(String pay_month) 
	{
		this.pay_month = pay_month;
	}
	/**
	 * 返回 党费已交至
	 * @return
	 */
	public String getPay_month() 
	{
		return this.pay_month;
	}
	public void setBranch_point(String branch_point) 
	{
		this.branch_point = branch_point;
	}
	/**
	 * 返回 党支部意见
	 * @return
	 */
	public String getBranch_point() 
	{
		return this.branch_point;
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
		if (!(object instanceof PoliticalOut)) 
		{
			return false;
		}
		PoliticalOut rhs = (PoliticalOut) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.appi_person, rhs.appi_person)
		.append(this.appi_date, rhs.appi_date)
		.append(this.user_name, rhs.user_name)
		.append(this.sex, rhs.sex)
		.append(this.belong_branch, rhs.belong_branch)
		.append(this.to_branch, rhs.to_branch)
		.append(this.pay_month, rhs.pay_month)
		.append(this.branch_point, rhs.branch_point)
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
		.append(this.appi_person) 
		.append(this.appi_date) 
		.append(this.user_name) 
		.append(this.sex) 
		.append(this.belong_branch) 
		.append(this.to_branch) 
		.append(this.pay_month) 
		.append(this.branch_point) 
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
		.append("appi_person", this.appi_person) 
		.append("appi_date", this.appi_date) 
		.append("user_name", this.user_name) 
		.append("sex", this.sex) 
		.append("belong_branch", this.belong_branch) 
		.append("to_branch", this.to_branch) 
		.append("pay_month", this.pay_month) 
		.append("branch_point", this.branch_point) 
		.append("attachment", this.attachment) 
		.toString();
	}

}