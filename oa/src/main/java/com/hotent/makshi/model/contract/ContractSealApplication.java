package com.hotent.makshi.model.contract;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:合同盖章申请 Model对象
 */
public class ContractSealApplication extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1817453025808271309L;
	//主键
	protected Long id;
	/**
	 *合同归属部门ID
	 */
	protected String  contract_departmentID;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	
	/**
	 *项目审批人ID
	 */
	protected String  project_administratorID;
	/**
	 *项目审批人
	 */
	protected String  project_administrator;
	
	/**
	 *项目总监
	 */
	protected String  project_director;
	protected String  project_directorID;
	/**
	 *合同类型
	 */
	protected String  contracttype;
	/**
	 *合同归属部门
	 */
	protected String  contract_department;
	/**
	 *甲方
	 */
	protected String  first_party;
	/**
	 *投资总额（万元）
	 */
	protected String  total_investment;
	/**
	 *乙方
	 */
	protected String  second_party;
	/**
	 *合同金额（万元）
	 */
	protected String  contract_money;
	/**
	 *签约时间
	 */
	protected java.util.Date  signing_time;
	/**
	 *结算金额（万元）
	 */
	protected String  settlement_money;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *序号
	 */
	protected String  no;
	/**
	 *合同类型
	 */
	protected String  type;
	/**
	 *第三级合同类型
	 */
	protected String  thirdcontracttype;
	
	/**
	 *工程标底价
	 */
	protected String  project_bid_floorprice;
	/**
	 *合同状态
	 */
	protected String  contract_status;
	/**
	 *工程中标价
	 */
	protected String  project_bid_price;
	/**
	 *费率
	 */
	protected String  rate;
	/**
	 *项目内容
	 */
	protected String  project_content;
	/**
	 *付费类型
	 */
	protected String  payment_type;
	/**
	 *项目规模
	 */
	protected String  project_scale;
	/**
	 *项目是否备案
	 */
	protected String  isrecord;
	/**
	 *竣工子资料是否存档
	 */
	protected String  issave;
	/**
	 *项目状态
	 */
	protected String  project_status;
	/**
	 *合同是否收回
	 */
	protected String  isrecovery;
	/**
	 *系统内外
	 */
	protected String  inout;
	/**
	 *工程地址
	 */
	protected String  project_place;
	/**
	 *开工时间
	 */
	protected java.util.Date  start_time;
	/**
	 *业主名称
	 */
	protected String  owner_name;
	/**
	 *完工时间
	 */
	protected java.util.Date  end_time;
	/**
	 *项目由来
	 */
	protected String  project_origin;
	/**
	 *项目负责人
	 */
	protected String  project_leader;
	protected String  project_leaderID;
	/**
	 *招标平台
	 */
	protected String  bidding_platform;
	/**
	 *合同经手人
	 */
	protected String  contract_handler;
	protected String  contract_handlerID;
	/**
	 *招标方式
	 */
	protected String  bidding_method;
	/**
	 *合同审核人
	 */
	protected String  contract_reviewer;
	protected String  contract_reviewerID;
	/**
	 *合同批签人
	 */
	protected String  contract_authorized_person;
	protected String  contract_authorized_personID;
	/**
	 *修改人
	 */
	protected String  updater;
	protected String  updaterID;
	/**
	 *创建人
	 */
	protected String  creater;
	protected String  createrID;
	/**
	 *修改时间
	 */
	protected java.util.Date  update_time;
	/**
	 *创建时间
	 */
	protected java.util.Date  create_time;
	/**
	 *保管人
	 */
	protected String  custodian;
	protected String  custodianID;
	/**
	 *进度
	 */
	protected String  progress;
	/**
	 * 项目名称
	 */
	protected String  projiect_name;
	/**
	 * 标段名称
	 */
	protected String  sections_name;
	/**
	 * 
	 *附件
	 */
	protected String  file;
	
	protected String  file_second;
	
	protected String  file_third;
	
	protected String  invoice_amount;
	
	
	
	protected String processStatus;
	
	protected Long  runId=0L;
	
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	protected String haveProjectChapter;
	
	protected Integer file_copies;
	
	protected Integer fj_second_copies;
	
	
	public String getHaveProjectChapter() {
		return haveProjectChapter;
	}
	public void setHaveProjectChapter(String haveProjectChapter) {
		this.haveProjectChapter = haveProjectChapter;
	}
	public Integer getFile_copies() {
		return file_copies;
	}
	public void setFile_copies(Integer file_copies) {
		this.file_copies = file_copies;
	}
	public Integer getFj_second_copies() {
		return fj_second_copies;
	}
	public void setFj_second_copies(Integer fj_second_copies) {
		this.fj_second_copies = fj_second_copies;
	}
	public String getThirdcontracttype() {
		return thirdcontracttype;
	}
	public void setThirdcontracttype(String thirdcontracttype) {
		this.thirdcontracttype = thirdcontracttype;
	}
	public String getProject_directorID() {
		return project_directorID;
	}
	public void setProject_directorID(String project_directorID) {
		this.project_directorID = project_directorID;
	}
	public String getProject_leaderID() {
		return project_leaderID;
	}
	public void setProject_leaderID(String project_leaderID) {
		this.project_leaderID = project_leaderID;
	}
	public String getContract_handlerID() {
		return contract_handlerID;
	}
	public void setContract_handlerID(String contract_handlerID) {
		this.contract_handlerID = contract_handlerID;
	}
	public String getContract_reviewerID() {
		return contract_reviewerID;
	}
	public void setContract_reviewerID(String contract_reviewerID) {
		this.contract_reviewerID = contract_reviewerID;
	}
	public String getContract_authorized_personID() {
		return contract_authorized_personID;
	}
	public void setContract_authorized_personID(String contract_authorized_personID) {
		this.contract_authorized_personID = contract_authorized_personID;
	}
	public String getUpdaterID() {
		return updaterID;
	}
	public void setUpdaterID(String updaterID) {
		this.updaterID = updaterID;
	}
	public String getCreaterID() {
		return createrID;
	}
	public void setCreaterID(String createrID) {
		this.createrID = createrID;
	}
	public String getCustodianID() {
		return custodianID;
	}
	public void setCustodianID(String custodianID) {
		this.custodianID = custodianID;
	}
	public String getProjiect_name() {
		return projiect_name;
	}
	public void setProjiect_name(String projiect_name) {
		this.projiect_name = projiect_name;
	}
	public String getSections_name() {
		return sections_name;
	}
	public void setSections_name(String sections_name) {
		this.sections_name = sections_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setContract_departmentID(String contract_departmentID) 
	{
		this.contract_departmentID = contract_departmentID;
	}
	/**
	 * 返回 合同归属部门ID
	 * @return
	 */
	public String getContract_departmentID() 
	{
		return this.contract_departmentID;
	}
	public void setContract_name(String contract_name) 
	{
		this.contract_name = contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getContract_name() 
	{
		return this.contract_name;
	}
	public void setProject_director(String project_director) 
	{
		this.project_director = project_director;
	}
	/**
	 * 返回 项目总监
	 * @return
	 */
	public String getProject_director() 
	{
		return this.project_director;
	}
	
	public String getContracttype() {
		return contracttype;
	}
	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}
	public void setContract_department(String contract_department) 
	{
		this.contract_department = contract_department;
	}
	/**
	 * 返回 合同归属部门
	 * @return
	 */
	public String getContract_department() 
	{
		return this.contract_department;
	}
	public void setFirst_party(String first_party) 
	{
		this.first_party = first_party;
	}
	/**
	 * 返回 甲方
	 * @return
	 */
	public String getFirst_party() 
	{
		return this.first_party;
	}
	public void setTotal_investment(String total_investment) 
	{
		this.total_investment = total_investment;
	}
	/**
	 * 返回 投资总额（万元）
	 * @return
	 */
	public String getTotal_investment() 
	{
		return this.total_investment;
	}
	public void setSecond_party(String second_party) 
	{
		this.second_party = second_party;
	}
	/**
	 * 返回 乙方
	 * @return
	 */
	public String getSecond_party() 
	{
		return this.second_party;
	}
	public void setContract_money(String contract_money) 
	{
		this.contract_money = contract_money;
	}
	/**
	 * 返回 合同金额（万元）
	 * @return
	 */
	public String getContract_money() 
	{
		return this.contract_money;
	}
	public void setSigning_time(java.util.Date signing_time) 
	{
		this.signing_time = signing_time;
	}
	/**
	 * 返回 签约时间
	 * @return
	 */
	public java.util.Date getSigning_time() 
	{
		return this.signing_time;
	}
	public void setSettlement_money(String settlement_money) 
	{
		this.settlement_money = settlement_money;
	}
	/**
	 * 返回 结算金额（万元）
	 * @return
	 */
	public String getSettlement_money() 
	{
		return this.settlement_money;
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
	public void setContract_num(String contract_num) 
	{
		this.contract_num = contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getContract_num() 
	{
		return this.contract_num;
	}
	public void setNo(String no) 
	{
		this.no = no;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getNo() 
	{
		return this.no;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 合同类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setProject_bid_floorprice(String project_bid_floorprice) 
	{
		this.project_bid_floorprice = project_bid_floorprice;
	}
	/**
	 * 返回 工程标底价
	 * @return
	 */
	public String getProject_bid_floorprice() 
	{
		return this.project_bid_floorprice;
	}
	public void setContract_status(String contract_status) 
	{
		this.contract_status = contract_status;
	}
	/**
	 * 返回 合同状态
	 * @return
	 */
	public String getContract_status() 
	{
		return this.contract_status;
	}
	public void setProject_bid_price(String project_bid_price) 
	{
		this.project_bid_price = project_bid_price;
	}
	/**
	 * 返回 工程中标价
	 * @return
	 */
	public String getProject_bid_price() 
	{
		return this.project_bid_price;
	}
	public void setRate(String rate) 
	{
		this.rate = rate;
	}
	/**
	 * 返回 费率
	 * @return
	 */
	public String getRate() 
	{
		return this.rate;
	}
	public void setProject_content(String project_content) 
	{
		this.project_content = project_content;
	}
	/**
	 * 返回 项目内容
	 * @return
	 */
	public String getProject_content() 
	{
		return this.project_content;
	}
	public void setPayment_type(String payment_type) 
	{
		this.payment_type = payment_type;
	}
	/**
	 * 返回 付费类型
	 * @return
	 */
	public String getPayment_type() 
	{
		return this.payment_type;
	}
	public void setProject_scale(String project_scale) 
	{
		this.project_scale = project_scale;
	}
	/**
	 * 返回 项目规模
	 * @return
	 */
	public String getProject_scale() 
	{
		return this.project_scale;
	}
	public void setIsrecord(String isrecord) 
	{
		this.isrecord = isrecord;
	}
	/**
	 * 返回 项目是否备案
	 * @return
	 */
	public String getIsrecord() 
	{
		return this.isrecord;
	}
	public void setIssave(String issave) 
	{
		this.issave = issave;
	}
	/**
	 * 返回 竣工子资料是否存档
	 * @return
	 */
	public String getIssave() 
	{
		return this.issave;
	}
	public void setProject_status(String project_status) 
	{
		this.project_status = project_status;
	}
	/**
	 * 返回 项目状态
	 * @return
	 */
	public String getProject_status() 
	{
		return this.project_status;
	}
	public void setIsrecovery(String isrecovery) 
	{
		this.isrecovery = isrecovery;
	}
	/**
	 * 返回 合同是否收回
	 * @return
	 */
	public String getIsrecovery() 
	{
		return this.isrecovery;
	}
	public void setInout(String inout) 
	{
		this.inout = inout;
	}
	/**
	 * 返回 系统内外
	 * @return
	 */
	public String getInout() 
	{
		return this.inout;
	}
	public void setProject_place(String project_place) 
	{
		this.project_place = project_place;
	}
	/**
	 * 返回 工程地址
	 * @return
	 */
	public String getProject_place() 
	{
		return this.project_place;
	}
	public void setStart_time(java.util.Date start_time) 
	{
		this.start_time = start_time;
	}
	/**
	 * 返回 开工时间
	 * @return
	 */
	public java.util.Date getStart_time() 
	{
		return this.start_time;
	}
	public void setOwner_name(String owner_name) 
	{
		this.owner_name = owner_name;
	}
	/**
	 * 返回 业主名称
	 * @return
	 */
	public String getOwner_name() 
	{
		return this.owner_name;
	}
	public void setEnd_time(java.util.Date end_time) 
	{
		this.end_time = end_time;
	}
	/**
	 * 返回 完工时间
	 * @return
	 */
	public java.util.Date getEnd_time() 
	{
		return this.end_time;
	}
	public void setProject_origin(String project_origin) 
	{
		this.project_origin = project_origin;
	}
	/**
	 * 返回 项目由来
	 * @return
	 */
	public String getProject_origin() 
	{
		return this.project_origin;
	}
	public void setProject_leader(String project_leader) 
	{
		this.project_leader = project_leader;
	}
	/**
	 * 返回 项目负责人
	 * @return
	 */
	public String getProject_leader() 
	{
		return this.project_leader;
	}
	public void setBidding_platform(String bidding_platform) 
	{
		this.bidding_platform = bidding_platform;
	}
	/**
	 * 返回 招标平台
	 * @return
	 */
	public String getBidding_platform() 
	{
		return this.bidding_platform;
	}
	public void setContract_handler(String contract_handler) 
	{
		this.contract_handler = contract_handler;
	}
	/**
	 * 返回 合同经手人
	 * @return
	 */
	public String getContract_handler() 
	{
		return this.contract_handler;
	}
	public void setBidding_method(String bidding_method) 
	{
		this.bidding_method = bidding_method;
	}
	/**
	 * 返回 招标方式
	 * @return
	 */
	public String getBidding_method() 
	{
		return this.bidding_method;
	}
	public void setContract_reviewer(String contract_reviewer) 
	{
		this.contract_reviewer = contract_reviewer;
	}
	/**
	 * 返回 合同审核人
	 * @return
	 */
	public String getContract_reviewer() 
	{
		return this.contract_reviewer;
	}
	public void setContract_authorized_person(String contract_authorized_person) 
	{
		this.contract_authorized_person = contract_authorized_person;
	}
	/**
	 * 返回 合同批签人
	 * @return
	 */
	public String getContract_authorized_person() 
	{
		return this.contract_authorized_person;
	}
	public void setUpdater(String updater) 
	{
		this.updater = updater;
	}
	/**
	 * 返回 修改人
	 * @return
	 */
	public String getUpdater() 
	{
		return this.updater;
	}
	public void setCreater(String creater) 
	{
		this.creater = creater;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreater() 
	{
		return this.creater;
	}
	public void setUpdate_time(Date update_time) 
	{
		this.update_time = update_time;
	}
	/**
	 * 返回 修改时间
	 * @return
	 */
	public Date getUpdate_time() 
	{
		return this.update_time;
	}
	public void setCreate_time(Date create_time) 
	{
		this.create_time = create_time;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public Date getCreate_time() 
	{
		return this.create_time;
	}
	public void setCustodian(String custodian) 
	{
		this.custodian = custodian;
	}
	/**
	 * 返回 保管人
	 * @return
	 */
	public String getCustodian() 
	{
		return this.custodian;
	}
	public void setProgress(String progress) 
	{
		this.progress = progress;
	}
	/**
	 * 返回 进度
	 * @return
	 */
	public String getProgress() 
	{
		return this.progress;
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
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractSealApplication)) 
		{
			return false;
		}
		ContractSealApplication rhs = (ContractSealApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.contract_departmentID, rhs.contract_departmentID)
		.append(this.contract_name, rhs.contract_name)
		.append(this.project_director, rhs.project_director)
		.append(this.contracttype, rhs.contracttype)
		.append(this.thirdcontracttype, rhs.thirdcontracttype)
		.append(this.contract_department, rhs.contract_department)
		.append(this.first_party, rhs.first_party)
		.append(this.total_investment, rhs.total_investment)
		.append(this.second_party, rhs.second_party)
		.append(this.contract_money, rhs.contract_money)
		.append(this.signing_time, rhs.signing_time)
		.append(this.settlement_money, rhs.settlement_money)
		.append(this.remark, rhs.remark)
		.append(this.contract_num, rhs.contract_num)
		.append(this.no, rhs.no)
		.append(this.type, rhs.type)
		.append(this.project_bid_floorprice, rhs.project_bid_floorprice)
		.append(this.contract_status, rhs.contract_status)
		.append(this.project_bid_price, rhs.project_bid_price)
		.append(this.rate, rhs.rate)
		.append(this.project_content, rhs.project_content)
		.append(this.payment_type, rhs.payment_type)
		.append(this.project_scale, rhs.project_scale)
		.append(this.isrecord, rhs.isrecord)
		.append(this.issave, rhs.issave)
		.append(this.project_status, rhs.project_status)
		.append(this.isrecovery, rhs.isrecovery)
		.append(this.inout, rhs.inout)
		.append(this.project_place, rhs.project_place)
		.append(this.start_time, rhs.start_time)
		.append(this.owner_name, rhs.owner_name)
		.append(this.end_time, rhs.end_time)
		.append(this.project_origin, rhs.project_origin)
		.append(this.project_leader, rhs.project_leader)
		.append(this.bidding_platform, rhs.bidding_platform)
		.append(this.contract_handler, rhs.contract_handler)
		.append(this.bidding_method, rhs.bidding_method)
		.append(this.contract_reviewer, rhs.contract_reviewer)
		.append(this.contract_authorized_person, rhs.contract_authorized_person)
		.append(this.updater, rhs.updater)
		.append(this.creater, rhs.creater)
		.append(this.update_time, rhs.update_time)
		.append(this.create_time, rhs.create_time)
		.append(this.custodian, rhs.custodian)
		.append(this.progress, rhs.progress)
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
		.append(this.contract_departmentID) 
		.append(this.contract_name) 
		.append(this.project_director) 
		.append(this.contracttype) 
		.append(this.thirdcontracttype)
		.append(this.contract_department) 
		.append(this.first_party) 
		.append(this.total_investment) 
		.append(this.second_party) 
		.append(this.contract_money) 
		.append(this.signing_time) 
		.append(this.settlement_money) 
		.append(this.remark) 
		.append(this.contract_num) 
		.append(this.no) 
		.append(this.type) 
		.append(this.project_bid_floorprice) 
		.append(this.contract_status) 
		.append(this.project_bid_price) 
		.append(this.rate) 
		.append(this.project_content) 
		.append(this.payment_type) 
		.append(this.project_scale) 
		.append(this.isrecord) 
		.append(this.issave) 
		.append(this.project_status) 
		.append(this.isrecovery) 
		.append(this.inout) 
		.append(this.project_place) 
		.append(this.start_time) 
		.append(this.owner_name) 
		.append(this.end_time) 
		.append(this.project_origin) 
		.append(this.project_leader) 
		.append(this.bidding_platform) 
		.append(this.contract_handler) 
		.append(this.bidding_method) 
		.append(this.contract_reviewer) 
		.append(this.contract_authorized_person) 
		.append(this.updater) 
		.append(this.creater) 
		.append(this.update_time) 
		.append(this.create_time) 
		.append(this.custodian) 
		.append(this.progress) 
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
		.append("contract_departmentID", this.contract_departmentID) 
		.append("contract_name", this.contract_name) 
		.append("project_director", this.project_director) 
		.append("contract_type", this.contracttype) 
		.append("thirdcontracttype", this.thirdcontracttype)
		.append("contract_department", this.contract_department) 
		.append("first_party", this.first_party) 
		.append("total_investment", this.total_investment) 
		.append("second_party", this.second_party) 
		.append("contract_money", this.contract_money) 
		.append("signing_time", this.signing_time) 
		.append("settlement_money", this.settlement_money) 
		.append("remark", this.remark) 
		.append("contract_num", this.contract_num) 
		.append("no", this.no) 
		.append("type", this.type) 
		.append("project_bid_floorprice", this.project_bid_floorprice) 
		.append("contract_status", this.contract_status) 
		.append("project_bid_price", this.project_bid_price) 
		.append("rate", this.rate) 
		.append("project_content", this.project_content) 
		.append("payment_type", this.payment_type) 
		.append("project_scale", this.project_scale) 
		.append("isrecord", this.isrecord) 
		.append("issave", this.issave) 
		.append("project_status", this.project_status) 
		.append("isrecovery", this.isrecovery) 
		.append("inout", this.inout) 
		.append("project_place", this.project_place) 
		.append("start_time", this.start_time) 
		.append("owner_name", this.owner_name) 
		.append("end_time", this.end_time) 
		.append("project_origin", this.project_origin) 
		.append("project_leader", this.project_leader) 
		.append("bidding_platform", this.bidding_platform) 
		.append("contract_handler", this.contract_handler) 
		.append("bidding_method", this.bidding_method) 
		.append("contract_reviewer", this.contract_reviewer) 
		.append("contract_authorized_person", this.contract_authorized_person) 
		.append("updater", this.updater) 
		.append("creater", this.creater) 
		.append("update_time", this.update_time) 
		.append("create_time", this.create_time) 
		.append("custodian", this.custodian) 
		.append("progress", this.progress) 
		.append("file", this.file) 
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
	public java.util.Date getCreateTime() {
		return createTime;
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
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
	public String getProject_administratorID() {
		return project_administratorID;
	}
	public void setProject_administratorID(String project_administratorID) {
		this.project_administratorID = project_administratorID;
	}
	public String getProject_administrator() {
		return project_administrator;
	}
	public void setProject_administrator(String project_administrator) {
		this.project_administrator = project_administrator;
	}
	public String getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getFile_second() {
		return file_second;
	}
	public void setFile_second(String file_second) {
		this.file_second = file_second;
	}
	public String getFile_third() {
		return file_third;
	}
	public void setFile_third(String file_third) {
		this.file_third = file_third;
	}

	
}