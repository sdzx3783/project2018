package com.hotent.makshi.utils;


public interface Constants {
	/**
	 * 已统计
	 */
	Integer COUNT_FLAG_TRUE=1;
	
	/**
	 * 未统计
	 */
	Integer COUNT_FLAG_FALSE=0;
	
	/**
	 * 缺勤一天
	 */
	Integer LEAVERANGEALLDAY=0;
	
	/**
	 * 缺勤上午
	 */
	Integer LEAVEMORNING=1;
	
	/**
	 * 缺勤下午
	 */
	Integer LEAVEAFTERNOON=2;
	
	/**
	 * 缺勤状态，0：未缺勤，1：缺勤一天，2：缺勤半天
	 */
	Integer LEAVESTATEARR[]={0,1,2};
	
	/**
	 * 缺勤时间段
	 * 1.上午
	 * 2.下午
	 */
	Integer LEAVERANGEARRArr[]={1,2};
	
}
