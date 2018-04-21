package com.hotent.makshi.model.hr;

import java.util.Date;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:家庭成员 Model对象
 */
public class EntryChildren extends WfBaseModel
{
	//主键
	protected Long id;
	protected Long refId;
	/**
	 *姓名
	 */
	protected String  name;
	/**
	 *性别
	 */
	protected String  sex;
	/**
	 *出生年
	 */
	protected Date  birthday;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	

	
}