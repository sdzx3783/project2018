package com.hotent.platform.xml.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.xml.constant.XmlConstant;

@XmlRootElement(name="dataSources")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysDataSourceXml
{
	/**
	 * 系统数据源
	 */
	@XmlElement(name=XmlConstant.SYS_DATA_SOURCE,type=SysDataSource.class)
	private SysDataSource dataSource;

	
	
	public SysDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(SysDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	

}
