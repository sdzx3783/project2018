package com.hotent.platform.xml.system;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.system.ConditionScript;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "system")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConditionScriptXmlList
{
	/**
	 * 条件脚本Xml List
	 */
	@XmlElements({ @XmlElement(name = "conditionScripts", type = ConditionScriptXml.class) })
	private List<ConditionScriptXml> conditionScriptXmlList;

	public List<ConditionScriptXml> getConditionScriptXmlList() {
		return conditionScriptXmlList;
	}

	public void setConditionScriptXmlList(
			List<ConditionScriptXml> conditionScriptXmlList) {
		this.conditionScriptXmlList = conditionScriptXmlList;
	}
	
	

}
