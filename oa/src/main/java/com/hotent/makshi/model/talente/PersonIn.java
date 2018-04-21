package com.hotent.makshi.model.talente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:人才引进 Model对象
 */
public class PersonIn extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *人才引进记录编号
	 */
	protected String  person_in_num;
	/**
	 *档案托管单位
	 */
	protected String  trust_unit;
	/**
	 *员工编号
	 */
	protected String  user_num;
	/**
	 *落户时间
	 */
	protected java.util.Date  settling_time;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *户口所属派出所
	 */
	protected String  police_station;
	/**
	 *申请时间
	 */
	protected java.util.Date  application_time;
	/**
	 *到档情况
	 */
	protected String  isArrive;
	/**
	 *申报方式
	 */
	protected String  method;
	/**
	 *落户情况
	 */
	protected String  settled_situation;
	/**
	 *申报类型
	 */
	protected String  type;
	/**
	 *缴费时间
	 */
	protected java.util.Date  payment_time;
	/**
	 *到档时间
	 */
	protected java.util.Date  arrival_time;
	/**
	 *缴费金额
	 */
	protected String  payment_amount;
	/**
	 *人事档案编号
	 */
	protected String  userdata_num;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  file;
	/**
	 *引进状态
	 */
	protected String  introduction_state;
	
	protected String  id_number;
	
	protected String  edu;
	
	protected String  age;
	
	protected java.util.Date  entryDate;
	
	protected String  residence_type;
	
	protected String  house_status;
	
	protected String  tel_number;
	
	protected String  appli_reason;
	
	protected String  account;
	
	protected String  applicantID;
	
	
	protected Long  runId=0L;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getApplicantID() {
		return applicantID;
	}
	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getResidence_type() {
		return residence_type;
	}
	public void setResidence_type(String residence_type) {
		this.residence_type = residence_type;
	}
	public String getHouse_status() {
		return house_status;
	}
	public void setHouse_status(String house_status) {
		this.house_status = house_status;
	}
	public String getTel_number() {
		return tel_number;
	}
	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}
	public String getAppli_reason() {
		return appli_reason;
	}
	public void setAppli_reason(String appli_reason) {
		this.appli_reason = appli_reason;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setPerson_in_num(String person_in_num) 
	{
		this.person_in_num = person_in_num;
	}
	/**
	 * 返回 人才引进记录编号
	 * @return
	 */
	public String getPerson_in_num() 
	{
		return this.person_in_num;
	}
	public void setTrust_unit(String trust_unit) 
	{
		this.trust_unit = trust_unit;
	}
	/**
	 * 返回 档案托管单位
	 * @return
	 */
	public String getTrust_unit() 
	{
		return this.trust_unit;
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
	public void setSettling_time(java.util.Date settling_time) 
	{
		this.settling_time = settling_time;
	}
	/**
	 * 返回 落户时间
	 * @return
	 */
	public java.util.Date getSettling_time() 
	{
		return this.settling_time;
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
	public void setPolice_station(String police_station) 
	{
		this.police_station = police_station;
	}
	/**
	 * 返回 户口所属派出所
	 * @return
	 */
	public String getPolice_station() 
	{
		return this.police_station;
	}
	public void setApplication_time(java.util.Date application_time) 
	{
		this.application_time = application_time;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getApplication_time() 
	{
		return this.application_time;
	}
	public void setIsArrive(String isArrive) 
	{
		this.isArrive = isArrive;
	}
	/**
	 * 返回 到档情况
	 * @return
	 */
	public String getIsArrive() 
	{
		return this.isArrive;
	}
	public void setMethod(String method) 
	{
		this.method = method;
	}
	/**
	 * 返回 申报方式
	 * @return
	 */
	public String getMethod() 
	{
		return this.method;
	}
	public void setSettled_situation(String settled_situation) 
	{
		this.settled_situation = settled_situation;
	}
	/**
	 * 返回 落户情况
	 * @return
	 */
	public String getSettled_situation() 
	{
		return this.settled_situation;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 申报类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setPayment_time(java.util.Date payment_time) 
	{
		this.payment_time = payment_time;
	}
	/**
	 * 返回 缴费时间
	 * @return
	 */
	public java.util.Date getPayment_time() 
	{
		return this.payment_time;
	}
	public void setArrival_time(java.util.Date arrival_time) 
	{
		this.arrival_time = arrival_time;
	}
	/**
	 * 返回 到档时间
	 * @return
	 */
	public java.util.Date getArrival_time() 
	{
		return this.arrival_time;
	}
	public void setPayment_amount(String payment_amount) 
	{
		this.payment_amount = payment_amount;
	}
	/**
	 * 返回 缴费金额
	 * @return
	 */
	public String getPayment_amount() 
	{
		return this.payment_amount;
	}
	public void setUserdata_num(String userdata_num) 
	{
		this.userdata_num = userdata_num;
	}
	/**
	 * 返回 人事档案编号
	 * @return
	 */
	public String getUserdata_num() 
	{
		return this.userdata_num;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
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
	public void setIntroduction_state(String introduction_state) 
	{
		this.introduction_state = introduction_state;
	}
	/**
	 * 返回 引进状态
	 * @return
	 */
	public String getIntroduction_state() 
	{
		return this.introduction_state;
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
		if (!(object instanceof PersonIn)) 
		{
			return false;
		}
		PersonIn rhs = (PersonIn) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.person_in_num, rhs.person_in_num)
		.append(this.trust_unit, rhs.trust_unit)
		.append(this.user_num, rhs.user_num)
		.append(this.settling_time, rhs.settling_time)
		.append(this.applicant, rhs.applicant)
		.append(this.police_station, rhs.police_station)
		.append(this.application_time, rhs.application_time)
		.append(this.isArrive, rhs.isArrive)
		.append(this.method, rhs.method)
		.append(this.settled_situation, rhs.settled_situation)
		.append(this.type, rhs.type)
		.append(this.payment_time, rhs.payment_time)
		.append(this.arrival_time, rhs.arrival_time)
		.append(this.payment_amount, rhs.payment_amount)
		.append(this.userdata_num, rhs.userdata_num)
		.append(this.remark, rhs.remark)
		.append(this.file, rhs.file)
		.append(this.introduction_state, rhs.introduction_state)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.person_in_num) 
		.append(this.trust_unit) 
		.append(this.user_num) 
		.append(this.settling_time) 
		.append(this.applicant) 
		.append(this.police_station) 
		.append(this.application_time) 
		.append(this.isArrive) 
		.append(this.method) 
		.append(this.settled_situation) 
		.append(this.type) 
		.append(this.payment_time) 
		.append(this.arrival_time) 
		.append(this.payment_amount) 
		.append(this.userdata_num) 
		.append(this.remark) 
		.append(this.file) 
		.append(this.introduction_state) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("person_in_num", this.person_in_num) 
		.append("trust_unit", this.trust_unit) 
		.append("user_num", this.user_num) 
		.append("settling_time", this.settling_time) 
		.append("applicant", this.applicant) 
		.append("police_station", this.police_station) 
		.append("application_time", this.application_time) 
		.append("isArrive", this.isArrive) 
		.append("method", this.method) 
		.append("settled_situation", this.settled_situation) 
		.append("type", this.type) 
		.append("payment_time", this.payment_time) 
		.append("arrival_time", this.arrival_time) 
		.append("payment_amount", this.payment_amount) 
		.append("userdata_num", this.userdata_num) 
		.append("remark", this.remark) 
		.append("file", this.file) 
		.append("introduction_state", this.introduction_state) 
		.toString();
	}

}