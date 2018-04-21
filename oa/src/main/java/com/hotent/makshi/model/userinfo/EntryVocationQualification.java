package com.hotent.makshi.model.userinfo;

import java.util.Date;

import com.hotent.core.model.BaseModel;

/**
 * 对象功能:执业资格 Model对象
 */
public class EntryVocationQualification extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3942331914290237800L;
	// 主键
	protected Long id;
	protected Long refId;
	protected Long userId;
	protected Long linkId;
	protected String status;
	// 证书关联id
	public Long getLinkId() {
		return linkId;
	}
	
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	/**
	 * 执业资格编号
	 */
	protected String num;
	/**
	 * 执业资格名称
	 */
	protected String name;

	/**
	 * 发证机构
	 */
	protected String organization;
	/**
	 * 执业资格证专业
	 */
	protected String major;
	/**
	 * 取得证书时间
	 */
	protected java.util.Date achieve_time;
	/**
	 * 附件
	 */
	protected String attachment;
	/**
	 * 是否转入本公司
	 */
	protected String switchs;
	/**
	 * 借阅人
	 */
	protected String borrower;
	protected String borrowerID;

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

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 执业资格名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * 返回 发证机构
	 * 
	 * @return
	 */
	public String getOrganization() {
		return this.organization;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * 返回 执业资格证专业
	 * 
	 * @return
	 */
	public String getMajor() {
		return this.major;
	}

	public void setAchieve_time(java.util.Date achieve_time) {
		this.achieve_time = achieve_time;
	}

	/**
	 * 返回 取得证书时间
	 * 
	 * @return
	 */
	public java.util.Date getAchieve_time() {
		return this.achieve_time;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * 返回 附件
	 * 
	 * @return
	 */
	public String getAttachment() {
		return this.attachment;
	}

	public void setSwitchs(String switchs) {
		this.switchs = switchs;
	}

	/**
	 * 返回 是否转入本公司
	 * 
	 * @return
	 */
	public String getSwitchs() {
		return this.switchs;
	}
	public EntryVocationQualification(){
		
	}
	public EntryVocationQualification(Long refId, String num,
			String name, String organization, String major, Date achieve_time,
			String attachment, String switchs, String status) {
		super();
		this.refId = refId;
		this.num = num;
		this.name = name;
		this.organization = organization;
		this.major = major;
		this.achieve_time = achieve_time;
		this.attachment = attachment;
		this.switchs = switchs;
		this.status = status;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}