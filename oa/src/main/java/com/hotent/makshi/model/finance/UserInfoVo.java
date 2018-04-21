package com.hotent.makshi.model.finance;

import java.util.Date;

import com.hotent.platform.model.system.SysUser;

public class UserInfoVo extends SysUser{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5585785776420903587L;
	/**
	 *学历
	 */
	protected String  education;
	/**
	 *专业
	 */
	protected String  major;
	/**
	 *职称
	 */
	protected String  positional;
	/**
	 * 注册持证情况
	 */
	protected String  certificate;
	
	/**
	 *籍贯
	 */
	protected String  address;
	/**
	 *毕业时间
	 */
	protected Date graduateTime;

	/**
	 *基本工资
	 */
	protected Double  baseSalary;
	/**
	 *岗位工资
	 */
	protected Double  posSalary;
	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getPosSalary() {
		return posSalary;
	}

	public void setPosSalary(Double posSalary) {
		this.posSalary = posSalary;
	}

	public Date getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPositional() {
		return positional;
	}

	public void setPositional(String positional) {
		this.positional = positional;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
