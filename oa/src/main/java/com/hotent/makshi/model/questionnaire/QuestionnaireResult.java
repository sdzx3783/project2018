package com.hotent.makshi.model.questionnaire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.fr.third.org.apache.poi.hssf.record.formula.functions.Int;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.utils.DateUtils;
/**
 * 对象功能:调查问卷投票结果 Model对象
 */
public class QuestionnaireResult extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *投票用户
	 */
	protected String  user;
	/**
	 *投票用户ID
	 */
	protected String  user_id;
	/**
	 *调查问卷ID
	 */
	protected Long  questionnaire_id;
	/**
	 *调查问卷问题ID
	 */
	protected Long  questionnaire_ques_id;
	/**
	 *投票结果
	 */
	protected String  result;
	/**
	 *创建日期
	 */
	protected Date  ctime;
	/**
	 *修改日期
	 */
	protected Date  mtime;
	
	
	protected String ip;
	
	
	
   	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof QuestionnaireResult)) 
		{
			return false;
		}
		QuestionnaireResult rhs = (QuestionnaireResult) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user, rhs.user)
		.append(this.user_id, rhs.user_id)
		.append(this.questionnaire_id, rhs.questionnaire_id)
		.append(this.questionnaire_ques_id, rhs.questionnaire_ques_id)
		.append(this.result, rhs.result)
		.append(this.ctime, rhs.ctime)
		.append(this.mtime, rhs.mtime)
		.append(this.ip, rhs.ip)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user) 
		.append(this.user_id) 
		.append(this.questionnaire_id) 
		.append(this.questionnaire_ques_id) 
		.append(this.result) 
		.append(this.ctime) 
		.append(this.mtime) 
		.append(this.ip) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user", this.user)
		.append("user_id", this.user_id)
		.append("questionnaire_id", this.questionnaire_id)
		.append("questionnaire_ques_id", this.questionnaire_ques_id)
		.append("result", this.result)
		.append("ctime", this.ctime)
		.append("mtime", this.mtime)
		.append("ip", this.ip)
		.toString();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the questionnaire_id
	 */
	public Long getQuestionnaire_id() {
		return questionnaire_id;
	}

	/**
	 * @param questionnaire_id the questionnaire_id to set
	 */
	public void setQuestionnaire_id(Long questionnaire_id) {
		this.questionnaire_id = questionnaire_id;
	}

	/**
	 * @return the questionnaire_ques_id
	 */
	public Long getQuestionnaire_ques_id() {
		return questionnaire_ques_id;
	}

	/**
	 * @param questionnaire_ques_id the questionnaire_ques_id to set
	 */
	public void setQuestionnaire_ques_id(Long questionnaire_ques_id) {
		this.questionnaire_ques_id = questionnaire_ques_id;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the ctime
	 */
	public Date getCtime() {
		return ctime;
	}

	/**
	 * @param ctime the ctime to set
	 */
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	/**
	 * @return the mtime
	 */
	public Date getMtime() {
		return mtime;
	}

	/**
	 * @param mtime the mtime to set
	 */
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	
	

}