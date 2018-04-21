package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:合同基本信息 Model对象
 */
/**
 * @author zami
 *
 */
public class Contractinfo extends BaseModel
{
	public static final String REGEX = "([a-zA-Z]{2,4}[1,2][0-9]{3}[-]\\d+(|([\\（][\\u4E00-\u9FA5_a-zA-Z0-9]+[\\）])|([-]\\d+|[-]\\d)+(|([\\（][\u4e00-\u9fa5_a-zA-Z0-9]+[\\）]))))";
	
	//主键
	protected Long id;
	/**
	 *合同归属部门ID
	 */
	protected String  contract_departmentID;
	/**
	 *类型
	 */
	protected String  contracttype;
	/**
	 *合同类型
	 */
	protected String  contract_type;
	/**
	 *第三级合同类型
	 */
	protected String  thirdcontracttype;
	/**
	 *合同类型
	 */
	protected String  type;
	/**
	 *合同金额（万元）
	 */
	protected Double  contract_money;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *序号
	 */
	protected String  no;
	/**
	 *结算金额（万元）
	 */
	protected Double  settlement_money;
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
	 *工程标底价
	 */
	protected Double  project_bid_floorprice;
	/**
	 *合同状态
	 */
	protected String  contract_status;
	/**
	 *工程中标价
	 */
	protected Double  project_bid_price;
	/**
	 *甲方
	 */
	protected String  first_party;
	/**
	 *费率
	 */
	protected String  rate;
	/**
	 *乙方
	 */
	protected String  second_party;
	/**
	 *项目内容
	 */
	protected String  project_content;
	/**
	 *付费类型
	 */
	protected String  payment_type;
/*	*//**
	 *签约时间
	 *//*
	protected java.util.Date  signing_time;*/
	/**
	 *项目规模
	 */
	protected String  project_scale;
	/**
	 *签约时间
	 */
	protected java.util.Date  singing_time;
	/**
	 *项目是否备案
	 */
	protected String  isrecord;
	/**
	 *项目总监
	 */
	protected String  project_director;
	protected String  project_directorID;
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
	 *合同归属部门
	 */
	protected String  contract_department;
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
	 *备注
	 */
	protected String  remark;
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
	protected java.util.Date   update_time;
	/**
	 *创建时间
	 */
	protected java.util.Date   create_time;
	/**
	 *保管人
	 */
	protected String  custodian;
	protected String  custodianID;
	/**
	 *投资总额（万元）
	 */
	protected Double  total_investment;
	/**
	 *进度
	 */
	protected String  progress;
	/**
	 *开票金额
	 */
	protected Double  invoice_amount;
	/**
	 *开票时间
	 */
	protected java.util.Date  billing_time;
	/**
	 *到账金额
	 */
	protected Double  arrival_amount;
	/**
	 *到账时间
	 */
	protected java.util.Date  arrival_time;
	/**
	 *附件
	 */
	protected String  file;
	
	protected String  file_second;
	
	protected String  file_third;
	/**
	 * 项目名称
	 */
	protected String  projiect_name;
	/**
	 * 标段名称
	 */
	protected String  sections_name;
	/**
	 *合同基本信息列表
	 */
	protected List<ContractBillingRecord> contractBillingRecordList=new ArrayList<ContractBillingRecord>();
	/**
	 *合同基本信息列表
	 */
	protected List<ContractPaymentRecord> contractPaymentRecordList=new ArrayList<ContractPaymentRecord>();
	
	/**
	 * 项目部
	 * @return
	 */
	protected String project_department;
	/**
	 * 月产值
	 * @return
	 */
	protected String outPut;
	/**
	 * 归档日期
	 * @return
	 */
	protected Date instock_date;
	
	/**
	 * 添加排序字段：年份、编号、子编号
	 * @return
	 */
	protected Integer yearSort;
	
	protected Integer numSort;
	
	protected Integer secondSort;
	
	/**
	 * 新增字段 签收人1，签收人2，份数1，份数2，是否有项目章
	 * @return
	 */
	protected String instock_sign;
	
	protected String sure_sign;
	
	protected Integer filecopies;
	
	protected Integer fj_sencond_copies;
	
	protected String project_chapter;
	
	protected String num_his;
	
