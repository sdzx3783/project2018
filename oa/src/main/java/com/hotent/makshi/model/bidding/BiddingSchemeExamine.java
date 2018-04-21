package com.hotent.makshi.model.bidding;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:招标方案审批 Model对象
 */
public class BiddingSchemeExamine extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *项目初审人员ID
	 */
	protected String  scheme_first_check_personID;
	/**
	 *方案名称
	 */
	protected String  scheme_name;
	/**
	 *编制依据
	 */
	protected String  scheme_evidence;
	/**
	 *方案内容
	 */
	protected String  scheme_content;
	/**
	 *合同编号
	 */
	protected String  scheme_contract_num;
	/**
	 *合同名称
	 */
	protected String  scheme_contract_name;
	/**
	 *申请人
	 */
	protected String  scheme_applicant;
	/**
	 *申请日期
	 */
	protected java.util.Date  scheme_appli_date;
	/**
	 *项目初审人员
	 */
	protected String  scheme_first_check_person;
	/**
	 *附件
	 */
	protected String  scheme_attachment;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setScheme_first_check_personID(String scheme_first_check_personID) 
	{
		this.scheme_first_check_personID = scheme_first_check_personID;
	}
	/**
	 * 返回 项目初审人员ID
	 * @return
	 */
	public String getScheme_first_check_personID() 
	{
		return this.scheme_first_check_personID;
	}
	public void setScheme_name(String scheme_name) 
	{
		this.scheme_name = scheme_name;
	}
	/**
	 * 返回 方案名称
	 * @return
	 */
	public String getScheme_name() 
	{
		return this.scheme_name;
	}
	public void setScheme_evidence(String scheme_evidence) 
	{
		this.scheme_evidence = scheme_evidence;
	}
	/**
	 * 返回 编制依据
	 * @return
	 */
	public String getScheme_evidence() 
	{
		return this.scheme_evidence;
	}
	public void setScheme_content(String scheme_content) 
	{
		this.scheme_content = scheme_content;
	}
	/**
	 * 返回 方案内容
	 * @return
	 */
	public String getScheme_content() 
	{
		return this.scheme_content;
	}
	public void setScheme_contract_num(String scheme_contract_num) 
	{
		this.scheme_contract_num = scheme_contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getScheme_contract_num() 
	{
		return this.scheme_contract_num;
	}
	public void setScheme_contract_name(String scheme_contract_name) 
	{
		this.scheme_contract_name = scheme_contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getScheme_contract_name() 
	{
		return this.scheme_contract_name;
	}
	public void setScheme_applicant(String scheme_applicant) 
	{
		this.scheme_applicant = scheme_applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getScheme_applicant() 
	{
		return this.scheme_applicant;
	}
	public void setScheme_appli_date(java.util.Date scheme_appli_date) 
	{
		this.scheme_appli_date = scheme_appli_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getScheme_appli_date() 
	{
		return this.scheme_appli_date;
	}
	public void setScheme_first_check_person(String scheme_first_check_person) 
	{
		this.scheme_first_check_person = scheme_first_check_person;
	}
	/**
	 * 返回 项目初审人员
	 * @return
	 */
	public String getScheme_first_check_person() 
	{
		return this.scheme_first_check_person;
	}
	public void setScheme_attachment(String scheme_attachment) 
	{
		this.scheme_attachment = scheme_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getScheme_attachment() 
	{
		return this.scheme_attachment;
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
		if (!(object instanceof BiddingSchemeExamine)) 
		{
			return false;
		}
		BiddingSchemeExamine rhs = (BiddingSchemeExamine) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.scheme_first_check_personID, rhs.scheme_first_check_personID)
		.append(this.scheme_name, rhs.scheme_name)
		.append(this.scheme_evidence, rhs.scheme_evidence)
		.append(this.scheme_content, rhs.scheme_content)
		.append(this.scheme_contract_num, rhs.scheme_contract_num)
		.append(this.scheme_contract_name, rhs.scheme_contract_name)
		.append(this.scheme_applicant, rhs.scheme_applicant)
		.append(this.scheme_appli_date, rhs.scheme_appli_date)
		.append(this.scheme_first_check_person, rhs.scheme_first_check_person)
		.append(this.scheme_attachment, rhs.scheme_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.scheme_first_check_personID) 
		.append(this.scheme_name) 
		.append(this.scheme_evidence) 
		.append(this.scheme_content) 
		.append(this.scheme_contract_num) 
		.append(this.scheme_contract_name) 
		.append(this.scheme_applicant) 
		.append(this.scheme_appli_date) 
		.append(this.scheme_first_check_person) 
		.append(this.scheme_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("scheme_first_check_personID", this.scheme_first_check_personID) 
		.append("scheme_name", this.scheme_name) 
		.append("scheme_evidence", this.scheme_evidence) 
		.append("scheme_content", this.scheme_content) 
		.append("scheme_contract_num", this.scheme_contract_num) 
		.append("scheme_contract_name", this.scheme_contract_name) 
		.append("scheme_applicant", this.scheme_applicant) 
		.append("scheme_appli_date", this.scheme_appli_date) 
		.append("scheme_first_check_person", this.scheme_first_check_person) 
		.append("scheme_attachment", this.scheme_attachment) 
		.toString();
	}

}