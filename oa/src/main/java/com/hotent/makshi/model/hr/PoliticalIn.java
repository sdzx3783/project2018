package com.hotent.makshi.model.hr;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:党员转入 Model对象
 */
public class PoliticalIn extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *姓名
	 */
	protected String  user_name;
	/**
	 *用户id
	 */
	protected String  user_id;
	/**
	 *性别
	 */
	protected String  sex;
	/**
	 *出生年月
	 */
	protected String  birthday;
	/**
	 *照片
	 */
	protected String  pic;
	/**
	 *民族
	 */
	protected String  nation;
	/**
	 *籍贯
	 */
	protected String  home;
	/**
	 *婚姻状况
	 */
	protected String  marrige_status;
	/**
	 *入党时间
	 */
	protected String  join_date;
	/**
	 *转正时间
	 */
	protected String  beformal_date;
	/**
	 *参加工作时间
	 */
	protected String start_work_date;
	/**
	 *入党时所在支部
	 */
	protected String  join_branch;
	/**
	 *转正时所在支部
	 */
	protected String  beformal_branch;
	/**
	 *入党介绍人
	 */
	protected String  introducer;
	/**
	 *所在支部
	 */
	protected String  now_branch;
	/**
	 *进入党支部日期
	 */
	protected String  in_branch_date;
	/**
	 *现任党内职务
	 */
	protected String  job_in_branch;
	/**
	 *学历
	 */
	protected String  edu;
	/**
	 *学位
	 */
	protected String  degree;
	/**
	 *毕业院校
	 */
	protected String  college;
	/**
	 *专业
	 */
	protected String  major;
	/**
	 *所在部门
	 */
	protected String  department;
	/**
	 *组织关系所在地
	 */
	protected String  relative_address;
	/**
	 *户籍所在地
	 */
	protected String  home_address;
	/**
	 *现居住地
	 */
	protected String  living_address;
	/**
	 *身份证号码
	 */
	protected String  id_number;
	/**
	 *联系电话
	 */
	protected Long  connect_tel;
	/**
	 *QQ号码
	 */
	protected String  qq;
	/**
	 *党费缴纳金额
	 */
	protected String  pay_amount;
	/**
	 *微信号
	 */
	protected String  weixin;
	/**
	 *党费缴纳至
	 */
	protected String  pay_date;
	/**
	 *是否转入
	 */
	protected String  is_in;
	/**
	 *是否转出
	 */
	protected String  is_out;
	/**
	 * 转出日期
	 * @return
	 */
	protected java.util.Date out_date;
	/**
	 * 转出日期
	 * @return
	 */
	protected java.util.Date in_date;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public java.util.Date getIn_date() {
		return in_date;
	}
	public void setIn_date(java.util.Date in_date) {
		this.in_date = in_date;
	}
	public java.util.Date getOut_date() {
		return out_date;
	}
	public void setOut_date(java.util.Date out_date) {
		this.out_date = out_date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
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
	public void setBirthday(String birthday) 
	{
		this.birthday = birthday;
	}
	/**
	 * 返回 出生年月
	 * @return
	 */
	public String getBirthday() 
	{
		return this.birthday;
	}
	public void setPic(String pic) 
	{
		this.pic = pic;
	}
	/**
	 * 返回 照片
	 * @return
	 */
	public String getPic() 
	{
		return this.pic;
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
	public void setHome(String home) 
	{
		this.home = home;
	}
	/**
	 * 返回 籍贯
	 * @return
	 */
	public String getHome() 
	{
		return this.home;
	}
	public void setMarrige_status(String marrige_status) 
	{
		this.marrige_status = marrige_status;
	}
	/**
	 * 返回 婚姻状况
	 * @return
	 */
	public String getMarrige_status() 
	{
		return this.marrige_status;
	}
	public void setJoin_date(String join_date) 
	{
		this.join_date = join_date;
	}
	/**
	 * 返回 入党时间
	 * @return
	 */
	public String getJoin_date() 
	{
		return this.join_date;
	}
	public void setBeformal_date(String beformal_date) 
	{
		this.beformal_date = beformal_date;
	}
	/**
	 * 返回 转正时间
	 * @return
	 */
	public String getBeformal_date() 
	{
		return this.beformal_date;
	}
	public void setStart_work_date(String start_work_date) 
	{
		this.start_work_date = start_work_date;
	}
	/**
	 * 返回 参加工作时间
	 * @return
	 */
	public String getStart_work_date() 
	{
		return this.start_work_date;
	}
	public void setJoin_branch(String join_branch) 
	{
		this.join_branch = join_branch;
	}
	/**
	 * 返回 入党时所在支部
	 * @return
	 */
	public String getJoin_branch() 
	{
		return this.join_branch;
	}
	public void setBeformal_branch(String beformal_branch) 
	{
		this.beformal_branch = beformal_branch;
	}
	/**
	 * 返回 转正时所在支部
	 * @return
	 */
	public String getBeformal_branch() 
	{
		return this.beformal_branch;
	}
	public void setIntroducer(String introducer) 
	{
		this.introducer = introducer;
	}
	/**
	 * 返回 入党介绍人
	 * @return
	 */
	public String getIntroducer() 
	{
		return this.introducer;
	}
	public void setNow_branch(String now_branch) 
	{
		this.now_branch = now_branch;
	}
	/**
	 * 返回 所在支部
	 * @return
	 */
	public String getNow_branch() 
	{
		return this.now_branch;
	}
	public void setIn_branch_date(String in_branch_date) 
	{
		this.in_branch_date = in_branch_date;
	}
	/**
	 * 返回 进入党支部日期
	 * @return
	 */
	public String getIn_branch_date() 
	{
		return this.in_branch_date;
	}
	public void setJob_in_branch(String job_in_branch) 
	{
		this.job_in_branch = job_in_branch;
	}
	/**
	 * 返回 现任党内职务
	 * @return
	 */
	public String getJob_in_branch() 
	{
		return this.job_in_branch;
	}
	public void setEdu(String edu) 
	{
		this.edu = edu;
	}
	/**
	 * 返回 学历
	 * @return
	 */
	public String getEdu() 
	{
		return this.edu;
	}
	public void setDegree(String degree) 
	{
		this.degree = degree;
	}
	/**
	 * 返回 学位
	 * @return
	 */
	public String getDegree() 
	{
		return this.degree;
	}
	public void setCollege(String college) 
	{
		this.college = college;
	}
	/**
	 * 返回 毕业院校
	 * @return
	 */
	public String getCollege() 
	{
		return this.college;
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
	 * 返回 所在部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setRelative_address(String relative_address) 
	{
		this.relative_address = relative_address;
	}
	/**
	 * 返回 组织关系所在地
	 * @return
	 */
	public String getRelative_address() 
	{
		return this.relative_address;
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
	public void setLiving_address(String living_address) 
	{
		this.living_address = living_address;
	}
	/**
	 * 返回 现居住地
	 * @return
	 */
	public String getLiving_address() 
	{
		return this.living_address;
	}
	public void setId_number(String id_number) 
	{
		this.id_number = id_number;
	}
	/**
	 * 返回 身份证号码
	 * @return
	 */
	public String getId_number() 
	{
		return this.id_number;
	}
	public void setConnect_tel(Long connect_tel) 
	{
		this.connect_tel = connect_tel;
	}
	/**
	 * 返回 联系电话
	 * @return
	 */
	public Long getConnect_tel() 
	{
		return this.connect_tel;
	}
	public void setQq(String qq) 
	{
		this.qq = qq;
	}
	/**
	 * 返回 QQ号码
	 * @return
	 */
	public String getQq() 
	{
		return this.qq;
	}
	public void setPay_amount(String pay_amount) 
	{
		this.pay_amount = pay_amount;
	}
	/**
	 * 返回 党费缴纳金额
	 * @return
	 */
	public String getPay_amount() 
	{
		return this.pay_amount;
	}
	public void setWeixin(String weixin) 
	{
		this.weixin = weixin;
	}
	/**
	 * 返回 微信号
	 * @return
	 */
	public String getWeixin() 
	{
		return this.weixin;
	}
	public void setPay_date(String pay_date) 
	{
		this.pay_date = pay_date;
	}
	/**
	 * 返回 党费缴纳至
	 * @return
	 */
	public String getPay_date() 
	{
		return this.pay_date;
	}
	
	
	public String getIs_out() {
		return is_out;
	}
	public void setIs_out(String is_out) {
		this.is_out = is_out;
	}
	public void setIs_in(String is_in) 
	{
		this.is_in = is_in;
	}
	/**
	 * 返回 是否转入
	 * @return
	 */
	public String getIs_in() 
	{
		return this.is_in;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PoliticalIn)) 
		{
			return false;
		}
		PoliticalIn rhs = (PoliticalIn) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_name, rhs.user_name)
		.append(this.sex, rhs.sex)
		.append(this.birthday, rhs.birthday)
		.append(this.pic, rhs.pic)
		.append(this.nation, rhs.nation)
		.append(this.home, rhs.home)
		.append(this.marrige_status, rhs.marrige_status)
		.append(this.join_date, rhs.join_date)
		.append(this.beformal_date, rhs.beformal_date)
		.append(this.start_work_date, rhs.start_work_date)
		.append(this.join_branch, rhs.join_branch)
		.append(this.beformal_branch, rhs.beformal_branch)
		.append(this.introducer, rhs.introducer)
		.append(this.now_branch, rhs.now_branch)
		.append(this.in_branch_date, rhs.in_branch_date)
		.append(this.job_in_branch, rhs.job_in_branch)
		.append(this.edu, rhs.edu)
		.append(this.degree, rhs.degree)
		.append(this.college, rhs.college)
		.append(this.major, rhs.major)
		.append(this.department, rhs.department)
		.append(this.relative_address, rhs.relative_address)
		.append(this.home_address, rhs.home_address)
		.append(this.living_address, rhs.living_address)
		.append(this.id_number, rhs.id_number)
		.append(this.connect_tel, rhs.connect_tel)
		.append(this.qq, rhs.qq)
		.append(this.pay_amount, rhs.pay_amount)
		.append(this.weixin, rhs.weixin)
		.append(this.pay_date, rhs.pay_date)
		.append(this.is_in, rhs.is_in)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_name) 
		.append(this.sex) 
		.append(this.birthday) 
		.append(this.pic) 
		.append(this.nation) 
		.append(this.home) 
		.append(this.marrige_status) 
		.append(this.join_date) 
		.append(this.beformal_date) 
		.append(this.start_work_date) 
		.append(this.join_branch) 
		.append(this.beformal_branch) 
		.append(this.introducer) 
		.append(this.now_branch) 
		.append(this.in_branch_date) 
		.append(this.job_in_branch) 
		.append(this.edu) 
		.append(this.degree) 
		.append(this.college) 
		.append(this.major) 
		.append(this.department) 
		.append(this.relative_address) 
		.append(this.home_address) 
		.append(this.living_address) 
		.append(this.id_number) 
		.append(this.connect_tel) 
		.append(this.qq) 
		.append(this.pay_amount) 
		.append(this.weixin) 
		.append(this.pay_date) 
		.append(this.is_in) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_name", this.user_name) 
		.append("sex", this.sex) 
		.append("birthday", this.birthday) 
		.append("pic", this.pic) 
		.append("nation", this.nation) 
		.append("home", this.home) 
		.append("marrige_status", this.marrige_status) 
		.append("join_date", this.join_date) 
		.append("beformal_date", this.beformal_date) 
		.append("start_work_date", this.start_work_date) 
		.append("join_branch", this.join_branch) 
		.append("beformal_branch", this.beformal_branch) 
		.append("introducer", this.introducer) 
		.append("now_branch", this.now_branch) 
		.append("in_branch_date", this.in_branch_date) 
		.append("job_in_branch", this.job_in_branch) 
		.append("edu", this.edu) 
		.append("degree", this.degree) 
		.append("college", this.college) 
		.append("major", this.major) 
		.append("department", this.department) 
		.append("relative_address", this.relative_address) 
		.append("home_address", this.home_address) 
		.append("living_address", this.living_address) 
		.append("id_number", this.id_number) 
		.append("connect_tel", this.connect_tel) 
		.append("qq", this.qq) 
		.append("pay_amount", this.pay_amount) 
		.append("weixin", this.weixin) 
		.append("pay_date", this.pay_date) 
		.append("is_in", this.is_in) 
		.toString();
	}

}