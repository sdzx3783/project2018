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
public class QuestionnaireReceiver extends WfBaseModel
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
	 *调查问卷参与者类型
	 */
	protected Integer  receiver_type;
	
	
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof QuestionnaireReceiver)) 
		{
			return false;
		}
		QuestionnaireReceiver rhs = (QuestionnaireReceiver) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user, rhs.user)
		.append(this.user_id, rhs.user_id)
		.append(this.questionnaire_id, rhs.questionnaire_id)
		.append(this.receiver_type, rhs.receiver_type)
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
		.append(this.receiver_type) 
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
		.append("receiver_type", this.receiver_type)
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
	 * @return the receiver_type
	 */
	public Integer getReceiver_type() {
		return receiver_type;
	}

	/**
	 * @param receiver_type the receiver_type to set
	 */
	public void setReceiver_type(Integer receiver_type) {
		this.receiver_type = receiver_type;
	}

	/**
	 * @param questionnaire_id the questionnaire_id to set
	 */
	public void setQuestionnaire_id(Long questionnaire_id) {
		this.questionnaire_id = questionnaire_id;
	}

	
	

}