package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:部门调动表 Model对象
 */
public class DepartmentTransfer extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *调入部门ID
	 */
	protected String  in_departmentID;
	/**
	 *调入部门经理ID
	 */
	protected String  in_department_managerID;
	/**
	 *员工姓名
	 */
	protected String  user_name;
	/**
	 *调出部门
	 */
	protected String  out_department;
	/**
	 *员工编号
	 */
	protected Long  user_id;
	/**
	 *短号
	 */
	protected String  short_phone_number;
	/**
	 *调整月份
	 */
	protected String  out_month;
	/**
	 *职称
	 */
	protected String  professional;
	/**
	 *调整原因
	 */
	protected String  transfer_reason;
	/**
	 *调入部门
	 */
	protected String  in_department;
	/**
	 *调入部门经理
	 */
	protected String  in_department_manager;
	/**
	 *岗位
	 */
	protected String  post;
	/**
	 *岗位工资
	 */
	protected Long  post_salary;
	/**
	 *基本工资
	 */
	protected Long  base_salary;
	/**
	 *通讯费标准
	 */
	protected String  communication_standard;
	/**
	 *调整后岗位ID
	 */
	protected String  in_postID;
	/**
	 *调整后岗位
	 */
	protected String  in_post;
	/**
	 *调整后岗位工资
	 */
	protected Long  in_post_salary;
	/**
	 *调整后基本工资
	 */
	protected Long  in_base_salary;
	/**
	 *调整后通讯费标准
	 */
	protected String  in_communication_standard;
	/**
	 *本人意见
	 */
	protected String  user_option;
	protected String  beforePost;
	protected String  beforePostID;
	protected String  account;
	protected String  file;
	protected String  leader;
	protected String  leaderID;
	

	
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
	
	public void setIn_departmentID(String in_departmentID) 
	{
		this.in_departmentID = in_departmentID;
	}
	/**
	 * 返回 调入部门ID
	 * @return
	 */
	public String getIn_departmentID() 
	{
		return this.in_departmentID;
	}
	public void setIn_department_managerID(String in_department_managerID) 
	{
		this.in_department_managerID = in_department_managerID;
	}
	/**
	 * 返回 调入部门经理ID
	 * @return
	 */
	public String getIn_department_managerID() 
	{
		return this.in_department_managerID;
	}
	public void setUser_name(String user_name) 
	{
		this.user_name = user_name;
	}
	/**
	 * 返回 员工姓名
	 * @return
	 */
	public String getUser_name() 
	{
		return this.user_name;
	}
	public void setOut_department(String out_department) 
	{
		this.out_department = out_department;
	}
	/**
	 * 返回 调出部门
	 * @return
	 */
	public String getOut_department() 
	{
		return this.out_department;
	}
	public void setUser_id(Long user_id) 
	{
		this.user_id = user_id;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public Long getUser_id() 
	{
		return this.user_id;
	}
	public void setShort_phone_number(String short_phone_number) 
	{
		this.short_phone_number = short_phone_number;
	}
	/**
	 * 返回 短号
	 * @return
	 */
	public String getShort_phone_number() 
	{
		return this.short_phone_number;
	}
	public void setOut_month(String out_month) 
	{
		this.out_month = out_month;
	}
	/**
	 * 返回 调整月份
	 * @return
	 */
	public String getOut_month() 
	{
		return this.out_month;
	}
	public void setProfessional(String professional) 
	{
		this.professional = professional;
	}
	/**
	 * 返回 职称
	 * @return
	 */
	public String getProfessional() 
	{
		return this.professional;
	}
	public void setTransfer_reason(String transfer_reason) 
	{
		this.transfer_reason = transfer_reason;
	}
	/**
	 * 返回 调整原因
	 * @return
	 */
	public String getTransfer_reason() 
	{
		return this.transfer_reason;
	}
	public void setIn_department(String in_department) 
	{
		this.in_department = in_department;
	}
	/**
	 * 返回 调入部门
	 * @return
	 */
	public String getIn_department() 
	{
		return this.in_department;
	}
	public void setIn_department_manager(String in_department_manager) 
	{
		this.in_department_manager = in_department_manager;
	}
	/**
	 * 返回 调入部门经理
	 * @return
	 */
	public String getIn_department_manager() 
	{
		return this.in_department_manager;
	}
	public void setPost(String post) 
	{
		this.post = post;
	}
	/**
	 * 返回 岗位
	 * @return
	 */
	public String getPost() 
	{
		return this.post;
	}
	public void setPost_salary(Long post_salary) 
	{
		this.post_salary = post_salary;
	}
	/**
	 * 返回 岗位工资
	 * @return
	 */
	public Long getPost_salary() 
	{
		return this.post_salary;
	}
	public void setBase_salary(Long base_salary) 
	{
		this.base_salary = base_salary;
	}
	/**
	 * 返回 基本工资
	 * @return
	 */
	public Long getBase_salary() 
	{
		return this.base_salary;
	}
	public void setCommunication_standard(String communication_standard) 
	{
		this.communication_standard = communication_standard;
	}
	/**
	 * 返回 通讯费标准
	 * @return
	 */
	public String getCommunication_standard() 
	{
		return this.communication_standard;
	}
	public void setIn_postID(String in_postID) 
	{
		this.in_postID = in_postID;
	}
	/**
	 * 返回 调整后岗位ID
	 * @return
	 */
	public String getIn_postID() 
	{
		return this.in_postID;
	}
	public void setIn_post(String in_post) 
	{
		this.in_post = in_post;
	}
	/**
	 * 返回 调整后岗位
	 * @return
	 */
	public String getIn_post() 
	{
		return this.in_post;
	}
	public void setIn_post_salary(Long in_post_salary) 
	{
		this.in_post_salary = in_post_salary;
	}
	/**
	 * 返回 调整后岗位工资
	 * @return
	 */
	public Long getIn_post_salary() 
	{
		return this.in_post_salary;
	}
	public void setIn_base_salary(Long in_base_salary) 
	{
		this.in_base_salary = in_base_salary;
	}
	/**
	 * 返回 调整后基本工资
	 * @return
	 */
	public Long getIn_base_salary() 
	{
		return this.in_base_salary;
	}
	public void setIn_communication_standard(String in_communication_standard) 
	{
		this.in_communication_standard = in_communication_standard;
	}
	/**
	 * 返回 调整后通讯费标准
	 * @return
	 */
	public String getIn_communication_standard() 
	{
		return this.in_communication_standard;
	}
	public void setUser_option(String user_option) 
	{
		this.user_option = user_option;
	}
	/**
	 * 返回 本人意见
	 * @return
	 */
	public String getUser_option() 
	{
		return this.user_option;
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
		if (!(object instanceof DepartmentTransfer)) 
		{
			return false;
		}
		DepartmentTransfer rhs = (DepartmentTransfer) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.in_departmentID, rhs.in_departmentID)
		.append(this.in_department_managerID, rhs.in_department_managerID)
		.append(this.user_name, rhs.user_name)
		.append(this.out_department, rhs.out_department)
		.append(this.user_id, rhs.user_id)
		.append(this.short_phone_number, rhs.short_phone_number)
		.append(this.out_month, rhs.out_month)
		.append(this.professional, rhs.professional)
		.append(this.transfer_reason, rhs.transfer_reason)
		.append(this.in_department, rhs.in_department)
		.append(this.in_department_manager, rhs.in_department_manager)
		.append(this.post, rhs.post)
		.append(this.post_salary, rhs.post_salary)
		.append(this.base_salary, rhs.base_salary)
		.append(this.communication_standard, rhs.communication_standard)
		.append(this.in_postID, rhs.in_postID)
		.append(this.in_post, rhs.in_post)
		.append(this.in_post_salary, rhs.in_post_salary)
		.append(this.in_base_salary, rhs.in_base_salary)
		.append(this.in_communication_standard, rhs.in_communication_standard)
		.append(this.user_option, rhs.user_option)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.in_departmentID) 
		.append(this.in_department_managerID) 
		.append(this.user_name) 
		.append(this.out_department) 
		.append(this.user_id) 
		.append(this.short_phone_number) 
		.append(this.out_month) 
		.append(this.professional) 
		.append(this.transfer_reason) 
		.append(this.in_department) 
		.append(this.in_department_manager) 
		.append(this.post) 
		.append(this.post_salary) 
		.append(this.base_salary) 
		.append(this.communication_standard) 
		.append(this.in_postID) 
		.append(this.in_post) 
		.append(this.in_post_salary) 
		.append(this.in_base_salary) 
		.append(this.in_communication_standard) 
		.append(this.user_option) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("in_departmentID", this.in_departmentID) 
		.append("in_department_managerID", this.in_department_managerID) 
		.append("user_name", this.user_name) 
		.append("out_department", this.out_department) 
		.append("user_id", this.user_id) 
		.append("short_phone_number", this.short_phone_number) 
		.append("out_month", this.out_month) 
		.append("professional", this.professional) 
		.append("transfer_reason", this.transfer_reason) 
		.append("in_department", this.in_department) 
		.append("in_department_manager", this.in_department_manager) 
		.append("post", this.post) 
		.append("post_salary", this.post_salary) 
		.append("base_salary", this.base_salary) 
		.append("communication_standard", this.communication_standard) 
		.append("in_postID", this.in_postID) 
		.append("in_post", this.in_post) 
		.append("in_post_salary", this.in_post_salary) 
		.append("in_base_salary", this.in_base_salary) 
		.append("in_communication_standard", this.in_communication_standard) 
		.append("user_option", this.user_option) 
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
	public String getBeforePost() {
		return beforePost;
	}
	public void setBeforePost(String beforePost) {
		this.beforePost = beforePost;
	}
	public String getBeforePostID() {
		return beforePostID;
	}
	public void setBeforePostID(String beforePostID) {
		this.beforePostID = beforePostID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeaderID() {
		return leaderID;
	}
	public void setLeaderID(String leaderID) {
		this.leaderID = leaderID;
	}

	
	
	
}