package com.hotent.makshi.model.project;

import java.io.Serializable;

public class FlowChartJsonLink implements Serializable{

	
	private static final long serialVersionUID = -3486392478315945423L;

	private String source;
	
	private String target;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
