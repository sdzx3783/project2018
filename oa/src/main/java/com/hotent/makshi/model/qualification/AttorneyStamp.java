package com.hotent.makshi.model.qualification;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:法定委托书盖章申请 Model对象
 */
public class AttorneyStamp extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792563655066759130L;
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
	protected java.util.Date  submittime;
	/**
	 *证书字号
	 */
	protected String  cerno;
	/**
	 *姓名
	 */
	protected String  name;
	
	/**
	 *有效期限
	 */
	protected java.util.Date  limittime;
	/**
	 *签发日期
	 */
	protected java.util.Date  signtime;
	/**
	 *代表人姓名
	 */
	protected String  representor;
	/**
	 *年龄
	 */
	protected Long  age;
	/**
	 *工作证号码
	 */
	protected String  workno;
	/**
	 *机构代码
	 */
	protected String  institutionno;
	/**
	 *机构类别
	 */
	protected String  institutiontype;
	/**
	 *主营(产)
	 */
	protected String  major_pro;
	/**
	 *申请时间
	 */
	protected String  sideline_pro;
	/**
	 *进口物品经营许可证号码
	 */
	protected String  licenseno;
	/**
	 *主营
	 */
	protected String  major;
	/**
	 *兼营
	 */
	protected String  sideline;
	/**
	 * 权限
	 */
	private String privilege;
	/**
	 * 职务 
	 */
	private String job;
	/**
	 * 营业执照号码
	 */
	private String businessnum;
	/**
	 * 经济性质
	 */
	private String economicnature;
	
	
	private String runid;
	private String runStatus;
	
	public String getRunid() {
		return runid;
	}
	public void setRunid(String runid) {
		this.runid = runid;
	}
	public String getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
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
	public void setSubmittime(java.util.Date submittime) 
	{
		this.submittime = submittime;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getSubmittime() 
	{
		return this.submittime;
	}
	public void setCerno(String cerno) 
	{
		this.cerno = cerno;
	}
	/**
	 * 返回 证书字号
	 * @return
	 */
	public String getCerno() 
	{
		return this.cerno;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	
	public void setLimittime(java.util.Date limittime) 
	{
		this.limittime = limittime;
	}
	/**
	 * 返回 有效期限
	 * @return
	 */
	public java.util.Date getLimittime() 
	{
		return this.limittime;
	}
	public void setSigntime(java.util.Date signtime) 
	{
		this.signtime = signtime;
	}
	/**
	 * 返回 签发日期
	 * @return
	 */
	public java.util.Date getSigntime() 
	{
		return this.signtime;
	}
	public void setRepresentor(String representor) 
	{
		this.representor = representor;
	}
	/**
	 * 返回 代表人姓名
	 * @return
	 */
	public String getRepresentor() 
	{
		return this.representor;
	}
	public void setAge(Long age) 
	{
		this.age = age;
	}
	/**
	 * 返回 年龄
	 * @return
	 */
	public Long getAge() 
	{
		return this.age;
	}
	public void setWorkno(String workno) 
	{
		this.workno = workno;
	}
	/**
	 * 返回 工作证号码
	 * @return
	 */
	public String getWorkno() 
	{
		return this.workno;
	}
	public void setInstitutionno(String institutionno) 
	{
		this.institutionno = institutionno;
	}
	/**
	 * 返回 机构代码
	 * @return
	 */
	public String getInstitutionno() 
	{
		return this.institutionno;
	}
	public void setInstitutiontype(String institutiontype) 
	{
		this.institutiontype = institutiontype;
	}
	/**
	 * 返回 机构类别
	 * @return
	 */
	public String getInstitutiontype() 
	{
		return this.institutiontype;
	}
	public void setMajor_pro(String major_pro) 
	{
		this.major_pro = major_pro;
	}
	/**
	 * 返回 主营(产)
	 * @return
	 */
	public String getMajor_pro() 
	{
		return this.major_pro;
	}
	public void setSideline_pro(String sideline_pro) 
	{
		this.sideline_pro = sideline_pro;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public String getSideline_pro() 
	{
		return this.sideline_pro;
	}
	public void setLicenseno(String licenseno) 
	{
		this.licenseno = licenseno;
	}
	/**
	 * 返回 进口物品经营许可证号码
	 * @return
	 */
	public String getLicenseno() 
	{
		return this.licenseno;
	}
	public void setMajor(String major) 
	{
		this.major = major;
	}
	/**
	 * 返回 主营
	 * @return
	 */
	public String getMajor() 
	{
		return this.major;
	}
	public void setSideline(String sideline) 
	{
		this.sideline = sideline;
	}
	/**
	 * 返回 兼营
	 * @return
	 */
	public String getSideline() 
	{
		return this.sideline;
	}
	
	
   	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getBusinessnum() {
		return businessnum;
	}
	public void setBusinessnum(String businessnum) {
		this.businessnum = businessnum;
	}
	public String getEconomicnature() {
		return economicnature;
	}
	public void setEconomicnature(String economicnature) {
		this.economicnature = economicnature;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AttorneyStamp)) 
		{
			return false;
		}
		AttorneyStamp rhs = (AttorneyStamp) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.orgID, rhs.orgID)
		.append(this.applicant, rhs.applicant)
		.append(this.org, rhs.org)
		.append(this.submittime, rhs.submittime)
		.append(this.cerno, rhs.cerno)
		.append(this.name, rhs.name)
		.append(this.limittime, rhs.limittime)
		.append(this.signtime, rhs.signtime)
		.append(this.representor, rhs.representor)
		.append(this.age, rhs.age)
		.append(this.workno, rhs.workno)
		.append(this.institutionno, rhs.institutionno)
		.append(this.institutiontype, rhs.institutiontype)
		.append(this.major_pro, rhs.major_pro)
		.append(this.sideline_pro, rhs.sideline_pro)
		.append(this.licenseno, rhs.licenseno)
		.append(this.major, rhs.major)
		.append(this.sideline, rhs.sideline)
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
		.append(this.submittime) 
		.append(this.cerno) 
		.append(this.name) 
		.append(this.limittime) 
		.append(this.signtime) 
		.append(this.representor) 
		.append(this.age) 
		.append(this.workno) 
		.append(this.institutionno) 
		.append(this.institutiontype) 
		.append(this.major_pro) 
		.append(this.sideline_pro) 
		.append(this.licenseno) 
		.append(this.major) 
		.append(this.sideline) 
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
		.append("submittime", this.submittime) 
		.append("cerno", this.cerno) 
		.append("name", this.name) 
		.append("limittime", this.limittime) 
		.append("signtime", this.signtime) 
		.append("representor", this.representor) 
		.append("age", this.age) 
		.append("workno", this.workno) 
		.append("institutionno", this.institutionno) 
		.append("institutiontype", this.institutiontype) 
		.append("major_pro", this.major_pro) 
		.append("sideline_pro", this.sideline_pro) 
		.append("licenseno", this.licenseno) 
		.append("major", this.major) 
		.append("sideline", this.sideline) 
		.toString();
	}

}