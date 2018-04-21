package com.hotent.makshi.model.bidding;

/**
 * 对象功能:招标申请打印对象
 */
public class BiddingOption 
{
	private String option;
	
	private String name;
	
	private java.util.Date time;
	
	private String taskName;

	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
	}

	public String getOption() {
		return option;
	}

	public String getName() {
		return name;
	}

	public java.util.Date getTime() {
		return time;
	}

	public BiddingOption() {
		super();
	}

	@Override
	public String toString() {
		return "BiddingSchemeExamineOption [option=" + option + ", name="
				+ name + ", time=" + time + "]";
	}
	
	
}