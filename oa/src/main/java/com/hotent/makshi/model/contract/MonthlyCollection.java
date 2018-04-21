package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;

public class MonthlyCollection extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5869632670831580464L;
	//主键
	protected Long id;
	/**
	 *合同归属部门
	 */
	protected String  department;
	
	protected Double january;
	
	protected Double february;
	
	protected Double march;
	
	protected Double april;
	
	protected Double may;
	
	protected Double june;
	
	protected Double july;
	
	protected Double august;
	
	protected Double september;
	
	protected Double october;
	
	protected Double november;
	
	protected Double december;
	
	protected Double invoiceAmount;
	
	protected Double payRate;
	
	protected Double leftMoney;

	protected Double billingMoney;
	
	protected Double pointLeft;
	
	
	public Double getBillingMoney() {
		return billingMoney;
	}

	public void setBillingMoney(Double billingMoney) {
		this.billingMoney = billingMoney;
	}

	public Double getPointLeft() {
		return pointLeft;
	}

	public void setPointLeft(Double pointLeft) {
		this.pointLeft = pointLeft;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getJanuary() {
		return january;
	}

	public void setJanuary(Double january) {
		this.january = january;
	}

	public Double getFebruary() {
		return february;
	}

	public void setFebruary(Double february) {
		this.february = february;
	}

	public Double getMarch() {
		return march;
	}

	public void setMarch(Double march) {
		this.march = march;
	}

	public Double getApril() {
		return april;
	}

	public void setApril(Double april) {
		this.april = april;
	}

	public Double getMay() {
		return may;
	}

	public void setMay(Double may) {
		this.may = may;
	}

	public Double getJune() {
		return june;
	}

	public void setJune(Double june) {
		this.june = june;
	}

	public Double getJuly() {
		return july;
	}

	public void setJuly(Double july) {
		this.july = july;
	}

	public Double getAugust() {
		return august;
	}

	public void setAugust(Double august) {
		this.august = august;
	}

	public Double getSeptember() {
		return september;
	}

	public void setSeptember(Double september) {
		this.september = september;
	}

	public Double getOctober() {
		return october;
	}

	public void setOctober(Double october) {
		this.october = october;
	}

	public Double getNovember() {
		return november;
	}

	public void setNovember(Double november) {
		this.november = november;
	}

	public Double getDecember() {
		return december;
	}

	public void setDecember(Double december) {
		this.december = december;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public Double getLeftMoney() {
		return leftMoney;
	}

	public void setLeftMoney(Double leftMoney) {
		this.leftMoney = leftMoney;
	}
	
	
}