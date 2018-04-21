package com.hotent.makshi.model.summary;

public class SummaryPersonalVip {
	private Long id;

	private String organization;

	private String level;

	private String certificateNo;

	private String membershipTime;

	private Double paymentStandard;

	private String effectiveTime;

	private String certificationDate;

	private String certeffectiveTime;

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

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime == null ? null : effectiveTime.trim();
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