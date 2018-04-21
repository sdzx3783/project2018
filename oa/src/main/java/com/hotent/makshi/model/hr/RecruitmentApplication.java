package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.BaseModel;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:招聘申请 Model对象
 */
public class RecruitmentApplication extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申报部门ID
	 */
	protected String  declare_departmentID;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申报部门
	 */
	protected String  declare_department;
	/**
	 *申报时间
	 */
	protected java.util.Date  declare_time;
	/**
	 *招聘方式
	 */
	protected String  recruitment_method;
	/**
	 *补增岗位
	 */
	protected String  position_whereabouts;
	/**
	 *招聘人数
	 */
	protected Long  recruitment_number;
	/**
	 *专业要求
	 */
	protected String  recruitment_professional;
	/**
	 *员工编号
	 */
	protected String  user_num;
	/**
	 *申请理由
	 */
	protected String  appli_reason;
	/**
	 *性别
	 */
	protected String  sex;
	/**
	 *婚姻状况
	 */
	protected String  marriage_status;
	/**
	 *户籍状况
	 */
	protected String  birthplace;
	/**
	 *政治面貌
	 */
	protected String  political_status;
	/**
	 *学历要求
	 */
	protected String  edu_requirement;
	/**
	 *语言要求
	 */
	protected String  language_reqirement;
	/**
	 *技能等级(证书)要求
	 */
	protected String  degree_requirement;
	/**
	 *经历/经验要求
	 */
	protected String  experience_reqirement;
	/**
	 *技能要求
	 */
	protected String  skill_requirement;
	/**
	 *个性要求
	 */
	protected String  character_requirement;
	/**
	 *其他要求
	 */
	protected String  other_requirement;
	/**
	 *其他补充
	 */
	protected String  other_remark;
	/**
	 *其他原因
	 */
	protected String  other_reason_content;
	/**
	 *年龄区间
	 */
	protected String  age_limit;
	/**
	 *最小年龄
	 */
	protected Long  age_least;
	/**
	 *最大年龄
	 */
	protected Long  age_most;
	/**
	 *毕业时间要求
	 */
	protected String  graduation_year;
	/**
	 *毕业年份
	 */
	protected String  graduation_date;
	
	protected String  globalFlowNo;
	
	protected Long  runId=0L;
	
	protected String  creator;
	
	protected java.util.Date  createTime;
	
	protected String  processStatus;
	
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDeclare_departmentID(String declare_departmentID) 
	{
		this.declare_departmentID = declare_departmentID;
	}
	/**
	 * 返回 申报部门ID
	 * @return
	 */
	public String getDeclare_departmentID() 
	{
		return this.declare_departmentID;
	}
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
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
	public void setDeclare_department(String declare_department) 
	{
		this.declare_department = declare_department;
	}
	/**
	 * 返回 申报部门
	 * @return
	 */
	public String getDeclare_department() 
	{
		return this.declare_department;
	}
	public void setDeclare_time(java.util.Date declare_time) 
	{
		this.declare_time = declare_time;
	}
	/**
	 * 返回 申报时间
	 * @return
	 */
	public java.util.Date getDeclare_time() 
	{
		return this.declare_time;
	}
	public void setRecruitment_method(String recruitment_method) 
	{
		this.recruitment_method = recruitment_method;
	}
	/**
	 * 返回 招聘方式
	 * @return
	 */
	public String getRecruitment_method() 
	{
		return this.recruitment_method;
	}
	public void setPosition_whereabouts(String position_whereabouts) 
	{
		this.position_whereabouts = position_whereabouts;
	}
	/**
	 * 返回 补增岗位
	 * @return
	 */
	public String getPosition_whereabouts() 
	{
		return this.position_whereabouts;
	}
	public void setRecruitment_number(Long recruitment_number) 
	{
		this.recruitment_number = recruitment_number;
	}
	/**
	 * 返回 招聘人数
	 * @return
	 */
	public Long getRecruitment_number() 
	{
		return this.recruitment_number;
	}
	public void setRecruitment_professional(String recruitment_professional) 
	{
		this.recruitment_professional = recruitment_professional;
	}
	/**
	 * 返回 专业要求
	 * @return
	 */
	public String getRecruitment_professional() 
	{
		return this.recruitment_professional;
	}
	public void setUser_num(String user_num) 
	{
		this.user_num = user_num;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_num() 
	{
		return this.user_num;
	}
	public void setAppli_reason(String appli_reason) 
	{
		this.appli_reason = appli_reason;
	}
	/**
	 * 返回 申请理由
	 * @return
	 */
	public String getAppli_reason() 
	{
		return this.appli_reason;
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
	public void setMarriage_status(String marriage_status) 
	{
		this.marriage_status = marriage_status;
	}
	/**
	 * 返回 婚姻状况
	 * @return
	 */
	public String getMarriage_status() 
	{
		return this.marriage_status;
	}
	public void setBirthplace(String birthplace) 
	{
		this.birthplace = birthplace;
	}
	/**
	 * 返回 户籍状况
	 * @return
	 */
	public String getBirthplace() 
	{
		return this.birthplace;
	}
	public void setPolitical_status(String political_status) 
	{
		this.political_status = political_status;
	}
	/**
	 * 返回 政治面貌
	 * @return
	 */
	public String getPolitical_status() 
	{
		return this.political_status;
	}
	public void setEdu_requirement(String edu_requirement) 
	{
		this.edu_requirement = edu_requirement;
	}
	/**
	 * 返回 学历要求
	 * @return
	 */
	public String getEdu_requirement() 
	{
		return this.edu_requirement;
	}
	public void setLanguage_reqirement(String language_reqirement) 
	{
		this.language_reqirement = language_reqirement;
	}
	/**
	 * 返回 语言要求
	 * @return
	 */
	public String getLanguage_reqirement() 
	{
		return this.language_reqirement;
	}
	public void setDegree_requirement(String degree_requirement) 
	{
		this.degree_requirement = degree_requirement;
	}
	/**
	 * 返回 技能等级(证书)要求
	 * @return
	 */
	public String getDegree_requirement() 
	{
		return this.degree_requirement;
	}
	public void setExperience_reqirement(String experience_reqirement) 
	{
		this.experience_reqirement = experience_reqirement;
	}
	/**
	 * 返回 经历/经验要求
	 * @return
	 */
	public String getExperience_reqirement() 
	{
		return this.experience_reqirement;
	}
	public void setSkill_requirement(String skill_requirement) 
	{
		this.skill_requirement = skill_requirement;
	}
	/**
	 * 返回 技能要求
	 * @return
	 */
	public String getSkill_requirement() 
	{
		return this.skill_requirement;
	}
	public void setCharacter_requirement(String character_requirement) 
	{
		this.character_requirement = character_requirement;
	}
	/**
	 * 返回 个性要求
	 * @return
	 */
	public String getCharacter_requirement() 
	{
		return this.character_requirement;
	}
	public void setOther_requirement(String other_requirement) 
	{
		this.other_requirement = other_requirement;
	}
	/**
	 * 返回 其他要求
	 * @return
	 */
	public String getOther_requirement() 
	{
		return this.other_requirement;
	}
	public void setOther_remark(String other_remark) 
	{
		this.other_remark = other_remark;
	}
	/**
	 * 返回 其他补充
	 * @return
	 */
	public String getOther_remark() 
	{
		return this.other_remark;
	}
	public void setOther_reason_content(String other_reason_content) 
	{
		this.other_reason_content = other_reason_content;
	}
	/**
	 * 返回 其他原因
	 * @return
	 */
	public String getOther_reason_content() 
	{
		return this.other_reason_content;
	}
	public void setAge_limit(String age_limit) 
	{
		this.age_limit = age_limit;
	}
	/**
	 * 返回 年龄区间
	 * @return
	 */
	public String getAge_limit() 
	{
		return this.age_limit;
	}
	public void setAge_least(Long age_least) 
	{
		this.age_least = age_least;
	}
	/**
	 * 返回 最小年龄
	 * @return
	 */
	public Long getAge_least() 
	{
		return this.age_least;
	}
	public void setAge_most(Long age_most) 
	{
		this.age_most = age_most;
	}
	/**
	 * 返回 最大年龄
	 * @return
	 */
	public Long getAge_most() 
	{
		return this.age_most;
	}
	public void setGraduation_year(String graduation_year) 
	{
		this.graduation_year = graduation_year;
	}
	/**
	 * 返回 毕业时间要求
	 * @return
	 */
	public String getGraduation_year() 
	{
		return this.graduation_year;
	}
	public void setGraduation_date(String graduation_date) 
	{
		this.graduation_date = graduation_date;
	}
	/**
	 * 返回 毕业年份
	 * @return
	 */
	public String getGraduation_date() 
	{
		return this.graduation_date;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RecruitmentApplication)) 
		{
			return false;
		}
		RecruitmentApplication rhs = (RecruitmentApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.declare_departmentID, rhs.declare_departmentID)
		.append(this.account, rhs.account)
		.append(this.applicant, rhs.applicant)
		.append(this.declare_department, rhs.declare_department)
		.append(this.declare_time, rhs.declare_time)
		.append(this.recruitment_method, rhs.recruitment_method)
		.append(this.position_whereabouts, rhs.position_whereabouts)
		.append(this.recruitment_number, rhs.recruitment_number)
		.append(this.recruitment_professional, rhs.recruitment_professional)
		.append(this.user_num, rhs.user_num)
		.append(this.appli_reason, rhs.appli_reason)
		.append(this.sex, rhs.sex)
		.append(this.marriage_status, rhs.marriage_status)
		.append(this.birthplace, rhs.birthplace)
		.append(this.political_status, rhs.political_status)
		.append(this.edu_requirement, rhs.edu_requirement)
		.append(this.language_reqirement, rhs.language_reqirement)
		.append(this.degree_requirement, rhs.degree_requirement)
		.append(this.experience_reqirement, rhs.experience_reqirement)
		.append(this.skill_requirement, rhs.skill_requirement)
		.append(this.character_requirement, rhs.character_requirement)
		.append(this.other_requirement, rhs.other_requirement)
		.append(this.other_remark, rhs.other_remark)
		.append(this.other_reason_content, rhs.other_reason_content)
		.append(this.age_limit, rhs.age_limit)
		.append(this.age_least, rhs.age_least)
		.append(this.age_most, rhs.age_most)
		.append(this.graduation_year, rhs.graduation_year)
		.append(this.graduation_date, rhs.graduation_date)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.declare_departmentID) 
		.append(this.account) 
		.append(this.applicant) 
		.append(this.declare_department) 
		.append(this.declare_time) 
		.append(this.recruitment_method) 
		.append(this.position_whereabouts) 
		.append(this.recruitment_number) 
		.append(this.recruitment_professional) 
		.append(this.user_num) 
		.append(this.appli_reason) 
		.append(this.sex) 
		.append(this.marriage_status) 
		.append(this.birthplace) 
		.append(this.political_status) 
		.append(this.edu_requirement) 
		.append(this.language_reqirement) 
		.append(this.degree_requirement) 
		.append(this.experience_reqirement) 
		.append(this.skill_requirement) 
		.append(this.character_requirement) 
		.append(this.other_requirement) 
		.append(this.other_remark) 
		.append(this.other_reason_content) 
		.append(this.age_limit) 
		.append(this.age_least) 
		.append(this.age_most) 
		.append(this.graduation_year) 
		.append(this.graduation_date) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("declare_departmentID", this.declare_departmentID) 
		.append("account", this.account) 
		.append("applicant", this.applicant) 
		.append("declare_department", this.declare_department) 
		.append("declare_time", this.declare_time) 
		.append("recruitment_method", this.recruitment_method) 
		.append("position_whereabouts", this.position_whereabouts) 
		.append("recruitment_number", this.recruitment_number) 
		.append("recruitment_professional", this.recruitment_professional) 
		.append("user_num", this.user_num) 
		.append("appli_reason", this.appli_reason) 
		.append("sex", this.sex) 
		.append("marriage_status", this.marriage_status) 
		.append("birthplace", this.birthplace) 
		.append("political_status", this.political_status) 
		.append("edu_requirement", this.edu_requirement) 
		.append("language_reqirement", this.language_reqirement) 
		.append("degree_requirement", this.degree_requirement) 
		.append("experience_reqirement", this.experience_reqirement) 
		.append("skill_requirement", this.skill_requirement) 
		.append("character_requirement", this.character_requirement) 
		.append("other_requirement", this.other_requirement) 
		.append("other_remark", this.other_remark) 
		.append("other_reason_content", this.other_reason_content) 
		.append("age_limit", this.age_limit) 
		.append("age_least", this.age_least) 
		.append("age_most", this.age_most) 
		.append("graduation_year", this.graduation_year) 
		.append("graduation_date", this.graduation_date) 
		.toString();
	}

}