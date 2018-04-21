package com.hotent.platform.xml.system;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.system.SysQueryFieldSetting;
import com.hotent.platform.model.system.SysQueryMetaField;
import com.hotent.platform.model.system.SysQuerySqlDef;
import com.hotent.platform.model.system.SysQueryView;
import com.hotent.platform.xml.constant.XmlConstant;
import com.hotent.platform.xml.table.BpmFormTableXml;

@XmlRootElement(name = "querySqlDefs")
@XmlAccessorType(XmlAccessType.FIELD)
public class SysQuerySqlDefXml 
{
	/**
	 * 自定义Sql查询
	 */
	@XmlElement(name = XmlConstant.SYS_QUERY_SQL_DEF, type = SysQuerySqlDef.class)
	private SysQuerySqlDef sysQuerySqlDef;
	/**
	 * 视图List
	 */
	
	@XmlElementWrapper(name =XmlConstant.SYS_QUERY_VIEW_LIST)
	@XmlElement(name = XmlConstant.SYS_QUERY_VIEW, type =SysQueryView.class)
	private List<SysQueryView> sysQueryViewList;
	
	/**
	 * 字段List
	 */
	@XmlElementWrapper(name =XmlConstant.SYS_QUERY_META_FIELD_LIST)
	@XmlElement(name = XmlConstant.SYS_QUERY_META_FIELD, type =SysQueryMetaField.class)
	private List<SysQueryMetaField> sysQueryMetaFieldList;
	


	
	
	
	public SysQuerySqlDef getSysQuerySqlDef() {
		return sysQuerySqlDef;
	}

	public void setSysQuerySqlDef(SysQuerySqlDef sysQuerySqlDef) {
		this.sysQuerySqlDef = sysQuerySqlDef;
	}

	

	public List<SysQueryMetaField> getSysQueryMetaFieldList() {
		return sysQueryMetaFieldList;
	}

	public void setSysQueryMetaFieldList(List<SysQueryMetaField> sysQueryMetaFieldList) {
		this.sysQueryMetaFieldList = sysQueryMetaFieldList;
	}

	public List<SysQueryView> getSysQueryViewList() {
		return sysQueryViewList;
	}

	public void setSysQueryViewList(List<SysQueryView> sysQueryViewList) {
		this.sysQueryViewList = sysQueryViewList;
	}

	
	
	

	
}
