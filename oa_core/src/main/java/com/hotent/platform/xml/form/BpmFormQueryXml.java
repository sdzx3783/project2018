package com.hotent.platform.xml.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.bpm.BpmFormQuery;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "formQuerys")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormQueryXml 
{
	/**
	 * 自定义查询
	 */
	@XmlElement(name = XmlConstant.BPM_FORM_QUERY, type = BpmFormQuery.class)
	private BpmFormQuery bpmFormQuery;

	
	/**
	 * get方法
	 * @return
	 */
	public BpmFormQuery getBpmFormQuery() {
		return bpmFormQuery;
	}
	/**
	 * set方法
	 * @param bpmFormQuery
	 */
	public void setBpmFormQuery(BpmFormQuery bpmFormQuery) {
		this.bpmFormQuery = bpmFormQuery;
	}
	
	

}
