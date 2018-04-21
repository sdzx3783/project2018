/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.hr.EntryService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.service.system.SysUserService;

/**
 * @author cesc
 *入职关联
 */
@Component("entryFetcher")
public class EntryFetcher implements IFetcher {
	@Resource
	private EntryService entryService;
	@Resource
	private SysUserService userService;
	@Resource
	private UserInfomationService infomationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	
	private static Logger logger=Logger.getLogger(EntryFetcher.class);
	
	@Override
	public void fetcheData(String tableName, String businessKey)  throws Exception {
		logger.info("--------流程数据转移到业务表开始---------------");
		try {
			entryService.addEntry(businessKey);
		} catch (Exception e) {
			
		}
		}
		
}
