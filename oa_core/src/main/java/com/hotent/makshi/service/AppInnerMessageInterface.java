package com.hotent.makshi.service;

public interface AppInnerMessageInterface {

	public abstract void addMyMessage(String pushType,Long orgId,Long userId,Long runId,Long taskId,String context,String url,String ticker,String title);
	
}
