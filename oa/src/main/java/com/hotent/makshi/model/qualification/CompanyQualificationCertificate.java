package com.hotent.makshi.model.qualification;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.core.model.BaseModel;

/**
 * 对象功能:公司资质证书 Model对象
 */
public class CompanyQualificationCertificate extends BaseModel {
	// 主键
	protected Long id;
	/**
	 * 证书编号
	 */
	protected String cno;
	/**
	 * 证书类型
	 */
	protected String ctype;
	/**
	 * 证书名称
	 */
	protected String cname;
	/**
	 * 发证机构
	 */
	protected String institution;
	/**
	 * 发证时间
	 */
	protected java.util.Date certificationtime;
	/**
	 * 有效期限
	 */
	protected java.util.Date certificationlimit;
	/**
	 * 承包范围
	 */
	protected String contractscope;
	/**
	 * 承包范围
	 */
	protected String remark;
	protected String attachment;

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * 状态 1删除 0正常
	 */
	protected int isdelete;

	public static final int status_useing = 0;
	public static final int status_overdue = 1;
	public static final int status_abandoned = 2;
	/**
	 * 状态：1.在用 2.过期 3.废弃
	 */
	protected int status;
	
	/**
	 * 借用状态：0：未借出；1：已借出
	 */
	protected String isborrowed;
	
	

	public String getIsborrowed() {
		return isborrowed;
	}

	public void setIsborrowed(String isborrowed) {
		this.isborrowed = isborrowed;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	protected String statusDesc;

	public String getStatusDesc() {
		if (status_useing == status)
			statusDesc = "在用";
		else if (status_overdue == status)
			statusDesc = "过期";
		else if (status_abandoned == status)
			statusDesc = "废弃";
		return statusDesc;
	}

	protected String isdeleteDesc;

	public String getIsdeleteDesc() {
		if (isdelete == 0)
			isdeleteDesc = "正常";
		else
			isdeleteDesc = "已删除";
		return isdeleteDesc;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	/**
	 * 返回 证书编号
	 * 
	 * @return
	 */
	public String getCno() {
		return this.cno;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	/**
	 * 返回 证书类型
	 * 
	 * @return
	 */
	public String getCtype() {
		return this.ctype;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * 返回 证书名称
	 * 
	 * @return
	 */
	public String getCname() {
		return this.cname;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * 返回 发证机构
	 * 
	 * @return
	 */
	public String getInstitution() {
		return this.institution;
	}

	public void setCertificationtime(java.util.Date certificationtime) {
		this.certificationtime = certificationtime;
	}

	/**
	 * 返回 发证时间
	 * 
	 * @return
	 */
	public java.util.Date getCertificationtime() {
		return this.certificationtime;
	}

	public void setCertificationlimit(java.util.Date certificationlimit) {
		this.certificationlimit = certificationlimit;
	}

	/**
	 * 返回 有效期限
	 * 
	 * @return
	 */
	public java.util.Date getCertificationlimit() {
		return this.certificationlimit;
	}

	public void setContractscope(String contractscope) {
		this.contractscope = contractscope;
	}

	/**
	 * 返回 承包范围
	 * 
	 * @return
	 */
	public String getContractscope() {
		return this.contractscope;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回 承包范围
	 * 
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CompanyQualificationCertificate)) {
			return false;
		}
		CompanyQualificationCertificate rhs = (CompanyQualificationCertificate) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.cno, rhs.cno).append(this.ctype, rhs.ctype).append(this.cname, rhs.cname).append(this.institution, rhs.institution)
				.append(this.certificationtime, rhs.certificationtime).append(this.certificationlimit, rhs.certificationlimit).append(this.contractscope, rhs.contractscope)
				.append(this.remark, rhs.remark).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.cno).append(this.ctype).append(this.cname).append(this.institution).append(this.certificationtime)
				.append(this.certificationlimit).append(this.contractscope).append(this.remark).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("cno", this.cno).append("ctype", this.ctype).append("cname", this.cname).append("institution", this.institution)
				.append("certificationtime", this.certificationtime).append("certificationlimit", this.certificationlimit).append("contractscope", this.contractscope).append("remark", this.remark)
				.toString();
	}

}