package com.hotent.makshi.model.title;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:职称申报 Model对象
 */
public class TitleDeclaration extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *工单号
	 */
	protected String  gloalFlowNo;
	/**
	 *发证机构
	 */
	protected String  issuing_authority;
	/**
	 *员工编号
	 */
	protected String  userNo;
	/**
	 *职称专业
	 */
	protected String  title_professional;
	/**
	 *姓名
	 */
	protected String  name;
	/**
	 *发证时间
	 */
	protected java.util.Date  issuing_time;
	/**
	 *职称名称
	 */
	protected String  title_name;
	/**
	 *评审委员会
	 */
	protected String  jury;
	/**
	 *证书编号
	 */
	protected String  certificate_num;
	/**
	 *评审时间
	 */
	protected java.util.Date  review_time;
	/**
	 *年份
	 */
	protected String  year;
	/**
	 *内容
	 */
	protected String  content;
	/**
	 *学时
	 */
	protected String  hours;
	/**
	 *附件
	 */
	protected String  file;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setGloalFlowNo(String gloalFlowNo) 
	{
		this.gloalFlowNo = gloalFlowNo;
	}
	/**
	 * 返回 工单号
	 * @return
	 */
	public String getGloalFlowNo() 
	{
		return this.gloalFlowNo;
	}
	public void setIssuing_authority(String issuing_authority) 
	{
		this.issuing_authority = issuing_authority;
	}
	/**
	 * 返回 发证机构
	 * @return
	 */
	public String getIssuing_authority() 
	{
		return this.issuing_authority;
	}
	public void setUserNo(String userNo) 
	{
		this.userNo = userNo;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUserNo() 
	{
		return this.userNo;
	}
	public void setTitle_professional(String title_professional) 
	{
		this.title_professional = title_professional;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getTitle_professional() 
	{
		return this.title_professional;
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
	public void setIssuing_time(java.util.Date issuing_time) 
	{
		this.issuing_time = issuing_time;
	}
	/**
	 * 返回 发证时间
	 * @return
	 */
	public java.util.Date getIssuing_time() 
	{
		return this.issuing_time;
	}
	public void setTitle_name(String title_name) 
	{
		this.title_name = title_name;
	}
	/**
	 * 返回 职称名称
	 * @return
	 */
	public String getTitle_name() 
	{
		return this.title_name;
	}
	public void setJury(String jury) 
	{
		this.jury = jury;
	}
	/**
	 * 返回 评审委员会
	 * @return
	 */
	public String getJury() 
	{
		return this.jury;
	}
	public void setCertificate_num(String certificate_num) 
	{
		this.certificate_num = certificate_num;
	}
	/**
	 * 返回 证书编号
	 * @return
	 */
	public String getCertificate_num() 
	{
		return this.certificate_num;
	}
	public void setReview_time(java.util.Date review_time) 
	{
		this.review_time = review_time;
	}
	/**
	 * 返回 评审时间
	 * @return
	 */
	public java.util.Date getReview_time() 
	{
		return this.review_time;
	}
	public void setYear(String year) 
	{
		this.year = year;
	}
	/**
	 * 返回 年份
	 * @return
	 */
	public String getYear() 
	{
		return this.year;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setHours(String hours) 
	{
		this.hours = hours;
	}
	/**
	 * 返回 学时
	 * @return
	 */
	public String getHours() 
	{
		return this.hours;
	}
	public void setFile(String file) 
	{
		this.file = file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getFile() 
	{
		return this.file;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof TitleDeclaration)) 
		{
			return false;
		}
		TitleDeclaration rhs = (TitleDeclaration) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.gloalFlowNo, rhs.gloalFlowNo)
		.append(this.issuing_authority, rhs.issuing_authority)
		.append(this.userNo, rhs.userNo)
		.append(this.title_professional, rhs.title_professional)
		.append(this.name, rhs.name)
		.append(this.issuing_time, rhs.issuing_time)
		.append(this.title_name, rhs.title_name)
		.append(this.jury, rhs.jury)
		.append(this.certificate_num, rhs.certificate_num)
		.append(this.review_time, rhs.review_time)
		.append(this.year, rhs.year)
		.append(this.content, rhs.content)
		.append(this.hours, rhs.hours)
		.append(this.file, rhs.file)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.gloalFlowNo) 
		.append(this.issuing_authority) 
		.append(this.userNo) 
		.append(this.title_professional) 
		.append(this.name) 
		.append(this.issuing_time) 
		.append(this.title_name) 
		.append(this.jury) 
		.append(this.certificate_num) 
		.append(this.review_time) 
		.append(this.year) 
		.append(this.content) 
		.append(this.hours) 
		.append(this.file) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("gloalFlowNo", this.gloalFlowNo) 
		.append("issuing_authority", this.issuing_authority) 
		.append("userNo", this.userNo) 
		.append("title_professional", this.title_professional) 
		.append("name", this.name) 
		.append("issuing_time", this.issuing_time) 
		.append("title_name", this.title_name) 
		.append("jury", this.jury) 
		.append("certificate_num", this.certificate_num) 
		.append("review_time", this.review_time) 
		.append("year", this.year) 
		.append("content", this.content) 
		.append("hours", this.hours) 
		.append("file", this.file) 
		.toString();
	}

}