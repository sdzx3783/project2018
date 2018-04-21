package com.hotent.platform.xml.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.hotent.platform.model.system.AliasScript;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "aliasScripts")
@XmlAccessorType(XmlAccessType.FIELD)
public class AliasScriptXml 
{
	/**
	 * 别名脚本
	 */
	@XmlElement(name = XmlConstant.SYS_ALIAS_SCRIPT, type = AliasScript.class)
	private AliasScript aliasScript;

	
	public AliasScript getAliasScript() {
		return aliasScript;
	}

	public void setAliasScript(AliasScript aliasScript) {
		this.aliasScript = aliasScript;
	}
	
	
}
