package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FlowChartJsonResult implements Serializable{

	private static final long serialVersionUID = 2543139994438731661L;

	private List<FlowChartJsonData> datas=new ArrayList<>();
	
	private List<FlowChartJsonLink> links=new ArrayList<>();


	public List<FlowChartJsonData> getDatas() {
		return datas;
	}

	public void setDatas(List<FlowChartJsonData> datas) {
		this.datas = datas;
	}

	public List<FlowChartJsonLink> getLinks() {
		return links;
	}

	public void setLinks(List<FlowChartJsonLink> links) {
		this.links = links;
	}
	
	
}
