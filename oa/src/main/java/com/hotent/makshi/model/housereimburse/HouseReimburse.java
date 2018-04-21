package com.hotent.makshi.model.housereimburse;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;

/**
 * 对象功能:租房报销记录表 Model对象
 */
public class HouseReimburse extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键
	protected Long id;
	/**
	 * 所属项目部ID
	 */
	protected String departmentID;
	/**
	 * 报销人ID
	 */
	protected String reimburse_personID;
	/**
	 * 租房编号
	 */
	protected String house_id;
	/**
	 * 所属项目部
	 */
	protected String department;
	/**
	 * 承租人
	 */
	protected String rent_person;
	/**
	 * 房屋地址
	 */
	protected String address;
	/**
	 * 报销人
	 */
	protected String reimburse_person;
	/**
	 * 租房开始日期
	 */
	protected java.util.Date start_date;
	/**
	 * 报销日期
	 */
	protected java.util.Date reimburse_date;
	/**
	 * 租房结束日期
	 */
	protected java.util.Date end_date;
	/**
	 * 费用期间(开始)
	 */
	protected java.util.Date pay_start_date;
	/**
	 * 费用期间（结束）
	 */
	protected java.util.Date pay_end_date;
	/**
	 * 租房金额
	 */
	protected String rent_money;
	/**
	 * 房屋结构
	 */
	protected String house_type;

	protected String rent_type;

	protected String reimburse_money;

	protected String number_people;

	public String getNumber_people() {
		return number_people;
	}

	public void setNumber_people(String number_people) {
		this.number_people = number_people;
	}

	public String getReimburse_money() {
		return reimburse_money;
	}

	public void setReimburse_money(String reimburse_money) {
		this.reimburse_money = reimburse_money;
	}

	public String getHouse_type() {
		return house_type;
	}

	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}

	public String getRent_type() {
		return rent_type;
	}

	public void setRent_type(String rent_type) {
		this.rent_type = rent_type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * 返回 所属项目部ID
	 * 
	 * @return
	 */
	public String getDepartmentID() {
		return this.departmentID;
	}

	public void setReimburse_personID(String reimburse_personID) {
		this.reimburse_personID = reimburse_personID;
	}

	/**
	 * 返回 报销人ID
	 * 
	 * @return
	 */
	public String getReimburse_personID() {
		return this.reimburse_personID;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	/**
	 * 返回 租房编号
	 * 
	 * @return
	 */
	public String getHouse_id() {
		return this.house_id;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * 返回 所属项目部
	 * 
	 * @return
	 */
	public String getDepartment() {
		return this.department;
	}

	public void setRent_person(String rent_person) {
		this.rent_person = rent_person;
	}

	/**
	 * 返回 承租人
	 * 
	 * @return
	 */
	public String getRent_person() {
		return this.rent_person;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 返回 房屋地址
	 * 
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}

	public void setReimburse_person(String reimburse_person) {
		this.reimburse_person = reimburse_person;
	}

	/**
	 * 返回 报销人
	 * 
	 * @return
	 */
	public String getReimburse_person() {
		return this.reimburse_person;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * 返回 租房开始日期
	 * 
	 * @return
	 */
	public Date getStart_date() {
		return this.start_date;
	}

	public void setReimburse_date(java.util.Date reimburse_date) {
		this.reimburse_date = reimburse_date;
	}

	/**
	 * 返回 报销日期
	 * 
	 * @return
	 */
	public java.util.Date getReimburse_date() {
		return this.reimburse_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/**
	 * 返回 租房结束日期
	 * 
	 * @return
	 */
	public Date getEnd_date() {
		return this.end_date;
	}

	public void setPay_start_date(java.util.Date pay_start_date) {
		this.pay_start_date = pay_start_date;
	}

	/**
	 * 返回 费用期间(开始)
	 * 
	 * @return
	 */
	public java.util.Date getPay_start_date() {
		return this.pay_start_date;
	}

	public void setPay_end_date(java.util.Date pay_end_date) {
		this.pay_end_date = pay_end_date;
	}

	/**
	 * 返回 费用期间（结束）
	 * 
	 * @return
	 */
	public java.util.Date getPay_end_date() {
		return this.pay_end_date;
	}

	public void setRent_money(String rent_money) {
		this.rent_money = rent_money;
	}

	/**
	 * 返回 租房金额
	 * 
	 * @return
	 */
	public String getRent_money() {
		return this.rent_money;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HouseReimburse)) {
			return false;
		}
		HouseReimburse rhs = (HouseReimburse) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.departmentID, rhs.departmentID).append(this.reimburse_personID, rhs.reimburse_personID).append(this.house_id, rhs.house_id)
				.append(this.department, rhs.department).append(this.rent_person, rhs.rent_person).append(this.address, rhs.address).append(this.reimburse_person, rhs.reimburse_person)
				.append(this.start_date, rhs.start_date).append(this.reimburse_date, rhs.reimburse_date).append(this.end_date, rhs.end_date).append(this.pay_start_date, rhs.pay_start_date)
				.append(this.pay_end_date, rhs.pay_end_date).append(this.rent_money, rhs.rent_money).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.departmentID).append(this.reimburse_personID).append(this.house_id).append(this.department)
				.append(this.rent_person).append(this.address).append(this.reimburse_person).append(this.start_date).append(this.reimburse_date).append(this.end_date).append(this.pay_start_date)
				.append(this.pay_end_date).append(this.rent_money).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("departmentID", this.departmentID).append("reimburse_personID", this.reimburse_personID).append("house_id", this.house_id)
				.append("department", this.department).append("rent_person", this.rent_person).append("address", this.address).append("reimburse_person", this.reimburse_person)
				.append("start_date", this.start_date).append("reimburse_date", this.reimburse_date).append("end_date", this.end_date).append("pay_start_date", this.pay_start_date)
				.append("pay_end_date", this.pay_end_date).append("rent_money", this.rent_money).toString();
	}

}