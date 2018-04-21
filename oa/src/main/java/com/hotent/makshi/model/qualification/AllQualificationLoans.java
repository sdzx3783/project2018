package com.hotent.makshi.model.qualification;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.qualification.Jyzz;
/**
 * 对象功能:公司各类资质原件借用流程 Model对象
 */
public class AllQualificationLoans extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *申请部门ID
	 */
	protected String  orgID;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请部门
	 */
	protected String  org;
	/**
	 *申请时间
	 */
	protected java.util.Date  appDate;
	/**
	 *使用时间
	 */
	protected java.util.Date  usedate;
	/**
	 *预计归还时间
	 */
	protected java.util.Date  exbacktime;
	/**
	 *事由
	 */
	protected String  tableBpmRemark;
	/**
	 *摘要
	 */
	protected String  abstracts;
	/**
	 *附件
	 */
	protected String  file;
	
	/**
	 *公司各类资质原件借用流程列表
	 */
	protected List<Jyzz> jyzzList=new ArrayList<Jyzz>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setOrgID(String orgID) 
	{
		this.orgID = orgID;
	}
	/**
	 * 返回 申请部门ID
	 * @return
	 */
	public String getOrgID() 
	{
		return this.orgID;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setOrg(String org) 
	{
		this.org = org;
	}
	/**
	 * 返回 申请部门
	 * @return
	 */
	public String getOrg() 
	{
		return this.org;
	}
	public void setAppDate(java.util.Date appDate) 
	{
		this.appDate = appDate;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getAppDate() 
	{
		return this.appDate;
	}
	public void setUsedate(java.util.Date usedate) 
	{
		this.usedate = usedate;
	}
	/**
	 * 返回 使用时间
	 * @return
	 */
	public java.util.Date getUsedate() 
	{
		return this.usedate;
	}
	public void setExbacktime(java.util.Date exbacktime) 
	{
		this.exbacktime = exbacktime;
	}
	/**
	 * 返回 预计归还时间
	 * @return
	 */
	public java.util.Date getExbacktime() 
	{
		return this.exbacktime;
	}
	public void setTableBpmRemark(String tableBpmRemark) 
	{
		this.tableBpmRemark = tableBpmRemark;
	}
	/**
	 * 返回 事由
	 * @return
	 */
	public String getTableBpmRemark() 
	{
		return this.tableBpmRemark;
	}
	public void setAbstracts(String abstracts) 
	{
		this.abstracts = abstracts;
	}
	/**
	 * 返回 摘要
	 * @return
	 */
	public String getAbstracts() 
	{
		return this.abstracts;
	}
	public void setFile(String file) 
	{
		this.file = file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFile() 
	{
		return this.file;
	}
	public void setJyzzList(List<Jyzz> jyzzList) 
	{
		this.jyzzList = jyzzList;
	}
	/**
	 * 返回 公司各类资质原件借用流程列表
	 * @return
	 */
	public List<Jyzz> getJyzzList() 
	{
		return this.jyzzList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AllQualificationLoans)) 
		{
			return false;
		}
		AllQualificationLoans rhs = (AllQualificationLoans) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.orgID, rhs.orgID)
		.append(this.applicant, rhs.applicant)
		.append(this.org, rhs.org)
		.append(this.appDate, rhs.appDate)
		.append(this.usedate, rhs.usedate)
		.append(this.exbacktime, rhs.exbacktime)
		.append(this.tableBpmRemark, rhs.tableBpmRemark)
		.append(this.abstracts, rhs.abstracts)
		.append(this.file, rhs.file)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.applicantID) 
		.append(this.orgID) 
		.append(this.applicant) 
		.append(this.org) 
		.append(this.appDate) 
		.append(this.usedate) 
		.append(this.exbacktime) 
		.append(this.tableBpmRemark) 
		.append(this.abstracts) 
		.append(this.file) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("applicantID", this.applicantID) 
		.append("orgID", this.orgID) 
		.append("applicant", this.applicant) 
		.append("org", this.org) 
		.append("appDate", this.appDate) 
		.append("usedate", this.usedate) 
		.append("exbacktime", this.exbacktime) 
		.append("tableBpmRemark", this.tableBpmRemark) 
		.append("abstracts", this.abstracts) 
		.append("file", this.file) 
		.toString();
	}

}