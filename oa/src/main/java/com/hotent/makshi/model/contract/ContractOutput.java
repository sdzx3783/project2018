package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractOutput extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578134226595522803L;
	//合同类型
	protected String  contractType;
	//合同编号
	protected String contractNum;
	//合同名称
	protected String contractName;
	//总投资
	protected Double totalInvestment;
	//产值
	protected String theOutput;
	//甲方
	protected String firstParty;
	//合同归属部门
	protected String department;
	//项目总监
	protected String projectDirector;
	//项目负责人
	protected String projectLeader;	
	//合同经手人
	protected String contractHandler;
	//第一季度
	protected Double sesonOne;
	//第二季度
	protected Double sesonTwo;
	//第三季度
	protected Double sesonThree;
	//第四季度
	protected Double sesonFour;
	//年产值
	protected Double yearPut;
	
	protected Long Id;
	
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Double getTotalInvestment() {
		return totalInvestment;
	}
	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}
	public String getTheOutput() {
		return theOutput;
	}
	public void setTheOutput(String theOutput) {
		this.theOutput = theOutput;
	}
	public String getFirstParty() {
		return firstParty;
	}
	public void setFirstParty(String firstParty) {
		this.firstParty = firstParty;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProjectDirector() {
		return projectDirector;
	}
	public void setProjectDirector(String projectDirector) {
		this.projectDirector = projectDirector;
	}
	public String getProjectLeader() {
		return projectLeader;
	}
	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}
	public String getContractHandler() {
		return contractHandler;
	}
	public void setContractHandler(String contractHandler) {
		this.contractHandler = contractHandler;
	}
	public Double getSesonOne() {
		return sesonOne;
	}
	public void setSesonOne(Double sesonOne) {
		this.sesonOne = sesonOne;
	}
	public Double getSesonTwo() {
		return sesonTwo;
	}
	public void setSesonTwo(Double sesonTwo) {
		this.sesonTwo = sesonTwo;
	}
	public Double getSesonThree() {
		return sesonThree;
	}
	public void setSesonThree(Double sesonThree) {
		this.sesonThree = sesonThree;
	}
	public Double getSesonFour() {
		return sesonFour;
	}
	public void setSesonFour(Double sesonFour) {
		this.sesonFour = sesonFour;
	}
	public Double getYearPut() {
		return yearPut;
	}
	public void setYearPut(Double yearPut) {
		this.yearPut = yearPut;
	}
	
	
}
