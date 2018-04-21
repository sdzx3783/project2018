package com.hotent.makshi.model.qualification;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:个人执业资格 Model对象
 */
public class PersonalQualification extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8234749678293540180L;
	//主键
	protected Long id;
	//转入日期
	protected java.util.Date in_date;
	//初始注册日期
	protected java.util.Date regist_date;
	protected Long userId;
	/**
	 * 当前借用人
	 */
	protected String borrower;
	/**
	 * 当前借用人Id
	 */
	protected String borrowerID;
	/**
	 * 是否转入
	 */
	protected Integer switchs;
	/**
	 * 所学专业
	 */
	protected String learnMajor;
	/**
	 *员工编号
	 */
	protected String  account;
	/**
	 *姓名
	 */
	protected String  name;
	/**
	 *性别
	 */
	protected String  sex;
	/**
	 *名族
	 */
	protected String  nation;
	/**
	 *身份证号码
	 */
	protected String  ID_number;
	/**
	 *学历
	 */
	protected String  xl;
	/**
	 *毕业院校
	 */
	protected String  graduation_school;
	/**
	 *毕业时间
	 */
	protected java.util.Date  graduation_date;
	/**
	 *职称等级一
	 */
	protected String  positional_degree_one;
	/**
	 *职称专业一
	 */
	protected String  positional_major_one;
	/**
	 *职称等级二
	 */
	protected String  positional_degree_two;
	/**
	 *职称专业二
	 */
	protected String  positional_major_two;
	/**
	 *职称等级三
	 */
	protected String  positional_degree_three;
	/**
	 *职称专业三
	 */
	protected String  positional_major_three;
	/**
	 *资格证书类型
	 */
	protected String  certificate_type;
	/**
	 *资格证书编号
	 */
	protected String  certificate_id;
	/**
	 *资格证书发证日期
	 */
	protected java.util.Date  certificate_date;
	/**
	 *执业编号
	 */
	protected String  certified_id;
	/**
	 *证书专业
	 */
	protected String  certificate_major;
	/**
	 *资格证书签发单位
	 */
	protected String  send_unit;
	/**
	 *转出日期
	 */
	protected java.util.Date  out_date;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *是否已注册
	 */
	protected String  isregist;
	/**
	 *注册证书编号
	 */
	protected String  certificate_regist_id;
	/**
	 *注册号
	 */
	protected String  regist_id;
	/**
	 *注册证书发证日期
	 */
	protected java.util.Date  get_date;
	/**
	 *注册证书有效日期
	 */
	protected java.util.Date  last_effectice_date;
	/**
	 *注册专业
	 */
	protected String  regist_major;
	/**
	 *执业印章号
	 */
	protected String  seal_id;
	/**
	 *转出日期
	 */
	protected java.util.Date  regist_out_date;
	/**
	 *注册证书发证单位
	 */
	protected String  regist_send_unit;
	/**
	 *最新注册类别
	 */
	protected String  lasted_regist_type;
	/**
	 *最新注册日期
	 */
	protected java.util.Date  lasted_regist_date;
	/**
	 *继续教育完成情况
	 */
	protected String  keep_edu_status;
	/**
	 * 必修课学时
	 */
	protected String regist_compulsory;
	/**
	 * 选修课学时
	 */
	protected String regist_elective;
	/**
	 * 总学时
	 */
	protected String regist_period;
	/**
	 *备注
	 */
	protected String  regist_remark;
	/**
	 *附件
	 */
	protected String  regist_attachment;
	/**
	 * 是否为挂靠
	 * @return
	 */
	protected int isBinding;
	/**
	 *occId
	 */
	protected Long occId;
	/**
	 *证书类型
	 */
	protected String  occ_type;
	/**
	 *证书编号
	 */
	protected String  occ_certificate_id;
	/**
	 *发证日期
	 */
	protected java.util.Date  occ_get_date;
	/**
	 *有效日期
	 */
	protected java.util.Date  occ_period_of_validity;
	/**
	 *专业
	 */
	protected String  occ_major;
	/**
	 *转出日期
	 */
	protected java.util.Date  occ_out_date;
	/**
	 *转入日期
	 */
	protected java.util.Date  occ_in_date;
	/**
	 *发证单位
	 */
	protected String  occ_send_unit;
	/**
	 *工种
	 */
	protected String  occ_type_work;
	/**
	 *工种等级
	 */
	protected String  occ_degree_work;
	/**
	 *继续教育完成情况
	 */
	protected String  occ_contine_edu_comple;
	/**
	 * 必修课学时
	 */
	protected String occ_compulsory;
	/**
	 * 选修课学时
	 */
	protected String occ_elective;
	/**
	 * 总学时
	 */
	protected String occ_period;
	/**
	 *备注
	 */
	protected String  occ_remark;
	/**
	 *附件
	 */
	protected String  occ_attachment;
	/**
	 * 执业印章有效期
	 * @return
	 */
	protected java.util.Date  effictiveDate;
	/**
	 * 第二注册专业
	 * @return
	 */
	protected String regist_secondMajor;
	/**
	 * 第三注册专业
	 * @return
	 */
	protected String regist_thirdMajor;
	/**
	 * 第二从业专业
	 * @return
	 */
	protected String occ_secondMajor;
	
	public java.util.Date getRegist_date() {
		return regist_date;
	}
	public void setRegist_date(java.util.Date regist_date) {
		this.regist_date = regist_date;
	}
	public String getOcc_secondMajor() {
		return occ_secondMajor;
	}
	public void setOcc_secondMajor(String occ_secondMajor) {
		this.occ_secondMajor = occ_secondMajor;
	}
	public String getRegist_secondMajor() {
		return regist_secondMajor;
	}
	public void setRegist_secondMajor(String regist_secondMajor) {
		this.regist_secondMajor = regist_secondMajor;
	}
	public String getRegist_thirdMajor() {
		return regist_thirdMajor;
	}
	public void setRegist_thirdMajor(String regist_thirdMajor) {
		this.regist_thirdMajor = regist_thirdMajor;
	}
	public String getRegist_compulsory() {
		return regist_compulsory;
	}
	public void setRegist_compulsory(String regist_compulsory) {
		this.regist_compulsory = regist_compulsory;
	}
	public String getRegist_elective() {
		return regist_elective;
	}
	public void setRegist_elective(String regist_elective) {
		this.regist_elective = regist_elective;
	}
	public String getRegist_period() {
		return regist_period;
	}
	public void setRegist_period(String regist_period) {
		this.regist_period = regist_period;
	}
	public Long getOccId() {
		return occId;
	}
	public void setOccId(Long occId) {
		this.occId = occId;
	}
	public String getOcc_compulsory() {
		return occ_compulsory;
	}
	public void setOcc_compulsory(String occ_compulsory) {
		this.occ_compulsory = occ_compulsory;
	}
	public String getOcc_elective() {
		return occ_elective;
	}
	public void setOcc_elective(String occ_elective) {
		this.occ_elective = occ_elective;
	}
	public String getOcc_period() {
		return occ_period;
	}
	public void setOcc_period(String occ_period) {
		this.occ_period = occ_period;
	}
	public java.util.Date getEffictiveDate() {
		return effictiveDate;
	}
	public void setEffictiveDate(java.util.Date effictiveDate) {
		this.effictiveDate = effictiveDate;
	}
	public String getOcc_type() {
		return occ_type;
	}
	public void setOcc_type(String occ_type) {
		this.occ_type = occ_type;
	}
	public String getOcc_certificate_id() {
		return occ_certificate_id;
	}
	public void setOcc_certificate_id(String occ_certificate_id) {
		this.occ_certificate_id = occ_certificate_id;
	}
	public java.util.Date getOcc_get_date() {
		return occ_get_date;
	}
	public void setOcc_get_date(java.util.Date occ_get_date) {
		this.occ_get_date = occ_get_date;
	}
	public java.util.Date getOcc_period_of_validity() {
		return occ_period_of_validity;
	}
	public void setOcc_period_of_validity(java.util.Date occ_period_of_validity) {
		this.occ_period_of_validity = occ_period_of_validity;
	}
	public String getOcc_major() {
		return occ_major;
	}
	public void setOcc_major(String occ_major) {
		this.occ_major = occ_major;
	}
	public java.util.Date getOcc_out_date() {
		return occ_out_date;
	}
	public void setOcc_out_date(java.util.Date occ_out_date) {
		this.occ_out_date = occ_out_date;
	}
	public String getOcc_send_unit() {
		return occ_send_unit;
	}
	public void setOcc_send_unit(String occ_send_unit) {
		this.occ_send_unit = occ_send_unit;
	}
	public String getOcc_type_work() {
		return occ_type_work;
	}
	public void setOcc_type_work(String occ_type_work) {
		this.occ_type_work = occ_type_work;
	}
	public String getOcc_degree_work() {
		return occ_degree_work;
	}
	public void setOcc_degree_work(String occ_degree_work) {
		this.occ_degree_work = occ_degree_work;
	}
	public String getOcc_contine_edu_comple() {
		return occ_contine_edu_comple;
	}
	public void setOcc_contine_edu_comple(String occ_contine_edu_comple) {
		this.occ_contine_edu_comple = occ_contine_edu_comple;
	}
	public String getOcc_remark() {
		return occ_remark;
	}
	public void setOcc_remark(String occ_remark) {
		this.occ_remark = occ_remark;
	}
	public String getOcc_attachment() {
		return occ_attachment;
	}
	public void setOcc_attachment(String occ_attachment) {
		this.occ_attachment = occ_attachment;
	}
	public int getIsBinding() {
		return isBinding;
	}
	public void setIsBinding(int isBinding) {
		this.isBinding = isBinding;
	}
	public String getLearnMajor() {
		return learnMajor;
	}
	public void setLearnMajor(String learnMajor) {
		this.learnMajor = learnMajor;
	}
	public java.util.Date getIn_date() {
		return in_date;
	}
	public void setIn_date(java.util.Date in_date) {
		this.in_date = in_date;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
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
	public void setNation(String nation) 
	{
		this.nation = nation;
	}
	/**
	 * 返回 名族
	 * @return
	 */
	public String getNation() 
	{
		return this.nation;
	}
	public void setID_number(String ID_number) 
	{
		this.ID_number = ID_number;
	}
	/**
	 * 返回 身份证号码
	 * @return
	 */
	public String getID_number() 
	{
		return this.ID_number;
	}
	public void setXl(String xl) 
	{
		this.xl = xl;
	}
	/**
	 * 返回 学历
	 * @return
	 */
	public String getXl() 
	{
		return this.xl;
	}
	public void setGraduation_school(String graduation_school) 
	{
		this.graduation_school = graduation_school;
	}
	/**
	 * 返回 毕业院校
	 * @return
	 */
	public String getGraduation_school() 
	{
		return this.graduation_school;
	}
	public void setGraduation_date(java.util.Date graduation_date) 
	{
		this.graduation_date = graduation_date;
	}
	/**
	 * 返回 毕业时间
	 * @return
	 */
	public java.util.Date getGraduation_date() 
	{
		return this.graduation_date;
	}
	public void setPositional_degree_one(String positional_degree_one) 
	{
		this.positional_degree_one = positional_degree_one;
	}
	/**
	 * 返回 职称等级一
	 * @return
	 */
	public String getPositional_degree_one() 
	{
		return this.positional_degree_one;
	}
	public void setPositional_major_one(String positional_major_one) 
	{
		this.positional_major_one = positional_major_one;
	}
	/**
	 * 返回 职称专业一
	 * @return
	 */
	public String getPositional_major_one() 
	{
		return this.positional_major_one;
	}
	public void setPositional_degree_two(String positional_degree_two) 
	{
		this.positional_degree_two = positional_degree_two;
	}
	/**
	 * 返回 职称等级二
	 * @return
	 */
	public String getPositional_degree_two() 
	{
		return this.positional_degree_two;
	}
	public void setPositional_major_two(String positional_major_two) 
	{
		this.positional_major_two = positional_major_two;
	}
	/**
	 * 返回 职称专业二
	 * @return
	 */
	public String getPositional_major_two() 
	{
		return this.positional_major_two;
	}
	public void setPositional_degree_three(String positional_degree_three) 
	{
		this.positional_degree_three = positional_degree_three;
	}
	/**
	 * 返回 职称等级三
	 * @return
	 */
	public String getPositional_degree_three() 
	{
		return this.positional_degree_three;
	}
	public void setPositional_major_three(String positional_major_three) 
	{
		this.positional_major_three = positional_major_three;
	}
	/**
	 * 返回 职称专业三
	 * @return
	 */
	public String getPositional_major_three() 
	{
		return this.positional_major_three;
	}
	public void setCertificate_type(String certificate_type) 
	{
		this.certificate_type = certificate_type;
	}
	/**
	 * 返回 资格证书类型
	 * @return
	 */
	public String getCertificate_type() 
	{
		return this.certificate_type;
	}
	public void setCertificate_id(String certificate_id) 
	{
		this.certificate_id = certificate_id;
	}
	/**
	 * 返回 资格证书编号
	 * @return
	 */
	public String getCertificate_id() 
	{
		return this.certificate_id;
	}
	public void setCertificate_date(java.util.Date certificate_date) 
	{
		this.certificate_date = certificate_date;
	}
	/**
	 * 返回 资格证书发证日期
	 * @return
	 */
	public java.util.Date getCertificate_date() 
	{
		return this.certificate_date;
	}
	public void setCertified_id(String certified_id) 
	{
		this.certified_id = certified_id;
	}
	/**
	 * 返回 执业编号
	 * @return
	 */
	public String getCertified_id() 
	{
		return this.certified_id;
	}
	public void setCertificate_major(String certificate_major) 
	{
		this.certificate_major = certificate_major;
	}
	/**
	 * 返回 证书专业
	 * @return
	 */
	public String getCertificate_major() 
	{
		return this.certificate_major;
	}
	public void setSend_unit(String send_unit) 
	{
		this.send_unit = send_unit;
	}
	/**
	 * 返回 资格证书签发单位
	 * @return
	 */
	public String getSend_unit() 
	{
		return this.send_unit;
	}
	public void setOut_date(java.util.Date out_date) 
	{
		this.out_date = out_date;
	}
	/**
	 * 返回 转出日期
	 * @return
	 */
	public java.util.Date getOut_date() 
	{
		return this.out_date;
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
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setIsregist(String isregist) 
	{
		this.isregist = isregist;
	}
	/**
	 * 返回 是否已注册
	 * @return
	 */
	public String getIsregist() 
	{
		return this.isregist;
	}
	public void setCertificate_regist_id(String certificate_regist_id) 
	{
		this.certificate_regist_id = certificate_regist_id;
	}
	/**
	 * 返回 注册证书编号
	 * @return
	 */
	public String getCertificate_regist_id() 
	{
		return this.certificate_regist_id;
	}
	public void setRegist_id(String regist_id) 
	{
		this.regist_id = regist_id;
	}
	/**
	 * 返回 注册号
	 * @return
	 */
	public String getRegist_id() 
	{
		return this.regist_id;
	}
	public void setGet_date(java.util.Date get_date) 
	{
		this.get_date = get_date;
	}
	/**
	 * 返回 注册证书发证日期
	 * @return
	 */
	public java.util.Date getGet_date() 
	{
		return this.get_date;
	}
	public void setLast_effectice_date(java.util.Date last_effectice_date) 
	{
		this.last_effectice_date = last_effectice_date;
	}
	/**
	 * 返回 注册证书有效日期
	 * @return
	 */
	public java.util.Date getLast_effectice_date() 
	{
		return this.last_effectice_date;
	}
	public void setRegist_major(String regist_major) 
	{
		this.regist_major = regist_major;
	}
	/**
	 * 返回 注册专业
	 * @return
	 */
	public String getRegist_major() 
	{
		return this.regist_major;
	}
	public void setSeal_id(String seal_id) 
	{
		this.seal_id = seal_id;
	}
	/**
	 * 返回 执业印章号
	 * @return
	 */
	public String getSeal_id() 
	{
		return this.seal_id;
	}
	public void setRegist_out_date(java.util.Date regist_out_date) 
	{
		this.regist_out_date = regist_out_date;
	}
	/**
	 * 返回 转出日期
	 * @return
	 */
	public java.util.Date getRegist_out_date() 
	{
		return this.regist_out_date;
	}
	public void setRegist_send_unit(String regist_send_unit) 
	{
		this.regist_send_unit = regist_send_unit;
	}
	/**
	 * 返回 注册证书发证单位
	 * @return
	 */
	public String getRegist_send_unit() 
	{
		return this.regist_send_unit;
	}
	public void setLasted_regist_type(String lasted_regist_type) 
	{
		this.lasted_regist_type = lasted_regist_type;
	}
	/**
	 * 返回 最新注册类别
	 * @return
	 */
	public String getLasted_regist_type() 
	{
		return this.lasted_regist_type;
	}
	public void setLasted_regist_date(java.util.Date lasted_regist_date) 
	{
		this.lasted_regist_date = lasted_regist_date;
	}
	/**
	 * 返回 最新注册日期
	 * @return
	 */
	public java.util.Date getLasted_regist_date() 
	{
		return this.lasted_regist_date;
	}
	public void setKeep_edu_status(String keep_edu_status) 
	{
		this.keep_edu_status = keep_edu_status;
	}
	/**
	 * 返回 继续教育完成情况
	 * @return
	 */
	public String getKeep_edu_status() 
	{
		return this.keep_edu_status;
	}
	public void setRegist_remark(String regist_remark) 
	{
		this.regist_remark = regist_remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRegist_remark() 
	{
		return this.regist_remark;
	}
	public void setRegist_attachment(String regist_attachment) 
	{
		this.regist_attachment = regist_attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getRegist_attachment() 
	{
		return this.regist_attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PersonalQualification)) 
		{
			return false;
		}
		PersonalQualification rhs = (PersonalQualification) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.account, rhs.account)
		.append(this.name, rhs.name)
		.append(this.sex, rhs.sex)
		.append(this.nation, rhs.nation)
		.append(this.ID_number, rhs.ID_number)
		.append(this.xl, rhs.xl)
		.append(this.graduation_school, rhs.graduation_school)
		.append(this.graduation_date, rhs.graduation_date)
		.append(this.positional_degree_one, rhs.positional_degree_one)
		.append(this.positional_major_one, rhs.positional_major_one)
		.append(this.positional_degree_two, rhs.positional_degree_two)
		.append(this.positional_major_two, rhs.positional_major_two)
		.append(this.positional_degree_three, rhs.positional_degree_three)
		.append(this.positional_major_three, rhs.positional_major_three)
		.append(this.certificate_type, rhs.certificate_type)
		.append(this.certificate_id, rhs.certificate_id)
		.append(this.certificate_date, rhs.certificate_date)
		.append(this.certified_id, rhs.certified_id)
		.append(this.certificate_major, rhs.certificate_major)
		.append(this.send_unit, rhs.send_unit)
		.append(this.out_date, rhs.out_date)
		.append(this.remark, rhs.remark)
		.append(this.attachment, rhs.attachment)
		.append(this.isregist, rhs.isregist)
		.append(this.certificate_regist_id, rhs.certificate_regist_id)
		.append(this.regist_id, rhs.regist_id)
		.append(this.get_date, rhs.get_date)
		.append(this.last_effectice_date, rhs.last_effectice_date)
		.append(this.regist_major, rhs.regist_major)
		.append(this.seal_id, rhs.seal_id)
		.append(this.regist_out_date, rhs.regist_out_date)
		.append(this.regist_send_unit, rhs.regist_send_unit)
		.append(this.lasted_regist_type, rhs.lasted_regist_type)
		.append(this.lasted_regist_date, rhs.lasted_regist_date)
		.append(this.keep_edu_status, rhs.keep_edu_status)
		.append(this.regist_remark, rhs.regist_remark)
		.append(this.regist_attachment, rhs.regist_attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.account) 
		.append(this.name) 
		.append(this.sex) 
		.append(this.nation) 
		.append(this.ID_number) 
		.append(this.xl) 
		.append(this.graduation_school) 
		.append(this.graduation_date) 
		.append(this.positional_degree_one) 
		.append(this.positional_major_one) 
		.append(this.positional_degree_two) 
		.append(this.positional_major_two) 
		.append(this.positional_degree_three) 
		.append(this.positional_major_three) 
		.append(this.certificate_type) 
		.append(this.certificate_id) 
		.append(this.certificate_date) 
		.append(this.certified_id) 
		.append(this.certificate_major) 
		.append(this.send_unit) 
		.append(this.out_date) 
		.append(this.remark) 
		.append(this.attachment) 
		.append(this.isregist) 
		.append(this.certificate_regist_id) 
		.append(this.regist_id) 
		.append(this.get_date) 
		.append(this.last_effectice_date) 
		.append(this.regist_major) 
		.append(this.seal_id) 
		.append(this.regist_out_date) 
		.append(this.regist_send_unit) 
		.append(this.lasted_regist_type) 
		.append(this.lasted_regist_date) 
		.append(this.keep_edu_status) 
		.append(this.regist_remark) 
		.append(this.regist_attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("account", this.account) 
		.append("name", this.name) 
		.append("sex", this.sex) 
		.append("nation", this.nation) 
		.append("ID_number", this.ID_number) 
		.append("xl", this.xl) 
		.append("graduation_school", this.graduation_school) 
		.append("graduation_date", this.graduation_date) 
		.append("positional_degree_one", this.positional_degree_one) 
		.append("positional_major_one", this.positional_major_one) 
		.append("positional_degree_two", this.positional_degree_two) 
		.append("positional_major_two", this.positional_major_two) 
		.append("positional_degree_three", this.positional_degree_three) 
		.append("positional_major_three", this.positional_major_three) 
		.append("certificate_type", this.certificate_type) 
		.append("certificate_id", this.certificate_id) 
		.append("certificate_date", this.certificate_date) 
		.append("certified_id", this.certified_id) 
		.append("certificate_major", this.certificate_major) 
		.append("send_unit", this.send_unit) 
		.append("out_date", this.out_date) 
		.append("remark", this.remark) 
		.append("attachment", this.attachment) 
		.append("isregist", this.isregist) 
		.append("certificate_regist_id", this.certificate_regist_id) 
		.append("regist_id", this.regist_id) 
		.append("get_date", this.get_date) 
		.append("last_effectice_date", this.last_effectice_date) 
		.append("regist_major", this.regist_major) 
		.append("seal_id", this.seal_id) 
		.append("regist_out_date", this.regist_out_date) 
		.append("regist_send_unit", this.regist_send_unit) 
		.append("lasted_regist_type", this.lasted_regist_type) 
		.append("lasted_regist_date", this.lasted_regist_date) 
		.append("keep_edu_status", this.keep_edu_status) 
		.append("regist_remark", this.regist_remark) 
		.append("regist_attachment", this.regist_attachment) 
		.toString();
	}
	public PersonalQualification() {
		super();
	}
	public Integer getSwitchs() {
		return switchs;
	}
	public void setSwitchs(Integer switchs) {
		this.switchs = switchs;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getBorrowerID() {
		return borrowerID;
	}
	public void setBorrowerID(String borrowerID) {
		this.borrowerID = borrowerID;
	}
	public java.util.Date getOcc_in_date() {
		return occ_in_date;
	}
	public void setOcc_in_date(java.util.Date occ_in_date) {
		this.occ_in_date = occ_in_date;
	}

}