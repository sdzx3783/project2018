package com.hotent.makshi.model.summary;

public class SummaryCompanyVip {

	private Long id;

	private String organization;

	private String level;

	private String certificateNo;

	private String duties;

	private String membershipTime;

	private Double paymentStandard;

	private Integer vipYears;

	private String certificationDate;

	private String certeffectiveTime;

	private String departmentID;

	private String department;

	private String contactser;

	private String contactsPhone;

	private Integer isdelete;

	private String remark;

	private String attachment;

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo == null ? null : certificateNo.trim();
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties == null ? null : duties.trim();
	}

	public String getMembershipTime() {
		return membershipTime;
	}

	public void setMembershipTime(String membershipTime) {
		this.membershipTime = membershipTime == null ? null : membershipTime.trim();
	}

	public Double getPaymentStandard() {
		return paymentStandard;
	}

	public void setPaymentStandard(Double paymentStandard) {
		this.paymentStandard = paymentStandard;
	}

	public Integer getVipYears() {
		return vipYears;
	}

	public void setVipYears(Integer vipYears) {
		this.vipYears = vipYears;
	}

	public String getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(String certificationDate) {
		this.certificationDate = certificationDate == null ? null : certificationDate.trim();
	}

	public String getCerteffectiveTime() {
		return certeffectiveTime;
	}

	public void setCerteffectiveTime(String certeffectiveTime) {
		this.certeffectiveTime = certeffectiveTime == null ? null : certeffectiveTime.trim();
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID == null ? null : departmentID.trim();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
	}

	public String getContactser() {
		return contactser;
	}

	public void setContactser(String contactser) {
		this.contactser = contactser == null ? null : contactser.trim();
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone == null ? null : contactsPhone.trim();
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

}