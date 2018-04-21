package com.hotent.makshi.model.bidding;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:投诉处理审批 Model对象
 */
public class BiddingComplain extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *项目初审人员ID
	 */
	protected String  complain_first_check_personID;
	/**
	 *事项名称
	 */
	protected String  complain_name;
	/**
	 *事项内容
	 */
	protected String  complain_content;
	/**
	 *投诉单位
	 */
	protected String  complain_unit;
	/**
	 *提交人
	 */
	protected String  complain_applicant;
	/**
	 *提交时间
	 */
	protected java.util.Date  complain_appli_date;
	/**
	 *备注
	 */
	protected String  complain_remarks;
	/**
	 *项目初审人员
	 */
	protected String  complain_first_check_person;
	/**
	 *附件
	 */
	protected String  complain_attachment;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setComplain_first_check_personID(String complain_first_check_personID) 
	{
		this.complain_first_check_personID = complain_first_check_personID;
	}
	/**
	 * 返回 项目初审人员ID
	 * @return
	 */
	public String getComplain_first_check_personID() 
	{
		return this.complain_first_check_personID;
	}
	public void setComplain_name(String complain_name) 
	{
		this.complain_name = complain_name;
	}
	/**
	 * 返回 事项名称
	 * @return
	 */
	public String getComplain_name() 
	{
		return this.complain_name;
	}
	public void setComplain_content(String complain_content) 
	{
		this.complain_content = complain_content;
	}
	/**
	 * 返回 事项内容
	 * @return
	 */
	public String getComplain_content() 
	{
		return this.complain_content;
	}
	public void setComplain_unit(String complain_unit) 
	{
		this.complain_unit = complain_unit;
	}
	/**
	 * 返回 投诉单位
	 * @return
	 */
	public String getComplain_unit() 
	{
		return this.complain_unit;
	}
	public void setComplain_applicant(String complain_applicant) 
	{
		this.complain_applicant = complain_applicant;
	}
	/**
	 * 返回 提交人
	 * @return
	 */
	public String getComplain_applicant() 
	{
		return this.complain_applicant;
	}
	public void setComplain_appli_date(java.util.Date complain_appli_date) 
	{
		this.complain_appli_date = complain_appli_date;
	}
	/**
	 * 返回 提交时间
	 * @return
	 */
	public java.util.Date getComplain_appli_date() 
	{
		return this.complain_appli_date;
	}
	public void setComplain_remarks(String complain_remarks) 
	{
		this.complain_remarks = complain_remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getComplain_remarks() 
	{
		return this.complain_remarks;
	}
	public void setComplain_first_check_person(String complain_first_check_person) 
	{
		this.complain_first_check_person = complain_first_check_person;
	}
	/**
	 * 返回 项目初审人员
	 * @return
	 */
	public String getComplain_first_check_person() 
	{
		return this.complain_first_check_person;
	}
	public void setComplain_attachment(String complain_attachment) 
	{
		this.complain_attachment = complain_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getComplain_attachment() 
	{
		return this.complain_attachment;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof BiddingComplain)) 
		{
			return false;
		}
		BiddingComplain rhs = (BiddingComplain) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.complain_first_check_personID, rhs.complain_first_check_personID)
		.append(this.complain_name, rhs.complain_name)
		.append(this.complain_content, rhs.complain_content)
		.append(this.complain_unit, rhs.complain_unit)
		.append(this.complain_applicant, rhs.complain_applicant)
		.append(this.complain_appli_date, rhs.complain_appli_date)
		.append(this.complain_remarks, rhs.complain_remarks)
		.append(this.complain_first_check_person, rhs.complain_first_check_person)
		.append(this.complain_attachment, rhs.complain_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.complain_first_check_personID) 
		.append(this.complain_name) 
		.append(this.complain_content) 
		.append(this.complain_unit) 
		.append(this.complain_applicant) 
		.append(this.complain_appli_date) 
		.append(this.complain_remarks) 
		.append(this.complain_first_check_person) 
		.append(this.complain_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("complain_first_check_personID", this.complain_first_check_personID) 
		.append("complain_name", this.complain_name) 
		.append("complain_content", this.complain_content) 
		.append("complain_unit", this.complain_unit) 
		.append("complain_applicant", this.complain_applicant) 
		.append("complain_appli_date", this.complain_appli_date) 
		.append("complain_remarks", this.complain_remarks) 
		.append("complain_first_check_person", this.complain_first_check_person) 
		.append("complain_attachment", this.complain_attachment) 
		.toString();
	}

}