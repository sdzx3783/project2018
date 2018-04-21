package com.hotent.makshi.model.bidding;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:补充通知/标底公示 Model对象
 */
public class BiddingPublicity extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *项目初审人员ID
	 */
	protected String  publicity_first_check_personID;
	/**
	 *文件名称
	 */
	protected String  publicity_name;
	/**
	 *编制依据
	 */
	protected String  publicity_evidence;
	/**
	 *文件内容
	 */
	protected String  publicity_content;
	/**
	 *合同编号
	 */
	protected String  publicity_contract_num;
	/**
	 *合同名称
	 */
	protected String  publicity_contract_name;
	/**
	 *申请人
	 */
	protected String  publicity_applicant;
	/**
	 *申请日期
	 */
	protected java.util.Date  publicity_appli_date;
	/**
	 *项目初审人员
	 */
	protected String  publicity_first_check_person;
	/**
	 *附件
	 */
	protected String  publicity_attachment;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPublicity_first_check_personID(String publicity_first_check_personID) 
	{
		this.publicity_first_check_personID = publicity_first_check_personID;
	}
	/**
	 * 返回 项目初审人员ID
	 * @return
	 */
	public String getPublicity_first_check_personID() 
	{
		return this.publicity_first_check_personID;
	}
	public void setPublicity_name(String publicity_name) 
	{
		this.publicity_name = publicity_name;
	}
	/**
	 * 返回 文件名称
	 * @return
	 */
	public String getPublicity_name() 
	{
		return this.publicity_name;
	}
	public void setPublicity_evidence(String publicity_evidence) 
	{
		this.publicity_evidence = publicity_evidence;
	}
	/**
	 * 返回 编制依据
	 * @return
	 */
	public String getPublicity_evidence() 
	{
		return this.publicity_evidence;
	}
	public void setPublicity_content(String publicity_content) 
	{
		this.publicity_content = publicity_content;
	}
	/**
	 * 返回 文件内容
	 * @return
	 */
	public String getPublicity_content() 
	{
		return this.publicity_content;
	}
	public void setPublicity_contract_num(String publicity_contract_num) 
	{
		this.publicity_contract_num = publicity_contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getPublicity_contract_num() 
	{
		return this.publicity_contract_num;
	}
	public void setPublicity_contract_name(String publicity_contract_name) 
	{
		this.publicity_contract_name = publicity_contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getPublicity_contract_name() 
	{
		return this.publicity_contract_name;
	}
	public void setPublicity_applicant(String publicity_applicant) 
	{
		this.publicity_applicant = publicity_applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getPublicity_applicant() 
	{
		return this.publicity_applicant;
	}
	public void setPublicity_appli_date(java.util.Date publicity_appli_date) 
	{
		this.publicity_appli_date = publicity_appli_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getPublicity_appli_date() 
	{
		return this.publicity_appli_date;
	}
	public void setPublicity_first_check_person(String publicity_first_check_person) 
	{
		this.publicity_first_check_person = publicity_first_check_person;
	}
	/**
	 * 返回 项目初审人员
	 * @return
	 */
	public String getPublicity_first_check_person() 
	{
		return this.publicity_first_check_person;
	}
	public void setPublicity_attachment(String publicity_attachment) 
	{
		this.publicity_attachment = publicity_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getPublicity_attachment() 
	{
		return this.publicity_attachment;
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
		if (!(object instanceof BiddingPublicity)) 
		{
			return false;
		}
		BiddingPublicity rhs = (BiddingPublicity) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.publicity_first_check_personID, rhs.publicity_first_check_personID)
		.append(this.publicity_name, rhs.publicity_name)
		.append(this.publicity_evidence, rhs.publicity_evidence)
		.append(this.publicity_content, rhs.publicity_content)
		.append(this.publicity_contract_num, rhs.publicity_contract_num)
		.append(this.publicity_contract_name, rhs.publicity_contract_name)
		.append(this.publicity_applicant, rhs.publicity_applicant)
		.append(this.publicity_appli_date, rhs.publicity_appli_date)
		.append(this.publicity_first_check_person, rhs.publicity_first_check_person)
		.append(this.publicity_attachment, rhs.publicity_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.publicity_first_check_personID) 
		.append(this.publicity_name) 
		.append(this.publicity_evidence) 
		.append(this.publicity_content) 
		.append(this.publicity_contract_num) 
		.append(this.publicity_contract_name) 
		.append(this.publicity_applicant) 
		.append(this.publicity_appli_date) 
		.append(this.publicity_first_check_person) 
		.append(this.publicity_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("publicity_first_check_personID", this.publicity_first_check_personID) 
		.append("publicity_name", this.publicity_name) 
		.append("publicity_evidence", this.publicity_evidence) 
		.append("publicity_content", this.publicity_content) 
		.append("publicity_contract_num", this.publicity_contract_num) 
		.append("publicity_contract_name", this.publicity_contract_name) 
		.append("publicity_applicant", this.publicity_applicant) 
		.append("publicity_appli_date", this.publicity_appli_date) 
		.append("publicity_first_check_person", this.publicity_first_check_person) 
		.append("publicity_attachment", this.publicity_attachment) 
		.toString();
	}

}