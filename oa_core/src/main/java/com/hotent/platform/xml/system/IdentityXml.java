package com.hotent.platform.xml.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.system.Identity;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name = "identities")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdentityXml 
{
	/**
	 * 流水号
	 */
	@XmlElement(name = XmlConstant.SYS_IDENTITY, type = Identity.class)
	private Identity identity;

	
	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	
}
