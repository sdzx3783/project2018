package com.hotent.makshi.model.bidding;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:招标文件审批 Model对象
 */
public class BiddingDocumentExamine extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *项目初审人员ID
	 */
	protected String  document_first_check_personID;
	/**
	 *文件名称
	 */
	protected String  document_name;
	/**
	 *编制依据
	 */
	protected String  document_evidence;
	/**
	 *文件内容
	 */
	protected String  document_content;
	/**
	 *合同编号
	 */
	protected String  document_contract_num;
	/**
	 *合同名称
	 */
	protected String  document_contract_name;
	/**
	 *申请人
	 */
	protected String  document_applicant;
	/**
	 *申请日期
	 */
	protected java.util.Date  document_appli_date;
	/**
	 *代理费支付方
	 */
	protected String  document_payer;
	/**
	 *项目初审人员
	 */
	protected String  document_first_check_person;
	/**
	 *附件
	 */
	protected String  document_attachment;
	/**
	 *合同金额
	 */
	protected String  documrnt_money;
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDocument_first_check_personID(String document_first_check_personID) 
	{
		this.document_first_check_personID = document_first_check_personID;
	}
	/**
	 * 返回 项目初审人员ID
	 * @return
	 */
	public String getDocument_first_check_personID() 
	{
		return this.document_first_check_personID;
	}
	public void setDocument_name(String document_name) 
	{
		this.document_name = document_name;
	}
	/**
	 * 返回 文件名称
	 * @return
	 */
	public String getDocument_name() 
	{
		return this.document_name;
	}
	public void setDocument_evidence(String document_evidence) 
	{
		this.document_evidence = document_evidence;
	}
	/**
	 * 返回 编制依据
	 * @return
	 */
	public String getDocument_evidence() 
	{
		return this.document_evidence;
	}
	public void setDocument_content(String document_content) 
	{
		this.document_content = document_content;
	}
	/**
	 * 返回 文件内容
	 * @return
	 */
	public String getDocument_content() 
	{
		return this.document_content;
	}
	public void setDocument_contract_num(String document_contract_num) 
	{
		this.document_contract_num = document_contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getDocument_contract_num() 
	{
		return this.document_contract_num;
	}
	public void setDocument_contract_name(String document_contract_name) 
	{
		this.document_contract_name = document_contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getDocument_contract_name() 
	{
		return this.document_contract_name;
	}
	public void setDocument_applicant(String document_applicant) 
	{
		this.document_applicant = document_applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getDocument_applicant() 
	{
		return this.document_applicant;
	}
	public void setDocument_appli_date(java.util.Date document_appli_date) 
	{
		this.document_appli_date = document_appli_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getDocument_appli_date() 
	{
		return this.document_appli_date;
	}
	public void setDocument_payer(String document_payer) 
	{
		this.document_payer = document_payer;
	}
	/**
	 * 返回 代理费支付方
	 * @return
	 */
	public String getDocument_payer() 
	{
		return this.document_payer;
	}
	public void setDocument_first_check_person(String document_first_check_person) 
	{
		this.document_first_check_person = document_first_check_person;
	}
	/**
	 * 返回 项目初审人员
	 * @return
	 */
	public String getDocument_first_check_person() 
	{
		return this.document_first_check_person;
	}
	public void setDocument_attachment(String document_attachment) 
	{
		this.document_attachment = document_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getDocument_attachment() 
	{
		return this.document_attachment;
	}
	public void setDocumrnt_money(String documrnt_money) 
	{
		this.documrnt_money = documrnt_money;
	}
	/**
	 * 返回 合同金额
	 * @return
	 */
	public String getDocumrnt_money() 
	{
		return this.documrnt_money;
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
		if (!(object instanceof BiddingDocumentExamine)) 
		{
			return false;
		}
		BiddingDocumentExamine rhs = (BiddingDocumentExamine) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.document_first_check_personID, rhs.document_first_check_personID)
		.append(this.document_name, rhs.document_name)
		.append(this.document_evidence, rhs.document_evidence)
		.append(this.document_content, rhs.document_content)
		.append(this.document_contract_num, rhs.document_contract_num)
		.append(this.document_contract_name, rhs.document_contract_name)
		.append(this.document_applicant, rhs.document_applicant)
		.append(this.document_appli_date, rhs.document_appli_date)
		.append(this.document_payer, rhs.document_payer)
		.append(this.document_first_check_person, rhs.document_first_check_person)
		.append(this.document_attachment, rhs.document_attachment)
		.append(this.documrnt_money, rhs.documrnt_money)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.document_first_check_personID) 
		.append(this.document_name) 
		.append(this.document_evidence) 
		.append(this.document_content) 
		.append(this.document_contract_num) 
		.append(this.document_contract_name) 
		.append(this.document_applicant) 
		.append(this.document_appli_date) 
		.append(this.document_payer) 
		.append(this.document_first_check_person) 
		.append(this.document_attachment) 
		.append(this.documrnt_money) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("document_first_check_personID", this.document_first_check_personID) 
		.append("document_name", this.document_name) 
		.append("document_evidence", this.document_evidence) 
		.append("document_content", this.document_content) 
		.append("document_contract_num", this.document_contract_num) 
		.append("document_contract_name", this.document_contract_name) 
		.append("document_applicant", this.document_applicant) 
		.append("document_appli_date", this.document_appli_date) 
		.append("document_payer", this.document_payer) 
		.append("document_first_check_person", this.document_first_check_person) 
		.append("document_attachment", this.document_attachment) 
		.append("documrnt_money", this.documrnt_money) 
		.toString();
	}

}