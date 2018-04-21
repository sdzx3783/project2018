package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:部门内调岗 Model对象
 */
public class Transferposition extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *员工姓名
	 */
	protected String  name;
	/**
	 *所属部门
	 */
	protected String  department;
	/**
	 *员工编号
	 */
	protected String  userId;
	/**
	 *短号
	 */
	protected String  shortNum;
	/**
	 *调整月份
	 */
	protected String  month;
	/**
	 *职称
	 */
	protected String  positional;
	/**
	 *调整原因
	 */
	protected String  reason;
	/**
	 *岗位
	 */
	protected String  beforePostion;
	protected String  beforePostionID;
	/**
	 *岗位工资
	 */
	protected String  beforeMoney;
	/**
	 *基本工资
	 */
	protected String  beforeBasicMoney;
	/**
	 *通讯费标准
	 */
	protected String  beforeTelStandard;
	/**
	 *岗位
	 */
	protected String  afterPostion;
	protected String  afterPostionID;
	/**
	 *岗位工资
	 */
	protected String  afterMoney;
	/**
	 *基本工资
	 */
	protected String  afterBasicMoney;
	/**
	 *通讯费标准
	 */
	protected String  afterTelStandard;
	/**
	 *本人意见
	 */
	protected String  myOpinion;
	
	protected String beforePost;
	
	
	//审批状体
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	
	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 员工姓名
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 所属部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUserId() 
	{
		return this.userId;
	}
	
	
	
	
	
	
	
	public String getShortNum() {
		return shortNum;
	}
	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}
	public void setMonth(String month) 
	{
		this.month = month;
	}
	/**
	 * 返回 调整月份
	 * @return
	 */
	public String getMonth() 
	{
		return this.month;
	}
	public void setPositional(String positional) 
	{
		this.positional = positional;
	}
	/**
	 * 返回 职称
	 * @return
	 */
	public String getPositional() 
	{
		return this.positional;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 调整原因
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setBeforePostion(String beforePostion) 
	{
		this.beforePostion = beforePostion;
	}
	/**
	 * 返回 岗位
	 * @return
	 */
	public String getBeforePostion() 
	{
		return this.beforePostion;
	}
	public void setBeforeMoney(String beforeMoney) 
	{
		this.beforeMoney = beforeMoney;
	}
	/**
	 * 返回 岗位工资
	 * @return
	 */
	public String getBeforeMoney() 
	{
		return this.beforeMoney;
	}
	public void setBeforeBasicMoney(String beforeBasicMoney) 
	{
		this.beforeBasicMoney = beforeBasicMoney;
	}
	/**
	 * 返回 基本工资
	 * @return
	 */
	public String getBeforeBasicMoney() 
	{
		return this.beforeBasicMoney;
	}
	public void setBeforeTelStandard(String beforeTelStandard) 
	{
		this.beforeTelStandard = beforeTelStandard;
	}
	/**
	 * 返回 通讯费标准
	 * @return
	 */
	public String getBeforeTelStandard() 
	{
		return this.beforeTelStandard;
	}
	public void setAfterPostion(String afterPostion) 
	{
		this.afterPostion = afterPostion;
	}
	/**
	 * 返回 岗位
	 * @return
	 */
	public String getAfterPostion() 
	{
		return this.afterPostion;
	}
	public void setAfterMoney(String afterMoney) 
	{
		this.afterMoney = afterMoney;
	}
	/**
	 * 返回 岗位工资
	 * @return
	 */
	public String getAfterMoney() 
	{
		return this.afterMoney;
	}
	public void setAfterBasicMoney(String afterBasicMoney) 
	{
		this.afterBasicMoney = afterBasicMoney;
	}
	/**
	 * 返回 基本工资
	 * @return
	 */
	public String getAfterBasicMoney() 
	{
		return this.afterBasicMoney;
	}
	public void setAfterTelStandard(String afterTelStandard) 
	{
		this.afterTelStandard = afterTelStandard;
	}
	/**
	 * 返回 通讯费标准
	 * @return
	 */
	public String getAfterTelStandard() 
	{
		return this.afterTelStandard;
	}
	public void setMyOpinion(String myOpinion) 
	{
		this.myOpinion = myOpinion;
	}
	/**
	 * 返回 本人意见
	 * @return
	 */
	public String getMyOpinion() 
	{
		return this.myOpinion;
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
		if (!(object instanceof Transferposition)) 
		{
			return false;
		}
		Transferposition rhs = (Transferposition) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.name, rhs.name)
		.append(this.department, rhs.department)
		.append(this.userId, rhs.userId)
		.append(this.shortNum, rhs.shortNum)
		.append(this.month, rhs.month)
		.append(this.positional, rhs.positional)
		.append(this.reason, rhs.reason)
		.append(this.beforePostion, rhs.beforePostion)
		.append(this.beforeMoney, rhs.beforeMoney)
		.append(this.beforeBasicMoney, rhs.beforeBasicMoney)
		.append(this.beforeTelStandard, rhs.beforeTelStandard)
		.append(this.afterPostion, rhs.afterPostion)
		.append(this.afterMoney, rhs.afterMoney)
		.append(this.afterBasicMoney, rhs.afterBasicMoney)
		.append(this.afterTelStandard, rhs.afterTelStandard)
		.append(this.myOpinion, rhs.myOpinion)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.name) 
		.append(this.department) 
		.append(this.userId) 
		.append(this.shortNum) 
		.append(this.month) 
		.append(this.positional) 
		.append(this.reason) 
		.append(this.beforePostion) 
		.append(this.beforeMoney) 
		.append(this.beforeBasicMoney) 
		.append(this.beforeTelStandard) 
		.append(this.afterPostion) 
		.append(this.afterMoney) 
		.append(this.afterBasicMoney) 
		.append(this.afterTelStandard) 
		.append(this.myOpinion) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("name", this.name) 
		.append("department", this.department) 
		.append("userId", this.userId) 
		.append("short", this.shortNum) 
		.append("month", this.month) 
		.append("positional", this.positional) 
		.append("reason", this.reason) 
		.append("beforePost", this.beforePostion) 
		.append("beforeMoney", this.beforeMoney) 
		.append("beforeBasicMoney", this.beforeBasicMoney) 
		.append("beforeTelStandard", this.beforeTelStandard) 
		.append("afterPost", this.afterPostion) 
		.append("afterMoney", this.afterMoney) 
		.append("afterBasicMoney", this.afterBasicMoney) 
		.append("afterTelStandard", this.afterTelStandard) 
		.append("myOpinion", this.myOpinion) 
		.toString();
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public String getBeforePostionID() {
		return beforePostionID;
	}
	public void setBeforePostionID(String beforePostionID) {
		this.beforePostionID = beforePostionID;
	}
	public String getAfterPostionID() {
		return afterPostionID;
	}
	public void setAfterPostionID(String afterPostionID) {
		this.afterPostionID = afterPostionID;
	}
	public String getBeforePost() {
		return beforePost;
	}
	public void setBeforePost(String beforePost) {
		this.beforePost = beforePost;
	}
	
	

}