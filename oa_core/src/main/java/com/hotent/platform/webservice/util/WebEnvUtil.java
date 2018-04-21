package com.hotent.platform.webservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebEnvUtil {
	private static Logger logger = LoggerFactory.getLogger(WebEnvUtil.class);
	private static String RUN_ENV = "";

	static {
		try {
			RUN_ENV = System.getenv("RUN_ENV");
		} catch (NullPointerException e) {
			logger.info("没有配置环境变量RUN_ENV，默认使用生成机(prd)");
		} catch (SecurityException e) {
			logger.error("读取环境变量错误" + e.getMessage());
		}
	}
	/**
	 * 没配置环境变量时返回空
	 * @return
	 */
	public static String getRunEnv(){
		return RUN_ENV;
	}
}
