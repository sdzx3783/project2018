package com.hotent.makshi.service.fetcher;

public interface IFetcher {
	/**
	 * 提取数据
	 * @throws Exception 
	 */
	void fetcheData(String tableName,String businessKey) throws Exception;
}
