package com.hotent.platform.web.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.cache.ICache;
import com.hotent.core.scheduler.SchedulerService;
import com.hotent.core.sms.impl.ModemMessage;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.platform.service.form.BpmFormTemplateService;
import com.hotent.platform.service.system.SysIndexColumnService;
import com.hotent.platform.webservice.util.PropUtils;
import com.hotent.platform.webservice.util.WebEnvUtil;

/**
 * 监控服务器启动和关闭事件。
 * <pre>
 * 1.服务器启动时，调用初始化系统模版事件。
 * 2.服务器关闭是，停止短信猫事件。
 * </pre>
 * @author ray
 *
 */
public class ServerListener implements ServletContextListener {
    	private static Log logger = LogFactory.getLog(ServerListener.class);
    	
    	private static int scheduleJob=0;
    	
    	//private static String isScheduleCluster="";//生产环境 集群环境需在执行定时任务的机子上多配置一个环境变量（oaschcluster=1），如果集群环境有配置同步时间，可去掉这个判断标记
    	
    	/*static{
    		try {
    			isScheduleCluster = System.getenv("oaschcluster");
    		} catch (NullPointerException e) {
    			logger.info("没有配置环境变量RUN_ENV，默认使用生成机(prd)");
    		} catch (SecurityException e) {
    			logger.error("读取环境变量错误" + e.getMessage());
    		}
    	}*/
    	
		private static void init() {
			try {
				/*
				String s= PropUtils.getPropertyByDirKeyWithDependByEnv("scheduleJob", "application");
				if(StringUtils.isNotEmpty(s)){
					scheduleJob=Integer.valueOf(s);
				}*/
			} catch (Exception ex) {
				scheduleJob = 0;
			}
		}
    	
    	public void contextDestroyed(ServletContextEvent event) {
	    ModemMessage.getInstance().stopService();	    
	    logger.debug("[contextDestroyed]停止短信猫服务。");
	    
	    /**
		 * 清理缓存。
		 * 有可能是memcached缓存，在结束时需要清除缓存。
		 */
	    ICache icache=AppUtil.getBean(ICache.class);
		icache.clearAll();
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.debug("---------[contextInitialized]开始初始化表单模版。");
		//初始化系统模版。
		BpmFormTemplateService.initTemplate();
		
		logger.debug("--------[contextInitialized]初始化表单模版成功。");
		
		logger.debug("[contextInitialized]开始初始化首页栏目。");
		SysIndexColumnService.initIndex();

		logger.debug("[contextInitialized]初始化首页栏目成功。");
		
		//启动定时器
		SchedulerService schedulerService = AppUtil.getBean(SchedulerService.class);
		try {
			//init();
			/*if(scheduleJob==1){*/
			schedulerService.start();
			/*}*/
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("定时器启动失败!");
		}
		
	}

}
