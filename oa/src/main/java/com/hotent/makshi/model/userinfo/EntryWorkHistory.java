package com.hotent.makshi.model.userinfo;

import java.util.Date;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:工作经历 Model对象
 */
public class EntryWorkHistory extends BaseModel
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
	 *工作单位
	 */
	protected String  workplace;
	/**
	 *部门岗位
	 */
	protected String  department_post;
	/**
	 *技术职务
	 */
	protected String  positions;
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
	public void setWorkplace(String workplace) 
	{
		this.workplace = workplace;
	}
	/**
	 * 返回 工作单位
	 * @return
	 */
	public String getWorkplace() 
	{
		return this.workplace;
	}
	public void setDepartment_post(String department_post) 
	{
		this.department_post = department_post;
	}
	/**
	 * 返回 部门岗位
	 * @return
	 */
	public String getDepartment_post() 
	{
		return this.department_post;
	}
	public void setPositions(String positions) 
	{
		this.positions = positions;
	}
	/**
	 * 返回 技术职务
	 * @return
	 */
	public String getPositions() 
	{
		return this.positions;
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

	public EntryWorkHistory(){
		
	}
	public EntryWorkHistory( Long refId, Date startDate,Date endDate,
			String workplace, String department_post, String positions,
			String attachment) {
		super();
		this.refId = refId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.workplace = workplace;
		this.department_post = department_post;
		this.positions = positions;
		this.attachment = attachment;
	}

	
	
}