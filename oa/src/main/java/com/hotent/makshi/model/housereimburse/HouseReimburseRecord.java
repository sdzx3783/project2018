package com.hotent.makshi.model.housereimburse;

public class HouseReimburseRecord {

	private Long id;
	private Long reimburse_id;
	private String reimburse_at;
	private String moneys;
	private String person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReimburse_id() {
		return reimburse_id;
	}

	public void setReimburse_id(Long reimburse_id) {
		this.reimburse_id = reimburse_id;
	}

	public String getReimburse_at() {
		return reimburse_at;
	}

	public void setReimburse_at(String reimburse_at) {
		this.reimburse_at = reimburse_at;
	}

	public String getMoneys() {
		return moneys;
	}

	public void setMoneys(String moneys) {
		this.moneys = moneys;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
}
