package com.hotent.makshi.model.stationery;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.makshi.model.stock.StationeryStock;
/**
 * 对象功能:办公用品申购表 Model对象
 */
public class StationeryPurchase extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人
	 */
	protected String  user_name;
	/**
	 *一级部门
	 */
	protected String  first_department;
	/**
	 *工号
	 */
	protected String  user_id;
	
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *二级部门或项目部
	 */
	protected String  second_department;
	/**
	 *审批状态
	 */
	protected String  state;
	/**
	 *申请时间
	 */
	protected String  application_time;
	/**
	 *工单号
	 */
	protected String  globalflowno;
	protected Long  runId=0L;
	
	/**
	 * 用品名称
	 */
	protected String name;
	/**
	 * 数量
	 */
	protected Long purchase_number;
	/**
	 * 规格
	 * @return
	 */
	protected String specification;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPurchase_number() {
		return purchase_number;
	}
	public void setPurchase_number(Long purchase_number) {
		this.purchase_number = purchase_number;
	}

	/**
	 *办公用品申购表列表
	 */
	protected List<StationeryStock> stationeryStockList=new ArrayList<StationeryStock>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setFirst_department(String first_department) 
	{
		this.first_department = first_department;
	}
	/**
	 * 返回 一级部门
	 * @return
	 */
	public String getFirst_department() 
	{
		return this.first_department;
	}
	public void setUser_id(String user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getUser_id() 
	{
		return this.user_id;
	}
	public void setSecond_department(String second_department) 
	{
		this.second_department = second_department;
	}
	/**
	 * 返回 二级部门或项目部
	 * @return
	 */
	public String getSecond_department() 
	{
		return this.second_department;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 审批状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
	}
	public void setApplication_time(String application_time) 
	{
		this.application_time = application_time;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public String getApplication_time() 
	{
		return this.application_time;
	}
	public void setGlobalflowno(String globalflowno) 
	{
		this.globalflowno = globalflowno;
	}
	/**
	 * 返回 工单号
	 * @return
	 */
	public String getGlobalflowno() 
	{
		return this.globalflowno;
	}
	public void setStationeryStockList(List<StationeryStock> stationeryStockList) 
	{
		this.stationeryStockList = stationeryStockList;
	}
	/**
	 * 返回 办公用品申购表列表
	 * @return
	 */
	public List<StationeryStock> getStationeryStockList() 
	{
		return this.stationeryStockList;
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
		if (!(object instanceof StationeryPurchase)) 
		{
			return false;
		}
		StationeryPurchase rhs = (StationeryPurchase) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_name, rhs.user_name)
		.append(this.first_department, rhs.first_department)
		.append(this.user_id, rhs.user_id)
		.append(this.second_department, rhs.second_department)
		.append(this.state, rhs.state)
		.append(this.application_time, rhs.application_time)
		.append(this.globalflowno, rhs.globalflowno)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_name) 
		.append(this.first_department) 
		.append(this.user_id) 
		.append(this.second_department) 
		.append(this.state) 
		.append(this.application_time) 
		.append(this.globalflowno) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_name", this.user_name) 
		.append("first_department", this.first_department) 
		.append("user_id", this.user_id) 
		.append("second_department", this.second_department) 
		.append("state", this.state) 
		.append("application_time", this.application_time) 
		.append("globalflowno", this.globalflowno) 
		.toString();
	}

}