package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:信息变更 Model对象
 */
public class Infochange extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *姓名
	 */
	protected String  name;
	/**
	 *部门
	 */
	protected String  department;
	/**
	 *公司短号
	 */
	protected String  shortNum;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *学历变更前
	 */
	protected String  eduBefore;
	/**
	 *学历变更后
	 */
	protected String  eduAfter;
	/**
	 *变更前
	 */
	protected String  schoolBefore;
	/**
	 *变更后
	 */
	protected String  schoolAfter;
	/**
	 *变更前
	 */
	protected String  majorBefore;
	/**
	 *变更后
	 */
	protected String  majorAfter;
	/**
	 *婚姻状况
	 */
	protected String  marrigeStatusBefore;
	/**
	 *婚姻状况（变更后）
	 */
	protected String  marrigeStatusAfter;
	/**
	 *配偶姓名
	 */
	protected String  spouseNameBefore;
	/**
	 *配偶姓名（更改后）
	 */
	protected String  spouseNameAfter;
	/**
	 *配偶身份证号码
	 */
	protected String  spouseIdBefore;
	/**
	 *配偶身份证（变更后）
	 */
	protected String  spouseIdAfter;
	/**
	 *开始时间
	 */
	protected java.util.Date  startDateBefore;
	/**
	 *变更后开始日期
	 */
	protected java.util.Date  startDateAfter;
	/**
	 *结束时间
	 */
	protected java.util.Date  endDateBefore;
	/**
	 *变更后结束日期
	 */
	protected java.util.Date  endDateAfter;
	/**
	 *附件(学历相关证件)
	 */
	protected String  eduAttachment;
	/**
	 *职称编号
	 */
	protected String  positionIdBefore;
	/**
	 *职称编号(更改后)
	 */
	protected String  positionIdAfter;
	/**
	 *职称名称
	 */
	protected String  positonNameBefore;
	/**
	 *职称名称（更改后）
	 */
	protected String  positionNameAfter;
	/**
	 *发证机构
	 */
	protected String  organizationBefore;
	/**
	 *发证机构（更改后）
	 */
	protected String  organizationAfter;
	/**
	 *职称专业
	 */
	protected String  positionMajorBofer;
	/**
	 *职称专业（更改后）
	 */
	protected String  positionMajorAfter;
	/**
	 *取得职称时间
	 */
	protected java.util.Date  positionGetDateBefore;
	/**
	 *取得职称时间
	 */
	protected java.util.Date  positionGetDateAfter;
	/**
	 *附件(职称相关证件)
	 */
	protected String  positionalAttachment;
	
	
	protected String addressType;
	
	
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 姓名
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
	 * 返回 部门
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setShortNum(String shortNum) 
	{
		this.shortNum = shortNum;
	}
	/**
	 * 返回 公司短号
	 * @return
	 */
	public String getShortNum() 
	{
		return this.shortNum;
	}
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
	}
	public void setEduBefore(String eduBefore) 
	{
		this.eduBefore = eduBefore;
	}
	/**
	 * 返回 学历变更前
	 * @return
	 */
	public String getEduBefore() 
	{
		return this.eduBefore;
	}
	public void setEduAfter(String eduAfter) 
	{
		this.eduAfter = eduAfter;
	}
	/**
	 * 返回 学历变更后
	 * @return
	 */
	public String getEduAfter() 
	{
		return this.eduAfter;
	}
	public void setSchoolBefore(String schoolBefore) 
	{
		this.schoolBefore = schoolBefore;
	}
	/**
	 * 返回 变更前
	 * @return
	 */
	public String getSchoolBefore() 
	{
		return this.schoolBefore;
	}
	public void setSchoolAfter(String schoolAfter) 
	{
		this.schoolAfter = schoolAfter;
	}
	/**
	 * 返回 变更后
	 * @return
	 */
	public String getSchoolAfter() 
	{
		return this.schoolAfter;
	}
	public void setMajorBefore(String majorBefore) 
	{
		this.majorBefore = majorBefore;
	}
	/**
	 * 返回 变更前
	 * @return
	 */
	public String getMajorBefore() 
	{
		return this.majorBefore;
	}
	public void setMajorAfter(String majorAfter) 
	{
		this.majorAfter = majorAfter;
	}
	/**
	 * 返回 变更后
	 * @return
	 */
	public String getMajorAfter() 
	{
		return this.majorAfter;
	}
	public void setMarrigeStatusBefore(String marrigeStatusBefore) 
	{
		this.marrigeStatusBefore = marrigeStatusBefore;
	}
	/**
	 * 返回 婚姻状况
	 * @return
	 */
	public String getMarrigeStatusBefore() 
	{
		return this.marrigeStatusBefore;
	}
	public void setMarrigeStatusAfter(String marrigeStatusAfter) 
	{
		this.marrigeStatusAfter = marrigeStatusAfter;
	}
	/**
	 * 返回 婚姻状况（变更后）
	 * @return
	 */
	public String getMarrigeStatusAfter() 
	{
		return this.marrigeStatusAfter;
	}
	public void setSpouseNameBefore(String spouseNameBefore) 
	{
		this.spouseNameBefore = spouseNameBefore;
	}
	/**
	 * 返回 配偶姓名
	 * @return
	 */
	public String getSpouseNameBefore() 
	{
		return this.spouseNameBefore;
	}
	public void setSpouseNameAfter(String spouseNameAfter) 
	{
		this.spouseNameAfter = spouseNameAfter;
	}
	/**
	 * 返回 配偶姓名（更改后）
	 * @return
	 */
	public String getSpouseNameAfter() 
	{
		return this.spouseNameAfter;
	}
	public void setSpouseIdBefore(String spouseIdBefore) 
	{
		this.spouseIdBefore = spouseIdBefore;
	}
	/**
	 * 返回 配偶身份证号码
	 * @return
	 */
	public String getSpouseIdBefore() 
	{
		return this.spouseIdBefore;
	}
	public void setSpouseIdAfter(String spouseIdAfter) 
	{
		this.spouseIdAfter = spouseIdAfter;
	}
	/**
	 * 返回 配偶身份证（变更后）
	 * @return
	 */
	public String getSpouseIdAfter() 
	{
		return this.spouseIdAfter;
	}
	public void setStartDateBefore(java.util.Date startDateBefore) 
	{
		this.startDateBefore = startDateBefore;
	}
	/**
	 * 返回 开始时间
	 * @return
	 */
	public java.util.Date getStartDateBefore() 
	{
		return this.startDateBefore;
	}
	public void setStartDateAfter(java.util.Date startDateAfter) 
	{
		this.startDateAfter = startDateAfter;
	}
	/**
	 * 返回 变更后开始日期
	 * @return
	 */
	public java.util.Date getStartDateAfter() 
	{
		return this.startDateAfter;
	}
	public void setEndDateBefore(java.util.Date endDateBefore) 
	{
		this.endDateBefore = endDateBefore;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public java.util.Date getEndDateBefore() 
	{
		return this.endDateBefore;
	}
	public void setEndDateAfter(java.util.Date endDateAfter) 
	{
		this.endDateAfter = endDateAfter;
	}
	/**
	 * 返回 变更后结束日期
	 * @return
	 */
	public java.util.Date getEndDateAfter() 
	{
		return this.endDateAfter;
	}
	public void setEduAttachment(String eduAttachment) 
	{
		this.eduAttachment = eduAttachment;
	}
	/**
	 * 返回 附件(学历相关证件)
	 * @return
	 */
	public String getEduAttachment() 
	{
		return this.eduAttachment;
	}
	public void setPositionIdBefore(String positionIdBefore) 
	{
		this.positionIdBefore = positionIdBefore;
	}
	/**
	 * 返回 职称编号
	 * @return
	 */
	public String getPositionIdBefore() 
	{
		return this.positionIdBefore;
	}
	public void setPositionIdAfter(String positionIdAfter) 
	{
		this.positionIdAfter = positionIdAfter;
	}
	/**
	 * 返回 职称编号(更改后)
	 * @return
	 */
	public String getPositionIdAfter() 
	{
		return this.positionIdAfter;
	}
	public void setPositonNameBefore(String positonNameBefore) 
	{
		this.positonNameBefore = positonNameBefore;
	}
	/**
	 * 返回 职称名称
	 * @return
	 */
	public String getPositonNameBefore() 
	{
		return this.positonNameBefore;
	}
	public void setPositionNameAfter(String positionNameAfter) 
	{
		this.positionNameAfter = positionNameAfter;
	}
	/**
	 * 返回 职称名称（更改后）
	 * @return
	 */
	public String getPositionNameAfter() 
	{
		return this.positionNameAfter;
	}
	public void setOrganizationBefore(String organizationBefore) 
	{
		this.organizationBefore = organizationBefore;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getOrganizationBefore() 
	{
		return this.organizationBefore;
	}
	public void setOrganizationAfter(String organizationAfter) 
	{
		this.organizationAfter = organizationAfter;
	}
	/**
	 * 返回 发证机构（更改后）
	 * @return
	 */
	public String getOrganizationAfter() 
	{
		return this.organizationAfter;
	}
	public void setPositionMajorBofer(String positionMajorBofer) 
	{
		this.positionMajorBofer = positionMajorBofer;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getPositionMajorBofer() 
	{
		return this.positionMajorBofer;
	}
	public void setPositionMajorAfter(String positionMajorAfter) 
	{
		this.positionMajorAfter = positionMajorAfter;
	}
	/**
	 * 返回 职称专业（更改后）
	 * @return
	 */
	public String getPositionMajorAfter() 
	{
		return this.positionMajorAfter;
	}
	public void setPositionGetDateBefore(java.util.Date positionGetDateBefore) 
	{
		this.positionGetDateBefore = positionGetDateBefore;
	}
	/**
	 * 返回 取得职称时间
	 * @return
	 */
	public java.util.Date getPositionGetDateBefore() 
	{
		return this.positionGetDateBefore;
	}
	public void setPositionGetDateAfter(java.util.Date positionGetDateAfter) 
	{
		this.positionGetDateAfter = positionGetDateAfter;
	}
	/**
	 * 返回 取得职称时间
	 * @return
	 */
	public java.util.Date getPositionGetDateAfter() 
	{
		return this.positionGetDateAfter;
	}
	public void setPositionalAttachment(String positionalAttachment) 
	{
		this.positionalAttachment = positionalAttachment;
	}
	/**
	 * 返回 附件(职称相关证件)
	 * @return
	 */
	public String getPositionalAttachment() 
	{
		return this.positionalAttachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Infochange)) 
		{
			return false;
		}
		Infochange rhs = (Infochange) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.attachment, rhs.attachment)
		.append(this.name, rhs.name)
		.append(this.department, rhs.department)
		.append(this.shortNum, rhs.shortNum)
		.append(this.account, rhs.account)
		.append(this.eduBefore, rhs.eduBefore)
		.append(this.eduAfter, rhs.eduAfter)
		.append(this.schoolBefore, rhs.schoolBefore)
		.append(this.schoolAfter, rhs.schoolAfter)
		.append(this.majorBefore, rhs.majorBefore)
		.append(this.majorAfter, rhs.majorAfter)
		.append(this.marrigeStatusBefore, rhs.marrigeStatusBefore)
		.append(this.marrigeStatusAfter, rhs.marrigeStatusAfter)
		.append(this.spouseNameBefore, rhs.spouseNameBefore)
		.append(this.spouseNameAfter, rhs.spouseNameAfter)
		.append(this.spouseIdBefore, rhs.spouseIdBefore)
		.append(this.spouseIdAfter, rhs.spouseIdAfter)
		.append(this.startDateBefore, rhs.startDateBefore)
		.append(this.startDateAfter, rhs.startDateAfter)
		.append(this.endDateBefore, rhs.endDateBefore)
		.append(this.endDateAfter, rhs.endDateAfter)
		.append(this.eduAttachment, rhs.eduAttachment)
		.append(this.positionIdBefore, rhs.positionIdBefore)
		.append(this.positionIdAfter, rhs.positionIdAfter)
		.append(this.positonNameBefore, rhs.positonNameBefore)
		.append(this.positionNameAfter, rhs.positionNameAfter)
		.append(this.organizationBefore, rhs.organizationBefore)
		.append(this.organizationAfter, rhs.organizationAfter)
		.append(this.positionMajorBofer, rhs.positionMajorBofer)
		.append(this.positionMajorAfter, rhs.positionMajorAfter)
		.append(this.positionGetDateBefore, rhs.positionGetDateBefore)
		.append(this.positionGetDateAfter, rhs.positionGetDateAfter)
		.append(this.positionalAttachment, rhs.positionalAttachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.attachment) 
		.append(this.name) 
		.append(this.department) 
		.append(this.shortNum) 
		.append(this.account) 
		.append(this.eduBefore) 
		.append(this.eduAfter) 
		.append(this.schoolBefore) 
		.append(this.schoolAfter) 
		.append(this.majorBefore) 
		.append(this.majorAfter) 
		.append(this.marrigeStatusBefore) 
		.append(this.marrigeStatusAfter) 
		.append(this.spouseNameBefore) 
		.append(this.spouseNameAfter) 
		.append(this.spouseIdBefore) 
		.append(this.spouseIdAfter) 
		.append(this.startDateBefore) 
		.append(this.startDateAfter) 
		.append(this.endDateBefore) 
		.append(this.endDateAfter) 
		.append(this.eduAttachment) 
		.append(this.positionIdBefore) 
		.append(this.positionIdAfter) 
		.append(this.positonNameBefore) 
		.append(this.positionNameAfter) 
		.append(this.organizationBefore) 
		.append(this.organizationAfter) 
		.append(this.positionMajorBofer) 
		.append(this.positionMajorAfter) 
		.append(this.positionGetDateBefore) 
		.append(this.positionGetDateAfter) 
		.append(this.positionalAttachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("attachment", this.attachment) 
		.append("name", this.name) 
		.append("department", this.department) 
		.append("shortNum", this.shortNum) 
		.append("account", this.account) 
		.append("eduBefore", this.eduBefore) 
		.append("eduAfter", this.eduAfter) 
		.append("schoolBefore", this.schoolBefore) 
		.append("schoolAfter", this.schoolAfter) 
		.append("majorBefore", this.majorBefore) 
		.append("majorAfter", this.majorAfter) 
		.append("marrigeStatusBefore", this.marrigeStatusBefore) 
		.append("marrigeStatusAfter", this.marrigeStatusAfter) 
		.append("spouseNameBefore", this.spouseNameBefore) 
		.append("spouseNameAfter", this.spouseNameAfter) 
		.append("spouseIdBefore", this.spouseIdBefore) 
		.append("spouseIdAfter", this.spouseIdAfter) 
		.append("startDateBefore", this.startDateBefore) 
		.append("startDateAfter", this.startDateAfter) 
		.append("endDateBefore", this.endDateBefore) 
		.append("endDateAfter", this.endDateAfter) 
		.append("eduAttachment", this.eduAttachment) 
		.append("positionIdBefore", this.positionIdBefore) 
		.append("positionIdAfter", this.positionIdAfter) 
		.append("positonNameBefore", this.positonNameBefore) 
		.append("positionNameAfter", this.positionNameAfter) 
		.append("organizationBefore", this.organizationBefore) 
		.append("organizationAfter", this.organizationAfter) 
		.append("positionMajorBofer", this.positionMajorBofer) 
		.append("positionMajorAfter", this.positionMajorAfter) 
		.append("positionGetDateBefore", this.positionGetDateBefore) 
		.append("positionGetDateAfter", this.positionGetDateAfter) 
		.append("positionalAttachment", this.positionalAttachment) 
		.toString();
	}

}