package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractSingingRate extends BaseModel {
	private static final long serialVersionUID = 5135485531323089943L;

	/**
	 * 合同归属部门
	 */
	protected String departmentId;

	protected String department;

	protected String contracttype;

	protected Integer allContract;

	protected Integer cgContract;

	protected Integer fcgContract;

	protected Double allContractMoney;

	protected Double cgContractMoney;

	protected Double fcgContractMoney;

	protected Double investmentContractMoney;

	protected Integer allContractTypes;
	protected Integer allGd;

	protected Integer cgGd;

	protected Integer fcgGd;

	protected Integer waitGd;

	protected Double allRate;

	protected Double fcgRate;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getContracttype() {
		return contracttype;
	}

	public void setContracttype(String contracttype) {
		this.contracttype = contracttype;
	}

	public Integer getAllContract() {
		return allContract;
	}

	public void setAllContract(Integer allContract) {
		this.allContract = allContract;
	}

	public Integer getCgContract() {
		return cgContract;
	}

	public void setCgContract(Integer cgContract) {
		this.cgContract = cgContract;
	}

	public Integer getFgContract() {
		return fcgContract;
	}

	public void setFgContract(Integer fgContract) {
		this.fcgContract = fgContract;
	}

	public Double getAllContractMoney() {
		return allContractMoney;
	}

	public void setAllContractMoney(Double allContractMoney) {
		this.allContractMoney = allContractMoney;
	}

	public Double getCgContractMoney() {
		return cgContractMoney;
	}

	public void setCgContractMoney(Double cgContractMoney) {
		this.cgContractMoney = cgContractMoney;
	}

	public Double getFcgContractMoney() {
		return fcgContractMoney;
	}

	public void setFcgContractMoney(Double fcgContractMoney) {
		this.fcgContractMoney = fcgContractMoney;
	}

	public Double getInvestmentContractMoney() {
		return investmentContractMoney;
	}

	public void setInvestmentContractMoney(Double investmentContractMoney) {
		this.investmentContractMoney = investmentContractMoney;
	}

	public Integer getAllGd() {
		return allGd;
	}

	public void setAllGd(Integer allGd) {
		this.allGd = allGd;
	}

	public Integer getCgGd() {
		return cgGd;
	}

	public void setCgGd(Integer cgGd) {
		this.cgGd = cgGd;
	}

	public Integer getFcgGd() {
		return fcgGd;
	}

	public void setFcgGd(Integer fcgGd) {
		this.fcgGd = fcgGd;
	}

	public Double getAllRate() {
		return allRate;
	}

	public void setAllRate(Double allRate) {
		this.allRate = allRate;
	}

	public Double getFcgRate() {
		return fcgRate;
	}

	public void setFcgRate(Double fcgRate) {
		this.fcgRate = fcgRate;
	}

	public Integer getFcgContract() {
		return fcgContract;
	}

	public void setFcgContract(Integer fcgContract) {
		this.fcgContract = fcgContract;
	}

	public Integer getWaitGd() {
		return waitGd;
	}

	public void setWaitGd(Integer waitGd) {
		this.waitGd = waitGd;
	}

	public Integer getAllContractTypes() {
		return allContractTypes;
	}

	public void setAllContractTypes(Integer allContractTypes) {
		this.allContractTypes = allContractTypes;
	}

}