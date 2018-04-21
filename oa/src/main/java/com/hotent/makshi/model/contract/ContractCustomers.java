package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractCustomers extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578134226595522803L;

	protected String  department;
	
	protected Double allAccount;
	
	protected Double rate;


	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getAllAccount() {
		return allAccount;
	}

	public void setAllAccount(Double allAccount) {
		this.allAccount = allAccount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}
