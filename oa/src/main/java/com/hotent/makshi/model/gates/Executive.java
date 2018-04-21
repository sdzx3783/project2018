package com.hotent.makshi.model.gates;

public class Executive {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	private String orgId;
	private String orgName;
	private long count;
	private long boy;
	private long girl;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public long getCount() {
		return boy + girl;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getBoy() {
		return boy;
	}

	public void setBoy(long boy) {
		this.boy = boy;
	}

	public long getGirl() {
		return girl;
	}

	public void setGirl(long girl) {
		this.girl = girl;
	}

}
