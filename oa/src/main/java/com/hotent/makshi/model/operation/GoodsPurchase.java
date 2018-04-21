package com.hotent.makshi.model.operation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.operation.Purchaselists;
/**
 * 对象功能:物品采购申请 Model对象
 */
public class GoodsPurchase extends BaseModel
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
	 *领用部门ID
	 */
	protected String  recipients_departmentID;
	/**
	 *领用部门
	 */
	protected String  recipients_department;
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
	 *备注
	 */
	protected String  remarks;
	/**
	 *领用人
	 */
	protected String  recipients_user;
	/**
	 *领用人ID
	 */
	protected String  recipients_userID;
	/**
	 *采购日期
	 */
	protected java.util.Date  purchase_date;
	
	/**
	 *物品采购申请列表
	 */
	protected List<Purchaselists> purchaselistsList=new ArrayList<Purchaselists>();
	
	protected Long refId;
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
	protected String  number;
	/**
	 *单价
	 */
	protected String  price;
	/**
	 * 采购方式
	 */
	protected String  type;	
	/**
	 * 质量要求
	 */
	protected String  standard;		
	/**
	 *附件
	 */
	protected String  attachment;
	
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public void setRecipients_userID(String recipients_userID) 
	{
		this.recipients_userID = recipients_userID;
	}
	/**
	 * 返回 领用人ID
	 * @return
	 */
	public String getRecipients_userID() 
	{
		return this.recipients_userID;
	}
	public void setRecipients_departmentID(String recipients_departmentID) 
	{
		this.recipients_departmentID = recipients_departmentID;
	}
	/**
	 * 返回 领用部门ID
	 * @return
	 */
	public String getRecipients_departmentID() 
	{
		return this.recipients_departmentID;
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
	public void setRecipients_user(String recipients_user) 
	{
		this.recipients_user = recipients_user;
	}
	/**
	 * 返回 领用人
	 * @return
	 */
	public String getRecipients_user() 
	{
		return this.recipients_user;
	}
	public void setRecipients_department(String recipients_department) 
	{
		this.recipients_department = recipients_department;
	}
	/**
	 * 返回 领用部门
	 * @return
	 */
	public String getRecipients_department() 
	{
		return this.recipients_department;
	}
	public void setPurchase_date(java.util.Date purchase_date) 
	{
		this.purchase_date = purchase_date;
	}
	/**
	 * 返回 采购日期
	 * @return
	 */
	public java.util.Date getPurchase_date() 
	{
		return this.purchase_date;
	}
	public void setPurchaselistsList(List<Purchaselists> purchaselistsList) 
	{
		this.purchaselistsList = purchaselistsList;
	}
	/**
	 * 返回 物品采购申请列表
	 * @return
	 */
	public List<Purchaselists> getPurchaselistsList() 
	{
		return this.purchaselistsList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof GoodsPurchase)) 
		{
			return false;
		}
		GoodsPurchase rhs = (GoodsPurchase) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.appli_departmentID, rhs.appli_departmentID)
		.append(this.recipients_userID, rhs.recipients_userID)
		.append(this.recipients_departmentID, rhs.recipients_departmentID)
		.append(this.applicant, rhs.applicant)
		.append(this.appli_date, rhs.appli_date)
		.append(this.appli_department, rhs.appli_department)
		.append(this.remarks, rhs.remarks)
		.append(this.recipients_user, rhs.recipients_user)
		.append(this.recipients_department, rhs.recipients_department)
		.append(this.purchase_date, rhs.purchase_date)
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
		.append(this.recipients_userID) 
		.append(this.recipients_departmentID) 
		.append(this.applicant) 
		.append(this.appli_date) 
		.append(this.appli_department) 
		.append(this.remarks) 
		.append(this.recipients_user) 
		.append(this.recipients_department) 
		.append(this.purchase_date) 
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
		.append("recipients_userID", this.recipients_userID) 
		.append("recipients_departmentID", this.recipients_departmentID) 
		.append("applicant", this.applicant) 
		.append("appli_date", this.appli_date) 
		.append("appli_department", this.appli_department) 
		.append("remarks", this.remarks) 
		.append("recipients_user", this.recipients_user) 
		.append("recipients_department", this.recipients_department) 
		.append("purchase_date", this.purchase_date) 
		.toString();
	}

}