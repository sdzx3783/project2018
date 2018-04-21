package com.hotent.makshi.model.bidding;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:其他事项审批 Model对象
 */
public class BiddingOthers extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *事项名称
	 */
	protected String  others_name;
	/**
	 *编制依据
	 */
	protected String  others_evidence;
	/**
	 *申请内容
	 */
	protected String  others_content;
	/**
	 *申请人
	 */
	protected String  others_applicant;
	/**
	 *申请时间
	 */
	protected java.util.Date  others_appli_date;
	/**
	 *附件
	 */
	protected String  others_attachment;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setOthers_name(String others_name) 
	{
		this.others_name = others_name;
	}
	/**
	 * 返回 事项名称
	 * @return
	 */
	public String getOthers_name() 
	{
		return this.others_name;
	}
	public void setOthers_evidence(String others_evidence) 
	{
		this.others_evidence = others_evidence;
	}
	/**
	 * 返回 编制依据
	 * @return
	 */
	public String getOthers_evidence() 
	{
		return this.others_evidence;
	}
	public void setOthers_content(String others_content) 
	{
		this.others_content = others_content;
	}
	/**
	 * 返回 申请内容
	 * @return
	 */
	public String getOthers_content() 
	{
		return this.others_content;
	}
	public void setOthers_applicant(String others_applicant) 
	{
		this.others_applicant = others_applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getOthers_applicant() 
	{
		return this.others_applicant;
	}
	public void setOthers_appli_date(java.util.Date others_appli_date) 
	{
		this.others_appli_date = others_appli_date;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getOthers_appli_date() 
	{
		return this.others_appli_date;
	}
	public void setOthers_attachment(String others_attachment) 
	{
		this.others_attachment = others_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getOthers_attachment() 
	{
		return this.others_attachment;
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
		if (!(object instanceof BiddingOthers)) 
		{
			return false;
		}
		BiddingOthers rhs = (BiddingOthers) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.others_name, rhs.others_name)
		.append(this.others_evidence, rhs.others_evidence)
		.append(this.others_content, rhs.others_content)
		.append(this.others_applicant, rhs.others_applicant)
		.append(this.others_appli_date, rhs.others_appli_date)
		.append(this.others_attachment, rhs.others_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.others_name) 
		.append(this.others_evidence) 
		.append(this.others_content) 
		.append(this.others_applicant) 
		.append(this.others_appli_date) 
		.append(this.others_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("others_name", this.others_name) 
		.append("others_evidence", this.others_evidence) 
		.append("others_content", this.others_content) 
		.append("others_applicant", this.others_applicant) 
		.append("others_appli_date", this.others_appli_date) 
		.append("others_attachment", this.others_attachment) 
		.toString();
	}

}