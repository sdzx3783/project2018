package com.hotent.makshi.model.project;

import java.io.Serializable;

public class FlowChartJsonData implements Serializable{

	private static final long serialVersionUID = 4693278173270419249L;

	private String name;
	
	private String code;
	
	private Float x;
	
	private Float y;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	
}
