package com.hotent.makshi.model.contract;

import java.util.List;


public class ContractNumAndMoneyTotal{
	
	private Integer rowTotalNum;
	
	private Double rowTotalMoney;
	
	private List<ContractNumAndMoney> list;

	public Integer getRowTotalNum() {
		return rowTotalNum;
	}

	public void setRowTotalNum(Integer rowTotalNum) {
		this.rowTotalNum = rowTotalNum;
	}

	public Double getRowTotalMoney() {
		return rowTotalMoney;
	}

	public void setRowTotalMoney(Double rowTotalMoney) {
		this.rowTotalMoney = rowTotalMoney;
	}

	public List<ContractNumAndMoney> getList() {
		return list;
	}

	public void setList(List<ContractNumAndMoney> list) {
		this.list = list;
	}
	
	
}
