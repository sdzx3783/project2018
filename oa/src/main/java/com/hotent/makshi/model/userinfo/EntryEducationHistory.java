package com.hotent.makshi.model.userinfo;

import java.util.Date;


import com.hotent.core.model.BaseModel;
/**
 * 对象功能:学习经历 Model对象
 */
public class EntryEducationHistory extends BaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *起止时间
	 */
	protected Date  startDate;
	
	protected Date  endDate;
	/**
	 *就读学校或机构
	 */
	protected String  education_school;
	/**
	 *专业
	 */
	protected String  major;
	/**
	 *所获证书、学位、奖励
	 */
	protected String  achieve_certificate;
	/**
	 *附件
	 */
	protected String  attachment;
	
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
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEducation_school(String education_school) 
	{
		this.education_school = education_school;
	}
	/**
	 * 返回 就读学校或机构
	 * @return
	 */
	public String getEducation_school() 
	{
		return this.education_school;
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
	public void setAchieve_certificate(String achieve_certificate) 
	{
		this.achieve_certificate = achieve_certificate;
	}
	/**
	 * 返回 所获证书、学位、奖励
	 * @return
	 */
	public String getAchieve_certificate() 
	{
		return this.achieve_certificate;
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
	
	
	public EntryEducationHistory(){
		
	}
	public EntryEducationHistory( Long refId, Date startDate,Date endDate,
			String education_school, String major, String achieve_certificate,
			String attachment) {
		super();
		this.refId = refId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.education_school = education_school;
		this.major = major;
		this.achieve_certificate = achieve_certificate;
		this.attachment = attachment;
	}

	
	
	
}