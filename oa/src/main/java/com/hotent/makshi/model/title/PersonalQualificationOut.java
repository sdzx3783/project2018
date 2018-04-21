package com.hotent.makshi.model.title;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:个人执业资格转出 Model对象
 */
public class PersonalQualificationOut extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *注册人员ID
	 */
	protected String  nameID;
	/**
	 *注册人员
	 */
	protected String  name;
	/**
	 *身份证号
	 */
	protected String  idcard;
	/**
	 *执业证书类型
	 */
	protected String  practis_certificate_type;
	/**
	 *证书专业
	 */
	protected String  major;
	/**
	 *执业证书编号
	 */
	protected String  practis_certificate_num;
	/**
	 *执业证书发证日期
	 */
	protected java.util.Date  practis_certificate_issue_date;
	/**
	 *执业证书签发单位
	 */
	protected String  practis_certificate_com;
	/**
	 *备注
	 */
	protected String  practis_certificate_remark;
	/**
	 *附件
	 */
	protected String  practis_certificate_file;
	/**
	 *注册证书编号
	 */
	protected String  regist_certificate_num;
	/**
	 *注册号
	 */
	protected String  regist_num;
	/**
	 *注册证书发证日期
	 */
	protected java.util.Date  regist_certificate_issue_date;
	/**
	 *注册证书有效日期
	 */
	protected java.util.Date  regist_certificate_effective_date;
	/**
	 *注册专业
	 */
	protected String  regist_major;
	/**
	 *执业印章号
	 */
	protected String  seal_num;
	/**
	 *转出日期
	 */
	protected java.util.Date  out_date;
	/**
	 *注册证书发证单位
	 */
	protected String  regist_certificate_com;
	/**
	 *最新注册类别
	 */
	protected String  regist_type;
	/**
	 *最新注册日期
	 */
	protected java.util.Date  regist_date;
	/**
	 *继续教育完成情况
	 */
	protected String  contine_edu_comple;
	/**
	 *备注
	 */
	protected String  regist_remark;
	/**
	 *附件
	 */
	protected String  regist_file;
	/**
	 *转出打印
	 */
	protected String  dispatch_content;
	
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
	 * 返回 注册人员ID
	 * @return
	 */
	public String getNameID() 
	{
		return this.nameID;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 注册人员
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setIdcard(String idcard) 
	{
		this.idcard = idcard;
	}
	/**
	 * 返回 身份证号
	 * @return
	 */
	public String getIdcard() 
	{
		return this.idcard;
	}
	public void setPractis_certificate_type(String practis_certificate_type) 
	{
		this.practis_certificate_type = practis_certificate_type;
	}
	/**
	 * 返回 执业证书类型
	 * @return
	 */
	public String getPractis_certificate_type() 
	{
		return this.practis_certificate_type;
	}
	public void setMajor(String major) 
	{
		this.major = major;
	}
	/**
	 * 返回 证书专业
	 * @return
	 */
	public String getMajor() 
	{
		return this.major;
	}
	public void setPractis_certificate_num(String practis_certificate_num) 
	{
		this.practis_certificate_num = practis_certificate_num;
	}
	/**
	 * 返回 执业证书编号
	 * @return
	 */
	public String getPractis_certificate_num() 
	{
		return this.practis_certificate_num;
	}
	public void setPractis_certificate_issue_date(java.util.Date practis_certificate_issue_date) 
	{
		this.practis_certificate_issue_date = practis_certificate_issue_date;
	}
	/**
	 * 返回 执业证书发证日期
	 * @return
	 */
	public java.util.Date getPractis_certificate_issue_date() 
	{
		return this.practis_certificate_issue_date;
	}
	public void setPractis_certificate_com(String practis_certificate_com) 
	{
		this.practis_certificate_com = practis_certificate_com;
	}
	/**
	 * 返回 执业证书签发单位
	 * @return
	 */
	public String getPractis_certificate_com() 
	{
		return this.practis_certificate_com;
	}
	public void setPractis_certificate_remark(String practis_certificate_remark) 
	{
		this.practis_certificate_remark = practis_certificate_remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getPractis_certificate_remark() 
	{
		return this.practis_certificate_remark;
	}
	public void setPractis_certificate_file(String practis_certificate_file) 
	{
		this.practis_certificate_file = practis_certificate_file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getPractis_certificate_file() 
	{
		return this.practis_certificate_file;
	}
	public void setRegist_certificate_num(String regist_certificate_num) 
	{
		this.regist_certificate_num = regist_certificate_num;
	}
	/**
	 * 返回 注册证书编号
	 * @return
	 */
	public String getRegist_certificate_num() 
	{
		return this.regist_certificate_num;
	}
	public void setRegist_num(String regist_num) 
	{
		this.regist_num = regist_num;
	}
	/**
	 * 返回 注册号
	 * @return
	 */
	public String getRegist_num() 
	{
		return this.regist_num;
	}
	public void setRegist_certificate_issue_date(java.util.Date regist_certificate_issue_date) 
	{
		this.regist_certificate_issue_date = regist_certificate_issue_date;
	}
	/**
	 * 返回 注册证书发证日期
	 * @return
	 */
	public java.util.Date getRegist_certificate_issue_date() 
	{
		return this.regist_certificate_issue_date;
	}
	public void setRegist_certificate_effective_date(java.util.Date regist_certificate_effective_date) 
	{
		this.regist_certificate_effective_date = regist_certificate_effective_date;
	}
	/**
	 * 返回 注册证书有效日期
	 * @return
	 */
	public java.util.Date getRegist_certificate_effective_date() 
	{
		return this.regist_certificate_effective_date;
	}
	public void setRegist_major(String regist_major) 
	{
		this.regist_major = regist_major;
	}
	/**
	 * 返回 注册专业
	 * @return
	 */
	public String getRegist_major() 
	{
		return this.regist_major;
	}
	public void setSeal_num(String seal_num) 
	{
		this.seal_num = seal_num;
	}
	/**
	 * 返回 执业印章号
	 * @return
	 */
	public String getSeal_num() 
	{
		return this.seal_num;
	}
	public void setOut_date(java.util.Date out_date) 
	{
		this.out_date = out_date;
	}
	/**
	 * 返回 转出日期
	 * @return
	 */
	public java.util.Date getOut_date() 
	{
		return this.out_date;
	}
	public void setRegist_certificate_com(String regist_certificate_com) 
	{
		this.regist_certificate_com = regist_certificate_com;
	}
	/**
	 * 返回 注册证书发证单位
	 * @return
	 */
	public String getRegist_certificate_com() 
	{
		return this.regist_certificate_com;
	}
	public void setRegist_type(String regist_type) 
	{
		this.regist_type = regist_type;
	}
	/**
	 * 返回 最新注册类别
	 * @return
	 */
	public String getRegist_type() 
	{
		return this.regist_type;
	}
	public void setRegist_date(java.util.Date regist_date) 
	{
		this.regist_date = regist_date;
	}
	/**
	 * 返回 最新注册日期
	 * @return
	 */
	public java.util.Date getRegist_date() 
	{
		return this.regist_date;
	}
	public void setContine_edu_comple(String contine_edu_comple) 
	{
		this.contine_edu_comple = contine_edu_comple;
	}
	/**
	 * 返回 继续教育完成情况
	 * @return
	 */
	public String getContine_edu_comple() 
	{
		return this.contine_edu_comple;
	}
	public void setRegist_remark(String regist_remark) 
	{
		this.regist_remark = regist_remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRegist_remark() 
	{
		return this.regist_remark;
	}
	public void setRegist_file(String regist_file) 
	{
		this.regist_file = regist_file;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getRegist_file() 
	{
		return this.regist_file;
	}
	public void setDispatch_content(String dispatch_content) 
	{
		this.dispatch_content = dispatch_content;
	}
	/**
	 * 返回 转出打印
	 * @return
	 */
	public String getDispatch_content() 
	{
		return this.dispatch_content;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PersonalQualificationOut)) 
		{
			return false;
		}
		PersonalQualificationOut rhs = (PersonalQualificationOut) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.nameID, rhs.nameID)
		.append(this.name, rhs.name)
		.append(this.idcard, rhs.idcard)
		.append(this.practis_certificate_type, rhs.practis_certificate_type)
		.append(this.major, rhs.major)
		.append(this.practis_certificate_num, rhs.practis_certificate_num)
		.append(this.practis_certificate_issue_date, rhs.practis_certificate_issue_date)
		.append(this.practis_certificate_com, rhs.practis_certificate_com)
		.append(this.practis_certificate_remark, rhs.practis_certificate_remark)
		.append(this.practis_certificate_file, rhs.practis_certificate_file)
		.append(this.regist_certificate_num, rhs.regist_certificate_num)
		.append(this.regist_num, rhs.regist_num)
		.append(this.regist_certificate_issue_date, rhs.regist_certificate_issue_date)
		.append(this.regist_certificate_effective_date, rhs.regist_certificate_effective_date)
		.append(this.regist_major, rhs.regist_major)
		.append(this.seal_num, rhs.seal_num)
		.append(this.out_date, rhs.out_date)
		.append(this.regist_certificate_com, rhs.regist_certificate_com)
		.append(this.regist_type, rhs.regist_type)
		.append(this.regist_date, rhs.regist_date)
		.append(this.contine_edu_comple, rhs.contine_edu_comple)
		.append(this.regist_remark, rhs.regist_remark)
		.append(this.regist_file, rhs.regist_file)
		.append(this.dispatch_content, rhs.dispatch_content)
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
		.append(this.name) 
		.append(this.idcard) 
		.append(this.practis_certificate_type) 
		.append(this.major) 
		.append(this.practis_certificate_num) 
		.append(this.practis_certificate_issue_date) 
		.append(this.practis_certificate_com) 
		.append(this.practis_certificate_remark) 
		.append(this.practis_certificate_file) 
		.append(this.regist_certificate_num) 
		.append(this.regist_num) 
		.append(this.regist_certificate_issue_date) 
		.append(this.regist_certificate_effective_date) 
		.append(this.regist_major) 
		.append(this.seal_num) 
		.append(this.out_date) 
		.append(this.regist_certificate_com) 
		.append(this.regist_type) 
		.append(this.regist_date) 
		.append(this.contine_edu_comple) 
		.append(this.regist_remark) 
		.append(this.regist_file) 
		.append(this.dispatch_content) 
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
		.append("name", this.name) 
		.append("idcard", this.idcard) 
		.append("practis_certificate_type", this.practis_certificate_type) 
		.append("major", this.major) 
		.append("practis_certificate_num", this.practis_certificate_num) 
		.append("practis_certificate_issue_date", this.practis_certificate_issue_date) 
		.append("practis_certificate_com", this.practis_certificate_com) 
		.append("practis_certificate_remark", this.practis_certificate_remark) 
		.append("practis_certificate_file", this.practis_certificate_file) 
		.append("regist_certificate_num", this.regist_certificate_num) 
		.append("regist_num", this.regist_num) 
		.append("regist_certificate_issue_date", this.regist_certificate_issue_date) 
		.append("regist_certificate_effective_date", this.regist_certificate_effective_date) 
		.append("regist_major", this.regist_major) 
		.append("seal_num", this.seal_num) 
		.append("out_date", this.out_date) 
		.append("regist_certificate_com", this.regist_certificate_com) 
		.append("regist_type", this.regist_type) 
		.append("regist_date", this.regist_date) 
		.append("contine_edu_comple", this.contine_edu_comple) 
		.append("regist_remark", this.regist_remark) 
		.append("regist_file", this.regist_file) 
		.append("dispatch_content", this.dispatch_content) 
		.toString();
	}

}