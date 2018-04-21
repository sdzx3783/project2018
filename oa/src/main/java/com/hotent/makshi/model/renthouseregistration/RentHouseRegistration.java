package com.hotent.makshi.model.renthouseregistration;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:租房登记表 Model对象
 */
public class RentHouseRegistration extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *部门名称ID
	 */
	protected String  departmentID;
	/**
	 *租房编号
	 */
	protected String  house_id;
	/**
	 *房屋面积
	 */
	protected String  size;
	/**
	 *部门名称
	 */
	protected String  department;
	/**
	 *房租金额
	 */
	protected String  money;
	/**
	 *房东姓名
	 */
	protected String  landlord;
	/**
	 *房屋结构
	 */
	protected String  house_type;
	/**
	 *承租人
	 */
	protected String  rent_person;
	/**
	 *房屋押金：
	 */
	protected String  deposit;
	/**
	 *经办人：
	 */
	protected String  handle_person;
	/**
	 *租房性质
	 */
	protected String  rent_type;
	/**
	 *房屋地址
	 */
	protected String  address;
	/**
	 *住宿人数
	 */
	protected String  number_people;
	/**
	 *租房开始日期
	 */
	protected java.util.Date  start_date;
	/**
	 *宿舍负责人
	 */
	protected String  responsible_person;
	/**
	 *租房停止日期
	 */
	protected java.util.Date  end_date;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *备注
	 */
	protected String  remarks;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getHandle_person() {
		return handle_person;
	}
	public void setHandle_person(String handle_person) {
		this.handle_person = handle_person;
	}
	/**
	 * 返回 部门名称ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setHouse_id(String house_id) 
	{
		this.house_id = house_id;
	}
	/**
	 * 返回 租房编号
	 * @return
	 */
	public String getHouse_id() 
	{
		return this.house_id;
	}
	public void setSize(String size) 
	{
		this.size = size;
	}
	/**
	 * 返回 房屋面积
	 * @return
	 */
	public String getSize() 
	{
		return this.size;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 部门名称
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setMoney(String money) 
	{
		this.money = money;
	}
	/**
	 * 返回 房租金额
	 * @return
	 */
	public String getMoney() 
	{
		return this.money;
	}
	public void setLandlord(String landlord) 
	{
		this.landlord = landlord;
	}
	/**
	 * 返回 房东姓名
	 * @return
	 */
	public String getLandlord() 
	{
		return this.landlord;
	}
	public void setHouse_type(String house_type) 
	{
		this.house_type = house_type;
	}
	/**
	 * 返回 房屋结构
	 * @return
	 */
	public String getHouse_type() 
	{
		return this.house_type;
	}
	public void setRent_person(String rent_person) 
	{
		this.rent_person = rent_person;
	}
	/**
	 * 返回 承租人
	 * @return
	 */
	public String getRent_person() 
	{
		return this.rent_person;
	}
	public void setRent_type(String rent_type) 
	{
		this.rent_type = rent_type;
	}
	/**
	 * 返回 租房性质
	 * @return
	 */
	public String getRent_type() 
	{
		return this.rent_type;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 房屋地址
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setNumber_people(String number_people) 
	{
		this.number_people = number_people;
	}
	/**
	 * 返回 住宿人数
	 * @return
	 */
	public String getNumber_people() 
	{
		return this.number_people;
	}
	public void setStart_date(java.util.Date start_date) 
	{
		this.start_date = start_date;
	}
	/**
	 * 返回 租房开始日期
	 * @return
	 */
	public java.util.Date getStart_date() 
	{
		return this.start_date;
	}
	public void setResponsible_person(String responsible_person) 
	{
		this.responsible_person = responsible_person;
	}
	/**
	 * 返回 宿舍负责人
	 * @return
	 */
	public String getResponsible_person() 
	{
		return this.responsible_person;
	}
	public void setEnd_date(java.util.Date end_date) 
	{
		this.end_date = end_date;
	}
	/**
	 * 返回 租房停止日期
	 * @return
	 */
	public java.util.Date getEnd_date() 
	{
		return this.end_date;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemarks() 
	{
		return this.remarks;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof RentHouseRegistration)) 
		{
			return false;
		}
		RentHouseRegistration rhs = (RentHouseRegistration) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.departmentID, rhs.departmentID)
		.append(this.house_id, rhs.house_id)
		.append(this.size, rhs.size)
		.append(this.department, rhs.department)
		.append(this.money, rhs.money)
		.append(this.landlord, rhs.landlord)
		.append(this.house_type, rhs.house_type)
		.append(this.rent_person, rhs.rent_person)
		.append(this.rent_type, rhs.rent_type)
		.append(this.address, rhs.address)
		.append(this.number_people, rhs.number_people)
		.append(this.start_date, rhs.start_date)
		.append(this.responsible_person, rhs.responsible_person)
		.append(this.end_date, rhs.end_date)
		.append(this.attachment, rhs.attachment)
		.append(this.remarks, rhs.remarks)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.departmentID) 
		.append(this.house_id) 
		.append(this.size) 
		.append(this.department) 
		.append(this.money) 
		.append(this.landlord) 
		.append(this.house_type) 
		.append(this.rent_person) 
		.append(this.rent_type) 
		.append(this.address) 
		.append(this.number_people) 
		.append(this.start_date) 
		.append(this.responsible_person) 
		.append(this.end_date) 
		.append(this.attachment) 
		.append(this.remarks) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("departmentID", this.departmentID) 
		.append("house_id", this.house_id) 
		.append("size", this.size) 
		.append("department", this.department) 
		.append("money", this.money) 
		.append("landlord", this.landlord) 
		.append("house_type", this.house_type) 
		.append("rent_person", this.rent_person) 
		.append("rent_type", this.rent_type) 
		.append("address", this.address) 
		.append("number_people", this.number_people) 
		.append("start_date", this.start_date) 
		.append("responsible_person", this.responsible_person) 
		.append("end_date", this.end_date) 
		.append("attachment", this.attachment) 
		.append("remarks", this.remarks) 
		.toString();
	}

}