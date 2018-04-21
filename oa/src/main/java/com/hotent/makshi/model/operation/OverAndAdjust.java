package com.hotent.makshi.model.operation;

import java.util.Date;

import com.hotent.core.model.BaseModel;

public class OverAndAdjust extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3542002176839625439L;

	// 主键
	protected Long id;
	
	//开始时间
	protected Date startDate;
	//结束时间
	protected Date endDate;
	//开始上下午
	protected Integer morning;
	//结束上下午
	protected Integer afternoon;
	//天数
	protected Double days;
	
	
	
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getMorning() {
		return morning;
	}
	public void setMorning(Integer morning) {
		this.morning = morning;
	}
	public Integer getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(Integer afternoon) {
		this.afternoon = afternoon;
	}

	
}
