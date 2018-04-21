package com.hotent.makshi.model.questionnaire;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:调查问卷 Model对象
 */
public class Questionnaire extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *标题
	 */
	protected String  title;
	/**
	 *描述
	 */
	protected String  desc;
	/**
	 *创建人
	 */
	protected String  creater;
	/**
	 *创建人ID
	 */
	protected String  creater_id;
	/**
	 *创建人创建人是否可以参与投票
	 */
	protected Boolean creater_partin;
	/**
	 *开始日期
	 */
	protected Date  begin_date;
	/**
	 *结束日期
	 */
	protected Date  end_date;
	/**
	 *状态
	 */
	protected Integer status;
	/**
	 *重复投票时间
	 */
	protected Integer  repeat_time;
	/**
	 *限制IP
	 */
	protected Boolean limit_ip;
	/**
	 *限制COOKIE
	 */
	protected Boolean limit_cookie;
	
	
	protected List<QuestionnaireQuestion> questionnaireQuestionList;
	
	protected String  questionnaireReceiverPer;
	
	protected String  questionnaireReceiverOrg;
	
	protected String  questionnaireReceiverPerName;
	
	protected String  questionnaireReceiverOrgName;
	

	/**
	 * @return the questionnaireReceiverPer
	 */
	public String getQuestionnaireReceiverPer() {
		return questionnaireReceiverPer;
	}

	/**
	 * @param questionnaireReceiverPer the questionnaireReceiverPer to set
	 */
	public void setQuestionnaireReceiverPer(String questionnaireReceiverPer) {
		this.questionnaireReceiverPer = questionnaireReceiverPer;
	}

	/**
	 * @return the questionnaireReceiverOrg
	 */
	public String getQuestionnaireReceiverOrg() {
		return questionnaireReceiverOrg;
	}

	/**
	 * @param questionnaireReceiverOrg the questionnaireReceiverOrg to set
	 */
	public void setQuestionnaireReceiverOrg(String questionnaireReceiverOrg) {
		this.questionnaireReceiverOrg = questionnaireReceiverOrg;
	}

	/**
	 * @return the questionnaireReceiverPerName
	 */
	public String getQuestionnaireReceiverPerName() {
		return questionnaireReceiverPerName;
	}

	/**
	 * @param questionnaireReceiverPerName the questionnaireReceiverPerName to set
	 */
	public void setQuestionnaireReceiverPerName(String questionnaireReceiverPerName) {
		this.questionnaireReceiverPerName = questionnaireReceiverPerName;
	}

	/**
	 * @return the questionnaireReceiverOrgName
	 */
	public String getQuestionnaireReceiverOrgName() {
		return questionnaireReceiverOrgName;
	}

	/**
	 * @param questionnaireReceiverOrgName the questionnaireReceiverOrgName to set
	 */
	public void setQuestionnaireReceiverOrgName(String questionnaireReceiverOrgName) {
		this.questionnaireReceiverOrgName = questionnaireReceiverOrgName;
	}

	/**
	 * @return the questionnaireQuestionList
	 */
	public List<QuestionnaireQuestion> getQuestionnaireQuestionList() {
		return questionnaireQuestionList;
	}

	/**
	 * @param questionnaireQuestionList the questionnaireQuestionList to set
	 */
	public void setQuestionnaireQuestionList(
			List<QuestionnaireQuestion> questionnaireQuestionList) {
		this.questionnaireQuestionList = questionnaireQuestionList;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Questionnaire)) 
		{
			return false;
		}
		Questionnaire rhs = (Questionnaire) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.title, rhs.title)
		.append(this.desc, rhs.desc)
		.append(this.creater, rhs.creater)
		.append(this.creater_id, rhs.creater_id)
		.append(this.creater_partin, rhs.creater_partin)
		.append(this.begin_date, rhs.begin_date)
		.append(this.end_date, rhs.end_date)
		.append(this.status, rhs.status)
		.append(this.repeat_time, rhs.repeat_time)
		.append(this.limit_ip, rhs.limit_ip)
		.append(this.limit_cookie, rhs.limit_cookie)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.title) 
		.append(this.desc) 
		.append(this.creater) 
		.append(this.creater_id) 
		.append(this.creater_partin) 
		.append(this.begin_date) 
		.append(this.end_date) 
		.append(this.status) 
		.append(this.repeat_time)
		.append(this.limit_ip)
		.append(this.limit_cookie)
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("title", this.title)
		.append("desc", this.desc)
		.append("creater", this.creater)
		.append("creater_id", this.creater_id)
		.append("creater_partin", this.creater_partin)
		.append("begin_date", this.begin_date)
		.append("end_date", this.end_date)
		.append("status", this.status)
		.append("repeat_time", this.repeat_time)
		.append("limit_ip", this.limit_ip)
		.append("limit_cookie", this.limit_cookie)
		.toString();
	}
	
	/**
	 * @return the creater_partin
	 */
	public Boolean getCreater_partin() {
		return creater_partin;
	}

	/**
	 * @param creater_partin the creater_partin to set
	 */
	public void setCreater_partin(Boolean creater_partin) {
		this.creater_partin = creater_partin;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * @return the creater
	 */
	public String getCreater() {
		return creater;
	}
	/**
	 * @param creater the creater to set
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	/**
	 * @return the creater_id
	 */
	public String getCreater_id() {
		return creater_id;
	}
	/**
	 * @param creater_id the creater_id to set
	 */
	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}
	/**
	 * @return the begin_date
	 */
	public Date getBegin_date() {
		return begin_date;
	}
	/**
	 * @param begin_date the begin_date to set
	 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the repeat_time
	 */
	public Integer getRepeat_time() {
		return repeat_time;
	}
	/**
	 * @param repeat_time the repeat_time to set
	 */
	public void setRepeat_time(Integer repeat_time) {
		this.repeat_time = repeat_time;
	}

	/**
	 * @return the limit_ip
	 */
	public Boolean getLimit_ip() {
		return limit_ip;
	}
	/**
	 * @param limit_ip the limit_ip to set
	 */
	public void setLimit_ip(Boolean limit_ip) {
		this.limit_ip = limit_ip;
	}
	/**
	 * @return the limit_cookie
	 */
	public Boolean getLimit_cookie() {
		return limit_cookie;
	}
	/**
	 * @param limit_cookie the limit_cookie to set
	 */
	public void setLimit_cookie(Boolean limit_cookie) {
		this.limit_cookie = limit_cookie;
	}

	

}