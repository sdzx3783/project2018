package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractNumAndMoney extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8180279122778576653L;

	private Integer year;
	
	private Integer num;
	
	private Double money;

	public Integer getNum() {
		return num;
	}

	
	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	
	
}
