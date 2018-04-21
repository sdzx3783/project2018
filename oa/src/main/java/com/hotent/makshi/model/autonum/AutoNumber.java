package com.hotent.makshi.model.autonum;

public class AutoNumber {
	private long id;
	private String name;
	private String alias;
	private long curvalue;
	private String uninque_no;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public long getCurvalue() {
		return curvalue;
	}

	public void setCurvalue(long curvalue) {
		this.curvalue = curvalue;
	}

	public String getUninque_no() {
		return uninque_no;
	}

	public void setUninque_no(String uninque_no) {
		this.uninque_no = uninque_no;
	}

}
