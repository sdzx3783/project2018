package com.hotent.makshi.model.finance;

import java.util.HashMap;
import java.util.Map;

import com.hotent.core.model.BaseModel;

public class Workstats extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4928218172250525203L;

	private String userid;
	
	private String username;
	
	private String day;
	
	private Integer hadwork;// 0未填报 3代表半天(上午)填考勤 7、8、12代表全天填考勤  4代表半天考勤下午(7/8/12已无效)
	private Integer isleader;// 0代表普通员工 1代表领导（不用填考勤填报）
	
	public Integer getIsleader() {
		return isleader;
	}

	public void setIsleader(Integer isleader) {
		this.isleader = isleader;
	}

	public static final String  ISLEAVEKEY="isleave";//是否请假key
	public static final String  LEAVETYPEKEY="leavetype";//是否请假key
	public static final String  ISOVERTIMEKEY="isovertime";//是否加班key
	public static final String  ISADJUSTKEY="isadjust";//是否调休key
	
	private Map<String,Object> amMap=new HashMap<>();
	private Map<String,Object> bmMap=new HashMap<>();
	private int amOrBm=0;//默认值 0 代表全天上班 1代表上午 2代表下午
	
	
	public int getAmOrBm() {
		return amOrBm;
	}

	public void setAmOrBm(int amOrBm) {
		this.amOrBm = amOrBm;
	}

	public Map<String, Object> getAmMap() {
		return amMap;
	}

	public void setAmMap(Map<String, Object> amMap) {
		this.amMap = amMap;
	}

	public Map<String, Object> getBmMap() {
		return bmMap;
	}

	public void setBmMap(Map<String, Object> bmMap) {
		this.bmMap = bmMap;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getHadwork() {
		return hadwork;
	}

	public void setHadwork(Integer hadwork) {
		this.hadwork = hadwork;
	}
}
