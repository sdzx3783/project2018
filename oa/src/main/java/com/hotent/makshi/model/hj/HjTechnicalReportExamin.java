package com.hotent.makshi.model.hj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:技术报告报审 Model对象
 */
public class HjTechnicalReportExamin extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *上传人ID
	 */
	protected String  nameID;
	/**
	 *所属部门ID
	 */
	protected String  departmentID;
	/**
	 *专业负责人、专业技术人员ID
	 */
	protected String  checkfirstID;
	/**
	 *专业负责人/专业技术人员ID
	 */
	protected String  checksecondID;
	/**
	 *项目负责人、项目副负责人ID
	 */
	protected String  checkthirdID;
	/**
	 *总工程师、分管领导ID
	 */
	protected String  checkfourthID;
	/**
	 *单位主要负责人ID
	 */
	protected String  checkfifthID;
	/**
	 *上传人
	 */
	protected String  name;
	/**
	 *上传日期
	 */
	protected Date  uploadTime;
	/**
	 *所属部门
	 */
	protected String  department;
	/**
	 *项目名称
	 */
	protected String  itemName;
	/**
	 *报告名称
	 */
	protected String  reportName;
	/**
	 *报告内容
	 */
	protected String  reportContent;
	/**
	 *备注
	 */
	protected String  tag;
	/**
	 *专业负责人、专业技术人员
	 */
	protected String  checkfirst;
	/**
	 *专业负责人/专业技术人员
	 */
	protected String  checksecond;
	/**
	 *项目负责人、项目副负责人
	 */
	protected String  checkthird;
	/**
	 *总工程师、分管领导
	 */
	protected String  checkfourth;

	/**
	 *单位主要负责人
	 */
	protected String  checkfifth;
	

	/**
	 * 批准
	 */
	protected String unit_head;
	
	
	/**
	 *项目任务id
	 */
	protected String  projectTaskId;
	/**
	 *附件
	 */
	protected String  enclosure;
	/**
	 *是否需要审定
	 */
	protected String  validation;
	
	/**
	 * 报告类型
	 */
	protected String reporttype;

	public String getUnit_head() {
		return unit_head;
	}
	public void setUnit_head(String unit_head) {
		this.unit_head = unit_head;
	}
	
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	protected Long  runId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNameID(String nameID) 
	{
		this.nameID = nameID;
	}
	/**
	 * 返回 上传人ID
	 * @return
	 */
	public String getNameID() 
	{
		return this.nameID;
	}
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 所属部门ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setCheckfirstID(String checkfirstID) 
	{
		this.checkfirstID = checkfirstID;
	}
	/**
	 * 返回 专业负责人、专业技术人员ID
	 * @return
	 */
	public String getCheckfirstID() 
	{
		return this.checkfirstID;
	}
	public void setChecksecondID(String checksecondID) 
	{
		this.checksecondID = checksecondID;
	}
	/**
	 * 返回 专业负责人/专业技术人员ID
	 * @return
	 */
	public String getChecksecondID() 
	{
		return this.checksecondID;
	}
	public void setCheckthirdID(String checkthirdID) 
	{
		this.checkthirdID = checkthirdID;
	}
	/**
	 * 返回 项目负责人、项目副负责人ID
	 * @return
	 */
	public String getCheckthirdID() 
	{
		return this.checkthirdID;
	}
	public void setCheckfourthID(String checkfourthID) 
	{
		this.checkfourthID = checkfourthID;
	}
	/**
	 * 返回 总工程师、分管领导ID
	 * @return
	 */
	public String getCheckfourthID() 
	{
		return this.checkfourthID;
	}
	public void setCheckfifthID(String checkfifthID) 
	{
		this.checkfifthID = checkfifthID;
	}
	/**
	 * 返回 单位主要负责人ID
	 * @return
	 */
	public String getCheckfifthID() 
	{
		return this.checkfifthID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 上传人
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setUploadTime(java.util.Date uploadTime) 
	{
		this.uploadTime = uploadTime;
	}
	/**
	 * 返回 上传日期
	 * @return
	 */
	public java.util.Date getUploadTime() 
	{
		return this.uploadTime;
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
	public void setItemName(String itemName) 
	{
		this.itemName = itemName;
	}
	/**
	 * 返回 项目名称
	 * @return
	 */
	public String getItemName() 
	{
		return this.itemName;
	}
	public void setReportName(String reportName) 
	{
		this.reportName = reportName;
	}
	/**
	 * 返回 报告名称
	 * @return
	 */
	public String getReportName() 
	{
		return this.reportName;
	}
	public void setReportContent(String reportContent) 
	{
		this.reportContent = reportContent;
	}
	/**
	 * 返回 报告内容
	 * @return
	 */
	public String getReportContent() 
	{
		return this.reportContent;
	}
	public void setTag(String tag) 
	{
		this.tag = tag;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getTag() 
	{
		return this.tag;
	}
	public void setCheckfirst(String checkfirst) 
	{
		this.checkfirst = checkfirst;
	}
	/**
	 * 返回 专业负责人、专业技术人员
	 * @return
	 */
	public String getCheckfirst() 
	{
		return this.checkfirst;
	}
	public void setChecksecond(String checksecond) 
	{
		this.checksecond = checksecond;
	}
	/**
	 * 返回 专业负责人/专业技术人员
	 * @return
	 */
	public String getChecksecond() 
	{
		return this.checksecond;
	}
	public void setCheckthird(String checkthird) 
	{
		this.checkthird = checkthird;
	}
	/**
	 * 返回 项目负责人、项目副负责人
	 * @return
	 */
	public String getCheckthird() 
	{
		return this.checkthird;
	}
	public void setCheckfourth(String checkfourth) 
	{
		this.checkfourth = checkfourth;
	}
	/**
	 * 返回 总工程师、分管领导
	 * @return
	 */
	public String getCheckfourth() 
	{
		return this.checkfourth;
	}
	public void setCheckfifth(String checkfifth) 
	{
		this.checkfifth = checkfifth;
	}
	/**
	 * 返回 单位主要负责人
	 * @return
	 */
	public String getCheckfifth() 
	{
		return this.checkfifth;
	}
	public void setProjectTaskId(String projectTaskId) 
	{
		this.projectTaskId = projectTaskId;
	}
	/**
	 * 返回 项目任务id
	 * @return
	 */
	public String getProjectTaskId() 
	{
		return this.projectTaskId;
	}
	public void setEnclosure(String enclosure) 
	{
		this.enclosure = enclosure;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getEnclosure() 
	{
		return this.enclosure;
	}
	public void setValidation(String validation) 
	{
		this.validation = validation;
	}
	/**
	 * 返回 是否需要审定
	 * @return
	 */
	public String getValidation() 
	{
		return this.validation;
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
		if (!(object instanceof HjTechnicalReportExamin)) 
		{
			return false;
		}
		HjTechnicalReportExamin rhs = (HjTechnicalReportExamin) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.departmentID, rhs.departmentID)
		.append(this.checkfirstID, rhs.checkfirstID)
		.append(this.checksecondID, rhs.checksecondID)
		.append(this.checkthirdID, rhs.checkthirdID)
		.append(this.checkfourthID, rhs.checkfourthID)
		.append(this.checkfifthID, rhs.checkfifthID)
		.append(this.name, rhs.name)
		.append(this.uploadTime, rhs.uploadTime)
		.append(this.department, rhs.department)
		.append(this.itemName, rhs.itemName)
		.append(this.reportName, rhs.reportName)
		.append(this.reportContent, rhs.reportContent)
		.append(this.tag, rhs.tag)
		.append(this.checkfirst, rhs.checkfirst)
		.append(this.checksecond, rhs.checksecond)
		.append(this.checkthird, rhs.checkthird)
		.append(this.checkfourth, rhs.checkfourth)
		.append(this.checkfifth, rhs.checkfifth)
		.append(this.projectTaskId, rhs.projectTaskId)
		.append(this.enclosure, rhs.enclosure)
		.append(this.validation, rhs.validation)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.nameID) 
		.append(this.departmentID) 
		.append(this.checkfirstID) 
		.append(this.checksecondID) 
		.append(this.checkthirdID) 
		.append(this.checkfourthID) 
		.append(this.checkfifthID) 
		.append(this.name) 
		.append(this.uploadTime) 
		.append(this.department) 
		.append(this.itemName) 
		.append(this.reportName) 
		.append(this.reportContent) 
		.append(this.tag) 
		.append(this.checkfirst) 
		.append(this.checksecond) 
		.append(this.checkthird) 
		.append(this.checkfourth) 
		.append(this.checkfifth) 
		.append(this.projectTaskId) 
		.append(this.enclosure) 
		.append(this.validation) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("nameID", this.nameID) 
		.append("departmentID", this.departmentID) 
		.append("checkfirstID", this.checkfirstID) 
		.append("checksecondID", this.checksecondID) 
		.append("checkthirdID", this.checkthirdID) 
		.append("checkfourthID", this.checkfourthID) 
		.append("checkfifthID", this.checkfifthID) 
		.append("name", this.name) 
		.append("uploadTime", this.uploadTime) 
		.append("department", this.department) 
		.append("itemName", this.itemName) 
		.append("reportName", this.reportName) 
		.append("reportContent", this.reportContent) 
		.append("tag", this.tag) 
		.append("checkfirst", this.checkfirst) 
		.append("checksecond", this.checksecond) 
		.append("checkthird", this.checkthird) 
		.append("checkfourth", this.checkfourth) 
		.append("checkfifth", this.checkfifth) 
		.append("projectTaskId", this.projectTaskId) 
		.append("enclosure", this.enclosure) 
		.append("validation", this.validation) 
		.toString();
	}

}