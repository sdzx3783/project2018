package com.hotent.platform.xml.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.system.ConditionScript;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "conditionScripts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConditionScriptXml
{
	/**
	 * 条件脚本
	 */
	@XmlElement(name = XmlConstant.SYS_CONDITION_SCRIPT, type = ConditionScript.class)
	private ConditionScript conditionScript;

	public ConditionScript getConditionScript() {
		return conditionScript;
	}

	public void setConditionScript(ConditionScript conditionScript) {
		this.conditionScript = conditionScript;
	}
	
	

}
