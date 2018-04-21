package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractSupplier extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5578134226595522803L;

	protected String  ownerName;
	
	protected Double contractMoney;
	
	protected Double rate;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}
