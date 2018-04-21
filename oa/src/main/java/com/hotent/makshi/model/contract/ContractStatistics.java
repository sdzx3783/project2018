package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class ContractStatistics extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8919233331707824399L;

	private String singingYear;
	
	private String allNumber;
	
	private Double allMoney;
	
	private String cgNumber;
	
	private Double cgMoney;
	
	private String fcgNumber;
	
	private Double fcgMoney;

	public String getSingingYear() {
		return singingYear;
	}

	public void setSingingYear(String singingYear) {
		this.singingYear = singingYear;
	}

	public String getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(String allNumber) {
		this.allNumber = allNumber;
	}

	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public String getCgNumber() {
		return cgNumber;
	}

	public void setCgNumber(String cgNumber) {
		this.cgNumber = cgNumber;
	}

	public Double getCgMoney() {
		return cgMoney;
	}

	public void setCgMoney(Double cgMoney) {
		this.cgMoney = cgMoney;
	}

	public String getFcgNumber() {
		return fcgNumber;
	}

	public void setFcgNumber(String fcgNumber) {
		this.fcgNumber = fcgNumber;
	}

	public Double getFcgMoney() {
		return fcgMoney;
	}

	public void setFcgMoney(Double fcgMoney) {
		this.fcgMoney = fcgMoney;
	}

	
}