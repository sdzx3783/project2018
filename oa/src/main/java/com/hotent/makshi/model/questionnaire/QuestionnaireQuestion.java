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
 * 对象功能:问卷调查问题 Model对象
 */
public class QuestionnaireQuestion extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *调查问卷ID
	 */
	protected Long  questionnaire_id;
	/**
	 *标题
	 */
	protected String  title;
	/**
	 *是否多选题
	 */
	protected Boolean  checkbox;
	/**
	 *用户最多选择
	 */
	protected Integer  max_choice;
	/**
	 *是否必填
	 */
	protected Boolean  required;
	
	protected Integer type;
	
	protected List<QuestionnaireResult> resultList;//问答题回答
	
	protected List<QuestionnaireOption> questionnaireOptionList;
	
	
   	/**
	 * @return the questionnaireOptionList
	 */
	public List<QuestionnaireOption> getQuestionnaireOptionList() {
		return questionnaireOptionList;
	}

	/**
	 * @param questionnaireOptionList the questionnaireOptionList to set
	 */
	public void setQuestionnaireOptionList(
			List<QuestionnaireOption> questionnaireOptionList) {
		this.questionnaireOptionList = questionnaireOptionList;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof QuestionnaireQuestion)) 
		{
			return false;
		}
		QuestionnaireQuestion rhs = (QuestionnaireQuestion) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.questionnaire_id, rhs.questionnaire_id)
		.append(this.title, rhs.title)
		.append(this.checkbox, rhs.checkbox)
		.append(this.max_choice, rhs.max_choice)
		.append(this.required, rhs.required)
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
		.append(this.title) 
		.append(this.checkbox) 
		.append(this.max_choice) 
		.append(this.required) 
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
		.append("title", this.title)
		.append("checkbox", this.checkbox)
		.append("max_choice", this.max_choice)
		.append("required", this.required)
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
	 * @return the checkbox
	 */
	public Boolean getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox the checkbox to set
	 */
	public void setCheckbox(Boolean checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * @return the max_choice
	 */
	public Integer getMax_choice() {
		return max_choice;
	}

	/**
	 * @param max_choice the max_choice to set
	 */
	public void setMax_choice(Integer max_choice) {
		this.max_choice = max_choice;
	}

	/**
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<QuestionnaireResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<QuestionnaireResult> resultList) {
		this.resultList = resultList;
	}



	
	
	
}