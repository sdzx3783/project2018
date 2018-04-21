package com.hotent.makshi.model.hr;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
import com.hotent.core.util.TimeUtil;
/**
 * 对象功能:员工转正表 Model对象
 */
public class UserFormal extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *岗位ID
	 */
	protected String  posnameID;
	/**
	 *申请人
	 */
	protected String  user_name;
	/**
	 *劳动合同约定转正时间
	 */
	protected String  agreement_date_to_formal;
	/**
	 *员工编号
	 */
	protected String  user_id;
	/**
	 *职称
	 */
	protected String  positional;
	/**
	 *入职日期
	 */
	protected String  entry_date;
	/**
	 *专业
	 */
	protected String  major;
	/**
	 *试用期所在部门及项目部
	 */
	protected String  department;
	/**
	 *学历
	 */
	protected String  education;
	/**
	 *岗位
	 */
	protected String  posname;
	/**
	 *工资
	 */
	protected String  salary;
	
	//审批状体
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	//申请人所属部门
	protected String creatorOrgName;
	//入职日期
	protected java.util.Date entryTime;
	
	protected java.util.Date formalDate;
	
	public java.util.Date getFormalDate() {
		return formalDate;
	}
	public void setFormalDate(java.util.Date formalDate) {
		this.formalDate = formalDate;
	}
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPosnameID(String posnameID) 
	{
		this.posnameID = posnameID;
	}
	/**
	 * 返回 岗位ID
	 * @return
	 */
	public String getPosnameID() 
	{
		return this.posnameID;
	}
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setAgreement_date_to_formal(String agreement_date_to_formal) 
	{
		this.agreement_date_to_formal = agreement_date_to_formal;
	}
	/**
	 * 返回 劳动合同约定转正时间
	 * @return
	 */
	public String getAgreement_date_to_formal() 
	{
		return this.agreement_date_to_formal;
	}
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
	}
	public void setPositional(String positional) 
	{
		this.positional = positional;
	}
	/**
	 * 返回 职称
	 * @return
	 */
	public String getPositional() 
	{
		return this.positional;
	}
	public void setEntry_date(String entry_date) 
	{
		this.entry_date = entry_date;
	}
	/**
	 * 返回 入职日期
	 * @return
	 */
	public String getEntry_date() 
	{
		return this.entry_date;
	}
	public void setMajor(String major) 
	{
		this.major = major;
	}
	/**
	 * 返回 专业
	 * @return
	 */
	public String getMajor() 
	{
		return this.major;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 试用期所在部门及项目部
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setEducation(String education) 
	{
		this.education = education;
	}
	/**
	 * 返回 学历
	 * @return
	 */
	public String getEducation() 
	{
		return this.education;
	}
	public void setPosname(String posname) 
	{
		this.posname = posname;
	}
	/**
	 * 返回 岗位
	 * @return
	 */
	public String getPosname() 
	{
		return this.posname;
	}
	public void setSalary(String salary) 
	{
		this.salary = salary;
	}
	/**
	 * 返回 工资
	 * @return
	 */
	public String getSalary() 
	{
		return this.salary;
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
		if (!(object instanceof UserFormal)) 
		{
			return false;
		}
		UserFormal rhs = (UserFormal) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.posnameID, rhs.posnameID)
		.append(this.user_name, rhs.user_name)
		.append(this.agreement_date_to_formal, rhs.agreement_date_to_formal)
		.append(this.user_id, rhs.user_id)
		.append(this.positional, rhs.positional)
		.append(this.entry_date, rhs.entry_date)
		.append(this.major, rhs.major)
		.append(this.department, rhs.department)
		.append(this.education, rhs.education)
		.append(this.posname, rhs.posname)
		.append(this.salary, rhs.salary)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.posnameID) 
		.append(this.user_name) 
		.append(this.agreement_date_to_formal) 
		.append(this.user_id) 
		.append(this.positional) 
		.append(this.entry_date) 
		.append(this.major) 
		.append(this.department) 
		.append(this.education) 
		.append(this.posname) 
		.append(this.salary) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("posnameID", this.posnameID) 
		.append("user_name", this.user_name) 
		.append("agreement_date_to_formal", this.agreement_date_to_formal) 
		.append("user_id", this.user_id) 
		.append("positional", this.positional) 
		.append("entry_date", this.entry_date) 
		.append("major", this.major) 
		.append("department", this.department) 
		.append("education", this.education) 
		.append("posname", this.posname) 
		.append("salary", this.salary) 
		.toString();
	}

	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		//TimeUtil.getDateString(createTime);
		return TimeUtil.getDateString(createTime);
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public String getCreatorOrgName() {
		return creatorOrgName;
	}
	public void setCreatorOrgName(String creatorOrgName) {
		this.creatorOrgName = creatorOrgName;
	}
	public String getEntryTime() {
		if(entryTime!=null){
			return TimeUtil.getDateString(entryTime);
		}
		return "";
	}
	public void setEntryTime(java.util.Date entryTime) {
		this.entryTime = entryTime;
	}
}