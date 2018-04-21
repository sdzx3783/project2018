package com.hotent.makshi.model.operation;

import com.hotent.core.model.BaseModel;

public class OverTime extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3507901318201700117L;
	// 主键
	protected Long id;

	// 姓名
	protected String name;

	// 用户Id
	protected Long userId;

	// 加班总天数
	protected Double overTimeDays;

	//调休天数
	protected Double adjustDays;
	
	// 剩余未调休天数
	protected Double leftAdjustDays;
	
	//年份
	protected String appliYear;
	
	public String getAppliYear() {
		return appliYear;
	}

	public void setAppliYear(String appliYear) {
		this.appliYear = appliYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Double getOverTimeDays() {
		return overTimeDays;
	}

	public void setOverTimeDays(Double overTimeDays) {
		this.overTimeDays = overTimeDays;
	}

	public Double getAdjustDays() {
		return adjustDays;
	}

	public void setAdjustDays(Double adjustDays) {
		this.adjustDays = adjustDays;
	}

	public Double getLeftAdjustDays() {
		return leftAdjustDays;
	}

	public void setLeftAdjustDays(Double leftAdjustDays) {
		this.leftAdjustDays = leftAdjustDays;
	}

	public OverTime() {
		super();
	}

}
