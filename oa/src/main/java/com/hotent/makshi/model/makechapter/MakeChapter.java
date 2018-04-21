package com.hotent.makshi.model.makechapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.utils.DateUtils;

/**
 * 对象功能:印章表 Model对象
 */
public class MakeChapter extends WfBaseModel {
	// 主键
	protected Long id;
	/**
	 * 领取人ID
	 */
	protected String take_personID;
	/**
	 * 合同编号
	 */
	protected String contract_id;
	/**
	 * 总投资额
	 */
	protected String total_investment;
	/**
	 * 申请人
	 */
	protected String application_person;
	/**
	 * 申请印章名称
	 */
	protected String chapter_name;
	/**
	 * 申请刻章事由
	 */
	protected String reason;
	/**
	 * 要求到位时间
	 */
	protected java.util.Date limit_date;
	/**
	 * 项目名称
	 */
	protected String project_name;
	/**
	 * 刻章类型
	 */
	protected String chapter_type;
	/**
	 * 附件
	 */
	protected String attachment;
	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 工单号
	 */
	protected String globalflowno;
	/**
	 * 申请时间
	 */
	protected String application_time;
	/**
	 * 领取时间
	 */
	protected java.util.Date take_date;
	/**
	 * 领取人
	 */
	protected String take_person;
	/**
	 * 审批状态
	 */
	protected String state;
	protected Long runId = 0L;

	/**
	 * 印章是否已注销 0在用，1已注销
	 */
	private Integer ifSealDel = 0;

	/**
	 * 印章图片
	 */
	private String sealImg;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTake_personID(String take_personID) {
		this.take_personID = take_personID;
	}

	/**
	 * 返回 领取人ID
	 * 
	 * @return
	 */
	public String getTake_personID() {

		return this.take_personID;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	/**
	 * 返回 合同编号
	 * 
	 * @return
	 */
	public String getContract_id() {
		if (StringUtils.isEmpty(contract_id))
			return null;
		return this.contract_id.toUpperCase();
	}

	public void setTotal_investment(String total_investment) {
		this.total_investment = total_investment;
	}

	/**
	 * 返回 总投资额
	 * 
	 * @return
	 */
	public String getTotal_investment() {
		return this.total_investment;
	}

	public void setApplication_person(String application_person) {
		this.application_person = application_person;
	}

	/**
	 * 返回 申请人
	 * 
	 * @return
	 */
	public String getApplication_person() {
		return this.application_person;
	}

	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}

	/**
	 * 返回 申请印章名称
	 * 
	 * @return
	 */
	public String getChapter_name() {
		return this.chapter_name;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 返回 申请刻章事由
	 * 
	 * @return
	 */
	public String getReason() {
		return this.reason;
	}

	public void setLimit_date(java.util.Date limit_date) {
		this.limit_date = limit_date;
	}

	/**
	 * 返回 要求到位时间
	 * 
	 * @return
	 */
	public java.util.Date getLimit_date() {
		return this.limit_date;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	/**
	 * 返回 项目名称
	 * 
	 * @return
	 */
	public String getProject_name() {
		return this.project_name;
	}

	public void setChapter_type(String chapter_type) {
		this.chapter_type = chapter_type;
	}

	/**
	 * 返回 刻章类型
	 * 
	 * @return
	 */
	public String getChapter_type() {
		return this.chapter_type;
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

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 返回 备注
	 * 
	 * @return
	 */
	public String getRemarks() {
		return this.remarks;
	}

	public void setGlobalflowno(String globalflowno) {
		this.globalflowno = globalflowno;
	}

	/**
	 * 返回 工单号
	 * 
	 * @return
	 */
	public String getGlobalflowno() {
		return this.globalflowno;
	}

	public void setApplication_time(String application_time) {
		this.application_time = application_time;
	}

	/**
	 * 返回 申请时间
	 * 
	 * @return
	 */
	public Date getApplication_time() {
		String applicationTime = this.application_time;
		if (StringUtils.isEmpty(applicationTime))
			return null;
		return DateUtils.parseDate(applicationTime, "yyyy-MM-dd");
	}

	public void setTake_date(java.util.Date take_date) {
		this.take_date = take_date;
	}

	/**
	 * 返回 领取时间
	 * 
	 * @return
	 */
	public java.util.Date getTake_date() {
		return this.take_date;
	}

	public void setTake_person(String take_person) {
		this.take_person = take_person;
	}

	/**
	 * 返回 领取人
	 * 
	 * @return
	 */
	public String getTake_person() {
		return this.take_person;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Integer getIfSealDel() {
		return ifSealDel;
	}

	public void setIfSealDel(Integer ifSealDel) {
		this.ifSealDel = ifSealDel;
	}

	public String getSealImg() {
		return sealImg;
	}

	public void setSealImg(String sealImg) {
		this.sealImg = sealImg;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MakeChapter)) {
			return false;
		}
		MakeChapter rhs = (MakeChapter) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.take_personID, rhs.take_personID).append(this.contract_id, rhs.contract_id).append(this.total_investment, rhs.total_investment)
				.append(this.application_person, rhs.application_person).append(this.chapter_name, rhs.chapter_name).append(this.reason, rhs.reason).append(this.limit_date, rhs.limit_date)
				.append(this.project_name, rhs.project_name).append(this.chapter_type, rhs.chapter_type).append(this.attachment, rhs.attachment).append(this.remarks, rhs.remarks)
				.append(this.globalflowno, rhs.globalflowno).append(this.application_time, rhs.application_time).append(this.take_date, rhs.take_date).append(this.take_person, rhs.take_person)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.take_personID).append(this.contract_id).append(this.total_investment).append(this.application_person)
				.append(this.chapter_name).append(this.reason).append(this.limit_date).append(this.project_name).append(this.chapter_type).append(this.attachment).append(this.remarks)
				.append(this.globalflowno).append(this.application_time).append(this.take_date).append(this.take_person).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("take_personID", this.take_personID).append("contract_id", this.contract_id).append("total_investment", this.total_investment)
				.append("application_person", this.application_person).append("chapter_name", this.chapter_name).append("reason", this.reason).append("limit_date", this.limit_date)
				.append("project_name", this.project_name).append("chapter_type", this.chapter_type).append("attachment", this.attachment).append("remarks", this.remarks)
				.append("globalflowno", this.globalflowno).append("application_time", this.application_time).append("take_date", this.take_date).append("take_person", this.take_person).toString();
	}

}