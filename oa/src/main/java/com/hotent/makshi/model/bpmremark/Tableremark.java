package com.hotent.makshi.model.bpmremark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:表单摘要 Model对象
 */
public class Tableremark extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *摘要
	 */
	protected String  tableBpmRemark;
	/**
	 *申请部门
	 */
	protected String  appDepartment;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getAppDepartment() {
		return appDepartment;
	}
	public void setAppDepartment(String appDepartment) {
		this.appDepartment = appDepartment;
	}
	public void setTableBpmRemark(String tableBpmRemark) 
	{
		this.tableBpmRemark = tableBpmRemark;
	}
	public String getTableBpmRemark() 
	{
		return this.tableBpmRemark;
	}


}