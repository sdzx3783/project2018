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
 * 对象功能:调查问卷问题选项 Model对象
 */
public class QuestionnaireOption extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *问卷调查ID
	 */
	protected Long  questionnaire_id;
	/**
	 *调查问卷问题ID
	 */
	protected Long  questionnaire_ques_id;
	/**
	 *选项描述
	 */
	protected String  desc;
	/**
	 *是否删除
	 */
	protected Boolean  del;
	
	/**
	 * 投票人数
	 */
	protected Integer count;
	
	/**
	 * 百分比
	 */
	protected Double percentum;
	
	
   	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the percentum
	 */
	public Double getPercentum() {
		return percentum;
	}

	/**
	 * @param percentum the percentum to set
	 */
	public void setPercentum(Double percentum) {
		this.percentum = percentum;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof QuestionnaireOption)) 
		{
			return false;
		}
		QuestionnaireOption rhs = (QuestionnaireOption) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.questionnaire_id, rhs.questionnaire_id)
		.append(this.questionnaire_ques_id, rhs.questionnaire_ques_id)
		.append(this.desc, rhs.desc)
		.append(this.del, rhs.del)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.questionnaire_id) 
		.append(this.questionnaire_ques_id) 
		.append(this.desc) 
		.append(this.del) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("questionnaire_id", this.questionnaire_id)
		.append("questionnaire_ques_id", this.questionnaire_ques_id)
		.append("desc", this.desc)
		.append("del", this.del)
		.toString();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	 * @return the del
	 */
	public Boolean getDel() {
		return del;
	}

	/**
	 * @param del the del to set
	 */
	public void setDel(Boolean del) {
		this.del = del;
	}
	

}