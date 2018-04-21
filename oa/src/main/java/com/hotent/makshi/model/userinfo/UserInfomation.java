package com.hotent.makshi.model.userinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.userinfo.EntryFamily;
import com.hotent.makshi.model.hr.EntryChildren;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryWorkHistory;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
/**
 * 对象功能:用户信息档案表 Model对象
 */
public class UserInfomation extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6806258254337558960L;

	//主键
	protected Long id;
	
	protected String account;
	
	protected java.util.Date  entrylDate;
	
	protected java.util.Date  formalDate;
	
	protected java.util.Date  leavelDate;
	/**
	 *员工编号
	 */
	protected Long  userId;
	/**
	 *出生日期
	 */
	protected java.util.Date  birthday;
	/**
	 *婚姻状况
	 */
	protected String  marriage_state;
	/**
	 *曾用名
	 */
	protected String  used_name;
	/**
	 *民族
	 */
	protected String  nation;
	/**
	 *籍贯
	 */
	protected String  address;
	/**
	 *职称专业
	 */
	protected String  positional_major;
	/**
	 *文化程度
	 */
	protected String  education;
	/**
	 *参加工作时间
	 */
	protected java.util.Date  start_work_time;
	/**
	 * 毕业时间
	 */
	protected java.util.Date graduate_time;
	/**
	 * 剩余年假
	 */
	protected Double yearVacation;
	/**
	 *毕业院校
	 */
	protected String  graduate_school;
	/**
	 *政治面貌
	 */
	protected String  political_status;
	/**
	 *专业
	 */
	protected String  major;
	/**
	 *身份证号码
	 */
	protected String  identification_id;
	/**
	 *职称
	 */
	protected String  positional;
	/**
	 *户籍
	 */
	protected String  address_type;
	/**
	 *是否有传染病史
	 */
	protected String  infection_history;
	/**
	 *是否有遗传病史
	 */
	protected String  disorders_history;
	/**
	 *社会保险电脑号
	 */
	protected String  social_security_computer_id;
	/**
	 *利手
	 */
	protected String  handedness;
	/**
	 *特长爱好
	 */
	protected String  hobby;
	/**
	 *户籍所在地
	 */
	protected String  home_address;
	/**
	 *配偶姓名
	 */
	protected String  spouse_name;
	/**
	 *父母居住地
	 */
	protected String  parents;
	/**
	 *配偶身份证号码
	 */
	protected String  spouse_identification_id;
	/**
	 *配偶居住地
	 */
	protected String  spouse_address;
	/**
	 *通讯地址
	 */
	protected String  link_address;
	/**
	 *手机短号
	 */
	protected String  sjdh;
	/**
	 *紧急联系人
	 */
	protected String  emergency_link_person;
	/**
	 *交行卡号
	 */
	protected String  BOC_id;
	/**
	 *紧急联系人电话
	 */
	protected String  emergency_link_person_phone;
	/**
	 *QQ号码
	 */
	protected String  QQ;
	/**
	 *微信
	 */
	protected String  wechart;
	
	/**
	 * 社保登记编号
	 */
	protected String social_security_num;
	/**
	 * 与工资总额
	 */
	protected String monthly_wages;
	/**
	 * 养老险
	 */
	protected String endowment_insurance;
	/**
	 * 医疗险一档
	 */
	protected String medical_insurance_first;
	/**
	 * 医疗险二档
	 */
	protected String medical_insurance_second;
	/**
	 * 工伤险
	 */
	protected String injury_insurance;
	/**
	 * 失业险
	 */
	protected String unemployment_insurance;
	
	/**
	 * 工行卡号
	 */
	protected String ICBC_id;
	
	
	
	
	
	/**
	 *用户信息档案表列表
	 */
	protected List<EntryFamily> entryFamilyList=new ArrayList<EntryFamily>();
	/**
	 *用户信息档案表列表
	 */
	protected List<EntryEducationHistory> entryEducationHistoryList=new ArrayList<EntryEducationHistory>();
	/**
	 *用户信息档案表列表
	 */
	protected List<EntryWorkHistory> entryWorkHistoryList=new ArrayList<EntryWorkHistory>();
	/**
	 *用户信息档案表列表
	 */
	protected List<EntryProfessional> entryProfessionalList=new ArrayList<EntryProfessional>();
	/**
	 *用户信息档案表列表
	 */
	protected List<EntryVocationQualification> entryVocationQualificationList=new ArrayList<EntryVocationQualification>();
	//注册资格
	protected List<RegistrationQualification> registrationQualificationList=new ArrayList<RegistrationQualification>();
	
	protected List<OccupationalRequirements> occupationalRequirementList=new ArrayList<OccupationalRequirements>();
	/**
	 *子女信息
	 */
	protected List<EntryChildren> entryChildrenList=new ArrayList<EntryChildren>();
	
	public List<EntryChildren> getEntryChildrenList() {
		return entryChildrenList;
	}
	public void setEntryChildrenList(List<EntryChildren> entryChildrenList) {
		this.entryChildrenList = entryChildrenList;
	}
	
	public List<OccupationalRequirements> getOccupationalRequirementList() {
		return occupationalRequirementList;
	}
	public void setOccupationalRequirementList(List<OccupationalRequirements> occupationalRequirementList) {
		this.occupationalRequirementList = occupationalRequirementList;
	}
	public List<RegistrationQualification> getRegistrationQualificationList() {
		return registrationQualificationList;
	}
	public void setRegistrationQualificationList(
			List<RegistrationQualification> registrationQualificationList) {
		this.registrationQualificationList = registrationQualificationList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setBirthday(java.util.Date birthday) 
	{
		this.birthday = birthday;
	}
	/**
	 * 返回 出生日期
	 * @return
	 */
	public java.util.Date getBirthday() 
	{
		return this.birthday;
	}
	public void setMarriage_state(String marriage_state) 
	{
		this.marriage_state = marriage_state;
	}
	/**
	 * 返回 婚姻状况
	 * @return
	 */
	public String getMarriage_state() 
	{
		return this.marriage_state;
	}
	public void setUsed_name(String used_name) 
	{
		this.used_name = used_name;
	}
	/**
	 * 返回 曾用名
	 * @return
	 */
	public String getUsed_name() 
	{
		return this.used_name;
	}
	public void setNation(String nation) 
	{
		this.nation = nation;
	}
	/**
	 * 返回 民族
	 * @return
	 */
	public String getNation() 
	{
		return this.nation;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 籍贯
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setPositional_major(String positional_major) 
	{
		this.positional_major = positional_major;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getPositional_major() 
	{
		return this.positional_major;
	}
	public void setEducation(String education) 
	{
		this.education = education;
	}
	/**
	 * 返回 文化程度
	 * @return
	 */
	public String getEducation() 
	{
		return this.education;
	}
	public void setStart_work_time(java.util.Date start_work_time) 
	{
		this.start_work_time = start_work_time;
	}
	/**
	 * 返回 参加工作时间
	 * @return
	 */
	public java.util.Date getStart_work_time() 
	{
		return this.start_work_time;
	}
	public void setGraduate_school(String graduate_school) 
	{
		this.graduate_school = graduate_school;
	}
	/**
	 * 返回 毕业院校
	 * @return
	 */
	public String getGraduate_school() 
	{
		return this.graduate_school;
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
	public void setIdentification_id(String identification_id) 
	{
		this.identification_id = identification_id;
	}
	/**
	 * 返回 身份证号码
	 * @return
	 */
	public String getIdentification_id() 
	{
		return this.identification_id;
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
	public void setAddress_type(String address_type) 
	{
		this.address_type = address_type;
	}
	/**
	 * 返回 户籍
	 * @return
	 */
	public String getAddress_type() 
	{
		return this.address_type;
	}
	public void setInfection_history(String infection_history) 
	{
		this.infection_history = infection_history;
	}
	/**
	 * 返回 是否有传染病史
	 * @return
	 */
	public String getInfection_history() 
	{
		return this.infection_history;
	}
	public void setDisorders_history(String disorders_history) 
	{
		this.disorders_history = disorders_history;
	}
	/**
	 * 返回 是否有遗传病史
	 * @return
	 */
	public String getDisorders_history() 
	{
		return this.disorders_history;
	}
	public void setSocial_security_computer_id(String social_security_computer_id) 
	{
		this.social_security_computer_id = social_security_computer_id;
	}
	/**
	 * 返回 社会保险电脑号
	 * @return
	 */
	public String getSocial_security_computer_id() 
	{
		return this.social_security_computer_id;
	}
	public void setHandedness(String handedness) 
	{
		this.handedness = handedness;
	}
	/**
	 * 返回 利手
	 * @return
	 */
	public String getHandedness() 
	{
		return this.handedness;
	}
	public void setHobby(String hobby) 
	{
		this.hobby = hobby;
	}
	/**
	 * 返回 特长爱好
	 * @return
	 */
	public String getHobby() 
	{
		return this.hobby;
	}
	public void setHome_address(String home_address) 
	{
		this.home_address = home_address;
	}
	/**
	 * 返回 户籍所在地
	 * @return
	 */
	public String getHome_address() 
	{
		return this.home_address;
	}
	public void setSpouse_name(String spouse_name) 
	{
		this.spouse_name = spouse_name;
	}
	/**
	 * 返回 配偶姓名
	 * @return
	 */
	public String getSpouse_name() 
	{
		return this.spouse_name;
	}
	public void setParents(String parents) 
	{
		this.parents = parents;
	}
	/**
	 * 返回 父母居住地
	 * @return
	 */
	public String getParents() 
	{
		return this.parents;
	}
	public void setSpouse_identification_id(String spouse_identification_id) 
	{
		this.spouse_identification_id = spouse_identification_id;
	}
	/**
	 * 返回 配偶身份证号码
	 * @return
	 */
	public String getSpouse_identification_id() 
	{
		return this.spouse_identification_id;
	}
	public void setSpouse_address(String spouse_address) 
	{
		this.spouse_address = spouse_address;
	}
	/**
	 * 返回 配偶居住地
	 * @return
	 */
	public String getSpouse_address() 
	{
		return this.spouse_address;
	}
	public void setLink_address(String link_address) 
	{
		this.link_address = link_address;
	}
	/**
	 * 返回 通讯地址
	 * @return
	 */
	public String getLink_address() 
	{
		return this.link_address;
	}
	public void setSjdh(String sjdh) 
	{
		this.sjdh = sjdh;
	}
	/**
	 * 返回 手机短号
	 * @return
	 */
	public String getSjdh() 
	{
		return this.sjdh;
	}
	public void setEmergency_link_person(String emergency_link_person) 
	{
		this.emergency_link_person = emergency_link_person;
	}
	/**
	 * 返回 紧急联系人
	 * @return
	 */
	public String getEmergency_link_person() 
	{
		return this.emergency_link_person;
	}
	public void setBOC_id(String BOC_id) 
	{
		this.BOC_id = BOC_id;
	}
	/**
	 * 返回 交行卡号
	 * @return
	 */
	public String getBOC_id() 
	{
		return this.BOC_id;
	}
	public void setEmergency_link_person_phone(String emergency_link_person_phone) 
	{
		this.emergency_link_person_phone = emergency_link_person_phone;
	}
	/**
	 * 返回 紧急联系人电话
	 * @return
	 */
	public String getEmergency_link_person_phone() 
	{
		return this.emergency_link_person_phone;
	}
	public void setQQ(String QQ) 
	{
		this.QQ = QQ;
	}
	/**
	 * 返回 QQ号码
	 * @return
	 */
	public String getQQ() 
	{
		return this.QQ;
	}
	public void setWechart(String wechart) 
	{
		this.wechart = wechart;
	}
	/**
	 * 返回 微信
	 * @return
	 */
	public String getWechart() 
	{
		return this.wechart;
	}
	public void setEntryFamilyList(List<EntryFamily> entryFamilyList) 
	{
		this.entryFamilyList = entryFamilyList;
	}
	/**
	 * 返回 用户信息档案表列表
	 * @return
	 */
	public List<EntryFamily> getEntryFamilyList() 
	{
		return this.entryFamilyList;
	}
	public void setEntryEducationHistoryList(List<EntryEducationHistory> entryEducationHistoryList) 
	{
		this.entryEducationHistoryList = entryEducationHistoryList;
	}
	/**
	 * 返回 用户信息档案表列表
	 * @return
	 */
	public List<EntryEducationHistory> getEntryEducationHistoryList() 
	{
		return this.entryEducationHistoryList;
	}
	public void setEntryWorkHistoryList(List<EntryWorkHistory> entryWorkHistoryList) 
	{
		this.entryWorkHistoryList = entryWorkHistoryList;
	}
	/**
	 * 返回 用户信息档案表列表
	 * @return
	 */
	public List<EntryWorkHistory> getEntryWorkHistoryList() 
	{
		return this.entryWorkHistoryList;
	}
	public void setEntryProfessionalList(List<EntryProfessional> entryProfessionalList) 
	{
		this.entryProfessionalList = entryProfessionalList;
	}
	/**
	 * 返回 用户信息档案表列表
	 * @return
	 */
	public List<EntryProfessional> getEntryProfessionalList() 
	{
		return this.entryProfessionalList;
	}
	public void setEntryVocationQualificationList(List<EntryVocationQualification> entryVocationQualificationList) 
	{
		this.entryVocationQualificationList = entryVocationQualificationList;
	}
	/**
	 * 返回 用户信息档案表列表
	 * @return
	 */
	public List<EntryVocationQualification> getEntryVocationQualificationList() 
	{
		return this.entryVocationQualificationList;
	}
	
   	public java.util.Date getGraduate_time() {
		return graduate_time;
	}
	public void setGraduate_time(java.util.Date graduate_time) {
		this.graduate_time = graduate_time;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof UserInfomation)) 
		{
			return false;
		}
		UserInfomation rhs = (UserInfomation) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.userId, rhs.userId)
		.append(this.birthday, rhs.birthday)
		.append(this.marriage_state, rhs.marriage_state)
		.append(this.used_name, rhs.used_name)
		.append(this.nation, rhs.nation)
		.append(this.address, rhs.address)
		.append(this.positional_major, rhs.positional_major)
		.append(this.education, rhs.education)
		.append(this.start_work_time, rhs.start_work_time)
		.append(this.graduate_school, rhs.graduate_school)
		.append(this.political_status, rhs.political_status)
		.append(this.major, rhs.major)
		.append(this.identification_id, rhs.identification_id)
		.append(this.positional, rhs.positional)
		.append(this.address_type, rhs.address_type)
		.append(this.infection_history, rhs.infection_history)
		.append(this.disorders_history, rhs.disorders_history)
		.append(this.social_security_computer_id, rhs.social_security_computer_id)
		.append(this.handedness, rhs.handedness)
		.append(this.hobby, rhs.hobby)
		.append(this.home_address, rhs.home_address)
		.append(this.spouse_name, rhs.spouse_name)
		.append(this.parents, rhs.parents)
		.append(this.spouse_identification_id, rhs.spouse_identification_id)
		.append(this.spouse_address, rhs.spouse_address)
		.append(this.link_address, rhs.link_address)
		.append(this.sjdh, rhs.sjdh)
		.append(this.emergency_link_person, rhs.emergency_link_person)
		.append(this.BOC_id, rhs.BOC_id)
		.append(this.emergency_link_person_phone, rhs.emergency_link_person_phone)
		.append(this.QQ, rhs.QQ)
		.append(this.wechart, rhs.wechart)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.userId) 
		.append(this.birthday) 
		.append(this.marriage_state) 
		.append(this.used_name) 
		.append(this.nation) 
		.append(this.address) 
		.append(this.positional_major) 
		.append(this.education) 
		.append(this.start_work_time) 
		.append(this.graduate_school) 
		.append(this.political_status) 
		.append(this.major) 
		.append(this.identification_id) 
		.append(this.positional) 
		.append(this.address_type) 
		.append(this.infection_history) 
		.append(this.disorders_history) 
		.append(this.social_security_computer_id) 
		.append(this.handedness) 
		.append(this.hobby) 
		.append(this.home_address) 
		.append(this.spouse_name) 
		.append(this.parents) 
		.append(this.spouse_identification_id) 
		.append(this.spouse_address) 
		.append(this.link_address) 
		.append(this.sjdh) 
		.append(this.emergency_link_person) 
		.append(this.BOC_id) 
		.append(this.emergency_link_person_phone) 
		.append(this.QQ) 
		.append(this.wechart) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("userId", this.userId) 
		.append("birthday", this.birthday) 
		.append("marriage_state", this.marriage_state) 
		.append("used_name", this.used_name) 
		.append("nation", this.nation) 
		.append("address", this.address) 
		.append("positional_major", this.positional_major) 
		.append("education", this.education) 
		.append("start_work_time", this.start_work_time) 
		.append("graduate_school", this.graduate_school) 
		.append("political_status", this.political_status) 
		.append("major", this.major) 
		.append("identification_id", this.identification_id) 
		.append("positional", this.positional) 
		.append("address_type", this.address_type) 
		.append("infection_history", this.infection_history) 
		.append("disorders_history", this.disorders_history) 
		.append("social_security_computer_id", this.social_security_computer_id) 
		.append("handedness", this.handedness) 
		.append("hobby", this.hobby) 
		.append("home_address", this.home_address) 
		.append("spouse_name", this.spouse_name) 
		.append("parents", this.parents) 
		.append("spouse_identification_id", this.spouse_identification_id) 
		.append("spouse_address", this.spouse_address) 
		.append("link_address", this.link_address) 
		.append("sjdh", this.sjdh) 
		.append("emergency_link_person", this.emergency_link_person) 
		.append("BOC_id", this.BOC_id) 
		.append("emergency_link_person_phone", this.emergency_link_person_phone) 
		.append("QQ", this.QQ) 
		.append("wechart", this.wechart) 
		.toString();
	}
	
	public UserInfomation(){
		
	}
	public UserInfomation(Long id, Long userId, Date birthday,
			String marriage_state, String used_name, String nation,
			String address, String positional_major, String education,
			Date start_work_time, String graduate_school,
			String political_status, String major, String identification_id,
			String positional, String address_type, String infection_history,
			String disorders_history, String social_security_computer_id,
			String handedness, String hobby, String home_address,
			String spouse_name, String parents,
			String spouse_identification_id, String spouse_address,
			String link_address, String sjdh, String emergency_link_person,
			String bOC_id, String emergency_link_person_phone, String qQ,
			String wechart) {
		super();
		this.id=id;
		this.userId = userId;
		this.birthday = birthday;
		this.marriage_state = marriage_state;
		this.used_name = used_name;
		this.nation = nation;
		this.address = address;
		this.positional_major = positional_major;
		this.education = education;
		this.start_work_time = start_work_time;
		this.graduate_school = graduate_school;
		this.political_status = political_status;
		this.major = major;
		this.identification_id = identification_id;
		this.positional = positional;
		this.address_type = address_type;
		this.infection_history = infection_history;
		this.disorders_history = disorders_history;
		this.social_security_computer_id = social_security_computer_id;
		this.handedness = handedness;
		this.hobby = hobby;
		this.home_address = home_address;
		this.spouse_name = spouse_name;
		this.parents = parents;
		this.spouse_identification_id = spouse_identification_id;
		this.spouse_address = spouse_address;
		this.link_address = link_address;
		this.sjdh = sjdh;
		this.emergency_link_person = emergency_link_person;
		this.BOC_id = bOC_id;
		this.emergency_link_person_phone = emergency_link_person_phone;
		this.QQ = qQ;
		this.wechart = wechart;
	}
	public String getSocial_security_num() {
		return social_security_num;
	}
	public void setSocial_security_num(String social_security_num) {
		this.social_security_num = social_security_num;
	}
	public String getMonthly_wages() {
		return monthly_wages;
	}
	public void setMonthly_wages(String monthly_wages) {
		this.monthly_wages = monthly_wages;
	}
	public String getEndowment_insurance() {
		return endowment_insurance;
	}
	public void setEndowment_insurance(String endowment_insurance) {
		this.endowment_insurance = endowment_insurance;
	}
	public String getMedical_insurance_first() {
		return medical_insurance_first;
	}
	public void setMedical_insurance_first(String medical_insurance_first) {
		this.medical_insurance_first = medical_insurance_first;
	}
	public String getMedical_insurance_second() {
		return medical_insurance_second;
	}
	public void setMedical_insurance_second(String medical_insurance_second) {
		this.medical_insurance_second = medical_insurance_second;
	}
	public String getInjury_insurance() {
		return injury_insurance;
	}
	public void setInjury_insurance(String injury_insurance) {
		this.injury_insurance = injury_insurance;
	}
	public String getUnemployment_insurance() {
		return unemployment_insurance;
	}
	public void setUnemployment_insurance(String unemployment_insurance) {
		this.unemployment_insurance = unemployment_insurance;
	}
	public String getICBC_id() {
		return ICBC_id;
	}
	public void setICBC_id(String iCBC_id) {
		ICBC_id = iCBC_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public java.util.Date getEntrylDate() {
		return entrylDate;
	}
	public void setEntrylDate(java.util.Date entrylDate) {
		this.entrylDate = entrylDate;
	}
	public java.util.Date getFormalDate() {
		return formalDate;
	}
	public void setFormalDate(java.util.Date formalDate) {
		this.formalDate = formalDate;
	}
	public java.util.Date getLeavelDate() {
		return leavelDate;
	}
	public void setLeavelDate(java.util.Date leavelDate) {
		this.leavelDate = leavelDate;
	}
	public Double getYearVacation() {
		return yearVacation;
	}
	public void setYearVacation(Double yearVacation) {
		this.yearVacation = yearVacation;
	}
	
}