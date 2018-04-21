package com.hotent.makshi.model.gates;

public class ExecutiveAsset {

	private String department_id;
	private String department;
	private String asset_code;
	private int asset_total;

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAsset_code() {
		return asset_code;
	}

	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}

	public int getAsset_total() {
		return asset_total;
	}

	public void setAsset_total(int asset_total) {
		this.asset_total = asset_total;
	}

}
