package com.hotent.makshi.model.operation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:运维部检测报告表 Model对象
 */
public class TestReport extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *编写人员ID
	 */
	protected String  editorID;
	/**
	 *提交人ID
	 */
	protected String  applicantID;
	/**
	 *报告名称
	 */
	protected String  name;
	/**
	 *项目名称
	 */
	protected String  project;
	/**
	 *编写人员
	 */
	protected String  editor;
	/**
	 *开始日期
	 */
	protected java.util.Date  startDate;
	/**
	 *完成日期
	 */
	protected java.util.Date  endDate;
	/**
	 *提交人
	 */
	protected String  applicant;
	/**
	 *提交日期
	 */
	protected java.util.Date  appliDate;
	/**
	 *打印日期
	 */
	protected java.util.Date  printDate;
	/**
	 *盖章完成日期
	 */
	protected java.util.Date  sealDate;
	/**
	 *提交业主日期
	 */
	protected java.util.Date  transferDate;
	/**
	 *备注
	 */
	protected String  remark;
	/**
	 *附件
	 */
	protected String  attachment;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEditorID(String editorID) 
	{
		this.editorID = editorID;
	}
	/**
	 * 返回 编写人员ID
	 * @return
	 */
	public String getEditorID() 
	{
		return this.editorID;
	}
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 提交人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 报告名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setProject(String project) 
	{
		this.project = project;
	}
	/**
	 * 返回 项目名称
	 * @return
	 */
	public String getProject() 
	{
		return this.project;
	}
	public void setEditor(String editor) 
	{
		this.editor = editor;
	}
	/**
	 * 返回 编写人员
	 * @return
	 */
	public String getEditor() 
	{
		return this.editor;
	}
	public void setStartDate(java.util.Date startDate) 
	{
		this.startDate = startDate;
	}
	/**
	 * 返回 开始日期
	 * @return
	 */
	public java.util.Date getStartDate() 
	{
		return this.startDate;
	}
	public void setEndDate(java.util.Date endDate) 
	{
		this.endDate = endDate;
	}
	/**
	 * 返回 完成日期
	 * @return
	 */
	public java.util.Date getEndDate() 
	{
		return this.endDate;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 提交人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setAppliDate(java.util.Date appliDate) 
	{
		this.appliDate = appliDate;
	}
	/**
	 * 返回 提交日期
	 * @return
	 */
	public java.util.Date getAppliDate() 
	{
		return this.appliDate;
	}
	public void setPrintDate(java.util.Date printDate) 
	{
		this.printDate = printDate;
	}
	/**
	 * 返回 打印日期
	 * @return
	 */
	public java.util.Date getPrintDate() 
	{
		return this.printDate;
	}
	public void setSealDate(java.util.Date sealDate) 
	{
		this.sealDate = sealDate;
	}
	/**
	 * 返回 盖章完成日期
	 * @return
	 */
	public java.util.Date getSealDate() 
	{
		return this.sealDate;
	}
	public void setTransferDate(java.util.Date transferDate) 
	{
		this.transferDate = transferDate;
	}
	/**
	 * 返回 提交业主日期
	 * @return
	 */
	public java.util.Date getTransferDate() 
	{
		return this.transferDate;
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
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof TestReport)) 
		{
			return false;
		}
		TestReport rhs = (TestReport) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.editorID, rhs.editorID)
		.append(this.applicantID, rhs.applicantID)
		.append(this.name, rhs.name)
		.append(this.project, rhs.project)
		.append(this.editor, rhs.editor)
		.append(this.startDate, rhs.startDate)
		.append(this.endDate, rhs.endDate)
		.append(this.applicant, rhs.applicant)
		.append(this.appliDate, rhs.appliDate)
		.append(this.printDate, rhs.printDate)
		.append(this.sealDate, rhs.sealDate)
		.append(this.transferDate, rhs.transferDate)
		.append(this.remark, rhs.remark)
		.append(this.attachment, rhs.attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.editorID) 
		.append(this.applicantID) 
		.append(this.name) 
		.append(this.project) 
		.append(this.editor) 
		.append(this.startDate) 
		.append(this.endDate) 
		.append(this.applicant) 
		.append(this.appliDate) 
		.append(this.printDate) 
		.append(this.sealDate) 
		.append(this.transferDate) 
		.append(this.remark) 
		.append(this.attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("editorID", this.editorID) 
		.append("applicantID", this.applicantID) 
		.append("name", this.name) 
		.append("project", this.project) 
		.append("editor", this.editor) 
		.append("startDate", this.startDate) 
		.append("endDate", this.endDate) 
		.append("applicant", this.applicant) 
		.append("appliDate", this.appliDate) 
		.append("printDate", this.printDate) 
		.append("sealDate", this.sealDate) 
		.append("transferDate", this.transferDate) 
		.append("remark", this.remark) 
		.append("attachment", this.attachment) 
		.toString();
	}

}