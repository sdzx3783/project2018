package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.model.hj.Hjwppurchaselist;
/**
 * 对象功能:物品 Model对象
 */
public class HjGoodsPurchaseds extends WfBaseModel
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
	protected String  appli_departmentID;
	/**
	 *现场负责人审核ID
	 */
	protected String  person_chargeID;
	/**
	 *部门副经理核定ID
	 */
	protected String  deputy_managerID;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请日期
	 */
	protected java.util.Date  appli_date;
	/**
	 *申请部门
	 */
	protected String  appli_department;
	/**
	 *采购方式
	 */
	protected String  type;
	/**
	 *备注
	 */
	protected String  remarks;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *现场负责人审核
	 */
	protected String  person_charge;
	/**
	 *部门副经理核定
	 */
	protected String  deputy_manager;
	protected Long  runId=0L;
	
	/**
	 *物品列表
	 */
	protected List<Hjwppurchaselist> hjwppurchaselistList=new ArrayList<Hjwppurchaselist>();
	
	protected Hjwppurchaselist hjWppurchase;
	protected Long refId;
	
	protected Long Id1;
	public Long getId1() {
		return Id1;
	}
	public void setId1(Long id1) {
		Id1 = id1;
	}

	/**
	 *物品名称
	 */
	protected String  name;
	/**
	 *规格型号
	 */
	protected String  specification;
	/**
	 *数量
	 */
	protected Long  number;
	/**
	 *质量要求
	 */
	protected String  price;
	
	/**
	 * 领用人
	 */
	protected String lname;
	/**
	 * 采购日期
	 */
	protected java.util.Date date;
	/**
	 * 领用部门
	 */
	protected String ldepartment;
	
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getLdepartment() {
		return ldepartment;
	}
	public void setLdepartment(String ldepartment) {
		this.ldepartment = ldepartment;
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
	public void setAppli_departmentID(String appli_departmentID) 
	{
		this.appli_departmentID = appli_departmentID;
	}
	/**
	 * 返回 申请部门ID
	 * @return
	 */
	public String getAppli_departmentID() 
	{
		return this.appli_departmentID;
	}
	public void setPerson_chargeID(String person_chargeID) 
	{
		this.person_chargeID = person_chargeID;
	}
	/**
	 * 返回 现场负责人审核ID
	 * @return
	 */
	public String getPerson_chargeID() 
	{
		return this.person_chargeID;
	}
	public void setDeputy_managerID(String deputy_managerID) 
	{
		this.deputy_managerID = deputy_managerID;
	}
	/**
	 * 返回 部门副经理核定ID
	 * @return
	 */
	public String getDeputy_managerID() 
	{
		return this.deputy_managerID;
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
	public void setAppli_date(java.util.Date appli_date) 
	{
		this.appli_date = appli_date;
	}
	/**
	 * 返回 申请日期
	 * @return
	 */
	public java.util.Date getAppli_date() 
	{
		return this.appli_date;
	}
	public void setAppli_department(String appli_department) 
	{
		this.appli_department = appli_department;
	}
	/**
	 * 返回 申请部门
	 * @return
	 */
	public String getAppli_department() 
	{
		return this.appli_department;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 采购方式
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemarks() 
	{
		return this.remarks;
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
	public void setPerson_charge(String person_charge) 
	{
		this.person_charge = person_charge;
	}
	/**
	 * 返回 现场负责人审核
	 * @return
	 */
	public String getPerson_charge() 
	{
		return this.person_charge;
	}
	public void setDeputy_manager(String deputy_manager) 
	{
		this.deputy_manager = deputy_manager;
	}
	/**
	 * 返回 部门副经理核定
	 * @return
	 */
	public String getDeputy_manager() 
	{
		return this.deputy_manager;
	}
	public void setHjwppurchaselistList(List<Hjwppurchaselist> hjwppurchaselistList) 
	{
		this.hjwppurchaselistList = hjwppurchaselistList;
	}
	/**
	 * 返回 物品列表
	 * @return
	 */
	public List<Hjwppurchaselist> getHjwppurchaselistList() 
	{
		return this.hjwppurchaselistList;
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
		if (!(object instanceof HjGoodsPurchaseds)) 
		{
			return false;
		}
		HjGoodsPurchaseds rhs = (HjGoodsPurchaseds) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.appli_departmentID, rhs.appli_departmentID)
		.append(this.person_chargeID, rhs.person_chargeID)
		.append(this.deputy_managerID, rhs.deputy_managerID)
		.append(this.applicant, rhs.applicant)
		.append(this.appli_date, rhs.appli_date)
		.append(this.appli_department, rhs.appli_department)
		.append(this.type, rhs.type)
		.append(this.remarks, rhs.remarks)
		.append(this.attachment, rhs.attachment)
		.append(this.person_charge, rhs.person_charge)
		.append(this.deputy_manager, rhs.deputy_manager)
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
		.append(this.appli_departmentID) 
		.append(this.person_chargeID) 
		.append(this.deputy_managerID) 
		.append(this.applicant) 
		.append(this.appli_date) 
		.append(this.appli_department) 
		.append(this.type) 
		.append(this.remarks) 
		.append(this.attachment) 
		.append(this.person_charge) 
		.append(this.deputy_manager) 
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
		.append("appli_departmentID", this.appli_departmentID) 
		.append("person_chargeID", this.person_chargeID) 
		.append("deputy_managerID", this.deputy_managerID) 
		.append("applicant", this.applicant) 
		.append("appli_date", this.appli_date) 
		.append("appli_department", this.appli_department) 
		.append("type", this.type) 
		.append("remarks", this.remarks) 
		.append("attachment", this.attachment) 
		.append("person_charge", this.person_charge) 
		.append("deputy_manager", this.deputy_manager) 
		.toString();
	}
	public Hjwppurchaselist getHjWppurchase() {
		return hjWppurchase;
	}
	public void setHjWppurchase(Hjwppurchaselist hjWppurchase) {
		this.hjWppurchase = hjWppurchase;
	}

}