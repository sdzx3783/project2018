package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:劳务人员信息采集(水保示范园流程) Model对象
 */
public class ContractWorkersInfoCollection extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *登记人ID
	 */
	protected String  registererID;
	/**
	 *审核人ID
	 */
	protected String  checkerID;
	/**
	 *部门领导ID
	 */
	protected String  managerID;
	/**
	 *名称
	 */
	protected String  name;
	/**
	 *入职时间
	 */
	protected java.util.Date  entry_date;
	/**
	 *身份证号
	 */
	protected String  id_card;
	/**
	 *登记人
	 */
	protected String  registerer;
	/**
	 *户籍
	 */
	protected String  address;
	/**
	 *紧急联系人
	 */
	protected String  emergencyer;
	/**
	 *紧急联系人电话
	 */
	protected String  emergency_phone;
	/**
	 *劳务工种
	 */
	protected String  work_type;
	/**
	 *是否住宿
	 */
	protected String  is_stay;
	/**
	 *性别
	 */
	protected String  gender;
	/**
	 *年龄
	 */
	protected Long  age;
	/**
	 *审核人
	 */
	protected String  checker;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *相片
	 */
	protected String  attachment;
	/**
	 *部门领导
	 */
	protected String  manager;
	/**
	 *在职状态 1在 0离职
	 */
	protected Integer  status;
	protected Integer  isfinished;
	
	public Integer getIsfinished() {
		return isfinished;
	}
	public void setIsfinished(Integer isfinished) {
		this.isfinished = isfinished;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setRegistererID(String registererID) 
	{
		this.registererID = registererID;
	}
	/**
	 * 返回 登记人ID
	 * @return
	 */
	public String getRegistererID() 
	{
		return this.registererID;
	}
	public void setCheckerID(String checkerID) 
	{
		this.checkerID = checkerID;
	}
	/**
	 * 返回 审核人ID
	 * @return
	 */
	public String getCheckerID() 
	{
		return this.checkerID;
	}
	public void setManagerID(String managerID) 
	{
		this.managerID = managerID;
	}
	/**
	 * 返回 部门领导ID
	 * @return
	 */
	public String getManagerID() 
	{
		return this.managerID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setEntry_date(java.util.Date entry_date) 
	{
		this.entry_date = entry_date;
	}
	/**
	 * 返回 入职时间
	 * @return
	 */
	public java.util.Date getEntry_date() 
	{
		return this.entry_date;
	}
	public void setId_card(String id_card) 
	{
		this.id_card = id_card;
	}
	/**
	 * 返回 身份证号
	 * @return
	 */
	public String getId_card() 
	{
		return this.id_card;
	}
	public void setRegisterer(String registerer) 
	{
		this.registerer = registerer;
	}
	/**
	 * 返回 登记人
	 * @return
	 */
	public String getRegisterer() 
	{
		return this.registerer;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 户籍
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setEmergencyer(String emergencyer) 
	{
		this.emergencyer = emergencyer;
	}
	/**
	 * 返回 紧急联系人
	 * @return
	 */
	public String getEmergencyer() 
	{
		return this.emergencyer;
	}
	public void setEmergency_phone(String emergency_phone) 
	{
		this.emergency_phone = emergency_phone;
	}
	/**
	 * 返回 紧急联系人电话
	 * @return
	 */
	public String getEmergency_phone() 
	{
		return this.emergency_phone;
	}
	public void setWork_type(String work_type) 
	{
		this.work_type = work_type;
	}
	/**
	 * 返回 劳务工种
	 * @return
	 */
	public String getWork_type() 
	{
		return this.work_type;
	}
	public void setIs_stay(String is_stay) 
	{
		this.is_stay = is_stay;
	}
	/**
	 * 返回 是否住宿
	 * @return
	 */
	public String getIs_stay() 
	{
		return this.is_stay;
	}
	public void setGender(String gender) 
	{
		this.gender = gender;
	}
	/**
	 * 返回 性别
	 * @return
	 */
	public String getGender() 
	{
		return this.gender;
	}
	public void setAge(Long age) 
	{
		this.age = age;
	}
	/**
	 * 返回 年龄
	 * @return
	 */
	public Long getAge() 
	{
		return this.age;
	}
	public void setChecker(String checker) 
	{
		this.checker = checker;
	}
	/**
	 * 返回 审核人
	 * @return
	 */
	public String getChecker() 
	{
		return this.checker;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 相片
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setManager(String manager) 
	{
		this.manager = manager;
	}
	/**
	 * 返回 部门领导
	 * @return
	 */
	public String getManager() 
	{
		return this.manager;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractWorkersInfoCollection)) 
		{
			return false;
		}
		ContractWorkersInfoCollection rhs = (ContractWorkersInfoCollection) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.registererID, rhs.registererID)
		.append(this.checkerID, rhs.checkerID)
		.append(this.managerID, rhs.managerID)
		.append(this.name, rhs.name)
		.append(this.entry_date, rhs.entry_date)
		.append(this.id_card, rhs.id_card)
		.append(this.registerer, rhs.registerer)
		.append(this.address, rhs.address)
		.append(this.emergencyer, rhs.emergencyer)
		.append(this.emergency_phone, rhs.emergency_phone)
		.append(this.work_type, rhs.work_type)
		.append(this.is_stay, rhs.is_stay)
		.append(this.gender, rhs.gender)
		.append(this.age, rhs.age)
		.append(this.checker, rhs.checker)
		.append(this.remark, rhs.remark)
		.append(this.attachment, rhs.attachment)
		.append(this.manager, rhs.manager)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.registererID) 
		.append(this.checkerID) 
		.append(this.managerID) 
		.append(this.name) 
		.append(this.entry_date) 
		.append(this.id_card) 
		.append(this.registerer) 
		.append(this.address) 
		.append(this.emergencyer) 
		.append(this.emergency_phone) 
		.append(this.work_type) 
		.append(this.is_stay) 
		.append(this.gender) 
		.append(this.age) 
		.append(this.checker) 
		.append(this.remark) 
		.append(this.attachment) 
		.append(this.manager) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("registererID", this.registererID) 
		.append("checkerID", this.checkerID) 
		.append("managerID", this.managerID) 
		.append("name", this.name) 
		.append("entry_date", this.entry_date) 
		.append("id_card", this.id_card) 
		.append("registerer", this.registerer) 
		.append("address", this.address) 
		.append("emergencyer", this.emergencyer) 
		.append("emergency_phone", this.emergency_phone) 
		.append("work_type", this.work_type) 
		.append("is_stay", this.is_stay) 
		.append("gender", this.gender) 
		.append("age", this.age) 
		.append("checker", this.checker) 
		.append("remark", this.remark) 
		.append("attachment", this.attachment) 
		.append("manager", this.manager) 
		.toString();
	}

}