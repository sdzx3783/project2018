package com.hotent.makshi.model.qualification;

import com.hotent.core.model.BaseModel;

public class CertificateAndBorrow extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2002937234114402276L;

	//主键
	private Long id;
	
	private Integer type;
	
	private String certifateId;
	
	private String name;
	
	private Long userId;
	
	private Long linkId;
	
	private String borrower;
	
	private String borrowerId;
	
	private java.util.Date effictivedate;
	
	private Integer switchs;
	
	private Integer deleted;
	

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public java.util.Date getEffictivedate() {
		return effictivedate;
	}

	public void setEffictivedate(java.util.Date effictivedate) {
		this.effictivedate = effictivedate;
	}

	public Integer getSwitchs() {
		return switchs;
	}

	public void setSwitchs(Integer switchs) {
		this.switchs = switchs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getCertifateId() {
		return certifateId;
	}

	public void setCertifateId(String certifateId) {
		this.certifateId = certifateId;
	}
	
	
}
