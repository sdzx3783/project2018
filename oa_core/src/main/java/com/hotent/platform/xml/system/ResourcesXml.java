package com.hotent.platform.xml.system;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.hotent.platform.model.bpm.BpmDefinition;
import com.hotent.platform.model.system.Resources;
@XmlRootElement(name = "resources")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourcesXml {
	@XmlElement(name = "resour", type = Resources.class)
	private Resources resour;
	
	public Resources getResour() {
		return resour;
	}

	public void setResour(Resources resour) {
		this.resour = resour;
	}

	@XmlElement(name = "resource", type = Resources.class)
	private List<Resources> resList;
	
	public List<Resources> getResList() {
		return resList;
	}

	public void setResList(List<Resources> resList) {
		this.resList = resList;
	}

	@XmlElement(name = "resourcesList", type = ResourcesXml.class)
	private List<ResourcesXml> resourcesList;
	


	public List<ResourcesXml> getResourcesList() {
		if(resourcesList==null){
			return new ArrayList<ResourcesXml>();
		}
		return resourcesList;
	}

	public void setResourcesList(List<ResourcesXml> resourcesList) {
		this.resourcesList = resourcesList;
	}
	
}
