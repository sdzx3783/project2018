package com.hotent.platform.model.worktime;

import com.hotent.core.model.BaseModel;

public class WorkItem extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1132848454631032192L;
	
	private String refid;
	
	private String workname;
	private String worknameView;
	private Integer workrate;
	private Integer type;
	public String getRefid() {
		return refid;
	}
	public void setRefid(String refid) {
		this.refid = refid;
	}
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public Integer getWorkrate() {
		return workrate;
	}
	public void setWorkrate(Integer workrate) {
		this.workrate = workrate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getWorknameView() {
		if(type==0){//自定义任务类 显示下拉框显示值
			String defaultview="日常工作";
			switch (workname) {
			case "7":
				defaultview="日常工作"; break;
			case "1":
				defaultview="培训"; break;
			case "2":
				defaultview="会议"; break;
			case "3":
				defaultview="请假"; break;
			case "4":
				defaultview="集体活动"; break;
			case "5":
				defaultview="空闲"; break;
			case "6":
				defaultview="其他"; break;
			}
			return defaultview;
		}
		return workname;
	}
	
	
}