	public String getNum_his() {
		return num_his;
	}
	public void setNum_his(String num_his) {
		this.num_his = num_his;
	}
	public String getProject_chapter() {
		return project_chapter;
	}
	public void setProject_chapter(String project_chapter) {
		this.project_chapter = project_chapter;
	}
	public String getInstock_sign() {
		return instock_sign;
	}
	public void setInstock_sign(String instock_sign) {
		this.instock_sign = instock_sign;
	}
	public String getSure_sign() {
		return sure_sign;
	}
	public void setSure_sign(String sure_sign) {
		this.sure_sign = sure_sign;
	}
	public Integer getFilecopies() {
		return filecopies;
	}
	public void setFilecopies(Integer filecopies) {
		this.filecopies = filecopies;
	}
	public Integer getFj_sencond_copies() {
		return fj_sencond_copies;
	}
	public void setFj_sencond_copies(Integer fj_sencond_copies) {
		this.fj_sencond_copies = fj_sencond_copies;
	}
	public Integer getYearSort() {
		return yearSort;
	}
	public void setYearSort(Integer yearSort) {
		this.yearSort = yearSort;
	}
	public Integer getNumSort() {
		return numSort;
	}
	public void setNumSort(Integer numSort) {
		this.numSort = numSort;
	}
	public Integer getSecondSort() {
		return secondSort;
	}
	public void setSecondSort(Integer secondSort) {
		this.secondSort = secondSort;
	}
	public String getThirdcontracttype() {
		return thirdcontracttype;
	}
	public void setThirdcontracttype(String thirdcontracttype) {
		this.thirdcontracttype = thirdcontracttype;
	}
	public Date getInstock_date() {
		return instock_date;
	}
	public void setInstock_date(Date instock_date) {
		this.instock_date = instock_date;
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
	public String getOutPut() {
		return outPut;
	}
	public void setOutPut(String outPut) {
		this.outPut = outPut;
	}
	public String getProject_department() {
		return project_department;
	}
	public void setProject_department(String project_department) {
		this.project_department = project_department;
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
	
	
	
	
	public String getContract_authorized_personID() {
		return contract_authorized_personID;
	}
	public void setContract_authorized_personID(String contract_authorized_personID) {
		this.contract_authorized_personID = contract_authorized_personID;
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
	/**
	 * 返回 合同归属部门ID
	 * @return
	 */
	public String getContract_departmentID() 
	{
		return this.contract_departmentID;
	}
	public void setContracttype(String contracttype) 
	{
		this.contracttype = contracttype;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public String getContracttype() 
	{
		return this.contracttype;
	}
	public void setContract_type(String contract_type) 
	{
		this.contract_type = contract_type;
	}
	/**
	 * 返回 合同类型
	 * @return
	 */
	public String getContract_type() 
	{
		return this.contract_type;
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
	public void setContract_money(Double contract_money) 
	{
		this.contract_money = contract_money;
	}
	/**
	 * 返回 合同金额（万元）
	 * @return
	 */
	public Double getContract_money() 
	{
		return this.contract_money;
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
	public void setSettlement_money(Double settlement_money) 
	{
		this.settlement_money = settlement_money;
	}
	/**
	 * 返回 结算金额（万元）
	 * @return
	 */
	public Double getSettlement_money() 
	{
		return this.settlement_money;
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
	public void setProject_bid_floorprice(Double project_bid_floorprice) 
	{
		this.project_bid_floorprice = project_bid_floorprice;
	}
	/**
	 * 返回 工程标底价
	 * @return
	 */
	public Double getProject_bid_floorprice() 
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
	public void setProject_bid_price(Double project_bid_price) 
	{
		this.project_bid_price = project_bid_price;
	}
	/**
	 * 返回 工程中标价
	 * @return
	 */
	public Double getProject_bid_price() 
	{
		return this.project_bid_price;
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
	public void setSinging_time(java.util.Date singing_time) 
	{
		this.singing_time = singing_time;
	}
	/**
	 * 返回 签约时间
	 * @return
	 */
	public java.util.Date getSinging_time() 
	{
		return this.singing_time;
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
	public void setTotal_investment(Double total_investment) 
	{
		this.total_investment = total_investment;
	}
	/**
	 * 返回 投资总额（万元）
	 * @return
	 */
	public Double getTotal_investment() 
	{
		return this.total_investment;
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
	public void setInvoice_amount(Double invoice_amount) 
	{
		this.invoice_amount = invoice_amount;
	}
	/**
	 * 返回 开票金额
	 * @return
	 */
	public Double getInvoice_amount() 
	{
		return this.invoice_amount;
	}
	public void setBilling_time(java.util.Date billing_time) 
	{
		this.billing_time = billing_time;
	}
	/**
	 * 返回 开票时间
	 * @return
	 */
	public java.util.Date getBilling_time() 
	{
		return this.billing_time;
	}
	public void setArrival_amount(Double arrival_amount) 
	{
		this.arrival_amount = arrival_amount;
	}
	/**
	 * 返回 到账金额
	 * @return
	 */
	public Double getArrival_amount() 
	{
		return this.arrival_amount;
	}
	public void setArrival_time(java.util.Date arrival_time) 
	{
		this.arrival_time = arrival_time;
	}
	/**
	 * 返回 到账时间
	 * @return
	 */
	public java.util.Date getArrival_time() 
	{
		return this.arrival_time;
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
	public void setContractBillingRecordList(List<ContractBillingRecord> contractBillingRecordList) 
	{
		this.contractBillingRecordList = contractBillingRecordList;
	}
	/**
	 * 返回 合同基本信息列表
	 * @return
	 */
	public List<ContractBillingRecord> getContractBillingRecordList() 
	{
		return this.contractBillingRecordList;
	}
	public void setContractPaymentRecordList(List<ContractPaymentRecord> contractPaymentRecordList) 
	{
		this.contractPaymentRecordList = contractPaymentRecordList;
	}
	/**
	 * 返回 合同基本信息列表
	 * @return
	 */
	public List<ContractPaymentRecord> getContractPaymentRecordList() 
	{
		return this.contractPaymentRecordList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Contractinfo)) 
		{
			return false;
		}
		Contractinfo rhs = (Contractinfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.contract_departmentID, rhs.contract_departmentID)
		.append(this.contracttype, rhs.contracttype)
		.append(this.contract_type, rhs.contract_type)
		.append(this.type, rhs.type)
		.append(this.thirdcontracttype, rhs.thirdcontracttype)
		.append(this.contract_money, rhs.contract_money)
		.append(this.contract_num, rhs.contract_num)
		.append(this.no, rhs.no)
		.append(this.settlement_money, rhs.settlement_money)
		.append(this.contract_name, rhs.contract_name)
		.append(this.project_bid_floorprice, rhs.project_bid_floorprice)
		.append(this.contract_status, rhs.contract_status)
		.append(this.project_bid_price, rhs.project_bid_price)
		.append(this.first_party, rhs.first_party)
		.append(this.rate, rhs.rate)
		.append(this.second_party, rhs.second_party)
		.append(this.project_content, rhs.project_content)
		.append(this.payment_type, rhs.payment_type)
		.append(this.project_scale, rhs.project_scale)
		.append(this.singing_time, rhs.singing_time)
		.append(this.isrecord, rhs.isrecord)
		.append(this.project_director, rhs.project_director)
		.append(this.issave, rhs.issave)
		.append(this.project_status, rhs.project_status)
		.append(this.isrecovery, rhs.isrecovery)
		.append(this.contract_department, rhs.contract_department)
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
		.append(this.remark, rhs.remark)
		.append(this.updater, rhs.updater)
		.append(this.creater, rhs.creater)
		.append(this.update_time, rhs.update_time)
		.append(this.create_time, rhs.create_time)
		.append(this.custodian, rhs.custodian)
		.append(this.total_investment, rhs.total_investment)
		.append(this.progress, rhs.progress)
		.append(this.invoice_amount, rhs.invoice_amount)
		.append(this.billing_time, rhs.billing_time)
		.append(this.arrival_amount, rhs.arrival_amount)
		.append(this.arrival_time, rhs.arrival_time)
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
		.append(this.contracttype) 
		.append(this.contract_type) 
		.append(this.type) 
		.append(this.thirdcontracttype)
		.append(this.contract_money) 
		.append(this.contract_num) 
		.append(this.no) 
		.append(this.settlement_money) 
		.append(this.contract_name) 
		.append(this.project_bid_floorprice) 
		.append(this.contract_status) 
		.append(this.project_bid_price) 
		.append(this.first_party) 
		.append(this.rate) 
		.append(this.second_party) 
		.append(this.project_content) 
		.append(this.payment_type) 
		.append(this.project_scale) 
		.append(this.singing_time) 
		.append(this.isrecord) 
		.append(this.project_director) 
		.append(this.issave) 
		.append(this.project_status) 
		.append(this.isrecovery) 
		.append(this.contract_department) 
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
		.append(this.remark) 
		.append(this.updater) 
		.append(this.creater) 
		.append(this.update_time) 
		.append(this.create_time) 
		.append(this.custodian) 
		.append(this.total_investment) 
		.append(this.progress) 
		.append(this.invoice_amount) 
		.append(this.billing_time) 
		.append(this.arrival_amount) 
		.append(this.arrival_time) 
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
		.append("contracttype", this.contracttype) 
		.append("contract_type", this.contract_type) 
		.append("thirdcontracttype", this.thirdcontracttype)
		.append("type", this.type) 
		.append("contract_money", this.contract_money) 
		.append("contract_num", this.contract_num) 
		.append("no", this.no) 
		.append("settlement_money", this.settlement_money) 
		.append("contract_name", this.contract_name) 
		.append("project_bid_floorprice", this.project_bid_floorprice) 
		.append("contract_status", this.contract_status) 
		.append("project_bid_price", this.project_bid_price) 
		.append("first_party", this.first_party) 
		.append("rate", this.rate) 
		.append("second_party", this.second_party) 
		.append("project_content", this.project_content) 
		.append("payment_type", this.payment_type) 
		.append("project_scale", this.project_scale) 
		.append("singing_time", this.singing_time) 
		.append("isrecord", this.isrecord) 
		.append("project_director", this.project_director) 
		.append("issave", this.issave) 
		.append("project_status", this.project_status) 
		.append("isrecovery", this.isrecovery) 
		.append("contract_department", this.contract_department) 
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
		.append("remark", this.remark) 
		.append("updater", this.updater) 
		.append("creater", this.creater) 
		.append("update_time", this.update_time) 
		.append("create_time", this.create_time) 
		.append("custodian", this.custodian) 
		.append("total_investment", this.total_investment) 
		.append("progress", this.progress) 
		.append("invoice_amount", this.invoice_amount) 
		.append("billing_time", this.billing_time) 
		.append("arrival_amount", this.arrival_amount) 
		.append("arrival_time", this.arrival_time) 
		.append("file", this.file) 
		.toString();
	}
	
	public Contractinfo(){
		
	}
	
	public Contractinfo(String contract_departmentID,
			String contract_num, Double contract_money, String contract_name,
			Double settlement_money, String contract_type,String contracttype,String thirdcontracttype,
			Double project_bid_floorprice, String contract_status,
			Double project_bid_price, String first_party, String rate,
			String second_party, String project_content, String payment_type,
			String project_scale, Date singing_time, String isrecord,
			String project_director, String issave, String project_status,
			String isrecovery, String contract_department, String inout,
			String project_place, Date start_time, String owner_name,
			Date end_time, String project_origin, String project_leader,
			String bidding_platform, String contract_handler,
			String bidding_method, String contract_reviewer,
			String contract_authorized_person, String remark, String updater,
			String creater, Date update_time, Date create_time,
			String custodian, Double total_investment, String file, String no,
			String type,Double invoice_amount,
			String  project_directorID,String  contract_handlerID,String  contract_reviewerID,
			String  project_leaderID,String  contract_authorized_personID ,String  updaterID,
			String  createrID,String  custodianID,String  projiect_name,
			String  sections_name,String file_second,String file_third
			) {
		super();
		this.contract_departmentID = contract_departmentID;
		this.contract_num = contract_num;
		this.contract_money = contract_money;
		this.contract_name = contract_name;
		this.settlement_money = settlement_money;
		this.contract_type = contract_type;
		this.contracttype = contracttype;
		this.thirdcontracttype = thirdcontracttype;
		this.project_bid_floorprice = project_bid_floorprice;
		this.contract_status = contract_status;
		this.project_bid_price = project_bid_price;
		this.first_party = first_party;
		this.rate = rate;
		this.second_party = second_party;
		this.project_content = project_content;
		this.payment_type = payment_type;
		this.project_scale = project_scale;
		this.singing_time = singing_time;
		this.isrecord = isrecord;
		this.project_director = project_director;
		this.issave = issave;
		this.project_status = project_status;
		this.isrecovery = isrecovery;
		this.contract_department = contract_department;
		this.inout = inout;
		this.project_place = project_place;
		this.start_time = start_time;
		this.owner_name = owner_name;
		this.end_time = end_time;
		this.project_origin = project_origin;
		this.project_leader = project_leader;
		this.bidding_platform = bidding_platform;
		this.contract_handler = contract_handler;
		this.bidding_method = bidding_method;
		this.contract_reviewer = contract_reviewer;
		this.contract_authorized_person = contract_authorized_person;
		this.remark = remark;
		this.updater = updater;
		this.creater = creater;
		this.update_time = update_time;
		this.create_time = create_time;
		this.custodian = custodian;
		this.total_investment = total_investment;
		this.file = file;
		this.no = no;
		this.type = type;
		this.invoice_amount=invoice_amount;
		
		this.project_directorID=project_directorID;
		this.contract_handlerID=contract_handlerID;
		this.contract_reviewerID=contract_reviewerID;
		this.project_leaderID=project_leaderID;
		this.contract_authorized_personID=contract_authorized_personID;
		this.updaterID=updaterID;
		this.createrID=createrID;
		this.custodianID=custodianID;
		this.projiect_name=projiect_name;
		this.sections_name=sections_name;
		
		this.file_second = file_second;
		this.file_third = file_third;
	}

}