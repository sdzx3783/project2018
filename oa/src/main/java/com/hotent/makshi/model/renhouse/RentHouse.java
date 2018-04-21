package com.hotent.makshi.model.renhouse;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.model.reninfo.RentInfo;
/**
 * 对象功能:租房申请表 Model对象
 */
public class RentHouse extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *申请二级部门ID
	 */
	protected String  second_departmentID;
	/**
	 *申请部门ID
	 */
	protected String  departmentID;
	/**
	 *申请人
	 */
	protected String  application_person;
	/**
	 *申请二级部门
	 */
	protected String  second_department;
	/**
	 *申请部门
	 */
	protected String  department;
	/**
	 *租房用途
	 */
	protected String  use_type;
	/**
	 *租房事由
	 */
	protected String  reason;
	/**
	 *其他原因
	 */
	protected String  reason_content;
	/**
	 *部门当前租房信息
	 */
	protected String  current_rent_info;
	/**
	 *经办人：ID
	 */
	protected String  handle_personID;
	/**
	 *房屋精确地址：
	 */
	protected String  address;
	/**
	 *房屋押金：
	 */
	protected String  deposit;
	/**
	 *房屋租金（元/月）：
	 */
	protected String  money;
	/**
	 *经办人：
	 */
	protected String  handle_person;
	/**
	 *房屋面积（平米）：
	 */
	protected String  size;
	/**
	 *房东姓名：
	 */
	protected String  landlord;
	/**
	 *承租人：
	 */
	protected String  rent_person;
	/**
	 *住宿人数：
	 */
	protected String  number_people;
	/**
	 *宿舍负责人：
	 */
	protected String  responsible_person;
	/**
	 *租房开始日期：
	 */
	protected java.util.Date  start_date;
	/**
	 *租房结束日期：
	 */
	protected java.util.Date  rent_end_date;
	public java.util.Date getRent_end_date() {
		return rent_end_date;
	}
	public void setRent_end_date(java.util.Date rent_end_date) {
		this.rent_end_date = rent_end_date;
	}

	/**
	 *核定报销费用：
	 */
	protected String  reimburse_type;
	/**
	 *水电费（%）：
	 */
	protected String  water_and_electricity;
	/**
	 *报销审核意见
	 */
	protected String  reimburse_note;
	/**
	 * 房屋结构
	 */
	protected String structure;
	
	protected Long  runId=0L;
	
	/**
	 * 租房信息附件
	 */
	protected String  rent_house_attachment;
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getRent_house_attachment() {
		return rent_house_attachment;
	}
	public void setRent_house_attachment(String rent_house_attachment) {
		this.rent_house_attachment = rent_house_attachment;
	}

	/**
	 *租房申请表列表
	 */
	protected List<RentInfo> rentInfoList=new ArrayList<RentInfo>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSecond_departmentID(String second_departmentID) 
	{
		this.second_departmentID = second_departmentID;
	}
	/**
	 * 返回 申请二级部门ID
	 * @return
	 */
	public String getSecond_departmentID() 
	{
		return this.second_departmentID;
	}
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 申请部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setApplication_person(String application_person) 
	{
		this.application_person = application_person;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplication_person() 
	{
		return this.application_person;
	}
	public void setSecond_department(String second_department) 
	{
		this.second_department = second_department;
	}
	/**
	 * 返回 申请二级部门
	 * @return
	 */
	public String getSecond_department() 
	{
		return this.second_department;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 申请部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setUse_type(String use_type) 
	{
		this.use_type = use_type;
	}
	/**
	 * 返回 租房用途
	 * @return
	 */
	public String getUse_type() 
	{
		return this.use_type;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 租房事由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setReason_content(String reason_content) 
	{
		this.reason_content = reason_content;
	}
	/**
	 * 返回 其他原因
	 * @return
	 */
	public String getReason_content() 
	{
		return this.reason_content;
	}
	public void setCurrent_rent_info(String current_rent_info) 
	{
		this.current_rent_info = current_rent_info;
	}
	/**
	 * 返回 部门当前租房信息
	 * @return
	 */
	public String getCurrent_rent_info() 
	{
		return this.current_rent_info;
	}
	public void setHandle_personID(String handle_personID) 
	{
		this.handle_personID = handle_personID;
	}
	/**
	 * 返回 经办人：ID
	 * @return
	 */
	public String getHandle_personID() 
	{
		return this.handle_personID;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 房屋精确地址：
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setDeposit(String deposit) 
	{
		this.deposit = deposit;
	}
	/**
	 * 返回 房屋押金：
	 * @return
	 */
	public String getDeposit() 
	{
		return this.deposit;
	}
	public void setMoney(String money) 
	{
		this.money = money;
	}
	/**
	 * 返回 房屋租金（元/月）：
	 * @return
	 */
	public String getMoney() 
	{
		return this.money;
	}
	public void setHandle_person(String handle_person) 
	{
		this.handle_person = handle_person;
	}
	/**
	 * 返回 经办人：
	 * @return
	 */
	public String getHandle_person() 
	{
		return this.handle_person;
	}
	public void setSize(String size) 
	{
		this.size = size;
	}
	/**
	 * 返回 房屋面积（平米）：
	 * @return
	 */
	public String getSize() 
	{
		return this.size;
	}
	public void setLandlord(String landlord) 
	{
		this.landlord = landlord;
	}
	/**
	 * 返回 房东姓名：
	 * @return
	 */
	public String getLandlord() 
	{
		return this.landlord;
	}
	public void setRent_person(String rent_person) 
	{
		this.rent_person = rent_person;
	}
	/**
	 * 返回 承租人：
	 * @return
	 */
	public String getRent_person() 
	{
		return this.rent_person;
	}
	public void setNumber_people(String number_people) 
	{
		this.number_people = number_people;
	}
	/**
	 * 返回 住宿人数：
	 * @return
	 */
	public String getNumber_people() 
	{
		return this.number_people;
	}
	public void setResponsible_person(String responsible_person) 
	{
		this.responsible_person = responsible_person;
	}
	/**
	 * 返回 宿舍负责人：
	 * @return
	 */
	public String getResponsible_person() 
	{
		return this.responsible_person;
	}
	public void setStart_date(java.util.Date start_date) 
	{
		this.start_date = start_date;
	}
	/**
	 * 返回 租房开始日期：
	 * @return
	 */
	public java.util.Date getStart_date() 
	{
		return this.start_date;
	}
	public void setReimburse_type(String reimburse_type) 
	{
		this.reimburse_type = reimburse_type;
	}
	/**
	 * 返回 核定报销费用：
	 * @return
	 */
	public String getReimburse_type() 
	{
		return this.reimburse_type;
	}
	public void setWater_and_electricity(String water_and_electricity) 
	{
		this.water_and_electricity = water_and_electricity;
	}
	/**
	 * 返回 水电费（%）：
	 * @return
	 */
	public String getWater_and_electricity() 
	{
		return this.water_and_electricity;
	}
	public void setReimburse_note(String reimburse_note) 
	{
		this.reimburse_note = reimburse_note;
	}
	/**
	 * 返回 报销审核意见
	 * @return
	 */
	public String getReimburse_note() 
	{
		return this.reimburse_note;
	}
	public void setRentInfoList(List<RentInfo> rentInfoList) 
	{
		this.rentInfoList = rentInfoList;
	}
	/**
	 * 返回 租房申请表列表
	 * @return
	 */
	public List<RentInfo> getRentInfoList() 
	{
		return this.rentInfoList;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RentHouse)) 
		{
			return false;
		}
		RentHouse rhs = (RentHouse) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.second_departmentID, rhs.second_departmentID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.application_person, rhs.application_person)
		.append(this.second_department, rhs.second_department)
		.append(this.department, rhs.department)
		.append(this.use_type, rhs.use_type)
		.append(this.reason, rhs.reason)
		.append(this.reason_content, rhs.reason_content)
		.append(this.current_rent_info, rhs.current_rent_info)
		.append(this.handle_personID, rhs.handle_personID)
		.append(this.address, rhs.address)
		.append(this.deposit, rhs.deposit)
		.append(this.money, rhs.money)
		.append(this.handle_person, rhs.handle_person)
		.append(this.size, rhs.size)
		.append(this.landlord, rhs.landlord)
		.append(this.rent_person, rhs.rent_person)
		.append(this.number_people, rhs.number_people)
		.append(this.responsible_person, rhs.responsible_person)
		.append(this.start_date, rhs.start_date)
		.append(this.reimburse_type, rhs.reimburse_type)
		.append(this.water_and_electricity, rhs.water_and_electricity)
		.append(this.reimburse_note, rhs.reimburse_note)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.second_departmentID) 
		.append(this.departmentID) 
		.append(this.application_person) 
		.append(this.second_department) 
		.append(this.department) 
		.append(this.use_type) 
		.append(this.reason) 
		.append(this.reason_content) 
		.append(this.current_rent_info) 
		.append(this.handle_personID) 
		.append(this.address) 
		.append(this.deposit) 
		.append(this.money) 
		.append(this.handle_person) 
		.append(this.size) 
		.append(this.landlord) 
		.append(this.rent_person) 
		.append(this.number_people) 
		.append(this.responsible_person) 
		.append(this.start_date) 
		.append(this.reimburse_type) 
		.append(this.water_and_electricity) 
		.append(this.reimburse_note) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("second_departmentID", this.second_departmentID) 
		.append("departmentID", this.departmentID) 
		.append("application_person", this.application_person) 
		.append("second_department", this.second_department) 
		.append("department", this.department) 
		.append("use_type", this.use_type) 
		.append("reason", this.reason) 
		.append("reason_content", this.reason_content) 
		.append("current_rent_info", this.current_rent_info) 
		.append("handle_personID", this.handle_personID) 
		.append("address", this.address) 
		.append("deposit", this.deposit) 
		.append("money", this.money) 
		.append("handle_person", this.handle_person) 
		.append("size", this.size) 
		.append("landlord", this.landlord) 
		.append("rent_person", this.rent_person) 
		.append("number_people", this.number_people) 
		.append("responsible_person", this.responsible_person) 
		.append("start_date", this.start_date) 
		.append("reimburse_type", this.reimburse_type) 
		.append("water_and_electricity", this.water_and_electricity) 
		.append("reimburse_note", this.reimburse_note) 
		.toString();
	}

}