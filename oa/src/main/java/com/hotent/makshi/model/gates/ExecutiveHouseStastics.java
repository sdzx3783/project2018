/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-30，cp创建。 
 */
package com.hotent.makshi.model.gates;

public class ExecutiveHouseStastics {

	private String orgId;
	private String orgName;
	private int houseNums;
	private int roomNums;
	private double monthMoneys;
	private int peopleNums;

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

	public int getHouseNums() {
		return houseNums;
	}

	public void setHouseNums(int houseNums) {
		this.houseNums = houseNums;
	}

	public int getRoomNums() {
		return roomNums;
	}

	public void setRoomNums(int roomNums) {
		this.roomNums = roomNums;
	}

	public double getMonthMoneys() {
		return monthMoneys;
	}

	public void setMonthMoneys(double monthMoneys) {
		this.monthMoneys = monthMoneys;
	}

	public int getPeopleNums() {
		return peopleNums;
	}

	public void setPeopleNums(int peopleNums) {
		this.peopleNums = peopleNums;
	}

}
