package com.hotent.platform.xml.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.hotent.platform.model.system.PersonScript;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "personScripts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonScriptXml
{
	/**
	 * 人员脚本
	 */
	@XmlElement(name = XmlConstant.SYS_PERSON_SCRIPT, type = PersonScript.class)
	private PersonScript personScript;

	public PersonScript getPersonScript() {
		return personScript;
	}

	public void setPersonScript(PersonScript personScript) {
		this.personScript = personScript;
	}

	
	

}
