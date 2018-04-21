package com.hotent.makshi.model.communicationExpense;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:通讯费消费记录表 Model对象
 */
public class CommunicationExpense extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *序号
	 */
	protected String  order_id;
	/**
	 *代付号码地市
	 */
	protected String  address;
	/**
	 *代付号码
	 */
	protected String  tel_number;
	/**
	 *代付用户名称
	 */
	protected String  user_name;
	/**
	 *代付额度
	 */
	protected String  limit;
	/**
	 *实际代付金额
	 */
	protected String  money;
	/**
	 *套餐
	 */
	protected String  thepackage;
	/**
	 *定额
	 */
	protected String  quota;
	/**
	 *限额
	 */
	protected String  max_quota;
	/**
	 *需缴费用
	 */
	protected String  need_pay;
	/**
	 *报销时间
	 */
	protected String  date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setOrder_id(String order_id) 
	{
		this.order_id = order_id;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getOrder_id() 
	{
		return this.order_id;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 代付号码地市
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setTel_number(String tel_number) 
	{
		this.tel_number = tel_number;
	}
	/**
	 * 返回 代付号码
	 * @return
	 */
	public String getTel_number() 
	{
		return this.tel_number;
	}
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 代付用户名称
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setLimit(String limit) 
	{
		this.limit = limit;
	}
	/**
	 * 返回 代付额度
	 * @return
	 */
	public String getLimit() 
	{
		return this.limit;
	}
	public void setMoney(String money) 
	{
		this.money = money;
	}
	/**
	 * 返回 实际代付金额
	 * @return
	 */
	public String getMoney() 
	{
		return this.money;
	}
	
	public String getThepackage() {
		return thepackage;
	}
	public void setThepackage(String thepackage) {
		this.thepackage = thepackage;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	/**
	 * 返回 定额
	 * @return
	 */
	public String getQuota() 
	{
		return this.quota;
	}
	public void setMax_quota(String max_quota) 
	{
		this.max_quota = max_quota;
	}
	/**
	 * 返回 限额
	 * @return
	 */
	public String getMax_quota() 
	{
		return this.max_quota;
	}
	public void setNeed_pay(String need_pay) 
	{
		this.need_pay = need_pay;
	}
	/**
	 * 返回 需缴费用
	 * @return
	 */
	public String getNeed_pay() 
	{
		return this.need_pay;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}
	/**
	 * 返回 报销时间
	 * @return
	 */
	public String getDate() 
	{
		return this.date;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CommunicationExpense)) 
		{
			return false;
		}
		CommunicationExpense rhs = (CommunicationExpense) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.order_id, rhs.order_id)
		.append(this.address, rhs.address)
		.append(this.tel_number, rhs.tel_number)
		.append(this.user_name, rhs.user_name)
		.append(this.limit, rhs.limit)
		.append(this.money, rhs.money)
		.append(this.thepackage, rhs.thepackage)
		.append(this.quota, rhs.quota)
		.append(this.max_quota, rhs.max_quota)
		.append(this.need_pay, rhs.need_pay)
		.append(this.date, rhs.date)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.order_id) 
		.append(this.address) 
		.append(this.tel_number) 
		.append(this.user_name) 
		.append(this.limit) 
		.append(this.money) 
		.append(this.thepackage) 
		.append(this.quota) 
		.append(this.max_quota) 
		.append(this.need_pay) 
		.append(this.date) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("order_id", this.order_id) 
		.append("address", this.address) 
		.append("tel_number", this.tel_number) 
		.append("user_name", this.user_name) 
		.append("limit", this.limit) 
		.append("money", this.money) 
		.append("thepackage", this.thepackage) 
		.append("quota", this.quota) 
		.append("max_quota", this.max_quota) 
		.append("need_pay", this.need_pay) 
		.append("date", this.date) 
		.toString();
	}

}